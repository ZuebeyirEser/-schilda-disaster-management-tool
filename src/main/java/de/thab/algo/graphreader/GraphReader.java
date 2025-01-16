package de.thab.algo.graphreader;

import de.thab.algo.abstractdatastructures.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
    /**
     * Reads a graph from a file and returns both the Graph object and vertex names.
     * Useful when you need to keep track of vertex names.
     *
     * @param filePath path to the input file
     * @return Object array containing the Graph and vertex names array
     * @throws IOException if there's an error reading the file
     */
    public static Object[] readFromFileWithNames(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read nodes names from first line
            String[] vertexNames = reader.readLine().trim().split("\\s+");
            int numberOfVertex = vertexNames.length;

            // Create new graph with the number of vertices
            Graph graph = new Graph(numberOfVertex,vertexNames);

            // Read adjacency matrix and add edges
            for (int i = 0; i < numberOfVertex; i++) {
                String[] values = reader.readLine().trim().split("\\s+");
                for (int j = 1; j < values.length; j++) {
                    int weight = Integer.parseInt(values[j]);
                    if (weight > 0) {
                        graph.addEdge(i, j - 1, weight);
                    }
                }
            }

            return new Object[]{graph, vertexNames};
        }
    }

    public static Object[] readFromFileWithNamesDijkstra(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read nodes names from first line
            String[] vertexNames = reader.readLine().trim().split("\\s+");
            int numberOfVertex = vertexNames.length;

            // Create new graph with the number of vertices
            Graph graph = new Graph(numberOfVertex,vertexNames);

            // Read adjacency matrix and add edges
            for (int i = 0; i < numberOfVertex; i++) {
                String[] values = reader.readLine().trim().split("\\s+");
                for (int j = 1; j < values.length; j++) {
                    int weight = Integer.parseInt(values[j]);
                    if (weight > 0) {
                        graph.addEdgeDijkstra(i, j - 1, weight);
                    }
                }
            }

            return new Object[]{graph, vertexNames};
        }
    }
}