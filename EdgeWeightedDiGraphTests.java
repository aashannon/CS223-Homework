import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class EdgeWeightedDiGraphTests {
    @Test
    public void testTreeCreation() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(15);
        assertEquals(d.V(), 15);
    }

    @Test
    public void testEdgeCreation() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, 1.1);
        }
        for(int i = 0; i < 9; i++) {
            assertEquals((Double)1.1, (Double)d.edgeWeight(i, i+1));
        }
    }
    @Test
    public void testMissingEdge() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        assertNull(d.edgeWeight(1, 2));
    }

    @Test
    public void testDirectedEdges() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        d.addEdge(1, 2, 1.1);
        assertEquals((Double)1.1, (Double)d.edgeWeight(1,2));
        assertNull(d.edgeWeight(2, 1));
    }


    @Test
    public void testVariableEdgeWeights() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/10.0);
        }
        for(int i = 0; i < 9; i++) {
            assertEquals( (Double)(i/10.0), (Double)d.edgeWeight(i, i+1));
        }
    }

    @Test
    public void testNegativeEdgeWeights() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, (-i - 0.5));
        }
        for(int i = 0; i < 9; i++) {
            assertEquals((Double)(-i - 0.5), (Double)d.edgeWeight(i, i+1));
        }
    }

    @Test
    public void testReplaceEdge() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/10.0);
        }
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/2.0);
        }
        for(int i = 0; i < 9; i++) {
            assertEquals((Double)(i/2.0), (Double)d.edgeWeight(i, i+1));
        }
    }


    @Test
    public void testRemoveEdge() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/10.0);
        }
        for(int i = 0; i < 9; i+=2) {
            d.removeEdge(i, i+1);
        }
        for(int i = 0; i < 9; i+=2) {
            assertNull(d.edgeWeight(i, i+1));
        }
        for(int i = 1; i < 9; i+=2) {
            assertEquals((Double)(i/10.0), (Double)d.edgeWeight(i, i+1));
        }
    }


    @Test
    public void testEdgeCount() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i + 1, i / 10.0);
        }
        assertEquals(9, d.E());
    }

    @Test
    public void testEdgeCountAfterReplace() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/10.0);
        }
        assertEquals(d.E(), 9);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i+1, i/2.0);
        }
        assertEquals(9, d.E());
    }

    @Test
    public void testEdgeCountAfterRemove() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        for(int i = 0; i < 9; i++) {
            d.addEdge(i, i + 1, i / 10.0);
        }
        assertEquals(9, d.E());
        for(int i = 0; i < 9; i+=2) {
            d.removeEdge(i, i+1);
        }
        assertEquals(4, d.E());
    }

    @Test
    public void testNoAdjacents() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);

        Iterable<Integer> adj = d.adj(1);
        Iterator<Integer> it = adj.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testOneAdjacent() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);

        d.addEdge(8,9, 1);

        Iterable<Integer> adj = d.adj(8);
        Iterator<Integer> it = adj.iterator();
        assertTrue(it.hasNext());
        assertEquals((Integer)9, (Integer)it.next());
    }

    @Test
    public void testMultipleAdjacents() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        Boolean[] touched = new Boolean[10];

        for(int i = 1; i < 10; i++) {
            d.addEdge(0, i, 1);
        }

        Iterable<Integer> adj = d.adj(0);

        for(int v : adj) {
            touched[v] = true;
        }

        for(int i = 1; i < 10; i++) {
            assertTrue(touched[i]);
        }
    }

    @Test
    public void testMutipleVertexAdjacents() {
        EdgeWeightedDiGraph d = new EdgeWeightedDiGraph(10);
        Boolean[] touched = new Boolean[10];

        for(int i = 1; i < 10; i++) {
            d.addEdge(0, i, 1);
        }
        for(int i = 0; i < 9; i++) {
            d.addEdge(9, i, 1);
        }

        Iterable<Integer> adj = d.adj(0);
        for(int v : adj) {
            touched[v] = true;
        }
        for(int i = 1; i < 10; i++) {
            assertTrue(touched[i]);
        }

        touched = new Boolean[10];
        adj = d.adj(9);
        for(int v : adj) {
            touched[v] = true;
        }
        for(int i = 0; i < 9; i++) {
            assertTrue(touched[i]);
        }
    }
}
