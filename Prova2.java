import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Prova2 {
    static class Tarefa implements Callable<String> {
        int id, tempo;
        int [] tarefas;
        boolean feita = false;
        Tarefa tarefa;
        public Tarefa(int id, int tempo, int [] t) {
            this.id = id;
            this.tempo = tempo;
            this.tarefas = t;
        }

        public String call() throws Exception {
            try {
                Thread.sleep(this.tempo);
            } catch (InterruptedException e) {}
            this.feita
            return "Tarefa "+this.id+" feita";
        }

        /*
        synchronized public void setTarefa(Tarefa t) {
            this.tarefa = t;
        }

        synchronized public void fazer() {

        }
        */
    }

    static class Rainha extends Thread {
        int id = -1;
        Queue<Tarefa> tarefas;
        boolean todasFeitas = false;
        public Rainha(Queue<Tarefa> t) {
            this.tarefas = t;
        }

        public void run() {
            while (!todasFeitas) {
                boolean t = true;
                for (int i = 0; i < this.tarefas.size(); i++) {
                    Tarefa temp = this.tarefas.remove();
                    if (!temp.feita) {
                        t = false;
                    }
                    this.tarefas.add(temp);
                }
                if (t) {
                    todasFeitas = true;
                }

                
            }
        }

    }

    static class Operario implements Runnable {
        public void run() {
            while(true) {

            }
        }
    }

    public static void main(String[] args) {
        Scanner iscan = new Scanner(System.in);
        int O = 0, N = 0;
        O = iscan.nextInt();
        N = iscan.nextInt();
        Queue<Tarefa> tarefas = new LinkedList<Tarefa>();
        iscan.nextLine();
        for (int i = 1; i <= N; i++) {
            int id = 0, tempo = 0;
            String line = "";
            line = iscan.nextLine();
            String [] t = line.split(" ");
            int [] trabalhos = new int[t.length - 2];
            for (int j = 0; j < t.length; j++) {
                if (j == 0) {
                    id = Integer.parseInt(t[j]);
                } else if (j == 1) {
                    tempo = Integer.parseInt(t[j]);
                } else {
                    trabalhos[j - 2] = Integer.parseInt(t[j]);
                }
            }
            tarefas.add(new Tarefa(id, tempo, trabalhos));
        }
        Future<String> [] trabalhos = new Future<String>[N];
        ExecutorService rainha = null;
        try {
            rainha = Executors.newFixedThreadPool(O);
            for (int i = 0; i < N; i++) {

            }
            List<Future<String>> list = rainha.invokeAll(tarefas);
            for (Future<String> future : list) {
                System.out.println(future.get());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rainha != null) {
                rainha.shutdownNow();
            }
        }
    }
    /* 
        1 4
        1 200 2 4
        4 30
        3 50 2 
    */



}
