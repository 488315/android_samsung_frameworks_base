package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.sepunion.IOneHandService;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class OneHandService extends IOneHandService.Stub implements AbsSemSystemService {
  public static final String TAG = OneHandService.class.getSimpleName();
  public Context mContext;
  public OneHandServiceMonitor mMonitor = new OneHandServiceMonitor();
  public BroadcastReceiver mBootCompleteReceiver =
      new BroadcastReceiver() { // from class: com.android.server.sepunion.OneHandService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          if (intent == null
              || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            return;
          }
          OneHandService.this.startGestureService();
        }
      };
  public Handler mHandler = new Handler();

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  public OneHandService(Context context) {
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

  public void clickTile(String str) {
    Slog.d(TAG, "clickTile() componentName=" + str);
  }

  public void writeSetting(String str, String str2, int i) {
    Slog.d(TAG, "writeSetting() type=" + str + ", key=" + str2 + ", value=" + i);
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
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.sepunion.OneHandService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            OneHandService.this.lambda$onBootPhase$0();
          }
        });
  }

  /* renamed from: clearOneHandModeRunningState, reason: merged with bridge method [inline-methods] */
  public final void lambda$onBootPhase$0() {
    if (Settings.System.getIntForUser(
            this.mContext.getContentResolver(), "any_screen_running", 0, -2)
        == 1) {
      Settings.System.putIntForUser(
          this.mContext.getContentResolver(), "any_screen_running", 0, -2);
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println("\n##### OneHandService  #####\n##### (dumpsys sepunion onehand) #####\n");
    printWriter.println("    Service alive=" + this.mMonitor.isAlive());
  }

  public final boolean isInstalled() {
    try {
      this.mContext.getPackageManager().getPackageInfo("com.samsung.android.sidegesturepad", 1);
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public final void startGestureService() {
    if (isInstalled()) {
      try {
        Intent component =
            new Intent("com.samsung.action.SIDEGESTUREPAD_SERVICE")
                .setComponent(
                    new ComponentName(
                        "com.samsung.android.sidegesturepad",
                        "com.samsung.android.sidegesturepad.SGPService"));
        component.addFlags(16777248);
        component.putExtra("option", "start");
        component.putExtra("extra", "system");
        this.mContext.startService(component);
      } catch (Exception e) {
        Slog.d(TAG, "exception on startGestureService() e=" + e);
      }
      Slog.d(TAG, "startGestureService()");
    }
  }

  public class OneHandServiceMonitor implements IBinder.DeathRecipient {
    public Runnable mRestartRunnable;
    public IBinder mWatcher;

    public OneHandServiceMonitor() {
      this.mRestartRunnable =
          new Runnable() { // from class:
                           // com.android.server.sepunion.OneHandService$OneHandServiceMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              OneHandService.OneHandServiceMonitor.this.lambda$new$0();
            }
          };
    }

    public boolean isAlive() {
      IBinder iBinder = this.mWatcher;
      return iBinder != null && iBinder.isBinderAlive();
    }

    public void registerWatcher(IBinder iBinder) {
      Slog.d(
          OneHandService.TAG, "registerWatcher() binder=" + this.mWatcher + ", watcher=" + iBinder);
      try {
        if (this.mWatcher == null) {
          iBinder.linkToDeath(this, 0);
          this.mWatcher = iBinder;
        }
      } catch (RemoteException e) {
        Slog.d(OneHandService.TAG, "exception on registerWatcher() e=" + e);
      }
    }

    public void unregisterWatcher(IBinder iBinder) {
      Slog.d(
          OneHandService.TAG,
          "unregisterWatcher() binder=" + this.mWatcher + ", watcher=" + iBinder);
      IBinder iBinder2 = this.mWatcher;
      if (iBinder2 == null || iBinder2 != iBinder) {
        return;
      }
      try {
        iBinder.unlinkToDeath(this, 0);
        this.mWatcher = null;
      } catch (IllegalArgumentException e) {
        Slog.d(OneHandService.TAG, "exception on unregisterWatcher() e=" + e);
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.d(OneHandService.TAG, "binderDied() binder=" + this.mWatcher);
      Settings.Global.putInt(
          OneHandService.this.mContext.getContentResolver(),
          "navigation_bar_gesture_disabled_by_policy",
          0);
      try {
        IBinder iBinder = this.mWatcher;
        if (iBinder != null) {
          iBinder.unlinkToDeath(this, 0);
          this.mWatcher = null;
          Slog.e(OneHandService.TAG, "OneHandService has died unexpectedly");
          addPowerSaveWhitelist();
          OneHandService.this.mHandler.removeCallbacks(this.mRestartRunnable);
          OneHandService.this.mHandler.post(this.mRestartRunnable);
          OneHandService.this.mHandler.postDelayed(this.mRestartRunnable, 10000L);
        }
      } catch (Exception e) {
        Slog.d(OneHandService.TAG, "exception on binderDied() e=" + e);
      }
    }

    public final void addPowerSaveWhitelist() {
      try {
        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"))
            .addPowerSaveWhitelistApp("com.samsung.android.sidegesturepad");
      } catch (Exception e) {
        Slog.d(OneHandService.TAG, "exception on addPowerSaveWhitelist() e=" + e);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
      OneHandService.this.startGestureService();
    }
  }
}
