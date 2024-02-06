public interface PriorityQueue <I extends Comparable<I>> {
    void insert(I key);
    I delNext();
    int size();
}
