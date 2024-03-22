import jdk.nashorn.internal.ir.Block;

import java.security.Signature;

public class MerkleTree {
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
