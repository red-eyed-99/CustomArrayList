import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс предназначен для тестирования сортировки {@link QuickSort}
 */
class QuickSortTest {

    /**
     * Метод тестирует сортировку {@code CustomArrayList}, заполненного целыми числами.
     */
    @Test
    @DisplayName("Sort custom array list with integers")
    void sortIntegerCustomArrayList_listSorted() {
        var actualList = new CustomArrayList<>(Arrays.asList(2, 2, 1, 10, 8, 5, 2, 7, 9, 7));
        var expectedList = new CustomArrayList<>(Arrays.asList(1, 2, 2, 2, 5, 7, 7, 8, 9, 10));

        QuickSort.sort(actualList);

        assertEquals(expectedList, actualList, "Elements are not sorted");
    }

    /**
     * Метод тестирует сортировку {@code CustomArrayList}, заполненного строками.
     */
    @Test
    @DisplayName("Sort custom array list with strings")
    void sortStringCustomArrayList_listSorted() {
        var actualList = new CustomArrayList<>(Arrays.asList("hello", "world", "snow", "apple", "tom", "cheese"));
        var expectedList = new CustomArrayList<>(Arrays.asList("apple", "cheese", "hello", "snow", "tom", "world"));

        QuickSort.sort(actualList);

        assertEquals(expectedList, actualList, "Elements are not sorted");
    }

    /**
     * Метод тестирует сортировку {@code CustomArrayList}, заполненного строками с использованием {@code Comparator}
     * по длине строки.
     */
    @Test
    @DisplayName("Sort custom array list with strings using comparator by string length")
    void sortStringWithComparatorCustomArrayList_listSorted() {
        var actualList = new CustomArrayList<>(Arrays.asList("hello", "world", "snow", "apple", "tom", "cheese"));
        var expectedList = new CustomArrayList<>(Arrays.asList("tom", "snow", "apple", "hello", "world", "cheese"));

        QuickSort.sort(actualList, (o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            }
            if (o1.length() < o2.length()) {
                return -1;
            }
            return o1.compareTo(o2);
        });

        assertEquals(expectedList, actualList, "Elements are not sorted");
    }

    /**
     * Метод тестирует сортировку {@code CustomArrayList}, заполненного объектами типа {@code Person}.
     */
    @Test
    @DisplayName("Sort custom array list with person objects")
    void sortPersonByAgeCustomArrayList_listSorted() {
        var bob = new Person("bob", 21);
        var john = new Person("john", 25);
        var tom = new Person("tom", 50);
        var jane = new Person("jane", 30);

        var actualList = new CustomArrayList<>(Arrays.asList(john, tom, bob, jane));
        var expectedList = new CustomArrayList<>(Arrays.asList(bob, john, jane, tom));

        QuickSort.sort(actualList);

        assertEquals(expectedList, actualList, "Elements are not sorted");
    }
}
