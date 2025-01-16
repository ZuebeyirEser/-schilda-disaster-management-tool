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

    /**
     * Implements Dijkstra's algorithm to find the shortest paths from a starting node
     * to all other nodes in the graph.
     *
     * The algorithm maintains a priority queue of nodes, starting from the source node,
     * and updates the shortest path to each node based on the minimum weight edge.
     * It ensures that the shortest distance from the start node to each node is calculated
     * while avoiding cycles and revisiting nodes.
     *
     * @param startNode The starting node from which the shortest paths will be calculated.
     */
    public void dijkstra(int startNode) {
        int[] distances = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];
        int[] parent = new int[numberOfNodes];

        // Initialize distances and parent array
        for (int i = 0; i < numberOfNodes; i++) {
            distances[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        distances[startNode] = 0;

        CustomPriorityQueue pq = new CustomPriorityQueue(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++) {
            pq.enqueue(i, distances[i]);
        }

        while (!pq.isEmpty()) {
            int u = pq.dequeue().getVertex();
            if (distances[u] == Integer.MAX_VALUE) break;  // No more reachable vertices

            visited[u] = true;

            ArrayList<Node> adjacentNodes = adj.get(u);
            for (int j = 0; j < adjacentNodes.size(); j++) {
                Node v = adjacentNodes.get(j);
                int vVertex = v.dest;
                int weight = v.weight;

                if (!visited[vVertex] && distances[u] != Integer.MAX_VALUE) {
                    int newDist = distances[u] + weight;
                    if (newDist < distances[vVertex]) {
                        distances[vVertex] = newDist;
                        parent[vVertex] = u;
                        pq.decreaseKey(vVertex, distances[vVertex]);
                    }
                }
            }
        }

        printShortestPaths(distances, parent, startNode);
    }

    /**
     * Prints the shortest paths from the starting node to all other nodes.
     *
     * Displays the destination node, the distance from the start node, and the path taken
     * to reach the destination node. If no path exists, it shows "No path available."
     *
     * @param distances The array containing the shortest distance to each node.
     * @param parent The array storing the parent node of each node along the shortest path.
     * @param startNode The starting node from which the shortest paths were calculated.
     */
    private void printShortestPaths(int[] distances, int[] parent, int startNode) {
        System.out.println("\n=== Shortest Paths from " + nodeNames[startNode] + " ===");
        System.out.println("Destination\tDistance\tPath");
        System.out.println("----------------------------------------");

        for (int i = 0; i < numberOfNodes; i++) {
            if (i != startNode) {
                String distance = distances[i] == Integer.MAX_VALUE ? "∞" : String.valueOf(distances[i]);
                System.out.printf("%-12s\t%-9s\t%s%n",
                        nodeNames[i],
                        distance,
                        getPath(parent, i, startNode, distances[i]));
            }
        }
    }

    /**
     * Recursively constructs the path from the start node to the current vertex.
     *
     * This method traces the parent nodes back to the start node and builds the path
     * by concatenating the node names along the way.
     *
     * @param parent The array storing the parent node of each node along the shortest path.
     * @param currentVertex The current vertex for which the path is being traced.
     * @param startVertex The starting node for the path calculation.
     * @param distance The shortest distance to the current vertex.
     * @return The path from the start node to the current vertex in a string format.
     */
    private String getPath(int[] parent, int currentVertex, int startVertex, int distance) {
        if (distance == Integer.MAX_VALUE) {
            return "No path available";
        }
        if (currentVertex == startVertex) {
            return nodeNames[startVertex];
        }
        if (parent[currentVertex] == -1) {
            return "No path available";
        }
        return getPath(parent, parent[currentVertex], startVertex, distance) + " → " + nodeNames[currentVertex];
    }
    /**
     * Prints the entire graph structure, showing each node and its adjacent neighbors.
     *
     * For each node in the graph, this method prints the outgoing edges along with their
     * corresponding weights, formatted as "Node → Neighbor(weight)".
     */
    public void printGraph() {
        System.out.println("Graph Structure:");
        System.out.println("---------------");
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.printf("%-2s → ", nodeNames[i]);
            ArrayList<Node> neighbors = adj.get(i);

            for (int j = 0; j < neighbors.size(); j++) {
                Node neighbor = neighbors.get(j);
                System.out.printf("%s(%d)", nodeNames[neighbor.dest], neighbor.weight);
                if (j < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    public int getNumberOfNodes(){
        return numberOfNodes;
    }

    public String getNodeName(int node){
        return nodeNames[node];
    }
}