package hw.streamapi.tasks;

import hw.streamapi.common.Person;
import hw.streamapi.common.PersonService;
import hw.streamapi.common.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

    // !!! Редактируйте этот метод !!!
    // это квадрат. так неинтересно. надо подумать еще.
    private List<Person> findOrderedPersons(List<Integer> personIds) {
        Set<Person> persons = PersonService.findPersons(personIds);
        List<Person> result = new ArrayList<>();
        for (Integer id: personIds) {
            for (Person person: persons) {
                if (person.getId().equals(id)) {
                    result.add(person);
                }
            }
        }
        return result;
    }

    @Override
    public boolean check() {
        List<Integer> ids = List.of(1, 2, 3);

        return findOrderedPersons(ids).stream()
            .map(Person::getId)
            .collect(Collectors.toList())
            .equals(ids);
    }

}
