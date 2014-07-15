package mingming.research.phonestackorder;

import java.util.Timer;
import java.util.TimerTask;

import camera.CameraFragmentListener;
import mingming.research.ClientSocket;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener{

	 Button bt_connect;
	Thread clientThread;
	ClientSocket myClientSocket = null;
	
	Timer mTimer;
	Paint mPaint;
	View mView;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);         
        mView = findViewById(R.id.container).getRootView();
        bt_connect = (Button)findViewById(R.id.button_connect);
        bt_connect.setOnClickListener(this);
        
        mPaint = new Paint();
        

    }
    
    protected void onResume()
    { 	
    	super.onResume();
    }
    
    static int prev_id = -1;
    
    class myTimerTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					String[] data = myClientSocket.text.split(",");
					if(data.length == 2)
					{
						int id = Integer.parseInt(data[1].trim());
						
						if(prev_id != id)
						{
							if(id == 1)
							{ 
								mView.setBackgroundColor(Color.GREEN);
							}
							else if(id == 0)
							{
							  //start another intent
								Intent mIntent = new Intent(getApplicationContext(), CameraActivity.class);
								startActivity(mIntent);
							}
							prev_id = id;
						}			
					}
				}
			});
		}
    }

    
    protected void onPause()
    {    	

    	super.onPause();
    }
    
    
    protected void onDestroy()
    {
    	if(mTimer != null)
    	{
    		mTimer.cancel();
    	}
    	super.onDestroy();
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            

            
            return rootView;
        }
    }
    
    
    protected void onDraw(Canvas canvas){
    	
    	mPaint.setColor(Color.GREEN);
    	mPaint.setStyle(Style.FILL);
    	canvas.drawPaint(mPaint);
    }
    
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.button_connect:
				if(!ClientSocket.running)
				{
					ClientSocket.running = true;
			    	if(myClientSocket == null)
			    	{
			    		myClientSocket = new ClientSocket();
			    		if(clientThread == null)
			    		{
			    			clientThread = new Thread(myClientSocket);
			    			clientThread.start();
			    		}
			    	}
  
				}
				else
				{
					ClientSocket.running = false;
					if(clientThread != null && clientThread.isAlive())
					{
						clientThread.interrupt();
					}
				}
				
		    	if(mTimer == null)
		    	{
		    		mTimer = new Timer();
		    		mTimer.scheduleAtFixedRate(new myTimerTask(), 1000, 30);
		    	} 
				break;
			default:
				break;
		}
	}



}
