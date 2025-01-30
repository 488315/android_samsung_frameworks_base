package com.android.server.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.p005os.IInstalld;
import android.util.Log;
import com.samsung.android.knox.IBasicCommand;
import dalvik.system.DexClassLoader;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class KnoxForesightService extends IBasicCommand.Stub {
  public static String KFAgentVersion;
  public static int sClassLoadCount;
  public static KnoxForesightService sInstance;
  public Thread clientThread;
  public DexClassLoader dexClassLoader;
  public IBasicCommand kfAgent;
  public Context mContext;

  public KnoxForesightService(Context context) {
    this.mContext = context;
    registerReceivers();
  }

  public static synchronized KnoxForesightService getInstance(Context context) {
    KnoxForesightService knoxForesightService;
    synchronized (KnoxForesightService.class) {
      if (sInstance == null) {
        sInstance = new KnoxForesightService(context);
      }
      knoxForesightService = sInstance;
    }
    return knoxForesightService;
  }

  public Bundle sendCmd(Bundle bundle) {
    Log.d("KnoxForesightService", "sendCmd " + bundle);
    if (!checkPlatformSignatureByUid(Binder.getCallingUid())) {
      Log.d("KnoxForesightService", "caller not allowed : " + Binder.getCallingUid());
      throw new SecurityException("caller not allowed : " + Binder.getCallingUid());
    }
    String string = bundle.getString("cmd");
    if (string == null) {
      Log.d("KnoxForesightService", "invalid cmd received");
      return null;
    }
    if (string.equals("initialize")) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        initializeKnoxForesight();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } else {
      Log.d("KnoxForesightService", "unknown cmd received");
    }
    return null;
  }

  public void setCaller(IBasicCommand iBasicCommand) {
    Log.d("KnoxForesightService", "setCaller");
    if (!checkPlatformSignatureByUid(Binder.getCallingUid())) {
      Log.d("KnoxForesightService", "caller not allowed : " + Binder.getCallingUid());
      throw new SecurityException("caller not allowed : " + Binder.getCallingUid());
    }
    this.kfAgent = iBasicCommand;
  }

  public void initializeKnoxForesight() {
    String str;
    if (!checkPlatformSignature()) {
      Log.d("KnoxForesightService", "signature is not matched, ignore");
      return;
    }
    try {
      PackageInfo packageInfo =
          this.mContext
              .getPackageManager()
              .getPackageInfo("com.samsung.android.knox.foresight", IInstalld.FLAG_FORCE);
      String str2 = packageInfo.applicationInfo.publicSourceDir;
      this.clientThread = createThread();
      if (this.dexClassLoader != null && (str = KFAgentVersion) != null) {
        int i = sClassLoadCount;
        sClassLoadCount = i + 1;
        if (i >= 5 || str.equals(packageInfo.versionName)) {
          Log.d(
              "KnoxForesightService",
              "ignore for max load count exceeded " + sClassLoadCount + "/" + KFAgentVersion);
          this.clientThread.setContextClassLoader(this.dexClassLoader);
          this.clientThread.start();
        }
      }
      KFAgentVersion = packageInfo.versionName;
      this.dexClassLoader =
          new DexClassLoader(str2, null, null, this.mContext.getClass().getClassLoader());
      this.clientThread.setContextClassLoader(this.dexClassLoader);
      this.clientThread.start();
    } catch (PackageManager.NameNotFoundException unused) {
      Log.d("KnoxForesightService", "KnoxForesight is not installed");
    }
  }

  public final Thread createThread() {
    return new Thread() { // from class: com.android.server.knox.KnoxForesightService.1
      @Override // java.lang.Thread, java.lang.Runnable
      public void run() {
        Log.d(
            "KnoxForesightService",
            "systemclassloader : " + ClassLoader.getSystemClassLoader().toString());
        try {
          getContextClassLoader()
              .loadClass("com.samsung.android.knox.foresight.framework.system.SystemServiceEntry")
              .getMethod("initialize", Context.class)
              .invoke(null, KnoxForesightService.this.mContext);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e2) {
          e2.printStackTrace();
        } catch (InvocationTargetException e3) {
          e3.printStackTrace();
        } catch (Exception e4) {
          e4.printStackTrace();
        }
      }
    };
  }

  public final void registerReceivers() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter.addDataScheme("package");
    intentFilter.addDataSchemeSpecificPart("com.samsung.android.knox.foresight", 0);
    this.mContext.registerReceiver(new ForesightPackageReceiver(), intentFilter, null, null);
  }

  public class ForesightPackageReceiver extends BroadcastReceiver {
    public ForesightPackageReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Log.d("KnoxForesightService", "ForesightPackageReceiver: " + intent);
      String action = intent.getAction();
      action.hashCode();
      if (action.equals("android.intent.action.PACKAGE_ADDED")) {
        if ("com.samsung.android.knox.foresight"
            .equals(KnoxForesightService.this.getPackageName(intent))) {
          KnoxForesightService.this.initializeKnoxForesight();
          return;
        } else {
          Log.d("KnoxForesightService", "package name not matched");
          return;
        }
      }
      Log.w("KnoxForesightService", "Received unknown intent: " + intent);
    }
  }

  public boolean checkPlatformSignature() {
    return this.mContext
            .getPackageManager()
            .checkSignatures("android", "com.samsung.android.knox.foresight")
        == 0;
  }

  public boolean checkPlatformSignatureByUid(int i) {
    return this.mContext.getPackageManager().checkSignatures(1000, i) == 0;
  }

  public String getPackageName(Intent intent) {
    Uri data = intent.getData();
    if (data != null) {
      return data.getSchemeSpecificPart();
    }
    return null;
  }
}
