import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class Block {
    private String hash;
    private String previousHash;
    private String merkleRoot;
    private long timeStamp;
    MerkleTree transaction;
    Block nextBlock = null;

    public String getPreviousHash(){
        return previousHash;
    }

    public Block(MerkleTree merkleTree, String previousHash){
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calcHash();
        this.merkleRoot = merkleTree.getRoot().getValue();
        this.transaction = merkleTree;
    }
    public String calcHash(){
        //Hashing function
        MessageDigest digest = null;
        String content = previousHash + timeStamp + merkleRoot;

        try{
            digest = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        //Encode data to String
        byte[] hash = digest.digest(content.getBytes());
        String encodeDdata = Base64.getEncoder().encodeToString(hash);
        return encodeDdata;
    }
    public String getHash(){
        return hash;
    }

    public void setMerkleRoot(String MerkleRoot){
        this.merkleRoot = MerkleRoot;
    }

    public String getMerkleRoot(){
        return merkleRoot;
    }

    public void setMerkleTree(MerkleTree tree) {
        this.transaction = tree;
    }

    public MerkleTree getMerkleTree() {
        return this.transaction;
    }

    public void setPreviousHash(String previousHash){
        this.previousHash = previousHash;
    }
}

