package hw.streamapi.tasks;

import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объединить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

    // !!! Редактируйте этот метод !!!
    // вроде похоже на нормальное решение, быстрее, наверное не сделаешь
    // тут, получается, сортировка O(nlogn) где n то сумма длин списков
    // ВЕРОЯТНО, смержить две коллекции можно было бы вот так:
    //   List<Person> l = new ArrayList<>();
    //   l.addAll(persons1);
    //   l.addAll(persons2);
    //   l.stream() и дальше сортировка,
    // ИЛИ вот так:
    //   Stream.of(persons1, persons2)
    //         .flatMap(Collection::stream) //...,
    // но мне кажется, что так читаемее, а стримы из половинок мы и во флэтмапе создаем, если я всё правильно понимаю
    private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                        Collection<Person> persons2,
                                                        int limit) {

        return Stream.concat(persons1.stream(), persons2.stream())
            .sorted(Comparator.comparing(Person::getCreatedAt))
            .limit(limit).collect(toList());
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
            .collect(toList())
            .equals(List.of(3, 1, 2))
            && combineAndSortWithLimit(persons1, persons2, 5).stream()
            .map(Person::getId)
            .collect(toList())
            .equals(List.of(3, 1, 2, 4));
    }
}
