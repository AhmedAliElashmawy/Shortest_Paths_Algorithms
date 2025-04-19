package shortestpath;


import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import shortestpath.utils.HelperFunctions;
import shortestpath.algorithms.*;
import shortestpath.utils.Edge;










public class ShortestPathsTest {

    private Dijkstra dijkstra = new Dijkstra();
    private BellmanFord bellmanFord = new BellmanFord();
    private FloydWarshall floydWarshall = new FloydWarshall();



    private static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow









    @Test
    public void testShortestPathBetweenTwoNodesInASmallGraph()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, 2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));




        int source = 0 , destination = 3 , nodes = 4;

        int expectedCost = 6;


        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);


        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(expectedCost, dijkstraCosts[destination]);
        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);
    }


    @Test
    public void testShortestPathBetweenTwoNodesInAGraphWithNegativeWeights()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, -2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));

        int source = 0 , destination = 3 , nodes = 4;
        int expectedCost = 2;



        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal


        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);
    }

    @Test
    public void testShortestPathBetweenTwoNodesInAGraphWithNegativeCycle()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, -2));
        edgeList.add(List.of(3, 1, -1));
        edgeList.add(List.of(2, 3, -5));


        int source = 0 , destination = 3 , nodes = 4;
        boolean negativeCycle = true;



        // Build Input for each method
        // List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);


        // Dijkstra
        // boolean[] negativeCycleDijkstra = {false};
        // int[] dijkstraCosts = new int[nodes];
        // int[] dijkstraParents = new int[nodes];
        // dijkstra.setAdjacencyList(adjList);
        // Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents , negativeCycleDijkstra));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        // System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(negativeCycle, negativeCycleBellmanFord[0]);
        assertEquals(negativeCycle, negativeCycleFloydWarshall[0]);


    }


    @Test
    public void testShortestPathBetweenTwoNodesInALargeGraph()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, 2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));
        edgeList.add(List.of(3, 4, 1));
        edgeList.add(List.of(4, 5, 2));
        edgeList.add(List.of(5, 6, 3));
        edgeList.add(List.of(6, 7, 4));
        edgeList.add(List.of(7, 8, 5));
        edgeList.add(List.of(8, 9, 6));
        edgeList.add(List.of(9, 10, 7));
        edgeList.add(List.of(10, 11, 8));
        edgeList.add(List.of(11, 12, 9));
        edgeList.add(List.of(12, 13, 10));
        edgeList.add(List.of(13, 14, 11));
        edgeList.add(List.of(14, 15, 12));
        edgeList.add(List.of(15, 16, 13));
        edgeList.add(List.of(16, 17, 14));
        edgeList.add(List.of(17, 18, 15));
        edgeList.add(List.of(18, 19, 16));
        edgeList.add(List.of(19, 20, 17));
        edgeList.add(List.of(20, 21, 18));
        edgeList.add(List.of(21, 22, 19));
        edgeList.add(List.of(22, 23, 20));
        edgeList.add(List.of(23, 24, 21));


        int source = 0 , destination = 24 , nodes = 25;

        int expectedCost = 237;


        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(expectedCost, dijkstraCosts[destination]);
        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);

    }


    @Test
    public void testShortestPathBetweenTwoNodesInALargeGraphWithNegativeWeights()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, -2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));
        edgeList.add(List.of(3, 4, -1));
        edgeList.add(List.of(4, 5, -2));
        edgeList.add(List.of(5, 6, -3));
        edgeList.add(List.of(6, 7, -4));
        edgeList.add(List.of(7, 8, -5));
        edgeList.add(List.of(8, 9, -6));
        edgeList.add(List.of(9, 10, -7));
        edgeList.add(List.of(10, 11, -8));
        edgeList.add(List.of(11, 12, -9));
        edgeList.add(List.of(12, 13, -10));
        edgeList.add(List.of(13, 14, -11));
        edgeList.add(List.of(14, 15, -12));
        edgeList.add(List.of(15, 16, -13));
        edgeList.add(List.of(16, 17, -14));
        edgeList.add(List.of(17, 18, -15));
        edgeList.add(List.of(18, 19, -16));
        edgeList.add(List.of(19, 20, -17));
        edgeList.add(List.of(20, 21, -18));
        edgeList.add(List.of(21, 22, -19));
        edgeList.add(List.of(22, 23, -20));
        edgeList.add(List.of(23, 24 , -21));
        int source = 0 , destination = 24 , nodes = 25;

        int expectedCost = -229;



        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);

    }


    @Test
    public void testShortestPathBetweenTwoNodesInABinaryTree()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 3, 2));
        edgeList.add(List.of(1, 4, 6));
        edgeList.add(List.of(2, 5, 3));
        edgeList.add(List.of(2, 6, 5));

        int source = 0 , destination = 6 , nodes = 7;

        int expectedCost = 9;


        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(expectedCost, dijkstraCosts[destination]);
        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);

    }


    @Test
    public void testShortestPathBetweenTwoNodesInABinaryTreeWithNegativeWeights()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 3, -2));
        edgeList.add(List.of(1, 4, 6));
        edgeList.add(List.of(2, 5, 3));
        edgeList.add(List.of(2, 6, -5));

        int source = 0 , destination = 6 , nodes = 7;

        int expectedCost = -1;

        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[] dijkstraCosts = new int[nodes];
        int[] dijkstraParents = new int[nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> dijkstra.dijkstra(source, dijkstraCosts, dijkstraParents));

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[] bellmanFordCosts = new int[nodes];
        int[] bellmanFordParents = new int[nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> bellmanFord.Bellman_Ford(formatedEdgeList, nodes, source, bellmanFordCosts, bellmanFordParents , negativeCycleBellmanFord));


        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int [][] floydWarshallCosts = new int[nodes][nodes];
        int [][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors , negativeCycleFloydWarshall));




        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");
        // Check if the costs are equal

        assertEquals(expectedCost, bellmanFordCosts[destination]);
        assertEquals(expectedCost, floydWarshallCosts[source][destination]);

    }


    @Test
    public void testShortestPathBetweenAllPairsOfNodesInASmallGraph()
    {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, 2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));

        int source = 0 , destination = 3 , nodes = 4;

        int[][] expectedCosts = {
                {0, 1, 3, 6},
                {INF, 0, 2, 5},
                {INF, INF, 0, 3},
                {INF, INF, INF, 0}
        };

        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));



        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");


        // Check if the costs are equal
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                assertEquals(expectedCosts[i][j], dijkstraCosts[i][j]);
                assertEquals(expectedCosts[i][j], bellmanFordCosts[i][j]);
                assertEquals(expectedCosts[i][j], floydWarshallCosts[i][j]);
            }
        }

    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInAGraphWithNegativeWeights() {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, -2));
        edgeList.add(List.of(1, 3, 6));
        edgeList.add(List.of(2, 3, 3));

        int nodes = 4;

        int[][] expectedCosts = {
            {0, 1, -1, 2},
            {INF, 0, -2, 1},
            {INF, INF, 0, 3},
            {INF, INF, INF, 0}
        };

        // Build Input for each method
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        // Print the results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        // Check if the costs are equal
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                assertEquals(expectedCosts[i][j], bellmanFordCosts[i][j]);
                assertEquals(expectedCosts[i][j], floydWarshallCosts[i][j]);
            }
        }
    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInAGraphWithNegativeCycle() {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 2, -2));
        edgeList.add(List.of(3, 1, -1));
        edgeList.add(List.of(2, 3, -5));

        int nodes = 4;
        boolean negativeCycle = true;

        // Build Input for each method
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        // Print results
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        assertEquals(negativeCycle, negativeCycleBellmanFord[0]);
        assertEquals(negativeCycle, negativeCycleFloydWarshall[0]);
    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInALargeGraph() {
        List<List<Integer>> edgeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            edgeList.add(List.of(i, i+1, i+1));
        }

        int nodes = 25;

        // Build inputs
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        // Print results and verify costs match
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                if (i <= j) {
                    int expectedCost = 0;
                    for (int k = i; k < j; k++) {
                        expectedCost += k + 1;
                    }
                    assertEquals(expectedCost, dijkstraCosts[i][j]);
                    assertEquals(expectedCost, bellmanFordCosts[i][j]);
                    assertEquals(expectedCost, floydWarshallCosts[i][j]);
                }
            }
        }
    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInALargeGraphWithNegativeWeights() {
        List<List<Integer>> edgeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            edgeList.add(List.of(i, i+1, -(i+1)));
        }

        int nodes = 25;

        // Build inputs
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        // Print results and verify costs match
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                if (i <= j) {
                    int expectedCost = 0;
                    for (int k = i; k < j; k++) {
                        expectedCost += -(k + 1);
                    }
                    assertEquals(expectedCost, bellmanFordCosts[i][j]);
                    assertEquals(expectedCost, floydWarshallCosts[i][j]);
                }
            }
        }
    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInABinaryTree() {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 3, 2));
        edgeList.add(List.of(1, 4, 6));
        edgeList.add(List.of(2, 5, 3));
        edgeList.add(List.of(2, 6, 5));

        int nodes = 7;

        int[][] expectedCosts = new int[nodes][nodes];
        for (int i = 0; i < nodes; i++) {
            Arrays.fill(expectedCosts[i], INF);
            expectedCosts[i][i] = 0;
        }
        expectedCosts[0][1] = 1;
        expectedCosts[0][2] = 4;
        expectedCosts[0][3] = 3;
        expectedCosts[0][4] = 7;
        expectedCosts[0][5] = 7;
        expectedCosts[0][6] = 9;
        expectedCosts[1][3] = 2;
        expectedCosts[1][4] = 6;
        expectedCosts[2][5] = 3;
        expectedCosts[2][6] = 5;

        // Build inputs
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                assertEquals(expectedCosts[i][j], dijkstraCosts[i][j]);
                assertEquals(expectedCosts[i][j], bellmanFordCosts[i][j]);
                assertEquals(expectedCosts[i][j], floydWarshallCosts[i][j]);
            }
        }
    }

    @Test
    public void testShortestPathBetweenAllPairsOfNodesInABinaryTreeWithNegativeWeights() {
        List<List<Integer>> edgeList = new ArrayList<>();
        edgeList.add(List.of(0, 1, 1));
        edgeList.add(List.of(0, 2, 4));
        edgeList.add(List.of(1, 3, -2));
        edgeList.add(List.of(1, 4, -6));
        edgeList.add(List.of(2, 5, -3));
        edgeList.add(List.of(2, 6, -5));

        int nodes = 7;

        int[][] expectedCosts = new int[nodes][nodes];
        for (int i = 0; i < nodes; i++) {
            Arrays.fill(expectedCosts[i], INF);
            expectedCosts[i][i] = 0;
        }
        expectedCosts[0][1] = 1;
        expectedCosts[0][2] = 4;
        expectedCosts[0][3] = -1;
        expectedCosts[0][4] = -5;
        expectedCosts[0][5] = 1;
        expectedCosts[0][6] = -1;
        expectedCosts[1][3] = -2;
        expectedCosts[1][4] = -6;
        expectedCosts[2][5] = -3;
        expectedCosts[2][6] = -5;

        // Build inputs
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");
        System.err.println("--------------------------------------------------------------");

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                assertEquals(expectedCosts[i][j], bellmanFordCosts[i][j]);
                assertEquals(expectedCosts[i][j], floydWarshallCosts[i][j]);
            }
        }
    }

    @Test
    public void testShortestPathBetweenAllPairsInAMassiveBinaryTree() {
        List<List<Integer>> edgeList = new ArrayList<>();
        int nodes = 1000;
        for (int i = 0; i < 999; i++) {
            edgeList.add(List.of(i, i+1, 1));
        }



        // Build inputs
        List<List<Edge>> adjList = HelperFunctions.buildAdjList(edgeList);
        List<Edge> formatedEdgeList = HelperFunctions.buildEdgeList(edgeList);
        int[][] adjacencyMatrix = HelperFunctions.buildAdjacencyMatrix(edgeList);

        // Dijkstra
        int[][] dijkstraCosts = new int[nodes][nodes];
        int[][] dijkstraParents = new int[nodes][nodes];
        dijkstra.setAdjacencyList(adjList);
        Double dijkstraTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                dijkstra.dijkstra(i, dijkstraCosts[i], dijkstraParents[i]);
            }
        });

        // Bellman-Ford
        boolean[] negativeCycleBellmanFord = {false};
        int[][] bellmanFordCosts = new int[nodes][nodes];
        int[][] bellmanFordParents = new int[nodes][nodes];
        Double bellmanFordTime = HelperFunctions.measureExecutionTime(() -> {
            for (int i = 0; i < nodes; i++) {
                bellmanFord.Bellman_Ford(formatedEdgeList, nodes, i, bellmanFordCosts[i], bellmanFordParents[i], negativeCycleBellmanFord);
            }
        });

        // Floyd-Warshall
        boolean[] negativeCycleFloydWarshall = {false};
        int[][] floydWarshallCosts = new int[nodes][nodes];
        int[][] floydWarshallPredecessors = new int[nodes][nodes];
        floydWarshall.setAdjacencyMatrix(adjacencyMatrix);
        Double floydWarshallTime = HelperFunctions.measureExecutionTime(() -> floydWarshall.floydWarshall(floydWarshallCosts, nodes, floydWarshallPredecessors, negativeCycleFloydWarshall));

        // Print results and verify costs match
        System.err.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.err.println("Dijkstra time: " + dijkstraTime + "ms");
        System.err.println("Bellman-Ford time: " + bellmanFordTime + "ms");
        System.err.println("Floyd-Warshall time: " + floydWarshallTime + "ms");

        System.err.println("--------------------------------------------------------------");


        // Check if the costs are equal
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                if (i <= j) {
                    int expectedCost = 0;
                    for (int k = i; k < j; k++) {
                        expectedCost += 1;
                    }
                    assertEquals(expectedCost, dijkstraCosts[i][j]);
                    assertEquals(expectedCost, bellmanFordCosts[i][j]);
                    assertEquals(expectedCost, floydWarshallCosts[i][j]);
                }
            }
        }

    }







}
