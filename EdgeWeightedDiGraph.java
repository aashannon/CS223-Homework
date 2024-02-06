import java.util.HashMap;
import java.util.Vector;

public class EdgeWeightedDiGraph extends WeightedGraph {
    /**
     * You will need some variables for internal storage,
     * but the details are up to you. You may make use
     * of build in Java data structures to provide array/list
     * and map(/symbol table/dictionary) support,
     * or implement your own.
     *
     * If you implement your own classes for internal storage,
     * make sure to include them in this file. You can only
     * submit one file to Autolab, and it doesn't know how
     * to unpack things.
     * 
     * BE SURE TO READ THE ASSIGNMENT HANDOUT, AS ADDITIONAL
     * IMPLEMENTATION REQUIREMENTS ARE SPECIFIED THERE.
     *
     */

    private HashMap<Integer, Double>[] graphData;
    private int edges;

    /**
     * Part 1: Implement this
     *
     * Make sure to call super(V) before doing anything else;
     * the code won't compile without that ;)
     *
     * Do any other initialization work you need here.
     *
     * As with the example in the slides, we assume that we will
     * be given a number of vertices when we create the object,
     * and that this cannot be changed after the fact.
     *
     * @param V -- Number of vertices in the graph
     */
    public EdgeWeightedDiGraph(int V) {
	// Calls the superclass
    // Initialize Hashmap
    //Sets edges to 0
    //Iterate through and add a new HashMap
        super(V);
        graphData = new HashMap[V];
        edges = 0;

        for (int i = 0; i < V; i++) {
            graphData[i] = new HashMap<>();

        }
    }

    /**
     * Part 3: Implement this
     *
     * Do something sensible here. An instance variable you can return
     * in this method is probably a good call.
     *
     * Be sure that you deal with the rules for updating edge count correctly.
     *
     * @return -- Total number of edges in the graph
     */
    @Override public int E() {
        // Resets edges to 0
        // Adds the number of edges per vertex to edges and returns

        int edges = 0;

        for (int i = 0; i < graphData.length; i++) {
            edges += graphData[i].size();

        }
        return edges;

    }

    /**
     * Part 2: Implement this
     *
     * This method is used to add an edge or update its weight.
     *
     * Throw an IndexOutOfBoundsException if the user specifies
     * a starting or ending vertex that does not exist.
     *
     * If an edge already exists between v and u, replace its weight.
     * At no time should you end up with more than one edge between
     * any pair of vertices.
     *
     * Loops are not permitted. Throw an IllegalArgumentException
     * if the user tries to specify a loop (i.e. if v == u).
     *
     * negative edge weights are permitted.
     *
     * Replacing a weight should not affect the number of edges
     * in the graph.
     *
     * @param v -- Starting vertex of the edge
     * @param u -- Ending vertex of the edge
     * @param w -- Weight to be given to the edge
     */
    @Override public void addEdge(int v, int u, double w) {
        // Throws an Exception if it loops
        // Checks to see if edges already exist and adds them if not
        // Otherwise updates the weight

        if (v == u) {
            throw new IllegalArgumentException("Self-loops are not allowed.");

        }

        if (u < 0 || u >= V()) {
            throw new IndexOutOfBoundsException("Vertex " + u + " is out of range.");

        }

        if (!graphData[v].containsKey(u)) {
            graphData[v].put(u, w);
            edges++;

        } else {
            graphData[v].put(u, w);

        }
    }


    /**
     * Part 5: Implement this
     *
     * Remove the specified edge from the graph, assuming it exists.
     *
     * Throw an IndexOutOfBoundsException if the user specifies
     * a starting or ending vertex that does not exist.
     *
     * If the specified edge does not exist, do nothing.
     *
     * @param v -- Starting vertex of the edge
     * @param u -- Ending vertex of the edge
     */
    @Override public void removeEdge(int v, int u) {
        // Checks if the start vertex is in the graph and
        // If the edge exists, remove it
        // Otherwise throw an Exception

        if (v >= 0 && u >= 0 && v < V() && u < V()) {
            if (graphData[v].containsKey(u)) {
                graphData[v].remove(u);

            }

        } else {
            throw new IndexOutOfBoundsException("The Vertex " + v + " does not exist");

        }
    }

    /**
     * Part 4: Implement this
     *
     * Return the weight of the edge from v to u, or null if no such
     * edge exists.
     *
     * Throw an IndexOutOfBoundsException if the user specifies
     * a starting or ending vertex that does not exist.
     *
     * @param v -- Starting vertex of the edge
     * @param u -- Ending vertex of the edge
     * @return -- Weight of edge (v,u) or null if no edge exists
     */
    @Override public Double edgeWeight(int v, int u) {
        // Checks to see if the vertices are out of bounds and
        // If so, throw an Exception
        // Otherwise, returns the weight of the edge, or null if not

        if (v < 0 || v >= V()) {
            throw new IndexOutOfBoundsException("Vertex v is out of bounds.");
        }

        if (u < 0 || u >= V()) {
            throw new IndexOutOfBoundsException("Vertex u is out of bounds.");
        }

        if (graphData[v].containsKey(u)) {
            return graphData[v].get(u);
        } else {
            return null;
        }
    }

    /**
     * Part 6: Implement this
     *
     * Return an iterable object containing the vertices
     * adjacent to v.
     *
     * DO NOT include edge weights in the results.
     *
     * Throw an IndexOutOfBoundsException if the user
     * specifies a vertex that does not exist.
     *
     * Return an empty iterable object if the vertex has
     * no adjacent vertices.
     *
     * @param v -- Vertex whose adjacenies we wish to know
     * @return -- Iterable object with vertices adjacent to v
     */
    @Override public Iterable<Integer> adj(int v) {
        // Checks to see if the vertex is out of bounds,
        // If so, throws an Exception
        // Creates a Vector to store the adj vertices and
        // Iterates over all the vertices and
        // Adds the adj vertices to the Vector
        // Returns the Vector

        if (v < 0 || v >= V()) {
            throw new IndexOutOfBoundsException("The Vertex " + v + " does not exist in the graph");
        }

        Vector<Integer> adj = new Vector<>();
        for (int i = 0; i < V(); i++) {
            if (graphData[v].containsKey(i)) {
                adj.add(i);
            }
        }

        return adj;
    }
}
