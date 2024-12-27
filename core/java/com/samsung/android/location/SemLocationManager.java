package com.samsung.android.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.location.ISLocationListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        LocListenerTransport(SemLocationListener listener) {
            this.mListener = listener;
            this.mListenerHandler = new Handler() { // from class: com.samsung.android.location.SemLocationManager.LocListenerTransport.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    LocListenerTransport.this._handleMessage(msg);
                }
            };
        }

        @Override // com.samsung.android.location.ISLocationListener
        public void onLocationChanged(Location location) {
            Message msg = Message.obtain();
            msg.what = 2;
            msg.obj = location;
            sendCallbackMessage(msg);
        }

        private void sendCallbackMessage(Message msg) {
            if (!this.mListenerHandler.sendMessage(msg)) {
                try {
                    SemLocationManager.this.removeLocationUpdates(this.mListener);
                } catch (Exception e) {
                    Log.e(SemLocationManager.TAG, "sendCallbackMessage removeLocationUpdates occur exception " + e.toString());
                    e.printStackTrace();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void _handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    this.mListener.onLocationChanged((Location) msg.obj);
                    break;
            }
        }
    }

    public SemLocationManager(Context context, ISLocationManager service) {
        this.mService = service;
        this.mContext = context;
    }

    public boolean isAvailable(int module) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return false;
        }
        try {
            return this.mService.isAvailable(module, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "isAvailable : RemoteException " + ex.toString());
            return false;
        }
    }

    public int removeGeofence(PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        try {
            return this.mService.removeGeofencesPendingIntent(intent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "removeGeofence: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int requestSingleLocation(int accuracy, int timeout, boolean isAddress, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return this.mService.requestSingleLocation(accuracy, timeout, isAddress, intent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "requestSingleLocation: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int requestSingleLocation(int accuracy, int timeout, boolean isAddress, SemLocationListener listener) {
        int requestSingleLocation;
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            synchronized (this.mLocListeners) {
                LocListenerTransport transport = this.mLocListeners.get(listener);
                if (transport == null) {
                    transport = new LocListenerTransport(listener);
                }
                this.mLocListeners.put(listener, transport);
                requestSingleLocation = this.mService.requestSingleLocation(accuracy, timeout, isAddress, null, transport, this.mContext.getPackageName(), this.mContext.getAttributionTag());
            }
            return requestSingleLocation;
        } catch (RemoteException ex) {
            Log.e(TAG, "requestSingleLocation: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int removeSingleLocation(SemLocationListener listener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            LocListenerTransport transport = this.mLocListeners.remove(listener);
            if (transport == null) {
                Log.e(TAG, "Already stopped location");
                return -3;
            }
            return this.mService.removeSingleLocation(null, transport, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "removeSingleLocation: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int requestLocationUpdates(boolean isAddress, SemLocationListener listener) {
        int requestLocation;
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            synchronized (this.mLocListeners) {
                LocListenerTransport transport = this.mLocListeners.get(listener);
                if (transport == null) {
                    transport = new LocListenerTransport(listener);
                }
                this.mLocListeners.put(listener, transport);
                requestLocation = this.mService.requestLocation(isAddress, transport, this.mContext.getPackageName(), this.mContext.getAttributionTag());
            }
            return requestLocation;
        } catch (RemoteException ex) {
            Log.e(TAG, "requestLocationUpdates: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int removeLocationUpdates(SemLocationListener listener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            LocListenerTransport transport = this.mLocListeners.remove(listener);
            if (transport == null) {
                Log.e(TAG, "Already stopped location");
                return -3;
            }
            return this.mService.removeLocation(transport, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "removeLocationUpdates: RemoteException " + ex.toString());
            return -4;
        }
    }

    public void requestPassiveLocation(PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        if (intent == null) {
            Log.e(TAG, "parameters are not vaild");
            return;
        }
        try {
            this.mService.requestPassiveLocation(intent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable ex) {
            Log.e(TAG, "requestLocationToPoi: RemoteException " + ex.toString());
        }
    }

    public void removePassiveLocation(PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        if (intent == null) {
            Log.e(TAG, "parameters are not vaild");
            return;
        }
        try {
            this.mService.removePassiveLocation(intent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable ex) {
            Log.e(TAG, "requestLocationToPoi: RemoteException " + ex.toString());
        }
    }

    public int requestBatchedLocations(SemLocationBatchingRequest request, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null || request == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return this.mService.requestBatchedLocations(request, intent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable ex) {
            Log.e(TAG, "requestLocationBatchingUpdates: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int requestBatchedLocations(SemLocationBatchingRequest request, SemLocationBatchingListener listener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null || request == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        return -7;
    }

    public int removeBatchedLocations(PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        try {
            return this.mService.removeBatchedLocations(intent, null, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable ex) {
            Log.e(TAG, "requestLocationBatchingUpdates: RemoteException " + ex.toString());
            return -4;
        }
    }

    public int removeBatchedLocations(SemLocationBatchingListener listener) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (listener == null) {
            Log.e(TAG, "parameters are not vaild");
            return -2;
        }
        return -7;
    }

    public void flushBatchedLocations() {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return;
        }
        try {
            this.mService.flushBatchedLocations(this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (Throwable ex) {
            Log.e(TAG, "flushLocations: RemoteException " + ex.toString());
        }
    }

    public int addGeofence(SemGeopointGeofence param, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (intent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(param.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence geofence = new SemGeofence(1, param.getLatitude(), param.getLongitude(), param.getRadius(), param.getWifiBssidList());
            geofence.setRequestId(param.getRequestId());
            List<SemGeofence> list = new ArrayList<>();
            list.add(geofence);
            return this.mService.addGeofences(list, intent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "addGeofence : RemoteException " + ex.toString());
            return -4;
        }
    }

    public int addGeofence(SemBluetoothGeofence param, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (intent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(param.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence geofence = new SemGeofence(3, param.getBssid());
            geofence.setRequestId(param.getRequestId());
            List<SemGeofence> list = new ArrayList<>();
            list.add(geofence);
            return this.mService.addGeofences(list, intent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "addGeofence : RemoteException " + ex.toString());
            return -4;
        }
    }

    public int addGeofence(SemWifiGeofence param, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (intent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(param.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence geofence = new SemGeofence(2, param.getBssid());
            geofence.setRequestId(param.getRequestId());
            List<SemGeofence> list = new ArrayList<>();
            list.add(geofence);
            return this.mService.addGeofences(list, intent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "addGeofence : RemoteException " + ex.toString());
            return -4;
        }
    }

    public int addGeofence(SemBleScanGeofence param, PendingIntent intent) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        if (intent == null) {
            Log.e(TAG, "intent is null");
            return -2;
        }
        if (intent.isImmutable()) {
            Log.e(TAG, "pending intent must be mutable");
            return -2;
        }
        if (TextUtils.isEmpty(param.getRequestId())) {
            Log.e(TAG, this.mContext.getPackageName() + " request abnormal requestId");
            return -2;
        }
        try {
            SemGeofence geofence = new SemGeofence(5, param.getAddress(), param.getLatitude(), param.getLongitude());
            geofence.setRequestId(param.getRequestId());
            List<SemGeofence> list = new ArrayList<>();
            list.add(geofence);
            return this.mService.addGeofences(list, intent, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "addGeofence : RemoteException " + ex.toString());
            return -4;
        }
    }

    public int removeGeofence(String requestId) {
        if (this.mService == null) {
            Log.e(TAG, "SLocationService is not supported");
            return -1;
        }
        try {
            List<String> requestIds = new ArrayList<>();
            requestIds.add(requestId);
            return this.mService.removeGeofences(requestIds, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException ex) {
            Log.e(TAG, "removeGeofence: RemoteException " + ex.toString());
            return -4;
        }
    }
}
