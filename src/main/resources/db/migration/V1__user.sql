CREATE TYPE user_status AS ENUM ('active', 'banned');

CREATE TABLE app_user
(
    id          SERIAL PRIMARY KEY NOT NULL,
    username    VARCHAR(255) UNIQUE,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    email       VARCHAR(255),
    phone       VARCHAR(255),
    user_status user_status                 DEFAULT 'active',
    created     TIMESTAMP          NOT NULL DEFAULT current_timestamp
);

