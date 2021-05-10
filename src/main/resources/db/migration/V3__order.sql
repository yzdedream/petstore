CREATE TABLE pet_order
(
    id        SERIAL PRIMARY KEY NOT NULL,
    pet_id    INT                NOT NULL REFERENCES pet (id) ON DELETE RESTRICT,
    quantity  INT                NOT NULL,
    ship_date TIMESTAMP,
    status    VARCHAR(255),
    complete  BOOLEAN
);

