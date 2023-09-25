import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q3 {
    static class Node {
        int value;
        Node esquerdo;
        Node direito;
        public Node(int v) {
            this.value = v;
            this.esquerdo = this.direito = null;
        }
    }
    static class Tree {
        Node raiz;
        int nodeCount;
        public Tree() {
            this.raiz = null;
        }

        synchronized Node insert(Node n, int v, int id) {
            if (n == null) {
                n = new Node(v);
                nodeCount++;
                // System.out.println("Produtor "+id+" inseriu: "+v);
                return n;
            } else {
                if (v < n.value)
                    n.esquerdo = insert(n.esquerdo, v, id);
                else if (v > n.value)
                    n.direito = insert(n.direito, v, id);
            }
            return n;
        }

        synchronized Node remove(Node n, int v, int id) {
            if (n.value == v) {
                n = null;
                nodeCount--;
                System.out.println("Consumidor "+id+" consumiu: "+v);
                return n;
            } else {
                if (n.value < v) {
                    return remove(n.esquerdo, v, id);
                } else if (n.value > v) {
                    return remove(n.direito, v, id);
                }
            }
            n = null;
            nodeCount--;
            System.out.println("Consumidor "+id+" consumiu: "+v);
            return n;
        }
    }

    static class Produtor extends Thread {
        int start;
        int end;
        int id;
        Tree t;
        public Produtor(int start, int end, int id, Tree t) {
            this.start = start;
            this.end = end;
            this.id = id;
            this.t = t;
        }

        public void run() {
            for (int i = start; i < end; i++) {
                t.insert(t.raiz, (int) Math.floor(Math.random() *(50*2000 - 0 + 1) + 0), this.id);
            }
        }
    }

    static class Consumidor extends Thread {
        int amount, id;
        Tree t;
        public Consumidor(int amount, int id, Tree t) {
            this.amount = amount;
            this.id = id;
            this.t = t;
        }

        public void run() {
            while (this.amount > 0) {
                this.t.remove(t.raiz, (int) Math.floor(Math.random() *(50*2000 - 0 + 1) + 0), this.id);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        Tree myTree = new Tree();
        myTree.raiz = myTree.insert(myTree.raiz, (int) Math.floor(Math.random() *(50*2000 - 0 + 1) + 0), -1);

        Thread [] produtores = new Thread[50];
        Thread [] consumidores = new Thread[10];
        for (int i = 0; i < produtores.length; i++) {
            produtores[i] = new Produtor(0, 2000, i, myTree);
        }
        for (int i = 0; i < produtores.length; i++) {
            es.execute(produtores[i]);
        }
        for (int i = 0; i < consumidores.length; i++) {
            consumidores[i] = new Consumidor(10, i, myTree);
        }
        for (int i = 0; i < consumidores.length; i++) {
            es.execute(consumidores[i]);
        }
        
        es.shutdown();
        try {
            while(!es.awaitTermination(1, TimeUnit.MINUTES));
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Tempo em ms: "+timeElapsed);
            System.out.println("Quantidade de nós: "+myTree.nodeCount);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
