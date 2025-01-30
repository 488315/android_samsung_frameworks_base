package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.util.Log;
import com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter;

/* loaded from: classes.dex */
public class GrdmIcccManagerService extends IGrdmIntegrityControlCheckCenter.Stub {
  public static String TAG = "GrdmIcccManagerService";
  public static Context mContext;

  public static native byte[] grdm_iccc_attestation_platform(byte[] bArr);

  public static native int grdm_iccc_load();

  public static native int grdm_iccc_unload();

  public final String getCallerPackageName(int i) {
    Log.d(TAG, "getCallerPackageName");
    try {
      return ((ActivityManager) mContext.getSystemService("activity"))
          .getPackageFromAppProcesses(i);
    } catch (Exception unused) {
      return null;
    }
  }

  public synchronized byte[] grdmSetAttestationData(byte[] bArr) {
    Log.d(TAG, "grdmSetAttestationData");
    return "com.samsung.android.knox.attestation"
            .equals(getCallerPackageName(Binder.getCallingPid()))
        ? grdm_iccc_attestation_platform(bArr)
        : null;
  }
}
