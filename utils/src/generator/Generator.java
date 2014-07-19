import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Generator {

    public static final String OUTPUT_FOLDER_PROP = "outputFolder";
    public static final String GENERATED_FILE = "test12/input.txt";

    public static void main(String[] args) throws IOException {
        File file = System.getProperties().containsKey(OUTPUT_FOLDER_PROP) ? new File(System.getProperty(OUTPUT_FOLDER_PROP), GENERATED_FILE) : new File(GENERATED_FILE);
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        PrintWriter out = new PrintWriter(FileUtils.openOutputStream(file));

        out.println(5000);
        for (int i = 0; i < 5000; i++) {
            out.print(i * 2 + 1);
            if (i < 5000 - 1) {
                out.print(' ');
            } else {
                out.println();
            }
        }

        out.flush();
        out.close();
    }

}
