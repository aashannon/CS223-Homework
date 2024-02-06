import java.util.Vector;

public class AdjMatrixDiGraph extends Graph {
    protected boolean [][] adjMatrix;
    protected int numEdges;

    public AdjMatrixDiGraph(int V) {
        super(V);   // invoke Graph's constructor, setting numVertices
        numEdges = 0; // no edges yet
        adjMatrix = new boolean[V][V]; // all false to start
    }

    /*
     * This is a copy constructor. It clones an existing instance of
     * an AdjMatrixDiGraph, which is handy if you want to make backup copies
     */
    public AdjMatrixDiGraph(AdjMatrixDiGraph G) {
        super(G.V());   // invoke Graph's constructor, setting numVertices
        numEdges = G.E(); // no edges yet
        adjMatrix = new boolean[numVertices][numVertices];
        // and now, clone the adjMatrix from G
        for(int i = 0; i < numVertices; i++) {
            for(int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = G.adjMatrix[i][j];
            }
        }

    }

    @Override public int E() { return numEdges; }

    @Override public void addEdge(int v, int w) {
        if(!adjMatrix[v][w]) {
            adjMatrix[v][w] = true;
            numEdges++;
        }
    }

    @Override public Iterable<Integer> adj(int v) {
        Vector<Integer> adj = new Vector<>();
        for(int w = 0; w < numVertices; w++) {
            if(adjMatrix[v][w]) {
                adj.add(w);
            }
        }
        return adj;
    }


}