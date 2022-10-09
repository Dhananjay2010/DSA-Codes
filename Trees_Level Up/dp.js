public static class pointPair implements Comparable<pointPair> {
  int r = 0, c = 0, steps = 0;
  String psf = "";

  pointPair(int r, int c, int steps, String psf) {
      this.r = r;
      this.c = c;
      this.steps = steps;
      this.psf = psf;
  }

  @Override
  public int compareTo(pointPair o) {
      if (this.steps != o.steps)
          return this.steps - o.steps;
      else
          return this.psf.compareTo(o.psf);
  }
}

public String findShortestWay(int[][] maze, int[] start, int[] destination) {
  int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
  int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
  String[] dirS = { "d", "u", "r", "l" };
  pointPair[][] dis = new pointPair[n][m];
  for (int i = 0; i < n * m; i++)
      dis[i / m][i % m] = new pointPair(i / m, i % m, (int) 1e8, "");

  PriorityQueue<pointPair> que = new PriorityQueue<>();
  pointPair src = new pointPair(sr, sc, 0, "");

  que.add(src);
  dis[sr][sc] = src;

  while (que.size() != 0) {
      pointPair p = que.remove();
      for (int i = 0; i < 4; i++) {
          int[] d = dir[i];

          int r = p.r, c = p.c, steps = p.steps;
          while (r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0 && !(r == er && c == ec)) { // !(r == er &&
                                                                                                   // c == ec) ==
                                                                                                   // (r != er ||
                                                                                                   // c != ec)
              r += d[0];
              c += d[1];
              steps++;
          }

          if (!(r == er && c == ec)) { // why it is necc. ???
              r -= d[0];
              c -= d[1];
              steps--;
          }

          pointPair np = new pointPair(r, c, steps, p.psf + dirS[i]);
          if (steps > dis[r][c].steps || dis[r][c].compareTo(np) <= 0) // why this kind of check ???
              continue;

          que.add(np);
          dis[r][c] = np;
      }
  }

  pointPair ans = dis[er][ec];
  return ans.steps != (int) 1e8 ? ans.psf : "impossible";
}