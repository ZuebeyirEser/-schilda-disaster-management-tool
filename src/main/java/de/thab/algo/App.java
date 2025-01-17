package de.thab.algo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import de.thab.algo.abstractdatastructures.CustomHashMap;
import de.thab.algo.abstractdatastructures.CustomHashSet;
import de.thab.algo.abstractdatastructures.CustomMap;
import de.thab.algo.abstractdatastructures.CustomSet;
import de.thab.algo.abstractdatastructures.Graph;
import de.thab.algo.functionFive.FunctionFiveHelper;
import de.thab.algo.functionFour.ClusteringHelper;
import de.thab.algo.functionOne.InfrastructureNetworkHelper;
import de.thab.algo.functionThree.Main;
import de.thab.algo.functionTwo.EvacuationHelper;
import de.thab.algo.graphreader.GraphReader;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize a sample graph for demonstration
        Graph graphMock = new Graph(5, new String[]{"A", "B", "C", "D", "E"});
        graphMock.addEdge(0, 1, 10);
        graphMock.addEdge(0, 2, 20);
        graphMock.addEdge(1, 2, 5);
        graphMock.addEdge(3, 4, 15);

        //Initialize Graph
        String filePath = "src/main/resources/graph_directed_weighted.txt";

        try{
            Object[] graphData = GraphReader.readFromFileWithNames(filePath);
            Graph graph = (Graph) graphData[0];
            String[] vertexNames = (String[]) graphData[1];

            while (true) {
                System.out.println("\nSelect a function:");
                System.out.println("1. Rebuild communication infrastructure");
                System.out.println("2. Plan evacuation routes");
                System.out.println("3. Plan routes for emergency services");
                System.out.println("4. Set up supply points ");
                System.out.println("5. Deployment planning for emergency services");
                System.out.println("0. Exit");
    
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                switch (choice) {
                    case 1 -> performFunctionOne(graph, scanner);
                    case 2 -> performFunctionTwo(graph, scanner);
                    case 3 -> performFunctionThree(graph, scanner);
                    case 4 -> performFunctionFour(graph, scanner);
                    case 5 -> performFunctionFive(scanner);
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void performFunctionOne(Graph graph, Scanner scanner) {
        System.out.println("=== Function One: Rebuild communication infrastructure ===");
        InfrastructureNetworkHelper infrastructureFunction = new InfrastructureNetworkHelper();
        infrastructureFunction.main(null);
    }

    private static void performFunctionTwo(Graph graph, Scanner scanner) {
        System.out.println("=== Function Two: Plan evacuation routes ===");
        EvacuationHelper evacuationFunction = new EvacuationHelper();
        evacuationFunction.main(null);
    }

    private static void performFunctionThree(Graph graph, Scanner scanner) {
        try {
            System.out.println("=== Function Four: Plan routes for emergency services ===");
            Main function = new Main();
            function.main(null);
        } catch (IOException ex) {
        }
    }

    private static void performFunctionFour(Graph graph, Scanner scanner) {
        System.out.println("=== Function Four: Set Up Supply Points ===");

        System.out.print("Enter the number of clusters (k): ");
        int k = scanner.nextInt();

        System.out.println("Running K-Medoids clustering...");
        ClusteringHelper clusteringHelper = new ClusteringHelper(graph);

        CustomHashMap<Integer, List<Integer>> clusters = clusteringHelper.kMedoids(k, 1000);
        clusteringHelper.displayClusters(clusters);
    }

    private static void performFunctionFive(Scanner scanner) {
        System.out.println("=== Function Five: Allocate Resources to Nodes ===");
    
        System.out.print("Enter the number of teams: ");
        int teamCount = scanner.nextInt();
        scanner.nextLine();
    
        System.out.println("Enter skills of each team (comma-separated):");
        List<CustomSet<String>> teamSkills = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) {
            System.out.print("Team " + (i + 1) + ": ");
            String[] skills = scanner.nextLine().split(",");
            teamSkills.add(new CustomHashSet<>(Arrays.asList(skills)));
        }
    
        System.out.print("Enter the number of nodes: ");
        int nodeCount = scanner.nextInt();
        scanner.nextLine();
    
        System.out.println("Enter requirements of each node (comma-separated):");
        List<CustomSet<String>> nodeRequirements = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            System.out.print("Node " + (i + 1) + ": ");
            String[] requirements = scanner.nextLine().split(",");
            nodeRequirements.add(new CustomHashSet<>(Arrays.asList(requirements)));
        }
    
        // Initialize FunctionFiveHelper
        FunctionFiveHelper helper = new FunctionFiveHelper(teamCount, nodeCount);
    
        // Build the bipartite graph
        helper.buildGraph(teamSkills, nodeRequirements);
    
        // Calculate max flow
        int maxFlow = helper.maxFlow();
        System.out.println("Maximum number of functional nodes: " + maxFlow);
    
        // Get and display the allocation
        CustomHashMap<Integer, Integer> allocation = helper.getAllocation();
        System.out.println("Resource Allocation:");
        for (CustomMap.Entry<Integer, Integer> entry : allocation.entrySet()) {
            System.out.println("Team " + entry.getKey() + " -> Node " + entry.getValue());
        }
    }


     
}
