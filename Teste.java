import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Tarefa {
    private int id;
    private int tempoResolucao;
    private List<Integer> dependencias;

    public Tarefa(int id, int tempoResolucao, List<Integer> dependencias) {
        this.id = id;
        this.tempoResolucao = tempoResolucao;
        this.dependencias = dependencias;
    }

    public int getId() {
        return id;
    }

    public int getTempoResolucao() {
        return tempoResolucao;
    }

    public List<Integer> getDependencias() {
        return dependencias;
    }
}

public class Teste {

    public static void main(String[] args) {
        Scanner iscan = new Scanner(System.in);
        int O = 0, N = 0;
        O = iscan.nextInt();
        N = iscan.nextInt();
        List<Tarefa> tarefas = new ArrayList<>();
        ExecutorService rainha = null;
        try {
            rainha = Executors.newFixedThreadPool(O);
            Set<Integer> tarefasConcluidas = new HashSet<>();
            iscan.nextLine();
            for (int i = 1; i <= N; i++) {
                int id = 0, tempo = 0;
                String line = "";
                line = iscan.nextLine();
                String[] t = line.split(" ");
                List<Integer> trabalhos = new ArrayList<>();
                for (int j = 0; j < t.length; j++) {
                    if (j == 0) {
                        id = Integer.parseInt(t[j]);
                    } else if (j == 1) {
                        tempo = Integer.parseInt(t[j]);
                    } else {
                        trabalhos.add(Integer.parseInt(t[j]));
                    }
                }
                tarefas.add(new Tarefa(id, tempo, trabalhos));
            }

            while (!tarefasConcluidas.containsAll(getTarefasIniciaveis(tarefas, tarefasConcluidas))) {
                for (Tarefa tarefa : tarefas) {
                    if (!tarefasConcluidas.contains(tarefa.getId())
                            && tarefasConcluidas.containsAll(tarefa.getDependencias())) {
                        rainha.execute(() -> realizarTarefa(tarefa, tarefasConcluidas));
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rainha != null) {
                rainha.shutdownNow();
            }
        }
    }

    public static List<Integer> getTarefasIniciaveis(List<Tarefa> tarefas, Set<Integer> tarefasConcluidas) {
        List<Integer> iniciaveis = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            if (!tarefasConcluidas.contains(tarefa.getId())
                    && tarefasConcluidas.containsAll(tarefa.getDependencias())) {
                iniciaveis.add(tarefa.getId());
            }
        }
        return iniciaveis;
    }

    public static void realizarTarefa(Tarefa tarefa, Set<Integer> tarefasConcluidas) {
        System.out.println("tarefa " + tarefa.getId() + " feita");
        try {
            Thread.sleep(tarefa.getTempoResolucao());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tarefasConcluidas.add(tarefa.getId());
    }
    /*
1 4
1 200 2 4
4 30
3 50 2
2 100
     */
}
