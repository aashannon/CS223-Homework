public abstract class Graph {
    protected int numVertices; // private for peer classes, public for subclasses
    public Graph(int V) {numVertices = V;}
    public int V() {return numVertices;}
    public abstract int E();
    public abstract void addEdge(int v, int w);
    public abstract Iterable<Integer> adj(int v);
}