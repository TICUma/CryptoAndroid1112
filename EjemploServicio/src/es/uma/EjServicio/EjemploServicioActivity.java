package es.uma.EjServicio;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EjemploServicioActivity extends Activity  {
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonStart = (Button) findViewById(R.id.BotonStart);
        Button buttonStop = (Button) findViewById(R.id.BotonStop);

   	 	buttonStart.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		comenzarServicio();
			}
        }
        );
   	 	
   	 	buttonStop.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		pararServicio();
			}
        }
        );
    }
    
    public void comenzarServicio () {
          Log.d("Aplicaci—n", "onClick: comenzando Servicio");
          startService(new Intent(this, ServicioEjemplo.class));
     }
    
    public void pararServicio () {
    	Log.d("Aplicaci—n", "onClick: parando Servicio");
        stopService(new Intent(this, ServicioEjemplo.class));
   }
    
}