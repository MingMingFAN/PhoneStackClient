package mingming.research.phonestackclient;

import java.util.Timer;
import java.util.TimerTask;

import mingming.research.socketclient.ClientSocket;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements Camera.PreviewCallback{

	private SurfaceView preview = null;
	private SurfaceHolder previewHolder = null;
	private Camera camera = null;
	private boolean inPreview = false;
	private boolean cameraConfigured = false;
	ClientSocket clt;
	
	Timer mTimer;
	
	private boolean showPreview = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		preview = (SurfaceView)findViewById(R.id.cameraView);
		preview.setWillNotDraw(false);

		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		
	}

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			// no-op -- wait until surfaceChanged()
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			initPreview(width, height);
			startPreview();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// no-op
		}
	};

	protected void onResume() {
		super.onResume();

		new NetworkGetConnection().execute("");
		
	    camera=Camera.open();
	    startPreview();
	    
	    
	    if(mTimer != null)
	    	mTimer.cancel();
	    
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new myTimerTask(), 1000, 500);
	}

	
	protected void onPause() {
		if(mTimer != null)
			mTimer.cancel();
		
		
		camera.setPreviewCallback(null);
		
	    if (inPreview) {
		      camera.stopPreview();
		    }
		    
		    camera.release();
		    camera=null;
		    inPreview=false;
		
		new NetworkDisConnection().execute("");
		
		super.onPause();
	}
	
	private class NetworkGetConnection extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(clt == null)
			{
				clt = new ClientSocket();
				clt.connect2server();
			}
			return null;
		}
	}

	private class NetworkDisConnection extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (clt != null) {
				clt.disconnect();
				clt = null;
			}

			return null;
		}
	}



	private Camera.Size getBestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		Camera.Size result = null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea > resultArea) {
						result = size;
					}
				}
			}
		}
		return (result);
	}

	private void initPreview(int width, int height) {
		if (camera != null && previewHolder.getSurface() != null) {
			try {
				camera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
				Log.e("debug", "Exception in setPreviewDisplay()", t);
				Toast.makeText(MainActivity.this, t.getMessage(),
						Toast.LENGTH_LONG).show();
			}

			if (!cameraConfigured) {
				Camera.Parameters parameters = camera.getParameters();
				Camera.Size size = getBestPreviewSize(width, height, parameters);

				if (size != null) {
					parameters.setPreviewSize(size.width, size.height);
					camera.setParameters(parameters);
					cameraConfigured = true;
				}
			}
		}
	}

	private void startPreview() {
		if (cameraConfigured && camera != null) {
			camera.startPreview();
			inPreview = true;
			camera.setPreviewCallback(this);
		}
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		
	}
	
	
	class myTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(clt != null)
			{
				String command = clt.getReceivedMessage();
				Log.i("debug", "received command: " + command);
				if(command != null)
				{
					String pureCommand = command.trim();
					switch(pureCommand)
					{
						case "1": // define 1 to show camera preview 
							if(showPreview)
							{
								startPreview();
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										preview.setBackgroundColor(Color.TRANSPARENT);
									}
									
								});
								
							}
							showPreview = !showPreview;
							break;
						case "0": // define 0 to show the pure color
							if(!showPreview)
							{
							    if (inPreview) {
								      camera.stopPreview();
								    }
							    inPreview = false;
							    
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										preview.setBackgroundColor(Color.GREEN);
									}
									
								});

							}
							showPreview = !showPreview;
							
						break;
						default:
							break;
					}
				}
			}
		}
		
	}

}
