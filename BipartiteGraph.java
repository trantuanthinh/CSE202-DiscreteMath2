import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class BipartiteGraph {

    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int numberTestCases = reader.nextInt();
        for (int i = 0; i < numberTestCases; i++) {
            int verticesNumber = reader.nextInt();
            int edgesNumber = reader.nextInt();
            Vertex[] verticesList = readGraph(verticesNumber, edgesNumber);
            boolean isBipartite = true;
            for (Vertex vertex : verticesList) {
                if (!vertex.isVisited) {
                    if (!DFS(vertex)) {
                        isBipartite = false;
                        break;
                    }
                }
            }
            if (isBipartite) {
                stringBuilder.append("Yes").append("\n");
            } else {
                stringBuilder.append("No").append("\n");
            }
        }
        System.out.println(stringBuilder);
    }

    static public boolean DFS(Vertex vertex) {
        vertex.isVisited = true;
        for (Vertex vertex2 : vertex.adjacentVertices) {
            if (!vertex2.isVisited) {
                vertex2.set = !vertex.set;

                if (!DFS(vertex2)) {
                    return false;
                }
                ;
            } else if (vertex2.set == vertex.set) {
                return false;
            }
        }
        return true;
    }

    static public Vertex[] readGraph(int numberOfVertices, int numberOfEdges) {
        Vertex[] verticesList = new Vertex[numberOfVertices];
        for (int i = 0; i < verticesList.length; i++) {
            int id = i;
            verticesList[id] = new Vertex(id);
        }
        for (int i = 0; i < numberOfEdges; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[u].addAdjacentVertex(verticesList[v]);
            verticesList[v].addAdjacentVertex(verticesList[u]);
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private boolean isVisited = false;
        private boolean set = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public int getDegree() {
            return this.adjacentVertices.size();
        }

        public void addAdjacentVertex(Vertex vertex) {
            this.adjacentVertices.add(vertex);
        }

        public String toString() {
            return String.valueOf(this.id);
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