CREATE TABLE category
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE pet
(
    id          SERIAL PRIMARY KEY NOT NULL,
    category_id INT REFERENCES category (id),
    name        VARCHAR(255),
    status      VARCHAR(255)
);

CREATE TABLE pet_tag
(
    pet_id INT NOT NULL REFERENCES pet (id) ON DELETE CASCADE,
    tag_id INT NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    PRIMARY KEY (pet_id, tag_id)
);


CREATE TABLE photo
(
    id          SERIAL                                    NOT NULL PRIMARY KEY,
    pet_id      INT REFERENCES pet (id) ON DELETE CASCADE NOT NULL,
    url         text                                      NOT NULL,
    description text
);

