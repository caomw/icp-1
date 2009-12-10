package edu.mit.media.icp.client.math;


public class Matrix {
	public static double[] product(int m1, int n1, int m2, int n2, double[] A,
			double[] B) {
		// Multiplies the two given square matrices. Returns the result.
		double[] result = new double[m1 * n2];
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n2; j++) {
				result[i * n2 + j] = 0;
				for (int k = 0; k < n1; k++) {
					result[i * n2 + j] += A[i * n1 + k] * B[k * n2 + j];
				}
			}
		}
		return result;

	}
	public static double[] product(double[] A, double x) {
		double[] answer = new double[A.length];
		for (int i = 0; i < A.length; i++) {
			answer[i] = A[i] * x;
		}
		return answer;
	}

	public static double[] threeVectorCrossProduct(double[] A, double[] B) {
		double a, b, c, x, y, z;
		a = A[0];
		b = A[1];
		c = A[2];
		x = B[0];
		y = B[1];
		z = B[2];
		double[] answer = {-c * y + b * z, c * x - a * z, -b * x + a * y};
		return answer;
	}

	public static double[] sum(int m, int n, double[] A, double[] B) {
		//if (A.length != B.length) System.out.println("Matrix addition failed.");
		double[] R = new double[m * n];
		for (int i = 0; i < m * n; i++) {
			R[i] = A[i] + B[i];
		}
		return R;
	}
	public static double[] difference(int m, int n, double[] A, double[] B) {
		//if (A.length != B.length) System.out.println("Matrix subtraction failed.");
		double[] R = new double[m * n];
		for (int i = 0; i < m * n; i++) {
			R[i] = A[i] - B[i];
		}
		return R;
	}
}