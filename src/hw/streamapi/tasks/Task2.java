package hw.streamapi.tasks;

import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

    // !!! Редактируйте этот метод !!!
    private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                        Collection<Person> persons2,
                                                        int limit) {
        return new ArrayList<>();
    }

    @Override
    public boolean check() {
        Instant time = Instant.now();
        Collection<Person> persons1 = Set.of(
            new Person(1, "Person 1", time),
            new Person(2, "Person 2", time.plusSeconds(1))
        );
        Collection<Person> persons2 = Set.of(
            new Person(3, "Person 3", time.minusSeconds(1)),
            new Person(4, "Person 4", time.plusSeconds(2))
        );
        return combineAndSortWithLimit(persons1, persons2, 3).stream()
            .map(Person::getId)
            .collect(Collectors.toList())
            .equals(List.of(3, 1, 2))
            && combineAndSortWithLimit(persons1, persons2, 5).stream()
            .map(Person::getId)
            .collect(Collectors.toList())
            .equals(List.of(3, 1, 2, 4));
    }
}
