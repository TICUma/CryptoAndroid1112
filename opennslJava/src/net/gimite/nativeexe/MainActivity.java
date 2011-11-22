package net.gimite.nativeexe;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.gimite.nativeexe.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	
    private TextView outputView;
	private Button localRunButton;
	private Handler handler = new Handler();
	private Button remoteRunButton;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        outputView = (TextView)findViewById(R.id.outputView);
        localRunButton = (Button)findViewById(R.id.localRunButton);
        localRunButton.setOnClickListener(onLocalRunButtonClick);
       
        remoteRunButton = (Button)findViewById(R.id.remoteRunButton);
        remoteRunButton.setOnClickListener(onRemoteRunButtonClick);
    }
 
    
	private OnClickListener onLocalRunButtonClick = new OnClickListener() {
		public void onClick(View v) {
			
			   final String localPath = "/data/data/net.gimite.nativeexe/openssl";
		        instalar(localPath);
		        ejecutar(localPath+" version");
		}
	};
	
	/******
	 * Aqu’ pongo el c—digo para ejecutar comandos openssl desde android
	 */

	private OnClickListener onRemoteRunButtonClick = new OnClickListener() {
		public void onClick(View v) {
			output("Por hacer");
		}
	};

	/********
	 * Ejecuta un comando
	 * @param command
	 * @return
	 */
	
    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
         	process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
/*************
 * 
 * @param localPath
 */
    
    private void instalar(final String localPath) {
    	   try {
           	InputStream in= getAssets().open("openssl");
         	FileOutputStream out = new FileOutputStream(localPath);
   			int read;
   			byte[] buffer = new byte[4096];
   			while ((read = in.read(buffer)) > 0) {
   				out.write(buffer, 0, read);
   			}
   			out.close();
   			in.close();
   			exec("/system/bin/chmod 744 " + localPath);
   			
   		} catch (IOException e) {
   			e.printStackTrace();
   		}

    }
    
  /*******
   * Ejecutar openssl
   * @param openssl
   */
    private void ejecutar(final String openssl) {
 	    	
			Thread thread = new Thread(new Runnable() {
				public void run() {
					String output = exec(openssl);
					output(output);
				}
			});
			thread.start();
	 }
    
  /******
   * Salida  
   * @param str
   */
    private void output(final String str) {
    	Runnable proc = new Runnable() {
			public void run() {
				outputView.setText(str);
			}
    	};
    	handler.post(proc);
    }
    
}