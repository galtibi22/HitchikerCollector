package utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class LocationUtil {

    private Context context;
    public LocationUtil(Context context) {
        this.context = context;
        context.startService(new Intent(context, LocationService.class));
    }

    public String getLatLngFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            return address.get(0).getLatitude() + "," + address.get(0).getLongitude();
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        LatLng currentLocation = null;
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
        } else {
            if (isGPSEnabled) {
                Log.d("activity", "RLOC: GPS Enabled");
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "RLOC: loc by GPS");
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            }
            if (isNetworkEnabled && currentLocation == null) {
                Log.d("activity", "LOC Network Enabled");
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "LOC by Network");
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        }
        return currentLocation.latitude + "," + currentLocation.longitude;
    }

}
