import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ProdutorConsumidorLock {
    static class Buffer {
        LinkedList<Integer> buffer = new LinkedList<Integer>();

        private boolean disponibilidade = false;
        private Lock lock = new ReentrantLock();
        private Condition condicional = lock.newCondition();


        public Buffer() {}

        public int consume(String id) {
            lock.lock();
            try {
                while (this.buffer.size() == 0) {
                    try {
                        condicional.wait();
                    } catch (InterruptedException e) { }
                }
                disponibilidade = false;
                System.out.println("Consumidor "+id+" consumiu: "+this.buffer.getFirst());
                this.buffer.removeFirst();
                this.condicional.signalAll();
            } finally {
                lock.unlock();
                int q = this.buffer.getFirst();
                this.buffer.removeFirst();
                return q;
            }
        }

        public void produce(String id, int v) {
            
        }
    }

    static class Produtor extends Thread {
        String id;
        int [] values;
        Buffer buffer;
        public Produtor(String id, int [] v, Buffer b) {
            this.id = id;
            this.values = v;
            this.buffer = b;
        }

        public void run() {
            for (int i = 0; i < this.values.length; i++) {
                this.buffer.produce(this.id, this.values[i]);
            }
        }
    }

    static class Consumidor extends Thread {
        String id;
        Buffer buffer;
        public Consumidor(String id, Buffer b) {
            this.id = id;
            this.buffer = b;
        }

        public void run() {
            while(true) {
                this.buffer.consume(this.id);
            }
        }
    }

    public static void main(String[] args) {
        Buffer buf = new Buffer();
        int [] values = new int[10];
        for (int i = 0; i < values.length; i++) {
            values[i] = 3*i;
        }
        Thread produtor = new Produtor("0", values, buf);
        Thread consumidor = new Consumidor("1", buf);
        produtor.start();
        consumidor.start();
    }
}
