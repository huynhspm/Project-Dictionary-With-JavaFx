package org.example;

public class Word {
    private final String word_target;
    private final String word_explain;

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    @Override
    public String toString() {
        return word_target;
    }
}
