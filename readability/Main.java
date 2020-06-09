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
        int syllables = countSyllables(text);

        double score = 4.71 * ((double) chars / (double) words)
                    + 0.5 * ((double) words / (double) sentences)
                    - 21.43;
        String level = gradeLevel(score);

        System.out.printf("The text is:\n%s\n" +
                          "Words: %s\n" +
                          "Sentences: %s\n" +
                          "Characters: %s\n" +
                          "Syllables: %d\n" +
                          "The score is: %.2f\n" +
                          "This text should be understood by %s year olds.",
                           text, words, sentences, chars, syllables, score, level);

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

    public static int countSyllables(String text) {
        int syllables = 0;

        for (String s : text.trim().split("\\s+")) {
            syllables += countWordSyllables(s);
        }

        return syllables;
    }

    public static String gradeLevel(double score) {
        int s = (int)Math.ceil(score);
        String gLevel = Index.getGLevel(s);

        return gLevel;
    }

    public static int countWordSyllables(String word) {
        
        int vowels = 0;
        char[] c = word.toCharArray();
        char lastChar = 0;

        for (int i = 0; i < c.length; i++) {
            if (isVowel(c[i])) {
                vowels++;

                if (isVowel(lastChar)) {
                    vowels--;
                } else if (c[i] == 'e' && i == c.length - 1) {
                    vowels--;
                }

                
            }
            lastChar = c[i];
        }

        return vowels > 0 ? vowels : 1; 
    }

    private static boolean isVowel(char c) {

        return c == 'a' || c == 'e' || c == 'i' ||
               c == 'o' || c == 'u' || c == 'y';
    }
}
