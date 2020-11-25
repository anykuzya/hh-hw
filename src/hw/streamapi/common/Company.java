package hw.streamapi.common;

import java.util.Set;

public class Company {
    private Integer id;
    private String title;
    private Set<Vacancy> vacancies;

    public Company(Integer id, String title, Set<Vacancy> vacancies) {
        this.id = id;
        this.title = title;
        this.vacancies = vacancies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}
