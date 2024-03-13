import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

//77.5
public class BalancedTreeEasy {

    static InputReader reader;
    static List<Number> resuList = new ArrayList<>();
    static long minWeight = Long.MAX_VALUE;
    static long tempWeight = 0;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber);

        for (int i = 1; i < verticesList.length; i++) {
            if (verticesList[i].adjacentVertices.size() == 2) {
                calculateSubTree(verticesList[i]);
                for (int j = 1; j < verticesList.length; j++) {
                    verticesList[j].isVisited = false;
                }
            }
        }
        for (Number each : resuList) {
            stringBuilder.append(each).append(" ");
        }
        System.out.println(stringBuilder);
    }

    static public long dfs(Vertex vertex) {
        vertex.isVisited = true;
        tempWeight += vertex.weight;
        for (Vertex vertex2 : vertex.adjacentVertices) {
            if (!vertex2.isVisited) {
                dfs(vertex2);
            }
        }
        return tempWeight;
    }

    static public void calculateSubTree(Vertex vertex) {
        vertex.isVisited = true;
        tempWeight = 0;
        long firstWeight = dfs(vertex.adjacentVertices.get(0));
        long edgeWeight1 = firstWeight;
        tempWeight = 0;
        long secondWeight = dfs(vertex.adjacentVertices.get(1));
        long edgeWeight2 = secondWeight;

        long diff = (long) Math.abs(edgeWeight1 - edgeWeight2);
        if (diff < minWeight) {
            minWeight = diff;
            resuList.clear();
            resuList.add(vertex.id);
            if (edgeWeight1 <= edgeWeight2) {
                resuList.add(edgeWeight1);
                resuList.add(edgeWeight2);
            } else {
                resuList.add(edgeWeight2);
                resuList.add(edgeWeight1);
            }
        }
    }

    static public Vertex[] readGraph(int numberOfVertices) throws IOException {
        Vertex[] verticesList = new Vertex[numberOfVertices + 1];
        for (int i = 1; i < verticesList.length; i++) {
            int id = i;
            int weight = reader.nextInt();
            verticesList[id] = new Vertex(id, weight);
        }

        for (int i = 0; i < numberOfVertices - 1; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[u].addAdjacentVertices(verticesList[v]);
            verticesList[v].addAdjacentVertices(verticesList[u]);
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private int weight = 0;
        private boolean isVisited = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id, int weight) {
            this.id = id;
            this.weight = weight;

        }

        public void addAdjacentVertices(Vertex vertex) {
            this.adjacentVertices.add(vertex);
        }
    }

    static public class InputReader {
        private byte[] inbuf = new byte[2 << 23];
        public int lenbuf = 0, ptrbuf = 0;
        public InputStream is;

        public InputReader(InputStream stream) throws IOException {
            inbuf = new byte[2 << 23];
            lenbuf = 0;
            ptrbuf = 0;
            is = System.in;
            lenbuf = is.read(inbuf);
        }

        public InputReader(FileInputStream stream) throws IOException {
            inbuf = new byte[2 << 23];
            lenbuf = 0;
            ptrbuf = 0;
            is = stream;
            lenbuf = is.read(inbuf);
        }

        public boolean hasNext() throws IOException {
            if (skip() >= 0) {
                ptrbuf--;
                return true;
            }
            return false;
        }

        public String nextLine() throws IOException {
            int b = skip();
            StringBuilder sb = new StringBuilder();
            while (!isSpaceChar(b) && b != ' ') { // when nextLine, ()
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public String next() {
            int b = skip();
            StringBuilder sb = new StringBuilder();
            while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b
                                        // != ' ')
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        private int readByte() {
            if (lenbuf == -1)
                throw new InputMismatchException();
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0)
                    return -1;
            }
            return inbuf[ptrbuf++];
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        @SuppressWarnings("unused")
        private double nextDouble() {
            return Double.parseDouble(next());
        }

        public Character nextChar() {
            return skip() >= 0 ? (char) skip() : null;
        }

        private int skip() {
            int b;
            while ((b = readByte()) != -1 && isSpaceChar(b))
                ;
            return b;
        }

        public int nextInt() {
            int num = 0, b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }

        public long nextLong() {
            long num = 0;
            int b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }
    }
}