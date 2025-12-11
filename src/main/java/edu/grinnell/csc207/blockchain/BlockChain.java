package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    // Nested node class which holds each block in the blockchain in a singly linked
    // list
    private class Node {
        // Holds the block in the node
        Block block;
        // Holds next block in list
        Node next;

        public Node(Block block) {
            this.block = block;
            this.next = null;
        }

    }

    // Holds the first block, and last block
    public Node first;
    public Node last;

    /**
     * Creates a BlockChain that has a single block with a specified amount
     * 
     * @param initial inital amount the blcok starts with
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        first = new Node(new Block(0, initial, null));
        last = first;
    }

    /**
     * Mines a new candidate block which is to be added to the list.
     * 
     * @param amount integer transaction amount
     * @return Will return a valid Block to be add onto the chain
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block block = new Block(last.block.getNum() + 1,
                amount,
                last.block.getHash());

        // Return the new block
        return block;
    }

    /**
     * Return the size of the blockchain
     * 
     * @return number of blocks in the blockchain
     */
    public int getSize() {
        return last.block.getNum() + 1;
    }

    /**
     * Adds a block into the block chain
     * 
     * @param blk the block to be added to the block chain
     * @throws IllegalArgumentException if this block cannot be added
     */
    public void append(Block blk) throws IllegalArgumentException {
        // Checks to make sure that the block is allowed to be added to the chain
        try {
            if (!this.isValidBlockChain()) {
                throw new IllegalArgumentException();
            } else {
                // Add this new block to the end and make last equal to the new block
                last.next = new Node(blk);
                last = last.next;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes the last block from the chain, if there is only one element do
     * nothing
     * 
     * @return boolean, true if an element was removed, false if there was only had
     *         one block
     */
    public boolean removeLast() {
        if (first == last) {
            return false;
        } else {
            // Walk through the block chain and make last be the second to last element
            Node cur = first;
            while (cur.next.next != null) {
                cur = cur.next;
            }
            last = cur;
            last.next = null;
            return true;
        }
    }

    /**
     * Returns the hash of the last block
     * 
     * @return Hash of the last block
     */
    public Hash getHash() {
        return last.block.getHash();
    }

    /**
     * Walks the blockchain and ensures that its blocks are consistent and valid
     * 
     * @return boolean, true if valid, false if not valid
     */
    public boolean isValidBlockChain() throws NoSuchAlgorithmException {
        // Instantiate traversal node and account balance of anna and bob
        Node cur = first;
        int amountA = 0;
        int amountB = 0;
        // Make sure each block's hash value is valid
        while (cur != null) {
            byte[] curData = cur.block.getHash().getData();
            byte[] nextData = new Block(cur.block.getNum(),
                    cur.block.getAmount(),
                    cur.block.getPrevHash(),
                    cur.block.getNonce()).getHash().getData();
            for (int i = 0; i < curData.length; i++) {
                if (curData[i] != nextData[i]) {
                    return false;
                }
            }
            // Make sure each hash value starts with 3 0's
            if (nextData[0] != 0 || nextData[1] != 0 || nextData[2] != 0) {
                return false;
            }

            // Make sure the money does not go negative
            amountA += cur.block.getAmount();
            // After the initial sum of money in Anna's account start +/- transaction on
            // bob's acc
            if (cur.block.getNum() > 0) {
                amountB -= cur.block.getAmount();
            }
            if (amountA < 0 || amountB < 0) {
                return false;
            }
            cur = cur.next;
        }

        // Reset cur
        cur = first;
        // Make sure each block's hash value is consistent with other blocks in the
        // chain
        while (cur.next != null) {
            byte[] curData = cur.block.getHash().getData();
            byte[] nextData = cur.next.block.getPrevHash().getData();
            // Check if the hash of the cur block is equal to prevHash of block infront
            for (int i = 0; i < curData.length; i++) {
                if (curData[i] != nextData[i]) {
                    return false;
                }
            }

            // Check if the block numbers are consistent
            if (cur.next.block.getNum() - cur.block.getNum() != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints Alice's and Bob's respective balances
     */
    public void printBalances() {
        // Traverse the blockchain and add up the balances
        Node cur = first;
        int amountA = cur.block.getAmount();
        int amountB = 0;
        cur = cur.next;

        while (cur != null) {
            amountA += cur.block.getAmount();
            amountB -= cur.block.getAmount();
            cur = cur.next;
        }
        System.out.println("Alice: " + amountA + ", Bob: " + amountB);
    }

    /**
     * Returns the string representation of the block chain
     * 
     * @return string representation of the block chain
     */
    public String toString() {
        // Traverse through the blockchain and add to the string buffer each block
        StringBuffer buf = new StringBuffer();
        Node cur = first;
        while (cur != null) {
            buf.append("Block " + cur.block.getNum());
            buf.append(" (Amount: " + cur.block.getAmount() + ", ");
            buf.append("Nonce : " + cur.block.getNonce() + ", ");
            buf.append("prevHash: " + cur.block.getPrevHash() + ", ");
            buf.append("hash: " + cur.block.getHash() + ")\n");
            cur = cur.next;
        }

        return buf.toString();
    }
}
