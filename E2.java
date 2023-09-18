public class E2 {
    static boolean stop = false;
    static class C {
        int c;
        public C() {
            this.c = 0;
        }

        public int getAndAdd() {
            synchronized(this) {
                this.c++;
            }
            return this.c;
        }
    }

    static class Contador extends Thread {
        int id, start, end, c;
        C contador;
        public Contador(int s, int e, int id, C c) {
            this.start = s;
            this.end = e;
            this.id = id;
            this.contador = c;
        }

        public void run() {
            for (int i = start; i < end; i++) {
                this.c = this.contador.getAndAdd();
                System.out.println("Thread "+this.id+" says: "+this.contador.c);
            }
        }
    }   
    
    public static void main(String[] args) {
        C contComp = new C();
        int n = 100;
        int t = 10;
        int step = n / t;
        int start = 0;
        int end = step;
        Thread [] threads = new Thread[t];
        for (int i = 0; i < t; i++) {
            threads[i] = new Contador(start, end, i, contComp);
            start+=step;
            end+=step;
        }
        for (int i = 0; i < t; i++){
            threads[i].start();
        }

    }
}
