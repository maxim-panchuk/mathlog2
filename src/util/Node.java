package util;
public class Node {

    public NodeType nodeType;
    public String data;
    public Node parentNode;
    public Node leftChild;
    public Node rightChild;

    public Node (NodeType nodeType, String data,
                 Node parentNode, Node leftChild,
                 Node rightChild) {
        this.nodeType = nodeType;
        this.data = data;
        this.parentNode = parentNode;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void printNode () {
        System.out.println("NODE_TYPE"
                + nodeType + " DATA: " + data);
    }
}
