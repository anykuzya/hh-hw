package hw.streamapi.tasks;

import hw.streamapi.common.ApiPersonDto;
import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/*
Задача 5
Расширим предыдущую задачу
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 implements Task {

    private static ApiPersonDto convert(Person person, Integer areaId) {
        ApiPersonDto dto = new ApiPersonDto();
        dto.setCreated(person.getCreatedAt().toEpochMilli());
        dto.setId(person.getId().toString());
        dto.setName(person.getFirstName());
        dto.setAreaId(areaId);
        return dto;
    }

    // !!! Редактируйте этот метод !!!
    // похоже, мы больше не можем воспользоваться ссылкой на convert как маппером,
    // с двумя аргументами надо явно вызывать его
    private List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
        return persons.stream()
            .map(person -> convert(person, personAreaIds.get(person.getId())))
            .collect(toList());
    }

    @Override
    public boolean check() {
        Person person1 = new Person(1, "Name", Instant.now());
        Person person2 = new Person(2, "Name", Instant.now());
        Map<Integer, Integer> personAreaIds = Map.of(1, 1, 2, 2);
        return List.of(convert(person1, 1), convert(person2, 2))
            .equals(convert(List.of(person1, person2), personAreaIds));
    }
}
