package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private final org.example.Dictionary dictionary;

    public DictionaryManagement() {
        this.dictionary = new org.example.Dictionary();
    }

    public void insertFromCommandline(String word_target, String word_explain) {
        dictionary.addWord(word_target.toLowerCase(), word_explain.toLowerCase());
    }

    public void deleteFromCommandline(String deleteWord){
        dictionary.deleteWord(deleteWord.toLowerCase());
    }

    public void insertFromFile() {
        String url = "src/main/resources/org/example/Data/dictionaries.txt";
        try {
            FileInputStream fileInputStream = new FileInputStream(url);
            Scanner input = new Scanner(fileInputStream);
            boolean kt = true;
            while (input.hasNextLine()) {
                String[] parts = input.nextLine().split("\\s+\\s+", 2);
                if(kt){
                    parts[0] = parts[0].substring(1);
                    kt = false;
                }

                if(!parts[1].isEmpty() && parts[1].length() <= 100) {
                    dictionary.addWord(parts[0].toLowerCase(), parts[1].toLowerCase());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void lookUpWithPrefix(TrieNode u, String prefix, ArrayList<Word> list) {
        if(u == null){
            return;
        }
        if (u.isEnd()) {
            list.add(new Word(prefix, u.getMeaning()));
        }
        for (int i = 0; i < TrieNode.CHAR_SIZE; ++i) {
            if (u.getChildren().get(i) == null) {
                continue;
            }
            lookUpWithPrefix(u.getChildren().get(i), prefix + TrieNode.getCharWithIndex(i), list);
        }
    }

    public ArrayList<Word> dictionaryLookup(String newWord) {
        ArrayList<Word> list = new ArrayList<>();

        if(newWord.isEmpty()){
            return list;
        }

        TrieNode u = this.dictionary.getRoot();
        newWord = newWord.toLowerCase();
        for(int i = 0; i < newWord.length(); ++ i){
            int index = TrieNode.getIndexWithChar(newWord.charAt(i));
            if(index < 0 || index >= TrieNode.CHAR_SIZE || u.getChildren().get(index) == null){
                return list;
            }
            u = u.getChildren().get(index);
        }

        lookUpWithPrefix(u, newWord, list);
        return list;
    }


    public void dictionaryExportToFile() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/org/example/Data/dictionaries.txt");
        DataOutputStream data = new DataOutputStream(outputStream);
        ArrayList<Word> list = showAllWord();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getWord_target()+"           "+list.get(i).getWord_explain() + "\n";
            outputStream.write(s.getBytes());
        }
        data.close();
        outputStream.close();
    }

    public ArrayList<Word> showAllWord(){
        ArrayList<Word> list = new ArrayList<>();
        lookUpWithPrefix(dictionary.getRoot(), "", list);
        return list;
    }
}

