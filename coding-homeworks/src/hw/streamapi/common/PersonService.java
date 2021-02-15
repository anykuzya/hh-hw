package hw.streamapi.common;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonService {
    public static Set<Person> findPersons(Collection<Integer> personIds) {
        return personIds.stream()
            .sorted(Comparator.reverseOrder())
            .map(id -> new Person(id, "first " + id, "second " + id, "middle " + id, Instant.now()))
            .collect(Collectors.toSet());
    }
}
