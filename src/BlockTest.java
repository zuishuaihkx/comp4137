import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        // Create transactions
        BlockchainAccount a = new BlockchainAccount("AccountA");
        BlockchainAccount b = new BlockchainAccount("AccountB);
        BlockchainAccount c = new BlockchainAccount(AccountC);

        String one_coin = "10";
        String one_sig = a.generateDigitalSignature(one_coin);
        String one_data = one_coin + ":" + one_sig;
        Transaction one = new Transaction(one_data, a.getPublicKey(), b.getPublicKey());

        String two_coin = "11";
        String two_sig = b.generateDigitalSignature(two_coin);
        String two_data = two_coin + ":" + two_sig;
        Transaction two = new Transaction(two_data, b.getPublicKey(), a.getPublicKey());

        String three_coin = "12";
        String three_sig = a.generateDigitalSignature(three_coin);
        String three_data = three_coin + ":" + three_sig;
        Transaction three = new Transaction(three_data, a.getPublicKey(), c.getPublicKey());

        String four_coin = "13";
        String four_sig = c.generateDigitalSignature(four_coin);
        String four_data = four_coin + ":" + four_sig;
        Transaction four = new Transaction(four_data, c.getPublicKey(), b.getPublicKey());

        // Create transaction list
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(one);
        transactions.add(two);
        transactions.add(three);
        transactions.add(four);

        // Create Merkle tree
        MerkleTree merkleTree = new MerkleTree(transactions);
        System.out.println("Original Merkle Root: " + merkleTree.getRoot().getValue());

        // Create block
        String previousHash = "0";
        Block block = new Block(merkleTree, previousHash);
        String init_hash = block.getHash();
        String init_merkleroot = block.getMerkleRoot();
        System.out.println("Create a new block");
        // Check if block has been tampered with
        if (!block.getHash().equals(init_hash)) {
            System.out.println("The block has been falsified.");
        } else {
            System.out.println("The block is a valid block.");
        }

        if (!block.getMerkleRoot().equals(init_merkleroot)) {
            System.out.println("This transaction has been falsified.");
        } else {
            System.out.println("The transaction is valid.");
        }
        System.out.println("Tamper a block");
        // Simulate tampering with a transaction
        one_coin = "50"; // Modify transaction amount
        one_sig = a.generateDigitalSignature(one_coin);
        one_data = one_coin + ":" + one_sig;
        one = new Transaction(one_data, a.getPublicKey(), b.getPublicKey());

        // Update transaction list
        transactions.set(0, one); // Update the first transaction

        // Create new Merkle tree
        MerkleTree tamperedMerkleTree = new MerkleTree(transactions);
        System.out.println("Tampered Merkle Root: " + tamperedMerkleTree.getRoot().getValue());

        // Update the Merkle root of the block
        block.setMerkleRoot(tamperedMerkleTree.getRoot().getValue());

        // Check if tampered block has been falsified
        if (!block.getHash().equals(init_hash)) {
            System.out.println("The block has been falsified.");
        } else {
            System.out.println("The block is a valid block.");
        }

        if (!block.getMerkleRoot().equals(init_merkleroot)) {
            System.out.println("This transaction has been falsified.");
        } else {
            System.out.println("The transaction is valid.");
        }
    }
}