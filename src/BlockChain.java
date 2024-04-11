import java.util.ArrayList;

public class BlockChain {
    static ArrayList<Block> Blockchain = new ArrayList<>();
    public BlockChain(Block firstblock){
        Blockchain.add(firstblock);
    }

    public void addBlock(Block newBlock) {
        Blockchain.get(-1).nextBlock = newBlock;
        Blockchain.add(newBlock);
    }

    public Block getLastBlock(){
        return Blockchain.get(-1);
    }

    public void printBlockChain() {
        for (int i = 0; i < Blockchain.size(); i++) {
            Block block = Blockchain.get(i);
            if (i > 0) {
                System.out.print(" -> ");
            }
            System.out.print("previousHash:"+block.getPreviousHash() + "==" + "rootHash:"+block.getMerkleRoot());
        }
    }
}
