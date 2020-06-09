package readability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSummary {

    private int words;
    private int sentences;
    private int chars;
    private int syllables;
    private int polysyllables;

    private String text;

    public TextSummary(String text) {
        this.text = text;
        countWords();
        countSentences();
        countChars();
        countSyllables();
        countPolysyllables();
    }

    public int getWords() {
        return words;
    }

    public int getSentences() {
        return sentences;
    }

    public int getChars() {
        return chars;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolysyllables() {
        return polysyllables;
    }

    public String getText() {
        return text;
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

        this.polysyllables = pSyllables;
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
