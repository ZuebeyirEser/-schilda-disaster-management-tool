package de.thab.algo.functionOne;

import de.thab.algo.abstractdatastructures.Graph;
import de.thab.algo.graphreader.GraphReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * The {@code InfrastructureNetworkHelper} class is a utility for managing an infrastructure network.
 * It reads a directed weighted graph from a file, initializes nodes representing infrastructure elements,
 * allows users to set priority nodes, and calculates the minimum spanning tree for disaster recovery.
 * The class now supports starting the MST calculation from the first priority node set by the user.
 */
public class InfrastructureNetworkHelper {

    /**
     * Enum representing different types of infrastructure nodes like rescue station, hospital, government building
     */
    private enum NodeType {
        RESCUE_STATION,
        HOSPITAL,
        GOVERNMENT_BUILDING,
        STANDARD_NODE
    }

    /**
     * Class representing an infrastructure node with additional metadata like name and type of nodes
     */
    public static class InfrastructureNode {
        String name;
        NodeType type;
        boolean isPriority;

        /**
         * Constructs an {@code InfrastructureNode} with the specified name.
         *
         * @param name the name of the node
         */
        public InfrastructureNode(String name) {
            this.name = name;
            this.type = NodeType.STANDARD_NODE;
            this.isPriority = false;
        }
    }

    /**
     * The main method that serves as the entry point for the application.
     * It reads graph data from a file, initializes infrastructure nodes,
     * accepts user input for priority nodes, and displays results.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String filePath = "src/main/resources/test.txt";

        try (Scanner scanner = new Scanner(System.in)){

            Object[] graphData = GraphReader.readFromFileWithNames(filePath);
            Graph graph = (Graph) graphData[0];
            String[] vertexNames = (String[]) graphData[1];


            // Initialize infrastructure nodes
            InfrastructureNode[] infrastructureNodes = initializeInfrastructureNodes(vertexNames);

            // Get priority nodes from user
            setPriorityNodeFromUserInput(infrastructureNodes, scanner);


            // Calculate and display results
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
     * Displays the results of the Minimum Spanning Tree calculation and priority node information.
     * The MST calculation now starts from the first priority node set by the user, or from node 0 if no priority node is set.
     *
     * @param graph The Graph object representing the infrastructure network
     * @param infrastructureNodes Array of InfrastructureNode objects containing node information
     */
    private static void displayResults(Graph graph, InfrastructureNode[] infrastructureNodes) {
        System.out.println("\nCalculating Minimum Spanning Tree for Disaster Recovery:");
        // for now i am just passing the first index as starting node
        // to do : find out the bug in case if i wanna start with 1 or higher
        int startNode = findFirstPriorityNode(infrastructureNodes);
        graph.primMST(startNode);

        // Print additional information about priority nodes
        printPriorityNodesInfo(infrastructureNodes);
    }

    /**
     * Finds the index of the first priority node in the array of infrastructure nodes.
     *
     * @param nodes Array of InfrastructureNode objects
     * @return The index of the first priority node, or 0 if no priority node is found
     */
    private static int findFirstPriorityNode(InfrastructureNode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isPriority) {
                return i;
            }
        }
        return 0; // Default to 0 if no priority node is found
    }

    /**
     * Allows the user to set priority nodes and their types through console input.
     * Users can select nodes by their number and specify their type (RESCUE_STATION, HOSPITAL, GOVERNMENT_BUILDING).
     *
     * @param nodes Array of InfrastructureNode objects to be updated
     * @param scanner Scanner object for reading user input
     */
    private static void setPriorityNodeFromUserInput(InfrastructureNode[] nodes, Scanner scanner) {
        System.out.println("Available Nodes:");
        for (int i = 0; i < nodes.length; i++) {
            System.out.println((i + 1) + ". " + nodes[i].name);
        }

        System.out.println("\nSelect Critical Nodes (Enter node numbers separated by spaces, end with 0):");

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

    /**
     * Prints information about the priority nodes, including their names and types.
     *
     * @param nodes Array of InfrastructureNode objects
     */
    private static void printPriorityNodesInfo(InfrastructureNode[] nodes) {
        System.out.println("\nPriority Nodes Information:");
        for (InfrastructureNode node : nodes) {
            if (node.isPriority) {
                System.out.println(node.name + " - Type: " + node.type);
            }
        }
    }
}
