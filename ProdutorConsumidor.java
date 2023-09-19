import java.util.LinkedList;

public class ProdutorConsumidor {
    static class Buffer {
        LinkedList<Integer> buffer = new LinkedList<Integer>();
        public Buffer() {}

        synchronized void consume(String id) {
            if (this.buffer.size() > 0) {
                System.out.println("Consumidor "+id+" consumiu: "+this.buffer.getFirst());
                this.buffer.removeFirst();
                this.notifyAll(); // Consumidor notifica o produtor (Produtor acorda)
            } else {
                try {
                    this.wait(); // Consumidor dorme e libera o lock do Buffer
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            
        }

        synchronized void produce(String id, int value) {
            if (this.buffer.size() == 0) {
                this.buffer.push(value);
                System.out.println("Produtor "+id+" produziu: "+value);
                try {
                    this.notifyAll(); // Produtor notifica consumidor (consumidor acorda)
                    this.wait(); // Produtor dorme e libera o lock do Buffer 
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                
            }
        }
    }

    static class Produtor extends Thread {
        int [] values;
        String id;
        Buffer buffer;
        public Produtor(int [] v, String id, Buffer b) {
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
            while (true) {
                this.buffer.consume(this.id);
            }
        }
    }

    public static void main(String[] args) {
        Buffer buf = new Buffer();
        int [] values = new int[10];
        for (int i = 0; i < values.length; i++) {
            values[i] = 2*i;
        }
        Thread produtor = new Produtor(values, "0", buf);
        Thread consumidor = new Consumidor("1", buf);
        consumidor.start();
        produtor.start();

    }
}