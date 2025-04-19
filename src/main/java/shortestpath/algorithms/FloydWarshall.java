package shortestpath.algorithms;

import java.io.*;
import java.util.*;

public class FloydWarshall {
    private static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow
    private int V, E;
    private int[][] adjMatrix;

    public FloydWarshall(){}
    public FloydWarshall(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] firstLine = br.readLine().split("\\s+");
        V = Integer.parseInt(firstLine[0]);
        E = Integer.parseInt(firstLine[1]);

        adjMatrix = new int[V][V];
        for (int[] row : adjMatrix) Arrays.fill(row, INF);
        for (int i = 0; i < V; i++) adjMatrix[i][i] = 0;

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            int w = Integer.parseInt(parts[2]);
            adjMatrix[u][v] = Math.min(adjMatrix[u][v], w); // handle multiple edges
        }

        br.close();
    }

    public int size() {
        return V;
    }

    // Floyd-Warshall Algorithm
    public void floydWarshall(int[][] costs,int v , int[][] predecessors , boolean[] negativeCycle) {
        V = v;
        for(int[] arr : costs) Arrays.fill(arr, INF);
        for(int[] arr : predecessors) Arrays.fill(arr, -1);
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if(i==j)
                {
                    costs[i][j] = 0;
                }
                else if (adjMatrix[i][j] == 0)
                {
                    costs[i][j] = INF;
                }
                else
                {
                    costs[i][j] = adjMatrix[i][j];
                }
                predecessors[i][j] = (i != j && adjMatrix[i][j] < INF) ? i : -1;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {

                for (int j = 0; j < V; j++) {
                    if (costs[i][k] + costs[k][j] < costs[i][j] && costs[i][k] != INF && costs[k][j] != INF) {
                        costs[i][j] = costs[i][k] + costs[k][j];
                        predecessors[i][j] = predecessors[k][j];
                    }
                }
            }
        }

        // Check for negative cycles
        for (int i = 0; i < V; i++) {
            if (costs[i][i] < 0) {
                negativeCycle[0] = true;
            }
        }
    }

    public void setAdjacencyMatrix(int[][] matrix) {
        this.adjMatrix = matrix;
    }

    // Optional helper to reconstruct path from i to j
    public List<Integer> reconstructPath(int[][] predecessors, int i, int j) {
        List<Integer> path = new ArrayList<>();
        if (predecessors[i][j] == -1) return path;
        while (j != i) {
            path.add(j);
            j = predecessors[i][j];
        }
        path.add(i);
        Collections.reverse(path);
        return path;
    }
}