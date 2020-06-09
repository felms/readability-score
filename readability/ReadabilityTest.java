package readability;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadabilityTest {

    private int words;
    private int sentences;
    private int chars;
    private int syllables;
    private int pSyllables;

    private String text;

    public ReadabilityTest(String text) {
        this.text = text;
        countWords();
        countSentences();
        countChars();
        countSyllables();
        countPolysyllables();
    }

    public void analyzeText() {
        double score = 4.71 * ((double) chars / (double) words)
                + 0.5 * ((double) words / (double) sentences)
                - 21.43;
        String level = gradeLevel(score);

        System.out.printf("The text is:\n%s\n\n" +
                        "Words: %s\n" +
                        "Sentences: %s\n" +
                        "Characters: %s\n" +
                        "Syllables: %d\n" +
                        "Polysyllables: %d\n" +
                        "Enter the score you want to calculate (ARI, FK, SMOG, CL, all):\n",
                text, words, sentences, chars, syllables, pSyllables);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();

        switch (input) {
            case "ARI":
                automatedReadabilityIndex();
                break;

            case "FK" :
                fleschKincaidReadabilityTests();
                break;

            case "SMOG" :
                simpleMeasureOfGobbledygook();
                break;

            case "CL":
                colemanLiauIndex();
                break;

            case "ALL":
                automatedReadabilityIndex();
                fleschKincaidReadabilityTests();
                simpleMeasureOfGobbledygook();
                colemanLiauIndex();
                break;

            default:
                break;
        }

    }

    public void automatedReadabilityIndex() {
        double score = 4.71 * ((double) chars / (double) words)
                + 0.5 * ((double) words / (double) sentences)
                - 21.43;
        String level = gradeLevel(score);
        System.out.printf("Automated Readability Index: %.2f (about %s year olds).\n", score, level);
    }

    public void fleschKincaidReadabilityTests() {
        double score = 0.39 * ((double)this.words / (double)this.sentences)
                + 11.8 * ((double) this.syllables / (double) this.words)
                - 15.59;
        String level = gradeLevel(score);
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s year olds).\n", score, level);
    }

    public void simpleMeasureOfGobbledygook() {
        double score = 1.043
                * Math.sqrt((double) this.pSyllables * (30.0 / (double) this.sentences))
                + 3.1291;
        String level = gradeLevel(score);
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s year olds).\n", score, level);
    }

    public void colemanLiauIndex() {

        double l = (double) this.chars / ((double) this.words / 100.0);
        double s = (double) this.sentences / ((double) this.words / 100.0);

        double score = 0.0588 * l - 0.296 * s - 15.8;
        String level = gradeLevel(score);
        System.out.printf("Coleman–Liau index: %.2f (about %s year olds).\n", score, level);
    }

    private void countWords() {
        this.words = this.text.split("\\s+").length;
    }

    private void countSentences() {
        this.sentences = this.text.split("[?.!:]").length;
    }

    private void countChars() {
        Pattern pattern = Pattern.compile("\\S");
        Matcher matcher = pattern.matcher(this.text);
        this.chars = (int)matcher.results().count();
    }

    private void countSyllables() {
        int syllables = 0;

        for (String s : this.text.trim().split("\\s+")) {
            syllables += countWordSyllables(s);
        }

        this.syllables = syllables;
    }

    public void countPolysyllables() {
        int pSyllables = 0;

        String input = this.text.replaceAll("[?.!:,;]", "");
        for (String s : input.trim().split("\\s+")) {
            if (countWordSyllables(s) > 2) {
                pSyllables++;
            }
        }

        this.pSyllables = pSyllables;
    }

    private String gradeLevel(double score) {
        int s = (int)Math.round(score);
        String gLevel = Index.getGLevel(s);

        return gLevel;
    }

    private int countWordSyllables(String word) {

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

    private boolean isVowel(char c) {

        return c == 'a' || c == 'e' || c == 'i' ||
                c == 'o' || c == 'u' || c == 'y';
    }
}
