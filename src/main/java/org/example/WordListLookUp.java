package org.example;

import java.util.ArrayList;

public class WordListLookUp {
    private final String word;
    private final ArrayList<Word> list;
    private final int MaxSize = 10;

    public WordListLookUp(String word) {
        this.word = word;
        this.list = new ArrayList<>();
        if(word != null && !word.isEmpty()){
            lookUpListWord();
        }
    }

    public ArrayList<Word> getList() {
        return list;
    }

    private String swapCharacter(int i){
        return word.substring(0, i - 1) + word.charAt(i) +
                word.charAt(i - 1) + word.substring(i + 1);
    }

    private String addCharacter(int i, char character){
        return word.substring(0, i) + character + word.substring(i);
    }

    private String replaceCharacter(int i, char character){
        return word.substring(0, i) + character + word.substring(i + 1);
    }

    private String deleteCharacter(int i){
        return word.substring(0, i) + word.substring(i + 1);
    }

    private void modifyToLookUp(String newWord){
        String s;
        for(int i = 0; i < newWord.length(); ++ i) {
            if(i > 0) {
                s = swapCharacter(i);
                list.addAll(org.example.MainDictionary.dictionaryManagement.dictionaryLookup(s));
            }

            s = deleteCharacter(i);
            list.addAll(org.example.MainDictionary.dictionaryManagement.dictionaryLookup(s));

            if(list.size() > MaxSize){
                return;
            }

            for (int j = 0; j < org.example.TrieNode.CHAR_SIZE; ++j){
                char character = org.example.TrieNode.getCharWithIndex(j);
                s = addCharacter(i, character);
                list.addAll(org.example.MainDictionary.dictionaryManagement.dictionaryLookup(s));
                if(list.size() > MaxSize){
                    return;
                }

                s = replaceCharacter(i, character);
                list.addAll(org.example.MainDictionary.dictionaryManagement.dictionaryLookup(s));
                if(list.size() > MaxSize){
                    return;
                }
            }
        }
    }

    private void lookUpListWord() {
        list.addAll(org.example.MainDictionary.dictionaryManagement.dictionaryLookup(word));
        if(!list.isEmpty()) {
            return;
        }

        modifyToLookUp(word);
        if(list.size() < MaxSize){
            modifyToLookUp(word.substring(0, word.length() - 1));
            modifyToLookUp(word.substring(0, word.length() - 2));
        }

        modifyList();
    }

    private void modifyList(){
        for(int i = 0; i < list.size(); ++ i){
            for(int j = i + 1; j < list.size(); ++ j){
                if(list.get(i).getWord_target().equals(list.get(j).getWord_target())){
                    list.remove(j);
                    j --;
                }
            }
        }
    }
}
