package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;


public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    List<Sensor> deviceSensors;
    private TextView sensorAccelerometerEdit;
    private TextView sensorLightEdit;
    private TextView sensorGravityEdit;
    private TextView latitude;
    private TextView longitude;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        sensorAccelerometerEdit = findViewById(R.id.accelerometerData);
        sensorLightEdit = findViewById(R.id.lightData);
        sensorGravityEdit = findViewById(R.id.GravityData);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : deviceSensors)
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latitude = findViewById(R.id.LatitudeView);
        longitude =findViewById(R.id.LongitudeView);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

        }

        try {
           // location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);/**/
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    latitude.setText(String.valueOf(location.getLatitude()));
                    longitude.setText(String.valueOf(location.getLongitude()));

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });


        }catch (Exception e){
            Log.e("Sensors", e.toString());

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            sensorAccelerometerEdit.setText("X="+sensorEvent.values[0] + " Y="+sensorEvent.values[1]  + " Z="+sensorEvent.values[2]);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            sensorLightEdit.setText(String.valueOf(sensorEvent.values[0]));
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY)
            sensorGravityEdit.setText("X="+sensorEvent.values[0] + " Y="+sensorEvent.values[1]  + " Z="+sensorEvent.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
