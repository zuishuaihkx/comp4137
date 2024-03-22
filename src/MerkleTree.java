import jdk.nashorn.internal.ir.Block;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.security.*;
public class MerkleTree {

    private MerkleNode root = null;

    private List<Transaction> transactions;
    public MerkleTree(List<Transaction> transactions) {
        this.transactions = transactions;
        generateTree();
    }

    public List<MerkleNode> generateLeafNode(List<Transaction> transactions) {
        List<MerkleNode> leaf = new ArrayList<>();
        for(Transaction i : transactions) {
            leaf.add(new MerkleNode(sha256(i.getTransaction_id())));
        }
        return leaf;
    }
    public void generateTree() {
        List<MerkleNode> original = generateLeafNode(this.transactions);

        while (true) {
            List<MerkleNode> generate = new ArrayList<>();
            for (int i = 0; i < original.size()-1; i++) {
                String value = original.get(i).getValue() + original.get(i+1).getValue();
                MerkleNode generateNode = new MerkleNode(sha256(value));
                generate.add(generateNode);
                generateNode.setLeftNode(original.get(i));
                generateNode.setRightNode(original.get(i+1));
            }
            original = generate;
            if (generate.size() == 1) {
                this.root = generate.get(0);
                break;
            }
        }
    }

    public MerkleNode getRoot() {
        return root;
    }

    // Hash function. Input data is transcation_id. Output data is hash(transcation_id).
    public static String sha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void checkTransaction(Transaction one, BlockchainAccount b, BlockchainAccount a){
        String receivedData = one.getData();
        String receivedCoin = receivedData.substring(0, receivedData.indexOf(":"));
        String receivedSignature = receivedData.substring(receivedData.indexOf(":") + 1);

        boolean isSignatureValid = b.verifyDigitalSignature(receivedCoin, receivedSignature, a.getPublicKey());
        if (isSignatureValid) {
            System.out.println("Verified!");
        } else {
            System.out.println("Unverified!");
        }

    }
    public static void main(String[] args) {
        BlockchainAccount a = new BlockchainAccount();
        BlockchainAccount b = new BlockchainAccount();
        String coin="10";
        String sig=a.generateDigitalSignature(coin);
        String data=coin+":"+sig;
        Transaction one=new Transaction(data,a.getPublicKey(),b.getPublicKey());




    }
}
