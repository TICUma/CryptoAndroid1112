package es.uma.SSL;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import android.util.Log;

public class SSLCertificados {
	
	private int port=443; // Suponemos puerto SSL es 443, si es otro tendremos que ponerlo en 
						  // el interfaz.
	private String host;
	
	Certificate[] peerCertificates; //Certificados que obtengo conexi—n
	X509Certificate[] serverCertificates=null;

	int numCert=0;	//Nœmeros Certificados Obtenidos
	
	
	/**********
	 * 
	 * @param hostd
	 */
	
	public SSLCertificados (String hostd){
	
	if (hostd.isEmpty()) {
		Log.e("SSL", "Host vac’o ");		
		return;
	}
	else host=hostd;
	
	Log.i("SSL", "Certificados  ");
	
	try {
	    SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
	    SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
	 
	    
	    HandshakeyCertificacion(socket, host); 
	    
	    // Create streams to securely send and receive data to the server
	    //InputStream in = socket.getInputStream();
	    //OutputStream out = socket.getOutputStream();
	        
	   // socket.getInputStream();
	    // Close the socket
	    socket.close();
	    
	    // Close the socket
	   // in.close();
	   // out.close();
	} catch(IOException e) {
		Log.i("SSL", "Certificados  "+e);
	}
    
}


/**
 * 
 */
public void HandshakeyCertificacion( SSLSocket sslSocket, String domain)
        throws IOException {
	
  

    // Hacemos el Handshake
    try {
        sslSocket.startHandshake();
    } catch (IOException e) {
    	Log.e("SSLHandshake", e.getMessage());
    }

    // obtener los certificados
    peerCertificates = sslSocket.getSession().getPeerCertificates();
   

    if (peerCertificates == null) {
    	Log.e("SSLHandshake", "No hay certificados");
    	return;
    } else {
    	
    	numCert=peerCertificates.length;
    	
    	if (numCert <= 0 ) {
        	Log.e("SSLHandshake", "Certificados <=0 "+numCert);
        	return;
        }
    	else{
    		serverCertificates = new X509Certificate[numCert];
            Log.i("SSL", "Hemos encontrado "+numCert+ "Certificados.");
            
            //Copiamos los certificados a X509
            for (int i = 0; i < peerCertificates.length; ++i) {
                serverCertificates[i] = (X509Certificate)(peerCertificates[i]);
            }	
    	}
    	
    }

    // Mirar si el certificado es el del dominio
    X509Certificate currCertificate = serverCertificates[0];
    if (currCertificate == null) {
        Log.e("SSL", "Certificado para esta web es null");
    } else {
    
    	Log.i("SSL", "Certificado de : "+ currCertificate.getSubjectDN());
        
    	//Es necesario comparar el DN con el host que hemos solicitado.
    	//A veces tambien altSubjectName
    }

    // Ahora comienza a ver si la cadena se cumple y la fecha es v‡lida
    
    // No se comprueba la firma es necesario.
    
    int chainLength = serverCertificates.length;
    
    if (serverCertificates.length > 1) {
      int currIndex;
     
      for (currIndex = 0; currIndex < serverCertificates.length; ++currIndex) {
        boolean foundNext = false;
       
        for (int nextIndex = currIndex + 1;
             nextIndex < serverCertificates.length;
             ++nextIndex) {
          
	        if (serverCertificates[currIndex].getIssuerDN().equals(
	              serverCertificates[nextIndex].getSubjectDN())) {
	            	foundNext = true;
	            	Log.i("SSL", "Certificado de : "+ serverCertificates[currIndex].getIssuerDN());
	            	Log.i("SSL", "Certificado de : "+ serverCertificates[nextIndex].getSubjectDN());
	            	
	            if (nextIndex != currIndex + 1) {
	              X509Certificate tempCertificate = serverCertificates[nextIndex];
	              serverCertificates[nextIndex] = serverCertificates[currIndex + 1];
	              serverCertificates[currIndex + 1] = tempCertificate;
	            }
	            break;
	          }
	        }
        if (!foundNext) break;
      }

      chainLength = currIndex + 1;
      X509Certificate lastCertificate = serverCertificates[chainLength - 1];
      Date now = new Date();
      if (lastCertificate.getSubjectDN().equals(lastCertificate.getIssuerDN())
          && now.after(lastCertificate.getNotAfter())) {
        --chainLength;
      }
    }

    
}

	/***************
	 * 
	 * @return
	 */
	public int getNumCertificados (){
	  return numCert;
	}
}
