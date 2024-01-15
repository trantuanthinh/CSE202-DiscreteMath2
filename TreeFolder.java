import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class TreeFolder {

    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();

        Hashtable<String, Vertex> verticesTable = readGraph(verticesNumber);
        Vertex rootVertex = new Vertex(null);
        for (Vertex vertex : verticesTable.values()) {
            if (vertex.root) {
                rootVertex = vertex;
            }
            Collections.sort(vertex.adjacentVertices,
                    (vertex1, vertex2) -> vertex1.name.compareToIgnoreCase(vertex2.name));
        }
        stringBuilder = DFS(rootVertex, stringBuilder, 0);
        System.out.println(stringBuilder);
    }

    static public StringBuilder DFS(Vertex vertex, StringBuilder stringBuilder, int level) {
        vertex.isVisited = true;
        if (level == 0) {
            stringBuilder.append("-").append(vertex.name).append("\n");
        } else {
            stringBuilder.append("-");
            for (int i = 0; i < level; i++) {
                stringBuilder.append("---");
            }
            stringBuilder.append(vertex.name).append("\n");
        }
        for (Vertex vertex2 : vertex.adjacentVertices) {
            if (!vertex2.isVisited) {
                level++;
                DFS(vertex2, stringBuilder, level);
                level--;
            }
        }
        return stringBuilder;
    }

    static public Hashtable<String, Vertex> readGraph(int numberOfVertices) throws IOException {
        Hashtable<String, Vertex> verticesTable = new Hashtable<>();
        for (int i = 0; i < numberOfVertices - 1; i++) {
            String u = reader.nextLine();
            String v = reader.nextLine();
            if (!verticesTable.containsKey(u)) {
                Vertex vertex = new Vertex(u);
                verticesTable.put(u, vertex);
            }

            if (!verticesTable.containsKey(v)) {
                Vertex vertex = new Vertex(v);
                verticesTable.put(v, vertex);
            }
            verticesTable.get(u).addAdjacentVertex(verticesTable.get(v));
            verticesTable.get(v).addAdjacentVertex(verticesTable.get(u));
        }
        String root = reader.nextLine();
        verticesTable.get(root).setRoot();
        return verticesTable;
    }

    static public class Vertex {
        private String name;
        private boolean isVisited = false;
        private boolean root = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(String name) {
            this.name = name;
        }

        public int getDegree() {
            return this.adjacentVertices.size();
        }

        public void addAdjacentVertex(Vertex vertex) {
            this.adjacentVertices.add(vertex);
        }

        public void setRoot() {
            this.root = true;
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