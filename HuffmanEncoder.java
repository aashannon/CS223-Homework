import java.lang.reflect.Array;
import java.sql.SQLOutput;

public class HuffmanEncoder {

    /**
     * You can use this node implementation for building your Huffman tree.
     * Feel free to change it, but be sure to update at least compareTo
     * so it will work correctly with the MinHeap
     */
    private class Node implements Comparable<Node> {
        public Node left, right;
        public Character c;
        public int count;

        public Node(Character c, int count) {
            this.c = c;
            this.count = count;
            left = right = null;
        }

        @Override
        public int compareTo(Node o) {
            return count - o.count;
        }

        @Override
        public String toString() {
            if (c == null) {
                return "null-" + count;
            }
            return c.toString() + "-" + count;
        }
    }

    MinHeapPriorityQueue<Node> minHeap;

    static String storeTrie;

    String[] bitTable;

    String bitEncoded;



    public HuffmanEncoder() {
        // Resets variables when called
        minHeap = new MinHeapPriorityQueue<>();

        storeTrie = "";

        bitTable = new String[256];

        bitEncoded = "";
    }

    /**
     * Huffman Part 1: Implement this
     *
     * Generates:
     *      1) Huffman tree ---------> DONE!!!!
     *      2) Encoded version of same ---------> DONE!!!!
     *      3) Character bit strings ---------> DONE!!!!
     *      4) Encoded version of string ---------> DONE!!!!
     *
     * @param s -- String to be Huffman encoded
     */
    public void encode(String s) {
        // This will take a String, process the conversions to a table, then return a Binary String
        int[] numOfTimes = new int[256];

        // Builds the Huffman Tree
        for (int i = 0; i < s.length(); i++){
            numOfTimes[s.charAt(i)]++;
        }

        for (int j = 0; j < numOfTimes.length; j++){
            if (numOfTimes[j] > 0){
                minHeap.insert(new Node((char)j, numOfTimes[j]));
            }
        }

        while (minHeap.size() > 1){
            Node parent = new Node(null, 0);
            parent.left = minHeap.delNext();
            parent.right = minHeap.delNext();
            parent.count = parent.left.count + parent.right.count;
            minHeap.insert(parent);
        }

        Node temp = minHeap.delNext();

        writeTrie(temp); // See writeTrie
        mapBits(bitTable, temp, ""); // See mapBits

        // Converts Array to Bit String
        for (int i = 0; i < s.length(); i++){
            bitEncoded += bitTable[s.charAt(i)];
        }

//        System.out.println(minHeap);
//        System.out.println(storeTrie);
//        System.out.println(bitTable);
//        System.out.println(bitEncoded);
        return;
    }

    private static void writeTrie(Node x) {
        // Writes a Trie Table
        if (x.c != null) {
            storeTrie += "1" + x.c;
            return;

        } else {
            storeTrie += "0";
            writeTrie(x.left);
            writeTrie(x.right);
        }
    }

    private static void mapBits(String[] a, Node n, String s) {
        // This converts a Trie Table into an Array for encoding
        if (n.c != null) {
            a[n.c] = s;
            return;
        } else {
            mapBits(a, n.left, s + '0');
            mapBits(a, n.right, s + '1');
        }
    }
    /**
     * Huffman Part 2: Implement this
     *
     * Your string encoding should use the actual letter rather than an
     * ASCII code. So if you are outputting a capital letter A, write "A",
     * not 65.
     *
     * @return String encoding of Huffman tree, as per slides
     */
    public String getEncodedTree() {
        // Fetches the written Trie returned by writeTrie()
        return storeTrie;
    }

    /**
     * Huffman Part 3: Implement this
     *
     * Returns an array of encoded bit strings.
     * Array should have 256 entries (one for each character value 0-255).
     * Entries that correspond to a character in the input should contain
     * the bitstring for that character.
     * Entries whose characters are not in the input should be null.
     *
     * Hint: the trick used for tracking counts in the slides would help here
     *
     * @return array of encoded bit strings
     */
    public String[] getBitStrings() {
        // Fetches the Array returned by mapBits()
        return bitTable;
    }

    /**
     * Huffman Part 4: Implement this
     *
     * Returns a string corresponding to the Huffman encoding of the
     * string provided to encode(). String should contain only '0's
     * and '1's (i.e. be binary). Other characters will be considered
     * incorrect.
     *
     * @return 'bit' encoding of overall string
     */
    public String getEncodedText() {
        // Fetches the String returned by encode()
        return bitEncoded;
    }


    public static void main(String args[]) {
        HuffmanEncoder he = new HuffmanEncoder();

        he.encode("ABRACADABRA!");
        System.out.println(he.getEncodedTree());
        System.out.println(he.getEncodedText());
        System.out.println(he.getEncodedText().length() + " <-- should be 28");
    }

}

class MinHeapPriorityQueue<I extends Comparable<I>> implements PriorityQueue<I> {
    // Java doesn't like creating arrays of generic types
    // I'm providing code that will manage this array for you
    private I[] heap;
    private int N;      // number of elements in the heap

    // Java would normally warn us about the cast below.
    // this directive keeps that from happening
    @SuppressWarnings("unchecked")
    public MinHeapPriorityQueue() {
        N = 0;
        heap = (I[]) new Comparable[1];
    }

    /**
     * Priority Queue Part 1: Implement this
     *
     * Insert an item into the priority queue, performing
     * sift/swim operations as appropriate. You *must* maintain
     * a heap array. No fair just searching for the smallest value later
     *
     * Make a call to resize() when you need to resize the array
     *
     * @param item -- Item to insert into the queue
     */
    @Override public void insert(I item) {
        // Inserts a new Node and sifts up based on min
        if (N >= heap.length) {resize();}
        int temp = N;

        for (; temp > 0 && item.compareTo(heap[(temp-1)/2]) < 0 ; temp = (temp-1)/2){
            heap[temp] = heap[(temp-1)/2];
        }

        heap[temp] = item;
        N++;
    }

    /**
     * Priority Queue Part 2: Implement this
     *
     * Extract the next item from queue, perform appropriate sift/swim
     * operations, and return the item. You *must* maintain a heap array.
     * No fair just searching for the smallest value.
     *
     * @return Item with highest priority (i.e. one with lowest value)
     */
    @Override public I delNext() {
        // Pops the Node from the Array
        if (N == 0){return null;}
        I min = heap[0];
        I last = heap[--N];
        int temp = 0;
        while (2*temp +1 <= N){
            int child = 2*temp +1;
            if (child != N && heap[child+1].compareTo(heap[child]) < 0){
                child++;
            }
            if (heap[child].compareTo(last) < 0){
                heap[temp] = heap[child];
                temp = child;
            } else break;
        }
        heap[temp] = last;
        return min;
    }

    /**
     * I assume you use N to track number of items in the heap.
     * If that's not the case, update this method.
     *
     * @return number of items in the heap
     */
    @Override public int size() {
        return N;
    }

    /**
     * Doubles the size of the heap array. Takes care of allocating
     * memory and moving everything, so you don't have to.
     * Call this from insert when you need more array space.
     */
    private void resize() {
        @SuppressWarnings("unchecked")
        I[] newHeap = (I[]) new Comparable[heap.length * 2];
        for(int i = 0; i < N; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * Returns a stringified version of the array. You shouldn't
     * need to mess with this if you use the heap array. If you do
     * something else, make sure this method still works properly
     * based on whatever your heap storage is, and the format matches exactly.
     *
     * @return a comma-separated list of array values, wrapped in brackets
     */
    @Override public String toString() {
        if(N == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < N-1; i++) {
            sb.append(heap[i] + ",");
        }
        sb.append(heap[N-1] + "]");
        return sb.toString();
    }

    public static void main(String args[]) {
        MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();

        pq.insert(5);
        pq.insert(11);
        pq.insert(8);
        pq.insert(4);
        pq.insert(3);
        pq.insert(15);
        System.out.println(pq + " <-- should be [3,4,8,11,5,15]");

        for(int i = 0; i < 6; i++) {
            System.out.println(pq.delNext());
        }
    }
}
