package hw.streamapi.tasks;

import hw.streamapi.common.Company;
import hw.streamapi.common.Task;
import hw.streamapi.common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 implements Task {

    private Set<String> vacancyNames(Collection<Company> companies) {
        return new HashSet<>();
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
