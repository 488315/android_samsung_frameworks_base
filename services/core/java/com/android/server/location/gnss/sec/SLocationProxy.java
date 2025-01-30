package com.android.server.location.gnss.sec;

import android.location.GnssStatus;
import android.location.Location;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.location.ISLocationManager;

/* loaded from: classes2.dex */
public class SLocationProxy {
    public ISLocationManager mSLocationService = null;

    public void enableSLocation() {
        this.mSLocationService = ISLocationManager.Stub.asInterface(ServiceManager.getService("sec_location"));
    }

    public void reportGeofenceTransition(int i, Location location, int i2, long j) {
        ISLocationManager iSLocationManager;
        if (location == null || (iSLocationManager = this.mSLocationService) == null || i <= 10000) {
            return;
        }
        try {
            iSLocationManager.reportGpsGeofenceTransition(i, new Location(location), i2, j);
        } catch (RemoteException e) {
            Log.e("SLocationProxy", e.toString());
        }
    }

    public void reportGeofenceStatus(int i, Location location) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.reportGpsGeofenceStatus(i, new Location(location));
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onReportGeofenceAddStatus(int i, int i2) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.reportGpsGeofenceAddStatus(i, i2);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onReportGeofenceRemoveStatus(int i, int i2) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.reportGpsGeofenceRemoveStatus(i, i2);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onReportGeofencePauseStatus(int i, int i2) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.reportGpsGeofencePauseStatus(i, i2);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onReportGeofenceResumeStatus(int i, int i2) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.reportGpsGeofenceResumeStatus(i, i2);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onStatusChanged(boolean z) {
        ISLocationManager iSLocationManager = this.mSLocationService;
        if (iSLocationManager != null) {
            try {
                iSLocationManager.onGnssStatusChanged(z);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public void onSvStatusChanged(GnssStatus gnssStatus) {
        if (this.mSLocationService != null) {
            int satelliteCount = gnssStatus.getSatelliteCount();
            int[] iArr = new int[satelliteCount];
            float[] fArr = new float[satelliteCount];
            float[] fArr2 = new float[satelliteCount];
            float[] fArr3 = new float[satelliteCount];
            float[] fArr4 = new float[satelliteCount];
            float[] fArr5 = new float[satelliteCount];
            for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
                iArr[i] = gnssStatus.getSvid(i);
                fArr[i] = gnssStatus.getCn0DbHz(i);
                fArr2[i] = gnssStatus.getElevationDegrees(i);
                fArr3[i] = gnssStatus.getAzimuthDegrees(i);
                fArr4[i] = gnssStatus.getCarrierFrequencyHz(i);
                fArr5[i] = gnssStatus.getBasebandCn0DbHz(i);
            }
            try {
                this.mSLocationService.onSvStatusChanged(satelliteCount, iArr, fArr, fArr2, fArr3, fArr4, fArr5);
            } catch (RemoteException e) {
                Log.e("SLocationProxy", e.toString());
            }
        }
    }

    public static boolean isSupportGnssBatching() {
        if ("true".equals(SystemProperties.get("ro.location.hwflp"))) {
            return true;
        }
        Log.d("SLocationProxy", "GNSS batching is disabled.");
        return false;
    }
}
