package com.example.ibrahim_01.locationapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private DatabaseReference databaseReference;


    private FirebaseAuth firebaseAuth;

   // private


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    private boolean setCamera = true;
    private float zoom = (float)15;
    final Marker[] userMarkers = new Marker[1000];
    List<String> listStrings = new ArrayList<String>();

    HashMap<String ,Marker> hashMapMarker = new HashMap<>();
    public int count2 = 0;
    int counterForTotalSnaps = 0;
    public  int counterForMarker = 0;
    private AutoCompleteTextView text;






    //  private Firebase mRef;


   // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        text=(AutoCompleteTextView)findViewById(R.id.usersAutoCompleteTxtView);

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,listStrings);
        text.setAdapter(adapter);
        text.setThreshold(1);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        databaseReference = FirebaseDatabase.getInstance().getReference();



        //Query query = databaseReference.orderByChild("status").equalTo(true);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userClass userClassObj = dataSnapshot.getValue(userClass.class);


                    if (dataSnapshot.getKey().equals(user.getUid())) {




                    }
                    else if(hashMapMarker.containsKey(dataSnapshot.getKey())){

                        Marker marker = hashMapMarker.get(dataSnapshot.getKey());

                        marker.setPosition(new LatLng(userClassObj.getLatitude(),userClassObj.getLongitude()));

                    }
                    else {


                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(userClassObj.getLatitude(), userClassObj.getLongitude()))
                                .anchor(0.5f, 0.5f)
                                .title(userClassObj.getFullName())
                                .snippet("newly added marker from child")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                        hashMapMarker.put(dataSnapshot.getKey(),marker);

                        listStrings.add(userClassObj.getFullName());


                        counterForMarker++;

                    }



                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                if (dataSnapshot.getKey().equals(user.getUid())) {


                }
                else {
                    userClass userClassObj = dataSnapshot.getValue(userClass.class);

                    Marker marker = hashMapMarker.get(dataSnapshot.getKey());

                    marker.setPosition(new LatLng(userClassObj.getLatitude(),userClassObj.getLongitude()));

                }





            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        query.addValueEventListener(new ValueEventListener() {
//
//                //    int count2 = 0;
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
////                         int count = -1;
////
////                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()  ){
////
////                            userClass userClassObj = userSnapshot.getValue(userClass.class);
////
////                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////
////
////
////                            count++;
////                            counterForTotalSnaps++;
////
////                            Log.i("dadasnap",dataSnapshot.getValue().toString());
////                            if( userSnapshot.getKey().equals(user.getUid()) ){
////
////                                    continue;
////                            }
////
////                            try {
////                                if(userMarkers[count] != null){
////
////                                if (userMarkers[count].getPosition().latitude == userClassObj.getLatitude() && userMarkers[count].getPosition().longitude == userClassObj.getLongitude()) {
////
////
////                                } else {
////
////                                      userMarkers[count].setPosition(new LatLng(userClassObj.getLatitude(),userClassObj.getLongitude()));
////
//////                                    userMarkers[count].remove();
//////                                    userMarkers[count] = mMap.addMarker(new MarkerOptions()
//////                                            .position(new LatLng(userClassObj.getLatitude(), userClassObj.getLongitude()))
//////                                            .anchor(0.5f, 0.5f)
//////                                            .title(userClassObj.getFullName())
//////                                            .snippet("from else clause")
//////                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
////
////
////
////
////                                }
////                                }
////                                else {
////
////                                    try {
////                                        userMarkers[count].remove();
////                                    }
////                                    catch (Exception e){
////                                        Log.i("dada",e.toString());
////                                    }
////                                    userMarkers[count] = mMap.addMarker(new MarkerOptions()
////                                            .position(new LatLng(userClassObj.getLatitude(), userClassObj.getLongitude()))
////                                            .anchor(0.5f, 0.5f)
////                                            .title(userClassObj.getFullName())
////                                            .snippet("first time")
////                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
////
////                                }
////                            }
////                            catch (Exception e ){
////                                Log.i("dada",e.toString());
////                            }
////                            count2++;
////                        }
////
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                        Log.i("dadaDouble",  databaseError.toString() );
//
//
//                    }
//                });




    }


    @Override
    protected void onPause() {


//        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
//
//
//        databaseReference.child(user.getUid()).child("status").setValue(false);



       // locationManager.removeUpdates();





        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Permission granted
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else // Permission denied
                {
                    Toast.makeText(this,"Permission denied!",Toast.LENGTH_SHORT).show();
                }
                return;
        }

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            try {
                Criteria criteria = new Criteria();

                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);
                lastLocation = locationManager.getLastKnownLocation(provider);
                Log.d("Provider", provider);

                if (lastLocation == null) {
                    lastLocation = new Location(LocationManager.PASSIVE_PROVIDER);
                }

                if (setCamera) {
                    MoveCameraToCurrentLocation();
                }

        }catch (Exception ex)
            {
                Log.d("hello",ex.toString());
            }
        }

        try {
            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if(!enabled) {
                        Toast.makeText(getBaseContext(),"Please enable GPS to use location services",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else{

                    }


                    return false;
                }

            });
        }
        catch(Exception ex)
        {
            Log.d("dada",ex.toString());
        }

        //}



//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void MoveCameraToCurrentLocation() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(zoom));

        if (locationManager != null && locationListener != null) {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


        }
        setCamera=false;
    }

    @Override
    protected void onResume() {
        // Make sure that GPS is enabled on the device
        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!enabled) {
            showDialogToEnableGPS();
        }

        super.onResume();
    }

    /**
     * Show a dialog to the user requesting that GPS be enabled
     */
    private void showDialogToEnableGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Need location services");
        builder.setMessage("Please enable GPS to use your location");
        //builder.(true);
        builder.setPositiveButton("ENABLE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("IGNORE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected synchronized void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }


    public void updateLatLng(double lat,double lng){


        databaseReference = FirebaseDatabase.getInstance().getReference();

         FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();


        databaseReference.child(user.getUid()).child("latitude").setValue(lat);
        databaseReference.child(user.getUid()).child("longitude").setValue(lng);

        //Query query = databaseReference.orderByChild("status").equalTo(true);


    }



//    public class locListener implements  LocationListener{
//
//        @Override
//        public void onLocationChanged(Location location) {
//            lastLocation = location;
//
//            if(currentLocationMarker != null)
//            {
//                currentLocationMarker.remove();
//            }
//
//            updateLatLng(location.getLatitude(),location.getLongitude());
//
//
//            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//
////        MarkerOptions markerOptions = new MarkerOptions();
////        markerOptions.position(latLng);
////        markerOptions.title("Current Location");
////        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//
//
//
//
//
//
//
//            //currentLocationMarker = mMap.addMarker(markerOptions);
//
////        Marker m1 = mMap.addMarker(new MarkerOptions()
////                .position(new LatLng(24.818679, 67.0294219))
////                .anchor(0.5f, 0.5f)
////                .title("SZABIST")
////                .snippet("Snippet1")
////                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomBy(zoom));
//
//            if(client != null)
//            {
//                LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
//            }
//
//        }
//
//
////        @Override
////        public void onProviderDisabled(String provider) {
////        }
////        @Override
////        public void onProviderEnabled(String provider) {
////        }
////        @Override
////        public void onStatusChanged(String provider, int status,
////                                    Bundle extras) {
////        }
//
//
//
//
//
//    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;
        }
        return true;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;

            if(currentLocationMarker != null)
            {
                currentLocationMarker.remove();
            }

            updateLatLng(location.getLatitude(),location.getLongitude());

        currentLocationMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title("Changed Current Location")
                .snippet("Changed")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        //Toast.makeText(getBaseContext(),"In Loc Listener 2",Toast.LENGTH_SHORT).show();



    }
}
