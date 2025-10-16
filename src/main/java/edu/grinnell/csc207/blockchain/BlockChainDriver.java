package edu.grinnell.csc207.blockchain;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * Discovers the nonce for a given transaction
     * @param scan a scanner to take in user input
     * @param blockChain The blockchain
     */
    public static void mine(Scanner scan, BlockChain blockChain){
        //Take user input and calculate a nonce value and output it with amount
        System.out.print("Amount transferred? ");
        int amount = scan.nextInt();
        //Clear the terminal
        scan.nextLine();
        try{
            Block block = blockChain.mine(amount);
            System.out.println("amount = " + amount + ", nonce = " + block.getNonce() + "\n");

        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    /**
     * appends a ne wblock onto the end of the chain once you give it a valid nonce 
     * @param scan a scanner to take in user input
     * @param blockChain The blockchain
     */
    public static void append(Scanner scan, BlockChain blockChain){
        //Take user input for amount and nonce and then append it to the block
        System.out.print("Amount transferred? ");
        int amount = scan.nextInt();
        //Clear the terminal
        scan.nextLine();

        System.out.print("Nonce? ");
        long nonce = scan.nextLong();
        //Clear the terminal
        scan.nextLine();

        //Retrieve the index of the new block and the hash of the last block
        Hash prevHash = blockChain.getHash();
        int blockIndex = blockChain.getSize();
        try{
            //Create a new block and append it to blockChain
            Block newBlock = new Block(blockIndex, amount, prevHash, nonce);
            blockChain.append(newBlock);
            System.out.println();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    /**
     * Removes the last block from the end of the chain
     * @param blockChain The blockchain
     */
    public static void remove(BlockChain blockChain){
        blockChain.removeLast();
        System.out.println();
    }

    /**
     * Checks taht the block chain is valid. Will print out if it is invalid or not
     * @param blockChain The blockchain
     */
    public static void check(BlockChain blockChain){
        try{
            if(blockChain.isValidBlockChain()){
                System.out.println("Chain is valid!\n");

            } else {
                System.out.println("Chain is invalid!\n");
            }
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
       
    }

    /**
     * Returns a block of text telling users which commands they can call
     */
    public static void help(){
        System.out.println("Valid commands:");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    report: reports the balances of Alice and Bob");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program\n");
    }
        
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Input should be of form <Initial Amount>");
        } else {
            int initial = Integer.parseInt(args[0]);
            try{
                BlockChain blockChain = new BlockChain(initial);
                System.out.print(blockChain.toString());

                //Instantiate a new scanner
                Scanner scan = new Scanner(System.in);

                //Start user input and ask for commands
                System.out.print("Command? ");
                String input = scan.nextLine();
                //Run until user inputs 
                while(true){

                    //Check to see if any of the commands were inputted
                    if(input.equals("quit")){
                        break;
                    }
                    else if(input.equals("mine")){
                        mine(scan, blockChain);
                    } else if(input.equals("append")){
                        append(scan, blockChain);
                    } else if(input.equals("remove")){
                        remove(blockChain);
                    } else if(input.equals("check")){
                        check(blockChain);
                    } else if(input.equals("report")){
                        blockChain.printBalances();
                        System.out.println();
                    } else if(input.equals("help")){
                        help();
                    } else {
                        System.out.println("That is not a valid input. Please try again.");
                        System.out.println("Call help to see funcitons\n");
                    }

                    //Output what the current blockchain after
                    System.out.print(blockChain.toString());

                    //Ask again for command
                    System.out.print("Command? ");
                    input = scan.nextLine();

                }
            } catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            

        }
    }  
}
