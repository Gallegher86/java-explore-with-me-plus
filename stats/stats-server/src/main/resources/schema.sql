-- Создание таблицы хитов
CREATE TABLE IF NOT EXISTS endpoint_hits  (
                               id BIGSERIAL PRIMARY KEY,
                               app VARCHAR(50) NOT NULL,
                               uri VARCHAR(2000) NOT NULL,
                               ip VARCHAR(45) NOT NULL,
                               timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS view_stats (
                            id BIGSERIAL PRIMARY KEY,
                            app VARCHAR(255) NOT NULL,
                            uri VARCHAR(1000) NOT NULL,
                            hits BIGINT NOT NULL DEFAULT 0
);