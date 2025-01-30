package com.android.server.sepunion;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import com.android.server.LocalServices;
import com.android.server.sepunion.cover.CoverManagerServiceImpl;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.IPluginManager;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemPluginManagerLocal;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class SemPluginManagerService extends IPluginManager.Stub
    implements AbsSemSystemService, AbsSemSystemServiceForS {
  public static final String TAG = SemPluginManagerService.class.getSimpleName();
  public SemPluginManagerLocal localService;
  public Context mContext;
  public CoverManagerServiceImpl mCoverManagerServiceImpl;
  public boolean mIsCoverSupport;

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStarting(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStopped(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserStopping(int i) {}

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserUnlocking(int i) {}

  public SemPluginManagerService(Context context) {
    Log.d(TAG, "SemPluginManagerService");
    this.mContext = context;
    this.mIsCoverSupport = context.getPackageManager().hasSystemFeature("com.sec.feature.cover");
    initialize();
  }

  public void initialize() {
    if (this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl = new CoverManagerServiceImpl(this.mContext);
    }
    SemPluginManagerLocal semPluginManagerLocal =
        (SemPluginManagerLocal) LocalServices.getService(SemPluginManagerLocal.class);
    this.localService = semPluginManagerLocal;
    if (semPluginManagerLocal == null) {
      LocalServices.addService(
          SemPluginManagerLocal.class, new SemPluginLocalService(this.mContext));
    }
  }

  public void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState) {
    Log.d(TAG, "notifySmartCoverAttachStateChanged");
    if (this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl.notifySmartCoverAttachStateChanged(j, z, coverState);
    }
  }

  public void notifyCoverSwitchStateChanged(long j, boolean z) {
    Log.d(TAG, "notifyCoverSwitchStateChanged");
    if (this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl.notifyCoverSwitchStateChanged(j, z);
    }
  }

  public CoverManagerServiceImpl getCoverManagerServiceImpl() {
    return this.mCoverManagerServiceImpl;
  }

  public CoverState getCoverState() {
    if (this.mIsCoverSupport) {
      return this.mCoverManagerServiceImpl.getCoverState();
    }
    return null;
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 600) {
      if (this.mIsCoverSupport) {
        this.mCoverManagerServiceImpl.systemRunning();
      }
    } else if (i == 1000 && this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl.onBootComplete();
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (strArr != null && strArr.length != 0 && strArr[0].equals("cover")) {
      dumpCoverInfomation(fileDescriptor, printWriter, strArr);
    } else {
      dumpCoverInfomation(fileDescriptor, printWriter, strArr);
    }
  }

  public final void dumpCoverInfomation(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mIsCoverSupport) {
      printWriter.println(
          "\n##### SEP COVER MANAGER SERVICE #####\n##### (dumpsys sepunion cover) #####\n");
      if (strArr == null || strArr.length == 0) {
        this.mCoverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
        return;
      }
      if (strArr[0].equals("close")) {
        this.mCoverManagerServiceImpl.notifyCoverSwitchStateChanged(System.nanoTime(), false);
        return;
      }
      if (strArr[0].equals("open")) {
        this.mCoverManagerServiceImpl.notifyCoverSwitchStateChanged(System.nanoTime(), true);
        return;
      }
      if (strArr[0].equals("secure_on")) {
        if (((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
            .resetPassword("111111", 1)) {
          return;
        }
        Log.d(TAG, "dumpCoverInfomation: resetPassword(secure_on) failed.");
      } else {
        if (strArr[0].equals("secure_off")) {
          if (((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
              .resetPassword("", 1)) {
            return;
          }
          Log.d(TAG, "dumpCoverInfomation: resetPassword(secure_off) failed.");
          return;
        }
        this.mCoverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
      }
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserUnlocked(int i) {
    if (this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl.onUserUnlocked(i);
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemServiceForS
  public void onUserSwitching(int i, int i2) {
    if (this.mIsCoverSupport) {
      this.mCoverManagerServiceImpl.onSwitchUser(i2);
    }
  }
}
