package mingming.research.phonestackorder;

import camera.CameraFragmentListener;
import android.app.Activity;
import android.os.Bundle;

public class CameraActivity extends Activity implements CameraFragmentListener {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.cameraview);
	    }
	
	@Override
	public void onCameraError() {
		// TODO Auto-generated method stub
		finish();
	}

}
