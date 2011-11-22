package es.uma.EjServicio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServicioEjemplo extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Servicio creado ...", Toast.LENGTH_LONG).show();
      Log.d("MiServicio", "Servicio creado ...");
    }
  
	public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio eliminado ...", Toast.LENGTH_LONG).show();
        Log.d("MiServicio","Servicio eliminado ...");
  }

  	public int onStartCommand(Intent intent, int flags, int startId) {
  		Toast.makeText(this, "Servicio iniciado ...", Toast.LENGTH_LONG).show();
  		Log.d("MiServicio", "Servicio iniciado ...");
  		return 1;
	}

}
