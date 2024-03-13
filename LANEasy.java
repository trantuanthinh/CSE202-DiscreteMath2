import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class LANEasy {

    static InputReader reader;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber);
        DFS(verticesList[0], 0);
        System.out.println(maxLength);
    }

    static int maxLength = 0;

    static public void DFS(Vertex vertex, int lengthFromRoot) {
        vertex.isVisited = true;
        for (Edge edge : vertex.edgesList) {
            if (!edge.vertex.isVisited) {
                maxLength = Math.max(maxLength, lengthFromRoot + edge.length);
                DFS(edge.vertex, edge.length + lengthFromRoot);
            }
        }
    }

    static public Vertex[] readGraph(int numberOfVertices)
            throws IOException {
        Vertex[] verticesList = new Vertex[numberOfVertices];
        for (int i = 0; i < verticesList.length; i++) {
            int id = i;
            verticesList[id] = new Vertex(id);
        }

        for (int i = 0; i < numberOfVertices - 1; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            int distance = reader.nextInt();

            verticesList[v].addEdges(new Edge(distance, verticesList[u]));
            verticesList[u].addEdges(new Edge(distance, verticesList[v]));

        }
        return verticesList;
    }

    static public class Edge {
        private int length;
        private Vertex vertex;

        public Edge(int length, Vertex vertex) {
            this.length = length;
            this.vertex = vertex;
        }
    }

    static public class Vertex {
        @SuppressWarnings("unused")
        private int id;
        @SuppressWarnings("unused")
        private int lengthFromRoot;
        private boolean isVisited = false;
        private List<Edge> edgesList = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public void addEdges(Edge edge) {
            this.edgesList.add(edge);
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