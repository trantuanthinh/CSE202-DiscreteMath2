import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

//100 done
public class FacebookFriends2 {

    static InputReader reader;

    public static void main(String[] args) throws IOException {
        reader = new InputReader(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int verticesNumber = reader.nextInt();
        int edgesNumber = reader.nextInt();
        Vertex[] verticesList = readGraph(verticesNumber, edgesNumber);
        List<ConnectedComponent> listConnectedComponents = new ArrayList<>();
        for (int i = 1; i < verticesList.length; i++) {
            if (!verticesList[i].isVisited) {
                ConnectedComponent connectedComponent = new ConnectedComponent();
                connectedComponent = DFS(verticesList[i], connectedComponent);
                listConnectedComponents.add(connectedComponent);
            }
        }
        Collections.sort(listConnectedComponents,
                (cc1, cc2) -> Integer.compare(cc1.representativeNumber, cc2.representativeNumber));
        for (ConnectedComponent connectedComponent : listConnectedComponents) {
            stringBuilder.append(connectedComponent.representativeNumber).append(" ")
                    .append(connectedComponent.countMale).append(" ")
                    .append(connectedComponent.countFemale).append("\n");
        }
        System.out.print(stringBuilder);
    }

    static public ConnectedComponent DFS(Vertex vertex, ConnectedComponent connectedComponent) {
        vertex.isVisited = true;
        connectedComponent.addVertices(vertex);
        connectedComponent.representativeNumber = vertex.id > connectedComponent.representativeNumber ? vertex.id
                : connectedComponent.representativeNumber;
        if (vertex.gender.equalsIgnoreCase("Nam")) {
            connectedComponent.countMale++;
        } else {
            connectedComponent.countFemale++;
        }
        for (Vertex vertex2 : vertex.adjacentVertices) {
            if (!vertex2.isVisited) {
                DFS(vertex2, connectedComponent);
            }
        }
        return connectedComponent;
    }

    static public Vertex[] readGraph(int numberOfVertices, int numberOfEdges) throws IOException {
        Vertex[] verticesList = new Vertex[numberOfVertices + 1];
        for (int i = 1; i < verticesList.length; i++) {
            int id = i;
            String gender = reader.nextLine();
            verticesList[id] = new Vertex(id, gender);
        }

        for (int i = 0; i < numberOfEdges; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            verticesList[v].addFriendList(verticesList[u]);
            verticesList[u].addFriendList(verticesList[v]);
        }
        return verticesList;
    }

    static public class ConnectedComponent {
        private List<Vertex> listVertices;
        private int countMale;
        private int countFemale;
        private int representativeNumber = 0;

        public ConnectedComponent() {
            this.listVertices = new ArrayList<>();
            this.countMale = 0;
            this.countFemale = 0;
        }

        public void addVertices(Vertex vertex) {
            this.listVertices.add(vertex);
        }
    }

    static public class Vertex {
        private int id;
        private String gender;
        private boolean isVisited = false;
        private List<Vertex> adjacentVertices = new ArrayList<>();

        public Vertex(int id, String gender) {
            this.id = id;
            this.gender = gender;
        }

        public void addFriendList(Vertex vertex) {
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