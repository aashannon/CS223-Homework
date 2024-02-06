import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanEncoderTests {

    private String testString = "ABRACADABRA!";


    @Test
    public void testDecodeTree() {
        HuffmanEncoder he = new HuffmanEncoder();
        HuffmanDecoder hd = new HuffmanDecoder();

        he.encode(testString);
        hd.parseTree(he.getEncodedTree());
    }

    @Test
    public void testEncodedBitStrings() {
        HuffmanEncoder he = new HuffmanEncoder();
        HuffmanDecoder hd = new HuffmanDecoder();

        he.encode(testString);
        String[] ebs = he.getBitStrings();

        hd.parseTree(he.getEncodedTree());
        String[] dbs = hd.getBitStrings();

        assertEquals(ebs.length, dbs.length);
        for(int i = 0; i < ebs.length; i++) {
            // the decoded version is 'correct'
            assertEquals(dbs[i], ebs[i]);
        }
    }

    @Test
    public void testEncodedText() {
        HuffmanEncoder he = new HuffmanEncoder();
        HuffmanDecoder hd = new HuffmanDecoder();

        he.encode(testString);
        hd.parseTree(he.getEncodedTree());
        assertEquals(testString, hd.decode(he.getEncodedText()));
    }

    @Test
    public void testEncodedLength() {
        HuffmanEncoder he = new HuffmanEncoder();
        he.encode(testString);
        assertEquals(28, he.getEncodedText().length());
    }

}
