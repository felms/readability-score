package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String fileName = args[0];

        try (Scanner scanner = new Scanner(new File(fileName))) {
            String text = scanner.nextLine();
            while (scanner.hasNext()) {
                text += scanner.nextLine();
            }
            TextSummary textSummary = new TextSummary(text);
            ReadabilityTest rTest = new ReadabilityTest(textSummary);
            rTest.analyzeText();
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }

    }

}
