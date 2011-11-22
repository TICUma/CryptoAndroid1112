package es.uma.Rsa;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.util.Log;

public class Rsa {

	KeyPair kp;
	RSAPublicKeySpec pub;
	RSAPrivateKeySpec priv;

/**************
 * 	
 */
	 public Rsa(){
		 generarClave(1024);
	 }
	 
/************
 * 	
 * @param LongClave
 */
	 public Rsa(int LongClave) {       

		 generarClave(LongClave);
	 }
	 
	 
/**************
 * 	 
 * @param LongClave
 */
	 public void generarClave(int LongClave) {   
		 
		 Log.i("generar Clave","Estoy Generando");
		 
	        try{
	            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	            kpg.initialize(LongClave);
	            kp = kpg.genKeyPair();

	            KeyFactory fact = KeyFactory.getInstance("RSA");
	            pub = fact.getKeySpec(kp.getPublic(),RSAPublicKeySpec.class);
	            priv = fact.getKeySpec(kp.getPrivate(),RSAPrivateKeySpec.class);
	        }catch(Exception e){
	        	Log.d("generar Clave", e.getMessage());
	        }
	    }
	 
	 public BigInteger getModulo (){
		 return pub.getModulus();
	 }
	 
	 public String getModuloString (){
		 return pub.getModulus().toString();
	 }
	 
	 public BigInteger getPublicExp(){
		 return pub.getPublicExponent();
	 }
	 
	 public String getPublicExpString(){
		 return pub.getPublicExponent().toString();
	 }
	 
	 public BigInteger getPrivateExp(){
		 return priv.getPrivateExponent();
	 }
	 
	 public String getPrivateExpString(){
		 return priv.getPrivateExponent().toString();
	 }
	 
/*********
 *    
 * @param data
 * @return
 */
	 public String Cifrar(String data) {
		 return Cifrar(data.getBytes());
	 }
/*******
 * 	 
 * @param data
 * @return
 */
	 public String Cifrar(byte[] data) {
		 byte[] cifrado=null;
		try {
			cifrado = this.rsaEncrypt(data);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 String Cif=Rsa.toHex(cifrado);
		return Cif;
   	}
/***************
 * 	 
 * @param Data
 * @return
 */
	 public String Descifrar(String Data){
		 byte[] ret=null;
		byte[] data= this.toByte(Data);
		 
		try {
			ret= rsaDecrypt(data);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(ret);
		
	 }
	 
/************
 * 	 
 * @param data
 * @return
 * @throws InvalidKeyException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeySpecException
 */
	    public byte[] rsaEncrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	    	  
	     	  KeyFactory fact = KeyFactory.getInstance("RSA");
	    	  PublicKey pubKey = fact.generatePublic(pub);
	    	  byte[] cipherData =null;  
	    	  
			try {
				Cipher cipher;
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		        cipherData = cipher.doFinal(data);
		    	
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			  return cipherData;    	
	    	}
	    
	    
/***************
 * 
 * @param data
 * @return
 * @throws InvalidKeyException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeySpecException
 */
	    public byte[] rsaDecrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
	    	  
	     	  KeyFactory fact = KeyFactory.getInstance("RSA");
	    	  PrivateKey privKey = fact.generatePrivate(priv);
	    	  byte[] cipherData =null;  
	    	  
			try {
				Cipher cipher;
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privKey);
		        cipherData = cipher.doFinal(data);
		    	
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			  return cipherData;    	
	    	}
	    
/*********
 * 	    
 * @param txt
 * @return
 */
	    public String toHex(String txt) {
            return toHex(txt.getBytes());
	    }
/******
 * 	    
 * @param hex
 * @return
 */
	    public String fromHex(String hex) {
            return new String(toByte(hex));
	    }
 /***********
  *    
  * @param hexString
  * @return
  */
    public byte[] toByte(String hexString) {
            int len = hexString.length()/2;
            byte[] result = new byte[len];
            for (int i = 0; i < len; i++)
                    result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
            return result;
    }
/********
 * 
 * @param buf
 * @return
 */
    	public static String toHex(byte[] buf) {
            if (buf == null)
                    return "";
            StringBuffer result = new StringBuffer(2*buf.length);
            for (int i = 0; i < buf.length; i++) {
                    appendHex(result, buf[i]);
            }
            return result.toString();
    	}
   /********
    *  	
    */
    	private final static String HEX = "0123456789ABCDEF";
 /***********
  *    	
  * @param sb
  * @param b
  */
    	private static void appendHex(StringBuffer sb, byte b) {
            sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    	}

}
