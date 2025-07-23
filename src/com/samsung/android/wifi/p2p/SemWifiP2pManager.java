package com.samsung.android.wifi.p2p;

import android.content.Context;
import android.net.MacAddress;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.wifi.p2p.ISemWifiP2pCallback;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import java.util.List;

/* loaded from: classes6.dex */
public class SemWifiP2pManager {
    public static final int BUSY = 2;
    public static final int ERROR = 0;

    @Deprecated(forRemoval = true, since = "17.0")
    public static final String EXTRA_DEVICE = "semWifiP2pDevice";
    public static final int P2P_UNSUPPORTED = 1;
    private static final String TAG = "SemWifiP2pManager";
    public static final String TYPE_WIFI_AWARE = "aware";
    public static final String TYPE_WIFI_P2P = "p2p";
    public static final String WIFI_P2P_CLIENT_IP_UPDATED_ACTION = "com.samsung.android.wifi.p2p.CLIENT_IP_UPDATED";

    @Deprecated(forRemoval = true, since = "17.0")
    public static final String WIFI_P2P_PEER_FOUND_ACTION = "com.samsung.android.wifi.p2p.PEER_FOUND";
    public static final String WIFI_P2P_SCREEN_SHARING_STATUS_RECEIVED_ACTION = "com.samsung.android.wifi.p2p.SCREEN_SHARING_STATUS_RECEIVED";
    private final Context mContext;
    private Looper mLooper;
    private final ISemWifiP2pManager mService;

    public interface ActionListener {
        void onFailure(int i);

        void onSuccess();
    }

    public SemWifiP2pManager(Context context, ISemWifiP2pManager iSemWifiP2pManager) {
        this.mContext = context;
        this.mService = iSemWifiP2pManager;
        this.mLooper = context.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SemWifiP2pCallbackProxy extends ISemWifiP2pCallback.Stub {
        private final String mActionTag;
        private final Object mCallback;
        private final Handler mHandler;

        SemWifiP2pCallbackProxy(SemWifiP2pManager semWifiP2pManager, String str, Looper looper, Object obj) {
            this.mActionTag = str;
            this.mHandler = new Handler(looper);
            this.mCallback = obj;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
        public void onSuccess() {
            Log.v(SemWifiP2pManager.TAG, "SemWifiP2pCallbackProxy:" + this.mActionTag + ": onSuccess");
            if (this.mCallback != null) {
                this.mHandler.post(new Runnable() { // from class: com.samsung.android.wifi.p2p.SemWifiP2pManager$SemWifiP2pCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiP2pManager.SemWifiP2pCallbackProxy.this.lambda$onSuccess$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            ((ActionListener) this.mCallback).onSuccess();
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
        public void onFailure(final int i) {
            Log.v(SemWifiP2pManager.TAG, "SemWifiP2pCallbackProxy:" + this.mActionTag + ": onFailure=" + i);
            if (this.mCallback != null) {
                this.mHandler.post(new Runnable() { // from class: com.samsung.android.wifi.p2p.SemWifiP2pManager$SemWifiP2pCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiP2pManager.SemWifiP2pCallbackProxy.this.lambda$onFailure$1(i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$1(int i) {
            ((ActionListener) this.mCallback).onFailure(i);
        }
    }

    public boolean isWifiP2pConnected() {
        try {
            return this.mService.isP2pConnected();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setMsMiceInfo(int i, String str, String str2) {
        try {
            this.mService.setMsMiceInfo(i, str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setScreenSharing(boolean z) {
        try {
            this.mService.setScreenSharing(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated(forRemoval = true, since = "15.1")
    public void setPreparedAccountPin(String str, String str2, String str3) {
        try {
            this.mService.setPreparedAccountPin(1, str, str2, str3, null, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPreparedAccountPin(String str, String str2, String str3, ActionListener actionListener) {
        SemWifiP2pCallbackProxy semWifiP2pCallbackProxy = actionListener != null ? new SemWifiP2pCallbackProxy(this, "setPreparedAccountPin", this.mLooper, actionListener) : null;
        try {
            this.mService.setPreparedAccountPin(1, str, str2, str3, null, semWifiP2pCallbackProxy);
        } catch (RemoteException e) {
            if (semWifiP2pCallbackProxy != null) {
                semWifiP2pCallbackProxy.onFailure(0);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ActionListener actionListener) {
        SemWifiP2pCallbackProxy semWifiP2pCallbackProxy = actionListener != null ? new SemWifiP2pCallbackProxy(this, "setPreparedAccountPin", this.mLooper, actionListener) : null;
        try {
            this.mService.setPreparedAccountPin(i, str, str2, str3, str4, semWifiP2pCallbackProxy);
        } catch (RemoteException e) {
            if (semWifiP2pCallbackProxy != null) {
                semWifiP2pCallbackProxy.onFailure(0);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    public void setListenOffloading(int i, int i2, int i3, int i4) {
        try {
            this.mService.setListenOffloading(i, i2, i3, i4);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void controlOpenWifiScanTimer(int i) {
        try {
            this.mService.controlOpenWifiScanTimer(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public List<String> getInUsePackageList(String str) {
        try {
            return this.mService.getInUsePackageList(str);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void setInUsePackage(String str, Context context, String str2, boolean z) {
        try {
            this.mService.setInUsePackage(str, context.getOpPackageName(), str2, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unsetInUsePackage(String str, Context context, String str2, boolean z) {
        try {
            this.mService.unsetInUsePackage(str, context.getOpPackageName(), str2, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unsetAllInUsePackage(String str) {
        try {
            this.mService.unsetAllInUsePackage(str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void removeClient(String str, ActionListener actionListener) {
        SemWifiP2pCallbackProxy semWifiP2pCallbackProxy = actionListener != null ? new SemWifiP2pCallbackProxy(this, "removeClient", this.mLooper, actionListener) : null;
        try {
            this.mService.removeClient(str, semWifiP2pCallbackProxy);
        } catch (RemoteException unused) {
            if (semWifiP2pCallbackProxy != null) {
                semWifiP2pCallbackProxy.onFailure(0);
            }
        }
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void discoverPeersOnSocialChannels(ActionListener actionListener) {
        discoverPeersOnSpecificChannel(1611, actionListener);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void discoverPeersOnSpecificChannel(int i, ActionListener actionListener) {
        SemWifiP2pCallbackProxy semWifiP2pCallbackProxy = actionListener != null ? new SemWifiP2pCallbackProxy(this, "discoverPeers", this.mLooper, actionListener) : null;
        try {
            this.mService.discoverPeers(i, semWifiP2pCallbackProxy);
        } catch (RemoteException unused) {
            if (semWifiP2pCallbackProxy != null) {
                semWifiP2pCallbackProxy.onFailure(0);
            }
        }
    }

    public MacAddress getP2pFactoryMacAddress() {
        try {
            return this.mService.getP2pFactoryMacAddress();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SemWifiP2pDevice getSemWifiP2pDevice(WifiP2pDevice wifiP2pDevice) {
        if (!wifiP2pDevice.getVendorElements().isEmpty()) {
            return new SemWifiP2pDevice(wifiP2pDevice.deviceAddress, wifiP2pDevice.deviceName, wifiP2pDevice.getVendorElements());
        }
        try {
            return this.mService.getSemWifiP2pDevice(wifiP2pDevice.deviceAddress);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int[] getChannelsMhzForBand(int i) {
        try {
            return this.mService.getChannelsMhzForBand(i);
        } catch (RemoteException e) {
            Log.w(TAG, "getChannelsMhzForBand:" + i + ": onFailure=" + e.getMessage());
            return new int[0];
        }
    }

    public boolean isP2pSoftApConcurrencySupported() {
        try {
            return this.mService.isP2pSoftApConcurrencySupported();
        } catch (RemoteException e) {
            Log.w(TAG, "isP2pSoftApConcurrencySupported:" + e.getMessage());
            return false;
        }
    }

    public boolean disconnectApBlockAutojoin(boolean z) {
        try {
            return this.mService.disconnectApBlockAutojoin(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public long getP2pFeature() {
        try {
            return this.mService.getP2pFeature();
        } catch (RemoteException unused) {
            return 0L;
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAwareEnabled(boolean z) {
        try {
            this.mService.setAwareEnabled(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
