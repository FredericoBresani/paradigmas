

public class Paradigmas extends Thread {
    public static Boolean finished = false;
    public static int idFinished = -1;
    static class Counter extends Thread {
        int inf, sup, id;
        Counter(int i, int s, int id) {
            this.inf = i;
            this.sup = s;
            this.id = id;
        }

        public void run() {
            for (;this.inf <= this.sup; this.inf++) {
                System.out.println("Thread " + this.id + " says: " + this.inf);
            }
            synchronized (this) {
                if (!finished) {
                    idFinished = this.id;
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread[] counters = new Thread[2];
        int max = 10;
        int jump = max / counters.length;
        int start = 1, sup = jump;
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new Counter(start, sup, i);
            start+=jump;
            sup+=jump;
        }
        for (int i = 0; i < counters.length; i++) {
            counters[i].start();
            try {
                counters[i].join();
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }   
        }
        System.out.println("Thread "+idFinished+" was the first to finish!");
    }

}