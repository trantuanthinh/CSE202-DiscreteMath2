import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class Overflow {

    static InputReader reader;
    static List<Vertex> resultList = new ArrayList<Vertex>();

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber);
        DFS(verticesList[0]);
        Collections.sort(resultList, (vertex1, vertex2) -> Double.compare(vertex1.id, vertex2.id));
        for (Vertex vertex : resultList) {
            stringBuilder.append(vertex.id).append(" ").append(vertex.weight).append("\n");
        }
        System.out.println(stringBuilder);
    }

    static public void DFS(Vertex vertex) {
        vertex.isVisited = true;
        double dividedWater = vertex.weight;
        if (vertex.getDegree() > 0) {
            dividedWater = vertex.weight / vertex.getDegree();
            for (Vertex vertex2 : vertex.adjacentVertices) {
                if (!vertex2.isVisited) {
                    vertex2.weight += dividedWater;
                    DFS(vertex2);
                }
            }
        } else {
            resultList.add(vertex);
        }
    }

    static public Vertex[] readGraph(int numberOfVertices) throws IOException {
        Vertex[] verticesList = new Vertex[numberOfVertices];
        for (int i = 0; i < verticesList.length; i++) {
            int id = i;
            double weight = reader.nextDouble();
            verticesList[id] = new Vertex(id, weight);
        }

        for (int i = 0; i < numberOfVertices - 1; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[v].addAdjacentVertices(verticesList[u]);
            // verticesList[u].addAdjacentVertices(verticesList[v]);
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private double weight;
        private boolean isVisited = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id, double weight) {
            this.id = id;
            this.weight = weight;
        }

        public void addAdjacentVertices(Vertex vertex) {
            this.adjacentVertices.add(vertex);
        }

        public double getDegree() {
            return (double) this.adjacentVertices.size();
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