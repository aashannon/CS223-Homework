import java.util.ArrayList;
import java.util.Arrays;

class HuffmanParseException extends RuntimeException {
    public HuffmanParseException() {
        super();
    }
}

class HuffmanDecodeException extends RuntimeException {
    public HuffmanDecodeException() {
        super();
    }
}

public class HuffmanDecoder {

    private class Node implements Comparable<Node>{
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
            if(c == null) { return "null-" + count; }
            return c.toString() + "-" + count;
        }
    }

    private Node root;


    public HuffmanDecoder() {
        root = null;
    }

    public Boolean parseTree(String encTree) {
        ArrayList<String> encodedTree = new ArrayList<String>(Arrays.asList(encTree.split("")));
        root = parseTreeHelper(encodedTree);
        if(encodedTree.size() != 0) { throw new HuffmanParseException(); }
        return true;
    }

    private Node parseTreeHelper(ArrayList<String> encodedTree)  {
        String bit = encodedTree.remove(0);
        if(bit.compareTo("0") == 0 ) {
            // interior node, so recurse / link children
            Node newbie = new Node(null, 0);
            newbie.left = parseTreeHelper(encodedTree);
            newbie.right = parseTreeHelper(encodedTree);
            return newbie;
        }
        else if(bit.compareTo("1") == 0) {
            bit = encodedTree.remove(0);
            Node newbie = new Node(bit.charAt(0), 0);
            return newbie;
        }
        else {
            throw new HuffmanParseException();
        }
    }

    public String decode(String s) {
        StringBuilder output = new StringBuilder();
        Node curr = root;
        for(char c : s.toCharArray()) {
            if(c == '0') {
                curr = curr.left;
            }
            else if(c == '1') {
                curr = curr.right;
            }
            else {
                throw new HuffmanDecodeException();
            }
            if(curr.c != null) {
                output.append(curr.c);
                curr = root;
            }
        }

        if (curr != root) {
            throw new HuffmanDecodeException();
        }

        return output.toString();
    }

    public String[] getBitStrings() {
        String bs = "";
        String[] bitStrings = new String [256];;
        bitStringHelper(bs, root, bitStrings);
        return bitStrings;
    }

    private void bitStringHelper(String bs, Node tree, String[] bitStrings) {
        if(tree.c != null) {
            bitStrings[tree.c] = bs;
            return;
        }

        bitStringHelper(bs + "0", tree.left, bitStrings);
        bitStringHelper(bs + "1", tree.right, bitStrings);
    }


    public static void main(String args[]) {
        HuffmanEncoder he = new HuffmanEncoder();
        HuffmanDecoder hd = new HuffmanDecoder();

        he.encode("ABRACADABRA!");
        String[] encoderBitStrings = he.getBitStrings();

        hd.parseTree(he.getEncodedTree());
        String[] decoderBitStrings = hd.getBitStrings();

        for(char i = 0;  i < 256; i++) {
            if(encoderBitStrings[i] != null || decoderBitStrings[i] != null) {
                System.out.println(i + ": " + encoderBitStrings[i] + "\t" + decoderBitStrings[i]);
            }
        }

        System.out.println(hd.decode(he.getEncodedText()));
    }

}

