package shortestpath.algorithms;

import java.util.*;

import shortestpath.utils.Edge;



public class BellmanFord {
    private static final int INF = Integer.MAX_VALUE/ 2; // Avoid overflow



    public void Bellman_Ford(List<Edge> edges, int V, int source, int[] distance, int[] parents , boolean[] negativeCycle) {
        Arrays.fill(distance, INF);
        Arrays.fill(parents, -1);
        distance[source] = 0;

        for(int i = 0; i < V; i++){
            for(Edge edge : edges){
                if(distance[edge.source] != INF&& distance[edge.source] + edge.weight < distance[edge.destination]){
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                    parents[edge.destination] = edge.source;
                }
            }
        }

        for(Edge edge : edges){
            if(distance[edge.source] != INF&& distance[edge.source] + edge.weight < distance[edge.destination]){
                negativeCycle[0] = true;
                return;
            }
        }

    }


}
