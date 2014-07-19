public class SolutionTemplate {

    private static final String TEMPLATE =

            "import java.io.BufferedReader;\n" +
            "import java.io.InputStream;\n" +
            "import java.io.InputStreamReader;\n" +
            "import java.io.OutputStreamWriter;\n" +
            "import java.io.PrintStream;\n" +
            "import java.io.PrintWriter;\n" +
            "import java.io.StreamTokenizer;\n" +
            "\n" +
            "public class Solution {\n" +
            "\n" +
            "    public static void main(String[] args) throws Exception {\n" +
            "        solve(System.in, System.out);\n" +
            "    }\n" +
            "\n" +
            "    public static void solve(InputStream inputStream, PrintStream outputStream) throws Exception {\n" +
            "        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(inputStream)));\n" +
            "//\t\tBufferedReader in = new BufferedReader(new InputStreamReader(inputStream));\n" +
            "        PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream));\n" +
            "\n" +
            "        \n" +
            "\n" +
            "        out.flush();\n" +
            "    }\n" +
            "\n" +
            "    private static double readDouble(StreamTokenizer in) throws Exception { in.nextToken(); return in.nval; }\n" +
            "    private static float readFloat(StreamTokenizer in) throws Exception { return (float) readDouble(in); }\n" +
            "    private static int readInteger(StreamTokenizer in) throws Exception { return (int) readDouble(in); }\n" +
            "    private static long readLong(StreamTokenizer in) throws Exception { return (long) readDouble(in); }\n" +
            "    private static byte readByte(StreamTokenizer in) throws Exception { return (byte) readDouble(in); }\n" +
            "\n" +
            "}";

    public static String get() {
        return TEMPLATE;
    }
}
