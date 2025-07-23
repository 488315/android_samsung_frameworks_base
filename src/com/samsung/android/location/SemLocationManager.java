package com.samsung.android.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.location.ISLocationListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class SemLocationManager {
    public static final String ACTION_SERVICE_READY = "com.samsung.android.location.SERVICE_READY";
    public static final String BATCHED_LOCATION = "batchedlocation";
    public static final int CURRENT_ADDRESS_FROM_LOCATION = 10;
    public static final String CURRENT_LOCATION = "currentlocation";
    public static final String CURRENT_LOCATION_ADDRESS = "currentlocationaddress";
    public static final int CURRENT_LOCATION_MOST_ACCURATE = 9;
    public static final int CURRENT_LOCATION_SINGLE = 7;
    public static final int CURRENT_LOCATION_TRACKING = 8;
    public static final int ERROR_ALREADY_STARTED = -5;
    public static final int ERROR_EXCEPTION = -4;
    public static final int ERROR_ID_NOT_EXIST = -3;
    public static final int ERROR_ILLEGAL_ARGUMENT = -2;
    public static final int ERROR_LOCATION_CURRENTLY_UNAVAILABLE = -100;
    public static final int ERROR_NOT_INITIALIZED = -1;
    public static final int ERROR_NOT_SUPPORTED = -7;
    public static final int ERROR_TOO_MANY_GEOFENCE = -6;
    public static final String FLUSH_COMPLETED = "flushcompleted";
    public static final String GEOFENCE_BLUETOOTH_ADDRESS = "geofencebluetoothaddress";
    public static final int GEOFENCE_ENTER = 1;
    public static final int GEOFENCE_EXIT = 2;
    public static final String GEOFENCE_LOCATION = "location";
    public static final String GEOFENCE_REQUEST_ID = "requestid";
    public static final String GEOFENCE_TRANSITION = "transition";
    public static final int GEOFENCE_TYPE_BLE_SCAN = 5;
    public static final int GEOFENCE_TYPE_BT = 3;
    public static final int GEOFENCE_TYPE_EVENT = 4;
    public static final int GEOFENCE_TYPE_GEOPOINT = 1;
    public static final int GEOFENCE_TYPE_WIFI = 2;
    public static final int GEOFENCE_UNKNOWN = 0;
    public static final int LOCATION_BATCHING = 11;
    public static final int OPERATION_SUCCESS = 0;
    public static final String PERMISSION_ALWAYS_SCAN = "permissionalwaysscan";
    private static final String TAG = "SemLocationManager";
    private final Context mContext;
    private HashMap<SemLocationListener, LocListenerTransport> mLocListeners = new HashMap<>();
    private final ISLocationManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemLocationManagerModule {
    }

    private class LocListenerTransport extends ISLocationListener.Stub {
        public static final int TYPE_LOCATION_CHANGED_ADDRESS = 2;
        private SemLocationListener mListener;
        private final Handler mListenerHandler;

        LocListenerTransport(SemLocationListener semLocationListener) {
            this.mListener = semLocationListener;
            this.mListenerHandler = new Handler() { // from class: com.samsung.android.location.SemLocationManager.LocListenerTransport.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    LocListenerTransport.this._handleMessage(message);
                }
            };
        }

        @Override // com.samsung.android.location.ISLocationListener
        public void onLocationChanged(Location location) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = location;
            sendCallbackMessage(obtain);
        }

        private void sendCallbackMessage(Message message) {
            if (this.mListenerHandler.sendMessage(message)) {
                return;
            }
            try {
                SemLocationManager.this.removeLocationUpdates(this.mListener);
            } catch (Exception e) {
                Log.e(SemLocationManager.TAG, "sendCallbackMessage removeLocationUpdates occur exception " + e.toString());
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void _handleMessage(Message message) {
            if (message.what != 2) {
                return;
            }
            this.mListener.onLocationChanged((Location) message.obj);
        }
    }

    public SemLocationManager(Context context, ISLocationManager iSLocationManager) {
        this.mService = iSLocationManager;
        this.mContext = context;
    }

    public boolean isAvailable(int i) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return false;
        }
        try {
            return iSLocationManager.isAvailable(i, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "isAvailable : Throwable " + th.toString());
            return false;
        }
    }

    public int removeGeofence(PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        try {
            return iSLocationManager.removeGeofencesPendingIntent(pendingIntent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "removeGeofence: Throwable " + th.toString());
            return -4;
        }
    }

    public int requestSingleLocation(int i, int i2, boolean z, PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return iSLocationManager.requestSingleLocation(i, i2, z, pendingIntent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "requestSingleLocation: Throwable " + th.toString());
            return -4;
        }
    }

    public int requestSingleLocation(int i, int i2, boolean z, SemLocationListener semLocationListener) {
        int requestSingleLocation;
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationListener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            synchronized (this.mLocListeners) {
                LocListenerTransport locListenerTransport = this.mLocListeners.get(semLocationListener);
                if (locListenerTransport == null) {
                    locListenerTransport = new LocListenerTransport(semLocationListener);
                }
                LocListenerTransport locListenerTransport2 = locListenerTransport;
                this.mLocListeners.put(semLocationListener, locListenerTransport2);
                requestSingleLocation = this.mService.requestSingleLocation(i, i2, z, null, locListenerTransport2, this.mContext.getPackageName(), this.mContext.getAttributionTag());
            }
            return requestSingleLocation;
        } catch (Throwable th) {
            Log.e(TAG, "requestSingleLocation: Throwable " + th.toString());
            return -4;
        }
    }

    public int removeSingleLocation(SemLocationListener semLocationListener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationListener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            LocListenerTransport remove = this.mLocListeners.remove(semLocationListener);
            if (remove == null) {
                Log.e(TAG, "Already stopped location");
                return -3;
            }
            return this.mService.removeSingleLocation(null, remove, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "removeSingleLocation: Throwable " + th.toString());
            return -4;
        }
    }

    public int requestLocationUpdates(boolean z, SemLocationListener semLocationListener) {
        int requestLocation;
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationListener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            synchronized (this.mLocListeners) {
                LocListenerTransport locListenerTransport = this.mLocListeners.get(semLocationListener);
                if (locListenerTransport == null) {
                    locListenerTransport = new LocListenerTransport(semLocationListener);
                }
                this.mLocListeners.put(semLocationListener, locListenerTransport);
                requestLocation = this.mService.requestLocation(z, locListenerTransport, this.mContext.getPackageName(), this.mContext.getAttributionTag());
            }
            return requestLocation;
        } catch (Throwable th) {
            Log.e(TAG, "requestLocationUpdates: Throwable " + th.toString());
            return -4;
        }
    }

    public int removeLocationUpdates(SemLocationListener semLocationListener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationListener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            LocListenerTransport remove = this.mLocListeners.remove(semLocationListener);
            if (remove == null) {
                Log.e(TAG, "Already stopped location");
                return -3;
            }
            return this.mService.removeLocation(remove, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "removeLocationUpdates: Throwable " + th.toString());
            return -4;
        }
    }

    public void requestPassiveLocation(PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "parameters are not vaild");
            return;
        }
        try {
            iSLocationManager.requestPassiveLocation(pendingIntent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "requestLocationToPoi: Throwable " + th.toString());
        }
    }

    public void removePassiveLocation(PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "parameters are not vaild");
            return;
        }
        try {
            iSLocationManager.removePassiveLocation(pendingIntent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "requestLocationToPoi: Throwable " + th.toString());
        }
    }

    public int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null || semLocationBatchingRequest == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return iSLocationManager.requestBatchedLocations(semLocationBatchingRequest, pendingIntent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "requestLocationBatchingUpdates: Throwable " + th.toString());
            return -4;
        }
    }

    public int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, SemLocationBatchingListener semLocationBatchingListener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationBatchingListener != null && semLocationBatchingRequest != null) {
            return -7;
        }
        Log.e(TAG, "parameters are not vaild");
        return -2;
    }

    public int removeBatchedLocations(PendingIntent pendingIntent) {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return iSLocationManager.removeBatchedLocations(pendingIntent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "requestLocationBatchingUpdates: Throwable " + th.toString());
            return -4;
        }
    }

    public int removeBatchedLocations(SemLocationBatchingListener semLocationBatchingListener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (semLocationBatchingListener != null) {
            return -7;
        }
        Log.e(TAG, "parameters are not vaild");
        return -2;
    }

    public void flushBatchedLocations() {
        ISLocationManager iSLocationManager = this.mService;
        if (iSLocationManager == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        try {
            iSLocationManager.flushBatchedLocations(this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "flushLocations: Throwable " + th.toString());
        }
    }

    public int addGeofence(SemGeopointGeofence semGeopointGeofence, PendingIntent pendingIntent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (pendingIntent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(semGeopointGeofence.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence semGeofence = new SemGeofence(1, semGeopointGeofence.getLatitude(), semGeopointGeofence.getLongitude(), semGeopointGeofence.getRadius(), semGeopointGeofence.getWifiBssidList());
            semGeofence.setRequestId(semGeopointGeofence.getRequestId());
            ArrayList arrayList = new ArrayList();
            arrayList.add(semGeofence);
            return this.mService.addGeofences(arrayList, pendingIntent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "addGeofence : Throwable " + th.toString());
            return -4;
        }
    }

    public int addGeofence(SemBluetoothGeofence semBluetoothGeofence, PendingIntent pendingIntent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (pendingIntent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(semBluetoothGeofence.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence semGeofence = new SemGeofence(3, semBluetoothGeofence.getBssid());
            semGeofence.setRequestId(semBluetoothGeofence.getRequestId());
            ArrayList arrayList = new ArrayList();
            arrayList.add(semGeofence);
            return this.mService.addGeofences(arrayList, pendingIntent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "addGeofence : Throwable " + th.toString());
            return -4;
        }
    }

    public int addGeofence(SemWifiGeofence semWifiGeofence, PendingIntent pendingIntent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (pendingIntent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(semWifiGeofence.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence semGeofence = new SemGeofence(2, semWifiGeofence.getBssid());
            semGeofence.setRequestId(semWifiGeofence.getRequestId());
            ArrayList arrayList = new ArrayList();
            arrayList.add(semGeofence);
            return this.mService.addGeofences(arrayList, pendingIntent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "addGeofence : Throwable " + th.toString());
            return -4;
        }
    }

    public int addGeofence(SemBleScanGeofence semBleScanGeofence, PendingIntent pendingIntent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (pendingIntent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (pendingIntent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(semBleScanGeofence.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence semGeofence = new SemGeofence(5, semBleScanGeofence.getAddress(), semBleScanGeofence.getLatitude(), semBleScanGeofence.getLongitude());
            semGeofence.setRequestId(semBleScanGeofence.getRequestId());
            ArrayList arrayList = new ArrayList();
            arrayList.add(semGeofence);
            return this.mService.addGeofences(arrayList, pendingIntent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "addGeofence : Throwable " + th.toString());
            return -4;
        }
    }

    public int removeGeofence(String str) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            return this.mService.removeGeofences(arrayList, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable th) {
            Log.e(TAG, "removeGeofence: Throwable " + th.toString());
            return -4;
        }
    }
}
