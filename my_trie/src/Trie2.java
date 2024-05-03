import java.util.HashMap;

public class Trie2 {
    private TrieNode Sentinel;
    private class TrieNode{
        private boolean end;
        private HashMap<Character, TrieNode> children;
        private TrieNode(boolean end, HashMap<Character, TrieNode> children) {
            this.end = end;
            this.children = children;
        }
    }
    public Trie2() {
        Sentinel = new TrieNode(false, new HashMap());
    }

    public void insert(String word) {
        HashMap<Character, TrieNode> children = Sentinel.children;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!children.containsKey(c)) {
                if (i == (word.length() - 1)) {
                    children.put(c, new TrieNode(true, new HashMap()));
                } else {
                    children.put(c, new TrieNode(false, new HashMap()));
                }
            } else {
                if (i == (word.length() - 1)) {
                    children.get(c).end = true;
                }
            }
            children = children.get(c).children;
        }
    }

    public boolean search(String word) {
        HashMap<Character, TrieNode> children = Sentinel.children;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!children.containsKey(c)) {
                return false;
            } else {
                if (i == (word.length() - 1) && children.get(c).end == true) {
                    return true;
                }
            }
            children = children.get(c).children;
        }
        return false;
    }


    public boolean startsWith(String prefix) {
        HashMap<Character, TrieNode> children = Sentinel.children;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!children.containsKey(c)) {
                return false;
            } else {
                if (i == (prefix.length() - 1)) {
                    return true;
                }
            }
            children = children.get(c).children;
        }
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
