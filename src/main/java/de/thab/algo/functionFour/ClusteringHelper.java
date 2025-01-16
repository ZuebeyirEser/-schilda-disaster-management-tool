package de.thab.algo.functionFour;

import de.thab.algo.abstractdatastructures.Graph;

import java.util.*;

public class ClusteringHelper {

    private final Graph graph;

    public ClusteringHelper(Graph graph) {
        this.graph = graph;
    }

    /**
     * Implements the K-Medoids clustering algorithm.
     *
     * @param k             Number of clusters.
     * @param maxIterations Maximum number of iterations.
     * @return A map of clusters with medoids as keys and lists of node indices as values.
     */
    public Map<Integer, List<Integer>> kMedoids(int k, int maxIterations) {
        Random random = new Random();
        int n = graph.getNumberOfNodes();

        // Step 1: Initialize medoids randomly
        Set<Integer> medoidSet = new HashSet<>();
        while (medoidSet.size() < k) {
            medoidSet.add(random.nextInt(n));
        }
        List<Integer> medoids = new ArrayList<>(medoidSet);

        Map<Integer, List<Integer>> clusters = new HashMap<>();
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            // Step 2: Assign each node to the nearest medoid
            clusters.clear();
            for (int i = 0; i < n; i++) {
                int nearestMedoid = -1;
                int minDistance = Integer.MAX_VALUE;

                for (int medoid : medoids) {
                    int distance = graph.getWeight(i, medoid);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestMedoid = medoid;
                    }
                }

                clusters.computeIfAbsent(nearestMedoid, x -> new ArrayList<>()).add(i);
            }

            // Step 3: Update medoids
            boolean medoidsChanged = false;
            List<Integer> newMedoids = new ArrayList<>();

            for (int medoid : medoids) {
                List<Integer> cluster = clusters.get(medoid);

                if (cluster == null || cluster.isEmpty()) {
                    // Keep the current medoid if the cluster is empty
                    newMedoids.add(medoid);
                    continue;
                }

                int bestMedoid = medoid;
                int minCost = Integer.MAX_VALUE;

                for (int candidate : cluster) {
                    int cost = 0;
                    for (int node : cluster) {
                        cost += graph.getWeight(candidate, node);
                    }

                    if (cost < minCost) {
                        minCost = cost;
                        bestMedoid = candidate;
                    }
                }

                if (bestMedoid != medoid) {
                    medoidsChanged = true;
                }
                newMedoids.add(bestMedoid);
            }

            medoids = newMedoids;

            // Step 4: Check for convergence
            if (!medoidsChanged) {
                break;
            }
        }

        return clusters;
    }

    /**
     * Helper method to display the clusters.
     *
     * @param clusters The cluster map with medoids and their assigned nodes.
     */
    public void displayClusters(Map<Integer, List<Integer>> clusters) {
        System.out.println("Clusters:");
        for (Map.Entry<Integer, List<Integer>> entry : clusters.entrySet()) {
            System.out.println("Medoid: " + graph.getNodeName(entry.getKey()));
            System.out.print("Nodes: ");
            for (int node : entry.getValue()) {
                System.out.print(graph.getNodeName(node) + " ");
            }
            System.out.println();
        }
    }
}
