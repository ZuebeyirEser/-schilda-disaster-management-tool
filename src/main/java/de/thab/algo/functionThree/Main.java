package de.thab.algo.functionThree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/graph_directed_weighted.txt";
        EvacuationSystem evacuationSystem = new EvacuationSystem();
        evacuationSystem.initialize(filePath);
        evacuationSystem.run();
    }
}
