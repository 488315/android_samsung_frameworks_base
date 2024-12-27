package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;

public final class SensorNotificationService extends SystemService
        implements SensorEventListener, LocationListener {
    public final Context mContext;
    public long mLocalGeomagneticFieldUpdateTime;
    public Sensor mMetaSensor;
    public SensorManager mSensorManager;

    public SensorNotificationService(Context context) {
        super(context.createAttributionContext("SensorNotificationService"));
        this.mLocalGeomagneticFieldUpdateTime = -1800000L;
        this.mContext = getContext();
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        LocationManager locationManager;
        if (i == 600) {
            SensorManager sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
            this.mSensorManager = sensorManager;
            Sensor defaultSensor = sensorManager.getDefaultSensor(32);
            this.mMetaSensor = defaultSensor;
            if (defaultSensor != null) {
                this.mSensorManager.registerListener(this, defaultSensor, 0);
            }
        }
        if (i != 1000
                || (locationManager = (LocationManager) this.mContext.getSystemService("location"))
                        == null) {
            return;
        }
        locationManager.requestLocationUpdates("passive", 1800000L, 100000.0f, this);
    }

    @Override // android.location.LocationListener
    public final void onLocationChanged(Location location) {
        if (!(location.getLatitude() == 0.0d && location.getLongitude() == 0.0d)
                && SystemClock.elapsedRealtime() - this.mLocalGeomagneticFieldUpdateTime
                        >= 600000) {
            long currentTimeMillis = System.currentTimeMillis();
            if ("false".equals(System.getProperty("sensor.notification.use_mocked", "false"))
                            == location.isMock()
                    || currentTimeMillis < 1262358000000L) {
                return;
            }
            try {
                SensorAdditionalInfo createLocalGeomagneticField =
                        SensorAdditionalInfo.createLocalGeomagneticField(
                                new GeomagneticField(
                                                        (float) location.getLatitude(),
                                                        (float) location.getLongitude(),
                                                        (float) location.getAltitude(),
                                                        currentTimeMillis)
                                                .getFieldStrength()
                                        / 1000.0f,
                                (float) ((r0.getDeclination() * 3.141592653589793d) / 180.0d),
                                (float) ((r0.getInclination() * 3.141592653589793d) / 180.0d));
                if (createLocalGeomagneticField != null) {
                    this.mSensorManager.setOperationParameter(createLocalGeomagneticField);
                    this.mLocalGeomagneticFieldUpdateTime = SystemClock.elapsedRealtime();
                }
            } catch (IllegalArgumentException unused) {
                Slog.e("SensorNotificationService", "Invalid local geomagnetic field, ignore.");
            }
        }
    }

    @Override // android.location.LocationListener
    public final void onProviderDisabled(String str) {}

    @Override // android.location.LocationListener
    public final void onProviderEnabled(String str) {}

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == this.mMetaSensor) {
            Intent intent = new Intent("android.intent.action.DYNAMIC_SENSOR_CHANGED");
            intent.setFlags(1073741824);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        LocalServices.addService(SensorNotificationService.class, this);
    }

    @Override // android.location.LocationListener
    public final void onStatusChanged(String str, int i, Bundle bundle) {}
}
