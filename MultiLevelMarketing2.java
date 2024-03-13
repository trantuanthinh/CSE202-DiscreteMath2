import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class MultiLevelMarketing2 {
    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int numberOfVertices = reader.nextInt();
        Vertex[] list = readGraph(numberOfVertices);
        long initPrice = reader.nextLong();
        if (list[0].priceLimit >= initPrice) {
            dfs1(list[0], initPrice);
        }
        for (Vertex vertex : list) {
            stringBuilder.append(vertex.totalProduct).append(" ");
        }
        System.out.println(stringBuilder);
    }

    public static void dfs1(Vertex vertex, long initPrice) {
        vertex.isVisited = true;
        vertex.totalProduct++;
        for (Vertex vertex2 : vertex.adjecentVertices) {
            if (!vertex2.isVisited) {
                long price = (long) Math.floor(initPrice + (initPrice * 0.1));
                if (vertex2.priceLimit >= price) {
                    dfs1(vertex2, price);
                } else {
                    dfs2(vertex2);
                    vertex.totalProduct += countVertex;
                    countVertex = 0;
                }
            }
        }
    }

    static int countVertex = 0;

    // Count every vertex in sub-tree with root Vertex vertex
    public static void dfs2(Vertex vertex) {
        vertex.isVisited = true;
        countVertex++;
        for (Vertex vertex2 : vertex.adjecentVertices) {
            if (!vertex2.isVisited) {
                dfs2(vertex2);
            }
        }
    }

    public static Vertex[] readGraph(int numberOfVertices) {
        Vertex[] list = new Vertex[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            Vertex vertex = new Vertex(i);
            list[i] = vertex;
        }
        for (int i = 0; i < numberOfVertices - 1; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            list[u].addAdjecentVertices(list[v]);
            list[v].addAdjecentVertices(list[u]);
        }
        for (Vertex vertex : list) {
            long priceLimit = reader.nextLong();
            vertex.priceLimit = priceLimit;
        }
        return list;
    }

    public static class Vertex {
        @SuppressWarnings("unused")
        private int id;
        private long priceLimit;
        private boolean isVisited = false;
        private List<Vertex> adjecentVertices = new ArrayList<Vertex>();
        private int totalProduct;

        public Vertex(int id) {
            this.id = id;
        }

        public void addAdjecentVertices(Vertex vertex) {
            this.adjecentVertices.add(vertex);
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
