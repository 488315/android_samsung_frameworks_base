package com.android.server.location.gnss.sec;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.location.gnss.hal.GnssNative;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class LppeFusedLocationHelper implements GnssNative.LppeHelperCallbacks {
    public ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final GnssNative mGnssNative;
    public final GnssVendorConfig mGnssVendorConfig;
    public final Handler mHandler;
    public Sensor mPressureSensor;
    public SensorEventListener mSensorEventListener;
    public SensorManager mSensorManager;
    public WifiManager mWifiManager;
    public WifiScanner mWifiScanner;
    public boolean mIsWifiScanRequested = false;
    public Runnable mWlanTimeout = null;
    public boolean mIsRetryWifiScan = false;
    public boolean mIsUbpRequested = false;
    public Runnable mUbpTimeout = null;
    public boolean mIsFlpRequested = false;
    public Runnable mFlpTimeout = null;
    public boolean mIsCivicAddressRequested = false;
    public Runnable mCivicAddressTimeout = null;
    public LocationManager mLocationManager = null;
    public final LocationListener mLocationListener = new LocationListener() { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper.1
        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            if (LppeFusedLocationHelper.this.mIsFlpRequested && "fused".equals(location.getProvider())) {
                Log.d("LocationX", "LPPeFusedLocationListener : FUSED_PROVIDER");
                LppeFusedLocationHelper.this.handleUpdateLPPeFLPLocation(location);
                if (LppeFusedLocationHelper.this.mFlpTimeout != null) {
                    LppeFusedLocationHelper.this.mHandler.removeCallbacks(LppeFusedLocationHelper.this.mFlpTimeout);
                    LppeFusedLocationHelper.this.mFlpTimeout = null;
                }
            }
        }
    };

    public final long getResponseTime(int i, int i2) {
        return (i < i2 ? i : i2) * 1000;
    }

    public final void handleUpdateCommonIesCapability() {
        Log.d("LocationX", "handleUpdateCommonIesCapability : highAccCapa Supported ");
        this.mGnssNative.injectLppeComIeCapability(0, false, false, false, 128, false, false, false, false, false);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestLppeCommonIesCapability() {
        Log.d("LocationX", "requestLppeCommonIesCapability");
        handleUpdateCommonIesCapability();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestFlpLocation(int i) {
        if (this.mIsFlpRequested) {
            Log.d("LocationX", "already requested FlpLocation");
            return;
        }
        this.mIsFlpRequested = true;
        Log.d("LocationX", "requestFlpLocation response time = " + i + "sec");
        long responseTime = getResponseTime(i - 1, 20);
        LocationRequest.Builder locationSettingsIgnored = new LocationRequest.Builder(1000L).setMaxUpdates(1).setDurationMillis(responseTime).setQuality(102).setLocationSettingsIgnored(true);
        if (this.mLocationManager.getProvider("fused") != null) {
            Log.d("LocationX", "Start LocationManager.FUSED_PROVIDER");
            this.mLocationManager.requestLocationUpdates("fused", locationSettingsIgnored.build(), ConcurrentUtils.DIRECT_EXECUTOR, this.mLocationListener);
            Runnable runnable = new Runnable() { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    LppeFusedLocationHelper.this.lambda$onRequestFlpLocation$0();
                }
            };
            this.mFlpTimeout = runnable;
            if (this.mHandler.postDelayed(runnable, responseTime)) {
                return;
            }
            Log.w("LocationX", "failed to add FLP timeout to message queue.");
            return;
        }
        Log.w("LocationX", "Unable to request location.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestFlpLocation$0() {
        if (this.mIsFlpRequested) {
            Log.d("LocationX", "FLP measurement is timeout");
            handleUpdateFLPError(2);
            this.mIsFlpRequested = false;
        }
    }

    public final void handleUpdateLPPeFLPLocation(Location location) {
        Log.d("LocationX", "LPPe handleUpdateLPPeFLPLocation");
        int i = (location.hasAltitude() ? 2 : 0) | 1 | (location.hasSpeed() ? 4 : 0) | (location.hasBearing() ? 8 : 0) | (location.hasAccuracy() ? 16 : 0) | (location.hasVerticalAccuracy() ? 32 : 0) | (location.hasSpeedAccuracy() ? 64 : 0) | (location.hasBearingAccuracy() ? 128 : 0);
        Log.d("LocationX", " location total flag : " + i);
        Location location2 = new Location(location);
        if (!location2.hasVerticalAccuracy()) {
            if (location2.hasAltitude()) {
                location2.setVerticalAccuracyMeters(100.0f);
            } else {
                location2.setAltitude(1280000.0d);
                i |= 2;
                location2.setVerticalAccuracyMeters(255.0f);
            }
            i |= 32;
        }
        Log.d("LocationX", " Vertical Accuracy : " + location.getVerticalAccuracyMeters() + ", Horizontal Accuracy : " + location.getAccuracy());
        this.mGnssNative.injectFlpLocation(i, location2.getLatitude(), location2.getLongitude(), location2.getAltitude(), location2.getSpeed(), location2.getBearing(), location2.getAccuracy(), location2.getVerticalAccuracyMeters(), location2.getSpeedAccuracyMetersPerSecond(), location2.getBearingAccuracyDegrees(), location2.getTime());
        this.mIsFlpRequested = false;
    }

    public final void handleUpdateFLPError(int i) {
        Log.d("LocationX", "handleUpdateFLPError :  " + i);
        this.mGnssNative.injectFlpError(i);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestUbpCapability() {
        Log.d("LocationX", "requestUbpCapability");
        handleUpdateUBPCapability();
    }

    public final void handleUpdateUBPCapability() {
        Sensor defaultSensor = this.mSensorManager.getDefaultSensor(6);
        this.mPressureSensor = defaultSensor;
        boolean z = defaultSensor != null;
        Log.d("LocationX", "handleUpdateUBPCapability : isUbpSupported  " + z);
        this.mGnssNative.injectUbpCapability(false, false, false, z);
    }

    public class UBPSensorEventListener implements SensorEventListener {
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public UBPSensorEventListener() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (LppeFusedLocationHelper.this.mIsUbpRequested) {
                int i = (int) (sensorEvent.values[0] * 100.0f);
                Log.d("LocationX", "UBPSensorEventListener : UBP Pressure = " + i);
                Log.d("LocationX", "UBPSensorEventListener : onSensorChanged() ");
                LppeFusedLocationHelper.this.handleUpdateUBPInfo(i);
                if (LppeFusedLocationHelper.this.mUbpTimeout != null) {
                    LppeFusedLocationHelper.this.mHandler.removeCallbacks(LppeFusedLocationHelper.this.mUbpTimeout);
                    LppeFusedLocationHelper.this.mUbpTimeout = null;
                }
            }
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestUbpInfo(int i) {
        long responseTime;
        if (this.mIsUbpRequested) {
            Log.d("LocationX", "already requested UbpInfo");
            return;
        }
        this.mIsUbpRequested = true;
        this.mSensorEventListener = new UBPSensorEventListener();
        Log.d("LocationX", "requestUbpInfo response time = " + i + "sec");
        if (this.mPressureSensor == null) {
            Log.d("LocationX", "requestUBPInfo : Caution (PressureSensor is null) ");
            this.mPressureSensor = this.mSensorManager.getDefaultSensor(6);
        }
        this.mSensorManager.registerListener(this.mSensorEventListener, this.mPressureSensor, 2);
        if (this.mGnssVendorConfig.isMtkGnss()) {
            responseTime = getResponseTime(i, 15);
        } else {
            responseTime = getResponseTime(i - 1, 14);
        }
        Runnable runnable = new Runnable() { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LppeFusedLocationHelper.this.lambda$onRequestUbpInfo$1();
            }
        };
        this.mUbpTimeout = runnable;
        if (this.mHandler.postDelayed(runnable, responseTime)) {
            return;
        }
        Log.w("LocationX", "failed to add UBP timeout to message queue.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestUbpInfo$1() {
        if (this.mIsUbpRequested) {
            Log.d("LocationX", "UBP measurement is timeout");
            handleUpdateUBPError(4);
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mIsUbpRequested = false;
        }
    }

    public final void handleUpdateUBPInfo(int i) {
        Log.d("LocationX", "handleUpdateUBPInfo = sensorMeasurement : " + i + " (Valid range  30000 ~ 115000)");
        if (i >= 30000 && i <= 115000) {
            Log.d("LocationX", "handleUpdateUBPInfo = bitMask : 8");
            this.mGnssNative.injectUbpInfo(8, i);
        } else {
            this.mGnssNative.injectUbpError(4);
        }
        this.mIsUbpRequested = false;
        this.mSensorManager.unregisterListener(this.mSensorEventListener);
    }

    public final void handleUpdateUBPError(int i) {
        Log.d("LocationX", "handleUpdateUBPError :  " + i);
        this.mGnssNative.injectUbpError(i);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestWifiCapability() {
        Log.d("LocationX", "requestWlanCapability");
        handleUpdateWLANCapbility();
    }

    public final void handleUpdateWLANCapbility() {
        Log.d("LocationX", "handleUpdateWLANCapbility : ecidMeasSupported - 35872, wlanTypesSupported - 64512");
        this.mGnssNative.injectWlanCapability(35872, 64512, 0L, 0, 0, 0);
    }

    public static long convertStringToHexLong(String str) {
        try {
            return Long.parseLong(str.replace(XmlUtils.STRING_ARRAY_SEPARATOR, ""), 16);
        } catch (NumberFormatException unused) {
            Log.w("LocationX", "convertStringToHexLong : NumberFormatException");
            return -1L;
        }
    }

    public class LppeWlanScanListener implements WifiScanner.ScanListener {
        public void onFullResult(ScanResult scanResult) {
        }

        public void onPeriodChanged(int i) {
        }

        public void onSuccess() {
        }

        public LppeWlanScanListener() {
        }

        public void onResults(WifiScanner.ScanData[] scanDataArr) {
            if (LppeFusedLocationHelper.this.mIsWifiScanRequested) {
                LppeFusedLocationHelper.this.handleUpdateWLANScanInfo(new ArrayList(Arrays.asList(scanDataArr[0].getResults())));
                if (LppeFusedLocationHelper.this.mWlanTimeout != null) {
                    LppeFusedLocationHelper.this.mHandler.removeCallbacks(LppeFusedLocationHelper.this.mWlanTimeout);
                    LppeFusedLocationHelper.this.mWlanTimeout = null;
                }
            }
        }

        public void onFailure(int i, String str) {
            if (LppeFusedLocationHelper.this.mIsWifiScanRequested) {
                if (!LppeFusedLocationHelper.this.mIsRetryWifiScan) {
                    Log.d("LocationX", "wlan scan failure. reason = " + i + ", description = " + str + ". try to scan wlan again.");
                    LppeFusedLocationHelper.this.startWifiScan();
                    LppeFusedLocationHelper.this.mIsRetryWifiScan = true;
                    return;
                }
                Log.d("LocationX", "2nd wlan scan failure. reason = " + i + ", description = " + str + ". set wlan scan error.");
                LppeFusedLocationHelper.this.handleUpdateWlanError(3);
                LppeFusedLocationHelper.this.mIsWifiScanRequested = false;
            }
        }
    }

    public final void startWifiScan() {
        WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
        scanSettings.band = 15;
        scanSettings.type = 0;
        scanSettings.ignoreLocationSettings = true;
        this.mWifiScanner.startScan(scanSettings, new LppeWlanScanListener());
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestWifiScan(int i) {
        if (this.mIsWifiScanRequested) {
            Log.d("LocationX", "already requested WlanScanInfo.");
            return;
        }
        this.mIsRetryWifiScan = false;
        this.mIsWifiScanRequested = true;
        Log.d("LocationX", "requestWlanScanInfo. response time = " + i + "sec");
        startWifiScan();
        long responseTime = getResponseTime(i - 1, 11);
        Runnable runnable = new Runnable() { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LppeFusedLocationHelper.this.lambda$onRequestWifiScan$2();
            }
        };
        this.mWlanTimeout = runnable;
        if (this.mHandler.postDelayed(runnable, responseTime)) {
            return;
        }
        Log.w("LocationX", "failed to add WLAN timeout to message queue.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestWifiScan$2() {
        if (this.mIsWifiScanRequested) {
            Log.d("LocationX", "wlan measurement is timeout");
            handleUpdateWlanError(3);
            this.mIsWifiScanRequested = false;
        }
    }

    public final void handleUpdateWLANScanInfo(List list) {
        long j;
        WifiInfo connectionInfo;
        int i = 1;
        if (list.size() < 1) {
            Log.d("LocationX", "WIFI Scan size" + list.size() + "error cause3");
            handleUpdateWlanError(3);
        } else {
            int min = Math.min(list.size(), 64);
            long[] jArr = new long[64];
            int[] iArr = new int[64];
            int[] iArr2 = new int[64];
            Log.d("LocationX", "LPPeWiFiReceiver : the number of AP scaned : " + list.size() + " used number : " + min);
            NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
            if (networkInfo == null || networkInfo.getType() != 1 || !networkInfo.isConnected() || (connectionInfo = this.mWifiManager.getConnectionInfo()) == null) {
                j = 0;
                i = 0;
            } else {
                j = convertStringToHexLong(connectionInfo.getBSSID());
                jArr[0] = j;
                iArr2[0] = ScanResult.convertFrequencyMhzToChannelIfSupported(connectionInfo.getFrequency());
                iArr[0] = connectionInfo.getRssi();
            }
            for (int i2 = 0; i2 < min - i; i2++) {
                ScanResult scanResult = (ScanResult) list.get(i2);
                int i3 = i2 + i;
                long convertStringToHexLong = convertStringToHexLong(scanResult.BSSID);
                jArr[i3] = convertStringToHexLong;
                if (i == 0 || j != convertStringToHexLong) {
                    iArr[i3] = scanResult.level;
                    iArr2[i3] = ScanResult.convertFrequencyMhzToChannelIfSupported(scanResult.frequency);
                } else {
                    i = 0;
                }
            }
            this.mGnssNative.injectWlanScanInfo(jArr, iArr, iArr2, min);
        }
        this.mIsWifiScanRequested = false;
    }

    public final void handleUpdateWlanError(int i) {
        Log.d("LocationX", "handleUpdateWLANError :  " + i);
        this.mGnssNative.injectWlanError(i);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LppeHelperCallbacks
    public void onRequestCivicAddress(final double d, final double d2, final double d3) {
        Log.d("LocationX", "onRequestCivicAddress");
        this.mIsCivicAddressRequested = true;
        Runnable runnable = new Runnable() { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LppeFusedLocationHelper.this.lambda$onRequestCivicAddress$3(d, d2, d3);
            }
        };
        this.mCivicAddressTimeout = runnable;
        if (this.mHandler.postDelayed(runnable, 2000L)) {
            Log.w("LocationX", "failed to add CivicAddress timeout to message queue.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestCivicAddress$3(double d, double d2, double d3) {
        if (this.mIsCivicAddressRequested) {
            requestAddress(d, d2, d3);
            this.mIsCivicAddressRequested = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestAddress(double d, double d2, double d3) {
        List<Address> list;
        Log.d("LocationX", "requestCivicAddress");
        try {
            list = new Geocoder(this.mContext).getFromLocation(d, d2, 1);
        } catch (IOException e) {
            Log.e("LocationX", "Couldn't get Address from location.");
            e.printStackTrace();
            list = null;
            if (list == null) {
                return;
            } else {
                return;
            }
        } catch (IllegalArgumentException e2) {
            Log.e("LocationX", "Latitude/Longitude are invalid.");
            e2.printStackTrace();
            list = null;
            if (list == null) {
            }
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mHandler.removeCallbacks(this.mCivicAddressTimeout);
        Address address = list.get(0);
        this.mGnssNative.injectCivicAddress(address.getCountryCode() != null ? address.getCountryCode() : "", address.getAdminArea() != null ? address.getAdminArea() : "", address.getSubAdminArea() != null ? address.getSubAdminArea() : "", address.getLocality() != null ? address.getLocality() : "", address.getSubLocality() != null ? address.getSubLocality() : "", address.getThoroughfare() != null ? address.getThoroughfare() : "", address.getSubThoroughfare() != null ? address.getSubThoroughfare() : "", address.getPostalCode() != null ? address.getPostalCode() : "", address.getFeatureName() != null ? address.getFeatureName() : "", address.getLocale().toString() != null ? address.getLocale().toString() : "", address.getPremises() != null ? address.getPremises() : "", address.hasLatitude() ? String.valueOf(address.getLatitude()) : "", address.hasLongitude() ? String.valueOf(address.getLongitude()) : "");
    }

    public LppeFusedLocationHelper(Context context, GnssNative gnssNative, Looper looper) {
        Log.d("LppeFusedLocationHelper", "Constructor");
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mHandler = new Handler(looper);
        this.mGnssVendorConfig = GnssVendorConfig.getInstance();
        initializeLppeLocationHelper();
        gnssNative.setLppeHelperCallbacks(this);
    }

    public final void initializeLppeLocationHelper() {
        Log.d("LppeFusedLocationHelper", "initializeLppeLocationHelper");
        this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.mLocationManager = (LocationManager) this.mContext.getSystemService("location");
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mWifiScanner = (WifiScanner) this.mContext.getSystemService("wifiscanner");
    }
}
