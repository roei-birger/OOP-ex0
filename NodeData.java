package ex0;

import java.util.Collection;
import java.util.HashMap;

/**
 * This class implements node_data interface that represents the set of operations applicable on a
 * node (vertex) in an (undirectional) unweighted graph.
 * The vertex's neighbors are implemented by HashMap for high efficiency.
 */
public class NodeData implements node_data {
    private static int id = 0;
    private HashMap<Integer, node_data> neighbors;
    int key;
    private int tag;
    private String info;


    /**
     * Constructs a NodeData with minimal fields
     */
    public NodeData() {
        this.key = id++;
        this.tag = 0;
        this.info = null;
        this.neighbors = new HashMap<>();

    }

    /**
     * Constructs a NodeData with the same fields
     * as the specified NodeData object that was received.
     *
     * @param node
     */

    public NodeData(node_data node) {
        if (node != null) {
            this.neighbors = new HashMap<>();
            this.tag = node.getTag();
            this.info = node.getInfo();
            this.key = node.getKey();

        }
    }
    /**
     * @return the node's key.
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * @return a collection with all the Neighbor nodes of this vertex.
     */
    @Override
    public Collection<node_data> getNi() {
        return this.neighbors.values();
    }

    /**
     * @param key
     * @return true if have an edge between the vertices.
     */
    @Override
    public boolean hasNi(int key) {
        return this.key == key | this.neighbors.containsKey(key);
    }

    /**
     * This function make edge between this vertex and node_data (t).
     *
     * @param t
     */
    @Override
    public void addNi(node_data t) {
        if (!neighbors.containsKey(t.getKey()) & t.getKey() != this.key)
            this.neighbors.put(t.getKey(), t);
    }

    /**
     * Removes the edge between this vertex and node.
     *
     * @param node
     */
    @Override
    public void removeNode(node_data node) {
        if (neighbors.containsKey(node.getKey()))
            this.neighbors.remove(node.getKey());
    }

    /**
     * @return the node's info.
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Sets the info of the vertex.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * @return the node's tag.
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Sets the tag of the vertex.
     *
     * @param t
     */
    @Override
    public void setTag(int t) {
        tag = t;
    }

}
