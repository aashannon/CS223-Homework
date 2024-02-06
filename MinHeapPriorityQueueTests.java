import static org.junit.Assert.*;
import java.util.Arrays; 
import java.util.Random;

import org.junit.Test;

public class MinHeapPriorityQueueTests {

    @Test
    public void testSingleInsert() {
      MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
      pq.insert(10);
      assertEquals((Integer)10, pq.delNext());
    }

    @Test
    public void testInorderInsert() {
    	MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
    	int[] values = {0, 1, 2, 3, 4, 5};
    	for(int i = 0; i < values.length; i++) {
    		pq.insert(values[i]);
    	}
    	for(int i = 0; i < values.length; i++) {
    		assertEquals((Integer)values[i], pq.delNext());
    	}
    }

    @Test
    public void testReverseInsert() {
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
    	for(int i = 5; i >= 0; i--) {
    		pq.insert(i);
    	}
		assertEquals((Integer)0, pq.delNext());
		assertEquals((Integer)1, pq.delNext());
		assertEquals((Integer)2, pq.delNext());
		assertEquals((Integer)3, pq.delNext());
		assertEquals((Integer)4, pq.delNext());
		assertEquals((Integer)5, pq.delNext());
    }

    @Test
    public void testRandomInsert() {
    	MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
    	int N = 10;
    	int [] values = new int[N];
    	Random RNG = new Random(1234);
    	for(int i = 0; i < N; i++) {
    		values[i] = RNG.nextInt();
    		pq.insert(values[i]);
    	}
    	Arrays.sort(values);
		System.out.println(" pq: " + pq);
		System.out.println("arr: " + Arrays.toString(values));
    	for(int i = 0; i < N; i++) {
    		assertEquals((Integer)values[i], pq.delNext());
    	}
    }

    @Test
	public void testDupInsert() {
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
		int N = 5;
		int val = 42;
		for(int i = 0; i < 5; i++) {
			pq.insert(val);
		}
		for(int i = 0; i < 5; i++) {
			assertEquals((Integer)val, pq.delNext());
		}
	}

    @Test
	public void testAlphaInsert()  {
    	MinHeapPriorityQueue<String> pq = new MinHeapPriorityQueue<>();
		String[] values = {"H", "e", "l", "l", "o"};
		for(int i = 0; i < values.length; i++) {
			pq.insert(values[i]);
		}
		Arrays.sort(values);
		for(int i = 0; i < values.length; i++) {
			assertEquals(values[i], pq.delNext());
		}
	}

	@Test
	public void testArray() {
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
		pq.insert(5);
		pq.insert(11);
		pq.insert(8);
		pq.insert(4);
		pq.insert(3);
		pq.insert(15);
		assertEquals("[3,4,8,11,5,15]", pq.toString());

	}

	// it's possible to get into trouble right before or right
	// after a resize. these tests check for that.

	@Test
	public void test2NArraySize() {
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
		for(int i = 7; i >= 0; i--) {
			pq.insert(i);
		}
		assertEquals((Integer)0, pq.delNext());
		assertEquals((Integer)1, pq.delNext());
		assertEquals((Integer)2, pq.delNext());
		assertEquals((Integer)3, pq.delNext());
		assertEquals((Integer)4, pq.delNext());
		assertEquals((Integer)5, pq.delNext());
		assertEquals((Integer)6, pq.delNext());
		assertEquals((Integer)7, pq.delNext());
	}

	@Test
	public void test2N1ArraySize() {
		MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();
		for(int i = 6; i >= 0; i--) {
			pq.insert(i);
		}
		assertEquals((Integer)0, pq.delNext());
		assertEquals((Integer)1, pq.delNext());
		assertEquals((Integer)2, pq.delNext());
		assertEquals((Integer)3, pq.delNext());
		assertEquals((Integer)4, pq.delNext());
		assertEquals((Integer)5, pq.delNext());
		assertEquals((Integer)6, pq.delNext());
	}
}
