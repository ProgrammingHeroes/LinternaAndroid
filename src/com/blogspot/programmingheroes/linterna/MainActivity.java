
// MainActivity.java ------------------------------------------------- 

package com.blogspot.programmingheroes.linterna;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


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
	
	private static final int NOTIFICATION_ID = 1;
	
	private Torch torch;
	
	private WakeLock wakeLock;
	
	private NotificationManager notificationManager;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // Acceder a la cámara.
        if (!initTorch())
        {
        	return;
        }
        
        // Encender el flash.
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
        
        // Iniciar el NotifactionManager.
        notificationManager = (NotificationManager)
        		getSystemService(NOTIFICATION_SERVICE);
        
        // Creamos la notificación.
        createNotification();
    }
    
    private boolean initTorch()
    {
    	try
    	{
	    	// Acceder a la cámara.
	    	torch = new Torch();
    	}
    	catch (Exception e)
    	{
    		// Mostrar mensaje de error al usuario.
    		Toast.makeText(this,
    				getResources().getString(R.string.text_error),
    				Toast.LENGTH_LONG).show();
    		// Salir de la aplicación.
    		finish();
    		
    		return false;
    	}
    	
    	return true;
    }
    
    private void createNotification()
    {
    	Intent intent = new Intent(this, MainActivity.class);
    	
    	PendingIntent pendingIntent = PendingIntent.getActivity(this,
    			0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    	
    	Notification notification = new NotificationCompat.Builder(this)
    		.setSmallIcon(R.drawable.ic_launcher)
    		.setContentTitle(getResources().getString(R.string.app_name))
    		.setContentText(getResources().getString(R.string.notification_text))
    		.setOngoing(true)
    		.setContentIntent(pendingIntent)
    		.build();
    	
    	notificationManager.notify(NOTIFICATION_ID, notification);
    }
    
    private void destroyNotification()
    {
    	notificationManager.cancel(NOTIFICATION_ID);
    }
    
    @Override
    public void onBackPressed()
    {
    	super.onBackPressed();
    	
    	// Destruir notifiación.
    	destroyNotification();
    	
    	// Apagar el flash.
    	// No es necesario apagar el flash si vamos a cerrar la
    	// cámara, se apaga automáticamente.
    	torch.release();
    	torch = null;
    	
    	// Soltar el wake lock.
    	wakeLock.release();
    }

	@Override
	public void onClick(View view)
	{
		if (torch.isOn())
		{
			torch.off();
			destroyNotification();
		}
		else
		{
			torch.on();
			createNotification();
		}
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		if (torch == null && initTorch())
		{
			wakeLock.acquire();
		}
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
		if (torch != null && !torch.isOn())
		{
			torch.release();
			torch = null;
			wakeLock.release();
		}
	}

} // fin de la clase MainActivity

// fin de MainActivity.java ------------------------------------------
