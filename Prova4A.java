/* 
 *  Para essa questão em Java preferi usar o ReentrantLock principalmente porque o produtor consegue produzir,
 *  liberar o lock e posteriormente já pegar o lock novamente. Dessa forma cumprindo o requisito da questão de
 *  fazer um produtor de mensagens que produz indefinidamente. ALém disso, é bastante fácil usar os locks juntos
 *  com conditions para fazer a troca de mensagens entre threads com await() e signalAll().
*/



import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Prova4A {
    static class Buffer {
        LinkedList<String> buffer = new LinkedList<String>();
        private Lock aLock = new ReentrantLock();
        private Condition condicional = aLock.newCondition();
        public Buffer() {}

        public void consume(String id) {
            aLock.lock();
            try {
                while (this.buffer.size() == 0) {
                    try {
                        this.condicional.await(); // Consumidor dorme e libera o lock
                    } catch (InterruptedException e) {}
                }
                System.out.println("Thread "+id+" consumiu: "+this.buffer.getFirst());
                this.buffer.removeFirst();
                this.condicional.signalAll(); // Consumidor notifica o produtor (Produtor acorda)
            } finally {
                aLock.unlock(); // consumidor libera o lock
            }
        }

        public void produce(String id, int value) {
            aLock.lock();
            try {
                this.buffer.push("Mensagem "+value);
                System.out.println("Thread "+id+" mandou: Mensagem "+value);
                this.condicional.signalAll(); // Produtor acorda consumidores
            } finally {
                aLock.unlock(); // Produtor libera o lock
            }
        }
    }

    static class Produtor extends Thread {
        Scanner iscan = new Scanner(System.in);
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
        int [] values = new int[50];
        for (int i = 0; i < values.length; i++) {
            values[i] = 2*i;
        }
        Thread produtor = new Produtor(values, "0", buf);
        Thread consumidor = new Consumidor("1", buf);
        consumidor.start();
        produtor.start();

    }
}
