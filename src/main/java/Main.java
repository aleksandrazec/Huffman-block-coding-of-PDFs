import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;
import static java.nio.file.Files.readAllBytes;

public class Main {
    public static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        writer = new PrintWriter("results/results.csv", "UTF-8");
        writer.println("path, ogSize, compSize, totalTime, readTime, freqTime, constructTime, encodeTime");
        encodeBook("books/alices_adventures_in_wonderland.pdf");
        encodeBook("books/aliceintxt.txt");
        encodeBook("books/aliceindjvu.djvu");
        encodeBook("books/aliceinepub.epub");
        encodeBook("books/aliceinmobi.mobi");

        encodeBook("books/the_brothers_karamazov.pdf");
        encodeBook("books/karamazovintxt.txt");
        encodeBook("books/karamazovindjvu.djvu");
        encodeBook("books/karamazovinepub.epub");
        encodeBook("books/karamazovinmobi.mobi");
        writer.close();
    }

    public static void encodeBook(String path) throws IOException {
        long startTime = System.currentTimeMillis();
        byte[] book= loadFile(path);

        BitSet bitSet = BitSet.valueOf(book);
//        System.out.println(bitSet.get(0,16));
//        System.out.println(bitSet.size());
        long endTimeReading = System.currentTimeMillis();
        Map<BitSet, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < bitSet.length(); i+=16) {
            frequencyMap.put(bitSet.get(i,i+16), frequencyMap.getOrDefault(bitSet.get(i,i+16),0) + 1);
        }
//        System.out.println(frequencyMap.size());

        Map<BitSet, Double> probFreqMap = new HashMap<>();
        for (BitSet bitSet1 : frequencyMap.keySet()){
            probFreqMap.put(bitSet1, Double.valueOf(frequencyMap.get(bitSet1))/(bitSet.length()/16));
        }
        long endTimeFrequencyBuilding = System.currentTimeMillis();
//        System.out.println(probFreqMap.size());
        HuffmanTree huffmanTree = new HuffmanTree(probFreqMap);
        long endTimeHuffmanConstruction = System.currentTimeMillis();
        BitSet encodedBitSet = huffmanTree.Encode(bitSet);
//        System.out.println(encodedBitSet);
        byte[] encodedByte = encodedBitSet.toByteArray();
        long endTime = System.currentTimeMillis();
//        System.out.println(encodedByte[0]);
        int NumBytes = encodedByte.length;
        System.out.println("Encoding of "+ path);
        byte[] array = readAllBytes(Path.of(path));
        int NumBytes2 = array.length;
        System.out.println("Original File Bytes: " + NumBytes2);
        System.out.println("Compressed Bytes: " + NumBytes);
        System.out.println("Total time: "+(endTime-startTime));
        System.out.println("Time to read document: "+(endTimeReading-startTime));
        System.out.println("Time to calculate frequencies: "+(endTimeFrequencyBuilding-endTimeReading));
        System.out.println("Time to construct huffman: "+(endTimeHuffmanConstruction-endTimeFrequencyBuilding));
        System.out.println("Time to encode: "+(endTime-endTimeHuffmanConstruction));
        System.out.println();
        writer.println(path+","+NumBytes2+","+NumBytes+","+(endTime-startTime)+","+(endTimeReading-startTime)+","+(endTimeFrequencyBuilding-endTimeReading)+","+(endTimeHuffmanConstruction-endTimeFrequencyBuilding)+","+(endTime-endTimeHuffmanConstruction));
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

