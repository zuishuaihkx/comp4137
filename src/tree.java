public class MerkleTree {

    public static String sha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildMerkleTree(List<Transaction> transactions) {
        List<String> tree = new ArrayList<>();
        for (Transaction transaction : transactions) {
            tree.add(transaction.getTransaction_id());
        }

        while (tree.size() > 1) {
            List<String> newTree = new ArrayList<>();

            for (int i = 0; i < tree.size(); i += 2) {
                String concatData = tree.get(i) + tree.get(i + 1);
                newTree.add(sha256(concatData));
            }

            tree = newTree;
        }

        return tree.get(0);
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("data1", "input1", "output1"));
        transactions.add(new Transaction("data2", "input2", "output2"));
        transactions.add(new Transaction("data3", "input3", "output3"));
        transactions.add(new Transaction("data4", "input4", "output4"));

        String merkleRoot = buildMerkleTree(transactions);
        System.out.println("Merkle Root: " + merkleRoot);
    }
}