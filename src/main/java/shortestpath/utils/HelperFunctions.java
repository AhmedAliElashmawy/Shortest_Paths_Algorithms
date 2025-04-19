package shortestpath.utils;

import java.util.*;
import shortestpath.utils.Edge;
public class HelperFunctions{

    private static final double NANO_TO_MILLI = 1e6;

    // Helper method to measure time taken by sorting algorithms
    public static Double measureExecutionTime(Runnable sortingFunction) {
        long startTime = System.nanoTime();
        sortingFunction.run();
        return (System.nanoTime() - startTime)/NANO_TO_MILLI;
    }

    // Convert the List<List<Integer>> to List<List<Edge>>
    public static List<List<Edge>> buildAdjList(List<List<Integer>> edgeList)
    {
        List<List<Edge>> adjList = new ArrayList<>();

        for (List<Integer> edge : edgeList)
        {
            int source = edge.get(0);
            int destination = edge.get(1);
            int weight = edge.get(2);

            int maxIndex = Math.max(source, destination);
            while (maxIndex >= adjList.size())
            {
                adjList.add(new ArrayList<>());
            }

            adjList.get(source).add(new Edge(destination, weight));
        }
        return adjList;
    }


    // Convert the List<List<Integer>> to List<Edge>
    public static List<Edge> buildEdgeList(List<List<Integer>> edgeList)
    {

        List<Edge> formatedEdgeList = new ArrayList<>();

        for(List<Integer> edge : edgeList)
        {
            int source = edge.get(0);
            int destination = edge.get(1);
            int weight = edge.get(2);
            formatedEdgeList.add(new Edge(source, destination, weight));
        }


        return formatedEdgeList;
    }

    // Convert the List<List<Integer>> to an adjacency matrix
    public static int[][] buildAdjacencyMatrix(List<List<Integer>> edgeList)
    {
        int numVertices = 0;


        // Determine the number of vertices
        for(List<Integer> edge : edgeList)
        {
            int source = edge.get(0);
            int destination = edge.get(1);
            numVertices = Math.max(numVertices, Math.max(source, destination) + 1);
        }

        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        for(List<Integer> edge : edgeList)
        {
            int source = edge.get(0);
            int destination = edge.get(1);
            int weight = edge.get(2);
            adjacencyMatrix[source][destination] = weight;
        }

        return adjacencyMatrix;
    }

}

