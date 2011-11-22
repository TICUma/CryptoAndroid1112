package es.uma;

import java.util.Random;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ProyectoEventoActivity extends Activity {
    private static final int CLEAR_MENU_ID = 1;
	private static final int ABOUT_MENU_ID = 2;
	private static final int NOTIFICATION_ID = 0;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        final CheckBox mostrarNegativos= (CheckBox) findViewById(R.id.checkBox1);
        
        final EditText tb1 = (EditText) findViewById(R.id.editText1); 
        Button aleatorio= (Button) findViewById(R.id.button1);
        Button limpiar= (Button) findViewById(R.id.button2);

        aleatorio.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Random generator = new Random();
        		int rand= generator.nextInt();
        		
        		
        		if (rand<0) 
        			numeroNegativoEvento(); //Muestro Eventos Nœmero Negativo
        	
	        
        		if( (rand<0) & (mostrarNegativos.isChecked()) | (rand>0)){  
	        			String rands = Integer.toString(rand);
	                    tb1.setText(rands);
	        	}
        		
			}
        }
        );
        	
        limpiar.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		tb1.setText("");
			}
        }
        );
     
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, CLEAR_MENU_ID, Menu.NONE, "Limpiar");
		menu.add(Menu.NONE, ABOUT_MENU_ID, Menu.NONE, "About");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final EditText tb1 = (EditText) findViewById(R.id.editText1); 
		 
		switch (item.getItemId()) {
			case CLEAR_MENU_ID: tb1.setText("");
				return true;
		case ABOUT_MENU_ID: 
				Toast.makeText(this, "Curso Criptografia en Android", Toast.LENGTH_LONG).show();
				return true; 
		default: ; return false;
		}
	}
	
  private void numeroNegativoEvento()
	    {
	        CharSequence title = "Nœmero Aleatorio";
	        CharSequence message = "No nos interesa los nœmeros negativos!";
	
	        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	        Notification notification = new Notification(R.drawable.ic_launcher, "Nœmero Negativo!", System.currentTimeMillis());
	        notification.defaults |= Notification.DEFAULT_SOUND;
	        notification.defaults |= Notification.DEFAULT_LIGHTS;
	        
	        Intent notificationIntent = new Intent(this, ProyectoEventoActivity.class);
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	 
	        notification.setLatestEventInfo(ProyectoEventoActivity.this, title, message, pendingIntent);
	        notificationManager.notify(NOTIFICATION_ID, notification);
	    }
}