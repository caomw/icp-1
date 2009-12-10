package edu.mit.media.icp.client.sensors;

import edu.mit.media.icp.client.State;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSListener implements LocationListener {
	LocationManager lm;
	private Location curLocation;

	public GPSListener(Activity a) {
		super();
		lm = (LocationManager) a.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1,
				this);
	}

	public void onLocationChanged(Location location) {
		if(curLocation == null)
			return;
		if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude()) {
			State.setLocation(location);
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
