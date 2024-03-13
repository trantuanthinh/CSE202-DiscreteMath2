import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

//90.91
public class Treasures {

    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        int edgesNumber = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber, edgesNumber);
        for (Vertex vertex : verticesList) {
            Collections.sort(vertex.adjacentVertices,
                    (vertex1, vertex2) -> Integer.compare(((Vertex) vertex1).id, ((Vertex) vertex2).id));
        }
        Vertex checkVertex = verticesList[0];
        stringBuilder.append(checkVertex.id).append(" ");
        List<Vertex> path = Search(checkVertex);
        for (Vertex vertex : path) {
            stringBuilder.append(vertex.id).append(" ");
        }
        System.out.println(stringBuilder);
    }

    static public List<Vertex> Search(Vertex goal) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex : goal.adjacentVertices) {
            DFS(vertex, goal, path, new ArrayList<>());
        }
        return path;
    }

    static public void DFS(Vertex vertex, Vertex goal, List<Vertex> list, List<Vertex> temp) {
        temp.add(vertex);
        for (Vertex vertex2 : vertex.adjacentVertices) {
            if (vertex2.id == goal.id) {
                if (temp.size() >= 2 && list.size() < temp.size()) {
                    list.clear();
                    for (Vertex vertex3 : temp) {
                        list.add(vertex3);
                    }
                }
            } else if (!temp.contains(vertex2)) {
                DFS(vertex2, goal, list, temp);
            }
            temp.remove(vertex2);
        }
    }

    static public Vertex[] readGraph(int numberOfVertices, int numberOfEdges) {
        Vertex[] verticesList = new Vertex[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            verticesList[i] = new Vertex(i);
        }
        for (int i = 0; i < numberOfEdges; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[u].addAdjacentVertex(verticesList[v]);
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public void addAdjacentVertex(Vertex vertex) {
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
