package edu.mit.media.icp.client;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.location.Location;
import android.util.Log;

public class State {

	private Location location;
	private Orientation orientation;

	private ArrayList<StateListener> stateListeners;
	private LinkedList<Location> locations;
	private LinkedList<Orientation> orientations;
	private HashSet<Target> targets;
	public static final int HISTORY_SIZE = 3;

	public class Target {
		float lat;
		float lng;
		float theta;
		float phi;
		float psi;

		public Target(float lat, float lng, float theta, float phi, float psi) {
			this.lat = lat;
			this.lng = lng;
			this.theta = theta;
			this.phi = phi;
			this.psi = psi;
		}
		
		public String toString(){
			return "target:\n" + lat + "\n" + lng + "\n" + theta + "\n" + phi + "\n" + psi;
		}
	}

	public class Orientation {
		private float[] values;

		public Orientation() {
			values = new float[3];
		}

		public void setValues(float[] values) {
			for (int i = 0; i < 3; i++) {
				this.values[i] = values[i];
			}
		}

		public float getCompassDirection() {
			return values[0];
		}

		public float getPitch() {
			return values[1];
		}

		public float getRoll() {
			return values[2];
		}

		public void setCompassDirection(float i) {
			values[0] = i;
		}

		public void setPitch(float i) {
			values[1] = i;
		}

		public void setRoll(float i) {
			values[2] = i;
		}
	}

	// Private constructor prevents instantiation from other classes
	private State() {
		stateListeners = new ArrayList<StateListener>();
		locations = new LinkedList<Location>();
		orientations = new LinkedList<Orientation>();
		orientation = new Orientation();
		targets = new HashSet<Target>();
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class StateHolder {
		private static final State INSTANCE = new State();
	}

	public static State getInstance() {
		return StateHolder.INSTANCE;
	}

	/*
	 * Getters / Setters
	 */

	public static HashSet<Target> getTargets() {
		return getInstance().targets;
	}

	public static void addTarget(Target t) {
		getInstance().targets.add(t);
	}

	public static void clearTargets() {
		getInstance().targets.clear();
	}

	/* temp */
	private static final String END_OF_INPUT = "\\Z";

	private static String getPageContent() {
		String result = null;
		URLConnection connection = null;
		URL fURL = null;
		try {
			fURL = new URL("http://radiant-flower-17.heroku.com/target");
			connection = fURL.openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter(END_OF_INPUT);
			result = scanner.next();
		} catch (IOException ex) {
			Log.e("data", "Cannot open connection to " + fURL.toString());
		}
		return result;
	}

	public static void updateTargets() {
		clearTargets();

		String s = getPageContent();
		Object obj = JSONValue.parse(s);
		JSONArray ja = (JSONArray)obj;
		
		for(int i = 0; i < ja.size(); i++){
			String t = ja.get(i).toString();
			JSONObject j = (JSONObject)JSONValue.parse(t);
			JSONObject jo = (JSONObject)JSONValue.parse(j.get("target").toString());

			float lat 	= ((Number)jo.get("lat")).floatValue();
			float lng 	= ((Number)jo.get("lng")).floatValue();
			float theta = ((Number)jo.get("theta")).floatValue();
			float phi 	= ((Number)jo.get("phi")).floatValue();
			float psi 	= ((Number)jo.get("psi")).floatValue();
			
			State.addTarget(State.getInstance().new Target(lat,lng,theta,phi,psi));
		}
	}
	
	public static void setOrientation(float[] orientation) {
		if (orientation == null)
			return;
		getInstance().orientation.setValues(orientation);
		getInstance().orientations.add(getInstance().orientation);
		if (getInstance().orientations.size() > 3)
			getInstance().orientations.removeFirst();
		getInstance().notifyListeners();

	}

	public static Orientation getOrientation() {
		return getInstance().orientation;
	}

	public static void setLocation(Location location) {
		if (location == null)
			return;
		getInstance().location = location;
		getInstance().locations.add(location);
		if (getInstance().locations.size() > 3)
			getInstance().locations.removeFirst();
		getInstance().notifyListeners();
	}

	public static Location getLocation() {
		return getInstance().location;
	}

	/*
	 * Listeners
	 */

	public static void addListener(StateListener sl) {
		getInstance().stateListeners.add(sl);
	}

	public static void removeListener(StateListener sl) {
		getInstance().stateListeners.remove(sl);
	}

	public static void clearListeners() {
		getInstance().stateListeners.clear();
	}

	private void notifyListeners() {
		for (StateListener sl : stateListeners) {
			sl.callback();
		}
	}

	/*
	 * Historical data
	 */

	public LinkedList<Orientation> getOrientations() {
		return getInstance().orientations;
	}

	public LinkedList<Location> getLocations() {
		return getInstance().locations;
	}

	/*
	 * Debugging
	 */

	private static final String TAG = "State";

	private static void debug() {
		getInstance();
		Log.d(TAG, "orientation");
		Log.d(TAG, new Float(State.getOrientation().getCompassDirection())
				.toString());
	}
}
