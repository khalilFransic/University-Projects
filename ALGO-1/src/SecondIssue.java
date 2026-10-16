import java.util.ArrayList;
public class SecondIssue {
    static ArrayList<Stars> shortestPath = new ArrayList<>();
    static Double minLength = Double.MAX_VALUE;
    static Double minTime = Double.MIN_VALUE;

    static void findShortestPath(Stars current, Stars destination, boolean[] visited, ArrayList<Stars> stars, double currentDistance, Double time, ArrayList<ArrayList<Stars>> graph, ArrayList<Stars> paths) {

        int currentIndex = paths.indexOf(current);
        visited[currentIndex] = true;
        stars.add(current);

        if (currentDistance >= minLength) {
            stars.remove(stars.size() - 1);
            visited[currentIndex] = false;
            return;
        }

        if (current.equals(destination)) {
            if (currentDistance < minLength) {
                minLength = currentDistance;
                minTime = time;
                shortestPath = new ArrayList<>(stars);
            }
        } else {
            for (Stars s : graph.get(currentIndex)) {
                int sIndex = paths.indexOf(s);
                if (!visited[sIndex]) {
                    double distanceToS = current.Distance(s);
                    double timeToS = current.Time(s);
                    findShortestPath(s, destination, visited, stars, currentDistance + distanceToS,time + timeToS, graph, paths);
                }
            }
        }

        stars.remove(stars.size() - 1);
        visited[currentIndex] = false;
    }
}