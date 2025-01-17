package de.thab.algo.functionTwo;

import de.thab.algo.abstractdatastructures.Graph;
import de.thab.algo.graphreader.GraphReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code InfrastructureNetworkHelper} class is a utility for managing an infrastructure network.
 * It reads a directed weighted graph from a file, initializes nodes representing infrastructure elements,
 * allows users to set priority nodes, and calculates shortest paths using Dijkstra's algorithm.
 */
public class EvacuationHelper {

    /**
     * Enum representing different types of infrastructure nodes
     */
    private enum NodeType {
        RESCUE_STATION,
        HOSPITAL,
        GOVERNMENT_BUILDING,
        STANDARD_NODE
    }

    /**
     * Class representing an infrastructure node with additional metadata
     */
    public static class InfrastructureNode {
        String name;
        NodeType type;
        boolean isPriority;

        public InfrastructureNode(String name) {
            this.name = name;
            this.type = NodeType.STANDARD_NODE;
            this.isPriority = false;
        }
    }

    public static void myFunctionTwo(Scanner input) {
        String filePath = "src/main/resources/graph_directed_weighted.txt";

        try {
            Object[] graphData = GraphReader.readFromFileWithNamesDijkstra(filePath);
            Graph graph = (Graph) graphData[0];
            String[] vertexNames = (String[]) graphData[1];

            InfrastructureNode[] infrastructureNodes = initializeInfrastructureNodes(vertexNames);

            System.out.println("\nInitial Graph Structure:");
            graph.printGraph();

            setPriorityNodeFromUserInput(infrastructureNodes, input);

            displayResults(graph, infrastructureNodes);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static InfrastructureNode[] initializeInfrastructureNodes(String[] nodeNames) {
        InfrastructureNode[] nodes = new InfrastructureNode[nodeNames.length];
        for (int i = 0; i < nodeNames.length; i++) {
            nodes[i] = new InfrastructureNode(nodeNames[i]);
        }
        return nodes;
    }

    /**
     * Displays the results of Dijkstra's algorithm calculation from the priority node
     */
    private static void displayResults(Graph graph, InfrastructureNode[] infrastructureNodes) {
        System.out.println("\nCalculating Shortest Paths from Priority Node:");
        int startNode = findFirstPriorityNode(infrastructureNodes);
        graph.dijkstra(startNode);
        printPriorityNodesInfo(infrastructureNodes);
    }

    private static int findFirstPriorityNode(InfrastructureNode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isPriority) {
                return i;
            }
        }
        return 0; // Default to 0 if no priority node is found
    }

    private static void setPriorityNodeFromUserInput(InfrastructureNode[] nodes, Scanner scanner) {
        System.out.println("\nAvailable Nodes:");
        for (int i = 0; i < nodes.length; i++) {
            System.out.println((i + 1) + ". " + nodes[i].name);
        }

        System.out.println("\nSelect Priority Node (Starting point for shortest path calculation):");

        int input = scanner.nextInt();
        if (input > 0 && input <= nodes.length) {
            int actualIndex = input - 1;
            nodes[actualIndex].isPriority = true;
            System.out.println("Enter node type for " + nodes[actualIndex].name +
                    " (1: RESCUE_STATION, 2: HOSPITAL, 3: GOVERNMENT_BUILDING):");
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (typeChoice) {
                case 1 -> nodes[actualIndex].type = NodeType.RESCUE_STATION;
                case 2 -> nodes[actualIndex].type = NodeType.HOSPITAL;
                case 3 -> nodes[actualIndex].type = NodeType.GOVERNMENT_BUILDING;
                default -> {
                    System.out.println("Invalid type. Setting as STANDARD_NODE.");
                    nodes[actualIndex].type = NodeType.STANDARD_NODE;
                }
            }
            System.out.println(nodes[actualIndex].name + " marked as priority " + nodes[actualIndex].type);
        } else {
            System.out.println("Invalid node number: " + input + ". Skipping.");
        }
    }

    private static void printPriorityNodesInfo(InfrastructureNode[] nodes) {
        System.out.println("\nPriority Nodes Information:");
        for (InfrastructureNode node : nodes) {
            if (node.isPriority) {
                System.out.println(node.name + " - Type: " + node.type);
            }
        }
    }
}