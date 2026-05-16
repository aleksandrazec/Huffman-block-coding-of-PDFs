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
}
