package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String fileName = args[0];

        try (Scanner scanner = new Scanner(new File(fileName))) {
            String text = scanner.nextLine();
            while (scanner.hasNext()) {
                text += scanner.nextLine();
            }

            analyzeText(text.trim());
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }

    }

    public static void analyzeText(String text) {
        int words = countWords(text);
        int sentences = countSentences(text);
        int chars = countChars(text);

        double score = 4.71 * ((double) chars / (double) words)
                    + 0.5 * ((double) words / (double) sentences)
                    - 21.43;
        String level = gradeLevel(score);

        System.out.printf("The text is:\n%s\n" +
                          "Words: %s\n" +
                          "Sentences: %s\n" +
                          "Characters: %s\n" +
                          "The score is: %.2f\n" +
                          "This text should be understood by %s year olds.",
                           text, words, sentences, chars, score, level);

    }

    public static int countSentences(String text) {
        return text.split("[?.!:]").length;
    }

    public static int countWords(String text) {
        return text.split("\\s+").length;
    }

    public static int countChars(String text) {
        Pattern pattern = Pattern.compile("\\S");
        Matcher matcher = pattern.matcher(text);
        return (int)matcher.results().count();
    }

    public static String gradeLevel(double score) {
        int s = (int)Math.ceil(score);
        String gLevel = Index.getGLevel(s);

        return gLevel;
    }
}
