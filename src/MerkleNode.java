public class MerkleNode {
    private MerkleNode leftNode = null;
    private MerkleNode rightNode = null;

    private String value;

    public MerkleNode(String value) {
        this.value = value;
    }
    public void setLeftNode(MerkleNode node){
        leftNode = node;
    }

    public void setRightNode(MerkleNode node) {
        rightNode = node;
    }

    public String getValue() {
        return value;
    }
}
