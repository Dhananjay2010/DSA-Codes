public class etrs {

    public int minScore(int n, int[][] roads) {

        ArrayList<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int[] road : roads) {
            int u = road[0], v = road[1], w = road[2];
            graph[u].add(new int[] { v, w });
            graph[v].add(new int[] { u, w });
        }

        boolean[] vis = new boolean[n + 1];
        return dfs(graph, vis, 1, n);
    }

    public int dfs(ArrayList<int[]>[] graph, boolean[] vis, int src, int n) {
        vis[src] = true;
        int minValue = (int) 1e9;
        for (int[] edge : graph[src]) {
            int v = edge[1], w = edge[2];

            if (!vis[v]) {
                if (v == n)
                    return w;
                int x = dfs(graph, vis, src, n);
                minValue = Math.min(minValue, Math.min(x, w));
            }
            vis[src] = false;
            return minValue;
        }
    }
}
