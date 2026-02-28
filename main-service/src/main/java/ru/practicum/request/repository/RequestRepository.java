package ru.practicum.request.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

import static ru.practicum.request.model.QRequest.request;
import static ru.practicum.user.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class RequestRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * Получить все заявки пользователя на участие в событиях через QueryDSL с BooleanExpression
     */
    public List<Request> findAllRequestsByUserId(Long userId) {
        // Проверка существования пользователя
        User foundUser = queryFactory.selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();

        if (foundUser == null) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }

        // Строим условие через BooleanExpression
        BooleanExpression userCondition = request.requester.eq(foundUser);

        // Основной запрос с использованием BooleanExpression
        return queryFactory.selectFrom(request)
                .where(userCondition)
                .orderBy(request.created.asc())
                .fetch();
    }

}
