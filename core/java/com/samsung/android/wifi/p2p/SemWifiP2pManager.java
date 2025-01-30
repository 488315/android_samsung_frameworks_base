package com.samsung.android.wifi.p2p;

import android.content.Context;
import android.net.MacAddress;
import android.net.wifi.p2p.WifiP2pDevice;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.RemoteException;
import android.util.Log;
import java.util.List;

/* loaded from: classes6.dex */
public class SemWifiP2pManager {
  public static final int BUSY = 2;
  public static final int ERROR = 0;
  public static final String EXTRA_DEVICE = "semWifiP2pDevice";
  public static final int P2P_UNSUPPORTED = 1;
  private static final String TAG = "SemWifiP2pManager";
  public static final String TYPE_WIFI_AWARE = "aware";
  public static final String TYPE_WIFI_P2P = "p2p";
  public static final String WIFI_P2P_PEER_FOUND_ACTION = "com.samsung.android.wifi.p2p.PEER_FOUND";

  @Deprecated(forRemoval = true, since = "13.5")
  public static final int WIFI_P2P_STATE_CONNECTED = 3;

  @Deprecated(forRemoval = true, since = "13.5")
  public static final int WIFI_P2P_STATE_DISABLED = 1;

  @Deprecated(forRemoval = true, since = "13.5")
  public static final int WIFI_P2P_STATE_ENABLED = 2;

  private final Context mContext;
  private Looper mLooper;
  private final ISemWifiP2pManager mService;

  public interface ActionListener {
    void onFailure(int i);

    void onSuccess();
  }

  public SemWifiP2pManager(Context context, ISemWifiP2pManager service) {
    this.mContext = context;
    this.mService = service;
    this.mLooper = context.getMainLooper();
  }

  /* JADX INFO: Access modifiers changed from: private */
  class SemWifiP2pCallbackProxy extends ISemWifiP2pCallback.Stub {
    private final String mActionTag;
    private final Object mCallback;
    private final Handler mHandler;

    SemWifiP2pCallbackProxy(String actionTag, Looper looper, Object callback) {
      this.mActionTag = actionTag;
      this.mHandler = new Handler(looper);
      this.mCallback = callback;
    }

    @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
    public void onSuccess() {
      Log.m100v(
          SemWifiP2pManager.TAG, "SemWifiP2pCallbackProxy:" + this.mActionTag + ": onSuccess");
      if (this.mCallback != null) {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.wifi.p2p.SemWifiP2pManager$SemWifiP2pCallbackProxy$$ExternalSyntheticLambda1
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
    public void onFailure(final int reason) {
      Log.m100v(
          SemWifiP2pManager.TAG,
          "SemWifiP2pCallbackProxy:" + this.mActionTag + ": onFailure=" + reason);
      if (this.mCallback != null) {
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.wifi.p2p.SemWifiP2pManager$SemWifiP2pCallbackProxy$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemWifiP2pManager.SemWifiP2pCallbackProxy.this.lambda$onFailure$1(reason);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFailure$1(int reason) {
      ((ActionListener) this.mCallback).onFailure(reason);
    }
  }

  @Deprecated(forRemoval = true, since = "13.5")
  public boolean isWifiP2pEnabled() {
    try {
      return this.mService.getWifiP2pState() != 1;
    } catch (RemoteException e) {
      return false;
    }
  }

  @Deprecated(forRemoval = true, since = "13.5")
  public boolean isWifiP2pConnected() {
    try {
      return this.mService.getWifiP2pState() == 3;
    } catch (RemoteException e) {
      return false;
    }
  }

  public void setMsMiceInfo(int capability, String hexName, String hexIpAddr) {
    try {
      this.mService.setMsMiceInfo(capability, hexName, hexIpAddr);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void setScreenSharing(boolean set) {
    try {
      this.mService.setScreenSharing(set);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated(forRemoval = true, since = "15.1")
  public void setPreparedAccountPin(String pin, String hexEncData, String hexIv) {
    try {
      this.mService.setPreparedAccountPin(pin, hexEncData, hexIv, null);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void setPreparedAccountPin(
      String pin, String hexEncData, String hexIv, ActionListener listener) {
    SemWifiP2pCallbackProxy callbackProxy = null;
    if (listener != null) {
      callbackProxy = new SemWifiP2pCallbackProxy("setPreparedAccountPin", this.mLooper, listener);
    }
    try {
      this.mService.setPreparedAccountPin(pin, hexEncData, hexIv, callbackProxy);
    } catch (RemoteException e) {
      if (callbackProxy != null) {
        callbackProxy.onFailure(0);
      }
      throw e.rethrowFromSystemServer();
    }
  }

  public void setListenOffloading(int channel, int period, int interval, int count) {
    try {
      this.mService.setListenOffloading(channel, period, interval, count);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public void controlOpenWifiScanTimer(int control) {
    try {
      this.mService.controlOpenWifiScanTimer(control);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public List<String> getInUsePackageList(String type) {
    try {
      return this.mService.getInUsePackageList(type);
    } catch (RemoteException e) {
      return null;
    }
  }

  public void setInUsePackage(String type, Context srcContext, String pkg, boolean reqNextAction) {
    try {
      this.mService.setInUsePackage(type, srcContext.getOpPackageName(), pkg, reqNextAction);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public void unsetInUsePackage(
      String type, Context srcContext, String pkg, boolean reqNextAction) {
    try {
      this.mService.unsetInUsePackage(type, srcContext.getOpPackageName(), pkg, reqNextAction);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public void unsetAllInUsePackage(String type) {
    try {
      this.mService.unsetAllInUsePackage(type);
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public void removeClient(String deviceAddress, ActionListener listener) {
    SemWifiP2pCallbackProxy callbackProxy = null;
    if (listener != null) {
      callbackProxy = new SemWifiP2pCallbackProxy("removeClient", this.mLooper, listener);
    }
    try {
      this.mService.removeClient(deviceAddress, callbackProxy);
    } catch (RemoteException e) {
      if (callbackProxy != null) {
        callbackProxy.onFailure(0);
      }
    }
  }

  public void discoverPeersOnSocialChannels(ActionListener listener) {
    discoverPeersOnSpecificChannel(1611, listener);
  }

  public void discoverPeersOnSpecificChannel(int channelNum, ActionListener listener) {
    SemWifiP2pCallbackProxy callbackProxy = null;
    if (listener != null) {
      callbackProxy = new SemWifiP2pCallbackProxy("discoverPeers", this.mLooper, listener);
    }
    try {
      this.mService.discoverPeers(channelNum, callbackProxy);
    } catch (RemoteException e) {
      if (callbackProxy != null) {
        callbackProxy.onFailure(0);
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

  public SemWifiP2pDevice getSemWifiP2pDevice(WifiP2pDevice device) {
    if (!device.getVendorElements().isEmpty()) {
      return new SemWifiP2pDevice(
          device.deviceAddress, device.deviceName, device.getVendorElements());
    }
    try {
      return this.mService.getSemWifiP2pDevice(device.deviceAddress);
    } catch (RemoteException e) {
      return null;
    }
  }

  public int[] getChannelsMhzForBand(int band) {
    try {
      return this.mService.getChannelsMhzForBand(band);
    } catch (RemoteException e) {
      Log.m102w(TAG, "getChannelsMhzForBand:" + band + ": onFailure=" + e.getMessage());
      return new int[0];
    }
  }

  public boolean isP2pSoftApConcurrencySupported() {
    try {
      return this.mService.isP2pSoftApConcurrencySupported();
    } catch (RemoteException e) {
      Log.m102w(TAG, "isP2pSoftApConcurrencySupported:" + e.getMessage());
      return false;
    }
  }

  public boolean disconnectApBlockAutojoin(boolean block) {
    try {
      return this.mService.disconnectApBlockAutojoin(block);
    } catch (RemoteException e) {
      return false;
    }
  }

  public long getP2pFeature() {
    try {
      return this.mService.getP2pFeature();
    } catch (RemoteException e) {
      return 0L;
    }
  }
}
