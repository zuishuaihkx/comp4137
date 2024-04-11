import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.security.spec.X509EncodedKeySpec;

public class BlockchainAccount {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private String name;


    //Elliptic Curve Cryptography
    public BlockchainAccount(String name) {
        try {
// Get the key generator and using the specific parameter to initiate generator
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec);

// generate user's public key and private key
            KeyPair keyPair = keyGen.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
            this.name = name;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    // Get the Base64 encoded public key.
    // Change the x,y coordination to string.
    //  Because the original public key is coordinate, it points to a curve.
    public String getPublicKey() {
        byte[] publicKeyBytes = publicKey.getEncoded();
        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKeyBytes);
        return encodedPublicKey;
    }

    // Get the Base64 encoded private key
    // The private key is encoded as a Base64 string
    public String getPrivateKey() {
        byte[] privateKeyBytes = privateKey.getEncoded();
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKeyBytes);
        return encodedPrivateKey;
    }

    public byte[] generateHash(String content){
        //Hashing function
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //Encode data to String
        byte[] hash = digest.digest(content.getBytes());
        return hash;
    }


    // Generate a digital signature for the data using the private key
    public String generateDigitalSignature(String data) {
        try {
    // Using SHA256withECDSA as alo to sign
            Signature signature = Signature.getInstance("SHA256withECDSA");

    // Using privatekey to sign
            signature.initSign(this.privateKey);

    // sign data
            signature.update(generateHash(data));

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean verifyDigitalSignature(String data, String signature, String publicKeyStr) {
        try {
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            byte[] dataBytes = generateHash(data);
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);


            Signature sig = Signature.getInstance("SHA256withECDSA");
            sig.initVerify(publicKey);
            sig.update(dataBytes);

            return sig.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}