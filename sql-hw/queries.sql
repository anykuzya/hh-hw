-- Вывести название вакансии, город, в котором опубликована вакансия(можно просто area_id),
-- имя работодателя для первых 10 вакансий, у которых не указана зарплата,
-- сортировать по дате создания вакансии от новых к более старым.

WITH ten_vacancies_to_area_names AS (
    SELECT position_name, area_name, employer_id FROM vacancy
    INNER JOIN area ON area.area_id = vacancy.area_id
    ORDER BY opened_at DESC
    LIMIT 10
) SELECT position_name, area_name, company_name
FROM ten_vacancies_to_area_names JOIN employer ON ten_vacancies_to_area_names.employer_id = employer.employer_id;

-- Вывести среднюю максимальную зарплату в вакансиях, среднюю минимальную и среднюю среднюю (compensation_to - compensation_from) ???
-- наверное, средняя средняя это всё-таки (compensation_to + compensation_from) / 2 ?
-- Все значения должны быть выведены в одном запросе. Значения должны быть указаны до вычета налогов.
WITH gross_salary_range AS (
    SELECT
        CASE
           WHEN compensation_gross IS TRUE
           THEN compensation_to * 0.87
           ELSE compensation_to
           END AS compensation_to,
       CASE
           WHEN compensation_gross IS TRUE
           THEN compensation_from * 0.87
           ELSE compensation_from
           END AS compensation_from
    FROM vacancy
) SELECT
    avg(compensation_to) AS average_max,   -- этот запрос считает средние значения только среди записей,
    avg(compensation_from) AS average_min, -- где соответствующее значение не нул. если хотим считать среднее,
                                           -- считая нул нулём, нужно использовать COALESCE(compensation_<to/from>, 0)
    avg((compensation_from + compensation_to) / 2) AS average_average
FROM gross_salary_range;

-- Вывести медианное количество вакансий на компанию. Использовать percentile_cont.
WITH vacancies_per_employer AS (
    SELECT count(vacancy_id) AS vacancies_amount, company_name FROM vacancy
    INNER JOIN employer ON vacancy.employer_id = employer.employer_id GROUP BY company_name
) SELECT percentile_cont(0.5) WITHIN GROUP (ORDER BY vacancies_amount) FROM vacancies_per_employer;

-- Вывести топ-5 компаний, получивших максимальное количество откликов на одну вакансию, в порядке убывания откликов.
-- Если более 5 компаний получили одинаковое максимальное количество откликов, отсортировать по алфавиту и вывести только 5.
WITH conversations_per_vacancy_to_employer_id AS (
    SELECT count(v.vacancy_id) AS conversation_amount, employer_id
    FROM conversation
    INNER JOIN vacancy v ON v.vacancy_id = conversation.vacancy_id
    GROUP BY v.vacancy_id
) SELECT company_name FROM
    (SELECT DISTINCT company_name, conversation_amount FROM conversations_per_vacancy_to_employer_id as cpvtei
    INNER JOIN employer e ON e.employer_id = cpvtei.employer_id
    ORDER BY conversation_amount DESC, company_name
    LIMIT 5) as top_5_companies_most_interest_vacancies_conversation_amount