package Attempt_2;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();

        try {

            String inputPath = "F:/All java projects/JavaOOP/src/Attempt_2/Graph.txt";
            String outputPath = "F:/All java projects/JavaOOP/src/Attempt_2/Graph1.txt";

            System.out.println("Reading from Graph.txt...");

            g.importGraph(inputPath);

            System.out.println("Writing to Graph1.txt...");

            g.exportGraph(outputPath);

            System.out.println("Done! Operations completed successfully.");

        } catch (IOException e) {
            System.out.println("Error with files: " + e.getMessage());
        }
       new TrainMenu(g);

    }
}
