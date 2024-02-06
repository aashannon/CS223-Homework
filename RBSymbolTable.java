import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Vector;


public class RBSymbolTable<K extends Comparable<K>, V>
        implements SymbolTable<K, V> {

    private enum Color {BLACK, RED};
    /*
     * This is a standard BST node class. You'll need to
     * modify it to track color somehow
     */
    private class Node {
        public K key;
        public V val;

        public Color color;

        public Node left, right;


        // go ahead and modify this constructor's parameters if you like
        public Node(K k, V v, Color c) {
            key = k;
            val = v;
            color = c;
            left = right = null;
        }
    }

    // this is the root of our tree
    private Node root;

    /*
     *  default constructor - this is invoked when we
     *  create a new RBSymbolTable. All it needs to do
     *  is make sure the root node is empty
     */
    public RBSymbolTable() {
        root = null;
    }

    int n = 0;

//    private void splitCheck (Node check) {
//        check.left.color = Color.BLACK;
//        check.right.color = Color.BLACK;
//    }
    private void setRoot(K key, V val){
        root = new Node(key, val, Color.BLACK);
    }

    private boolean red(Node n) { // is node red? (null nodes are black)
        return (n == null) ? false : (n.color == Color.RED);
    }
    /**
     * Insert designated key and value into data structure -- Part 1: Implement this
     * <p>
     * This is implementing the insert method SymbolTable
     * requires us to provide. The @override is not strictly
     * required, but putting it here means if we forgot
     * to say "implements SymbolTable<K,V>" when we defined
     * the class, Java would complain at us. Thus, it's
     * a useful sanity check.
     * <p>
     * You need to end up with a top-down Red-Black insert here, for it to pass the tests.
     * That means that you'll always split four-nodes before entering them, as you walk
     * down the tree looking for the correct insertion point. BE CAREFUL! Some online
     * resources discussion bottom-up trees, which will produce different results and thus
     * not pass the tests.
     * <p>
     * Performing the entire insert iteratively, rather than recursively,
     * will make your life a lot easier.
     *
     * @param key Key used to latter retrieve the data
     *            Must not be null
     *            Duplicate keys replace old data
     * @param val Value associated with the key
     *            May be null
     */
    @Override public void insert(K key, V val) {
        // This will insert a new node based on the giving key and value
        // This will preset the 4 cursors to work with 'parent' as the cursor that iterates the tree

        Node greatGrandParent = null;
        Node grandParent =  null;
        Node parent = root;
        Node child = null;

        if(root == null){
            // Base case of an empty tree, sets new root
            setRoot(key, val);
            return;
        }

        if (key.compareTo(root.key) == 0){
            //Base case if insertion already exists, otherwise will create a new node of root as RED node
            root.key = key;
            root.val = val;
            return;

        } else if ((key.compareTo(root.key) < 0) && (root.left == null)) {
            root.left = new Node(key, val, Color.RED);
            return;

        } else if ((key.compareTo(root.key) > 0) && (root.right == null)) {
            root.right = new Node(key, val, Color.RED);
            return;
        }

        if ((red(root.left)) && (red(root.right))){
            //Checks if both children of root are red and recolors before new insertions
            root.left.color = Color.BLACK;
            root.right.color = Color.BLACK;
        }

        greatGrandParent = null;
        grandParent = root;

        if (key.compareTo(root.key) < 0){
            // sets children on root
            parent = root.left;
        }else{
            parent = root.right;
        }

        if ((red(parent.left) && red(parent.right))){
            //Condition to move middle Node up the tree and recolors when it is a 4 node
            parent.left.color = Color.BLACK;
            parent.right.color = Color.BLACK;
            parent.color = Color.RED;
        }

        while (child == null){
            //This will travers the cursors down the tree until the child condition is filled
            //As with the root, a base case is set to check for repeats
            //Otherwise will check parent key and assign the new child to parent right or left
            //If a child is available after first pass through loop

            if (key == parent.key){
                parent.key = key;
                parent.val = val;
                return;
            }

            if ((key.compareTo(parent.key) < 0) && (parent.left != null)) {
                child = parent.left;
            } else if ((key.compareTo(parent.key) > 0) && (parent.right != null)) {
                child = parent.right;
            }



            if (child == null) {
                //If there is no node to insert, this will create a new node with the given values
                //Then assign based on key comparison

                child = new Node(key, val, Color.RED);

                if (key.compareTo(parent.key) < 0){
                    parent.left = child;
                } else if (key.compareTo(parent.key) > 0) {
                    parent.right = child;
                }

                if (parent.color == Color.RED) {
                    //Check color of parent to satisfy LL, RL, RR, LR double RED conditions
                    //Rotate based on location parent to grandParent node
                    if (parent == grandParent.left) {
                        if (child == parent.left) {
                            grandParent = rotateRight(grandParent);
                            grandParent.color = Color.BLACK;
                            parent.right.color = Color.RED;
                        } else {
                            grandParent.left = rotateLeft(parent);
                            grandParent = rotateRight(grandParent);
                            grandParent.color = Color.BLACK;
                            grandParent.right.color = Color.RED;
                        }

                    } else {
                        if (child == parent.right) {
                            grandParent = rotateLeft(grandParent);
                            grandParent.color = Color.BLACK;
                            parent.left.color = Color.RED;
                        } else {
                            grandParent.right = rotateRight(parent);
                            grandParent = rotateLeft(grandParent);
                            grandParent.color = Color.BLACK;
                            grandParent.left.color = Color.RED;
                        }
                    }
                    if (greatGrandParent == null){
                        //Sets the new greatGrantParent up if tree needs to grow
                        //Then reattaches rotated and altered subtrees.
                        root = grandParent;

                    } else if (greatGrandParent.key.compareTo(grandParent.key) > 0) {
                        greatGrandParent.left = grandParent;

                    } else {
                        greatGrandParent.right = grandParent;
                    }

                    return;
                }
            }

            //Sets new cursor nodes to next node in tree until new node is added
            greatGrandParent = grandParent;
            grandParent = parent;
            parent = child;
            child = null;
        }

        System.out.println(key);
        return;
    }

    /**
     * Right Rotation
     * <p>
     * Performs a right rotation of the designated node.
     * This is pull directly from the slides, so you don't have to.
     *
     * @param tree node to be rotated
     *             tree must exist, as must tree.left
     * @return root of tree post-rotation (should be the original tree.left)
     */
    private Node rotateRight(Node tree) {
        Node root = tree.left;
        tree.left = root.right;
        root.right = tree;
        return root;
    }

    /**
     * Left Rotation
     * <p>
     * Performs a left rotation of the designated node
     * This is pull directly from the slides, so you don't have to.
     *
     * @param tree node to be rotated
     *             tree must exist, as must tree.right
     * @return root of tree post-rotation (should be the original tree.right)
     */
    private Node rotateLeft(Node tree) {
        Node root = tree.right;
        tree.right = root.left;
        root.left = tree;
        return root;
    }

    /**
     * Retrieve the value associated with the given key, if present
     * <p>
     * Implementation of the search method in the interface.
     * Again, we just call a recursive helper.
     *
     * @param key key whose value we'd like to retrieve
     *            may not be null
     * @return value associated with key (may be null) or null if key is absent
     */
    @Override
    public V search(K key) {
        return searchHelper(root, key);
    }

    /**
     * Internal helper for the search method
     * <p>
     * Recursively search tree rooted at tree for given key
     * Returns the associated value, if it exists, or null
     * if the key is not found.
     * <p>
     * Note that as a result of the way this works, we can't
     * have a key whose value is null
     *
     * @param tree root node of the (sub-)tree to search
     * @param key  see notes for search()
     * @return value associated with key (may be null), or null if key is absent
     */
    private V searchHelper(Node tree, K key) {
        if (tree == null) {
            // tree is empty or no more tree, so key isn't here
            return null;
        }
        int cmp = key.compareTo(tree.key);
        if (cmp == 0) {
            // found the key, return its value
            return tree.val;
        }
        /*
         * the ? : is called the ternary operator. You provide
         * a logical expression before the ?, the value to give back
         * if it's true between ? and :, and the value for false
         * after :. This is compact, but can be hard to read.
         *
         * An equivalent if/then would look something like this:
         * 		V ret;
         * 		if(cmp < 0) {
         * 			ret = searchHelper(tree.left, key);
         * 		}
         *		else {
         *			ret = searchHelper(tree.right, key);
         *		}
         *		return ret;
         */
        return (cmp < 0) ? searchHelper(tree.left, key) : searchHelper(tree.right, key);
    }

    /**
     * Serialize tree into a vector for use with support functionality
     * <p>
     * This method is not part of the symbol table interface
     * Instead, it lets us convert the RBSymbolTable into a
     * form that's easy to hand off for display or testing.
     * This works by traversing the tree (with a helper) and
     * shoving information about its nodes into a vector we
     * can pass off later.
     *
     * @return Vector of strings containing key and node color (always black)
     * nodes are presented in preorder traversal order
     * null objects are used to indicate an absent child
     */
    public Vector<String> serialize() {
        Vector<String> vec = new Vector<>();
        serializeHelper(root, vec);
        return vec;
    }

    /**
     * Recursive helper for serialization
     * <p>
     * Perform a (recursive) pre-order traversal and
     * store node information into a provided vector of strings.
     * Note that we add ":black" to the end of the node's key.
     * This is because the TreePrinter will happily work on
     * Red-Black trees, where color is significant, so we fill
     * in a default.
     *
     * @param tree root node of (sub-)tree to serialize
     * @param vec  vector object into which to serialize
     */
    private void serializeHelper(Node tree, Vector<String> vec) {
        String nodeColor = (red(tree)) ? ":red" : ":black";
        if (tree == null)
            vec.addElement(null);
        else {
            // TODO: set nodeColor to either ":black" or ":red" based on tree
            vec.addElement(tree.key.toString() + nodeColor);
            serializeHelper(tree.left, vec);
            serializeHelper(tree.right, vec);
        }
    }


    /**
     * Use TreePrinter class to generate picture of the tree
     * <p>
     * This interacts with the TreePrinter class for us.
     * First, we generate a vector of strings containing the
     * serialized tree. Once that's done, use it to create
     * a TreePrinter object, and then open a file and have
     * the TreePrinter throw a picture of the tree into the file.
     *
     * @param fname name of file to output.
     *              should end in .svg
     */
    private void printTree(String fname) {
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


    /*
     * This main provides a relatively simple test harness for
     * the RBSymbolTable, by randomly adding some nodes and
     * then invoking the TreePrinter to get a picture of the tree.
     */
    public static void main(String args[]) {
        /*
         * Normally you'd probably want to use a SymbolTable on
         * the left here, but because we want to use .printTree(),
         * which is part of RBSymbolTable but not SymbolTable, it's
         * easier to just call it a RBSymbolTable to begin with, rather
         * than doing a cast.
         */
        RBSymbolTable<Integer, Integer> symtab = new RBSymbolTable<>();

        /*
         * It's probably a good idea to write your own test cases, doing
         * inserts and searches, and printing the tree.
         */
        symtab.insert(5, 5);
        symtab.insert(4, 4);
        symtab.insert(6, 6);

        symtab.printTree("simple.svg");

        /*
         * This will insert 100 nodes with random values for us.
         * We'll always get the same random sequence. If you want
         * a different one, either remove the 1234, or replace it
         * with something else.
         */
        symtab = new RBSymbolTable<>();
        Random RNG = new Random(1234);
        for (int i = 0; i < 100; i++) {
            int r = (int) (RNG.nextDouble() * 100);
            symtab.insert(r, r);
        }

        symtab.printTree("randomtree.svg");
    }

}
