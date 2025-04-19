package shortestpath.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MakeGraphs {

    public static class Edge {
        public int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }


    String theFile;
    private int V;
    private int[][] adjMatrix;
    private List<List<Edge>> adjList;
    private static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow
    private List<Edge> edges = new java.util.ArrayList<>();

    //this init all
    public MakeGraphs(String theFile) throws IOException {
        this.theFile = theFile;
        initAdjMatrix();
        initAdjList(); // unified for Dijkstra + Bellman
    }

    public MakeGraphs(String theFile, int whichAlgo) throws IOException {
        this.theFile = theFile;
        if (whichAlgo == 1) {
            initAdjMatrix(); // Floyd-Warshall
        } else {
            initAdjList();   // Dijkstra + Bellman
        }
    }


    // Floyd-Warshall Graph
    public void initAdjMatrix() throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(theFile));

        String[] firstLine = br.readLine().split("\\s+");
        V = Integer.parseInt(firstLine[0]);

        adjMatrix = new int [V][V];

        for (int i = 0; i < V; i++) {
            Arrays.fill(adjMatrix[i], INF);
            adjMatrix[i][i] = 0; // Distance to self is zero
        }


        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split("\\s+");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            int w = Integer.parseInt(parts[2]);
            adjMatrix[u][v] = Math.min(adjMatrix[u][v], w);
        }
        br.close();
    }

    public void initAdjList() throws IOException{
        adjList = new ArrayList<>();
        edges = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(theFile));
        String[] firstLine = br.readLine().split("\\s+");
        V = Integer.parseInt(firstLine[0]);

        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            int w = Integer.parseInt(parts[2]);

            Edge edge = new Edge(u, v, w);
            edges.add(edge);                // For Bellman-Ford
            adjList.get(u).add(edge);          // For Dijkstra
        }

        br.close();

    }


    public void dijkstra(int source, int[] costs, int[] parents) {
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        parents[source] = 0;
        costs[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];
            int currentCost = current[1];

            if (currentCost > costs[u]) continue;

            for (Edge edge : adjList.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;
                int newCost = currentCost + weight;

                if (newCost < costs[v]) {
                    costs[v] = newCost;
                    parents[v] = u;
                    pq.add(new int[]{v, newCost});
                }
            }
        }
    }


    public void Bellman_Ford(int source, int[] distance, int[] parent) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;
        for(int i = 0; i < V; i++){
            for(Edge edge : edges){
                if(distance[edge.source] != Integer.MAX_VALUE && distance[edge.source] + edge.weight < distance[edge.destination]){
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                    parent[edge.destination] = edge.source;
                }
            }
        }



    }


    public boolean floydWarshall(int[][] costs, int[][] predecessors) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                costs[i][j] = adjMatrix[i][j];
                predecessors[i][j] = (i != j && adjMatrix[i][j] < INF) ? i : -1;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (costs[i][k] + costs[k][j] < costs[i][j]) {
                        costs[i][j] = costs[i][k] + costs[k][j];
                        predecessors[i][j] = predecessors[k][j];
                    }
                }
            }
        }

        // Check for negative cycles
        for (int i = 0; i < V; i++) {
            if (costs[i][i] < 0) {
                return false;
            }
        }

        return true;
    }


    public int size() {
        return V;
    }

    public List<Edge> getEdges() {
        return edges;
    }

}
