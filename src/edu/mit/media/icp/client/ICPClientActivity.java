//TODO: Camera button
//TODO: GPS / Orientation / Picture -> email

package edu.mit.media.icp.client;

import java.util.Set;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import edu.mit.media.icp.client.State.Target;
import edu.mit.media.icp.client.graphics.GLLayer;
import edu.mit.media.icp.client.sensors.GPSListener;
import edu.mit.media.icp.client.sensors.OrientationListener;

public class ICPClientActivity extends Activity {
	OrientationListener ol;
	GPSListener gpsl;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ol = new OrientationListener(this);
		gpsl = new GPSListener(this);
		loadData();
		//setupWindow();
	}
	
	private void loadData(){
		State.updateTargets();
		Set<Target> ts = State.getTargets();
		for(Target t : ts){
			Log.e("target", t.toString());
		}
	}

	private void setupWindow() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setupContent();
	}

	private void setupContent() {
		FrameLayout rl = new FrameLayout(getApplicationContext());
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();

		CustomCameraView preview = new CustomCameraView(this);
		GLLayer layer = new GLLayer(this);

		rl.addView(layer, width, height);
		
//		rl.addView(preview, width, height);

		setContentView(rl);
	}
}
