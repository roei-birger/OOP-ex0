package ex0;

import java.util.*;

/**
 * This class implements graph_algorithms interface that represents a "regular" Graph Theory algorithms including:
 * 1. copy graph (deep and shallow copies).
 * 2. chek if the graph is linked.
 * 3. chek the length of the shortest path between two nodes and create a list of the shortest path between the nodes.
 * Field Summary:
 * my_g - A graph-type object that represents the graph in which algorithms operate.
 */

public class Graph_Algo implements graph_algorithms {

    private graph my_g;

    /**
     * Constructs a Graph_DS with not vertices.
     */
    public Graph_Algo() {
        this.my_g = new Graph_DS();
    }

    /**
     * Passes 'my_g' to be the pointer of the graph (g) that received.
     *
     * @param g
     */
    @Override
    public void init(graph g) {
        this.my_g = g;
    }

    /**
     * Build a deep copy of the received graph.
     * By calling the Graph_DS copy constructor.
     *
     * @return the created graph.
     */
    @Override
    public graph copy() {
        graph graphToCopy = new Graph_DS(my_g);
        return graphToCopy;
    }

    /**
     * Resets the tags in the graph.
     *
     * @param g
     */
    public void resetTags(graph g) {
        for (node_data t : g.getV()) {
            t.setTag(0);
        }
    }

    /**
     * -First the method resets all the vertices in the graph.
     * -If the sum of vertices in the graph are less than 2, return true.
     * -We'll put one random vertex in queue and change his tag to 1.
     * -By loop we will take out the queue each time a different vertex and put in the queue all its neighbors.
     * After the loop we will compare the number of times the loop was executed to
     * the number of vertices in the graph and so we will know if
     * we passed all the vertices and if the graph is connected.
     *
     * @return true if there is a path from every node to each other node.
     */
    @Override
    public boolean isConnected() {
        if (my_g == null) {
            return true;
        }
        resetTags(my_g);
        if (my_g.nodeSize() < 2) return true;
        Queue<node_data> q = new LinkedList<>();
        node_data first = my_g.getV().iterator().next();
        first.setTag(1);
        q.add(first);
        int count = 0;
        node_data temp;
        while (!q.isEmpty()) {
            temp = q.poll();
            if (temp.getTag() == 1) {
                for (node_data i : temp.getNi()) {
                    if (i.getTag() == 0) {
                        q.add(i);
                        i.setTag(1);
                    }
                }
            }
            temp.setTag(2);
            count++;
        }

//        for (node_data i : my_g.getV()) {
//            if (i.getTag() != 2)
//                return false;
//            }
//        return true;
        return my_g.nodeSize() == count;

    }

    /**
     * The method resets the graph and then checks all the vertices by using the BFS method.
     * which each time increases the tag by 1 (the tag represents the distance of the SCR vertex).
     * Finally we return the tag of the DEST vertex.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between src to dest.
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        resetTags(my_g);
        Queue<node_data> q = new LinkedList<>();
        q.add(my_g.getNode(src));
        node_data temp;
        while (!q.isEmpty()) {
            temp = q.poll();
            if (temp.getKey() == dest)
                return temp.getTag();
            else
                for (node_data i : temp.getNi()) {
                    if (i.getTag() == 0) {
                        i.setTag(temp.getTag() + 1);
                        q.add(i);
                    } else if (temp.getTag() + 1 < i.getTag()) {
                        i.setTag(temp.getTag() + 1);
                        q.add(i);
                    }
                }
        }
        return -1;
    }

    /**
     * The method resets the graph and then calls "shortestPathDist" method on the graph.
     * After that the method moves from the DEST vertex to SRC vertex,
     * each time inserts the vertex with the tag closer to the SRC into the list.
     * The method will be run until the SRC vertex enters the list.
     * Finally we return the created list.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the the shortest path between src to dest - as an ordered List of nodes.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        int destSize = shortestPathDist(src, dest);
        node_data temp = my_g.getNode(dest);
        LinkedList<node_data> finalList = new LinkedList<>();
        finalList.addFirst(temp);
        if (dest == src) {
            return finalList;
        }
        while (destSize > 1) {

            for (node_data i : temp.getNi()) {
                if (i.getTag() == (destSize - 1)) {
                    temp = i;
                    finalList.addFirst(temp);
                    break;
                }

            }
            destSize--;
        }

        finalList.addFirst(my_g.getNode(src));
        return finalList;
    }


}
