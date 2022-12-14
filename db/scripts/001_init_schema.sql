CREATE TABLE IF NOT EXISTS item
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(40),
    description VARCHAR(255),
    created     date,
    status      BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS account
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(40),
    login    VARCHAR(40),
    password VARCHAR(40),
    CONSTRAINT pk_account PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_login UNIQUE (login);
