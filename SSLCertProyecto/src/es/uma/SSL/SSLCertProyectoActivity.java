package es.uma.SSL;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SSLCertProyectoActivity extends Activity {
	SSLCertificados SSLCert;
	String host;
	Context context;
	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        
        Button ConectarB= (Button) findViewById(R.id.ConectarBoton);
        final EditText ServerName= (EditText) findViewById(R.id.serverName);
        final EditText Info= (EditText) findViewById(R.id.statusText);
        
        ConectarB.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		
        		if(ServerName.getText().length()==0){
        			Toast.makeText(context, "Longitud del Servidor no v‡lida", Toast.LENGTH_LONG).show();
        		}
        		else{
	        		host= ServerName.getText().toString();
	        		Info.setText(host);
	        	
	        		long init=System.currentTimeMillis();
	       	     	SSLCert = new SSLCertificados(host);
	        	    long fin=System.currentTimeMillis();
	        	    int numCert= SSLCert.getNumCertificados();
	        	
	        	    //setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, countries));
        	     
	        	    Info.setText("Certificados "+numCert+ " en: "+(fin-init)+" msdos. ");
        		}
    		}
        }
        );
        
    }
}