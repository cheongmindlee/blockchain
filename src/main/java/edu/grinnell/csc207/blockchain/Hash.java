package edu.grinnell.csc207.blockchain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] data;
    /**
     * Constructs a new Hash object that contains the given has(as an array of bites)
     * @param data
     */
    public Hash(byte[] data){
        this.data = data;
    }

    /**
     * Returns the hash contained in this object
     * @return
     */
    public byte[] getData(){
        return data;
    }

    /**
     * Returns true if this hash meets the criteria for validity, its first three indices contain zeroes
     * @return
     */
    public boolean isValid(){
        if(data.length > 3){
            if(data[0] == 0 && data[1] == 0 && data[2] == 0){
                return true;
            } else{
                return false;
            }
        } else{
            return false;
        }
    }

    /**
     * Returns the string representation of the hash as a string of hexadeimal digits, 2 digits per byte
     */
    public String toString(){
        StringBuilder retString = new StringBuilder();
        for(int i =0; i<data.length; i++){
            retString.append(String.format("%02x", Byte.toUnsignedInt(data[i])));
        }
        return retString.toString();
    }

    /**
     * Returns true if this hash is structurally equal to the argument
     */
    public boolean equals(Object other){
        if(other instanceof Hash){
            //Cast other to Hash type
            Hash otherH = (Hash) other;
            if(Arrays.equals(this.data, otherH.data)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
