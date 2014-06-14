
// MainActivity.java ------------------------------------------------- 

package com.blogspot.programmingheroes.linterna;


import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 *  Linterna para Android.
 * 
 * @version 1
 * @author ProgrammingHeroes
 *
 */
public class MainActivity extends Activity implements OnClickListener
{
	
	private static final String WAKE_LOCK_TAG = "Linterna";
	
	private Torch torch;
	
	private WakeLock wakeLock;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // Encender el flash.
        torch = new Torch();
        torch.on();
        
        // Cargar interfaz gráfica.
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button_on_off);
        button.setOnClickListener(this);
        
        // Adquirir el wake lock.
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
    	torch.release();
    	
    	// Soltar el wake lock.
    	wakeLock.release();
    }

	@Override
	public void onClick(View view)
	{
		if (torch.isOn())
		{
			torch.off();
		}
		else
		{
			torch.on();
		}
	}

} // fin de la clase MainActivity

// fin de MainActivity.java ------------------------------------------
