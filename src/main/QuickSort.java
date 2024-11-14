import java.util.Comparator;

/**
 * <p>Класс предназначен для быстрой сортировки (quicksort Хоара) элементов {@link CustomArrayList}.</p>
 */
public class QuickSort {

    /**
     * <p>Сортирует элементы {@code ArrayList<T>}, используя алгоритм quicksort.</p>
     * <p>Для определения правил сортировки принимает в качестве параметра {@code Comparator<T>}</p>
     *
     * @param list       список элементов
     * @param comparator объект, реализующий логику сравнения элементов
     * @param <T>        тип элементов списка
     */
    public static <T> void sort(CustomArrayList<T> list, Comparator<T> comparator) {
        int start = 0;
        int end = list.size() - 1;

        quickSort(list, start, end, comparator);
    }

    /**
     * Рекурсивно сортирует левый и правый относительно опорного элемента подмассивы списка, пока подмассив не будет
     * содержать один элемент.
     *
     * @param list       список элементов
     * @param start      индекс первого элемента подмассива
     * @param end        индекс посленего элемента подмассива
     * @param comparator объект, реализующий логику сравнения элементов
     * @param <T>        тип элементов списка
     */
    private static <T> void quickSort(CustomArrayList<T> list, int start, int end, Comparator<T> comparator) {
        if (start < end) {
            int rightSubArrayStart = split(list, start, end, comparator);

            quickSort(list, start, rightSubArrayStart - 1, comparator);
            quickSort(list, rightSubArrayStart, end, comparator);
        }
    }

    /**
     * <p>
     * Условно разделяет список на два подмассива: 1) массив элементов которые должны идти по порядку до
     * опорного элемента 2) массив элементов которые соответствуют опорному или должны итди после него.
     * </p>
     * <p>Описание:</p>
     * <p>1. Определяется опорный элемент списка / подмассива. В данном случае опорный элемент берется из середины.</p>
     * <p>2. Пока указатели, идущие с двух сторон не пройдут мимо друг друга делаем следующее:</p>
     * <p>
     * - с обоих сторон, ищем элементы. В левом подмассиве - которые больше или равны опорному. В правом подмассиве
     * - которые меньше или равны опорному. При нахождении этих элементов, указатели останавливаются на этих
     * элементах.
     * </p>
     * <p>
     * - меняем местами элементы, на которые указывают указатели (при условии, что указатели еще не прошли мимо
     * друг друга)
     * </p>
     * <p>
     * - перемещаем указатели на следующую позицию
     * </p>
     * </p>
     * <p>3. Возвращаем индекс элемента, с которого начинается правый подмассив.</p>
     *
     * @param list       список элементов
     * @param left       начало списка / подмассива
     * @param right      конец списка / подмассива
     * @param comparator объект, реализующий логику сравнения элементов
     * @param <T>        тип элементов списка
     * @return начало правого подмассива
     */
    private static <T> int split(CustomArrayList<T> list, int left, int right, Comparator<T> comparator) {
        T pivot = list.get((left + right) / 2);

        while (left <= right) {
            while (comparator.compare(list.get(left), pivot) < 0) {
                left++;
            }

            while (comparator.compare(list.get(right), pivot) > 0) {
                right--;
            }

            if (left <= right) {
                T temp = list.get(left);
                list.set(left, list.get(right));
                list.set(right, temp);
                left++;
                right--;
            }
        }

        return left;
    }

    public static <T extends Comparable<T>> void sort(CustomArrayList<T> list) {
        int start = 0;
        int end = list.size() - 1;

        quickSort(list, start, end);
    }

    private static <T extends Comparable<T>> void quickSort(CustomArrayList<T> list, int start, int end) {
        if (start < end) {
            int rightSubArrayStartIndex = split(list, start, end);

            quickSort(list, start, rightSubArrayStartIndex - 1);
            quickSort(list, rightSubArrayStartIndex, end);
        }
    }

    private static <T extends Comparable<T>> int split(CustomArrayList<T> list, int left, int right) {
        T pivot = list.get((left + right) / 2);

        while (left <= right) {
            while (list.get(left).compareTo(pivot) < 0) {
                left++;
            }

            while (list.get(right).compareTo(pivot) > 0) {
                right--;
            }

            if (left <= right) {
                T temp = list.get(left);
                list.set(left, list.get(right));
                list.set(right, temp);
                left++;
                right--;
            }
        }

        return left;
    }
}
