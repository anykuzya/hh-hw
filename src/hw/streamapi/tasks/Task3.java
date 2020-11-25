package hw.streamapi.tasks;

import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 implements Task {

    // !!! Редактируйте этот метод !!!
    private List<Person> sort(Collection<Person> persons) {
        return new ArrayList<>(persons);
    }

    @Override
    public boolean check() {
        Instant time = Instant.now();
        List<Person> persons = List.of(
            new Person(1, "Oleg", "Ivanov", time),
            new Person(2, "Vasya", "Petrov", time),
            new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
            new Person(4, "Oleg", "Ivanov", time.plusSeconds(1))
        );
        List<Person> sortedPersons = List.of(
            new Person(1, "Oleg", "Ivanov", time),
            new Person(4, "Oleg", "Ivanov", time.plusSeconds(1)),
            new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
            new Person(2, "Vasya", "Petrov", time)
        );
        return sortedPersons.equals(sort(persons));
    }
}
