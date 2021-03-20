package bellman_ford;

import java.util.Scanner;

import static utils.Utils.getIntMinMax;

public class Graph {

    private static int mySource;
    private final int vertices;
    private final int edges;
    private final CustomEdge[] edge;
    private static final int MAX_VERTICES = 50;

    // Создаём граф с vertices вершин и edges рёбер
    Graph(int v, int e) {
        vertices = v;
        edges = e;
        edge = new CustomEdge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new CustomEdge();
    }

    void doBellmanFord(Graph graph, int source) {
        int gVertices = graph.vertices;
        int gEdges = graph.edges;
        int[] distance = new int[gVertices];

        // Отмечу некоторые шаги
        // Шаг 1: заполняем массив расстояний и предшественников
        for (int i = 0; i < gVertices; ++i)
            distance[i] = Integer.MAX_VALUE;

        // Помечаем начальную вершину
        distance[source] = 0;

        // Шаг 2: рёбра |V| - 1 раз
        for (int i = 1; i < gVertices; ++i) {
            for (int j = 0; j < gEdges; ++j) {
                // получим данные рёбер
                int u = graph.edge[j].source;
                int v = graph.edge[j].destination;
                int w = graph.edge[j].weight;
                if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
                    distance[v] = distance[u] + w;
            }
        }

        // Шаг 3: Ищем цикл отрицательного веса
        // если значение изменилось, то у нас цикл отрицательного веса в графе
        // мы не сможем найти кратчайшее расстояние
        for (int j = 0; j < gEdges; ++j) {
            int u = graph.edge[j].source;
            int v = graph.edge[j].destination;
            int w = graph.edge[j].weight;
            if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v]) {
                System.out.println("Граф содержит цикл отрицательного веса!");
                return;
            }
        }

        // Не найдены циклы отрицательного веса,
        // выводим расстояние и массив предшественников
        printSolution(distance, gVertices);
    }

    public static boolean isOrientedEdge() {
        System.out.print("=> Ориентированное ребро? (y / n): ");
        Scanner in = new Scanner(System.in);
        String res = in.nextLine();
        return !res.equals("n");
    }

    void printSolution(int[] distance, int vertices) {
        System.out.println("\nСписок минимальных путей от выбранной вершины: ");
        for (int i = 0; i < vertices; ++i) {
            if (distance[i] != Integer.MAX_VALUE){
                System.out.println("От " + mySource + " до " + i + " минимальный путь = " + distance[i]);
            } else {
                System.out.println("От " + mySource + " до " + i + " пути не существует!");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Необходимо ввести данные о графе:");
        System.out.print("Введите число вершин: ");
        int vertices = getIntMinMax(0, MAX_VERTICES);
        System.out.print("Введите число рёбер: ");
        int edges = getIntMinMax(0, MAX_VERTICES * MAX_VERTICES);

        Graph graph = new Graph(vertices, edges);

        System.out.println("Необходимо ввести данные о рёбрах:");
        int srcVertex;
        int destVertex;
        int weightEdge;
        for (int e = 0; e < edges; e++) {
            System.out.print("Ребро " + (e + 1) + "\nВведите начальную вершину: ");
            srcVertex = getIntMinMax(0, vertices - 1);
            graph.edge[e].source = srcVertex;
            System.out.print("Введите конечную вершину: ");
            destVertex = getIntMinMax(0, vertices - 1);
            graph.edge[e].destination = destVertex;
            System.out.print("Введите вес ребра: ");
            weightEdge = getIntMinMax((Integer.MAX_VALUE - 1) * -1, Integer.MAX_VALUE - 1);
            graph.edge[e].weight = weightEdge;
            if (!isOrientedEdge()) {
                graph.edge[e].source = destVertex;
                graph.edge[e].destination = srcVertex;
                graph.edge[e].weight = weightEdge;
            }

        }

        System.out.print("\nВведите вершину, от которой хотите найти кратчайшие пути: ");
        mySource = getIntMinMax(0, vertices - 1);
        graph.doBellmanFord(graph, mySource);
    }
}