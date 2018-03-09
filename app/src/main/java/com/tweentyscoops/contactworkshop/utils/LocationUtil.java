package com.tweentyscoops.contactworkshop.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.List;
import java.util.Locale;

public class LocationUtil {

    public interface ReverseGeocodingCallback {
        void onReverseSuccess(String address);

        void onReverseError();
    }

    public static void reverseGeocoding(final Context context, Location location, final ReverseGeocodingCallback callback) {
        Geocoder gc = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String featureName = address.getFeatureName();
                String subLocality = address.getSubLocality();
                String countryName = address.getCountryName();
                String passCode = address.getPostalCode();
                if (!featureName.isEmpty()) featureName = featureName + ",";
                if (!subLocality.isEmpty()) subLocality = subLocality + ",";
                if (!countryName.isEmpty()) countryName = countryName + ",";
                String result = String.format("%s %s %s %s", featureName,
                        subLocality, countryName, passCode);
                callback.onReverseSuccess(result);
            } else {
                callback.onReverseError();
            }
        } catch (Exception e) {
            callback.onReverseError();
        }
    }
}
