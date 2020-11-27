package hw.streamapi.tasks;

import hw.streamapi.common.Person;
import hw.streamapi.common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

    private final Person fakePerson = new Person(-1, "fake first name", "fake last name", "fake middle name", Instant.now());

    // Не хотим выдавать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    public List<String> getFirstNamesWithoutFake(List<Person> persons) {
        // return persons.stream().filter(person -> person.getId() != -1).map(Person::getFirstName).collect(toList())
        // TODO: точно ли первая персона фальшивая? возможно, лучше будет вот так ^^^
        return persons.stream().skip(1).map(Person::getFirstName).collect(toList());
    }

    // получаем сет уникальных пользовательских имен
    public Set<String> getUniqueFirstNamesWithoutFake(List<Person> persons) {
        return new HashSet<>(getFirstNamesWithoutFake(persons));
    }

    // Для фронтов выдадим полное имя, а то сами не могут
    public static String convertPersonToFullName(Person person) {
        return Stream.of(person.getFirstName(), person.getMiddleName(), person.getSecondName())
            .filter(Objects::nonNull)
            .collect(joining(" "));
    }

    // словарь id персоны -> ее полное имя
    public Map<Integer, String> getPersonIdsToFullNamesMapping(Collection<Person> persons) {
        return persons.stream().collect(toMap(Person::getId, Task8::convertPersonToFullName, (a, b) -> a));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        HashSet<Person> people = new HashSet<>(persons1);
        people.retainAll(persons2);
        return !people.isEmpty();
    }

    public static long countEven(Stream<Integer> numbers) {
        return numbers.filter(n -> n % 2 == 0).count();
    }

    @Override
    public boolean check() {
        List<Person> persons = List.of(
            fakePerson,
            new Person(0, "Oleg", Instant.now()),
            new Person(1, "Anna", "Ivanova", Instant.now()),
            new Person(2, "Anna", "Kuznetsova", Instant.now()),
            new Person(3, "Anna", "Kuznetsova", Instant.now()) // это ОЧЕНЬ распространенное ИФ
        );
        if (!getFirstNamesWithoutFake(persons).equals(List.of("Oleg", "Anna", "Anna", "Anna"))) {
            System.out.println("Task8::getFirstNamesWithoutFake is broken");
            return false;
        }
        if (!getUniqueFirstNamesWithoutFake(persons).equals(Set.of("Oleg", "Anna"))) {
            System.out.println("Task8::getUniqueFirstNamesWithoutFake is broken");
            return false;
        }

        if (countEven(Stream.of(1, 1, 2, 5, 4, 6)) != 3) {
            System.out.println("Task8::countEven is broken");
            return false;
        }

        if (!persons.stream().map(Task8::convertPersonToFullName).collect(toList())
            .equals(List.of("fake first name fake middle name fake last name",
                "Oleg", "Anna Ivanova", "Anna Kuznetsova", "Anna Kuznetsova"))) {
            System.out.println("Task8::convertPersonToString is broken");
            return false;
        }

        if (!getPersonIdsToFullNamesMapping(persons).equals(
            Map.of(
                -1, "fake first name fake middle name fake last name",
                0, "Oleg",
                1 ,"Anna Ivanova",
                2,"Anna Kuznetsova",
                3, "Anna Kuznetsova"
            )
        )) {
            System.out.println("Task8::getPersonIdsToFullNamesMapping is broken");
            return false;
        }

        if (hasSamePersons(List.of(persons.get(1), persons.get(3)), List.of(persons.get(2), persons.get(4)))
            || !hasSamePersons(List.of(persons.get(1)), List.of(persons.get(1), persons.get(4)))) {
            System.out.println("Task8::hasSamePersons is broken");

            return false;
        }
        System.out.println("TGIF!");
        Boolean codeSmellsGood = null;
        boolean reviewerDrunk = true;
        return reviewerDrunk || codeSmellsGood;
    }
}
