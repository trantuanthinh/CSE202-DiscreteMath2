import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//100 done
public class BirthdayGifts {

    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        int edgesNumber = reader.nextInt();
        int currentDay = reader.nextInt();
        int nextKDays = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber, edgesNumber, currentDay, nextKDays);
        for (Vertex vertex : verticesList) {
            if (!vertex.isVisited) {
                BFS(vertex);
            }
        }
        for (Vertex vertex : verticesList) {
            stringBuilder.append(vertex.count).append(" ");
        }
        System.out.println(stringBuilder);
    }

    static public void BFS(Vertex vertex) {
        Queue<Vertex> queue = new LinkedList<>();
        vertex.isVisited = true;
        queue.add(vertex);
        while (!queue.isEmpty()) {
            Vertex checkVertex = queue.poll();
            for (Vertex vertex2 : checkVertex.adjacentVertices) {
                if (vertex2.hasInNextKDays) {
                    checkVertex.count += 1;
                }
                if (!vertex2.isVisited) {
                    vertex2.isVisited = true;
                    queue.add(vertex2);
                }
            }
        }
    }

    static public Vertex[] readGraph(int numberOfVertices, int edgesNumber, int currentDay, int nextKDays)
            throws IOException {
        Vertex[] verticesList = new Vertex[numberOfVertices];
        for (int i = 0; i < verticesList.length; i++) {
            int id = i;
            int date = reader.nextInt();
            verticesList[id] = new Vertex(id, date, currentDay, nextKDays);
        }

        for (int i = 0; i < edgesNumber; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[v].addAdjacentVertices(verticesList[u]);
            verticesList[u].addAdjacentVertices(verticesList[v]);
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private int date;
        private int count = 0;
        private boolean hasInNextKDays = false;
        private boolean isVisited = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id, int date, int currentDay, int nextKDays) {
            this.id = id;
            this.date = date;
            int inNextKDays = currentDay + nextKDays;
            if (inNextKDays > 365) {
                inNextKDays = inNextKDays - 365;
                if (currentDay <= date || date <= inNextKDays) {
                    this.hasInNextKDays = true;
                }
            } else if (currentDay <= date && date <= inNextKDays) {
                this.hasInNextKDays = true;
            }
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