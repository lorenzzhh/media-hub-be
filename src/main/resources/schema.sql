CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );


CREATE UNIQUE INDEX IF NOT EXISTS idx_app_user_username ON app_user(username);


CREATE TABLE media
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    filename VARCHAR(255),
    type VARCHAR(255),
    visibility BOOLEAN NOT NULL,

    title VARCHAR(255),
    description VARCHAR(1000),

    version BIGINT,

    owner_id BIGINT,

    CONSTRAINT fk_media_owner
        FOREIGN KEY (owner_id)
            REFERENCES app_user(id)
);

CREATE INDEX IF NOT EXISTS idx_media_owner
    ON media(owner_id);

CREATE INDEX IF NOT EXISTS idx_media_filename
    ON media(filename);
