package org.example;

import java.util.ArrayList;

public class Dictionary {

    private final TrieNode root;

    public Dictionary() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    public void addWord(String newWord, String meaning) {
        if(checkWord(newWord)){
            return;
        }

        TrieNode u = root;
        for(int i = 0 ; i < newWord.length(); ++ i) {
            int index = TrieNode.getIndexWithChar(newWord.charAt(i));
            if(u.getChildren().get(index) == null) {
                u.setNumberChildren(u.getNumberChildren() + 1);
                u.setChild(index, new TrieNode());
            }
            u = u.getChildren().get(index);
        }

        u.setEnd(true);
        u.setMeaning(meaning);
    }

    public void deleteWord(String deleteWord) {
        TrieNode u = root;
        ArrayList<TrieNode> trieNodeList = new ArrayList<>();
        trieNodeList.add(u);

        for(int i = 0; i < deleteWord.length(); ++ i) {
            int index = TrieNode.getIndexWithChar(deleteWord.charAt(i));
            u = u.getChildren().get(index);
            trieNodeList.add(u);
        }

        u.setEnd(false);
        u.setMeaning(null);

        for(int i = trieNodeList.size() - 1; i > 0; -- i){
            u = trieNodeList.get(i);
            if(u.getNumberChildren() == 1){
                u = null;
            }
            else{
                u.setNumberChildren(u.getNumberChildren() - 1);
                break;
            }
        }
    }

    private boolean checkWord(String word){
        for(int i = 0 ; i < word.length(); ++ i){
            int index = TrieNode.getIndexWithChar(word.charAt(i));
            if(index < 0 || index > TrieNode.CHAR_SIZE){
                return true;
            }
        }

        TrieNode u = root;
        for(int i = 0 ; i < word.length(); ++ i){
            u = u.getChildren().get(TrieNode.getIndexWithChar(word.charAt(i)));
            if(u == null){
                return false;
            }
        }

        return (u.isEnd());
    }
}

