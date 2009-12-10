package edu.mit.media.icp.client.graphics;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class GLLayer extends GLSurfaceView{
	public GLLayer(Context context) {
		super(context);
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		setRenderer(new CubeRenderer(true));
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
}
