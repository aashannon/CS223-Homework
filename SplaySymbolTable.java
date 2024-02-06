import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Stack;
import java.util.Vector;

public class SplaySymbolTable<K extends Comparable<K>, V> implements SymbolTable<K, V> {

    private class Node {
        K key;
        V val;
        Node left, right;
        Node parent;

        Node(K k, V v) {
            key = k;
            val = v;
            parent = null;
        }

        Node(K k, V v, Node p) {
            key = k;
            val = v;
            parent = p;
        }
    }

    private Node root;

    public SplaySymbolTable() {
        root = null;
    }


    private void rotateRight(Node p) {
        //Rotates the node p, by re-assigning the parent and children accordingly
        Node x = p.left;
        p.left = x.right;
        if (x.right != null) { //re-attach subtree
            x.right.parent = p;
        }

        x.right = p;
        if (p.parent != null) { //re-attach tree to grandParent
            if (x.parent.key.compareTo(p.parent.key) < 0) {
                p.parent.left = x;
            } else {
                p.parent.right = x;
            }
        }

        x.parent = p.parent;
        p.parent = x;
        if (x.parent == null){ // re-assigning root if conditions are suitable
            root = x;
        }
    }


    private void rotateLeft(Node p) {
        //Rotates the node p, by re-assigning the parent and children accordingly
        Node x = p.right;
        p.right = x.left;
        if (x.left != null) { //re-attach subtree
            x.left.parent = p;
        }

        x.left = p;
        if (p.parent != null){ //re-attach tree to grandParent
            if (x.parent.key.compareTo(p.parent.key) < 0) {
                p.parent.left = x;
            } else {
                p.parent.right = x;
            }
        }

        x.parent = p.parent;
        p.parent = x;
        if (x.parent == null){ // re-assigning root if conditions are suitable
            root = x;
        }
    }

    private void splay(Stack<Node> path) {
//        Node x = path.pop();
//        while (path.size() >= 2) {
//            Node p = path.pop();
//            Node g = path.pop();
//            if (g.left == p) {
//                if (p.left == x) { // zig-zig
//                    x = rotateRight(rotateRight(g));
//                } else { // zig-zag
//                    g.left = rotateLeft(p);
//                    x = rotateRight(g);
//                }
//            } else {
//                if (p.left == x) { // zag-zig
//                    g.right = rotateRight(p);
//                    x = rotateLeft(g);
//                } else { // zag-zag
//                    x = rotateLeft(rotateLeft(g));
//                }
//            }
//            if (path.isEmpty()) // re-attach splayed subtree
//                root = x;
//            else if (path.peek().right == g)
//                // peak returns the top stack object without removing it
//                path.peek().right = x;
//            else
//                path.peek().left = x;
//        }
//        if (!path.isEmpty())
//            root = (root.left == x) ? rotateRight(root) : rotateLeft(root);
    }

    /**
     * Splay target to root using parent references -- Part 1: Implement this
     * <p>
     * This method serves the same purpose as the above (implemented) splay,
     * but relies on nodes having parent references, rather than a separately
     * maintained stack representing the traversal path
     *
     * @param x node to splay to root
     */
    private void splay(Node x) {
        //Checks what side of the tree the node is on, and rotates it based on its
        //position in the subtree
        while ((x.parent!=null) && (x.parent.parent != null)) {
            Node parent = x.parent;
            Node grandParent = x.parent.parent;


            if (grandParent.left == parent) {
                if (parent.left == x) { //zig-zig
                    rotateRight(grandParent);
                    rotateRight(parent);
                } else { //zig-zag
                    rotateLeft(parent);
                    rotateRight(grandParent);
                }
            } else {
                if (parent.left == x) { // zag-zig
                    rotateRight(parent);
                    rotateLeft(grandParent);
                } else { // zag-zag
                    rotateLeft(grandParent);
                    rotateLeft(parent);

                }
            }
        }

        if (x != root) {
            //zig or zag when one rotation is needed to get to root
            if ((root.left == x)) {
                rotateRight(root);
            } else {
                rotateLeft(root);
            }
        }
//        return;
    }

    @Override
    public void insert(K key, V val) {
        if (root == null) {
            root = new Node(key, val);
            return;
        }
        Stack<Node> path = new Stack<Node>();
        Node x = root;
        while (true) {
            path.push(x);
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                x.key = key;
                x.val = val;
                break;
            }
            if (cmp < 0) {
                if (x.left == null) {
                    x.left = new Node(key, val, x);
                    x = x.left;
                    path.push(x);
                    break;
                }
                x = x.left;
            } else {
                if (x.right == null) {
                    x.right = new Node(key, val, x);
                    x = x.right;
                    path.push(x);
                    break;
                }
                x = x.right;
            }
        }
        splay(x);
    }

    @Override
    public V search(K key) {
        if (root == null)
            return null;
        Stack<Node> path = new Stack<Node>();
        Node x = root;
        V val = null;
        while (true) {
            path.push(x);
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                val = x.val;
                break;
            }
            if (cmp < 0) {
                if (x.left == null) break;
                x = x.left;
            } else {
                if (x.right == null) break;
                x = x.right;
            }
        }
        splay(x);
        return val;
    }

    /*
     * This is here so you can see it. You don't need to do anything with it,
     * and can safely ignore it if you prefer.
     */
    public V remove(K key) {
        if (root == null)
            return null;

        Stack<Node> path = new Stack<Node>();
        Node x = root;
        while (x != null) {
            path.push(x);
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                break; // key found
        }

        if (x == null) {
            splay(x);
            return null;
        }

        V val = x.val;

        if (x.left == null) {
            path.pop();
            if (path.isEmpty()) {
                root = x.right;
            } else {
                Node p = path.peek();
                if (p.left == x)
                    p.left = x.right;
                else
                    p.right = x.right;
            }
        } else if (x.right == null) {
            path.pop();
            if (path.isEmpty()) {
                root = x.left;
            } else {
                Node p = path.peek();
                if (p.left == x)
                    p.left = x.left;
                else
                    p.right = x.left;
            }
        } else {
            Node m = x.right;
            while (m.left != null) {
                path.push(m);
                m = m.left;
            }
            x.key = m.key;
            x.val = m.val;
            if (x.right == m)
                x.right = m.right;
            else {
                Node p = path.peek();
                p.left = m.right;
            }
        }

        splay(x);
        return val;
    }

    private void serializeAux(Node tree, Vector<String> vec) {
        if (tree == null)
            vec.addElement(null);
        else {
            vec.addElement(tree.key.toString() + ":black");
            serializeAux(tree.left, vec);
            serializeAux(tree.right, vec);
        }
    }

    public Vector<String> serialize() {
        Vector<String> vec = new Vector<String>();
        serializeAux(root, vec);
        return vec;
    }

    void printTree(String fname) {
        Vector<String> st = serialize();
        TreePrinter treePrinter = new TreePrinter(st);
        treePrinter.fontSize = 14;
        treePrinter.nodeRadius = 14;
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream ps = new PrintStream(out);
            treePrinter.printSVG(ps);
        } catch (FileNotFoundException e) {
        }
    }

    public static void main(String[] args) {
        SplaySymbolTable<Integer, Integer> table = new SplaySymbolTable<>();
        int[] keys = {1, 2, 3, 4, 9, 8, 7, 6};
        for (int i = 0; i < keys.length; i++) {
            table.insert(keys[i], keys[i]);

        }
        table.printTree("splay-tree-1.svg");
        table.search(3);
        table.search(9);
        table.search(7);
        table.printTree("splay-tree-2.svg");


        /*
            The following code provides some additional testing and uses
            the remove method, which, again, you don't need to mess with.
        !! Close the above comment to active the remaining code

        SplaySymbolTable<Integer, Integer> symtab = new SplaySymbolTable<>();
        for (int i = 0; i < 10; i++)
            symtab.insert(i, i);
        symtab.printTree("splay-insert-10.svg");

        Integer I = symtab.search(0);
        System.out.println("searched/found " + I);
        symtab.printTree("splay-search-0.svg");

        I = symtab.remove(7);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-7.svg");

        I = symtab.remove(1);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-1.svg");

        //*/
    }

}
