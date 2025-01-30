package com.samsung.android.hwrs;

import android.content.Context;
import android.p009os.RemoteException;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemHwrsManager {
  private static final String TAG = "[HWRS_SYS]SemHwrsManager";
  private final Context mContext;
  private final ISemHwrsManager mService;
  private final int mUserId;

  public SemHwrsManager(Context context, ISemHwrsManager service, int userId) {
    this.mContext = context;
    this.mService = service;
    this.mUserId = userId;
  }

  public boolean isCameraShareEnable() throws RemoteException {
    try {
      return this.mService.isCameraShareEnable();
    } catch (RemoteException e) {
      Log.m96e(TAG, "isCameraShareEnable failed- " + e);
      return false;
    }
  }
}
