import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static java.lang.Math.ceil;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = "books/alices_adventures_in_wonderland.pdf";
        byte[] book= loadFile(path);
//        for (int i = 0; i < book.length; i++) {
//            System.out.print(book[i]);
//        }
        BitSet bitSet = BitSet.valueOf(book);
        System.out.println(bitSet.get(0,16));
//        byte[] bytes=bitSet.toByteArray();
//        String s=new String(bytes, StandardCharsets.ISO_8859_1);
//        System.out.println(s);
//        BitSet[] bitSetsEight=new BitSet[book.length];
//        for (int i = 0; i < book.length; i++) {
//            bitSetsEight[i]=new BitSet(8);
//            for (int j = 0; j < 8; j++) {
//                if((book[i] & (1<<j))>0){
//                    bitSetsEight[i].set(j);
//                }
//            }
//        }

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

