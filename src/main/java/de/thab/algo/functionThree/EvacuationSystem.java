package de.thab.algo.functionThree;

import de.thab.algo.abstractdatastructures.Graph;
import de.thab.algo.abstractdatastructures.list.ArrayList;
import de.thab.algo.graphreader.GraphReader;

import java.io.IOException;
import java.util.Scanner;

public class EvacuationSystem {
    private EvacuationGraph graph;
    private InfrastructureNode[] infrastructureNodes;
    private String[] nodeNames;

    public void initialize(String filePath) throws IOException {
        Object[] graphData = GraphReader.readFromFileWithNamesDijkstra(filePath);
        Graph originalGraph = (Graph) graphData[0];
        nodeNames = (String[]) graphData[1];

        // Convert original graph to evacuation graph
        graph = new EvacuationGraph(nodeNames.length, nodeNames);
        infrastructureNodes = initializeInfrastructureNodes(nodeNames);

        // Copy edges from original graph to evacuation graph
        // You'll need to implement this based on your Graph class structure
        copyEdgesFromOriginalGraph(originalGraph);
    }

    private void copyEdgesFromOriginalGraph(Graph originalGraph) {
        // Since your original Graph class uses adjacency lists
        for (int i = 0; i < nodeNames.length; i++) {
            ArrayList<Graph.Node> originalAdjList = originalGraph.getAdjacentNodes(i);
            if (originalAdjList != null) {
                for (int j = 0; j < originalAdjList.size(); j++) {
                    Graph.Node originalNode = originalAdjList.get(j);
                    graph.addEdge(i, originalNode.getDest(), originalNode.getWeight());
                }
            }
        }
    }

    private InfrastructureNode[] initializeInfrastructureNodes(String[] nodeNames) {
        InfrastructureNode[] nodes = new InfrastructureNode[nodeNames.length];
        for (int i = 0; i < nodeNames.length; i++) {
            nodes[i] = new InfrastructureNode(nodeNames[i]);
        }
        return nodes;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> handleBlockRoad(scanner);
                    case 2 -> handleUnblockRoad(scanner);
                    case 3 -> handleSetPriorityNode(scanner);
                    case 4 -> handleCalculateRoutes();
                    case 5 -> graph.printNetwork();
                    case 6 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nEvacuation System Menu:");
        System.out.println("1. Block Road");
        System.out.println("2. Unblock Road");
        System.out.println("3. Set Priority Node");
        System.out.println("4. Calculate Evacuation Routes");
        System.out.println("5. Display Network");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleBlockRoad(Scanner scanner) {
        System.out.println("\nEnter source node number (1-" + nodeNames.length + "): ");
        int src = scanner.nextInt() - 1;
        System.out.println("Enter destination node number (1-" + nodeNames.length + "): ");
        int dest = scanner.nextInt() - 1;

        if (graph.blockRoad(src, dest)) {
            System.out.println("Road blocked successfully!");
        } else {
            System.out.println("Failed to block road. Please check node numbers.");
        }
    }

    private void handleUnblockRoad(Scanner scanner) {
        System.out.println("\nEnter source node number (1-" + nodeNames.length + "): ");
        int src = scanner.nextInt() - 1;
        System.out.println("Enter destination node number (1-" + nodeNames.length + "): ");
        int dest = scanner.nextInt() - 1;

        if (graph.unblockRoad(src, dest)) {
            System.out.println("Road unblocked successfully!");
        } else {
            System.out.println("Failed to unblock road. Please check node numbers.");
        }
    }

    private void handleSetPriorityNode(Scanner scanner) {
        System.out.println("\nAvailable Nodes:");
        for (int i = 0; i < nodeNames.length; i++) {
            System.out.println((i + 1) + ". " + nodeNames[i]);
        }

        System.out.println("\nSelect Priority Node (1-" + nodeNames.length + "): ");
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (input > 0 && input <= nodeNames.length) {
            int idx = input - 1;
            infrastructureNodes[idx].setPriority(true);

            System.out.println("Select node type:");
            System.out.println("1. Rescue Station");
            System.out.println("2. Hospital");
            System.out.println("3. Government Building");

            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            NodeType type = switch (typeChoice) {
                case 1 -> NodeType.RESCUE_STATION;
                case 2 -> NodeType.HOSPITAL;
                case 3 -> NodeType.GOVERNMENT_BUILDING;
                default -> NodeType.STANDARD_NODE;
            };

            infrastructureNodes[idx].setType(type);
            System.out.println(nodeNames[idx] + " marked as priority " + type);
        } else {
            System.out.println("Invalid node number!");
        }
    }

    private void handleCalculateRoutes() {
        int startNode = findFirstPriorityNode();
        if (startNode != -1) {
            graph.calculateEvacuationRoutes(startNode);
        } else {
            System.out.println("No priority node set. Please set a priority node first.");
        }
    }

    private int findFirstPriorityNode() {
        for (int i = 0; i < infrastructureNodes.length; i++) {
            if (infrastructureNodes[i].isPriority()) {
                return i;
            }
        }
        return -1;
    }
}
