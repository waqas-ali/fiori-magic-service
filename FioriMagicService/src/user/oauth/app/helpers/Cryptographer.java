package user.oauth.app.helpers;



import javax.crypto.Cipher;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Cryptographer
{
    private static final String IsoAnsiLatinI = "ISO-8859-1"; 
    private static final byte[] BASE_KEY = { 0x6a, 0x68, 0x6b, 0x6b, 0x73, 0x6f, 0x61, 0x6b, 0x66, 0x73, 0x61, 0x7a, 0x61, 0x2d, 0x39, 0x32, 0x6a, 0x68, 0x6b, 0x6b, 0x73, 0x6f, 0x61, 0x6b, 0x66, 0x73, 0x61, 0x7a, 0x61, 0x2d, 0x39, 0x32 };
    private static final String CipherMode = "DESede/ECB/PKCS5Padding";

    private byte[] key;
    private String cipher; 
    
    public String getCipher() {
		return cipher;
	}

	public Cryptographer(){
    	
		this.cipher = Cryptographer.CipherMode;
    	this.key = Cryptographer.BASE_KEY;
    }

    public Cryptographer(byte[] key){

    	this.setKey(key);
    }    
    
    public byte[] getKey()
    {
    	return this.key;
    }
    
    public void setKey(byte[] key) {
    	this.key = key;
    }
    
    public String encrypt(String input) {

        String retVal = "";

        try {
        	
	        Cipher algorithm = Cipher.getInstance(CipherMode);
	        algorithm.init(Cipher.ENCRYPT_MODE, new SecretKeySpec((new DESedeKeySpec(this.key)).getKey(), "DESede"));
	        byte[] tempArr = input.getBytes(IsoAnsiLatinI);
	        retVal =  ByteArrayUtil.toHexLiteral((algorithm.doFinal(tempArr, 0, tempArr.length)));	        
        } catch (Exception e) { }
    	
    	return retVal;
	}
    

    public String decrypt(String input) {
    
        String retVal = "";
        try {

            Cipher algorithm = Cipher.getInstance(CipherMode);
            algorithm.init(Cipher.DECRYPT_MODE, new SecretKeySpec((new DESedeKeySpec(this.key)).getKey(), "DESede"));
            retVal = new String(algorithm.doFinal(ByteArrayUtil.fromHEXLiteral(input)), IsoAnsiLatinI);
            
        } catch (Exception e) {}
                
    	return retVal;
    }
    
    // This main function is added for testing purpose. It will be removed later
    public static void main(String[] arr) {
    	
    	Cryptographer c = new Cryptographer();
    	String enc = c.encrypt("gmail");
    	System.out.println(enc);
    	String dec = c.decrypt("2f4f99930cbf207a");
    	System.out.print(dec);
    }
}




