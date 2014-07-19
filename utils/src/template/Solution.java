import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Solution {

    public static void main(String[] args) throws Exception {
        solve(System.in, System.out);
    }

    public static void solve(InputStream inputStream, PrintStream outputStream) throws Exception {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(inputStream)));
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream));



        out.flush();
    }

    private static double readDouble(StreamTokenizer in) throws Exception { in.nextToken(); return in.nval; }
    private static float readFloat(StreamTokenizer in) throws Exception { return (float) readDouble(in); }
    private static int readInteger(StreamTokenizer in) throws Exception { return (int) readDouble(in); }
    private static long readLong(StreamTokenizer in) throws Exception { return (long) readDouble(in); }
    private static byte readByte(StreamTokenizer in) throws Exception { return (byte) readDouble(in); }

}