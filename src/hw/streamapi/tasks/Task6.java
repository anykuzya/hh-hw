package hw.streamapi.tasks;

import hw.streamapi.common.Area;
import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    // но пока это нечитаемо, а еще скорее всего сломается на тёзках
    private Set<String> getPersonDescriptions(Collection<Person> persons,
                                              Map<Integer, Set<Integer>> personAreaIds,
                                              Collection<Area> areas) {

        Map<Integer, Person> idsToPersons = persons.stream()
            .collect(Collectors.toMap(Person::getId, Function.identity()));
        Map<Integer, Area> idsToAreas = areas.stream()
            .collect(Collectors.toMap(Area::getId, Function.identity()));
        var namesToAreas = personAreaIds.keySet().stream()
            .collect(Collectors.toMap(id -> idsToPersons.get(id).getFirstName(), // duplicateKeyException for second Vasya            // это мы зафиксировали, что в этой мапе будут имена людей
                id -> personAreaIds.get(id).stream().map(area_id -> idsToAreas.get(area_id).getName()).collect(Collectors.toSet()))); // это мы коллекцию областей каждого юзера получили в виде строк (и прочитать это сейчас невозможно)
        return namesToAreas.keySet().stream().flatMap(name -> namesToAreas.get(name).stream().map(area -> name + " - " + area)).collect(Collectors.toSet());
    }

    @Override
    public boolean check() {
        List<Person> persons = List.of(
            new Person(0, "Oleg", Instant.now()),
            new Person(1, "Vasya", Instant.now()),
            new Person(2, "Vasya", Instant.now())
        );
        Map<Integer, Set<Integer>> personAreaIds = Map.of(0, Set.of(1, 2), 1, Set.of(2, 3), 2, Set.of(1));
        List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
        return getPersonDescriptions(persons, personAreaIds, areas)
            .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo", "Vasya - Moscow"));
    }
}
