import java.util.HashMap;

public class Trie {
    private TrieNode Sentinel;
    private class TrieNode{
        private boolean end;
        private HashMap<Character, TrieNode> children;
        private TrieNode(boolean end, HashMap<Character, TrieNode> children) {
            this.end = end;
            this.children = children;
        }
    }
    public Trie() {
        Sentinel = new TrieNode(false, new HashMap());
    }

    public void insert(String word) {
        insertHelper(0, word, Sentinel);
    }

    public void insertHelper(int charIndex, String word, TrieNode node) {
        char ch;
        if (charIndex < word.length()) {
            ch = word.charAt(charIndex);
        } else {
            return;
        }
        HashMap<Character, TrieNode> children = node.children;
        if (!children.containsKey(ch)) {
            if (charIndex == (word.length() - 1)) {
                children.put(ch, new TrieNode(true, new HashMap()));
            } else {
                children.put(ch, new TrieNode(false, new HashMap()));
            }
        } else {
            if (charIndex == (word.length() - 1)) {
                children.get(ch).end = true;
            }
        }
        insertHelper(charIndex + 1, word, children.get(ch));
    }

    public boolean search(String word) {
        return searchHelper(0, word, Sentinel);
    }

    public boolean searchHelper(int charIndex, String word, TrieNode node) {
        char ch = 'a';
        if (charIndex < word.length()) {
            ch = word.charAt(charIndex);
        }
        if (charIndex == word.length() && node != null) {
            return node.end;
        }
        HashMap<Character, TrieNode> children = node.children;
        if (children.containsKey(ch)) {
            return searchHelper(charIndex + 1, word, children.get(ch));
        }
        //children.containsKey(ch) == false, return false
        return false;
    }

    public boolean startsWith(String prefix) {
        return prefixHelper(0, prefix, Sentinel);
    }

    public boolean prefixHelper(int charIndex, String word, TrieNode node) {
        char ch = 'a';
        if (charIndex < word.length()) {
            ch = word.charAt(charIndex);
        }
        if (charIndex == word.length() && node != null) {
            return true;
        }
        HashMap<Character, TrieNode> children = node.children;
        if (children.containsKey(ch)) {
            return prefixHelper(charIndex + 1, word, children.get(ch));
        }
        //children.containsKey(ch) == false, return false
        return false;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
