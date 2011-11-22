package es.uma.Rsa;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RsaProyectoActivity extends Activity {
	Rsa rsa;
    String cifrado;
    Context context;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        
        final EditText textOriginal = (EditText) findViewById(R.id.TextInicial); 
        textOriginal.setEnabled(false);
        final EditText textCifrado = (EditText) findViewById(R.id.TextCifrado); 
        final EditText textDescifrado = (EditText) findViewById(R.id.TextDescifrado);
        
        Button CrearClave= (Button) findViewById(R.id.GenerarClave);
        final Button Cifrar= (Button) findViewById(R.id.BotonCifrar);
        final Button Descifrar= (Button) findViewById(R.id.BotonDescifrar);
        
        Cifrar.setEnabled(false);
        Descifrar.setEnabled(false);
        
        final Spinner longClave = (Spinner) findViewById(R.id.longitudClave); 
        final TextView infoClave = (TextView) findViewById(R.id.InfoGenerarClave); 
        final TextView infoCif = (TextView) findViewById(R.id.TiempoCifrar); 
        final TextView infoDecif = (TextView) findViewById(R.id.TiempoDescifrar);
        
        
        longClave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				textDescifrado.setText(""); textCifrado.setText("");
				infoDecif.setText(""); infoCif.setText("");
				textOriginal.setText("");infoClave.setText("");
				Cifrar.setEnabled(false);
				Descifrar.setEnabled(false);
				textOriginal.setEnabled(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        textOriginal.setOnKeyListener(new View.OnKeyListener()
        {
 			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {

 				if (textOriginal.getText().length() > 0){
 					Cifrar.setEnabled(true);
 					textDescifrado.setText("");
 					textCifrado.setText("");
 					Descifrar.setEnabled(false);
 					infoCif.setText("");
 					infoDecif.setText("");
 				}
 				else Cifrar.setEnabled(false);
 				return false;
			}
        });
        
        CrearClave.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(context, "Generando Clave", Toast.LENGTH_LONG).show();
				
        		 int position=longClave.getSelectedItemPosition();
        	     final String longC= (String) longClave.getItemAtPosition(position);
        	     
        	     int clave = Integer.parseInt(longC);
        	     long init=System.currentTimeMillis();
        	     rsa = new Rsa(clave);
        	     long fin=System.currentTimeMillis();
        	     infoClave.setText("Clave de "+longC+ " bits Generada: "+(fin-init)+" milisegundos. ");
        	     textOriginal.setEnabled(true);
    		}
        }
        );
        
        Cifrar.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		
        		String value=textOriginal.getText().toString();
        		
        		
        		 long init=System.currentTimeMillis();
        		 cifrado=rsa.Cifrar(value);
        	     long fin=System.currentTimeMillis();
        	     infoCif.setText("Cifrado en "+(fin-init)+" milisegundos. ");
        		
        		
        		textCifrado.setText(cifrado);
        		Descifrar.setEnabled(true);
        		
			}
        }
        );
        
        Descifrar.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		
        		long init=System.currentTimeMillis();
        		String txt=rsa.Descifrar(cifrado);
        		long fin=System.currentTimeMillis();
       	     	infoDecif.setText("Descifrado en "+(fin-init)+" milisegundos. ");
      
       			textDescifrado.setText(txt);
			}
        }
        );
        
    }
    
   
}