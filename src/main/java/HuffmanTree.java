import java.util.BitSet;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    public PriorityQueue<Node> priorityQueue;
    public Map<BitSet, Double> probFreqMap;

    public HuffmanTree(Map<BitSet, Double> probFreqMap){
        priorityQueue=new PriorityQueue<>(
                (n1,n2) -> Double.compare(n1.frequency,n2.frequency)
        );
        this.probFreqMap = probFreqMap;
        ConstructHuffmanTree();
    }

    private void ConstructHuffmanTree(){
        for (BitSet bitSet : probFreqMap.keySet()) {
            String key = bitSet.toString();
            Double freq = probFreqMap.get(bitSet);
            Node temp = new Node(bitSet, freq);
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
            priorityQueue.poll();

        }
    }

}
