import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopoSort {

    /**
     * Part 1: Implement this
     *
     * Function to perform topological sort on a given Graph object.
     *
     * If a topological ordering exists, return it.
     * Other return null if:
     *      passed a null
     *      passed a graph with < 1 vertex
     *      passed a graph containing cycles
     *
     * Note that undirected graphs won't have topological orderings,
     * but you do not need to explicitly check for that.
     *
     * @param g A graph to be sorted, or null
     * @return  An iterable object with the ordering, or null
     */

    public static Iterable<Integer> sort(Graph g) {
        // Check to see if graph is null or has 1 or less vertex, otherwise
        // Create a new Array, Queue, and List to hold vertices and edges
        // Determine the in-bound edge count per vertex
        // Determine Vertices with not in-bound edges
        // Perform Depth First Search and then sort for order

        if (g == null || g.V() < 1){
            return null;
        }

        int[] inbound = new int[g.V()];
        Queue<Integer> queueList = new LinkedList<>();
        List<Integer> order = new ArrayList<>();

        for (int i = 0; i < g.V(); i++) {
            for (int j: g.adj(i)) {
                inbound[j]++;
            }
        }

        for (int k = 0; k < g.V(); k++){
            if (inbound[k] == 0){
                queueList.add(k);
            }
        }

        while (!queueList.isEmpty()){
            int v = queueList.remove();
            order.add(v);

            for (int u: g.adj(v)){
                inbound[u]--;
                if (inbound[u] == 0){
                    queueList.add(u);
                }
            }
        }

        if (order.size() == g.V()){
            return order;
        } else {
            return null;
        }
    }
}
