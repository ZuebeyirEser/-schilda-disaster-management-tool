package de.thab.algo.graphreader;

import de.thab.algo.abstractdatastructures.Graph;

public class GraphData {
    private final Graph graph;
    private final String[] nodeNames;

    public GraphData(Graph graph, String[] vertexNames) {
        this.graph = graph;
        this.nodeNames = vertexNames;
    }

}
