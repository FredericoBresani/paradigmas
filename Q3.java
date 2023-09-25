import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q3 {

    static class Node {
        int key, parentKey;
        Node left, right, parent;
        public Node(int key) {
            this.key = key;
            this.left = this.right = null;
        }
    }

    static class BinaryTree {
        Node root;
        public BinaryTree() {}
        /*
        add(root, 5);
        -> {5, null, null, parent(5)}
        add(root, 1);
        -> {5, add(null, 1), null, parent(5)}
        -> {5, {1, null, null, parent(5)}, null, parent(5)}
        add(root, 7);
        -> {5, {1, null, null, parent(5)}, null, parent(5)}
        -> {5, {1, null, null, parent(5)}, add(null, 7), parent(5)}
        -> {5, {1, null, null, parent(5)}, {7, null, null, parent(5)}, parent(5)}
        add(root, 3);
        -> {5, {1, null, null, parent(5)}, {7, null, null, parent(5)}, parent(5)}
        -> {5, add({1, null, null}, 3), {7, null, null, parent(5)}, parent(5)}
        -> {5, {1, null, add(null, 3)}, {7, null, null, parent(5)}, parent(5)}
        -> {5, {1, null, {3, null, null, parent(1)}, parent(5)}, {7, null, null, parent(5)}, parent(5)}
        */

        public Node add(Node root, int v) {
            if (this.root == null) {
                this.root = new Node(v);
                this.root.parentKey = v;
                return this.root;
            } else if (root == null) {
                return new Node(v);
            } else if (root.key < v) {
                root.right = add(root.right, v);
                root.right.parentKey = root.key;
                root.right.parent = root;
            } else if (root.key > v) {
                root.left = add(root.left, v);
                root.left.parentKey = root.key;
                root.left.parent = root;
            }
            return root;
        }

        public Node insertNode(Node root, Node nodeToInsert) {
            if (root == null) {
                return nodeToInsert;
            } else if (root.key < nodeToInsert.key) {
                root.right = insertNode(root.right, nodeToInsert);
                root.right.parentKey = root.key;
                root.right.parent = root;
            } else if (root.key > nodeToInsert.key) {
                root.left = insertNode(root.left, nodeToInsert);
                root.left.parentKey = root.key;
                root.left.parent = root;
            }
            return root;
        }

        public Node search(Node root, int k) {
            if (root == null) {
                return root;
            } else if (root.key == k) {
                return root;
            } else if (root.key < k) {
                return search(root.right, k);
            } else if (root.key > k) {
                return search(root.left, k);
            }
            return root;
        }

        public void remove(Node root) {
            if (root.key < root.parentKey) {
                root.parent.left = root.left;
                if (root.left != null) {
                    root.left.parent = root.parent;
                    root.left.parentKey = root.parent.key;
                }
                if (root.right != null) {
                    this.insertNode(this.root, root.right);
                }
            } else if (root.key > root.parentKey) {
                root.parent.right = root.right;
                if (root.right != null) {
                    root.right.parent = root.parent;
                    root.right.parentKey = root.parent.key;
                }
                if (root.left != null) {
                    this.insertNode(this.root, root.left);
                }
            } else {
               if (this.root.left != null) {
                this.root.left.parent = null;
                this.root.left.parentKey = this.root.left.key;
                if (this.root.right != null) {
                    Node temp = this.root.right;
                    this.root = this.root.left;
                    this.insertNode(this.root, temp);
                } else {
                    this.root = this.root.left;
                }
               } else if (this.root.right != null) {
                this.root.right.parent = null;
                this.root.right.parentKey = this.root.right.key;
                if (this.root.left != null) {
                    Node temp = this.root.left;
                    this.root = this.root.right;
                    this.insertNode(this.root, temp);
                } else {
                    this.root = this.root.right;
                }
               } else {
                this.root = null;
               } 
            }
        }
    }

    static class Produtor extends Thread {
        int amount;
        int id;
        BinaryTree t;
        public Produtor(int amount, int id, BinaryTree t) {
            this.amount = amount;
            this.id = id;
            this.t = t;
        }

        public void run() {
            for (int i = 0; i < amount; i++) {
                t.add(t.root, (int) Math.floor(Math.random() *(50*2000 - 0 + 1) + 0));
            }
        }
    }

    static class Consumidor extends Thread {
        int amount, id;
        BinaryTree t;
        public Consumidor(int amount, int id, BinaryTree t) {
            this.amount = amount;
            this.id = id;
            this.t = t;
        }

        public void run() {
            while (this.amount > 0) {
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        BinaryTree myTree = new BinaryTree();
        Thread [] produtores = new Thread[50];
        for (int i = 0; i < produtores.length; i++) {
            produtores[i] = new Produtor(2, i, myTree);
        }
        for (int i = 0; i < produtores.length; i++) {
            es.execute(produtores[i]);
        }
        myTree.add(myTree.root, 20);
        myTree.add(myTree.root, 25);
        myTree.add(myTree.root, 15);
        myTree.add(myTree.root, 10);
        myTree.add(myTree.root, 18);
        myTree.add(myTree.root, 5);
        myTree.add(myTree.root, 13);
        myTree.add(myTree.root, 17);
        myTree.add(myTree.root, 19);
        myTree.add(myTree.root, 3);
        myTree.add(myTree.root, 6);
        myTree.add(myTree.root, 12);
        myTree.add(myTree.root, 14); 
        myTree.add(myTree.root, 30);
        myTree.add(myTree.root, 23); 
        if (myTree.search(myTree.root, 15) == null) {
            System.out.println("Node not found!");
        } else {
            System.out.println("Node found!");
        } 
        myTree.remove(myTree.search(myTree.root, 15));
        myTree.remove(myTree.search(myTree.root, 25));
        myTree.remove(myTree.search(myTree.root, 19));
        myTree.remove(myTree.search(myTree.root, 18));
        myTree.remove(myTree.search(myTree.root, 20));
        myTree.remove(myTree.search(myTree.root, 5));
        myTree.remove(myTree.search(myTree.root, 6));
        myTree.remove(myTree.search(myTree.root, 3));
        myTree.remove(myTree.search(myTree.root, 12));
        myTree.remove(myTree.search(myTree.root, 14));
        myTree.remove(myTree.search(myTree.root, 13));
        myTree.remove(myTree.search(myTree.root, 17));
        myTree.remove(myTree.search(myTree.root, 10));
        myTree.remove(myTree.search(myTree.root, 23));
        myTree.remove(myTree.search(myTree.root, 30));
    }
}