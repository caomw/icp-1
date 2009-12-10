package edu.mit.media.icp.client;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CustomCameraView extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	CustomCameraView(Activity context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setBackgroundColor(Color.TRANSPARENT);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
			Log.println(Log.ERROR, "Error", e.toString());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(w, h);
		parameters.setPictureFormat(PixelFormat.JPEG); // or PNG ?
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

}