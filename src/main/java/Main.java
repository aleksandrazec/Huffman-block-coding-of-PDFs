import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "D:\\InteliJ\\IdeaProjects\\HuffmanCoding\\Huffman-block-coding-of-PDF-files\\books\\alices_adventures_in_wonderland.pdf"; // this might not work, change to absolute path if needed.
        byte[] book= loadFile(path);

        BitSet bitSet = BitSet.valueOf(book);
        System.out.println(bitSet.get(0,16));
        System.out.println(bitSet.size());

        Map<BitSet, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < bitSet.length(); i+=16) {
            frequencyMap.put(bitSet.get(i,i+16), frequencyMap.getOrDefault(bitSet.get(i,i+16),0) + 1);
        }
        System.out.println(frequencyMap.size());

        Map<BitSet, Double> probFreqMap = new HashMap<>();
        for (BitSet bitSet1 : frequencyMap.keySet()){
            probFreqMap.put(bitSet1, Double.valueOf(frequencyMap.get(bitSet1))/(bitSet.length()/16));
        }
        System.out.println(probFreqMap.size());
        HuffmanTree huffmanTree = new HuffmanTree(probFreqMap);
        BitSet encodedBitSet = huffmanTree.Encode(bitSet);
//        System.out.println(encodedBitSet);
        byte[] encodedByte = encodedBitSet.toByteArray();
//        System.out.println(encodedByte[0]);
    }

    public static byte[] loadFile(String sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(sourcePath);
            return readFully(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }
    public static byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);

        }
        return baos.toByteArray();
    }

}

