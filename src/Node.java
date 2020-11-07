final class Node {
    boolean EEX;
    double fugit;
    double stockPrice;
    int n;
    Node lChild;
    Node rChild;
    double intrinsicValue;
    double fairValue;

    public Node(double stockPrice, int n) {
        this.stockPrice = stockPrice;
        this.n = n;
    }

    public void setChildren(Node left, Node right){
        rChild = right;
        lChild = left;
    }
}
