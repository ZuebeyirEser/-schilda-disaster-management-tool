package de.thab.algo.functionFive;

import de.thab.algo.abstractdatastructures.*;

import java.util.ArrayList;
import java.util.List;

public class FunctionFiveHelper {
    private final List<Edge> edges = new ArrayList<>();
    private final CustomHashMap<Integer, List<Integer>> adj = new CustomHashMap<>();
    private final int source;
    private final int sink;
    private final int nodeCount;
    private final int teamCount;

    private static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    public FunctionFiveHelper(int teamCount, int nodeCount) {
        this.teamCount = teamCount;
        this.nodeCount = nodeCount;

        int totalNodes = teamCount + nodeCount + 2;
        this.source = 0;
        this.sink = totalNodes - 1;

        for (int i = 0; i < totalNodes; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int capacity) {
        edges.add(new Edge(from, to, capacity));
        edges.add(new Edge(to, from, 0)); // Reverse edge
        adj.get(from).add(edges.size() - 2);
        adj.get(to).add(edges.size() - 1);
    }

    public void buildGraph(List<CustomSet<String>> teamSkills, List<CustomSet<String>> nodeRequirements) {
        // Add edges from source to teams
        for (int i = 0; i < teamCount; i++) {
            addEdge(source, i + 1, 1);
        }

        // Add edges from teams to nodes
        for (int i = 0; i < teamCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (teamSkills.get(i).containsAll(nodeRequirements.get(j))) {
                    addEdge(i + 1, teamCount + j + 1, 1);
                }
            }
        }

        // Add edges from nodes to sink
        for (int i = 0; i < nodeCount; i++) {
            addEdge(teamCount + i + 1, sink, 1);
        }
    }

    public int maxFlow() {
        int totalFlow = 0;
        int[] parent = new int[adj.size()];

        while (bfs(parent)) {
            int pathFlow = Integer.MAX_VALUE;

            // Find the maximum flow through the path
            for (int v = sink; v != source; v = edges.get(parent[v]).from) {
                pathFlow = Math.min(pathFlow, edges.get(parent[v]).capacity - edges.get(parent[v]).flow);
            }

            // Update capacities
            for (int v = sink; v != source; v = edges.get(parent[v]).from) {
                edges.get(parent[v]).flow += pathFlow;
                edges.get(parent[v] ^ 1).flow -= pathFlow;
            }

            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    private boolean bfs(int[] parent) {
        boolean[] visited = new boolean[adj.size()];
        CustomQueue<Integer> queue = new CustomQueue<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int edgeIndex : adj.get(u)) {
                Edge edge = edges.get(edgeIndex);
                if (!visited[edge.to] && edge.flow < edge.capacity) {
                    parent[edge.to] = edgeIndex;
                    visited[edge.to] = true;
                    queue.add(edge.to);

                    if (edge.to == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public CustomHashMap<Integer, Integer> getAllocation() {
        CustomHashMap<Integer, Integer> allocation = new CustomHashMap<>();

        for (Edge edge : edges) {
            if (edge.from > 0 && edge.from <= teamCount && edge.to > teamCount && edge.to < sink && edge.flow > 0) {
                int teamIndex = edge.from; // Convert to 1-based index
                int nodeIndex = edge.to - teamCount; // Convert to 1-based index
                allocation.put(teamIndex, nodeIndex);
            }
        }

        return allocation;
    }
}
