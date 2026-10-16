package Attempt_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
    Map<String, ArrayList<Structure>> graph = new HashMap<>();
    Map<String, ArrayList<Structure>> graph1 = new HashMap<>();

    // --- station / route management, used by the GUI's "edit network" panel ---

    public boolean addStation(String city) {
        if (city == null || city.trim().isEmpty()) return false;
        city = city.trim();
        if (graph.containsKey(city)) return false;
        graph.put(city, new ArrayList<>());
        return true;
    }

    public boolean removeStation(String city) {
        if (city == null || !graph.containsKey(city)) return false;

        graph.remove(city);
        // also remove any route pointing into the removed station
        for (ArrayList<Structure> neighbors : graph.values()) {
            neighbors.removeIf(s -> s.city.equals(city));
        }
        return true;
    }

    // adds a one-directional route from -> to (distance). Both stations must already exist.
    public boolean addRoute(String from, String to, int distance) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) return false;
        if (distance <= 0) return false;

        // avoid duplicate edges between the same pair
        for (Structure s : graph.get(from)) {
            if (s.city.equals(to)) return false;
        }

        graph.get(from).add(new Structure(to, distance));
        return true;
    }

    public boolean removeRoute(String from, String to) {
        if (!graph.containsKey(from)) return false;
        return graph.get(from).removeIf(s -> s.city.equals(to));
    }

    // to read and wright the roads in the graph
//        public void roads() throws IOException {
//            StringBuilder sb = new StringBuilder();
//            for (String city : graph.keySet()) {
//                sb.append( "City:"+city);
//                sb.append("\n");
//                for (Structure e : graph.get(city)) {
//                    sb.append(e.city +" D:"+ e.distance + " km\n"
//                    );
//                }
//            }
//            try (FileWriter writer = new FileWriter("Graph1.txt")) {
//                writer.write(sb.toString());
//                System.out.println("File written successfully");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            BufferedReader reader = new BufferedReader(new FileReader("Graph1.txt"));
//
//            String line;
//            String currentCity = null;
//
//            while ((line = reader.readLine()) != null) {
//
//
//                if (line.isEmpty()) {
//                    continue;
//                }
//
//                if (line.startsWith("City:")) {
//                    currentCity = line.substring(5);
//                    graph1.put(currentCity, new ArrayList<>());
//                }
//                else {
//                    String[] parts = line.split(" D:");
//
//                    String destination = parts[0];
//                    int distance = Integer.parseInt(
//                            parts[1].replace(" km", "")
//                    );
//
//                    graph1.get(currentCity)
//                            .add(new Structure(destination, distance));
//                }
//            }
//
//            reader.close();
//        }
    // to read from (Graph.txt) the graph we made
    public void importGraph(String fileName) throws IOException {
        graph.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || !line.contains("->")) continue;

                String[] parts = line.split("->");
                String city = parts[0].trim();

                if (!graph.containsKey(city)) {
                    graph.put(city, new ArrayList<>());
                }

                if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                    String[] neighbors = parts[1].split(",");

                    for (String neighbor : neighbors) {
                        neighbor = neighbor.trim();
                        if (neighbor.isEmpty()) continue;

                        int openBracket = neighbor.indexOf("(");
                        int closeBracket = neighbor.indexOf(")");

                        if (openBracket != -1 && closeBracket != -1) {
                            String destination = neighbor.substring(0, openBracket).trim();
                            int distance = Integer.parseInt(neighbor.substring(openBracket + 1, closeBracket).trim());

                            graph.get(city).add(new Structure(destination, distance));

                            if (!graph.containsKey(destination)) {
                                graph.put(destination, new ArrayList<>());
                            }
                        }
                    }
                }
            }
        }
        System.out.println("the file has been read successfully");
    }
    // to write the graph into a file
    public void exportGraph(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (String city : graph.keySet()) {
            sb.append(city).append(" -> ");
            ArrayList<Structure> neighbors = graph.get(city);

            for (int i = 0; i < neighbors.size(); i++) {
                Structure s = neighbors.get(i);
                sb.append(s.city).append("(").append(s.distance).append(")");

                if (i != neighbors.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        FileWriter writer = new FileWriter(fileName);
        writer.write(sb.toString());
        writer.close();

        System.out.println("Export Done Successfully.");
    }
    public void printGraph() {
        System.out.println("START printGraph");

        StringBuilder sb = new StringBuilder();

        for (String city : graph.keySet()) {
            sb.append(city).append(":\n");

            for (Structure e : graph.get(city)) {
                sb.append("  -> ")
                        .append(e.city)
                        .append(" (")
                        .append(e.distance)
                        .append(" km)\n");
            }
        }

        System.out.println("Generated text:\n" + sb);

        try (FileWriter writer = new FileWriter("Graph.txt")) {
            writer.write(sb.toString());
            System.out.println("File written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawGraphToFile(String inputFile, String outputFile) throws IOException {

        importGraph(inputFile);

        StringBuilder sb = new StringBuilder();

        List<String> cities = new ArrayList<>(graph.keySet());
        Collections.sort(cities);

        for (String city : cities) {
            sb.append(city).append(":\n");

            for (Structure e : graph.get(city)) {
                sb.append("   |-- ")
                        .append(e.city)
                        .append("  (")
                        .append(e.distance)
                        .append(" km)\n");
            }

            if (graph.get(city).isEmpty()) {
                sb.append("   (no outgoing routes)\n");
            }
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }

        System.out.println("Network read from " + inputFile + " and drawn into " + outputFile);
    }
    // dijkstra algo to find the shortest path in the graph
    public void shortestPath(String start, String end) {

        PathResult result = computeShortestPath(start, end);

        if (result == null) {
            System.out.println("No path between " + start + " and " + end);
            return;
        }

        List<String> path = result.path;

        // Print result
        System.out.println("Shortest path from " + start + " to " + end + ":");

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i != path.size() - 1) System.out.print(" -> ");
        }

        System.out.println("\nDistance: " + result.distance + " km");
    }

    // simple holder for the result of Dijkstra, used by the GUI to draw/show the path
    public static class PathResult {
        public List<String> path;
        public int distance;

        public PathResult(List<String> path, int distance) {
            this.path = path;
            this.distance = distance;
        }
    }

    public PathResult computeShortestPath(String start, String end) {

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.distance, b.distance)
        );

        // Initialize distances
        for (String city : graph.keySet()) {
            dist.put(city, Integer.MAX_VALUE);
        }

        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            return null;
        }

        dist.put(start, 0);
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {

            Node current = pq.poll();
            String city = current.city;

            // Skip outdated entries
            if (current.distance > dist.get(city)) continue;

            // Stop early if we reached destination
            if (city.equals(end)) break;

            for (Structure neighbor : graph.get(city)) {

                int newDist = dist.get(city) + neighbor.distance;

                if (newDist < dist.get(neighbor.city)) {
                    dist.put(neighbor.city, newDist);
                    prev.put(neighbor.city, city);
                    pq.add(new Node(neighbor.city, newDist));
                }
            }
        }

        // If unreachable
        if (dist.get(end) == null || dist.get(end) == Integer.MAX_VALUE) {
            return null;
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();

        String current = end;
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }

        Collections.reverse(path);

        return new PathResult(path, dist.get(end));
    }

    // to walk inside the graph
    public boolean dfs(String city,Set<String> visited,String parent) {

        visited.add(city);

        for (Structure neighbor : graph.get(city)) {

            String nextCity = neighbor.city;

            if (!visited.contains(nextCity)) {

                if (dfs(nextCity, visited, city))
                    return true;
            }

            else if (!nextCity.equals(parent)) {

                return true;
            }
        }

        return false;
    }

    // Returns true if the graph contains a cycle, else false.
    public boolean isCycle() {

        Set<String> visited = new HashSet<>();

        for (String city : graph.keySet()) {

            if (!visited.contains(city)) {

                if (dfs(city, visited, null))
                    return true;
            }
        }

        return false;
    }

    // holder describing where a cycle was found, used only by the GUI
    public static class CycleResult {
        public List<String> path;   // the DFS path from the start of the search down to the back-edge
        public String closingEdgeFrom;
        public String closingEdgeTo;

        public CycleResult(List<String> path, String from, String to) {
            this.path = path;
            this.closingEdgeFrom = from;
            this.closingEdgeTo = to;
        }
    }

    private boolean dfsWithPath(String city, Set<String> visited, String parent, List<String> pathSoFar, CycleResult[] found) {

        visited.add(city);
        pathSoFar.add(city);

        for (Structure neighbor : graph.get(city)) {

            String nextCity = neighbor.city;

            if (!visited.contains(nextCity)) {

                if (dfsWithPath(nextCity, visited, city, pathSoFar, found))
                    return true;

            } else if (!nextCity.equals(parent)) {

                found[0] = new CycleResult(new ArrayList<>(pathSoFar), city, nextCity);
                return true;
            }
        }

        pathSoFar.remove(pathSoFar.size() - 1);
        return false;
    }

    // Returns the details of a cycle (path + closing edge) if one exists, otherwise null.
    public CycleResult findCycleDetails() {

        Set<String> visited = new HashSet<>();
        CycleResult[] found = new CycleResult[1];

        for (String city : graph.keySet()) {
            if (!visited.contains(city)) {
                List<String> pathSoFar = new ArrayList<>();
                if (dfsWithPath(city, visited, null, pathSoFar, found)) {
                    return found[0];
                }
            }
        }

        return null;
    }

    public void sortStationsByConnections() {

        List<String> cities = sortedStationsByConnections();

        System.out.println("Stations sorted by number of connections:");

        for (String city : cities) {
            System.out.println(
                    city + " -> " + graph.get(city).size() + " connections"
            );
        }
    }

    // same sorting logic as sortStationsByConnections(), but returns the list so the GUI can display it
    public List<String> sortedStationsByConnections() {

        List<String> cities = new ArrayList<>(graph.keySet());

        cities.sort((c1, c2) ->
                Integer.compare(
                        graph.get(c2).size(),
                        graph.get(c1).size()
                )
        );

        return cities;
    }
}