import java.util.ArrayList;
import java.util.List;

public class NodesApi {
    private final static List<Node> NODES = new ArrayList<>();

    public synchronized List<Node> getNodes() {
        return new ArrayList<>(NODES);
    }

    public synchronized void addNode(Node node) {
        NODES.add(node);
    }
}
