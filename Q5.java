public class Q5 {
    static class CountDownLatch {
        int contador;
        public CountDownLatch(int i) {
            this.contador = i;
        }

        synchronized public void await(int id) {
            while (this.contador != 0) {
                try {
                    System.out.println("Waiter "+id+" esta esperando!");
                    this.wait();
                } catch (InterruptedException e) {}
            }
            System.out.println("Waiter "+id+" parou de esperar!");
        }

        synchronized public void countDown(int id) {
            this.contador--;
            System.out.println("Counter "+id+" decrementou para: "+this.contador);
            if (this.contador == 0) {
                this.notifyAll();
                try {
                    this.wait();
                } catch (InterruptedException e) {}
            }
        }
    }

    static class Waiter extends Thread {
        CountDownLatch latch;
        int id;
        public Waiter(int id, CountDownLatch l) {
            this.latch = l;
            this.id = id;
        }

        public void run() {
            this.latch.await(this.id);
        }
    }

    static class Counter extends Thread {
        int amount, id;
        CountDownLatch latch;
        public Counter(int a, CountDownLatch l, int id) {
            this.amount = a;
            this.latch = l;
            this.id = id;
        }

        public void run() {
            for (int i = 0; i < this.amount; i++) {
                this.latch.countDown(this.id);
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(100);
        Thread [] counters = new Thread[10];
        Thread [] waiters = new Thread[5];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new Counter(10, c, i);
        }
        for (int i = 0; i < waiters.length; i++) {
            waiters[i] = new Waiter(i, c);
        }
        for (int i = 0; i < counters.length; i++) {
            counters[i].start();
        }
        for (int i = 0; i < waiters.length; i++) {
            waiters[i].start();
        }
    }
}
