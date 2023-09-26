import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Prova1 {
    static class Pista {
        int id;
        private boolean ocupada;
        private Lock aLock = new ReentrantLock(true); //fair lock, na tentativa de dar preferência aos aviões de saida mais cedo
        private Condition condicional = aLock.newCondition();
        public Pista(int id) {
            this.ocupada = false;
            this.id = id;
        }

        public void utilizar(int id, long c, long s) {
            aLock.lock();
            try {
                while (this.ocupada) {
                    try {
                        this.condicional.await();
                    } catch (InterruptedException e) {}
                }
                this.ocupada = true;
                try {
                    Thread.sleep(500);
                    long t = System.currentTimeMillis();
                    if (s == -1) { // Aviao de chegada
                        System.out.println("Pista "+this.id+" | "+"Horario esperado: "+c+"-> Aviao: "+id);
                        System.out.println("Pista "+this.id+" | "+"Horario real: "+t+"-> Aviao: "+id);
                        System.out.println("Pista "+this.id+" | "+"Atraso: "+(t-c)+"-> Aviao: "+id);

                    } else if (c == -1) { // Aviao de saida
                        System.out.println("Pista "+this.id+" | "+"Horario esperado: "+s+"-> Aviao: "+id);
                        System.out.println("Pista "+this.id+" | "+"Horario real: "+t+"-> Aviao: "+id);
                        System.out.println("Pista "+this.id+" | "+"Atraso: "+(t-s)+"-> Aviao: "+id);
                    }
                } catch (InterruptedException e) {}
                this.condicional.signalAll();
            } finally {
                this.ocupada = false;
                aLock.unlock();
            }

        }
    }

    static class Aviao extends Thread {
        int id;
        long horaChega, horaSai;
        boolean terminado = false;
        Pista [] pistas;
        public Aviao(long c, long s, int id, Pista [] p) {
            this.horaChega = c;
            this.horaSai = s;
            this.pistas = p;
            this.id = id;
        }

        public void run() {
            while (!this.terminado) {
                for (int i = 0; i < this.pistas.length && !this.terminado; i++) {
                    if (!this.pistas[i].ocupada) {
                        this.pistas[i].utilizar(this.id, this.horaChega, this.horaSai);
                        this.terminado = true;
                        System.out.println("Aviao "+this.id+" terminou!");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner iscan = new Scanner(System.in);
        int N = 0, M = 0, K = 0;
        N = iscan.nextInt();
        M = iscan.nextInt();
        iscan.nextLine();
        K = iscan.nextInt();
        Thread [] avioesSaida = new Thread[N];
        Thread [] avioesChegada = new Thread[M];
        Pista [] pistas = new Pista[K];

        for (int i = 0; i < K; i++) { // Pistas
            pistas[i] = new Pista(i);
        }
        int i = 0;
        for (; i < N; i++) { // Aviões de saída
            avioesSaida[i] = new Aviao(-1, System.currentTimeMillis(), i, pistas);
            avioesSaida[i].start();
        }
        for (int t = 0; i < N + M; i++, t++) { // Aviões de chegada
            avioesChegada[t] = new Aviao(System.currentTimeMillis(), -1, i, pistas);
            avioesChegada[t].start();
        }
    }
}
