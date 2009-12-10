//package edu.mit.media.icp.client.graphics;
//
//import java.util.ArrayList;
//
//import javax.microedition.khronos.opengles.GL;
//
//public class Camera {
//	public int index;
//	public int w;
//	public int h;
//	public int adj;
//	public double focal;
//	public double[] rot = new double[9];
//	public double[] t = new double[3];
//	public String name;
//	public String linkURL;
//	public String title;
//	public String takenBy;
//	public String takenOn;
//	public int back;
//	public double[] pp1 = new double[3];
//	public double[] pp2 = new double[3];
//	public double[] pp3 = new double[3];
//	public double[] pp4 = new double[3];
//	// private BufferedImage originalImage;
//	// private Buffer texture;
//	private CameraList cameras;
//	private ArrayList<Integer> visiblePoints;
//
//	public Camera(int index) {
//		this.index = index;
//		visiblePoints = new ArrayList<Integer>();
//	}
//
//	public void setCameraList(CameraList cameras) {
//		this.cameras = cameras;
//	}
//
//	// Return this camera's position.
//	public double[] getPosition() {
//		double[] position = new double[3];
//
//		position[0] = -(t[0] * rot[0] + t[1] * rot[3] + t[2] * rot[6]);
//		position[1] = -(t[0] * rot[1] + t[1] * rot[4] + t[2] * rot[7]);
//		position[2] = -(t[0] * rot[2] + t[1] * rot[5] + t[2] * rot[8]);
//
//		return position;
//	}
//
//	// Return this camera's pose.
//	public double[] getPose() {
//		double[] pose = new double[9];
//
//		pose[0] = rot[0];
//		pose[3] = rot[1];
//		pose[6] = rot[2];
//		pose[1] = rot[3];
//		pose[4] = rot[4];
//		pose[7] = rot[5];
//		pose[2] = rot[6];
//		pose[5] = rot[7];
//		pose[8] = rot[8];
//
//		return pose;
//	}
//
//	// Returns this camera's bounding box.
//	public double[] getBoundingBox() {
//		double[] boundingBox = { -w / 2.0, -h / 2.0, w / 2.0, h / 2.0 };
//		return boundingBox;
//	}
//
//	// Add this camera to the drawlist.
//	public void createDrawList(GL gl, double m_scale, double m_ref_length) {
//		/* Get the camera position and pose */
//		double[] camPos = getPosition();
//		double[] R = getPose();
//
//		/* Compute the position of the four corners of the frustum */
//		double xExt = 0.5 * w * m_ref_length * m_scale / focal;
//		double yExt = 0.5 * h * m_ref_length * m_scale / focal;
//
//		/* Corner 1 */
//		double[] p0 = { -xExt, -yExt, -m_ref_length * m_scale };
//		double[] p0New = new double[3];
//		p0New = Matrix.product(3, 3, 3, 1, R, p0);
//		p0 = Matrix.sum(3, 1, camPos, p0New);
//
//		/* Corner 2 */
//		double[] p1 = { -xExt, yExt, -m_ref_length * m_scale };
//		double[] p1New = new double[3];
//		p1New = Matrix.product(3, 3, 3, 1, R, p1);
//		p1 = Matrix.sum(3, 1, camPos, p1New);
//
//		/* Corner 3 */
//		double[] p2 = { xExt, yExt, -m_ref_length * m_scale };
//		double[] p2New = new double[3];
//		p2New = Matrix.product(3, 3, 3, 1, R, p2);
//		p2 = Matrix.sum(3, 1, camPos, p2New);
//
//		/* Corner 4 */
//		double[] p3 = { xExt, -yExt, -m_ref_length * m_scale };
//		double[] p3New = new double[3];
//		p3New = Matrix.product(3, 3, 3, 1, R, p3);
//		p3 = Matrix.sum(3, 1, camPos, p3New);
//
//		float color[] = { 0.8f, 0.8f, 0.8f, 0.7f };
//		gl.glColor4fv(color, 0);
//
//		// Check if using high res texture;
//
//		/* Now draw the frustum */
//		gl.glBegin(GL.GL_QUADS);
//
//		/* Box around the back-face of the frustum */
//		gl.glVertex3d(p0[0], p0[1], p0[2]);
//		gl.glVertex3d(p1[0], p1[1], p1[2]);
//		gl.glVertex3d(p2[0], p2[1], p2[2]);
//		gl.glVertex3d(p3[0], p3[1], p3[2]);
//
//		gl.glEnd();
//
//		gl.glLineWidth(1);
//
//		float color[] = { 0.0f, 0.0f, 0.0f, 0.6f };
//		gl.glColor4fv(color, 0);
//
//		gl.glBegin(GL.GL_LINES);
//
//		/* Lines radiating from the tip of the frustum */
//		gl.glVertex3d(camPos[0], camPos[1], camPos[2]);
//		gl.glVertex3d(p0[0], p0[1], p0[2]);
//
//		gl.glVertex3d(camPos[0], camPos[1], camPos[2]);
//		gl.glVertex3d(p1[0], p1[1], p1[2]);
//
//		gl.glVertex3d(camPos[0], camPos[1], camPos[2]);
//		gl.glVertex3d(p2[0], p2[1], p2[2]);
//
//		gl.glVertex3d(camPos[0], camPos[1], camPos[2]);
//		gl.glVertex3d(p3[0], p3[1], p3[2]);
//
//		gl.glEnd();
//
//	}
//
//	public String toString() {
//		String returnString = "<Camera: " + "w=" + w + ", h=" + h + ", adj="
//				+ adj + ", name=" + name + ", focal=" + focal + ", back="
//				+ back + ", rot=[";
//		for (int i = 0; i < 9; i++) {
//			returnString += rot[i] + ",";
//		}
//		returnString += "], t=[";
//		for (int i = 0; i < 3; i++) {
//			returnString += t[i] + ",";
//		}
//		returnString += "]>";
//		return returnString;
//	}
//
//	public double getFov() {
//		// calculate FOV based on height
//		double heightFOV = Math.atan2(h, focal);
//		// calculate FOV based on height
//		double widthFOV = Math.atan2(w, focal);
//		// return the larger FOV in degrees
//		if (heightFOV > widthFOV)
//			return heightFOV * 180 / Math.PI;
//		else
//			return widthFOV * 180 / Math.PI;
//	}
//
//	// Adds a point to this cameras visible points list.
//	public void addPoint(int pointIndex) {
//		visiblePoints.add(new Integer(pointIndex));
//	}
//
//	// Returns a step back score.
//	public int getStepBackScore(ArrayList<Integer> targetVisiblePoints) {
//		int targetIndex = 0;
//		int thisIndex = 0;
//		int equalCount = 0;
//		int inTarget = 0;
//		int inThis = 0;
//		while (targetIndex < targetVisiblePoints.size()
//				&& thisIndex < visiblePoints.size()) {
//			int targetPoint = targetVisiblePoints.get(targetIndex);
//			int thisPoint = visiblePoints.get(thisIndex);
//
//			if (targetPoint == thisPoint) {
//				targetIndex++;
//				thisIndex++;
//				equalCount++;
//			} else if (targetPoint > thisPoint) {
//				thisIndex++;
//				inThis++;
//			} else {
//				targetIndex++;
//				inTarget++;
//			}
//		}
//
//		while (targetIndex < targetVisiblePoints.size()) {
//			targetIndex++;
//			inTarget++;
//		}
//		while (thisIndex < visiblePoints.size()) {
//			thisIndex++;
//			inThis++;
//		}
//
//		int score = equalCount * 10 - inTarget * 20 - inThis;
//		// System.out.println(" equal=" + equalCount + ", target=" + inTarget +
//		// ", this=" + inThis + ", score=" + score);
//
//		return score;
//	}
//
//	// Returns the index of the step back camera.
//
//	public int getIndex() {
//		return index;
//	}
//}