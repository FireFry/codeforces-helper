import java.io.PrintStream;

public class Verdict {

    private static final Verdict ACCEPTED = new Verdict(true, "Accepted", null);

    private final boolean succeeded;
    private final String message;
    private final Exception exception;

    public static Verdict accepted() {
        return ACCEPTED;
    }

    public static Verdict failed(Exception e) {
        return new Verdict(false, "Exception during execution", e);
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
        return new Verdict(false, builder.toString(), null);
    }

    private Verdict(boolean succeeded, String message, Exception exception) {
        this.succeeded = succeeded;
        this.message = message;
        this.exception = exception;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void print(PrintStream out) {
        out.println(message);
        if (exception != null) {
            exception.printStackTrace(out);
        }
    }

}
