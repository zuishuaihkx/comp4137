import java.util.ArrayList;
import java.util.Scanner;

public class test1_2 {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
//        BlockchainAccount a = new BlockchainAccount();
//        BlockchainAccount b = new BlockchainAccount();
//        BlockchainAccount c =new BlockchainAccount();
        ArrayList<BlockchainAccount> Account = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        MerkleTree merkletree=null;
        BlockChain blockchain = null;

        while (true) {
            System.out.println("");
            Scanner scanner = new Scanner(System.in);
            System.out.println("What do you want to do? 1: Create Account ; 2: Create transaction ; 3: build MerkleTree ; 4: build Block and BlockChain");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("please input account name: ");
                String accountName = scanner.nextLine();
                BlockchainAccount newone = new BlockchainAccount(accountName);
                Account.add(newone);
                System.out.println("Your public key is :");
                System.out.println(newone.getPublicKey());
                System.out.println("Your private key is :");
                System.out.println(newone.getPrivateKey());
                continue;

            }
            if (choice.equals("2")) {
                System.out.println("Enter you privateKey: ");
                String privateKey = scanner.nextLine();
                BlockchainAccount user = null;
                Boolean find_user = false;
                Boolean find_receiver = false;
                for (int i = 0; i < Account.size(); i++) {
                    if (Account.get(i).getPrivateKey().equals(privateKey)) {
                        user = Account.get(i);
                        find_user = true;
                        break;
                    }
                }
                if (find_user) {
                    System.out.println("Enter value you want to transfer");
                    String coin = scanner.nextLine();
                    if (isFloat(coin) || isInteger(coin)){
                        String sig = user.generateDigitalSignature(coin);
                        String data = coin + ":" + sig;
                        System.out.println("Enter receiver address(public key)");
                        String address = scanner.nextLine();
                        for (int j = 0; j < Account.size(); j++) {
                            if (Account.get(j).getPublicKey().equals(address)) {
                                Transaction one = new Transaction(data, user.getPublicKey(), address);
                                System.out.println("Transaction successfully");
                                transactions.add(one);
                                find_receiver = true;
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println("input coin should be integer or float");
                    }

                    if (!find_receiver) {
                        System.out.println("receiver is not exist");
                    }

                } else {
                    System.out.println("No this account");
                }

            }
            if (choice.equals("3")) {
                MerkleTree tree= new MerkleTree(transactions);
                Boolean success = tree.generateTree();
                if (success) {
                    merkletree = tree;
                    System.out.println(tree.getRoot().getValue());
                    tree.printTree();
                    transactions.clear();
                }
            }
            if (choice.equals("4")) {
                if (blockchain==null) {
                    Block block = new Block(merkletree, "0");
                    blockchain = new BlockChain(block);
                }else {
                    Block block = new Block(merkletree,blockchain.getLastBlock().getHash());
                    blockchain.addBlock(block);
                    blockchain.getLastBlock().nextBlock = block;
                }
                blockchain.printBlockChain();
            }
        }
    }
}
