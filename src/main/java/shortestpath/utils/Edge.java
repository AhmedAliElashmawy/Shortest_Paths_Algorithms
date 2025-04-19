package shortestpath.utils;

public class Edge {
    public int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}
