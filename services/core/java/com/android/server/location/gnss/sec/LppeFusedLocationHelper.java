package com.android.server.location.gnss.sec;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LppeFusedLocationHelper implements GnssNative.LppeHelperCallbacks {
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final GnssNative mGnssNative;
    public final GnssVendorConfig mGnssVendorConfig;
    public final Handler mHandler;
    public final LocationManager mLocationManager;
    public Sensor mPressureSensor;
    public UBPSensorEventListener mSensorEventListener;
    public final SensorManager mSensorManager;
    public final WifiManager mWifiManager;
    public final WifiScanner mWifiScanner;
    public boolean mIsWifiScanRequested = false;
    public Runnable mWlanTimeout = null;
    public boolean mIsRetryWifiScan = false;
    public boolean mIsUbpRequested = false;
    public Runnable mUbpTimeout = null;
    public boolean mIsFlpRequested = false;
    public Runnable mFlpTimeout = null;
    public boolean mIsCivicAddressRequested = false;
    public LppeFusedLocationHelper$$ExternalSyntheticLambda3 mCivicAddressTimeout = null;
    public final AnonymousClass1 mLocationListener =
            new LocationListener() { // from class:
                                     // com.android.server.location.gnss.sec.LppeFusedLocationHelper.1
                @Override // android.location.LocationListener
                public final void onLocationChanged(Location location) {
                    if (LppeFusedLocationHelper.this.mIsFlpRequested
                            && "fused".equals(location.getProvider())) {
                        Log.d("LocationX", "LPPeFusedLocationListener : FUSED_PROVIDER");
                        LppeFusedLocationHelper lppeFusedLocationHelper =
                                LppeFusedLocationHelper.this;
                        lppeFusedLocationHelper.getClass();
                        Log.d("LocationX", "LPPe handleUpdateLPPeFLPLocation");
                        int i =
                                (location.hasAltitude() ? 2 : 0)
                                        | 1
                                        | (location.hasSpeed() ? 4 : 0)
                                        | (location.hasBearing() ? 8 : 0)
                                        | (location.hasAccuracy() ? 16 : 0)
                                        | (location.hasVerticalAccuracy() ? 32 : 0)
                                        | (location.hasSpeedAccuracy() ? 64 : 0)
                                        | (location.hasBearingAccuracy() ? 128 : 0);
                        Log.d("LocationX", " location total flag : " + i);
                        if (!location.hasVerticalAccuracy()) {
                            if (location.hasAltitude()) {
                                location.setVerticalAccuracyMeters(100.0f);
                            } else {
                                location.setAltitude(1280000.0d);
                                i |= 2;
                                location.setVerticalAccuracyMeters(255.0f);
                            }
                            i |= 32;
                        }
                        Log.d(
                                "LocationX",
                                " Vertical Accuracy : "
                                        + location.getVerticalAccuracyMeters()
                                        + ", Horizontal Accuracy : "
                                        + location.getAccuracy());
                        lppeFusedLocationHelper.mGnssNative.injectFlpLocation(
                                i,
                                location.getLatitude(),
                                location.getLongitude(),
                                location.getAltitude(),
                                location.getSpeed(),
                                location.getBearing(),
                                location.getAccuracy(),
                                location.getVerticalAccuracyMeters(),
                                location.getSpeedAccuracyMetersPerSecond(),
                                location.getBearingAccuracyDegrees(),
                                location.getTime());
                        lppeFusedLocationHelper.mIsFlpRequested = false;
                        LppeFusedLocationHelper lppeFusedLocationHelper2 =
                                LppeFusedLocationHelper.this;
                        Runnable runnable = lppeFusedLocationHelper2.mFlpTimeout;
                        if (runnable != null) {
                            lppeFusedLocationHelper2.mHandler.removeCallbacks(runnable);
                            LppeFusedLocationHelper.this.mFlpTimeout = null;
                        }
                    }
                }

                @Override // android.location.LocationListener
                public final void onProviderDisabled(String str) {}

                @Override // android.location.LocationListener
                public final void onProviderEnabled(String str) {}
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LppeWlanScanListener implements WifiScanner.ScanListener {
        public LppeWlanScanListener() {}

        public final void onFailure(int i, String str) {
            LppeFusedLocationHelper lppeFusedLocationHelper = LppeFusedLocationHelper.this;
            if (lppeFusedLocationHelper.mIsWifiScanRequested) {
                if (lppeFusedLocationHelper.mIsRetryWifiScan) {
                    Log.d(
                            "LocationX",
                            AccountManagerService$$ExternalSyntheticOutline0.m(
                                    i,
                                    "2nd wlan scan failure. reason = ",
                                    ", description = ",
                                    str,
                                    ". set wlan scan error."));
                    LppeFusedLocationHelper.this.handleUpdateWlanError();
                    LppeFusedLocationHelper.this.mIsWifiScanRequested = false;
                    return;
                }
                Log.d(
                        "LocationX",
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                i,
                                "wlan scan failure. reason = ",
                                ", description = ",
                                str,
                                ". try to scan wlan again."));
                LppeFusedLocationHelper lppeFusedLocationHelper2 = LppeFusedLocationHelper.this;
                lppeFusedLocationHelper2.getClass();
                WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
                scanSettings.band = 15;
                scanSettings.type = 0;
                scanSettings.ignoreLocationSettings = true;
                lppeFusedLocationHelper2.mWifiScanner.startScan(
                        scanSettings, lppeFusedLocationHelper2.new LppeWlanScanListener());
                LppeFusedLocationHelper.this.mIsRetryWifiScan = true;
            }
        }

        public final void onFullResult(ScanResult scanResult) {}

        public final void onPeriodChanged(int i) {}

        public final void onResults(WifiScanner.ScanData[] scanDataArr) {
            long j;
            WifiInfo connectionInfo;
            if (LppeFusedLocationHelper.this.mIsWifiScanRequested) {
                ArrayList arrayList = new ArrayList(Arrays.asList(scanDataArr[0].getResults()));
                LppeFusedLocationHelper lppeFusedLocationHelper = LppeFusedLocationHelper.this;
                lppeFusedLocationHelper.mIsWifiScanRequested = false;
                if (arrayList.size() < 1) {
                    Log.d("LocationX", "WIFI Scan size" + arrayList.size() + "error cause3");
                    lppeFusedLocationHelper.handleUpdateWlanError();
                } else {
                    int min = Math.min(arrayList.size(), 64);
                    long[] jArr = new long[min];
                    int[] iArr = new int[min];
                    int[] iArr2 = new int[min];
                    Log.d(
                            "LocationX",
                            "LPPeWiFiReceiver : the number of AP scanned : "
                                    + arrayList.size()
                                    + " used number : "
                                    + min);
                    NetworkInfo networkInfo =
                            lppeFusedLocationHelper.mConnectivityManager.getNetworkInfo(1);
                    if (networkInfo == null
                            || networkInfo.getType() != 1
                            || !networkInfo.isConnected()
                            || (connectionInfo =
                                            lppeFusedLocationHelper.mWifiManager
                                                    .getConnectionInfo())
                                    == null) {
                        j = 0;
                    } else {
                        j =
                                LppeFusedLocationHelper.convertStringToHexLong(
                                        connectionInfo.getBSSID());
                        jArr[0] = j;
                        iArr[0] = connectionInfo.getRssi();
                        iArr2[0] =
                                ScanResult.convertFrequencyMhzToChannelIfSupported(
                                        connectionInfo.getFrequency());
                    }
                    int i = j == 0 ? 0 : 1;
                    for (int i2 = 0; i2 < min - i; i2++) {
                        ScanResult scanResult = (ScanResult) arrayList.get(i2);
                        int i3 = i2 + i;
                        jArr[i3] = LppeFusedLocationHelper.convertStringToHexLong(scanResult.BSSID);
                        if (i == 0
                                || j
                                        != LppeFusedLocationHelper.convertStringToHexLong(
                                                scanResult.BSSID)) {
                            iArr[i3] = scanResult.level;
                            iArr2[i3] =
                                    ScanResult.convertFrequencyMhzToChannelIfSupported(
                                            scanResult.frequency);
                        } else {
                            i = 0;
                        }
                    }
                    lppeFusedLocationHelper.mGnssNative.injectWlanScanInfo(jArr, iArr, iArr2, min);
                }
                LppeFusedLocationHelper lppeFusedLocationHelper2 = LppeFusedLocationHelper.this;
                Runnable runnable = lppeFusedLocationHelper2.mWlanTimeout;
                if (runnable != null) {
                    lppeFusedLocationHelper2.mHandler.removeCallbacks(runnable);
                    LppeFusedLocationHelper.this.mWlanTimeout = null;
                }
            }
        }

        public final void onSuccess() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UBPSensorEventListener implements SensorEventListener {
        public UBPSensorEventListener() {}

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {}

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            if (LppeFusedLocationHelper.this.mIsUbpRequested) {
                int i = (int) (sensorEvent.values[0] * 100.0f);
                Log.d("LocationX", "UBPSensorEventListener : UBP Pressure = " + i);
                Log.d("LocationX", "UBPSensorEventListener : onSensorChanged() ");
                LppeFusedLocationHelper lppeFusedLocationHelper = LppeFusedLocationHelper.this;
                lppeFusedLocationHelper.getClass();
                Log.d(
                        "LocationX",
                        "handleUpdateUBPInfo = sensorMeasurement : "
                                + i
                                + " (Valid range  30000 ~ 115000)");
                GnssNative gnssNative = lppeFusedLocationHelper.mGnssNative;
                if (i < 30000 || i > 115000) {
                    gnssNative.injectUbpError(4);
                } else {
                    Log.d("LocationX", "handleUpdateUBPInfo = bitMask : 8");
                    gnssNative.injectUbpInfo(8, i);
                }
                lppeFusedLocationHelper.mIsUbpRequested = false;
                lppeFusedLocationHelper.mSensorManager.unregisterListener(
                        lppeFusedLocationHelper.mSensorEventListener);
                LppeFusedLocationHelper lppeFusedLocationHelper2 = LppeFusedLocationHelper.this;
                Runnable runnable = lppeFusedLocationHelper2.mUbpTimeout;
                if (runnable != null) {
                    lppeFusedLocationHelper2.mHandler.removeCallbacks(runnable);
                    LppeFusedLocationHelper.this.mUbpTimeout = null;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.location.gnss.sec.LppeFusedLocationHelper$1] */
    public LppeFusedLocationHelper(Context context, GnssNative gnssNative, Looper looper) {
        this.mLocationManager = null;
        Log.d("LppeFusedLocationHelper", "Constructor");
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mHandler = new Handler(looper);
        this.mGnssVendorConfig = GnssVendorConfig.getInstance();
        Log.d("LppeFusedLocationHelper", "initializeLppeLocationHelper");
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        this.mLocationManager = (LocationManager) context.getSystemService("location");
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mWifiScanner = (WifiScanner) context.getSystemService("wifiscanner");
        gnssNative.setLppeHelperCallbacks(this);
    }

    public static long convertStringToHexLong(String str) {
        try {
            return Long.parseLong(str.replace(":", ""), 16);
        } catch (NumberFormatException unused) {
            Log.w("LocationX", "convertStringToHexLong : NumberFormatException");
            return -1L;
        }
    }

    public static long getResponseTime(int i, int i2) {
        return (i < i2 ? i : i2) * 1000;
    }

    public final void handleUpdateWlanError() {
        Log.d("LocationX", "handleUpdateWLANError :  3");
        this.mGnssNative.injectWlanError(3);
    }
}
