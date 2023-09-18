import java.util.PriorityQueue;
import java.util.Queue;

public class E3 {
    static class SecureQueue {
        Queue<Integer> fila = new PriorityQueue<Integer>();

        public SecureQueue() {}

        synchronized public void insert(int i) {
            fila.add(i);
        }

        public int size() {
            return this.fila.size();
        }

        public int getElement() {
            return this.fila.remove();
        }
    }

    static class MyThread extends Thread {
        int id, start, end;
        SecureQueue fila;
        public MyThread(int start, int end, int id, SecureQueue f) {
            this.id = id;
            this.fila = f;
            this.start = start;
            this.end = end;
        }

        public void run() {
            for (;this.start <= this.end; this.start++) {
                this.fila.insert(this.start);
            }
        }
    }

    public static void main(String[] args) {
        SecureQueue fila = new SecureQueue();
        int n = 100;
        int t = 10;
        int jump = n/t;
        int start = 1;
        int end = jump;
        Thread [] threads = new Thread[t];
        for (int i = 0; i < t; i++) {
            threads[i] = new MyThread(start, end, i, fila);
            start+=jump;
            end+=jump;
        }
        for (int i = 0; i < t; i++) {
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }
        for(int i = 0; i < fila.size(); i++) {
            System.out.println(fila.getElement());
        }
    }
}
