package edu.mit.media.icp.client.graphics;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.util.Log;
import edu.mit.media.icp.client.State;

/**
 * Render a pair of tumbling cubes.
 */

class CubeRenderer implements GLSurfaceView.Renderer {
	private float mPitch;
	private float mRoll;
	private Frusta f;

	public CubeRenderer(boolean useTranslucentBackground) {
		mTranslucentBackground = useTranslucentBackground;
		mCube = new Cube();
		
	}

	public void onDrawFrame(GL10 gl) {
		/*
		 * Usually, the first thing one might want to do is to clear the screen.
		 * The most efficient way of doing this is to use glClear().
		 */

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		/*
		 * Now we're ready to draw some 3D objects
		 */

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -3.0f);

		gl.glRotatef(mAngle, 0, 1, 0);
		// gl.glRotatef(mPitch, 1, 0, 0);
		// gl.glRotatef(mRoll, 0, 0, 1);

		try {
			mAngle = State.getOrientation().getCompassDirection();
			mPitch = -State.getOrientation().getPitch();
			// mRoll = State.getOrientation().getRoll();
			// Log.d("compass", mAngle + "");
			// Log.d("pitch", mPitch + "");
			// Log.d("roll", mRoll + "");
		} catch (Exception e) {
			Log.e("opengl", e.getMessage());
		}

		/* Compute the direction we're looking */

		/*
		 * theta and phi are the heading in the plane and tilt out of the plane
		 * angles
		 */
		float mTheta = mPitch;
		float mPhi = mAngle;
		
		gl.glRotatef(mAngle, 0, 1, 0);
		//gl.glRotatef(mPitch, 1, 0, 0);
//		
//		double theta = (double) (Math.PI / 180.0 * mTheta);
//		double phi = (double) (Math.PI / 180.0 * mPhi);
//		float[] v = { 0, 0, 0 };
//		float[] up = { 0, 0, 0 };
//
//		v[0] = (float) (Math.cos(theta) * Math.sin(phi));
//		v[1] = (float) Math.cos(phi);
//		v[2] = (float) (Math.sin(theta) * Math.sin(phi));
//
//		/* Compute the new up vector */
//		float phi_up = (float) (Math.PI / 180.0 * (mPhi - 90.0));
//		float theta_up = (float) theta;
//
//		up[0] = (float) (Math.cos(theta_up) * Math.sin(phi_up));
//		up[1] = (float) Math.cos(phi_up);
//		up[2] = (float) (Math.sin(theta_up) * Math.sin(phi_up));

		/* "eye" is the position of the camera */
		//GLU.gluLookAt(gl, 0f, 0f, 0f, v[0], v[1], v[2], up[0], up[1], up[2]);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		mCube.draw(gl);
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		/*
		 * Set our projection matrix. This doesn't have to be done each time we
		 * draw, but usually a new projection needs to be set when the viewport
		 * is resized.
		 */

		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/*
		 * By default, OpenGL enables features that improve quality but reduce
		 * performance. One might want to tweak that especially on software
		 * renderer.
		 */
		gl.glDisable(GL10.GL_DITHER);

		/*
		 * Some one-time OpenGL initialization can be made here probably based
		 * on features of this particular context
		 */
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		if (mTranslucentBackground) {
			gl.glClearColor(0, 0, 0, 0);
		} else {
			gl.glClearColor(1, 1, 1, 1);
		}
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	private boolean mTranslucentBackground;
	private Cube mCube;
	private float mAngle;
}
