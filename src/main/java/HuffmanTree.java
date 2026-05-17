import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    public PriorityQueue<Node> priorityQueue;
    public Map<BitSet, Double> probFreqMap;
    public Map<BitSet, Node> encodingMap;

    public HuffmanTree(Map<BitSet, Double> probFreqMap){
        priorityQueue=new PriorityQueue<>(
                (n1,n2) -> Double.compare(n1.frequency,n2.frequency)
        );
        this.probFreqMap = probFreqMap;
        this.encodingMap = new HashMap<>();
        ConstructHuffmanTree();
    }

    private void ConstructHuffmanTree(){
        for (BitSet bitSet : probFreqMap.keySet()) {
            String key = bitSet.toString();
            Double freq = probFreqMap.get(bitSet);
            Node temp = new Node(bitSet, freq);
            encodingMap.put(bitSet, temp);
            priorityQueue.add(temp);
        }
//        for (Node node : priorityQueue) {
//            System.out.println(node.frequency);
//        }
//        for (int i = 0; i < priorityQueue.size(); i++) {
//            Node n =priorityQueue.poll();
//            System.out.println(i+"    "+n.frequency);
//        }
        while (priorityQueue.size()>1){
            Node n1 = priorityQueue.poll();
            Node n2 = priorityQueue.poll();
            Node internal=new Node(n1,n2);
            priorityQueue.add(internal);
        }
//        System.out.println(priorityQueue.peek().frequency);
        TraverseTree(priorityQueue.poll(), "");
    }
    private void TraverseTree(Node node, String currentString){
        node.encoding=currentString;
//        System.out.println(currentString);
        if(node.up!=null) {
            TraverseTree(node.up, currentString + "0");
        }
        if(node.down!=null) {
            TraverseTree(node.down, currentString + "1");
        }
    }

    public BitSet Encode(BitSet input) {
        BitSet bitSet = new BitSet();
        String temp;
        int currentLength=0;
        for (int i = 0; i < input.length(); i+=16) {
            temp = encodingMap.get(input.get(i,i+16)).encoding;
            for (int j = 0; j < temp.length(); j++) {
                if (temp.charAt(j) == '1') {
                    bitSet.set(currentLength+j);
                }
            }
            currentLength+=temp.length();
        }
        return bitSet;
    }
}
