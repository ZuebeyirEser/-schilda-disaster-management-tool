package de.thab.algo.functionThree;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void myFunctionThree(Scanner scanner) throws IOException {
        String filePath = "src/main/resources/graph_directed_weighted.txt";
        EvacuationSystem evacuationSystem = new EvacuationSystem();
        evacuationSystem.initialize(filePath);
        evacuationSystem.run(scanner);
    }
}
