package edu.mit.media.icp.client.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.util.Log;

public class Bundle implements Drawable {
	// fixed point representations of camera frustrum box coords
	private static final int z = (int)(-0.7*(0x10000));
	private static final int span = (int)(0.5*(0x10000));
	private static final int one = 0x10000;
	
	private static final int[] frustrum = {0,0,0,
											span, span, z,
											span, -span, z,
											-span, -span, z
											-span, span, z,
											span, span, z,};
	private static final int[] colors = {one, 0, 0, one,
											0, one, 0, one,
											0, one, 0, one,
											0, one, 0, one,
											0, one, 0, one,
											0, one, 0, one};

	// tag we use when we print to Android logs
	private static final String TAG = "Bundle";
	
	// FEATURE POINT DATA
	// the position of all feature points in this bundle
	// the size of each float[] in this Vector should be 3, and stores
	// the x,y,z coordinates of that feature point
	private Vector<float[]> mFeaturePointPositions;
	// the color of each feature point in the vector
	// the size of each float[] in this Vector should be 4, and stores
	// the r,g,b,a coordinates of that feature point
	private Vector<float[]> mFeaturePointColors;
	// the views in which feature point i is visible
	private Vector<Vector<Integer>> mVisibleFrom;
	
	// CAMERA DATA
	private Vector<Float> mFocalLengths;
	private Vector<Float> mRadialDistort1;
	private Vector<Float> mRadialDistort2;
	// each float[][] should be 3x3
	private Vector<float[][]> mRotationMat;
	// each float[] should have length 3
	private Vector<float[]> mCamTranslation;
	
	// OpenGL buffers for feature point data
	private IntBuffer mVertexBuffer;
	private IntBuffer mColorBuffer;
	// OpenGL buffers/data for camera positions and rotation matrices
	private IntBuffer mCamBuffer;
	private IntBuffer mCamColorBuffer;
	// column major flat representation of each camera rotation matrix, 16 elements
	// each element is stored in 16.16 fixed point format
	// the position of the camera is also stuffed in here as translation - so, to draw
	// a point at the camera location, just multiply (0,0,0) by this matrix, and to draw
	// a vector towards the direction the camera looks towards, draw a vector in the
	// direction (0,0,-1) and multiply it by this matrix
	private Vector<int[]> mRotationMatFlat;
	
	public Bundle(BufferedReader bundleFile) {
		mFeaturePointPositions = new Vector<float[]>();
		mFeaturePointColors = new Vector<float[]>();
		mVisibleFrom = new Vector<Vector<Integer>>();
		mFocalLengths = new Vector<Float>();
		mRadialDistort1 = new Vector<Float>();
		mRadialDistort2 = new Vector<Float>();
		mRotationMat = new Vector<float[][]>();
		mCamTranslation = new Vector<float[]>();
		
		// first, load the data from the provided file
		parseBundleFile(bundleFile);
		// then use that loaded data to initialize our OpenGL buffers
		loadGLBuffers();
	}
	
	/*
	 * Parses the specified bundle file, filling in this class's
	 * data structures with the data from that file.  Should only be
	 * called once, as the class is constructed.
	 */
	private void parseBundleFile(BufferedReader bundleFile) {
		String line;
		int numCameras, numPoints;
		
		line = getNextNonComment(bundleFile);
		if(line == null) {
			// there was nothing but comments in the file (or an IOException occured)
			Log.w(TAG, "Bundle file contains only comments");
			return;				
		}
		
		// read line which gives numCameras and numPoints
		String[] tokens = line.split("[ ]+");
		if(tokens.length != 2) {
			Log.w(TAG, "Wrong number of tokens; expected \"[num_cameras] [num_points]\", got " + line);
			return;
		}
		try {
			numCameras = Integer.parseInt(tokens[0]);
			numPoints = Integer.parseInt(tokens[1]);
		}
		catch(NumberFormatException nfe) {
			Log.w(TAG, "Unable to parse number of cameras or points; expected \"[num_cameras] [num_points]\", got " + line);
			return;
		}
		
		for(int j=0; j<numCameras; j++) {
			if(!readCamera(bundleFile)) {
				return;
			}
		}
		
		for(int i=0; i<numPoints; i++) {
			if(!readPoint(bundleFile)) {
				return;
			}
		}
	}
	
	/*
	 * Attempts to read a camera definition from the given stream, placing that
	 * camera's data at the end of the camera vectors
	 * Returns true if a camera was successfully read, false if there was an error (not enough
	 * lines in file, invalid format for data, etc)
	 */
	private boolean readCamera(BufferedReader bundleFile) {
		String line;
		String[] tokens;
		float mat[][] = new float[3][3];
		float translation[] = new float[3];
		
		// focal length, distortion coefficients
		if((line = getNextNonComment(bundleFile)) == null) {
			Log.w(TAG, "Error reading camera params.");
			return false;
		}
		tokens = line.split("[ ]+");
		if(tokens.length != 3) {
			Log.w(TAG, "Error reading camera params; received " + line);
			return false;
		}
		try {
			mFocalLengths.add(Float.valueOf(tokens[0]));
			mRadialDistort1.add(Float.valueOf(tokens[1]));
			mRadialDistort2.add(Float.valueOf(tokens[2]));
		}
		catch(NumberFormatException nfe) {
			Log.w(TAG, "Error reading camera params; received " + line + ". " + nfe.getMessage());
			return false;
		}
		
		for(int i=0; i<3; i++) {
			// rotation matrix, row i
			if((line = getNextNonComment(bundleFile)) == null) {
				Log.w(TAG, "Error reading rotation matrix.");
				return false;
			}
			tokens = line.split("[ ]+");
			if(tokens.length != 3) {
				Log.w(TAG, "Error reading rotation matrix; received " + line);
				return false;
			}
			try {
				mat[i][0] = Float.parseFloat(tokens[0]);
				mat[i][1] = Float.parseFloat(tokens[1]);
				mat[i][2] = Float.parseFloat(tokens[2]);
			}
			catch(NumberFormatException nfe) {
				Log.w(TAG, "Error reading rotation matrix; received " + line + ". " + nfe.getMessage());
				return false;
			}
		}
		mRotationMat.add(mat);
		
		// camera translation
		if((line = getNextNonComment(bundleFile)) == null) {
			Log.w(TAG, "Error reading camera translation.");
			return false;
		}
		tokens = line.split("[ ]+");
		if(tokens.length != 3) {
			Log.w(TAG, "Error reading camera translation; received " + line);
			return false;
		}
		try {
			translation[0] = Float.parseFloat(tokens[0]);
			translation[1] = Float.parseFloat(tokens[1]);
			translation[2] = Float.parseFloat(tokens[2]);
		}
		catch(NumberFormatException nfe) {
			Log.w(TAG, "Error reading camera translation; received " + line + ". " + nfe.getMessage());
			return false;
		}
		mCamTranslation.add(translation);
		
		return true;
	}
	
	private boolean readPoint(BufferedReader bundleFile) {
		String line;
		String[] tokens;
		
		// POSITION
		float[] position = new float[3];
		if((line = getNextNonComment(bundleFile)) == null) {
			Log.w(TAG, "Error reading feature point position.");
			return false;
		}
		tokens = line.split("[ ]+");
		if(tokens.length != 3) {
			Log.w(TAG, "Incorrect number of coordinates for feature point position: received " + line);
			return false;
		}
		try {
			position[0] = Float.parseFloat(tokens[0]);
			position[1] = Float.parseFloat(tokens[1]);
			position[2] = Float.parseFloat(tokens[2]);
		}
		catch(NumberFormatException nfe) {
			Log.w(TAG, "Error reading feature point position: received " + line);
			return false;
		}
		mFeaturePointPositions.add(position);
		
		// COLOR
		float[] color = new float[4];
		if((line = getNextNonComment(bundleFile)) == null) {
			Log.w(TAG, "Error reading feature point color.");
			return false;
		}
		tokens = line.split("[ ]+");
		if(tokens.length != 3) {
			Log.w(TAG, "Incorrect number of values for feature point color: received " + line);
			return false;
		}
		try {
			color[0] = Integer.parseInt(tokens[0])/255f;
			color[2] = Integer.parseInt(tokens[1])/255f;
			color[2] = Integer.parseInt(tokens[2])/255f;
			color[3] = 1.0f;
		}
		catch(NumberFormatException nfe) {
			Log.w(TAG, "Error reading feature point color: received " + line);
			return false;
		}
		mFeaturePointColors.add(color);
		
		// VIEW LIST
		if((line = getNextNonComment(bundleFile)) == null) {
			Log.w(TAG, "Error reading feature point view list.");
			return false;
		}
		tokens = line.split("[ ]+");
		Vector<Integer> visibleFrom = new Vector<Integer>();
		for(int i=0; i<tokens.length; i++) {
			// TODO: what's the deal with the view list? the example file contains
			// none-integer values
			/*try {
				visibleFrom.add(Integer.valueOf(tokens[i]));
			}
			catch(NumberFormatException nfe) {
				Log.w(TAG, "Error reading feature point view list: received " + line);
				return false;
			}*/
		}
		mVisibleFrom.add(visibleFrom);

		return true;
	}
	
	/*
	 * Returns the next non-comment line in the specified stream.  Returns null if
	 * the stream only contains comment lines, or if there's an IOException.
	 */
	private String getNextNonComment(BufferedReader reader) {
		String line;
		try {
			// just keep eating up lines until either we're out of lines, or the line read
			// is not a comment (it doesn't start with '#')
			while(((line = reader.readLine()) != null) && (line.charAt(0) == '#'));
		}
		catch(IOException ioe) {
			Log.w(TAG, "IOException in getNextNonComment: " + ioe.getMessage());
			return null;
		}
		
		return line;
	}
	
	/*
	 * Takes the bundle data currently loaded and uses it to fill in
	 * the OpenGL buffers (vertex, color, and index) for this class.
	 * Should only be called after the bundle data 
	 * (mFeaturePointPositions, mFeaturePointColors) is reloaded.
	 */
	private void loadGLBuffers() {
		assert mFeaturePointPositions.size() == mFeaturePointColors.size();
		
		ByteBuffer vertexBuffer = ByteBuffer.allocateDirect(mFeaturePointPositions.size()*3*4);
		vertexBuffer.order(ByteOrder.nativeOrder());
		mVertexBuffer = vertexBuffer.asIntBuffer();
		ByteBuffer colorBuffer = ByteBuffer.allocateDirect(mFeaturePointColors.size()*4*4);
		colorBuffer.order(ByteOrder.nativeOrder());
		mColorBuffer = colorBuffer.asIntBuffer();
		
		for(int i=0; i<mFeaturePointPositions.size(); i++) {
			assert mFeaturePointPositions.get(i).length == 3;
			mVertexBuffer.put(toFixedPoint(mFeaturePointPositions.get(i)[0]));
			mVertexBuffer.put(toFixedPoint(mFeaturePointPositions.get(i)[1]));
			mVertexBuffer.put(toFixedPoint(mFeaturePointPositions.get(i)[2]));
			
			assert mFeaturePointColors.get(i).length == 4;
			mColorBuffer.put(toFixedPoint(mFeaturePointColors.get(i)[0]));
			mColorBuffer.put(toFixedPoint(mFeaturePointColors.get(i)[1]));
			mColorBuffer.put(toFixedPoint(mFeaturePointColors.get(i)[2]));
			mColorBuffer.put(toFixedPoint(mFeaturePointColors.get(i)[3]));
		}
		
		mVertexBuffer.position(0);
		mColorBuffer.position(0);
		
		assert mCamTranslation.size() == mRotationMat.size();
		
		ByteBuffer camBuffer = ByteBuffer.allocateDirect(frustrum.length*4);
		camBuffer.order(ByteOrder.nativeOrder());
		mCamBuffer = camBuffer.asIntBuffer();
		mCamBuffer.put(frustrum);
		mCamBuffer.position(0);
		
		ByteBuffer camColorBuffer = ByteBuffer.allocateDirect(colors.length*4);
		camColorBuffer.order(ByteOrder.nativeOrder());
		mCamColorBuffer = camColorBuffer.asIntBuffer();
		mCamColorBuffer.put(colors);
		mCamColorBuffer.position(0);
		
		mRotationMatFlat = new Vector<int[]>();
		for(int i=0; i<mCamTranslation.size(); i++) {
			float[] translation = mCamTranslation.get(i);
			assert translation.length == 3;
			float[][] rotation = mRotationMat.get(i);
			assert rotation.length == 3;
			assert rotation[0].length == 3;
			assert rotation[1].length == 3;
			assert rotation[2].length == 3;
			int[] rotationMatFlat = new int[16];
			// mRotationMat is (the first index identifies rows), and
			// we want rotationMatFlat to be column-major
			// however, we don't want rotationMatFlat to store mRotationMat, we want
			// it to store -trans(mRotationMat)
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					rotationMatFlat[j*4+k] = toFixedPoint(-rotation[j][k]);
				}
				rotationMatFlat[j*4+3] = 0;
				// camera position is -R' * t, according to bundle docs
				rotationMatFlat[3*4+j] = toFixedPoint(-(translation[0]*rotation[0][j] +
													translation[1]*rotation[1][j] +
													translation[2]*rotation[2][j]));
			}
			rotationMatFlat[3*4+3] = toFixedPoint(1);
			mRotationMatFlat.add(rotationMatFlat);
		}
	}
	
	/*
	 * Converts to 16.16 fixed point
	 */
	private int toFixedPoint(float val) {
		return (int)(val*(0x10000));
	}
	
	public void draw(GL10 gl) {
		// draw feature points
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBuffer);
        gl.glDrawArrays(GL10.GL_POINTS, 0, mFeaturePointPositions.size());
        
        // draw cameras
        //gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glFrontFace(GL10.GL_CCW);
        //gl.glColor4x(toFixedPoint(1.0f), 0, 0, toFixedPoint(1.0f));
        for(int i=0; i<mRotationMatFlat.size(); i++) {
        	gl.glPushMatrix();
        		gl.glVertexPointer(3, GL10.GL_FIXED, 0, mCamBuffer);
        		gl.glColorPointer(4, GL10.GL_FIXED, 0, mCamColorBuffer);
        		gl.glMultMatrixx(mRotationMatFlat.get(i), 0);
        		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, frustrum.length/3);
        	gl.glPopMatrix();
        }
	}

}
