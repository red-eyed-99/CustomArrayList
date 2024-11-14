import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования функционала {@link CustomArrayList}
 */
class CustomArrayListTest {

    /**
     * Тестирует создание экземпляров {@code CustomArrayList}
     */
    @Nested
    @DisplayName("Create list test")
    class CreateListTest {

        /**
         * Проверяет, что при создании списка через конструктор по умолчанию, значение {@code capacity()} равно 10.
         */
        @Test
        @DisplayName("When list created with default constructor, its capacity is 10")
        void withDefaultConstructor_capacityMustBe10() {
            var customArrayList = new CustomArrayList<>();

            assertEquals(10, customArrayList.capacity(), "List capacity must be 10");
        }

        /**
         * Проверяет правильность создания списка с указанием начальной емкости.
         */
        @Test
        @DisplayName("When list created with initial capacity 17, its capacity is 17")
        void withInitialCapacity17_listCapacityMustBe17() {
            var customArrayList = new CustomArrayList<>(17);

            assertEquals(17, customArrayList.capacity(), "List capacity must be 17");
        }

        /**
         * Проверяет, выбрасывается ли {@link IllegalArgumentException} при попытке создать список с {@code capacity() <= 0}
         */
        @Test
        @DisplayName("Throws exception if list is created with initial capacity less or equals zero")
        void throwExceptionIfCapacityIsNegativeOrZero() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(0)),
                    () -> assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-1))
            );
        }
    }

    /**
     * Тестирует добавление элементов в список
     */
    @Nested
    @DisplayName("Add element to list")
    class AddElementToListTest {

        /**
         * Тестирует добавление элемента в список
         */
        @Test
        @DisplayName("Add element")
        void addElement_elementAddedToTheEndOfList() {
            var customArrayList = new CustomArrayList<>();
            var initialListSize = customArrayList.size();
            var expectedElement = new Object();

            customArrayList.add(expectedElement);
            var actualElement = customArrayList.get(initialListSize);

            assertAll(
                    () -> assertEquals(initialListSize + 1, customArrayList.size(), "List size must be incremented"),
                    () -> assertSame(expectedElement, actualElement, "Element is not added to the end of list")
            );
        }

        /**
         * Проверят выбрасывается ли {@link IndexOutOfBoundsException} при попытке добавить элемент в список
         * по отрицательному индексу или индексу больше размера списка.
         */
        @Test
        @DisplayName("Throws exception when trying to add an element at a negative or more than list size index")
        void throwsExceptionIfIndexIsNegativeOrMoreThanListSize() {
            var customArrayList = new CustomArrayList<>();

            assertAll(
                    () -> assertThrows(IndexOutOfBoundsException.class,
                            () -> customArrayList.add(customArrayList.size() + 1, new Object()), "Index out of range"),
                    () -> assertThrows(IndexOutOfBoundsException.class,
                            () -> customArrayList.add(-1, new Object()), "Index out of range")
            );
        }

        /**
         * <p>Тестирует добавление элемента в список по указанному индексу.</p>
         * <p>
         * При добавлении элемента в список по {@code index}, равному {@code size()}, элемент должен быть добавлен в
         * конец списка, при условии, что до этого индекса в списке содержатся элементы.
         * </p>
         *
         * @param index место вставки элемента
         */
        @ParameterizedTest
        @DisplayName("Add element to list at specified index")
        @ValueSource(ints = {0, 1, 3})
        void addElementByIndex_elementAddedAtTheSpecifiedIndex(int index) {
            var customArrayList = new CustomArrayList<>(3);
            customArrayList.add(new Object());
            customArrayList.add(new Object());
            customArrayList.add(new Object());

            var initialSize = customArrayList.size();
            var expectedElement = new Object();

            customArrayList.add(index, expectedElement);
            var actualElement = customArrayList.get(index);

            assertAll(
                    () -> assertEquals(initialSize + 1, customArrayList.size(), "List size must be incremented"),
                    () -> assertSame(expectedElement, actualElement, "Element is not added at specified index")
            );
        }
    }

    /**
     * Тестирует правильность установки значения элемента списка по индексу.
     */
    @Test
    @DisplayName("Set element to list at specified index")
    void setElementToList_elementSetAtTheSpecifiedIndex() {
        var customArrayList = new CustomArrayList<>(3);
        customArrayList.add(new Object());
        customArrayList.add(new Object());
        customArrayList.add(new Object());

        var expectedElement = new Object();
        customArrayList.set(1, expectedElement);

        assertEquals(expectedElement, customArrayList.get(1), "Element is not set at the specified index.");
    }

    /**
     * Тестирует удаление элементов из списка.
     */
    @Nested
    @DisplayName("Remove elements from list")
    class RemoveElementFromListTest {

        /**
         * Тестирует удаление определенного элемента из списка.
         */
        @Test
        @DisplayName("Remove element from list")
        void removeElement_elementRemovedFromList() {
            var customArrayList = new CustomArrayList<>(3);
            customArrayList.add(new Object());
            customArrayList.add(new Object());
            customArrayList.add(new Object());

            var initialSize = customArrayList.size();
            var elementToRemove = customArrayList.get(1);

            customArrayList.remove(elementToRemove);

            assertAll(
                    () -> assertNull(customArrayList.get(1), "Element is not removed from the list."),
                    () -> assertEquals(initialSize - 1, customArrayList.size(), "List size must be decremented")
            );

        }

        /**
         * Тестирует удаление элемента из списка по индексу.
         */
        @Test
        @DisplayName("Remove element from list by index")
        void removeElementAtIndex0_elementRemovedFromListAtIndex0() {
            var customArrayList = new CustomArrayList<>(3);
            customArrayList.add(new Object());
            customArrayList.add(new Object());
            customArrayList.add(new Object());

            var initialSize = customArrayList.size();

            customArrayList.remove(0);

            assertAll(
                    () -> assertNull(customArrayList.get(0), "Element is not removed from the list."),
                    () -> assertEquals(initialSize - 1, customArrayList.size(), "List size must be decremented")
            );
        }
    }

    /**
     * Тестирует правильность очищения списка.
     */
    @Test
    @DisplayName("Clear list")
    void clear_listClearedAndCapacityNotChanged() {
        var customArrayList = new CustomArrayList<>(3);
        customArrayList.add(new Object());
        customArrayList.add(new Object());
        customArrayList.add(new Object());

        var oldSize = customArrayList.size();
        var expectedSize = 0;
        var expectedCapacity = customArrayList.capacity();

        customArrayList.clear();

        assertAll(
                () -> assertEquals(expectedSize, customArrayList.size(), "After clearing list, its size must be 0"),
                () -> assertEquals(expectedCapacity, customArrayList.capacity(),
                        "After clearing list, its capacity must not be changed"),
                () -> {
                    for (int i = 0; i < oldSize; i++) {
                        assertNull(customArrayList.get(i), "Element is not removed from the list.");
                    }
                }
        );
    }

    /**
     * <p>Проверяет отсортированы ли элементы после вызова метода {@code sort()}.</p>
     * <p>
     * В коде, который проверяет правильность сортировки используется сравнение {@code < 0} потому что сравнение
     * {@code String} и {@code Character} возвращает не {@code -1}, когда левый символ по порядку идет раньше, а
     * разность между кодами этих символов.
     * </p>
     *
     * @param customArrayList список элементов
     * @param comparator      объект, реализующий логику сравнения двух объектов
     * @param <E>             тип элементов списка
     */
    @ParameterizedTest
    @DisplayName("Sort list")
    @MethodSource("CustomArrayListTest#getArgumentsForSortTest")
    <E> void sort_listSorted(CustomArrayList<E> customArrayList, Comparator<E> comparator) {
        customArrayList.sort(comparator);

        for (int i = 0; i < customArrayList.size() - 1; i++) {
            var currentElement = customArrayList.get(i);
            var nextElement = customArrayList.get(i + 1);

            assertTrue(comparator.compare(currentElement, nextElement) < 0, "Elements are not sorted");
        }
    }

    /**
     * Предоставляет данные для тестирования сортировки списка {@code sort_listSorted()}
     *
     * @return аргументы для тестирования
     */
    static Stream<Arguments> getArgumentsForSortTest() {
        var stringList = new CustomArrayList<String>(5);

        stringList.add("tom");
        stringList.add("ivan");
        stringList.add("dmitry");
        stringList.add("oleg");
        stringList.add("konstantin");

        var integerList = new CustomArrayList<Integer>(5);
        integerList.add(14);
        integerList.add(222);
        integerList.add(3);
        integerList.add(465);
        integerList.add(58);

        return Stream.of(
                Arguments.of(stringList, (Comparator<String>) String::compareTo),
                Arguments.of(integerList, (Comparator<Integer>) Integer::compare)
        );
    }
}
