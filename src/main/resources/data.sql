INSERT INTO app_user (username, password_hash, role)
VALUES ('admin', '$2a$10$XOaIkn7ob49a6WizP5t.MOwObdjhfcR8sI.h9ux.NuRUYHa9tYG/y', 'TEACHER'),
       ('anna', '$2a$10$XOaIkn7ob49a6WizP5t.MOwObdjhfcR8sI.h9ux.NuRUYHa9tYG/y', 'STUDENT'),
       ('james', '$2a$10$XOaIkn7ob49a6WizP5t.MOwObdjhfcR8sI.h9ux.NuRUYHa9tYG/y', 'STUDENT');





INSERT INTO media (
    filename,
    type,
    visibility,
    title,
    description,
    version,
    owner_id
)
VALUES (
           'spring-boot-guide.pdf',
           'PDF',
           TRUE,
           'Spring Boot Guide',
           'Ein umfassender Leitfaden für Spring Boot',
           0,
           1
       );