package readability;

public enum Index {
    KINDERGARTEN (1, "6", "Kindergarten"),
    FIRST_SECOND (2, "7", "First/Second Grade"),
    THIRD (3, "9", "Third Grade"),
    FOURTH(4, "10", "Fourth Grade"),
    FIFTH(5, "11", "Fifth Grade"),
    SIXTH(6, "12", "Sixth Grade"),
    SEVENTH(7, "13", "Seventh Grade"),
    EIGHTH(8, "14", "Eighth Grade"),
    NINTH(9, "15", "Ninth Grade"),
    TENTH(10, "16", "Tenth Grade"),
    ELEVENTH(11, "17", "Eleventh Grade"),
    TWELFTH(12, "18", "Twelfth grade"),
    COLLEGE(13, "24", "College student"),
    PROFESSOR(14, "24+", "Professor");

    private final int score;
    private final String ages;
    private final String gradeLevel;

    private Index(int score, String ages, String gradeLevel) {
        this.score = score;
        this.ages = ages;
        this.gradeLevel = gradeLevel;
    }

    public int getScore() {
        return score;
    }

    public String getAges() {
        return ages;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public static String getGLevel(int score) {
        String ages = "";
        for (Index index : values()) {
            if (index.getScore() == score) {
                ages = index.getAges();
            }
        }

        return ages;
    }
}
