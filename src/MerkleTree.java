import jdk.nashorn.internal.ir.Block;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.security.*;
import java.util.LinkedList;
import java.util.Queue;
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
            MerkleNode leafNode = new MerkleNode(sha256(i.getTransaction_id()));
            leafNode.setTransaction(i);
            leaf.add(leafNode);
        }
        return leaf;
    }
    public void generateTree() {
        List<MerkleNode> original = generateLeafNode(this.transactions);

        while (true) {
            List<MerkleNode> generate = new ArrayList<>();
            for (int i = 0; i < original.size()-1; i+=2) {
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
    public void printTree() {
        printSubtree(root, 0); // 从根节点开始，层级为0
    }

    private void printSubtree(MerkleNode node, int level) {
        if (node == null) {
            return; // 空节点不打印
        }
        // 打印当前节点的值，使用"--"来表示层级深度
        for (int i = 0; i < level; i++) {
            System.out.print("--"); // 层级深度的表示
        }
        System.out.println(node.getValue());
        // 递归打印左子树，层级加1
        printSubtree(node.getLeftNode(), level + 1);
        // 递归打印右子树，层级加1
        printSubtree(node.getRightNode(), level + 1);
    }

//    public static void main(String[] args) {
//        BlockchainAccount a = new BlockchainAccount();
//        BlockchainAccount b = new BlockchainAccount();
//        BlockchainAccount c =new BlockchainAccount();
//
////        Transaction one
//        String one_coin="10";
//        String one_sig=a.generateDigitalSignature(one_coin);
//        String one_data=one_coin+":"+one_sig;
//        Transaction one=new Transaction(one_data,a.getPublicKey(),b.getPublicKey());
//
////        Transaction two
//        String two_coin="11";
//        String two_sig=b.generateDigitalSignature(two_coin);
//        String two_data=two_coin+":"+two_sig;
//        Transaction two=new Transaction(two_data,b.getPublicKey(),a.getPublicKey());
//
////        Transaction three
//        String three_coin="12";
//        String three_sig=a.generateDigitalSignature(three_coin);
//        String three_data=three_coin+":"+three_sig;
//        Transaction three=new Transaction(three_data,a.getPublicKey(),c.getPublicKey());
//
////        Transaction one
//        String four_coin="13";
//        String four_sig=c.generateDigitalSignature(four_coin);
//        String four_data=four_coin+":"+four_sig;
//        Transaction four=new Transaction(four_data,c.getPublicKey(),b.getPublicKey());
//
//        List<Transaction> transacations=new ArrayList<>();
//        transacations.add(one);
//        transacations.add(two);
//        transacations.add(three);
//        transacations.add(four);
//
//        MerkleTree tree= new MerkleTree(transacations);
//        System.out.println(tree.getRoot().getValue());
//
//        tree.printTree();
//
//
//    }
}
