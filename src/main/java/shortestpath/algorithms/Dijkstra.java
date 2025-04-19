package shortestpath.algorithms;
import java.util.*;

import shortestpath.utils.Edge;


 public class Dijkstra {
    private List<List<Edge>> adjList;
    private static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow



    // Constructor: initialize graph with a given number of nodes
    public Dijkstra() {}

    public void setAdjacencyList(List<List<Edge>> adjList) {
        this.adjList = adjList;
    }


    // Dijkstra algorithm
    public void dijkstra(int source, int[] costs, int[] parents)
    {
        Arrays.fill(costs, INF);
        Arrays.fill(parents, -1);
        costs[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{source, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];
            int currentCost = current[1];




            if (currentCost > costs[u]) {
                continue;
            }




            for (Edge edge : adjList.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;
                int newCost = currentCost + weight;

                if (newCost < costs[v] && cost) {
                    costs[v] = newCost;
                    parents[v] = u;
                    pq.add(new int[]{v, newCost});
                }
            }
        }
    }

 }


