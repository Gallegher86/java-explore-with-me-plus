package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.CategoryAlreadyExistException;
import ru.practicum.exception.CategoryNotEmptyException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(NewCategoryDto dto) {
        log.info("CategoryService: получен запрос на создание категории: {}.", dto);
        validateName(dto.getName());
        Category entity = categoryMapper.toCategory(dto);
        Category saved = categoryRepository.save(entity);
        CategoryDto result = categoryMapper.toCategoryDto(saved);
        log.info("CategoryService: категория сохранена: {}", result);
        return result;
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        log.info("CategoryService: получен запрос на удаление категории с id: {}.", catId);
        validateId(catId);

        if (eventRepository.existsByCategoryId(catId)) {
            String errorMessage = String.format("Нельзя удалить категорию с id: %d, есть связанные события.", catId);
            throw new CategoryNotEmptyException(errorMessage);
        }

        categoryRepository.deleteById(catId);
        log.info("CategoryService: категория с id: {} удалена.", catId);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, NewCategoryDto dto) {
        log.info("CategoryService: получен запрос на обновление категории с id {}, новое имя: {}.",
                catId, dto.getName());
        String name = dto.getName();
        Category saved = findById(catId);
        CategoryDto result;

        if (name.equals(saved.getName())) {
            result = categoryMapper.toCategoryDto(saved);
            return result;
        }

        validateName(name);
        saved.setName(name);
        result = categoryMapper.toCategoryDto(saved);
        log.info("CategoryService: категория обновлена: {}", result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> get(Pageable pageable) {
        log.info("CategoryService: получен запрос на получение списка категорий.");
        List<Category> saved = categoryRepository.findCategoriesWithPagination(pageable);
        List<CategoryDto> result = categoryMapper.toCategoryDtoList(saved);
        log.info("CategoryService: выдана страница категорий размером: {}, начиная с {}.",
                saved.size(), pageable.getOffset());
        return result;
    }

    @Override
    public CategoryDto getById(Long catId) {
        log.info("CategoryService: получен запрос на получение категории с id: {}.", catId);
        Category saved = findById(catId);
        CategoryDto result = categoryMapper.toCategoryDto(saved);
        log.info("CategoryService: категория выдана: {}", result);
        return result;
    }

    @Transactional(readOnly = true)
    private Category findById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("CategoryService: категория с id %d не найдена.", catId)));

        log.info("CategoryService: категория с id {} найдена.", catId);
        return category;
    }

    private void validateId(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            String errorMessage = String.format("CategoryService: категория с id %d не найдена.", catId);
            throw new NotFoundException(errorMessage);
        }
    }

    private void validateName(String name) {
        if (categoryRepository.existsByName(name)) {
            String errorMessage = String.format("CategoryService: категория %s уже существует.", name);
            throw new CategoryAlreadyExistException(errorMessage);
        }
    }
}
