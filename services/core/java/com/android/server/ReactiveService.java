package com.android.server;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Slog;
import com.samsung.android.service.reactive.IReactiveService;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ReactiveService extends IReactiveService.Stub {
  public static Context mContext;
  public static final Object mLock = new Object();
  public static final Object mLockUEvent = new Object();
  public final String mDataBlockFile;
  public int mErrorCode;
  public PersistentDataBlockManager mPdbManager;
  public final BroadcastReceiver mReceiver;
  public int mServiceSupport;
  public boolean mThreadUartGoWait = true;

  private native boolean nativeFinishedSetupWizard();

  private native int nativeGetFlag(int i);

  private native byte[] nativeGetRandom();

  private native String nativeGetString();

  private native int nativeGetSystemSolution();

  private native boolean nativeHasPermission(String str, String str2);

  private native void nativeInit();

  private native int nativeRemoveString();

  private native byte[] nativeSessionAccept(byte[] bArr);

  private native int nativeSessionComplete(byte[] bArr);

  private native int nativeSetFlag(int i, int i2, String str);

  private native int nativeSetString(String str);

  private native int nativeVerify(String str, int i);

  private native boolean nativeisCsUnlockRequest();

  static {
    System.loadLibrary("terrier");
  }

  public ReactiveService(Context context) {
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.ReactiveService.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())
                && SystemProperties.get("persist.sys.setupwizard").equals("FINISH")) {
              try {
                if (Settings.Secure.getInt(
                        ReactiveService.mContext.getContentResolver(), "secure_frp_mode")
                    == 1) {
                  Settings.Secure.putInt(
                      ReactiveService.mContext.getContentResolver(), "secure_frp_mode", 0);
                  Slog.i("ReactiveService", "Secure FRP mode is disabled");
                }
              } catch (Settings.SettingNotFoundException unused) {
                Slog.i("ReactiveService", "SECURE_FRP_MODE not found");
              }
            }
          }
        };
    this.mReceiver = broadcastReceiver;
    mContext = context;
    this.mErrorCode = 0;
    nativeInit();
    this.mServiceSupport = nativeGetSystemSolution();
    this.mDataBlockFile = SystemProperties.get("ro.frp.pst");
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
      mContext.registerReceiver(broadcastReceiver, intentFilter);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final boolean hasPermission(int i, String str) {
    String str2;
    boolean nativeHasPermission;
    Signature signature =
        new Signature(
            "308201c13082012aa00302010202044e5cba90300d06092a864886f70d010105050030243110300e060355040b130773616d73756e673110300e0603550403130773616d73756e673020170d3131303833303130323532305a180f32313131303830363130323532305a30243110300e060355040b130773616d73756e673110300e0603550403130773616d73756e6730819f300d06092a864886f70d010101050003818d0030818902818100d80c410cec5799bb3607d27e0c992c9c35238c42e8726a5ecbb190f6dfb59aee9fa3b1a8812620bafb25b24ce4fc777d4b98da1f7fda95f4a7788a70c635fca893e022a676ae40c906ee83a63d953a310da47d3789af59219621aafb551ebe866f977298c70a9d60e5251270db0d35869cb7c5522b7a82c951189cb5d2b9b19d0203010001300d06092a864886f70d0101050500038181007f6820e6cfd72e67afeba80043da6eccc5ec5b0b994974669d2d7c8876f7e1fa9767dbc930549f875c1604cd8a6ea7d84b66ec500c7bf11526ee4ff77d0720036d61454e9354f5291c0545a43ddf26f9d09e64226239cbd0b98945578d596aa9f9ff7a05c140a2f05fe0260f574bfce94f50273558101f896a9c70be6b05496b");
    ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
    boolean z = false;
    if (activityManager == null) {
      Slog.e("ReactiveService", "ActivityManager is null, something wrong in framework");
      return false;
    }
    if (activityManager.getRunningAppProcesses() == null) {
      Slog.e("ReactiveService", "getRunningAppProcesses return null List. Cannot check permision");
      return false;
    }
    Iterator<ActivityManager.RunningAppProcessInfo> it =
        activityManager.getRunningAppProcesses().iterator();
    while (true) {
      if (!it.hasNext()) {
        str2 = "";
        break;
      }
      ActivityManager.RunningAppProcessInfo next = it.next();
      if (next.pid == i) {
        str2 = next.processName;
        break;
      }
    }
    PackageManager packageManager = mContext.getPackageManager();
    try {
      PackageInfo packageInfo = packageManager.getPackageInfo(str2, 64);
      if (packageInfo.applicationInfo.uid == Binder.getCallingUid()) {
        if (packageManager.checkSignatures("android", str2) == 0) {
          nativeHasPermission = nativeHasPermission(str2, str);
        } else if ("com.osp.app.signin".equals(str2)) {
          for (Signature signature2 : packageInfo.signatures) {
            if (signature.equals(signature2)) {
              nativeHasPermission = nativeHasPermission(str2, str);
            }
          }
        }
        z = nativeHasPermission;
        break;
      } else {
        Slog.e(
            "ReactiveService",
            "NOT Allowed : pkg does not match uid("
                + packageInfo.applicationInfo.uid
                + "/"
                + Binder.getCallingUid()
                + ")");
      }
    } catch (Exception e) {
      e.printStackTrace();
      Slog.e("ReactiveService", "Fail to get packageinfo.");
    }
    if (z) {
      Slog.i("ReactiveService", "Requested package name = [" + str2 + "]");
    } else {
      Slog.e("ReactiveService", "Permission denied. Package name = [" + str2 + "]");
    }
    return z;
  }

  public static boolean isSetupWizardCompleted() {
    boolean z = Settings.Global.getInt(mContext.getContentResolver(), "device_provisioned", 1) == 1;
    Slog.i("ReactiveService", "isSetupWizardCompleted: " + z);
    return z;
  }

  public int getServiceSupport() {
    return this.mServiceSupport;
  }

  /* JADX WARN: Removed duplicated region for block: B:35:0x005c  */
  /* JADX WARN: Removed duplicated region for block: B:38:0x005f  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int getFlag(int i) {
    int nativeGetFlag;
    int i2 = -2;
    if (i == 0) {
      int i3 = this.mServiceSupport;
      if ((i3 & 1) > 0 || (2 & i3) > 0) {
        synchronized (mLock) {
          nativeGetFlag = nativeGetFlag(i);
        }
        i2 = nativeGetFlag;
      }
      if (i2 >= 0) {}
      Slog.i("ReactiveService", "getFlag() : flag=[" + i + "], ret=[" + i2 + "]");
      return i2;
    }
    if (i == 2) {
      if ((this.mServiceSupport & 4) > 0) {
        PersistentDataBlockManager persistentDataBlockManager =
            (PersistentDataBlockManager) mContext.getSystemService("persistent_data_block");
        this.mPdbManager = persistentDataBlockManager;
        if (persistentDataBlockManager == null) {
          Slog.i("ReactiveService", "Failed to load FRP Manager");
          return -7;
        }
        try {
          if (persistentDataBlockManager.isEnabled()) {
            synchronized (mLock) {
              nativeGetFlag = isSetupWizardCompleted() ? 1 : 2;
            }
            i2 = nativeGetFlag;
          } else {
            i2 = 0;
          }
        } catch (Exception e) {
          e.printStackTrace();
          this.mErrorCode = -6;
          return -6;
        }
      }
      if (i2 >= 0) {
        this.mErrorCode = i2;
      } else {
        this.mErrorCode = 0;
      }
      Slog.i("ReactiveService", "getFlag() : flag=[" + i + "], ret=[" + i2 + "]");
      return i2;
    }
    this.mErrorCode = -3;
    return -3;
  }

  public int setFlag(int i, int i2, String str) {
    int nativeSetFlag;
    if (i != 0) {
      this.mErrorCode = -3;
      return -3;
    }
    if (i == 0) {
      int i3 = this.mServiceSupport;
      if ((i3 & 1) <= 0 && (i3 & 2) <= 0) {
        this.mErrorCode = -2;
        return -2;
      }
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return -5;
    }
    synchronized (mLock) {
      nativeSetFlag = nativeSetFlag(i, i2, str);
    }
    if (nativeSetFlag < 0) {
      this.mErrorCode = nativeSetFlag;
    } else {
      this.mErrorCode = 0;
    }
    Slog.i(
        "ReactiveService",
        "setFlag() : flag=[" + i + "], value=[" + i2 + "], ret=[" + nativeSetFlag + "]");
    return nativeSetFlag;
  }

  public String getString() {
    String nativeGetString;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return null;
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return null;
    }
    synchronized (mLock) {
      nativeGetString = nativeGetString();
    }
    if (nativeGetString == null) {
      this.mErrorCode = -4;
    }
    return nativeGetString;
  }

  public int setString(String str) {
    int nativeSetString;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return -2;
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return -5;
    }
    synchronized (mLock) {
      nativeSetString = nativeSetString(str);
    }
    if (nativeSetString != 0) {
      this.mErrorCode = nativeSetString;
    }
    return nativeSetString;
  }

  public int removeString() {
    int nativeRemoveString;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return -2;
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return -5;
    }
    synchronized (mLock) {
      nativeRemoveString = nativeRemoveString();
    }
    if (nativeRemoveString != 0) {
      this.mErrorCode = nativeRemoveString;
    }
    return nativeRemoveString;
  }

  public byte[] sessionAccept(byte[] bArr) {
    byte[] bArr2 = null;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return null;
    }
    synchronized (mLock) {
      try {
        bArr2 = nativeSessionAccept(bArr);
      } catch (Exception e) {
        Slog.e("ReactiveService", e.toString());
      }
    }
    if (bArr2 == null) {
      this.mErrorCode = -1;
    }
    return bArr2;
  }

  public int sessionComplete(byte[] bArr) {
    int i;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return -2;
    }
    synchronized (mLock) {
      try {
        i = nativeSessionComplete(bArr);
      } catch (Exception e) {
        Slog.e("ReactiveService", e.toString());
        i = -1;
      }
    }
    if ((this.mServiceSupport & 4) > 0 && i == 0 && nativeisCsUnlockRequest()) {
      PersistentDataBlockManager persistentDataBlockManager =
          (PersistentDataBlockManager) mContext.getSystemService("persistent_data_block");
      this.mPdbManager = persistentDataBlockManager;
      if (persistentDataBlockManager == null) {
        Slog.i("ReactiveService", "Failed to load FRP Manager");
        return -7;
      }
      try {
        persistentDataBlockManager.wipe();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    this.mErrorCode = i;
    return i;
  }

  public int getErrorCode() {
    return this.mErrorCode;
  }

  public byte[] getRandom() {
    byte[] nativeGetRandom;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return null;
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return null;
    }
    synchronized (mLock) {
      nativeGetRandom = nativeGetRandom();
    }
    if (nativeGetRandom != null) {
      return nativeGetRandom;
    }
    this.mErrorCode = -4;
    return null;
  }

  public int verify(String str, int i) {
    int nativeVerify;
    if (this.mServiceSupport == 0) {
      this.mErrorCode = -2;
      return -2;
    }
    if (!hasPermission(
        Binder.getCallingPid(), Thread.currentThread().getStackTrace()[2].getMethodName())) {
      this.mErrorCode = -5;
      return -5;
    }
    synchronized (mLock) {
      nativeVerify = nativeVerify(str, i);
    }
    if (nativeVerify != 0) {
      this.mErrorCode = nativeVerify;
    }
    return nativeVerify;
  }
}
