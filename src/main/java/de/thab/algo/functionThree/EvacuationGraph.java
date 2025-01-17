package de.thab.algo.functionThree;

import de.thab.algo.abstractdatastructures.list.ArrayList;
import de.thab.algo.abstractdatastructures.queue.CustomPriorityQueue;

public class EvacuationGraph {
    private int numberOfNodes;
    private ArrayList<ArrayList<EvacuationNode>> adj;
    private String[] nodeNames;

    public EvacuationGraph(int V, String[] nodeNames) {
        this.numberOfNodes = V;
        this.nodeNames = nodeNames;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>(V));
        }
    }

    public void addEdge(int src, int dest, int weight) {
        if (weight > 0) {
            boolean edgeExists = false;
            ArrayList<EvacuationNode> srcList = adj.get(src);

            for (int i = 0; i < srcList.size(); i++) {
                EvacuationNode node = srcList.get(i);
                if (node.getDest() == dest) {
                    if (weight < node.getWeight()) {
                        srcList.add(new EvacuationNode(dest, weight));
                    }
                    edgeExists = true;
                    break;
                }
            }

            if (!edgeExists) {
                adj.get(src).add(new EvacuationNode(dest, weight));
            }
        }
    }

    public boolean blockRoad(int src, int dest) {
        boolean blocked = false;

        ArrayList<EvacuationNode> srcNodes = adj.get(src);
        for (int i = 0; i < srcNodes.size(); i++) {
            EvacuationNode node = srcNodes.get(i);
            if (node.getDest() == dest) {
                node.setBlocked(true);
                blocked = true;
                break;
            }
        }

        ArrayList<EvacuationNode> destNodes = adj.get(dest);
        for (int i = 0; i < destNodes.size(); i++) {
            EvacuationNode node = destNodes.get(i);
            if (node.getDest() == src) {
                node.setBlocked(true);
                blocked = true;
                break;
            }
        }

        return blocked;
    }

    public boolean unblockRoad(int src, int dest) {
        boolean unblocked = false;

        ArrayList<EvacuationNode> srcNodes = adj.get(src);
        for (int i = 0; i < srcNodes.size(); i++) {
            EvacuationNode node = srcNodes.get(i);
            if (node.getDest() == dest) {
                node.setBlocked(false);
                unblocked = true;
                break;
            }
        }

        ArrayList<EvacuationNode> destNodes = adj.get(dest);
        for (int i = 0; i < destNodes.size(); i++) {
            EvacuationNode node = destNodes.get(i);
            if (node.getDest() == src) {
                node.setBlocked(false);
                unblocked = true;
                break;
            }
        }

        return unblocked;
    }

    public void calculateEvacuationRoutes(int startNode) {
        int[] distances = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];
        int[] parent = new int[numberOfNodes];

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
            if (distances[u] == Integer.MAX_VALUE) break;

            visited[u] = true;

            ArrayList<EvacuationNode> adjacentNodes = adj.get(u);
            for (int j = 0; j < adjacentNodes.size(); j++) {
                EvacuationNode v = adjacentNodes.get(j);
                if (v.isBlocked()) continue;

                int vVertex = v.getDest();
                int weight = v.getWeight();

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

        printEvacuationRoutes(distances, parent, startNode);
    }

    private void printEvacuationRoutes(int[] distances, int[] parent, int startNode) {
        System.out.println("\n=== Evacuation Routes from " + nodeNames[startNode] + " ===");
        System.out.println("Destination\tDistance\tRoute");
        System.out.println("----------------------------------------");

        for (int i = 0; i < numberOfNodes; i++) {
            if (i != startNode) {
                String distance = distances[i] == Integer.MAX_VALUE ? "∞" : String.valueOf(distances[i]);
                System.out.printf("%-12s\t%-9s\t%s%n",
                        nodeNames[i],
                        distance,
                        getEvacuationPath(parent, i, startNode, distances[i]));
            }
        }
    }

    private String getEvacuationPath(int[] parent, int currentVertex, int startVertex, int distance) {
        if (distance == Integer.MAX_VALUE) {
            return "No route available - road blocked";
        }
        if (currentVertex == startVertex) {
            return nodeNames[startVertex];
        }
        if (parent[currentVertex] == -1) {
            return "No route available - road blocked";
        }
        return getEvacuationPath(parent, parent[currentVertex], startVertex, distance) +
                " → " + nodeNames[currentVertex];
    }

    public void printNetwork() {
        System.out.println("Evacuation Network Structure:");
        System.out.println("----------------------------");
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.printf("%-2s → ", nodeNames[i]);
            ArrayList<EvacuationNode> neighbors = adj.get(i);

            for (int j = 0; j < neighbors.size(); j++) {
                EvacuationNode neighbor = neighbors.get(j);
                System.out.printf("%s(%d)%s",
                        nodeNames[neighbor.getDest()],
                        neighbor.getWeight(),
                        neighbor.isBlocked() ? "[BLOCKED]" : "");
                if (j < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }
}
