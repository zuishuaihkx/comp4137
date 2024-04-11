// Node class
public class MerkleNode {
    private MerkleNode leftNode = null;
    private MerkleNode rightNode = null;

    private String value;

    private Transaction transaction = null;

    public MerkleNode(String value) {
        this.value = value;
    }
    public void setLeftNode(MerkleNode node){
        leftNode = node;
    }

    public void setRightNode(MerkleNode node) {
        rightNode = node;
    }

    public void setTransaction(Transaction transaction) {this.transaction = transaction;}

    public Transaction getTransaction() {
        return transaction;
    }

    public String getValue() {
        return value;
    }

    public MerkleNode getLeftNode(){
        return leftNode;
    }

    public MerkleNode getRightNode() {
        return rightNode;
    }
}
