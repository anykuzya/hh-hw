package hw.streamapi.common;

import java.time.Instant;
import java.util.Objects;

public class Person {
    private Integer id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Instant createdAt;

    public Person(Integer id, String firstName, String secondName, String middleName, Instant createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.createdAt = createdAt;
    }

    public Person(Integer id, String firstName, String secondName, Instant createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.createdAt = createdAt;
    }

    public Person(Integer id, String firstName, Instant createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
            Objects.equals(firstName, person.firstName) &&
            Objects.equals(secondName, person.secondName) &&
            Objects.equals(middleName, person.middleName) &&
            Objects.equals(createdAt, person.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, middleName, createdAt);
    }
}
