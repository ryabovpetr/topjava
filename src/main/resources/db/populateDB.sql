DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, calories, date_time)
VALUES (100000, 'завтрак юзера', 580, '2023-07-25 10:15:00'),
       (100001, 'завтрак админа', 700, '2023-07-25 09:55:00'),
       (100000, 'ужин юзера', 1400, '2023-07-25 20:00:00'),
       (100001, 'обед админа', 2200, '2023-07-25 14:00:00'),
       (100000, 'Завтрак', 500, '2020-01-30 10:00:00'),
       (100000, 'Обед', 1000, '2020-01-30 13:00:00')



