
// MainActivity.java ------------------------------------------------- 

package com.blogspot.programmingheroes.linterna;


import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;


public class MainActivity extends Activity
{

	private Camera camera;
	
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
    }

} // fin de la clase MainActivity

// fin de MainActivity.java ------------------------------------------
