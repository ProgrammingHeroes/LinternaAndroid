
// MainActivity.java ------------------------------------------------- 

package com.blogspot.programmingheroes.linterna;


import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;


public class MainActivity extends Activity
{
	
	private static final String WAKE_LOCK_TAG = "Linterna";
	
	private Camera camera;
	
	private WakeLock wakeLock;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Encender el flash.
        camera = Camera.open();
        Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        
        // Adquirir el wake lock
        PowerManager powerManager =
        		(PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(
        		PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        wakeLock.setReferenceCounted(false);
        if (!wakeLock.isHeld())
        {
        	wakeLock.acquire();
        }
    }
    
    @Override
    public void onBackPressed()
    {
    	super.onBackPressed();
    	
    	// Apagar el flash.
    	// No es necesario apagar el flash si vamos a cerrar la
    	// cámara, se apaga automáticamente.
    	//Parameters parameters = camera.getParameters();
        //parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
        //camera.setParameters(parameters);
    	camera.stopPreview();
    	camera.release();
    	
    	// Soltar el wake lock
    	wakeLock.release();
    }

} // fin de la clase MainActivity

// fin de MainActivity.java ------------------------------------------
