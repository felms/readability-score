package readability;

import java.util.Scanner;

public class ReadabilityTest {

    private TextSummary textSummary;

    public ReadabilityTest(TextSummary textSummary) {
        this.textSummary = textSummary;
    }

    public void analyzeText() {
        double score = 4.71 * ((double) textSummary.getChars() / (double) textSummary.getWords())
                + 0.5 * ((double) textSummary.getWords() / (double) textSummary.getSentences())
                - 21.43;
        String level = gradeLevel(score);

        System.out.printf("The text is:\n%s\n\n" +
                        "Words: %s\n" +
                        "Sentences: %s\n" +
                        "Characters: %s\n" +
                        "Syllables: %d\n" +
                        "Polysyllables: %d\n" +
                        "Enter the score you want to calculate (ARI, FK, SMOG, CL, all):\n",
                        textSummary.getText(), textSummary.getWords(), textSummary.getSentences(),
                        textSummary.getChars(), textSummary.getSyllables(), textSummary.getPolysyllables());

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
        double score = 4.71 * ((double) textSummary.getChars() / (double) textSummary.getWords())
                + 0.5 * ((double) textSummary.getWords() / (double) textSummary.getSentences())
                - 21.43;
        String level = gradeLevel(score);
        System.out.printf("Automated Readability Index: %.2f (about %s year olds).\n", score, level);
    }

    public void fleschKincaidReadabilityTests() {
        double score = 0.39 * ((double)this.textSummary.getWords() / (double)textSummary.getSentences())
                + 11.8 * ((double) textSummary.getSyllables() / (double) this.textSummary.getWords())
                - 15.59;
        String level = gradeLevel(score);
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s year olds).\n", score, level);
    }

    public void simpleMeasureOfGobbledygook() {
        double score = 1.043
                * Math.sqrt((double) textSummary.getPolysyllables() * (30.0 / (double) textSummary.getSentences()))
                + 3.1291;
        String level = gradeLevel(score);
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s year olds).\n", score, level);
    }

    public void colemanLiauIndex() {

        double l = (double) textSummary.getChars() / ((double) textSummary.getWords() / 100.0);
        double s = (double) textSummary.getSentences() / ((double) textSummary.getWords() / 100.0);

        double score = 0.0588 * l - 0.296 * s - 15.8;
        String level = gradeLevel(score);
        System.out.printf("Coleman–Liau index: %.2f (about %s year olds).\n", score, level);
    }



    private String gradeLevel(double score) {
        int s = (int)Math.round(score);
        String gLevel = Index.getGLevel(s);

        return gLevel;
    }
}
