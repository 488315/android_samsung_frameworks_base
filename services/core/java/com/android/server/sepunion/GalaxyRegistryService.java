package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Slog;
import com.samsung.android.sepunion.IGalaxyRegistryService;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class GalaxyRegistryService extends IGalaxyRegistryService.Stub
    implements AbsSemSystemService {
  public static final String TAG = GalaxyRegistryService.class.getSimpleName();
  public Context mContext;
  public GalaxyRegistryServiceMonitor mMonitor = new GalaxyRegistryServiceMonitor();
  public BroadcastReceiver mBootCompleteReceiver =
      new BroadcastReceiver() { // from class: com.android.server.sepunion.GalaxyRegistryService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          if (intent == null
              || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            return;
          }
          GalaxyRegistryService.this.startService();
        }
      };
  public Handler mHandler = new Handler();

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  public GalaxyRegistryService(Context context) {
    this.mContext = context;
  }

  public void registerListener(String str, IBinder iBinder) {
    Slog.d(
        TAG, "registerListener() name=" + str + ", cb=" + iBinder + ", mMonitor=" + this.mMonitor);
    this.mMonitor.registerWatcher(iBinder);
  }

  public void unRegisterListener(String str, IBinder iBinder) {
    Slog.d(TAG, "unRegisterListener() name=" + str + ", cb=" + iBinder);
    this.mMonitor.unregisterWatcher(iBinder);
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 1000 && isInstalled()) {
      Slog.d(TAG, "onBootPhase()");
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
      intentFilter.setPriority(1000);
      this.mContext.registerReceiver(this.mBootCompleteReceiver, intentFilter);
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(
        "\n##### GalaxyRegistryService  #####\n##### (dumpsys sepunion galaxyregistry) #####\n");
    printWriter.println("    Service alive=" + this.mMonitor.isAlive());
  }

  public final boolean isInstalled() {
    PackageInfo packageInfo;
    Signature[] signatureArr;
    PackageManager packageManager = this.mContext.getPackageManager();
    try {
      packageInfo = packageManager.getPackageInfo("com.samsung.android.app.galaxyregistry", 64);
    } catch (PackageManager.NameNotFoundException e) {
      Slog.e(TAG, "app is not installed : " + e.getMessage());
    }
    if (packageInfo != null
        && (signatureArr = packageInfo.signatures) != null
        && signatureArr.length > 0) {
      return packageManager.checkSignatures("com.samsung.android.app.galaxyregistry", "android")
          == 0;
    }
    Slog.e(TAG, "app has invalid signature");
    return false;
  }

  public final String getTargetClass() {
    try {
      Bundle bundle =
          this.mContext
              .getPackageManager()
              .getApplicationInfo("com.samsung.android.app.galaxyregistry", 128)
              .metaData;
      if (bundle != null) {
        return bundle.getString("com.samsung.android.app.galaxyregistry.SYSTEM_SERVICE_CLASS", "");
      }
    } catch (PackageManager.NameNotFoundException e) {
      Slog.e(TAG, "app is not installed : " + e.getMessage());
    }
    return "";
  }

  public final void startService() {
    if (!isInstalled()) {
      Slog.i(TAG, "failed to start service - app is not installed");
      return;
    }
    if (TextUtils.isEmpty(getTargetClass())) {
      Slog.i(TAG, "failed to start service - target class is not specified");
      return;
    }
    try {
      Intent intent = new Intent();
      intent.setComponent(
          new ComponentName("com.samsung.android.app.galaxyregistry", getTargetClass()));
      intent.addFlags(16777248);
      intent.putExtra("option", "start");
      intent.putExtra("extra", "system");
      this.mContext.startService(intent);
    } catch (Exception e) {
      Slog.d(TAG, "exception on startService() e = " + e.getMessage());
    }
    Slog.d(TAG, "startService()");
  }

  public class GalaxyRegistryServiceMonitor implements IBinder.DeathRecipient {
    public Runnable mRestartRunnable;
    public IBinder mWatcher;

    public GalaxyRegistryServiceMonitor() {
      this.mRestartRunnable =
          new Runnable() { // from class:
                           // com.android.server.sepunion.GalaxyRegistryService$GalaxyRegistryServiceMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GalaxyRegistryService.GalaxyRegistryServiceMonitor.this.lambda$new$0();
            }
          };
    }

    public boolean isAlive() {
      IBinder iBinder = this.mWatcher;
      return iBinder != null && iBinder.isBinderAlive();
    }

    public void registerWatcher(IBinder iBinder) {
      Slog.d(
          GalaxyRegistryService.TAG,
          "registerWatcher() binder=" + this.mWatcher + ", watcher=" + iBinder);
      try {
        if (this.mWatcher == null) {
          iBinder.linkToDeath(this, 0);
          this.mWatcher = iBinder;
        }
      } catch (RemoteException e) {
        Slog.d(GalaxyRegistryService.TAG, "exception on registerWatcher() e=" + e);
      }
    }

    public void unregisterWatcher(IBinder iBinder) {
      Slog.d(
          GalaxyRegistryService.TAG,
          "unregisterWatcher() binder=" + this.mWatcher + ", watcher=" + iBinder);
      IBinder iBinder2 = this.mWatcher;
      if (iBinder2 == null || iBinder2 != iBinder) {
        return;
      }
      try {
        iBinder.unlinkToDeath(this, 0);
        this.mWatcher = null;
      } catch (IllegalArgumentException e) {
        Slog.d(GalaxyRegistryService.TAG, "exception on unregisterWatcher() e=" + e);
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.d(GalaxyRegistryService.TAG, "binderDied() binder=" + this.mWatcher);
      try {
        IBinder iBinder = this.mWatcher;
        if (iBinder != null) {
          iBinder.unlinkToDeath(this, 0);
          this.mWatcher = null;
          Slog.e(GalaxyRegistryService.TAG, "GalaxyRegistryService has died unexpectedly");
          GalaxyRegistryService.this.mHandler.removeCallbacks(this.mRestartRunnable);
          GalaxyRegistryService.this.mHandler.postDelayed(this.mRestartRunnable, 10000L);
        }
      } catch (Exception e) {
        Slog.d(GalaxyRegistryService.TAG, "exception on binderDied() e=" + e);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
      GalaxyRegistryService.this.startService();
    }
  }
}
