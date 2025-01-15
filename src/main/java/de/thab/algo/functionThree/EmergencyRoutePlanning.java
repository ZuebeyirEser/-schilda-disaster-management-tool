package de.thab.algo.functionThree;

import de.thab.algo.abstractdatastructures.Graph;
import de.thab.algo.abstractdatastructures.list.ArrayList;
import de.thab.algo.functionOne.InfrastructureNetworkHelper.InfrastructureNode;

/**
 * The {@code EmergencyRoutePlanning} class calculates optimal routes
 * for emergency services using Dijkstra's Algorithm to find the shortest paths
 * from service locations (e.g., fire stations, police stations) to disaster sites. 
 */

public class EmergencyRoutePlanning {
    /**
     * Plan emergency service routes by calculating the shortest paths
     * from emergency service nodes to disaster sites using Dijkstra's algorithm.
     * 
     * @param graph The Graph object representig the city infraestructure
     * @param nodes Array of InfrastructureNode objects containing node information 
     */

     public static void planEmergencyRoutes(Graph graph, InfrastructureNode[] nodes){
        System.out.println("\nPlanning Routes for Emergency Services: ");

        // Identify emergency service nodes and disaster sites
        ArrayList<Integer> emergencyServices = new ArrayList<>(nodes.length);
        ArrayList<Integer> disasterSites = new ArrayList<>(nodes.length);

        for(int i = 0; i < nodes.length; i++){
            if(nodes[i].type == InfrastructureNode.NodeType.RESCUE_STATION){
                emergencyServices.add(i);
            } else if (nodes[i].type == InfrastructureNode.NodeType.STANDARD_NODE){
                disasterSites.add(i);
            }
        }


        if (emergencyServices.isEmpty() || disasterSites.isEmpty()){
            System.out.println("No emergency service stations or disaster sites available");
            return;
        }

        // Calculate and display shortest paths for each emergency service location
        for (int i = 0; i < emergencyServices.size(); i++){
            int serviceNode = emergencyServices.get(i);
            System.out.println("Routes for emergency service station: " + nodes[serviceNode].name);

            // Use Dijkstra's alforithm to find shortest path
            int[] shortestPaths = graph.dijkstra(serviceNode);

            for (int j=0; j < disasterSites.size(); j++){
                int disasterNode = disasterSites.get(j);
                if(shortestPaths[disasterNode] < Integer.MAX_VALUE){
                    System.out.println("Route to disaster site: " + nodes[disasterNode].name + 
                                        " (Distance: " + shortestPaths[disasterNode] + ")");
                    System.out.println("Path: " + reconstructPath(graph, shortestPaths, serviceNode, disasterNode, nodes));
                } else {
                    System.out.println("No accesible route to disaster site: " + nodes[disasterNode].name);
                }
            }
        }
    }

    /**
     * Reconstructs the path from source to destination using the parent array from Dijkstra's Algorithm.
     *
     * @param graph The Graph object
     * @param shortestPaths Array containing shortest path distances
     * @param source The source node index
     * @param destination The destination node index
     * @param nodes Array of InfrastructureNode objects
     * @return A string representation of the path
     */

     private static String reconstructPath(Graph graph, int[] shortestPaths, int source, int destination, InfrastructureNode[] nodes){
        ArrayList<Integer> path = new ArrayList<>(shortestPaths.length);
        int current = destination;

        // *******************************************************
        // Assuming the graph provides a method to get the parent array
        int[] parent = graph.getParentArray();

        while (current != source && current != -1){
            path.add(0, current); // Add at beginning
            current = parent[current];
        }

        if(current == -1){
            return "No path found";
        }

        path.add(0, source);

        // Convert path indices to node names
        StringBuilder pathStr = new StringBuilder();
        for(int i = 0; i < path.size(); i++){
            pathStr.append(nodes[path.get(i)].name);
            if(i<path.size() - 1) {
                pathStr.append(" -> ");
            }
        }

        return pathStr.toString();
     }

    
}
