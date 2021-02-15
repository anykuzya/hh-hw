package hw.streamapi.tasks;

import hw.streamapi.common.Area;
import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */

public class Task6 implements Task {

    // после первой задачки кажется, что здесь можно применить такой же подход с мапами из id в объекты
    // сейчас по памяти это O(n+m) где n -- кол-во персон, m -- кол-во областей,
    // а по времени вроде бы тоже линия (за которую мы создаем маппинги из id в объекты)
    private static final String separator = " - ";
    private static Stream<String> getPersonAreasString(Person person, Set<Integer> personAreas,
                                                       Map<Integer, String> idsToAreaNames) {
        return personAreas.stream().map(areaId -> // это маленький стрим для каждого юзера, в нем его (этого юзера) области
            person.getFirstName() + separator + idsToAreaNames.getOrDefault(areaId, "")); // и это тоже все ещё стрим, но уже стрим со строками заданного формата для этого юзера

    }

    private Set<String> getPersonDescriptions(Collection<Person> persons,
                                              Map<Integer, Set<Integer>> personAreaIds,
                                              Collection<Area> areas) {

        var idsToAreaNames = areas.stream().collect(toMap(Area::getId, Area::getName));
        return persons.stream()
            .flatMap(person -> getPersonAreasString(person,
                                                    personAreaIds.getOrDefault(person.getId(), Collections.emptySet()),
                                                    idsToAreaNames))
            .collect(toSet());
    }

    @Override
    public boolean check() {
        List<Person> persons = List.of(
            new Person(0, "Oleg", Instant.now()),
            new Person(1, "Vasya", "Ivanov", Instant.now()),
            new Person(2, "Vasya", "Petrov", Instant.now()),
            new Person(3, "Anna", "Kuznetsova", Instant.now()) // без ареек
        );
        Map<Integer, Set<Integer>> personAreaIds = Map.of(0, Set.of(1, 2), 1, Set.of(2, 3), 2, Set.of(1));
        List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
        return getPersonDescriptions(persons, personAreaIds, areas)
            .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo", "Vasya - Moscow"));
    }
}
