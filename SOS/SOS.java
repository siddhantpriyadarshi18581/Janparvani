package org.bnmit.www.janaparvani;

import static androidx.core.location.LocationManagerCompat.requestLocationUpdates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SOS extends AppCompatActivity {
    public static final int REQUEST_CHECK_SETTING = 1001;
    SeekBar seekBar;
    Spinner spinner;
    Button button;
    String[] spin = {"Select Problems", "Robbery", "Fight Break", "Assault", "Chain Snatching", "Fire", "Sexual Harassment", "Bachha Chori", "Road Accident", "Murder", "Domestic Violence", "House Break/Burglary", "Others"};
    String str_prob;
    String severity;
    double lat, longi;
    String lati, longit;
    private LocationRequest locationRequest;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        editText = findViewById(R.id.editText);
        seekBar = findViewById(R.id.seekBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        seekBar.setMax(100);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SOS.this, android.R.layout.simple_dropdown_item_1line, spin);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), "Item " + item + " is Selected", Toast.LENGTH_SHORT).show();
                str_prob = item;
                switch (str_prob) {
                    case "Robbery":
                        seekBar.setProgress(0);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(MainActivity.this, "Problem Severity is 0%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Fight Break":
                        seekBar.setProgress(10);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 25%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Assault":
                        seekBar.setProgress(20);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 50%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Chain Snatching":
                        seekBar.setProgress(30);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 75%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Fire":
                        seekBar.setProgress(40);
                        severity = Integer.toString(seekBar.getProgress());
                        // Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Sexual Harassment":
                        seekBar.setProgress(50);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Bachha Chori":
                        seekBar.setProgress(60);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Road Accident":
                        seekBar.setProgress(70);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Murder":
                        seekBar.setProgress(80);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Domestic Violence":
                        seekBar.setProgress(90);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "House Break/Burglary":
                        seekBar.setProgress(100);
                        severity = Integer.toString(seekBar.getProgress());
                        //Toast.makeText(SOS.this, "Problem Severity is 100%", Toast.LENGTH_SHORT).show();
                        break;
                    case "Others":
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                                seekBar.setProgress(0);
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                severity = Integer.toString(seekBar.getProgress());
                                str_prob = editText.getText().toString();
                            }
                        });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(SOS.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (isGPSEnabled()) {
                            LocationServices.getFusedLocationProviderClient(SOS.this)
                                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                                        @Override
                                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                            super.onLocationResult(locationResult);

                                            LocationServices.getFusedLocationProviderClient(SOS.this)
                                                    .removeLocationUpdates(this);

                                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                                int index = locationResult.getLocations().size() - 1;
                                                lat = locationResult.getLocations().get(index).getLatitude();
                                                longi = locationResult.getLocations().get(index).getLongitude();
                                                lati = Double.toString(lat);
                                                longit = Double.toString(longi);
                                                sendToDB();
                                            }
                                        }
                                    }, Looper.getMainLooper());
                        } else {
                            turnOnGPS();
                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                    }

                }

            }
        });
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(SOS.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(SOS.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {
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
                LocationServices.getFusedLocationProviderClient(SOS.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(SOS.this)
                                        .removeLocationUpdates(this);

                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    int index = locationResult.getLocations().size() - 1;
                                    lat = locationResult.getLocations().get(index).getLatitude();
                                    longi = locationResult.getLocations().get(index).getLongitude();
                                    lati = Double.toString(lat);
                                    longit = Double.toString(longi);
                                }
                            }
                        }, Looper.getMainLooper());
            }
        }
    }



    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnable = false;
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnable;
    }

    public void sendToDB() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Problems", str_prob);
        data.put("Severity", severity);
        data.put("Latitude", lati);
        data.put("Longitude", longit);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Problems");
        databaseReference.child(str_prob).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SOS.this, "Help would be reached out", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
