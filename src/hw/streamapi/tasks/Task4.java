package hw.streamapi.tasks;

import hw.streamapi.common.ApiPersonDto;
import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны уже предоставлен
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 implements Task {

    private static ApiPersonDto convert(Person person) {
        ApiPersonDto dto = new ApiPersonDto();
        dto.setCreated(person.getCreatedAt().toEpochMilli());
        dto.setId(person.getId().toString());
        dto.setName(person.getFirstName());
        return dto;
    }

    // !!! Редактируйте этот метод !!!
    // тут вроде даже в комментарий нечего добавить.
    // пусть тогда будет вопрос: норм ли писать на одной строчке, когда вроде очевидно и читается легко?
    // Или вообще всегда нужно следующее действие над стримом с новой строчки писать, как ниже?
    // return persons.stream()
    //            .map(Task4::convert)
    //            .collect(toList());
    private List<ApiPersonDto> convert(List<Person> persons) {
        return persons.stream().map(Task4::convert).collect(toList());
    }

    @Override
    public boolean check() {
        Person person1 = new Person(1, "Name", Instant.now());
        Person person2 = new Person(2, "Name", Instant.now());
        return List.of(convert(person1), convert(person2))
            .equals(convert(List.of(person1, person2)));
    }
}
