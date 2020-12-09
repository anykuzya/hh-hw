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
