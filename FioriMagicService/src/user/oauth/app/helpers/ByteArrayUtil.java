package user.oauth.app.helpers;



public class ByteArrayUtil {
	
    public static String toHexLiteral(byte[] input)
    {
    	
      String hexString = "";

      for (int i=0; i<input.length; i++) 
      {
          String temp = Integer.toHexString(input[i] & 0xFF);
    	  hexString += (temp.length()<2) ? ("0" + temp) : temp;
      }
      return hexString;
    }

    public static byte[] fromHEXLiteral(String input) 
    {
        
    	input.toLowerCase();
    	byte[] bts = new byte[input.length() / 2];
    	
    	for (int i=0; i<bts.length; i++) {
    	    
    	   bts[i] = (byte) Integer.parseInt(input.substring(2*i, 2*i+2), 16);
    	}
    	return bts;
    }
    
    
}