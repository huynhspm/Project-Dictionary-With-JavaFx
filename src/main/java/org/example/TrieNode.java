package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class TrieNode {
    private boolean isEnd;
    private String meaning;
    private int numberChildren;
    static final int CHAR_SIZE = 28;
    private final ArrayList<TrieNode> children;

    TrieNode(){
        isEnd = false;
        numberChildren = 0;
        children = new ArrayList<>(Collections.nCopies(CHAR_SIZE, null));
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public ArrayList<TrieNode> getChildren() {
        return children;
    }

    public void setChild(int index, TrieNode node){
        children.set(index, node);
    }

    public int getNumberChildren() {
        return numberChildren;
    }

    public void setNumberChildren(int numberChildren) {
        this.numberChildren = numberChildren;
    }

    public static char getCharWithIndex(int index){
        if(index == 26) return ' ';
        if(index == 27) return '-';
        return (char) (index + 97);
    }

    public static int getIndexWithChar(char character){
        if(character == ' ') return 26;
        if(character == '-') return 27;
        return (int) (character) - 97;
    }
}
