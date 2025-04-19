package shortestpath.cli;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shortestpath.utils.MakeGraphs;

public class CLI {

    MakeGraphs graph;
    public void mainMenu(){
        clearConsole();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome To Shortest Paths Algorithms Program :)");
        String theFile ;


        System.out.println("1. choose existing Graph");
        System.out.println("2. make a Graph");
        System.out.println("3. Exit");
        System.out.print("Please choose an option [1-3]: ");

        int graphChoice = sc.nextInt();

        while (graphChoice > 3 || graphChoice < 1) {
            System.out.println("Wrong input");
            System.out.print("Please choose an option [1-3]: ");
            graphChoice = sc.nextInt();
        }

        if (graphChoice == 3) {
            System.out.println("Goodbye... :(");
            System.exit(0);
        }

        if (graphChoice == 1) {
            sc.nextLine();
            theFile = chooseFile(sc);

        }else{
            theFile = makeAGraphFile(sc);
        }


       try {
            graph = new MakeGraphs(theFile);
        } catch (IOException e) {
            System.out.println("Error initializing MakeGraphs: " + e.getMessage());
            sc.close();
            return;
        }

        do {
            clearConsole();
            System.out.println("------------------------------------------------------");
            System.out.println("SHORTEST PATHS ALGORITHMS");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Single-Source Shortest Paths");
            System.out.println("2. All-Pairs Shortest Paths");
            System.out.println("3. Check for Negative Cycle");
            System.out.println("4. Exist");
            System.out.println("------------------------------------------------------");
            System.out.print("Please choose an option [1-4]: ");

            int yourChoice =  sc.nextInt();


            while (yourChoice > 4 || yourChoice < 1) {
                System.out.println("Wrong input");
                System.out.print("Please choose an option [1-4]: ");
                yourChoice = sc.nextInt();
            }

            if (yourChoice == 4) {
                System.out.println("Goodbye... :(");
                break;
            }else if (yourChoice == 1) {
                signleAndPairsMenu(sc,yourChoice );
            } else if (yourChoice == 2) {
                signleAndPairsMenu(sc,yourChoice );
            } else if (yourChoice == 3) {
                checkNegativeCycle(sc);
            }

        } while (true);
        sc.close();
    }


    public void signleAndPairsMenu(Scanner sc, int type){

        do {
            clearConsole();
            System.out.print("Enter the Source Node [0-"+(graph.size()-1)+"]: ");
            int source = 0;
            if (type == 1) {


            source = sc.nextInt();
            while (source >= graph.size() || source < 0) {
                System.out.println("Wrong input");
                System.out.print("Please choose an option [0-"+(graph.size()-1)+"]: ");
                source = sc.nextInt();
            }
        }
            System.out.println("------------------------------------------------------");
            if (type == 1) {
                System.out.println("SINGLE-SOURCE SHORTEST PATHS");
            }else if (type == 2) {
                System.out.println("ALL-PAIRS SHORTEST PATH");
            }

            System.out.println("------------------------------------------------------");
            System.out.println("1. Dijkstra");
            System.out.println("2. Bellman-Ford");
            System.out.println("3. Floyd-Warshall");
            System.out.println("4. return to main menu");
            System.out.println("------------------------------------------------------");
            System.out.print("Please choose an option [1-4]: ");


            int algoChoice =  sc.nextInt();

            while (algoChoice > 4 || algoChoice < 1) {
                System.out.println("Wrong input");
                System.out.print("Please choose an option [1-4]: ");
                algoChoice = sc.nextInt();
            }


            if (algoChoice == 4) {
                return;
            }else{

            }

            if (type == 1) {
                singleSourceOpreations(algoChoice, source, sc);
            }else if (type == 2) {
                allPairsOpreations(algoChoice, sc);
            }


        } while (true);
    }

    public void allPairsOpreations(int algo, Scanner sc) {
        int V = graph.size();
        int[][] costs = new int[V][V];
        int[][] predecessors = new int[V][V];

        // Initialize costs and predecessors based on the selected algorithm
        if (algo == 1 || algo == 2) { // Dijkstra or Bellman-Ford
            for (int i = 0; i < V; i++) {
                int[] singleSourceCosts = new int[V];
                int[] singleSourceParents = new int[V];
                if (algo == 1) {
                    graph.dijkstra(i, singleSourceCosts, singleSourceParents);
                } else if (algo == 2) {
                    graph.Bellman_Ford(i, singleSourceCosts, singleSourceParents);
                }
                for (int j = 0; j < V; j++) {
                    costs[i][j] = singleSourceCosts[j];
                    predecessors[i][j] = singleSourceParents[j];
                }
            }
        } else if (algo == 3) { // Floyd-Warshall
            if (!graph.floydWarshall(costs, predecessors)) {
                System.out.println("Graph contains a negative weight cycle.");
                return;
            }
        } else {
            System.out.println("Invalid algorithm choice for all-pairs operations.");
            return;
        }

        // Menu for all-pairs operations

            clearConsole();
            System.out.println("------------------------------------------------------");
            System.out.println("ALL-PAIRS PATH OPERATIONS");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Display cost between two nodes");
            System.out.println("2. Display shortest path between two nodes");
            System.out.println("3. Return to main menu");
            System.out.println("------------------------------------------------------");
            do {
            System.out.print("Please choose an option [1-3]: ");

            int choice = sc.nextInt();

            while (choice < 1 || choice > 3) {
                System.out.print("Invalid input. Please choose an option [1-3]: ");
                choice = sc.nextInt();
            }

            if (choice == 3) {
                return; // Exit to main menu
            }

            System.out.print("Enter the source node (0 to " + (V - 1) + "): ");
            int source = sc.nextInt();
            System.out.print("Enter the target node (0 to " + (V - 1) + "): ");
            int target = sc.nextInt();

            while (source < 0 || source >= V || target < 0 || target >= V) {
                System.out.println("Invalid input. Enter valid nodes (0 to " + (V - 1) + "): ");
                System.out.print("Enter the source node: ");
                source = sc.nextInt();
                System.out.print("Enter the target node: ");
                target = sc.nextInt();
            }

            if (choice == 1) {
                // Display cost between two nodes
                if (costs[source][target] == Integer.MAX_VALUE) {
                    System.out.println("No path exists between " + source + " and " + target + ".");
                } else {
                    System.out.println("Cost from " + source + " to " + target + " is: " + costs[source][target]);
                }
            } else if (choice == 2) {
                // Display shortest path between two nodes
                System.out.println("Shortest path from " + source + " to " + target + ":");
                if (costs[source][target] == Integer.MAX_VALUE) {
                    System.out.println("No path exists.");
                } else {
                    printPathFloyd(source, target, predecessors);
                    System.out.println(); // Newline for better formatting
                }
            }
        } while (true);
    }


    public void checkNegativeCycle(Scanner sc) {
        clearConsole();
        System.out.println("------------------------------------------------------");
        System.out.println("CHECK FOR NEGATIVE CYCLE");
        System.out.println("------------------------------------------------------");
        System.out.println("1. Bellman-Ford");
        System.out.println("2. Floyd-Warshall");
        System.out.println("3. Return to main menu");
        System.out.println("------------------------------------------------------");
        do {


        System.out.print("Please choose an option [1-3]: ");

        int algoChoice = sc.nextInt();

        while (algoChoice < 1 || algoChoice > 3) {
            System.out.print("Invalid input. Please choose an option [1-3]: ");
            algoChoice = sc.nextInt();
        }

        if (algoChoice == 3) {
            return; // Exit to main menu
        }

        if (algoChoice == 1) {
            // Check for negative cycle using Bellman-Ford
            int V = graph.size();
            int[] costs = new int[V];
            int[] parents = new int[V];

            boolean flag = false;
            for (int i = 0; i < V; i++) {
                graph.Bellman_Ford(i, costs, parents);

                // Check for negative weight cycle
                for (MakeGraphs.Edge edge : graph.getEdges()) { // Assuming `getEdges()` returns the list of edges
                    if (costs[edge.source] != Integer.MAX_VALUE && costs[edge.source] + edge.weight < costs[edge.destination]) {
                        System.out.println("Graph contains a negative weight cycle.");
                        flag = true;
                        break;
                    }
                }
                if (flag ) {
                    break;
                }
            }
            if (flag) {
                continue;
            }
            System.out.println("No negative weight cycle detected.");
        } else if (algoChoice == 2) {
            // Check for negative cycle using Floyd-Warshall
            int V = graph.size();
            int[][] costs = new int[V][V];
            int[][] predecessors = new int[V][V];


            if (!graph.floydWarshall(costs, predecessors)) {
                System.out.println("Graph contains a negative weight cycle.");
            } else {
                System.out.println("No negative weight cycle detected.");
            }
        }
        } while (true);
    }
    public void singleSourceOpreations(int algo, int source, Scanner sc){
        int V = graph.size();
        int[] costs = new int[V];
        int[] parents = new int[V];
        int[][] costsFloyd = new int[V][V];
        int[][] parentsFloyd = new int[V][V];

        if (algo == 1) {
            graph.dijkstra(source, costs, parents);
        }else if(algo == 2){
            graph.Bellman_Ford(source, costs, parents);
        }else if (algo == 3) {
            graph.floydWarshall(costsFloyd, parentsFloyd);
        }else{
            System.out.println("this should not to happen!! , so i will write anything here \nclose it man NOW!!");
        }


            clearConsole();
            System.out.println("------------------------------------------------------");
            System.out.println("SINGLE-SOURCE PATH OPERATIONS");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Display cost to a specific node");
            System.out.println("2. Display shortest path to a specific node");
            System.out.println("3. return back");
            System.out.println("------------------------------------------------------");

            do {
                System.out.print("Please choose an opreation [1-3]: ");
            int yourChoice =  sc.nextInt();

            while (yourChoice > 3 || yourChoice < 1) {
                System.out.println("Wrong input");
                System.out.print("Please choose an opreation [1-3]: ");
                yourChoice = sc.nextInt();
            }


            if (yourChoice == 3) {
                return;
            }
            System.out.print("Enter the target from " + 0 + " to " + (graph.size() - 1) +": ");
            int targetNode = sc.nextInt();

            while (targetNode >= V || targetNode < 0) {
                System.out.println("Wrong input");
                System.out.print("Please choose an option [1-"+(graph.size()-1)+"]: ");
                targetNode = sc.nextInt();
            }

            if (yourChoice == 1) {
                if (algo == 3) {
                    System.out.println("Cost from " + source + " to " + targetNode + " is: " + costsFloyd[source][targetNode]);
                } else {
                    System.out.println("Cost from " + source + " to " + targetNode + " is: " + costs[targetNode]);
                }
            } else {
                System.out.println("Shortest path from " + source + " to " + targetNode + ":");
                if (algo == 3) {
                    printPathFloyd(source, targetNode, parentsFloyd);
                } else {
                    printPath(source, targetNode, parents);

                }
            }
            System.out.println();
        } while (true);


    }




    private void printPath(int source, int target, int[] parents) {
        if (target == -1 || parents[target] == -1) {
            System.out.println("No path exists.");
            return;
        }

        if (source == target) {
            System.out.print(source);
            return;
        }

        printPath(source, parents[target], parents);
        System.out.print(" -> " + target);
    }
    private void printPathFloyd(int source, int target, int[][] predecessors) {
        if (source == target) {
            System.out.print(source);
            return;
        }

        if (predecessors[source][target] == -1) {
            System.out.println("No path exists.");
            return;
        }

        // Recursively print the path
        printPathFloyd(source, predecessors[source][target], predecessors);
        System.out.print(" -> " + target);
    }

    public String chooseFile(Scanner sc) {
        while (true) {
            System.out.print("Enter the file name (in the current directory) or full path: ");
            String inputPath = sc.nextLine().trim();

            File file = new File(inputPath);

            // Resolve to absolute if relative
            if (!file.isAbsolute()) {
                file = new File(System.getProperty("user.dir"), inputPath);
            }

            try {
                file = file.getCanonicalFile(); // Resolve symbolic links and normalize
            } catch (IOException e) {
                System.out.println("Error resolving file path: " + e.getMessage());
                continue;
            }

            if (file.exists() && file.isFile()) {
                return file.getAbsolutePath();
            } else {
                System.out.println("File not found. Try again.");
            }
        }
    }



    public String makeAGraphFile(Scanner sc){

        System.out.print("Enter number of vertices: ");
        int V = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        List<String> edges = new ArrayList<>();
        for(int i = 0 ; i < E ; i++){
            System.out.println("Enter the "+(i+1)+" edge : line by line, from - to - weight");
            System.out.print("from: ");
            int from =  sc.nextInt();
            System.out.print("to: ");
            int to = sc.nextInt();
            System.out.print("weight: ");
            int weight = sc.nextInt();

            edges.add(from+" "+to+" "+weight);
        }

        System.out.print("Enter file name to save the Graph: ");
        sc.nextLine();
        String savedFileName = sc.nextLine();

        try(PrintWriter writer = new PrintWriter(new FileWriter(savedFileName))){
            writer.println(V+" "+E);

            for (String edge : edges) {
                writer.println(edge);
            }
            System.out.println("Graph file saved successfully as " + savedFileName);
        }catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return null;
        }


        return savedFileName;
    }

    public void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error while clearing console: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CLI commandLineInterface = new CLI();
        commandLineInterface.mainMenu();
    }
}
