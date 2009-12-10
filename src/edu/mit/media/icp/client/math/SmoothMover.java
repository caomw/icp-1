package edu.mit.media.icp.client.math;
// Takes 2 (x,y,z,theta,phi) tuples and parameter u (a double ranged from 0.0 to 1.0) and then returns a
//(x,y,z,thata,phi) tuple, the interpolated result based on u.


public class SmoothMover {
	public static double[] linearMove(	double x1,double y1,double z1,double t1,double p1,double fov1,
										double x2,double y2,double z2,double t2,double p2,double fov2,
										double u){
		double theta = smallestAngle(t2 - t1);
		double phi = smallestAngle(p2 - p1);
		
		double myArray[] = {	
			x1 + u*(x2-x1),
			y1 + u*(y2-y1),
			z1 + u*(z2-z1),
			t1 + u*(theta),
			p1 + u*(phi),
			fov1 + u*(fov2-fov1)};
		return myArray;
	}

	public static double[] sinMove(	double x1, double y1, double z1, double t1, double p1, double fov1,
									double x2, double y2, double z2, double t2, double p2, double fov2,
									double u) {
		double theta = smallestAngle(t2 - t1);
		double phi = smallestAngle(p2 - p1);
		double eq = Math.sin((u-.5)*Math.PI)/2+.5;

		double myArray[] = { 
			x1 + eq * (x2 - x1), 
			y1 + eq * (y2 - y1),
			z1 + eq * (z2 - z1), 
			t1 + eq * (theta), 
			p1 + eq * (phi),
			fov1 + eq * (fov2-fov1)};
		return myArray;
	}

	public static double[] sigmoidMove(	double x1, double y1, double z1, double t1, double p1, double fov1,
										double x2, double y2, double z2, double t2, double p2, double fov2,
										double u) {
		int quickness = 7;//higher values enhance illusion of quickness
		double theta = smallestAngle(t2 - t1);
		double phi = smallestAngle(p2 - p1);
		double eq = 1/(1+Math.pow(Math.E,-(2*u-1)*quickness));
		
		double myArray[] = { 
			x1 + eq * (x2 - x1), 
			y1 + eq * (y2 - y1),
			z1 + eq * (z2 - z1), 
			t1 + eq * (theta), 
			p1 + eq * (phi),
			fov1 + eq * (fov2-fov1)};
		return myArray;
		}
		

	public static double[] polynomialMove(	double x1, double y1, double z1, double t1, double p1, double fov1,
											double x2, double y2, double z2, double t2, double p2, double fov2,
											double u) {
		double theta = smallestAngle(t2 - t1);
		double phi = smallestAngle(p2 - p1);
		double eq = u*u*(u-1.5)*-2;
		
		double myArray[] = { 
			x1 + eq * (x2 - x1), 
			y1 + eq * (y2 - y1),
			z1 + eq * (z2 - z1), 
			t1 + eq * (theta), 
			p1 + eq * (phi),
			fov1 + eq * (fov2-fov1)};
		return myArray;
		}
	//blend between sigmoid and sin movements.
	public static double[] blendedMove(	double x1, double y1, double z1, double t1, double p1, double fov1,
										double x2, double y2, double z2, double t2, double p2, double fov2,
										double u) {
		double sinCoords[] = sinMove(x1,y1,z1,t1,p1,fov1,x2,y2,z2,t2,p2,fov2,u);
		double sigmoidCoords[] = sigmoidMove(x1,y1,z1,t1,p1,fov1,x2,y2,z2,t2,p2,fov2,u);
		//favor sigmoid near center
		
		double myArray[] = new double[6];            
		double sigEq = -Math.pow(2*u-1,2)+1;
		double sinEq = 1 - sigEq;
		for(int i = 0; i < myArray.length; i++){
			myArray[i] = sigmoidCoords[i]*sigEq + sinCoords[i]*sinEq;
		}
		return myArray;
	}
	
	private static double smallestAngle(double angle){
		double toReturn = angle % 360;
		if(toReturn > 180)
			toReturn = -(360-toReturn);
		return toReturn;
	}
}
