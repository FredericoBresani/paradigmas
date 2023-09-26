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
    static class Tarefa {
        int id, tempo;
        boolean feita = false;
        int [] tarefas;
        public Tarefa(int id, int tempo, int [] t) {
            this.id = id;
            this.tempo = tempo;
            this.tarefas = t;
        }

        /*
        synchronized public void setTarefa(Tarefa t) {
            this.tarefa = t;
        }

        synchronized public void fazer() {

        }
        */
    }

    static class ListaTarefas {
        Tarefa [] tarefas;
        public ListaTarefas(Tarefa [] t) {
            this.tarefas = t;
        }

        synchronized public void setTarefas() {
            
        }

        synchronized public void fazerTarefa()
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
        ListaTarefas listaT = new ListaTarefas(null);
        iscan.nextLine();
        for (int i = 1; i <= N; i++) {
            int id = 0, tempo = 0;
            String line = "";
            line = iscan.nextLine();
            String [] t = line.split(" ");
            int [] subTarefas = new int[t.length - 2];
            for (int j = 0; j < t.length; j++) {
                if (j == 0) {
                    id = Integer.parseInt(t[j]);
                } else if (j == 1) {
                    tempo = Integer.parseInt(t[j]);
                } else {
                    subTarefas[j - 2] = Integer.parseInt(t[j]);
                }
            }
            listaT.tarefas[i - 1] = new Tarefa(id, tempo, subTarefas);
        }
    }
    /* 
        1 4
        1 200 2 4
        4 30
        3 50 2 
    */



}
