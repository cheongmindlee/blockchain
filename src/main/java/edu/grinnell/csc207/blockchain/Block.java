package edu.grinnell.csc207.blockchain;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    private int num;
    private int amount;
    private Hash prevHash;
    private Hash hash;
    private long nonce;
    /**
     * Creates a new block from the specified parameters
     *  Performs mining operations to discover the nonce and hash for this block
     *  When prevHash does not exist we will hash in this order, block's number, data contained in block, 
     *   and then nonce value.
     *  
     * @param num the blocks number
     * @param amount the data(amount) transferred between two parties
     * @param prevHash the hash value of the previous block
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException{
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;

        //Find nonce value, we will increment i until the hash values starts with 3 0's
        long i = 0;
        //Check if prevHash exists
        while(true){
            //Calculate hash value using information we have
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(ByteBuffer.allocate(4).putInt(num).array());
            md.update(ByteBuffer.allocate(4).putInt(amount).array());
            if(prevHash != null){
                md.update(prevHash.getData());
            }
            md.update(ByteBuffer.allocate(8).putLong(i).array());

            byte[] hash = md.digest();

            //Check to see if has starts with atleast 3 0's
            if(hash[0] == 0 && hash[1] == 0 && hash[2] == 0){
                this.nonce = i;
                this.hash = new Hash(hash);
                break;
            }
            i++;
        }
    }

    /**
     * Creates a new block from the parameters, no need to perform mining operation
     * @param num the blocks number 
     * @param amount the data(amount) transferred between two parties
     * @param prevHash the hash value of the previous block
     * @param nonce nonce value already provided
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(ByteBuffer.allocate(4).putInt(num).array());
            md.update(ByteBuffer.allocate(4).putInt(amount).array());
            md.update(prevHash.getData());
            md.update(ByteBuffer.allocate(8).putLong(nonce).array());
    }

    /**
     * Returns number of this block
     * @return
     */
    public int getNum(){

    }

    /**
     * Returns the amount transferred that is recorded in this block
     * @return
     */
    public int getAmount(){

    }

    /**
     * Returns the nonce of this block
     * @return
     */
    public long getNonce(){

    }

    /**
     * Returns the hash of the previous block in the blockchain
     * @return
     */
    public Hash getPrevHash(){

    }

    /**
     * returns the hash of this block
     * @returns
     */
    public Hash getHash(){

    }

    /**
     * returns a string representation of the block
     */
    public String toString(){

    }
}
