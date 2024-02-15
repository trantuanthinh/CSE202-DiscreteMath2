import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

//100 done

//Pre-Order:     Root - Left - Right
//In-Order:      Left - Root - Right
//Post-Order:    Left - Right - Root
public class PostOrder {

    static InputReader reader;
    static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        int verticesNumber = reader.nextInt();

        Vertex[] verticesList = readGraph(verticesNumber);
        DFS(verticesList[1]);
        System.out.println(stringBuilder);
    }

    static public StringBuilder DFS(Vertex vertex) {
        if (vertex != null) {
            DFS(vertex.leftVertex);
            DFS(vertex.rightVertex);
            stringBuilder.append(vertex.id).append(" ");
        }
        return stringBuilder;
    }

    static public Vertex[] readGraph(int numberOfVertices) {
        Vertex[] verticesList = new Vertex[numberOfVertices + 1];
        for (int i = 0; i < numberOfVertices; i++) {
            int id = i + 1;
            verticesList[id] = new Vertex(id);
        }

        for (int i = 1; i <= numberOfVertices; i++) {
            int leftIndex = reader.nextInt();
            int rightIndex = reader.nextInt();
            verticesList[i].leftVertex = leftIndex > 0 ? verticesList[leftIndex] : null;
            verticesList[i].rightVertex = rightIndex > 0 ? verticesList[rightIndex] : null;
        }
        return verticesList;
    }

    static public class Vertex {
        private int id;
        private boolean isVisited = false;
        public Vertex leftVertex;
        public Vertex rightVertex;

        public Vertex(int id) {
            this.id = id;
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