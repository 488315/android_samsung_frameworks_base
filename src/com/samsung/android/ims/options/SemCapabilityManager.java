package com.samsung.android.ims.options;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.ims.extensions.SemContextExt;
import com.samsung.android.ims.options.SemImsCapabilityService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes6.dex */
public class SemCapabilityManager {
    private static final String INTENT_ACTION_IMSSERVICE_RESTART = "com.sec.ims.imsmanager.RESTART";
    private Context mContext;
    private ConnectionListener mListener;
    private int mPhoneId;
    private final String LOG_TAG_BASE = "semCapabilityManager";
    private String LOG_TAG = "semCapabilityManager";
    private SemImsCapabilityService mImsCapabilityService = null;
    private Set<SemCapabilityListener> mQueuedCapabilityListener = new HashSet();
    private BroadcastReceiver mRestartReceiver = null;
    private ServiceConnection mConnection = null;

    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    public SemCapabilityManager(Context context, ConnectionListener connectionListener, int i) {
        this.mPhoneId = 0;
        this.mContext = context;
        this.mListener = connectionListener;
        this.mPhoneId = i;
        init();
    }

    private void init() {
        this.LOG_TAG = "semCapabilityManager[" + this.mPhoneId + "] this: " + this;
        this.mRestartReceiver = new BroadcastReceiver() { // from class: com.samsung.android.ims.options.SemCapabilityManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "onReceive " + intent.getAction());
                if (TextUtils.equals(intent.getAction(), SemCapabilityManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                    Log.i(SemCapabilityManager.this.LOG_TAG, "IMS service restarted.");
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTENT_ACTION_IMSSERVICE_RESTART);
        this.mContext.registerReceiver(this.mRestartReceiver, intentFilter);
        connect();
    }

    public void connect() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(this.LOG_TAG, "Not recommended in main thread.");
        }
        if (this.mImsCapabilityService != null) {
            Log.i(this.LOG_TAG, "Already connected.");
            return;
        }
        Log.i(this.LOG_TAG, "Connecting to SemCapabilityDiscoveryService...");
        this.mConnection = new ServiceConnection() { // from class: com.samsung.android.ims.options.SemCapabilityManager.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "Connected to SemCapabilityDiscoveryService.");
                SemCapabilityManager.this.mImsCapabilityService = SemImsCapabilityService.Stub.asInterface(iBinder);
                if (SemCapabilityManager.this.mListener != null) {
                    SemCapabilityManager.this.mListener.onConnected();
                }
                if (SemCapabilityManager.this.mQueuedCapabilityListener.isEmpty()) {
                    return;
                }
                try {
                    Iterator it = SemCapabilityManager.this.mQueuedCapabilityListener.iterator();
                    while (it.hasNext()) {
                        SemCapabilityManager.this.registerListener((SemCapabilityListener) it.next());
                    }
                    SemCapabilityManager.this.mQueuedCapabilityListener.clear();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e(SemCapabilityManager.this.LOG_TAG, "registerListener failed. RemoteException: " + e);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(SemCapabilityManager.this.LOG_TAG, "Disconnected to SemCapabilityDiscoveryService.");
                if (SemCapabilityManager.this.mListener != null) {
                    SemCapabilityManager.this.mListener.onDisconnected();
                }
                SemCapabilityManager.this.mImsCapabilityService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClassName("com.sec.imsservice", "com.sec.internal.ims.imsservice.SemCapabilityService");
        SemContextExt.bindServiceAsUser(this.mContext, intent, this.mConnection, 1, SemContextExt.CURRENT_OR_SELF);
    }

    public void disconnect() {
        try {
            BroadcastReceiver broadcastReceiver = this.mRestartReceiver;
            if (broadcastReceiver != null) {
                this.mContext.unregisterReceiver(broadcastReceiver);
                this.mRestartReceiver = null;
            }
            ServiceConnection serviceConnection = this.mConnection;
            if (serviceConnection != null) {
                this.mContext.unbindService(serviceConnection);
            }
            ConnectionListener connectionListener = this.mListener;
            if (connectionListener != null) {
                connectionListener.onDisconnected();
            }
            this.mImsCapabilityService = null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(this.LOG_TAG, "disconnect: IllegalArgumentException: " + e);
        }
    }

    public boolean isConnected() {
        return this.mImsCapabilityService != null;
    }

    public SemCapabilities getOwnCapabilities() throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getOwnCapabilities(this.mPhoneId);
        }
        return null;
    }

    public SemCapabilities getCapabilities(Uri uri, int i) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService;
        if (uri == null || (semImsCapabilityService = this.mImsCapabilityService) == null) {
            return null;
        }
        return semImsCapabilityService.getCapabilities(uri.toString(), i, this.mPhoneId);
    }

    public SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getCapabilitiesByNumber(str, i, z, this.mPhoneId);
        }
        return null;
    }

    public SemCapabilities[] getCapabilitiesByContactId(String str, int i) throws RemoteException {
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService != null) {
            return semImsCapabilityService.getCapabilitiesByContactId(str, i, this.mPhoneId);
        }
        return null;
    }

    public void registerListener(SemCapabilityListener semCapabilityListener) throws RemoteException {
        Log.i(this.LOG_TAG, "registerListener: listener = " + semCapabilityListener + ", Caller = " + Binder.getCallingPid());
        if (semCapabilityListener == null || !TextUtils.isEmpty(semCapabilityListener.getToken())) {
            Log.i(this.LOG_TAG, "registerListener: token is notEmpty ");
            return;
        }
        SemImsCapabilityService semImsCapabilityService = this.mImsCapabilityService;
        if (semImsCapabilityService == null) {
            Log.e(this.LOG_TAG, "registerListener: not connected.");
            this.mQueuedCapabilityListener.add(semCapabilityListener);
        } else {
            String registerListener = semImsCapabilityService.registerListener(semCapabilityListener.callback, this.mPhoneId);
            if (registerListener != null) {
                semCapabilityListener.setToken(registerListener);
            }
        }
    }

    public void unregisterListener(SemCapabilityListener semCapabilityListener) throws RemoteException {
        if (semCapabilityListener == null) {
            return;
        }
        Log.i(this.LOG_TAG, "unregisterListener: listener = " + semCapabilityListener);
        if (this.mImsCapabilityService == null) {
            Log.e(this.LOG_TAG, "unregisterListener: not connected.");
            this.mQueuedCapabilityListener.remove(semCapabilityListener);
        } else {
            this.mImsCapabilityService.unregisterListener(semCapabilityListener.getToken(), this.mPhoneId);
            semCapabilityListener.setToken("");
        }
    }
}
