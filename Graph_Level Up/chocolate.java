import java.util.*;
import java.io.*;

public class chocolate {
    // b<================ Chocolate Journey =================>
    // https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/practice-problems/algorithm/successful-marathon-0691ec04/?
    // B ==> me , A == > Friend
    // Chocolate available only at k cities
    // N cities in the country
    // M bi-directional roads
    // Chocolate expires in x units of time
    // 1 Unit time covers 1 distance
    // Find minimum time friend needs to reach you with chocolate.
    // So A has to reach me.

    // input == > N, M, k, x
    // input ==> cities having chocolate
    // input ==> M bidirectioal roads with (u,v,w)
    // input == > at last A and B.

    public static void main(String[] args) throws IOException {
        chocolateJourney();
    }

    // Simple tha, do bar dijikstra call karna tha

    // To humne kiya ki A se sare vertex tak pahunchne ka min time nikala
    // Same humne B ke liye nikala har vertex tak pahunchne ka min time

    // Ab agar kisi vertex pe chocolate hai to humne ye check kiya ki A ka us
    // chocolate tak ka pahunchne ka time kya tha aur B ko kitna time lga chocolate
    // tak pahunchne mai. Dono ko add kiya condition check karke melting wali. Bas
    // aise hume answer mila.

    // ! Important point to note :

    // # A chocolate wale vertex se B tak jaye ye B chocolate wale vertex tak aaye,
    // # time dono ko same he lagega.

    public static void dijikstra(int src, int[] dis, ArrayList<int[]>[] graph) { // Simple dijikstra lagaya

        // {v,wsf}
        PriorityQueue<int[]> que = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });

        que.add(new int[] { src, 0 });
        dis[src] = 0;

        while (que.size() != 0) {
            int size = que.size();

            while (size-- > 0) {
                int[] rn = que.remove();
                int u = rn[0], wsf = rn[1];

                // # Equal to wali condition lagani he nhi chahiye. Bas greater wali lagani
                // # chahiye agar hum yahan pe laga rahe hain to.
                if (wsf > dis[u]) // Equal to wali condition nhi lagayi kyunki aisa ho sakta hai ki A ko chocolate
                                  // wali jahah tak aane mai same time laga per B ko wahan aane mai alag alag time
                                  // lag sakta hai to humko sabko compare karna hai.
                    continue;

                for (int[] e : graph[u]) {
                    int v = e[0], wt = e[1];
                    if (wsf + wt < dis[v]) {
                        dis[v] = wsf + wt;
                        que.add(new int[] { v, wsf + wt });
                    }
                }
            }

        }
    }

    public static void chocolateJourney() throws IOException {
        // Agar koi mera function exception throw karta hai to mai bhi exception throw
        // karunga
        Reader scn = new Reader();
        int n = scn.nextInt();
        int m = scn.nextInt();
        int k = scn.nextInt();
        int x = scn.nextInt();

        boolean[] chocolate = new boolean[n + 1];
        for (int i = 0; i < k; i++) // Marked the chocolate vertex true.
            chocolate[scn.nextInt()] = true;

        ArrayList<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        while (m-- > 0) { // ? Created graph
            int u = scn.nextInt(), v = scn.nextInt(), w = scn.nextInt();
            graph[u].add(new int[] { v, w });
            graph[v].add(new int[] { u, w });
        }

        int A = scn.nextInt(), B = scn.nextInt();

        // Dono ke distance array ko fill karaya taki min distance mil jaye har vertex
        // tak.
        int[] disA = new int[n + 1];
        Arrays.fill(disA, (int) 1e9);
        dijikstra(A, disA, graph);

        int[] disB = new int[n + 1];
        Arrays.fill(disB, (int) 1e9);
        dijikstra(B, disB, graph);

        int ans = (int) 1e9;
        for (int i = 0; i <= n; i++) {
            if (chocolate[i]) {
                // disA[i] != (int) 1e9 == > Agar manle A ko chocolate mili he nhi, to wo kabhi
                // ` bhi B tak chocolate leke jaa he nhi payega

                // disB[i] < x ==> B ka chocolate wali vertex tak chocolate ko melt hone se
                // pehle pahunchna hoga. Same concept jaba A ko chocolate mili to A B tak jaye
                // ya B A tak aaye same dono ko same he time lagega

                if (disA[i] != (int) 1e9 && disB[i] < x) {
                    ans = Math.min(ans, disA[i] + disB[i]);
                }
            }
        }

        if (ans == (int) 1e9)
            System.out.println("-1");
        else
            System.out.print(ans);

    }

    static class Reader { // # class used for fast input output in java
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    } else {
                        continue;
                    }
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
