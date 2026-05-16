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
        String path = "books/alices_adventures_in_wonderland.pdf"; // this might not work, change to absolute path if needed.
        byte[] book= loadFile(path);

        BitSet bitSet = BitSet.valueOf(book);
        System.out.println(bitSet.get(0,16));
        System.out.println(bitSet.size());

        Map<BitSet, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < bitSet.length()-16; i++) {
            frequencyMap.put(bitSet.get(i,i+16), frequencyMap.getOrDefault(bitSet.get(i,i+16),0) + 1);
        }
        System.out.println(frequencyMap.size());
//        for (BitSet name : frequencyMap.keySet()) {
//            String key = name.toString();
//            String value = frequencyMap.get(name).toString();
//            System.out.println(key + " " + value);
//        }

        Map<BitSet, Double> probFreqMap = new HashMap<>();
        for (BitSet bitSet1 : frequencyMap.keySet()){
            probFreqMap.put(bitSet1, Double.valueOf(frequencyMap.get(bitSet1))/(bitSet.length()/16));
        }
        System.out.println(probFreqMap.size());
//        float sum = 0;
//        for (BitSet name : probFreqMap.keySet()) {
//            String key = name.toString();
//            String value = probFreqMap.get(name).toString();
//            System.out.println(key + " " + value);
//            sum+= probFreqMap.get(name);
//        }
//
//        System.out.println(sum);
        HuffmanTree huffmanTree = new HuffmanTree(probFreqMap);
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

