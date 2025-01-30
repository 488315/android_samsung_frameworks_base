package com.android.server;

import android.content.Context;
import android.util.Log;
import com.samsung.android.security.mdf.MdfService.IMdfService;
import com.samsung.android.security.mdf.MdfService.MdfPolicy;

/* loaded from: classes.dex */
public class MdfService extends IMdfService.Stub {
  public MdfPolicy mdfPolicy;

  public MdfService(Context context) {
    MdfPolicy mdfPolicy = MdfPolicy.getInstance(context);
    this.mdfPolicy = mdfPolicy;
    if (mdfPolicy == null) {
      Log.i("MdfService", "mdfService is null");
      return;
    }
    if (mdfPolicy.isCCModeSupport()) {
      try {
        int initCCMode = this.mdfPolicy.initCCMode();
        if (initCCMode != 0) {
          Log.e("MdfService", "initCCMode res = " + Integer.toString(initCCMode));
          return;
        }
        return;
      } catch (SecurityException e) {
        e.printStackTrace();
        throw e;
      }
    }
    Log.i("MdfService", "This device does not support the MDF");
  }

  public int initCCMode() {
    try {
      MdfPolicy mdfPolicy = this.mdfPolicy;
      if (mdfPolicy == null) {
        return -2;
      }
      return mdfPolicy.initCCMode();
    } catch (SecurityException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
