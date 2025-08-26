package com.sec.ims.options;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.sec.ims.extensions.ContextExt;
import com.sec.ims.options.ICapabilityService;
import com.sec.ims.scab.CABContract;
import com.sec.ims.util.ImsUri;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class CapabilityManager {
    private static final String LOG_TAG_BASE = "CapabilityManager";
    private String LOG_TAG;
    private ServiceConnection mConnection;
    private Context mContext;
    private ICapabilityService mImsCapabilityService;
    private ConnectionListener mListener;
    private int mPhoneId;
    private Set<CapabilityListener> mQueuedCapabilityListener;

    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    public CapabilityManager(Context context) {
        this.mPhoneId = 0;
        this.LOG_TAG = LOG_TAG_BASE;
        this.mImsCapabilityService = null;
        this.mQueuedCapabilityListener = new HashSet();
        this.mListener = null;
        this.mConnection = null;
        this.mContext = context;
        init();
    }

    private void init() {
        this.LOG_TAG = "CapabilityManager[" + this.mPhoneId + "] this: " + this;
        connect();
    }

    public void addFakeCapabilityInfo(List<ImsUri> list, boolean z) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            iCapabilityService.addFakeCapabilityInfo(list, z, this.mPhoneId);
        }
    }

    public void connect() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(this.LOG_TAG, "Not recommended in main thread.");
        }
        if (this.mImsCapabilityService != null) {
            Log.i(this.LOG_TAG, "Already connected.");
            return;
        }
        Log.i(this.LOG_TAG, "Connecting to CapabilityDiscoveryService...");
        this.mConnection = new ServiceConnection() { // from class: com.sec.ims.options.CapabilityManager.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(CapabilityManager.this.LOG_TAG, "Connected to CapabilityDiscoveryService.");
                CapabilityManager.this.mImsCapabilityService = ICapabilityService.Stub.asInterface(iBinder);
                if (CapabilityManager.this.mListener != null) {
                    CapabilityManager.this.mListener.onConnected();
                }
                if (CapabilityManager.this.mQueuedCapabilityListener.isEmpty()) {
                    return;
                }
                try {
                    Iterator it = CapabilityManager.this.mQueuedCapabilityListener.iterator();
                    while (it.hasNext()) {
                        CapabilityManager.this.registerListener((CapabilityListener) it.next());
                    }
                    CapabilityManager.this.mQueuedCapabilityListener.clear();
                } catch (RemoteException e) {
                    Log.i(CapabilityManager.this.LOG_TAG, "registerListener failed. RemoteException: " + e);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(CapabilityManager.this.LOG_TAG, "Disconnected to CapabilityDiscoveryService.");
                if (CapabilityManager.this.mListener != null) {
                    CapabilityManager.this.mListener.onDisconnected();
                }
                CapabilityManager.this.mImsCapabilityService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClassName(CABContract.PACKAGE_CONTEXT, "com.sec.internal.ims.imsservice.CapabilityService");
        ContextExt.bindServiceAsUser(this.mContext, intent, this.mConnection, 1, ContextExt.CURRENT_OR_SELF);
    }

    public void disconnect() {
        try {
            ServiceConnection serviceConnection = this.mConnection;
            if (serviceConnection != null) {
                this.mContext.unbindService(serviceConnection);
                return;
            }
            ConnectionListener connectionListener = this.mListener;
            if (connectionListener != null) {
                connectionListener.onDisconnected();
            }
        } catch (IllegalArgumentException e) {
            Log.i(this.LOG_TAG, "disconnect: IllegalArgumentException: " + e);
        }
    }

    public Capabilities[] getAllCapabilities() throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getAllCapabilities(this.mPhoneId);
        }
        return null;
    }

    public Capabilities getCapabilities(Uri uri, int i) throws RemoteException {
        ICapabilityService iCapabilityService;
        if (uri == null || (iCapabilityService = this.mImsCapabilityService) == null) {
            return null;
        }
        return iCapabilityService.getCapabilities(ImsUri.parse(uri.toString()), i, this.mPhoneId);
    }

    public Capabilities[] getCapabilitiesByContactId(String str, int i) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesByContactId(str, i, this.mPhoneId);
        }
        return null;
    }

    public Capabilities getCapabilitiesById(int i) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesById(i, this.mPhoneId);
        }
        return null;
    }

    public Capabilities getCapabilitiesByNumber(String str, int i) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesByNumber(str, i, this.mPhoneId);
        }
        return null;
    }

    public Capabilities getCapabilitiesWithDelay(String str, int i) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesWithDelay(str, i, this.mPhoneId);
        }
        return null;
    }

    public Capabilities getCapabilitiesWithFeature(String str, int i) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesWithFeature(str, i, this.mPhoneId);
        }
        return null;
    }

    public Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getCapabilitiesWithFeatureByUriList(list, i, i2, this.mPhoneId);
        }
        return null;
    }

    public Capabilities getOwnCapabilities() throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            return iCapabilityService.getOwnCapabilities(this.mPhoneId);
        }
        return null;
    }

    public boolean isConnected() {
        return this.mImsCapabilityService != null;
    }

    public void registerListener(CapabilityListener capabilityListener) throws RemoteException {
        if (capabilityListener == null) {
            return;
        }
        Log.i(this.LOG_TAG, "registerListener: listener = " + capabilityListener);
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService == null) {
            Log.e(this.LOG_TAG, "registerListener: not connected.");
            this.mQueuedCapabilityListener.add(capabilityListener);
        } else {
            String strRegisterListener = iCapabilityService.registerListener(capabilityListener.callback, this.mPhoneId);
            if (strRegisterListener != null) {
                capabilityListener.mToken = strRegisterListener;
            }
        }
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        if (connectionListener != null && this.mListener != connectionListener && isConnected()) {
            connectionListener.onConnected();
        }
        this.mListener = connectionListener;
    }

    public void setUserActivity(boolean z) throws RemoteException {
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            iCapabilityService.setUserActivity(z, this.mPhoneId);
        }
    }

    public void unregisterListener(CapabilityListener capabilityListener) throws RemoteException {
        if (capabilityListener == null) {
            return;
        }
        Log.i(this.LOG_TAG, "unregisterListener: listener = " + capabilityListener.mToken);
        ICapabilityService iCapabilityService = this.mImsCapabilityService;
        if (iCapabilityService != null) {
            iCapabilityService.unregisterListener(capabilityListener.mToken, this.mPhoneId);
        } else {
            Log.e(this.LOG_TAG, "unregisterListener: not connected.");
            this.mQueuedCapabilityListener.remove(capabilityListener);
        }
    }

    public CapabilityManager(Context context, int i) {
        this.mPhoneId = 0;
        this.LOG_TAG = LOG_TAG_BASE;
        this.mImsCapabilityService = null;
        this.mQueuedCapabilityListener = new HashSet();
        this.mListener = null;
        this.mConnection = null;
        this.mContext = context;
        this.mPhoneId = i;
        init();
    }
}
