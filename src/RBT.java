public class RBT {
    private Node root;
    private final Node TNIL;

    private void leftRotate(Node x){
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNIL){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == TNIL) {
            this.root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x){
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNIL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == TNIL) {
            this.root = y;
        }
        else if (x == x.parent.right) {
            x.parent.right = y;
        }
        else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(int key){
        Node z = new Node();
        z.key = key;
        z.parent = null;

        Node y = TNIL;
        Node x = this.root;

        while (x != TNIL){
            y = x;
            if (z.key < x.key){
                x = x.left;
            }
            else{
                x = x.right;
            }
        }

        z.parent = y;
        if (y == TNIL){
            this.root = z;
        }
        else if(z.key < y.key){
            y.left = z;
        }
        else{
            y.right = z;
        }

        z.color = Color.RED;
        z.left = TNIL;
        z.right = TNIL;

        insertFixup(z);
    }

    private void insertFixup(Node z) {
        Node y;
        while (z.parent.color == Color.RED){
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            }
            else{
                y = z.parent.parent.left;
                if (z.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    public RBT(){
        TNIL = new Node();
        TNIL.color = Color.BLACK;
        TNIL.left = null;
        TNIL.right = null;
        root = TNIL;
    }

    public String printDotGraph() {
        StringBuilder sb = new StringBuilder();
        printDotNode(root, true, 0, sb);
        return sb.toString();
    }

    private void printDotNode(Node node, boolean isRoot, int index, StringBuilder sb) {
        if (isRoot) {
            sb.append("digraph g0 {\n");
            sb.append("node [height=.1, style=filled];\n");
        }

        if (node != null) {
            sb.append(String.format("node%s_%s[label=\"%s\", color=black, fillcolor=%s, fontcolor=%s];\n",
                    index, node.key, node.key, node.color == Color.RED ? "red"
                            : "black", node.color == Color.RED ? "black" : "white"));

            if (node.left != TNIL) {
                printDotNode(node.left, false, index + 1, sb);
                sb.append(String.format("node%s_%s -> node%s_%s;\n", index, node.key,
                        index + 1, node.left.key));
            }
            else {
                sb.append(String.format("nil_node_l_%s_%s[ width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n",
                        index, node.key));
                sb.append(String.format("node%s_%s -> nil_node_l_%s_%s;\n", index,
                        node.key, index, node.key));
            }

            if (node.right != TNIL) {
                printDotNode(node.right, false, index + 1, sb);
                sb.append(String.format("node%s_%s -> node%s_%s;\n", index, node.key,
                        index + 1, node.right.key));
            }
            else {
                sb.append(String.format("nil_node_r_%s_%s[ width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n",
                        index, node.key));
                sb.append(String.format("node%s_%s -> nil_node_r_%s_%s;\n", index,
                        node.key, index, node.key));
            }
        } else {
            sb.append(String.format("nil_node_root_%s[width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n",
                    index));
        }

        if (isRoot) {
            sb.append("}");
        }
    }
}

class Node{
    Node parent, left, right;
    Color color;
    int key;
}

enum Color{
    BLACK,
    RED
}
