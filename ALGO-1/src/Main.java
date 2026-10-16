import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Stars s0 = new Stars(15, 68, 48, "S0");
        Stars s1 = new Stars(17, 69, 25, "S1");
        Stars s2 = new Stars(85, 84, 68, "S2");
        Stars s3 = new Stars(91, 19, 58, "S3");
        Stars s4 = new Stars(29, 36, 38, "S4");

        ArrayList<Stars> vertices = new ArrayList<>(Arrays.asList(s0, s1, s2, s3, s4));
        int V = vertices.size();

        ArrayList<ArrayList<Stars>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());

        graph.get(0).add(s1);
        graph.get(0).add(s2);
        graph.get(1).add(s3);
        graph.get(1).add(s0);
        graph.get(2).add(s3);
        graph.get(3).add(s4);

        Stars start = s0;
        Stars end = s4;

        boolean[] visited = new boolean[V];
        ArrayList<Stars> path = new ArrayList<>();

        SecondIssue.findShortestPath(start, end, visited, path,0.0,0.0 , graph, vertices);

        System.out.print("Shortest path: ");
        for (Stars star : SecondIssue.shortestPath) {
            System.out.print(star.name + "(" + star.x + "," + star.y + "," + star.z + ")" + "  ");
        }
        System.out.println();
        System.out.printf("Total Distance: %.2f%n", SecondIssue.minLength);
        System.out.printf("Total Time: %.2f%n", SecondIssue.minTime);

    }

}