
// Torch.java --------------------------------------------------------

package com.blogspot.programmingheroes.linterna;


import android.hardware.Camera;
import android.hardware.Camera.Parameters;


/**
 *  Clase que nos permitirá usar de una forma sencilla el flash de
 * la cámara cómo linterna.
 * 
 * Cómo usar la clase:
 *  - Instanciarla usando el constructor sin argumentos.
 *  - Llamar a los métodos on/off cuando se quiera
 *    encender/apagar la luz.
 *  - Llamar al método release cuando ya no se vaya a usar más.
 *
 * @version 1
 * @author ProgrammingHeroes
 *
 */
public class Torch
{
	private Camera camera;

	private Parameters parameters;

	private boolean on;


	public Torch()
    {
        camera = Camera.open();
        parameters = camera.getParameters();
        camera.startPreview();
        on = false;
    }


	public void on()
    {
    	if (!on)
    	{
    		on = true;
    		parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
    		camera.setParameters(parameters);
    	}
    }

	public void off()
    {
    	if (on)
    	{
	        on = false;
	        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
	        camera.setParameters(parameters);
    	}
    }

	public void release()
    {
    	camera.stopPreview();
        camera.release();
    }

	public boolean isOn()
    {
    	// Opción alternativa que nos ahorraría el uso de la
		// variable on.
    	// return (parameters.getFlashMode() ==
		//         Parameters.FLASH_MODE_TORCH);
        return on;
    }

} // fin de la clase Torch

// fin de Torch.java -------------------------------------------------
