public abstract class WeightedGraph {
    protected int numVertices; // private for peer classes, public for subclasses
    public WeightedGraph(int V) {numVertices = V;}
    public int V() {return numVertices;}
    public abstract int E();
    public abstract void addEdge(int v, int u, double w);
    public abstract void removeEdge(int v, int u);
    public abstract Double edgeWeight(int v, int u);
    public abstract Iterable<Integer> adj(int v);
}