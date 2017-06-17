package com.usp.icmc.taemcasa.Maps;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by dionen on 16/06/2017.
 */

public class LatLng_Utils extends FragmentActivity  {

    private LatLng coord;

    public LatLng_Utils(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        coord = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            coord = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public float getLat(){
        return (float) coord.latitude;
    }

    public float getLng(){
        return (float) coord.longitude;
    }
}
