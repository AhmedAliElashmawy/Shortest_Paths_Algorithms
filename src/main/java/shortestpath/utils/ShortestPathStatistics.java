package shortestpath.utils;
import shortestpath.utils.ExcelFileHandler;
import shortestpath.algorithms.*;
import shortestpath.utils.HelperFunctions;

import java.util.*;
import java.util.stream.DoubleStream;


public class ShortestPathStatistics {
    private static final int DATA_POINTS = 40;
    private static final int MIN_NODES = 10;
    private static final int MAX_NODES = 1000;
    private static final double NANO_TO_MILLI = 1e6;

    private Dijkstra dijkstra = new Dijkstra();
    private BellmanFord bellmanFord = new BellmanFord();
    private FloydWarshall floydWarshall = new FloydWarshall();




    private static List<List<Integer>> generateRandomEdgeList(int nodeCount, int edgeCount) {
        Random random = new Random();
        List<List<Integer>> edgeList = new ArrayList<>();

        for (int i = 0; i < edgeCount; i++) {
            int u = random.nextInt(nodeCount);
            int v = random.nextInt(nodeCount);
            int weight = random.nextInt(200) + 1;
            List<Integer> edge = Arrays.asList(u, v, weight);
            edgeList.add(edge);
        }
        return edgeList;
    }


    public void runStatisticsForOneSourcedShortestPath(){
        Set<Integer> uniquePoints = new LinkedHashSet<>();
        DoubleStream.iterate(MIN_NODES,
                n -> n * Math.pow((double) MAX_NODES / MIN_NODES, 1.0 / (DATA_POINTS - 1)))
            .limit(DATA_POINTS)
            .mapToInt(n -> (int) Math.round(n))
            .forEach(uniquePoints::add);

        int[] points = uniquePoints.stream().mapToInt(Integer::intValue).toArray();

        try {
            ExcelFileHandler pathStatistics = new ExcelFileHandler("Shortest_Path_Statistics");

            // Sets header row labels
            String[] headerRow = {"Number of Nodes", "Dijkstra", "Bellman-Ford", "Floyd-Warshall"};
            pathStatistics.setRow(headerRow);

            for(int i = 0; i < points.length; ++i) {
                int nodeCount = points[i];
                int edgeCount = nodeCount * 2; // You can adjust the edge density
                List<List<Integer>> edgeList = generateRandomEdgeList(nodeCount, edgeCount);
                int source = 0;
                // Clone the graph for each algorithm
                List<List<Edge>> dijkstraGraph = HelperFunctions.buildAdjList(edgeList);
                List<Edge> bellmanGraph = HelperFunctions.buildEdgeList(edgeList);
                int [][] floydGraph = HelperFunctions.buildAdjacencyMatrix(edgeList);

                // Set Up Dijkstra
                dijkstra.setAdjacencyList(dijkstraGraph);
                int[] dijkstraCosts = new int[nodeCount];
                int[] dijkstraParents = new int[nodeCount];
                boolean[] dijkstraNegativeCycle = new boolean[1];
                Double dijkstraTime = HelperFunctions.measureExecutionTime(() ->
                    // Add your Dijkstra implementation here
                    dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents)
                );
                // Set Up Bellman-Ford
                int[] bellmanCosts = new int[nodeCount];
                int[] bellmanParents = new int[nodeCount];
                boolean[] bellmanNegativeCycle = new boolean[1];
                Double bellmanFordTime = HelperFunctions.measureExecutionTime(() ->
                    // Add your Bellman-Ford implementation here
                    bellmanFord.Bellman_Ford(bellmanGraph, nodeCount, source, bellmanCosts, bellmanParents , bellmanNegativeCycle)
                );

                // Set Up Floyd-Warshall
                int[][] floydCosts = new int[nodeCount][nodeCount];
                int[][] floydPredecessors = new int[nodeCount][nodeCount];
                boolean[] floydNegativeCycle = new boolean[1];
                floydWarshall.setAdjacencyMatrix(floydGraph);

                Double floydWarshallTime = HelperFunctions.measureExecutionTime(() ->
                    // Add your Floyd-Warshall implementation here
                    floydWarshall.floydWarshall(floydCosts, nodeCount, floydPredecessors, floydNegativeCycle)
                );

                // Insert the data into the sheet
                Object[] rowData = {(double)nodeCount, dijkstraTime, bellmanFordTime, floydWarshallTime};
                pathStatistics.setRow(rowData);
            }

            pathStatistics.saveToFile();
            System.out.println("Analysis completed. Check generated Excel file.");

        } catch (Exception e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
        }
    }

    public void runStatisticsForAllPairsShortestPath(){
        Set<Integer> uniquePoints = new LinkedHashSet<>();
        DoubleStream.iterate(MIN_NODES,
                n -> n * Math.pow((double) MAX_NODES / MIN_NODES, 1.0 / (DATA_POINTS - 1)))
            .limit(DATA_POINTS)
            .mapToInt(n -> (int) Math.round(n))
            .forEach(uniquePoints::add);

        int[] points = uniquePoints.stream().mapToInt(Integer::intValue).toArray();

        try {
            ExcelFileHandler pathStatistics = new ExcelFileHandler("All_Pairs_Shortest_Path_Statistics");

            // Sets header row labels
            String[] headerRow = {"Number of Nodes", "Dijkstra", "Bellman-Ford", "Floyd-Warshall"};
            pathStatistics.setRow(headerRow);

            for(int i = 0; i < points.length; ++i) {
                int nodeCount = points[i];
                int edgeCount = nodeCount * 2; // You can adjust the edge density
                List<List<Integer>> edgeList = generateRandomEdgeList(nodeCount, edgeCount);

                // Clone the graph for each algorithm
                List<List<Edge>> dijkstraGraph = HelperFunctions.buildAdjList(edgeList);
                List<Edge> bellmanGraph = HelperFunctions.buildEdgeList(edgeList);
                int [][] floydGraph = HelperFunctions.buildAdjacencyMatrix(edgeList);

                // Set Up Dijkstra
                dijkstra.setAdjacencyList(dijkstraGraph);
                int[][] dijkstraCosts = new int[nodeCount][nodeCount];
                int[][] dijkstraParents = new int[nodeCount][nodeCount];
                boolean[] dijkstraNegativeCycle = new boolean[1];
                Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
                    for (int source = 0; source < nodeCount; source++) {
                        dijkstra.dijkstra(source, dijkstraCosts[source], dijkstraParents[source]);
                    }
                });


                // Set Up Bellman-Ford
                int[][] bellmanCosts = new int[nodeCount][nodeCount];
                int[][] bellmanParents = new int[nodeCount][nodeCount];
                boolean[] bellmanNegativeCycle = new boolean[1];
                Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
                    for (int source = 0; source < nodeCount; source++) {
                        bellmanFord.Bellman_Ford(bellmanGraph, nodeCount, source, bellmanCosts[source], bellmanParents[source], bellmanNegativeCycle);
                    }
                });
                // Set Up Floyd-Warshall
                int[][] floydCosts = new int[nodeCount][nodeCount];
                int[][] floydPredecessors = new int[nodeCount][nodeCount];
                boolean[] floydNegativeCycle = new boolean[1];
                floydWarshall.setAdjacencyMatrix(floydGraph);

                Double floydWarshallTime = HelperFunctions.measureExecutionTime(() ->
                    // Add your Floyd-Warshall implementation here
                    floydWarshall.floydWarshall(floydCosts, nodeCount, floydPredecessors, floydNegativeCycle)
                );

                // Insert the data into the sheet
                Object[] rowData = {(double)nodeCount, dijkstraTime, bellmanFordTime, floydWarshallTime};
                pathStatistics.setRow(rowData);
            }

            pathStatistics.saveToFile();
            System.out.println("Analysis completed. Check generated Excel file.");

        } catch (Exception e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
        }
    }
}


