package shortestpath;
import shortestpath.cli.CLI;
import shortestpath.utils.ShortestPathStatistics;

public class Main {
    public static void main(String[] args){


        // Runs the statistics for the shortest path algorithms
        ShortestPathStatistics stats = new ShortestPathStatistics();

        stats.runStatisticsForOneSourcedShortestPath();
        stats.runStatisticsForAllPairsShortestPath();

        
        CLI cli = new CLI();
        cli.mainMenu();
    }
}
