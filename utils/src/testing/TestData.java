import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestData {
    private final String input;
    private final String ouput;

    public TestData(String input, String ouput) {
        this.input = input;
        this.ouput = ouput;
    }

    public InputStream inputSteam() {
        return new ByteArrayInputStream(input.getBytes());
    }

    public String output() {
        return ouput;
    }
}
