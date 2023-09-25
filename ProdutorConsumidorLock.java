import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ProdutorConsumidorLock {
    static class Buffer {
        public LinkedList<Integer> buffer = new LinkedList<Integer>();
        private Lock aLock = new ReentrantLock();
        private Condition condicional = aLock.newCondition();
        private boolean c = false;
        public Buffer() {}

        public void consume(String id) {
            aLock.lock();
            try {
                while (this.buffer.size() == 0) {
                    try {
                        condicional.await(); // O consumidor dorme e libera o lock
                    } catch (InterruptedException e) {}
                }
                System.out.println("Consumidor "+id+" consumiu: "+this.buffer.getFirst());
                this.buffer.removeFirst();
                condicional.signalAll(); // O consumidor acorda o produtor
            } finally {
                aLock.unlock(); // O condumidor libera o lock, agora o produtor pode pegar o lock
            }
        }

        public void produce(String id, int v) {
            aLock.lock();
            try {
                while (this.buffer.size() > 0) {
                    try {
                        condicional.await(); // O produtor dorme e libera o lock
                    } catch (InterruptedException e) {}
                } 
                this.buffer.push(v);
                System.out.println("Produtor "+id+" produziu: "+this.buffer.getFirst());
                condicional.signalAll(); // O produtor acorda o consumidor
            } finally {
                aLock.unlock(); // O produtor libera o lock, agora o consumidor pode pegar o lock
            }
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
        consumidor.start();
        produtor.start();
        
    }
}
