package de.thab.algo.abstractdatastructures;

import de.thab.algo.abstractdatastructures.list.ArrayList;
import de.thab.algo.abstractdatastructures.queue.CustomPriorityQueue;

/**
 * Represents an undirected weighted graph using an adjacency list.
 */
public class Graph {
    private int numberOfNodes;
    private ArrayList<ArrayList<Node>> adj;
    private String[] nodeNames;

    /**
     * Represents a node in the graph with a destination and weight.
     */
    public static class Node {
        private int dest;
        private int weight;

        /**
         * Constructs a Node with the specified destination and weight.
         *
         * @param dest   The destination vertex.
         * @param weight The weight of the edge.
         */
        Node(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }
    /**
     * Constructs a Graph with the specified number of vertices and node names.
     *
     * @param V         The number of vertices in the graph.
     * @param nodeNames An array of node names.
     */
    public Graph(int V,String[] nodeNames) {
        this.numberOfNodes = V;
        this.nodeNames = nodeNames;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>(V));
    }

    /**
     * Adds an edge to the graph for Prims algorithm.
     *
     * @param src    The source vertex.
     * @param dest   The destination vertex.
     * @param weight The weight of the edge.
     */
    public void addEdge(int src, int dest, int weight) {
        adj.get(src).add(new Node(dest, weight));
        adj.get(dest).add(new Node(src, weight));
    }

    // Modified addEdge to prevent duplicate edges
    /**
     * Adds an edge to the graph with additional checks for Dijkstra's algorithm.
     *
     * This method adds or updates an edge between the source and destination vertices
     * based on the specified weight. It ensures that:
     * <ul>
     *   <li>Only positive weights are allowed.</li>
     *   <li>Duplicate edges are avoided.</li>
     *   <li>If an edge already exists, the weight is updated only if the new weight is smaller.</li>
     * </ul>
     *
     * @param src    The source vertex.
     * @param dest   The destination vertex.
     * @param weight The weight of the edge (must be positive).
     */
    public void addEdgeDijkstra(int src, int dest, int weight) {
        if (weight > 0) {  // Only add edges with positive weights
            // Check if edge already exists and update if it does
            boolean edgeExists = false;
            ArrayList<Node> srcList = adj.get(src);
            for (int i = 0; i < srcList.size(); i++) {
                Node node = srcList.get(i);
                if (node.dest == dest) {
                    // Update weight if new weight is smaller
                    if (weight < node.weight) {
                        srcList.add(new Node(dest, weight));
                    }
                    edgeExists = true;
                    break;
                }
            }
            if (!edgeExists) {
                adj.get(src).add(new Node(dest, weight));
            }
        }
    }


    /**
     * Applies Prim's algorithm to find the Minimum Spanning Tree starting from the specified node.
     *
     * @param startNode The starting node for the MST.
     */
    public void primMST(int startNode) {
        int[] parent = new int[numberOfNodes];
        int[] key = new int[numberOfNodes];
        boolean[] inMST = new boolean[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        key[startNode] = 0;

        CustomPriorityQueue pq = new CustomPriorityQueue(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++) {
            pq.enqueue(i, key[i]);
        }

        while (!pq.isEmpty()) {
            int uVertex = pq.dequeue().getVertex();
            inMST[uVertex] = true;

            ArrayList<Node> adjacentNodes = adj.get(uVertex);
            for (int j = 0; j < adjacentNodes.size(); j++) {
                Node v = adjacentNodes.get(j);
                int vVertex = v.dest;
                int weight = v.weight;

                if (!inMST[vVertex] && weight < key[vVertex]) {
                    parent[vVertex] = uVertex;
                    key[vVertex] = weight;
                    pq.decreaseKey(vVertex, weight);
                }
            }
        }
        printMST(parent, startNode);
    }

    /**
     * Prints the Minimum Spanning Tree starting from the specified node.
     *
     * @param parent    The array containing parent information for each node.
     * @param startNode The starting node for printing the MST.
     */
    public void printMST(int[] parent, int startNode) {
        boolean[] visited = new boolean[numberOfNodes];
        int totalWeight = 0;
        System.out.println("\n=== Minimum Spanning Tree ===");
        System.out.println("Edge \t\tWeight");
        System.out.println("----------------------");

        totalWeight = printMSTRecursive(startNode, parent, visited, totalWeight);

        System.out.println("----------------------");
        System.out.println("Total MST Weight: " + totalWeight);
    }

    /**
     * Recursively prints the MST and calculates the total weight.
     *
     * @param node        The current node being processed.
     * @param parent      The array containing parent information for each node.
     * @param visited     Boolean array to keep track of visited nodes.
     * @param totalWeight The current total weight of the MST.
     * @return The updated total weight of the MST.
     */
    private int printMSTRecursive(int node, int[] parent, boolean[] visited, int totalWeight) {
        visited[node] = true;

        for (int i = 0; i < numberOfNodes; i++) {
            if (parent[i] == node && !visited[i]) {
                int weight = getWeight(node, i);
                totalWeight += weight;
                System.out.printf("%s -- %s \t%d%n", nodeNames[node], nodeNames[i], weight);
                totalWeight = printMSTRecursive(i, parent, visited, totalWeight);
            }
        }

        return totalWeight;
    }

    /**
     * Gets the weight of the edge between two nodes.
     *
     * @param src  The source node.
     * @param dest The destination node.
     * @return The weight of the edge, or 0 if no edge exists.
     */
    public int getWeight(int src, int dest) {
        ArrayList<Node> adjacentNodes = adj.get(src);
        for (int j = 0; j < adjacentNodes.size(); j++) {
            if (adjacentNodes.get(j).dest == dest) {
                return adjacentNodes.get(j).weight;
            }
        }
        return 0;
    }

    public int getNumberOfNodes(){
        return numberOfNodes;
    }

    public String getNodeName(int node){
        return nodeNames[node];
    }
}