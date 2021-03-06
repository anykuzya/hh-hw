INSERT INTO area (area_name)
VALUES ('Москва'), ('Санкт Петербург'), ('Казань'), ('Воронеж'),
       ('Тверь'), ('Липецк'), ('Тула'), ('Новосибирск'), ('Уфа'),('Нижний Новгород'), ('Москва'); -- ага, ещё одна: https://tinyurl.com/y7yorae5

INSERT INTO university (university_name, area_id)
VALUES
       ('MФТИ', 1),
       ('МГТУ им Баумана', 1),
       ('НИУ ВШЭ', 1),
       -- кстати, на hh.ru тоже вышки раздельно можно и нужно выбирать, я проверила
       ('НИУ ВШЭ в НН', 10), -- т.к. у вышки филиал в нн и в питере, будем считать, что это разные образовательные учреждения,
       ('НИУ ВШЭ в СПБ', 2), -- тк это не самое важное поле и я не хочу тут городить еще одну таблицу :(
       ('ИТМО', 2),
       ('СПбГУ', 2),
       ('Иннополис', 3),
       ('ТГУ', 7),
       ('БГУ', 9),
       ('ЛГПУ', 6),
       ('НГУ', 8);

INSERT INTO employer (company_name, company_description)
VALUES ('HeadHunter', 'HeadHunter — крупнейшая российская компания интернет-рекрутмента, развивающая бизнес в России, Белоруссии, Казахстане и на Украине. Клиентами HeadHunter являются порядка 1 000 000 компаний. Обширная база кандидатов HeadHunter содержит более чем 30 млн резюме, а среднее дневное количество вакансий превышает 450 тыс.'),
       ('JetBrains', 'JetBrains — международная компания, которая разрабатывает инструменты для разработки на языках Java, Kotlin, C#, F#, C++, Ruby, Python, PHP, JavaScript и многих других, а также средства командной работы.'),
       ('Яндекс', '«Я́ндекс» — российская транснациональная компания в отрасли информационных технологий, зарегистрированная в Нидерландах и владеющая одноимённой системой поиска в Сети, интернет-порталами и службами в нескольких странах. Наиболее заметное положение занимает на рынках России, Турции, Белоруссии и Казахстана.'),
       ('Mail.ru', 'Mail.ru — русскоязычный интернет-портал, принадлежащий технологической компании Mail.ru Group.'),
       ('Telegram', 'Telegram — кроссплатформенный мессенджер, позволяющий обмениваться сообщениями и медиафайлами многих форматов. Клиентские приложения Telegram доступны для Android, iOS, Windows Phone, Windows, macOS и GNU/Linux.'),
       ('ГУЗ "Родильный дом №1 г.Тулы"', NULL), ('Детский сад "Солнышко"', NULL), ('МБОУ СОШ №14 г. Липецка', 'школа'), ('НИУ ВШЭ', 'Высшая школа экономики'),
       ('Рога и Копыта', NULL), ('Копыта и Рога', 'Не путать с «Рога и копыта»!!!'), ('Шарашкина контора', NULL), ('Башкирский мёд', 'Семейная пасека'), ('Рога и Копыта', 'ага, ещё одни');

INSERT INTO employer_to_area (employer_id, area_id)
VALUES (1, 1), -- hh в москве,
       (2, 2), (2, 1), (2, 8), -- jb в питере, москве, новосибе
       (3, 1), (3, 2), (3, 3), (3, 10), (3, 8), -- яндекс в москве, питере, казани, НН, новосибе
       (4, 1), (4, 2), (4, 4), (4, 10), -- мейл в москве, питере, воронеже и НН
       (6, 7), (7, 6), (8, 6), (9, 1), (10, 5), (11, 5), (12, 6), (13, 9), (14, 11);


INSERT INTO vacancy (employer_id, position_name, opened_at, compensation_from, compensation_to, compensation_gross, area_id, other)
VALUES (1, 'Младший программист', make_date(2020, 11, 30), NULL, 87000, false, 1, NULL),
       (1, 'Ученик в школе программистов', make_date(2020, 08, 15), 0, 0, true, 1, 'старт школы в ноябре'),
       (1, 'Senior Java Developer', make_date(2020, 12, 2), 150000, 200000, true, 1, NULL),
-- ^^hh
       (2, 'Программист Java', make_date(2020, 11, 25), 120000, 200000, true, 2, NULL),
       (2, 'Web-разработчик', make_date(2020, 11, 26), 100000, 180000, true, 1, NULL),
       (2, 'DevOps', make_date(2020, 11, 24), NULL, 300000, false, 2, 'Не менее 3 лет опыта работы!'),
       (2, 'хозяюшка', make_date(2019, 12, 12), NULL, 40000, true, 1, NULL),
-- ^^jb
       (3, 'синьор-помидор', make_date(2020, 10, 30), NULL, 270000, true, 1, NULL),
       (3, 'синьор-помидор', make_date(2020, 10, 28), 100500, NULL, true, 3, NULL),
       (3, 'тестировщик', make_date(2020, 10, 25), NULL, NULL, true, 10, NULL),
-- ^^ya
       (4, 'Веб Программист', make_date(2020, 09, 12), NULL, NULL, false, 2, NULL),
       (4, 'тестировщик', make_date(2020, 09, 15), NULL, NULL, false, 4, NULL),
       (4, 'Инженер поддержки', make_date(2020, 09, 17), NULL, NULL, false, 1, NULL),
-- ^^mail
       (6, 'Акушерка', make_date(2020, 12, 4), 20000, 30000, false, 7, NULL),
       (7, 'Воспитатель', make_date(2020, 12, 3), 19000, 21000, false, 6, NULL),
       (7, 'Нянечка', make_date(2020, 12, 2), 15000, 17000, false, 6, NULL),
       (8, 'Учитель русского языка', make_date(2020, 9, 20), NULL, NULL, false, 6, NULL),
       (8, 'Учитель географии', make_date(2020, 9, 20), NULL, NULL, false, 6, NULL),
       (8, 'Учитель истории', make_date(2020, 9, 20), NULL, NULL, false, 6, NULL),
       (9, 'Преподаватель вышсей математики', make_date(2020, 9, 20), NULL, NULL, false, 1, 'круто, если вы гений, но еще лучше, если вы можете объяснять матан несообразительным студентам'),
       (10, 'Рогоносец', make_date(2020, 11, 27), NULL, NULL, false, 5, NULL),
       (11, 'Бездельник', make_date(2020, 11, 26), NULL, NULL, false, 5, NULL),
       (12, 'Выдаватель филькиных грамот', make_date(2020, 11, 25), NULL, NULL, false, 6, NULL),
       (13, 'Пасечник', make_date(2020, 09, 23), NULL, NULL, false, 9, NULL),
       (14, 'Лентяй', make_date(2020, 11, 26), NULL, NULL, false, 11, NULL);

INSERT INTO candidate (area_id, relocation_acceptable, candidate_full_name, candidate_birthday,
                       phone, email, education_level, experience_years, expected_compensation)
VALUES (1, true,'Кузнецова Анна Алексеевна', make_date(1996, 10, 11), '+79951182533', 'anna.kuznetsova@example.com', 'бакалавр', 3, 100500),
       (3, false, 'Елена Прекрасная', make_date(1989, 12, 24), '+380 9929929999', 'elena@beauty.com', 'высшее', 2, null),
       (6, true, 'Василиса Премудрая', make_date(1968, 3, 12), '+44 44-523-541-430', 'vasya@smart', 'кандидат наук', 20, 200000),
       (8, true, 'Алёша Попович', make_date(1994, 4, 27), '89259872358', 'alpop@boga.tyr', 'неоконченное высшее', 2, 15000),
       (10, false, 'Добрыня Никитич', make_date(1988, 5, 3), '89761239300', 'dobrynya@boga.tyr', 'бакалавр', 4, 140000),
       (2, false, 'Илья Муромец', make_date(1992, 8, 22), '89231237646', 'muromets@boga.tyr', 'магистр', 8, 180000),
       (5, true, 'Иван-Дурак', make_date(1990, 7, 24), '89437451532', null , 'среднее', 0, 5000),
       (4, true, 'Емеля', make_date(199, 11, 27), '+47 97232341', null, 'среднее', 4, 10000);

INSERT INTO candidates_to_universities (candidate_id, university_id, graduation_year)
VALUES (1, 3, 2018), (2, 9, 2020), (3, 2, 1995), (3, 1, 2000), (4, 12, null), (5, 4, 2014), (6, 6, 2016), (6, 5, 2019);

INSERT INTO resume (candidate_id, related_experience_years, created_at, other)
VALUES (1, 2, make_date(2019, 7, 1), 'C++/QT developer'),
       (1, 1, make_date(2020, 10, 15), 'kotlin developer'),
       (1, 0, make_date(2020, 8, 15), 'java developer'),
--^^anna
       (2, 1, make_date(2020, 3, 8), 'врач-акушер'),
       (2, 1, make_date(2019, 12, 8), 'уборщица'),
--^^elena
       (3, 15, make_date(2019, 11, 16), 'профессор'),
--^^vasya
       (4, 1, make_date(2020, 11, 18), 'веб-разработчик'),
--^^alesha
       (5, 2, make_date(2020, 11, 13), 'backend разработчик'),
--^^dobrynya
       (6, 4, make_date(2020, 10, 28), 'full-stack разработчик'),
--^^ilia
       (7, 0, make_date(2020, 11, 30), 'чтец'),
       (7, 0, make_date(2020, 11, 30), 'жнец'),
       (7, 0, make_date(2020, 11, 30), 'на дуде игрец'),
       (7, 0, make_date(2020, 11, 30), 'тестировщик'),
--^^ivan
       (8, 2, make_date(2020, 12, 8), 'на печи ездок'),
       (8, 2, make_date(2020, 12, 4), 'лежебока');
--^^emelya

INSERT INTO conversation (vacancy_id, resume_id, type, contacted_at)
VALUES (2, 3, 'response', make_date(2020, 8, 20)),
       (2, 7, 'response', make_date(2020, 8, 25)),
       (9, 6, 'invite', make_date(2020, 11, 1)),
       (4, 9, 'response', make_date(2020, 11, 29)),
       (5, 7, 'response', make_date(2020, 11, 27)),
       (5, 9, 'response', make_date(2020, 11, 28)),
       (6, 9, 'response', make_date(2020, 11, 24)),
       (7, 5, 'response', make_date(2020, 01, 24)),
       (7, 14, 'response', make_date(2020, 12, 5)),
       (8, 9, 'response', make_date(2020, 11, 1)),
       (8, 8, 'response', make_date(2020, 11, 2)),
       (10, 7, 'response', make_date(2020, 11, 14)),
       (12, 13, 'response', make_date(2020, 9, 17)),
       (12, 7, 'response', make_date(2020, 9, 17)),
       (14, 4, 'invite', make_date(2020, 12, 5)),
       (20, 6, 'response', make_date(2020, 9, 21)),
       (22, 15, 'response', make_date(2020, 12, 10)),
       (22, 7, 'response', make_date(2020, 12, 10)),
       (22, 12, 'response', make_date(2020, 11, 30)),
       (23, 15, 'response', make_date(2020, 12, 9)),
       (23, 13, 'response', make_date(2020, 12, 10)),
       (25, 15, 'response', make_date(2020, 12, 22));
