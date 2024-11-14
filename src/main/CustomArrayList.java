import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Класс, является собственной, частичной реализацией {@link java.util.ArrayList}.
 *
 * @param <E> тип элементов списка
 */
public class CustomArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final double INCREASE_FACTOR = 1.5;

    private Object[] elements;

    private int size;

    /**
     * Создает новый список со стандартным размером ({@value DEFAULT_CAPACITY} элементов)
     */
    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Создает новый список указанного размера
     *
     * @param capacity размер списка
     * @throws IllegalArgumentException если значение {@code capacity} меньше или равно нулю
     */
    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }

        elements = new Object[capacity];
    }

    /**
     * Создает новый список на основе другого списка типа {@link List}
     *
     * @param list список с элементами
     */
    public CustomArrayList(List<E> list) {
        this(list.size());

        for (E element : list) {
            add(element);
        }
    }

    /**
     * <p>Добавляет элемент в конец списка.</p>
     * <p>При нехватке места происходит увеличение размера списка в {@value INCREASE_FACTOR} раза</p>
     *
     * @param element элемент для добавления в список
     */
    public void add(E element) {
        if (size == capacity()) {
            increase();
        }

        elements[size++] = element;
    }

    /**
     * <p>Добавляет элемент в список по указанному индексу.</p>
     * <p>При нехватке места происходит увеличение размера списка в {@value INCREASE_FACTOR} раза.</p>
     * <p>Если {@code index} вставки элемента равен {@code size()}, то список расширяется и {@code element} вставляется
     * в конец списка.</p>
     *
     * @param index   место вставки элемента
     * @param element элемент для добавления в список
     * @throws IndexOutOfBoundsException если значение {@code index} меньше 0 или больше {@code size()}
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", but size is: " + size);
        }

        if (index == size) {
            add(element);
            return;
        }

        if (size == capacity()) {
            increase();
        }

        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Увеличивает размер списка в {@value INCREASE_FACTOR} раза
     */
    private void increase() {
        elements = Arrays.copyOf(elements, (int) (size * INCREASE_FACTOR));
    }

    /**
     * Возвращает элемент списка по указанному индексу
     *
     * @param index индекс возвращаемого элемента
     * @return элемент списка по указанному индексу
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elements[index];
    }

    /**
     * Устанавливает значение элемента по указанному индексу
     *
     * @param index   индекс элемента, значение которого будет изменено
     * @param element элемент, который будет установлен по указанному {@code index}
     * @throws IndexOutOfBoundsException если значение {@code index} меньше 0 или больше {@code size()}
     */
    public void set(int index, E element) {
        elements[index] = element;
    }

    /**
     * Удаляет элемент из списка при наличии
     *
     * @param element элемент, который нужно удалить
     */
    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                elements[i] = null;
                size--;
                return;
            }
        }
    }

    /**
     * Удаляет элемент из списка по укзаанному индексу
     *
     * @param index индекс элемента, который нужно удалить
     * @throws IndexOutOfBoundsException если значение {@code index} меньше 0 или больше {@code size()}
     */
    public void remove(int index) {
        elements[index] = null;
        size--;
    }

    /**
     * <p>Сортирует элементы в списке в зависимости от реализации {@code comparator}</p>
     * <p>Элементы списка со значением {@code null} сдвигаются в конец списка</p>
     *
     * @param comparator объект, реализующий логику сравнения объектов
     */
    public void sort(Comparator<E> comparator) {
        //Arrays.sort((E[]) elements, Comparator.nullsLast(comparator));
        QuickSort.sort(this, comparator);
    }

    /**
     * Возвращает количество добавленных элементов в список
     *
     * @return размер списка (количество добавленных элементов)
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает текущую вместимость списка
     *
     * @return вместимость списка
     */
    public int capacity() {
        return elements.length;
    }

    /**
     * Полностью очищает список, не изменяя его {@code capacity}
     */
    public void clear() {
        elements = new Object[capacity()];
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return Objects.deepEquals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    /**
     * Переопределяет строковое представление объекта {@link CustomArrayList}
     *
     * @return строкое представление объекта {@code main.CustomArrayList}
     */
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
