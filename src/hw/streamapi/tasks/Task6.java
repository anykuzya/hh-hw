package hw.streamapi.tasks;

import hw.streamapi.common.Area;
import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

    // ок, давай начнём с тупого решения "в лоб", потом подумаю, как сделать это на стримах
    // сейчас это O(n*m) где n -- кол-во персон, m -- кол-во областей
    private Set<String> getPersonDescriptions(Collection<Person> persons,
                                              Map<Integer, Set<Integer>> personAreaIds,
                                              Collection<Area> areas) {
        HashSet<String> result = new HashSet<>();
        for (Person person : persons) {
            var relatedAreaIds = personAreaIds.get(person.getId());
            for (Area area: areas) {
                if (relatedAreaIds.contains(area.getId())) {
                    result.add(person.getFirstName() + " - " + area.getName());
                }
            }

        }
        return result;
    }

    @Override
    public boolean check() {
        List<Person> persons = List.of(
            new Person(1, "Oleg", Instant.now()),
            new Person(2, "Vasya", Instant.now())
        );
        Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
        List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
        return getPersonDescriptions(persons, personAreaIds, areas)
            .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
    }
}
