import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
public class Transaction {
    private String data;
    private String input;
    private String output;

    private String transaction_id;

    public Transaction (String data, String input, String output){
        this.data = data;
        this.input = input;
        this.output = output;
        this.transaction_id = generateID(data+input+output);
    }
    public String generateID(String content){
        //Hashing function
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //Encode data to String
        byte[] hash = digest.digest(content.getBytes());
        String encodeDdata =Base64.getEncoder().encodeToString(hash);
        return encodeDdata;
    }

    public String getData() {
        return data;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }



}


