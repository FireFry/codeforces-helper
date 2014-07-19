import java.io.PrintStream;

public class Verdict {

    private final boolean succeeded;
    private final String message;
    private final Exception exception;
    private final long time;

    public static Verdict accepted(long time) {
        return new Verdict(true, "Accepted", null, time);
    }

    public static Verdict failed(Exception e) {
        return new Verdict(false, "Exception during execution", e, 0);
    }

    public static Verdict failed(String expected, String actual) {
        StringBuilder builder = new StringBuilder()
                .append("Unexpected output: ")
                .append(System.lineSeparator())
                .append(expected);
        if (!expected.endsWith("\n")) {
            builder.append(System.lineSeparator());
        }
        builder.append(actual);
        if (!actual.endsWith("\n")) {
            builder.append(System.lineSeparator());
        }
        return new Verdict(false, builder.toString(), null, 0);
    }

    private Verdict(boolean succeeded, String message, Exception exception, long time) {
        this.succeeded = succeeded;
        this.message = message;
        this.exception = exception;
        this.time = time;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public long time() {
        return time;
    }

    public void print(PrintStream out) {
        out.println(message);
        if (exception != null) {
            exception.printStackTrace(out);
        }
    }

}
