package com.example.objectspane;

import javafx.scene.paint.Color;

import java.util.Arrays;

public class GraphFunctions {

    private static int[] dijkstra(Graph graph, int source) {
        String[][] matrix = graph.getMatrix();
        int n = graph.getN();
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];
        int INF = Integer.MAX_VALUE;
        Arrays.fill(distances, INF);
        distances[source] = 0;

        for (int count = 0; count < n - 1; count++) {
            int u = -1;
            int minDistance = INF;

            for (int i = 0; i < n; i++) {
                if (!visited[i] && distances[i] < minDistance) {
                    u = i;
                    minDistance = distances[i];
                }
            }

            if (u == -1) {
                break;
            }

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (matrix[u][v] != null && !matrix[u][v].equals("0") && !visited[v]) {
                    int weight = Integer.parseInt(matrix[u][v]);  // Assuming weights are stored as integers in the matrix
                    if (distances[u] + weight < distances[v]) {
                        distances[v] = distances[u] + weight;
                    }
                }
            }
        }

        return distances;
    }

    public static int[] doDijkstra(Graph graph, int source) {
        int[] distances = dijkstra(graph, source);
        int i = 0;
        for (Box node: graph.getNodes()) {
            node.setValue(i + " : " + distances[i]);
            i++;
        }
        return distances;
    }

    private static void colorGraphHelper(Graph graph, int u, int[] colors, int color, int n) {
        colors[u] = color;

        Box node = graph.getNodes()[u];
        if (color == 1) {
            node.setColor(Color.RED);
        } else {
            node.setColor(Color.BLUE);
        }

        for (int v = 0; v < n; v++) {
            if (!graph.getMatrix()[u][v].equals("0")) {
                if (colors[v] == -1) {
                    colorGraphHelper(graph, v, colors, 1 - color, n);
                }
            }
        }
    }

    public static void colorGraph(Graph graph) {
        if (isBipartite(graph)) {
            int n = graph.getN();
            int[] colors = new int[n];
            Arrays.fill(colors, -1);
            for (int u = 0; u < n; u++) {
                if (colors[u] == -1) {
                    colorGraphHelper(graph, u, colors, 0, n);
                }
            }
        } else {
            System.out.println("IS NOT BIPARTITE");
        }
    }

    private static boolean isNotBipartiteHelper(Graph graph, int u, int[] colors, int color, int n) {
        colors[u] = color;
        for (int v = 0; v < n; v++) {
            String edge = graph.getMatrix()[u][v];
            if (edge != null && !edge.equals("0")) {
                if (colors[v] == -1) {
                    if (isNotBipartiteHelper(graph, v, colors, 1 - color, n)) {
                        return true;
                    }
                } else if (colors[v] == colors[u]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBipartite(Graph graph) {
        int n = graph.getN();
        int[] color = new int[n];
        Arrays.fill(color, -1);
        for (int u = 0; u < n; u++) {
            if (color[u] == -1) {
                if (isNotBipartiteHelper(graph, u, color, 0, n)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasCyclesHelper(Graph graph, int u, boolean[] visited, int parent, int n) {
        visited[u] = true;

        for (int v = 0; v < n; v++) {
            if (!graph.getMatrix()[u][v].equals("0")) {
                if (!visited[v]) {
                    if (hasCyclesHelper(graph, v, visited, u, n)) {
                        return true;
                    }
                } else if (v != parent) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasCycles(Graph graph) {
        int n = graph.getN();
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false); // Initialize all nodes as not visited

        for (int u = 0; u < n; u++) {
            if (!visited[u]) { // If the node is not visited
                if (hasCyclesHelper(graph, u, visited, -1, n)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] floydWarshall(Graph graph) {
        int n = graph.getN();
        String[][] matrix = graph.getMatrix();
        int[][] output = new int[n][n];
        int INF = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    output[i][j] = 0;
                } else if (matrix[i][j] != null && !matrix[i][j].equals("0")) {
                    output[i][j] = Integer.parseInt(matrix[i][j]);
                } else {
                    output[i][j] = INF;
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (output[i][k] != INF && output[k][j] != INF) {
                        output[i][j] = Math.min(output[i][j], output[i][k] + output[k][j]);
                    }
                }
            }
        }
        return output;
    }

    public static String[][] findMSTMatrix(Graph graph) {
        int n = graph.getN();
        String[][] matrix = graph.getMatrix();
        String[][] mstMatrix = new String[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mstMatrix[i], "0");
        }

        boolean[] inMST = new boolean[n];
        int[] minEdge = new int[n];
        int[] parent = new int[n];
        int INF = Integer.MAX_VALUE;
        Arrays.fill(minEdge, INF);
        Arrays.fill(parent, -1);
        minEdge[0] = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;
            int minWeight = INF;
            for (int v = 0; v < n; v++) {
                if (!inMST[v] && minEdge[v] < minWeight) {
                    u = v;
                    minWeight = minEdge[v];
                }
            }

            if (u == -1) {
                break;
            }

            inMST[u] = true;

            if (parent[u] != -1) {
                int weight = minEdge[u];
                mstMatrix[parent[u]][u] = String.valueOf(weight);
                mstMatrix[u][parent[u]] = String.valueOf(weight);
            }

            for (int v = 0; v < n; v++) {
                if (!inMST[v] && !matrix[u][v].equals("0")) {
                    int weight = Integer.parseInt(matrix[u][v]);
                    if (weight < minEdge[v]) {
                        minEdge[v] = weight;
                        parent[v] = u;
                    }
                }
            }
        }
        return mstMatrix;
    }

}
