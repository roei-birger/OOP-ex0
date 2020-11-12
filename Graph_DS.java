package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class implements graph interface that represents an undirectional unweighted graph.
 * The implementation based on an efficient HashMap representation.
 * The HashMap implement is for high efficiency.
 * Field Summary:
 * mc - count of changes in the graph.
 * edSize - count of edges in the graph.
 * noSize - count of vertices (nodes) in the graph.
 * vertices - collection containing all the vertices (nodes) in the graph.
 */
public class Graph_DS implements graph {

    private HashMap<Integer, node_data> vertices;
    private int mc;
    private int edSize;
    private int noSize;

    /**
     * Constructs a graph with not vertices.
     */
    public Graph_DS() {
        this.mc = 0;
        this.vertices = new HashMap<>();
        this.edSize = 0;
        this.noSize = 0;
    }

    /**
     * Constructs a graph with the same fields
     * as the specified graph object that was received.
     *
     * @param gr
     */
    public Graph_DS(graph gr) {
        if (gr != null) {
            this.vertices = new HashMap<>();
            Iterator<node_data> t = gr.getV().iterator();
            while (t.hasNext()) {
                node_data temp2 = new NodeData(t.next());
                this.vertices.put(temp2.getKey(), temp2);
            }
            for (node_data nodeTemp : gr.getV()) {
                Iterator<node_data> t2 = nodeTemp.getNi().iterator();
                while (t2.hasNext()) {
                    node_data i = new NodeData(t2.next());
                    connect(i.getKey(), nodeTemp.getKey());
                }
            }

            this.mc = gr.getMC();
            this.edSize = gr.edgeSize();
            this.noSize = gr.nodeSize();
        }
    }

    /**
     * @param key - the node_id
     * @return the node of this key, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (this.vertices.containsKey(key)) {
            return this.vertices.get(key);
        }
        return null;
    }

    /**
     * @param node1
     * @param node2
     * @return true if node1 is a neighbor of node2 and node2 is a neighbor of node1.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (vertices.containsKey(node1) & vertices.containsKey(node2))
            return (getNode(node1).hasNi(node2) & getNode(node2).hasNi(node1));
        return false;

    }

    /**
     * Add a new node to the graph with the node_data (n) that was received.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        this.vertices.put(n.getKey(), n);
        if (vertices.size() == (noSize + 1)) {
            this.noSize++;
            this.mc++;
        }
    }

    /**
     * This function makes an edge between the vertices (node1 and node2)
     * by inserting one node into the neighbor's list of the others node.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2) {
        if (vertices.containsKey(node1) & vertices.containsKey(node2) & !hasEdge(node1, node2) & node1 != node2) {
            this.vertices.get(node2).addNi(this.vertices.get(node1));
            this.vertices.get(node1).addNi(this.vertices.get(node2));
            this.edSize++;
            this.mc++;
        }
    }

    /**
     * @return a collection with all the nodes in the graph.
     */
    @Override
    public Collection<node_data> getV() {
        return this.vertices.values();
    }

    /**
     * @return a collection with all the Neighbor nodes of the vertex that was received.
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        if (vertices.containsKey(node_id))
            return this.vertices.get(node_id).getNi();
        return null;
    }

    /**
     * The function passes over all the neighbors of the vertex and removes the common edges.
     * Finally delete the vertex from the graph.
     *
     * @param key
     * @return the deleted vertex.
     */
    @Override
    public node_data removeNode(int key) {
        if (!this.vertices.containsKey(key)) return null;
        node_data myNode = getNode(key);
        Iterator<node_data> itr = myNode.getNi().iterator();
        if (itr.hasNext()) {
            node_data t = itr.next();
            while (itr.hasNext()) {
                if (t.getKey() != key) {
                    removeEdge(t.getKey(), key);
                    myNode.removeNode(t);
                    itr = myNode.getNi().iterator();
                }
                if (itr.hasNext()) {
                    t = itr.next();
                }

            }

            if (t.getKey() != key) {
                removeEdge(t.getKey(), key);
                myNode.removeNode(t);

            }

        }

        this.vertices.remove(key);
        this.noSize--;
        this.mc++;
        return myNode;
    }

    /**
     * This function removes the edge between vertices node1 and node2
     * by deleting one node of the neighbor's list of the other node.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (vertices.containsKey(node1) & vertices.containsKey(node2) & hasEdge(node1, node2) & node1 != node2) {
            this.vertices.get(node1).removeNode(this.vertices.get(node2));
            this.vertices.get(node2).removeNode(this.vertices.get(node1));
            this.edSize--;
            this.mc++;
        }
    }

    /**
     * @return the number of vertices (nodes) in the graph.
     */
    @Override
    public int nodeSize() {
        return this.noSize;
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return this.edSize;
    }

    /**
     * @return the Mode Count of the changes made in the graph.
     */
    @Override
    public int getMC() {
        return this.mc;
    }
}
