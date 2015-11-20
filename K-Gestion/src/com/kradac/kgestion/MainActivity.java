package com.kradac.kgestion;

import com.kradac.kgestion.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	public static double latitud = 0;
	public static double longitud = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // Vamos a declarar un nuevo thread
     		Thread timer = new Thread() {
     			// El nuevo Thread exige el metodo run
     			public void run() {
     				try {
     					sleep(2000);
     				} catch (InterruptedException e) {
     					// Si no puedo ejecutar el sleep muestro el error
     					e.printStackTrace();
     				} finally {
     					Intent i = new Intent(MainActivity.this,
     							Map.class);
     					startActivity(i);
     				}
     			}
     		};
     		// ejecuto el thread
     		timer.start();
    }
}
