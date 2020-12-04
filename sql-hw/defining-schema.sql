CREATE TABLE IF NOT EXISTS area (
    area_id SERIAL PRIMARY KEY,
    area_name TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS university (
    university_id SERIAL PRIMARY KEY,
    university_name TEXT,
    area_id INTEGER REFERENCES areas(area_id)
);
CREATE TYPE education_level AS ENUM ('среднее', 'среднее специальное', 'неоконченное высшее',
                                     'высшее', 'бакалавр', 'магистр', 'кандидат наук', 'доктор наук');
CREATE TABLE IF NOT EXISTS candidate (
    candidate_id SERIAL PRIMARY KEY,
     -- договоримся, что кандидат указывает один город как свой, в то время как у работаодателя их может быть несколько
     -- так что тут прямая ссылка на область, а для работодателей мы сделаем отдельную табличку для связи many2many
    area_id INTEGER REFERENCES areas(area_id),
    relocation_acceptable BOOLEAN,
    candidate_full_name TEXT NOT NULL, -- считаем, что человек может не сообщить нам вообще никаких своих данных
                                      -- типа телефона/мыла/итд, но хотя бы имя мы о нём хотим знать
    candidate_birthday DATE, -- так сможем посчитать возраст, может, где-то hrы эйджисты и не хотят брать "молодых-зелёных"
    phone VARCHAR(20), -- пусть будет с запасом, а то коды стран разные бывают, так что не будем считать,
                       -- что не бывает больше привычных 11-ти значных номеров, а создадим поле "с запасом"
    email VARCHAR(320), -- это цифра не с потолка, а из https://stackoverflow.com/a/386302
    education_level education_level, -- как и в настоящем hh.ru в этом поле будет максимальный уровень образования
                                     -- (содержимое enum как раз оттуда), а вот именно вузов и годов выпуска
                                     -- может быть несколько, так что эта связь будет отображена через отдельную табличку
    experience_years SMALLINT, -- не будем брать четыре байта инта на то, что может быть компактнее https://stackoverflow.com/a/697893
    expected_compensation INTEGER -- ради чего мы тут все собрались (тут smallint может и не хватить!)
);
CREATE TABLE candidates_to_universities (
    candidate_id INTEGER REFERENCES candidate(candidate_id) NOT NULL,
    university_id INTEGER REFERENCES university(university_id) NOT NULL,
    graduation_year SMALLINT
);
CREATE TABLE IF NOT EXISTS resume (
    resume_id SERIAL PRIMARY KEY,
    candidate_id INTEGER REFERENCES candidate(candidate_id) NOT NULL,
    related_experience_years SMALLINT,
    created_at DATE NOT NULL,
    other TEXT -- здесь всякие подробности про навыки юзера, релевантные данному резюме,
               -- но которые не хочется расписывать подробно в рамках этого дз
);
CREATE TABLE IF NOT EXISTS employer (
    employer_id SERIAL PRIMARY KEY,
    company_name VARCHAR(300) NOT NULL,
    company_description TEXT
);
CREATE TABLE IF NOT EXISTS employer_to_area (
    employer_id INTEGER REFERENCES employer(employer_id) NOT NULL,
    area_id INTEGER REFERENCES area(area_id) NOT NULL
);
CREATE TABLE IF NOT EXISTS vacancy (
    vacancy_id SERIAL PRIMARY KEY,
    employer_id INTEGER REFERENCES employer(employer_id) NOT NULL,
    position_name VARCHAR(300) NOT NULL,
    opened_at DATE NOT NULL,
    compensation_from INTEGER,
    compensation_to INTEGER,
    compensation_gross BOOLEAN, -- TODO убрать этот коммент
    -- true - до вычета, false -- после. то есть на руки compensation * 0.87, если тут true
    other TEXT -- здесь всякие подробности про вакансию, которые не хочется расписывать подробно в рамках этого дз
);

CREATE TYPE conversation_type AS ENUM ('отклик', 'приглашение'); -- когда кандидат апплаится на вакансию, это отклик
                                            -- когда hr нашел резюме и предлагает человеку вакансию, это приглашение
CREATE TABLE IF NOT EXISTS conversation (
    conversation_id SERIAL PRIMARY KEY,
    vacancy INTEGER REFERENCES vacancy(vacancy_id) NOT NULL,
    resume INTEGER REFERENCES resume(resume_id) NOT NULL,
    type conversation_type NOT NULL,
    contacted_at DATE NOT NULL
);