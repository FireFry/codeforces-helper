import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Preparator {

    public static final String CONTEST_PREFIX = "codeforces.ru/contest/";
    public static final String PROBLEMSET_PREFIX = "codeforces.ru/problemset/problem/";

    public static void main(String[] args) throws IOException {
        System.out.print("Enter url: ");
        String url = new BufferedReader(new InputStreamReader(System.in)).readLine();
        Collection<Problem> problems = loadProblems(url);
        File solutionsRoot = new File(System.getProperty("solutionsRoot"));
        for (Problem problem : problems) {
            prepare(solutionsRoot, problem);
        }
    }

    private static Collection<Problem> loadProblems(String url) throws IOException {
        ArrayList<Problem> result = new ArrayList<Problem>();
        if (url.matches("[\\w/:]*codeforces.ru/contest/[0-9]+")) {
            for (char p = 'A'; p <= 'E'; p++) {
                result.add(loadProblem(url + "/problem/" + p));
            }
        } else {
            result.add(loadProblem(url));
        }
        return result;
    }

    private static Problem loadProblem(String url) throws IOException {
        return new Problem(extractProblemId(url), loadTests(url));
    }

    private static ProblemId extractProblemId(String url) {
        int i;
        String formatted;
        if ((i = url.indexOf(CONTEST_PREFIX)) >= 0) {
            formatted = url.substring(i + CONTEST_PREFIX.length()).replace("problem/", "");
        } else if ((i = url.indexOf(PROBLEMSET_PREFIX)) >= 0) {
            formatted = url.substring(i + PROBLEMSET_PREFIX.length());
        } else {
            throw new UnsupportedOperationException();
        }
        String[] split = formatted.split("/");
        return new ProblemId(split[0], split[1]);
    }

    private static ArrayList<TestData> loadTests(String url) throws IOException {
        Element element = Jsoup.connect(url).get().getElementsByClass("sample-test").get(0);
        Elements inputs = element.getElementsByClass("input");
        Elements outputs = element.getElementsByClass("output");
        ArrayList<TestData> tests = new ArrayList<TestData>(inputs.size());
        for (int i = 0, size = inputs.size(); i < size; i++) {
            String inputString = extractTextData(inputs.get(i).getElementsByTag("pre").get(0));
            String outputString = extractTextData(outputs.get(i).getElementsByTag("pre").get(0));
            tests.add(new TestData(inputString, outputString));
        }
        return tests;
    }

    private static String extractTextData(Element pre) {
        StringBuilder builder = new StringBuilder();
        for (Node node : pre.childNodes()) {
            if (node instanceof TextNode) {
                builder.append(((TextNode) node).text());
            } else if (node instanceof Element) {
                Element e = (Element) node;
                if (e.tag().getName().equals("br")) {
                    builder.append(System.lineSeparator());
                } else {
                    System.out.println("Unknown element: " + e);
                }
            }
        }
        return builder.toString();
    }

    private static void prepare(File root, Problem problem) {
        File parent = new File(new File(root, problem.id.competition), problem.id.problem);
        writeSolutionTemplate(parent);
        writeTests(parent, problem.tests);
    }

    private static void writeSolutionTemplate(File parent) {
        tryWrite(new File(parent, "Solution.java"), SolutionTemplate.get());
    }

    private static void writeTests(File parent, List<TestData> tests) {
        for (int i = 0; i < tests.size(); i++) {
            TestData test = tests.get(i);
            File testFolder = new File(parent, "test" + (i + 1));
            tryWrite(new File(testFolder, "input.txt"), test.input);
            tryWrite(new File(testFolder, "output.txt"), test.output);
        }
    }

    private static void tryWrite(File folder, String string) {
        try {
            FileUtils.writeStringToFile(folder, string);
        } catch (Exception e) {
            System.out.println("Failed to write to " + folder + ": " + e);
            e.printStackTrace();
        }
    }

    private static final class TestData {
        public final String input;
        public final String output;

        private TestData(String input, String output) {
            this.input = input;
            this.output = output;
        }
    }

    private static final class Problem {
        public final ProblemId id;
        public final List<TestData> tests;

        private Problem(ProblemId id, Collection<TestData> tests) {
            this.id = id;
            this.tests = Collections.unmodifiableList(new ArrayList<TestData>(tests));
        }
    }

    private static final class ProblemId {
        public final String competition;
        public final String problem;

        private ProblemId(String competition, String problem) {
            this.competition = competition;
            this.problem = problem;
        }
    }

}
