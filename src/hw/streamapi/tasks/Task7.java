package hw.streamapi.tasks;

import hw.streamapi.common.Company;
import hw.streamapi.common.Task;
import hw.streamapi.common.Vacancy;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 implements Task {

    // кажется, что тут тоже быстрее бы не получилось -- чтобы найти все названия вакансий,
    // нужно на все вакансии всех компаний посмотреть.
    // первая мысль была сделать сразу flatMap (.flatMap(company -> company.getVacancies().stream())),
    // но по производительности это вроде не влияет, а вот читать что происходит в отдельных преобразованиях, проще
    private Set<String> vacancyNames(Collection<Company> companies) {
        return companies.stream()
            .map(Company::getVacancies)
            .flatMap(Collection::stream)
            .map(Vacancy::getTitle)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean check() {
        Vacancy vacancy1 = new Vacancy(1, "vacancy 1");
        Vacancy vacancy2 = new Vacancy(2, "vacancy 2");
        Vacancy vacancy3 = new Vacancy(3, "vacancy 1");
        Company company1 = new Company(1, "company 1", Set.of(vacancy1, vacancy2));
        Company company2 = new Company(2, "company 2", Set.of(vacancy3));
        return vacancyNames(Set.of(company1, company2)).equals(Set.of("vacancy 1", "vacancy 2"));
    }
}
