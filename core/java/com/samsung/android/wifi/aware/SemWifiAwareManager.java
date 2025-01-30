package com.samsung.android.wifi.aware;

import android.content.Context;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public class SemWifiAwareManager {
  public static final String EXTRA_VENDOR_NAN_COMMAND_ID = "wifi_vendor_nan_command_id";
  public static final String EXTRA_VENDOR_NAN_RESPONSE = "wifi_vendor_nan_response";
  public static final String EXTRA_VENDOR_NAN_STATUS_CODE = "wifi_vendor_nan_status_code";
  public static final String SEM_WIFI_VENDOR_NAN_COMMAND_RESPONSE_ACTION =
      "com.samsung.android.wifi.aware.NAN_COMMAND_RESPONSE";
  public static final int STATUS_FALSE = 2;
  public static final int STATUS_TRUE = 1;
  public static final int STATUS_UNABLE_TO_CHECK = 0;
  private final Context mContext;
  private final ISemWifiAwareManager mService;

  public SemWifiAwareManager(Context context, ISemWifiAwareManager service) {
    this.mContext = context;
    this.mService = service;
  }

  public void setClusterMergingEnabled(boolean enable) {
    try {
      this.mService.setClusterMergingEnabled(enable);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int isPreEnabled() {
    try {
      return this.mService.isPreEnabled();
    } catch (RemoteException e) {
      return 0;
    }
  }

  public void setNanCommand(int cmdId, byte[] cmd) {
    try {
      this.mService.setNanCommand(cmdId, cmd);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isVendorNanServiceAvailable() {
    try {
      return this.mService.isVendorNanServiceAvailable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public long getStdPlusFeature() {
    try {
      return this.mService.getStdPlusFeature();
    } catch (RemoteException e) {
      return 0L;
    }
  }
}
