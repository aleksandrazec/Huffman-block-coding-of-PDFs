import java.util.BitSet;

public class Node {
    public Double frequency;
    public BitSet value;
    public Node up;
    public Node down;
    public String encoding;

    Node(BitSet value,Double frequency) {
        this.frequency = frequency;
        this.value=value;
        up = null;
        down = null;
    }

    Node(Node up, Node down) {
        frequency = up.frequency + down.frequency;
        this.up = up;
        this.down = down;
    }

//    public BitSet getEncoding() {
//        BitSet bitSet = new BitSet(encoding.length());
//        for (int i = 0; i < encoding.length(); i++) {
//            if (encoding.charAt(i) == '1') {
//                bitSet.set(i);
//            }
//        }
//
//        return bitSet;
//    }
}
