package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root;

    private class BSTNode {
        private K key;
        private V value;
        private int size = 0;
        private BSTNode left;
        private BSTNode right;
        BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BSTMap() {

    }

    public BSTMap(K key, V value) {
        root = new BSTNode(key, value, 1);
    }
    @Override
    public void clear() {
        root = null;
    }
    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }
    @Override
    public V get(K key) {
        BSTNode keyNode = getNode(key);
        if (keyNode == null) {
            return null;
        } else {
            return keyNode.value;
        }
    }
    //return the node corresponds to that key
    public BSTNode getNode(K key) {
        BSTNode pointer = root;
        while (pointer != null) {
            if (pointer.key.compareTo(key) == 0) {
                return pointer;
            } else if (pointer.key.compareTo(key) < 0) {
                pointer = pointer.right;
            } else {
                pointer = pointer.left;
            }
        }
        return null;
    }
    @Override
    public int size() {
        return size(root);
    }
    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode root, K key, V value) {
        if (root == null) {
            return new BSTNode(key, value, 1);
        }
        int comp = root.key.compareTo(key);
        if (comp == 0) {
            root.value = value;
        } else if (comp < 0) {
            root.right = put(root.right, key, value);
        } else {
            root.left = put(root.left, key, value);
        }
        root.size = 1 + size(root.left) + size(root.right);
        return root;
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
