import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class E1 {
    static class Primos extends Thread {
        int i, f, id;
        ArrayList<String> p;
        Primos(int i, int f, int id, ArrayList<String> p) {
            this.i = i;
            this.f = f;
            this.id = id;
            this.p = p;
        }
        public void run() {
            for (int n = i; n <= f; n++) {
                boolean primo = true;
                for (double j = 2; j <= Math.sqrt(n); j++) {
                    if (n % j == 0) {
                        primo = false;
                    }
                }
                if (primo && n != 0 && n != 1) {
                    synchronized(this) {
                        this.p.add("Thread "+this.id+" says: "+n);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner iscan = new Scanner(System.in);
        System.out.println("Diga um numero");
        int n = iscan.nextInt();
        System.out.println("Diga a quantidade de Threads");
        int th = iscan.nextInt();
        System.out.println(n+":"+th);
        ArrayList<String> primos = new ArrayList<String>();
        Thread[] threads = new Thread[th];
        int jump = n/th;
        int start = 0, end = jump;
        for (int i = 0; i < th; i++){
            threads[i] = new Primos(start, end, i, primos);
            start+=jump;
            end+=jump;
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }
        for (int i = 0; i < primos.size(); i++) {
            System.out.println(primos.get(i));
        }
    }
}
