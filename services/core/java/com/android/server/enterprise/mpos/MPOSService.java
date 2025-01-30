package com.android.server.enterprise.mpos;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.samsung.android.knox.mpos.IMPOSService;
import com.samsung.android.knox.mpos.MposTZServiceConfig;
import com.samsung.android.knox.mpos.TACommandRequest;
import com.samsung.android.knox.mpos.TACommandResponse;
import java.io.IOException;
import java.util.Map;
import vendor.samsung.hardware.mpos.ISehMpos;

/* loaded from: classes2.dex */
public class MPOSService extends IMPOSService.Stub implements EnterpriseServiceCallback {
  public static final String TAG = MPOSService.class.getSimpleName();
  public final Context context;
  public ISehMpos iSehMpos;
  public final Map mRegisteredTzNativeMap;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public synchronized boolean loadTa(
      int i,
      ParcelFileDescriptor parcelFileDescriptor,
      long j,
      long j2,
      MposTZServiceConfig mposTZServiceConfig) {
    if (!checkPermission()) {
      return false;
    }
    int fd = parcelFileDescriptor == null ? -1 : parcelFileDescriptor.getFd();
    Log.d(TAG, "loadTa: " + i + ", fdInt: " + fd + ", offset: " + j + ", len: " + j2);
    try {
      MposTZNative mposTZNative =
          (MposTZNative) this.mRegisteredTzNativeMap.get(Integer.valueOf(i));
      if (mposTZNative == null) {
        mposTZNative =
            new MposTZNative(
                i,
                mposTZServiceConfig.taTechnology,
                mposTZServiceConfig.rootName,
                mposTZServiceConfig.processName,
                mposTZServiceConfig.maxSendCmdSize,
                mposTZServiceConfig.maxRecvRespSize);
        this.mRegisteredTzNativeMap.put(Integer.valueOf(i), mposTZNative);
      }
      boolean loadTA = mposTZNative.loadTA(this.context, fd, j, j2);
      if (loadTA) {
        updateServiceHolder(true);
      }
      if (parcelFileDescriptor != null) {
        try {
          parcelFileDescriptor.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return loadTA;
    } finally {
    }
  }

  public synchronized boolean unloadTa(int i) {
    String str = TAG;
    Log.d(str, "unloadTa: " + i);
    if (!checkPermission()) {
      return false;
    }
    MposTZNative mposTZNative = (MposTZNative) this.mRegisteredTzNativeMap.get(Integer.valueOf(i));
    if (mposTZNative == null) {
      Log.e(str, "unloadTa fail cause tzNative null for " + i);
      return false;
    }
    mposTZNative.unloadTA();
    this.mRegisteredTzNativeMap.remove(Integer.valueOf(i));
    Log.i(str, "remaning map size: " + this.mRegisteredTzNativeMap.size());
    if (this.mRegisteredTzNativeMap.size() <= 0) {
      updateServiceHolder(false);
    }
    return true;
  }

  public synchronized TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) {
    String str = TAG;
    Log.d(str, "processTACommand: " + i);
    if (!checkPermission()) {
      Log.i(str, "processTACommand: permission error");
      return null;
    }
    MposTZNative mposTZNative = (MposTZNative) this.mRegisteredTzNativeMap.get(Integer.valueOf(i));
    if (mposTZNative == null) {
      Log.e(str, "processTACommand fail cause tzNative null for " + i);
      return null;
    }
    return mposTZNative.processTACommand(tACommandRequest);
  }

  public final boolean checkPermission() {
    int callingUid;
    try {
      callingUid = Binder.getCallingUid();
    } catch (Exception e) {
      Log.e(TAG, "checkSystemUid failed: " + e.toString());
    }
    if (UserHandle.isSameApp(callingUid, 1000)) {
      return true;
    }
    String nameForUid = this.context.getPackageManager().getNameForUid(callingUid);
    String str = TAG;
    Log.d(str, "checkSystemUid: " + callingUid + ", name: " + nameForUid);
    ApplicationInfo applicationInfo =
        this.context.getPackageManager().getApplicationInfo(nameForUid, 0);
    if ("com.samsung.android.knox.mpos".equals(nameForUid)
        && applicationInfo.isSignedWithPlatformKey()) {
      return true;
    }
    Log.i(str, "checkSystemUid failed: callerUid: " + callingUid + ", name: " + nameForUid);
    return false;
  }

  public final void updateServiceHolder(boolean z) {
    try {
      boolean isDeclared =
          ServiceManager.isDeclared("vendor.samsung.hardware.mpos.ISehMpos/default");
      Log.i(TAG, "updateServiceHolder: " + isDeclared + ", " + z + ", " + this.iSehMpos);
      if (isDeclared) {
        if (z) {
          ISehMpos iSehMpos = this.iSehMpos;
          if (iSehMpos == null) {
            iSehMpos =
                ISehMpos.Stub.asInterface(
                    ServiceManager.waitForService("vendor.samsung.hardware.mpos.ISehMpos/default"));
          }
          this.iSehMpos = iSehMpos;
          return;
        }
        this.iSehMpos = null;
      }
    } catch (Exception e) {
      Log.i(TAG, "updateServiceHolder failed: " + e);
    }
  }
}
