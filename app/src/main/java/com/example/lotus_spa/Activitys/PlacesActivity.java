package com.example.lotus_spa.Activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.lotus_spa.Class.Route;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.GPS.DirectionFinder;
import com.example.lotus_spa.Utilits.GPS.DirectionFinderListener;
import com.example.lotus_spa.databinding.ActivityPlacesBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlacesActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private ActivityPlacesBinding binding;

    private LocationListener locationListener;
    private LocationManager locationManager;

    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // 5 meters
    private double longi, lat;

    private LatLng latLng;
    private LatLng pos[] = new LatLng[4];


    private ProgressDialog progressDialog;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try{
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.i("PlaceActivity", String.valueOf(latLng.latitude));
                    SharedPreferences coordenada = getSharedPreferences("Coordenadas", MODE_PRIVATE);
                    SharedPreferences.Editor edit = coordenada.edit();
                    edit.putString("lng", String.valueOf(latLng.longitude));
                    edit.putString("lat",String.valueOf(latLng.latitude));
                    edit.commit();
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }


        sendRequest();
    }

    private void sendRequest() {
        String origin = myPosition();
        String destination = TakeProximidade();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Por favor, espere.",
                "Procurando direções..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));
            Toast.makeText(this, "Tempo de: "+ route.duration.text + "\n" +
                    "Distância de: "+ route.distance.text , Toast.LENGTH_LONG).show();

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // mMap.addPolyline(new PolylineOptions().add(
        //         pos[0],
        // ))
    }

    @Override
    public void finish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            super.finish();
            // Swap without transition
        }
    }

    private String TakeProximidade() {

        SharedPreferences s = getSharedPreferences("Coordenadas", MODE_PRIVATE);
        String lng = s.getString("lng","0");
        String lat = s.getString("lat","0");

        LatLng e = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        pos[0] = new LatLng(-23.545124, -46.793754);
        pos[1] = new LatLng(-23.653562, -46.414635);
        pos[2] = new LatLng(-23.956015, -46.340027);
        pos[3] = new LatLng(-23.713187, -47.408387);
        if(e !=null) {
            double distancia1 = SphericalUtil.computeDistanceBetween(e, pos[0]);
            double distancia2 = SphericalUtil.computeDistanceBetween(e, pos[1]);
            double distancia3 = SphericalUtil.computeDistanceBetween(e, pos[2]);
            double distancia4 = SphericalUtil.computeDistanceBetween(e, pos[3]);
//
            if (distancia1 < distancia2 && distancia1 < distancia3 && distancia1 < distancia3) {
                return "R.Formosa, 33 - Vila Yolanda";
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos[0]));
                //mMap.addMarker(new MarkerOptions().position(pos[0]).title("R.Formosa, 33 - Vila Yolanda"));
                //mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
            } else if (distancia2 < distancia1 && distancia2 < distancia3 && distancia2 < distancia4) {
                return "Jardim Taquarussu";
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos[1]));
                //mMap.addMarker(new MarkerOptions().position(pos[1]).title("Jardim Taquarussu"));
                //mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
            } else if (distancia3 < distancia1 && distancia3 < distancia2 && distancia3 < distancia4) {
                return "R. Visc. de Farias, 83-33 - Campo Grande";
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos[2]));
                //mMap.addMarker(new MarkerOptions().position(pos[2]).title("R. Visc. de Farias, 83-33 - Campo Grande"));
                //mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
            } else if (distancia4 < distancia1 && distancia4 < distancia2 && distancia4 < distancia3) {
                return "Vila Olinda";
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos[3]));
                //mMap.addMarker(new MarkerOptions().position(pos[3]).title("Vila Olinda"));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            }
        }
        return null;
    }
    public String myPosition() {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = null;
            try {
                SharedPreferences s = getSharedPreferences("Coordenadas", MODE_PRIVATE);
                String lng = s.getString("lng","0");
                String lat = s.getString("lat","0");

                address = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
                if (address == null && address.size() == 0) {
                    return null;
                } else {
                    StringBuilder addressString = new StringBuilder();
                    Address address1 = address.get(0);
                    String a = address1.getAddressLine(0);
                    return a;
                }
            } catch (IOException e) {
            }
        return "Rio Pequeno";
    }


}