package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;

import com.samsung.android.sepunion.IOneHandService;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class OneHandService extends IOneHandService.Stub implements AbsSemSystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final OneHandServiceMonitor mMonitor = new OneHandServiceMonitor();
    public final AnonymousClass1 mBootCompleteReceiver =
            new BroadcastReceiver() { // from class: com.android.server.sepunion.OneHandService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null
                            || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        return;
                    }
                    OneHandService.m867$$Nest$mstartGestureService(OneHandService.this);
                }
            };
    public final AnonymousClass2 mServiceConnection = new AnonymousClass2();
    public final Handler mHandler = new Handler();

    /* renamed from: com.android.server.sepunion.OneHandService$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i = OneHandService.$r8$clinit;
            Slog.i(
                    "OneHandService",
                    "onServiceConnected() name=" + componentName + ", service=" + iBinder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            int i = OneHandService.$r8$clinit;
            Slog.i("OneHandService", "onServiceDisconnected() name=" + componentName);
        }
    }

    public final class OneHandServiceMonitor implements IBinder.DeathRecipient {
        public final OneHandService$$ExternalSyntheticLambda0 mRestartRunnable =
                new OneHandService$$ExternalSyntheticLambda0(1, this);
        public IBinder mWatcher;

        public OneHandServiceMonitor() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            int i = OneHandService.$r8$clinit;
            Slog.d("OneHandService", "binderDied() binder=" + this.mWatcher);
            Settings.Global.putInt(
                    OneHandService.this.mContext.getContentResolver(),
                    "navigation_bar_gesture_disabled_by_policy",
                    0);
            try {
                if (this.mWatcher != null) {
                    OneHandService oneHandService = OneHandService.this;
                    oneHandService.mContext.unbindService(oneHandService.mServiceConnection);
                    Slog.d("OneHandService", "unbindService()");
                    this.mWatcher.unlinkToDeath(this, 0);
                    this.mWatcher = null;
                    Slog.e("OneHandService", "OneHandService has died unexpectedly");
                    try {
                        IDeviceIdleController.Stub.asInterface(
                                        ServiceManager.getService("deviceidle"))
                                .addPowerSaveWhitelistApp("com.samsung.android.sidegesturepad");
                    } catch (Exception e) {
                        int i2 = OneHandService.$r8$clinit;
                        Slog.d("OneHandService", "exception on addPowerSaveWhitelist() e=" + e);
                    }
                    OneHandService.this.mHandler.removeCallbacks(this.mRestartRunnable);
                    OneHandService.this.mHandler.post(this.mRestartRunnable);
                    OneHandService.this.mHandler.postDelayed(this.mRestartRunnable, 10000L);
                }
            } catch (Exception e2) {
                int i3 = OneHandService.$r8$clinit;
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                        e2, "exception on binderDied() e=", "OneHandService");
            }
        }
    }

    /* renamed from: -$$Nest$mbindService, reason: not valid java name */
    public static void m866$$Nest$mbindService(OneHandService oneHandService) {
        boolean z;
        oneHandService.getClass();
        try {
            Intent intent = new Intent();
            intent.setClassName(
                    "com.samsung.android.sidegesturepad",
                    "com.samsung.android.sidegesturepad.SGPService");
            z = oneHandService.mContext.bindService(intent, oneHandService.mServiceConnection, 1);
        } catch (SecurityException e) {
            Slog.d("OneHandService", "exception on bindService() e=" + e);
            z = false;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "bindService() ret=", "OneHandService", z);
    }

    /* renamed from: -$$Nest$mstartGestureService, reason: not valid java name */
    public static void m867$$Nest$mstartGestureService(OneHandService oneHandService) {
        try {
            oneHandService
                    .mContext
                    .getPackageManager()
                    .getPackageInfo("com.samsung.android.sidegesturepad", 1);
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
                oneHandService.mContext.startService(component);
            } catch (Exception e) {
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                        e, "exception on startGestureService() e=", "OneHandService");
            }
            Slog.d("OneHandService", "startGestureService()");
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.OneHandService$1] */
    public OneHandService(Context context) {
        this.mContext = context;
    }

    public final void clickTile(String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "clickTile() componentName=", str, "OneHandService");
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "\n##### OneHandService  #####\n##### (dumpsys sepunion onehand) #####\n",
                        "    Service alive=");
        IBinder iBinder = this.mMonitor.mWatcher;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                m$1, iBinder != null && iBinder.isBinderAlive(), printWriter);
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            try {
                this.mContext
                        .getPackageManager()
                        .getPackageInfo("com.samsung.android.sidegesturepad", 1);
                Slog.d("OneHandService", "onBootPhase()");
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                intentFilter.setPriority(1000);
                this.mContext.registerReceiver(this.mBootCompleteReceiver, intentFilter);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        this.mHandler.post(new OneHandService$$ExternalSyntheticLambda0(0, this));
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    public final void registerListener(String str, IBinder iBinder) {
        Slog.d(
                "OneHandService",
                "registerListener() name="
                        + str
                        + ", cb="
                        + iBinder
                        + ", mMonitor="
                        + this.mMonitor);
        OneHandServiceMonitor oneHandServiceMonitor = this.mMonitor;
        oneHandServiceMonitor.getClass();
        Slog.d(
                "OneHandService",
                "registerWatcher() binder="
                        + oneHandServiceMonitor.mWatcher
                        + ", watcher="
                        + iBinder);
        try {
            if (oneHandServiceMonitor.mWatcher == null) {
                iBinder.linkToDeath(oneHandServiceMonitor, 0);
                oneHandServiceMonitor.mWatcher = iBinder;
                m866$$Nest$mbindService(OneHandService.this);
            }
        } catch (RemoteException e) {
            Slog.d("OneHandService", "exception on registerWatcher() e=" + e);
        }
    }

    public final void unRegisterListener(String str, IBinder iBinder) {
        Slog.d("OneHandService", "unRegisterListener() name=" + str + ", cb=" + iBinder);
        OneHandServiceMonitor oneHandServiceMonitor = this.mMonitor;
        oneHandServiceMonitor.getClass();
        Slog.d(
                "OneHandService",
                "unregisterWatcher() binder="
                        + oneHandServiceMonitor.mWatcher
                        + ", watcher="
                        + iBinder);
        IBinder iBinder2 = oneHandServiceMonitor.mWatcher;
        if (iBinder2 == null || iBinder2 != iBinder) {
            return;
        }
        try {
            OneHandService oneHandService = OneHandService.this;
            oneHandService.mContext.unbindService(oneHandService.mServiceConnection);
            Slog.d("OneHandService", "unbindService()");
            iBinder.unlinkToDeath(oneHandServiceMonitor, 0);
            oneHandServiceMonitor.mWatcher = null;
        } catch (IllegalArgumentException e) {
            Slog.d("OneHandService", "exception on unregisterWatcher() e=" + e);
        }
    }

    public final void writeSetting(String str, String str2, int i) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "writeSetting() type=", str, ", key=", str2, ", value="),
                i,
                "OneHandService");
    }
}
