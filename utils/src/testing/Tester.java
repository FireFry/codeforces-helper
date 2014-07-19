import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    private static final ClassLoader CLASS_LOADER = Tester.class.getClassLoader();

    public static void main(String[] args) {
        boolean succeeded = true;
        int test = 0;
        long maxTime = 0;
        for (TestData testData : findTests()) {
            test++;
            Verdict verdict = test(testData);
            if (!verdict.isSucceeded()) {
                succeeded = false;
                System.err.println("Test " + test + " failed");
                verdict.print(System.err);
            }
            if (verdict.time() > maxTime) {
                maxTime = verdict.time();
            }
        }
        if (test == 0) {
            System.err.println("No test found!");
        } else if (succeeded) {
            System.out.println("Ok! (time: " + maxTime + " ms)");
        }
    }

    private static Verdict test(TestData testData) {
        try {
            ByteArrayOutputStream solutionOutput = new ByteArrayOutputStream();
            Method method = Class.forName("Solution").getMethod("solve", InputStream.class, PrintStream.class);

            long strartAt = System.currentTimeMillis();
            method.invoke(null, testData.inputSteam(), new PrintStream(solutionOutput));
//            Solution.solve(testData.inputSteam(), new PrintStream(solutionOutput));
            long endAt = System.currentTimeMillis();

            return confirmOutput(testData.output(), solutionOutput.toString()) ?
                    Verdict.accepted(endAt - strartAt) :
                    Verdict.failed(testData.output(), solutionOutput.toString());
        } catch (Exception e) {
            return Verdict.failed(e);
        }
    }

    private static boolean confirmOutput(String expected, String actual) {
        return expected.equals(actual);
    }

    private static Iterable<? extends TestData> findTests() {
        List<TestData> result = new ArrayList<TestData>();
        int i = 1;
        try {
            for (;;) {
                result.add(new TestData(
                        read(CLASS_LOADER.getResourceAsStream("test" + i + "/input.txt")),
                        read(CLASS_LOADER.getResourceAsStream("test" + i + "/output.txt"))));
                i++;
            }
        } catch (Exception ignored) {}
        return result;
    }

    private static String read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[1024];
        int l;
        while ((l = reader.read(buffer, 0, buffer.length)) > 0) {
            stringBuilder.append(buffer, 0, l);
        }
        return stringBuilder.toString();
    }

}
