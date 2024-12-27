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

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;

import com.samsung.android.sepunion.IGalaxyRegistryService;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GalaxyRegistryService extends IGalaxyRegistryService.Stub
        implements AbsSemSystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final GalaxyRegistryServiceMonitor mMonitor = new GalaxyRegistryServiceMonitor();
    public final AnonymousClass1 mBootCompleteReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.sepunion.GalaxyRegistryService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null
                            || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        return;
                    }
                    GalaxyRegistryService.m865$$Nest$mstartService(GalaxyRegistryService.this);
                }
            };
    public final Handler mHandler = new Handler();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GalaxyRegistryServiceMonitor implements IBinder.DeathRecipient {
        public final GalaxyRegistryService$GalaxyRegistryServiceMonitor$$ExternalSyntheticLambda0
                mRestartRunnable =
                        new Runnable() { // from class:
                                         // com.android.server.sepunion.GalaxyRegistryService$GalaxyRegistryServiceMonitor$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                GalaxyRegistryService.m865$$Nest$mstartService(
                                        GalaxyRegistryService.this);
                            }
                        };
        public IBinder mWatcher;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.sepunion.GalaxyRegistryService$GalaxyRegistryServiceMonitor$$ExternalSyntheticLambda0] */
        public GalaxyRegistryServiceMonitor() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            int i = GalaxyRegistryService.$r8$clinit;
            Slog.d("GalaxyRegistryService", "binderDied() binder=" + this.mWatcher);
            try {
                IBinder iBinder = this.mWatcher;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(this, 0);
                    this.mWatcher = null;
                    Slog.e("GalaxyRegistryService", "GalaxyRegistryService has died unexpectedly");
                    GalaxyRegistryService.this.mHandler.removeCallbacks(this.mRestartRunnable);
                    GalaxyRegistryService.this.mHandler.postDelayed(this.mRestartRunnable, 10000L);
                }
            } catch (Exception e) {
                int i2 = GalaxyRegistryService.$r8$clinit;
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                        e, "exception on binderDied() e=", "GalaxyRegistryService");
            }
        }
    }

    /* renamed from: -$$Nest$mstartService, reason: not valid java name */
    public static void m865$$Nest$mstartService(GalaxyRegistryService galaxyRegistryService) {
        if (!galaxyRegistryService.isInstalled()) {
            Slog.i("GalaxyRegistryService", "failed to start service - app is not installed");
            return;
        }
        if (TextUtils.isEmpty(galaxyRegistryService.getTargetClass())) {
            Slog.i(
                    "GalaxyRegistryService",
                    "failed to start service - target class is not specified");
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.android.app.galaxyregistry",
                            galaxyRegistryService.getTargetClass()));
            intent.addFlags(16777248);
            intent.putExtra("option", "start");
            intent.putExtra("extra", "system");
            galaxyRegistryService.mContext.startService(intent);
        } catch (Exception e) {
            Slog.d("GalaxyRegistryService", "exception on startService() e = " + e.getMessage());
        }
        Slog.d("GalaxyRegistryService", "startService()");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.GalaxyRegistryService$1] */
    public GalaxyRegistryService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "\n"
                            + "##### GalaxyRegistryService  #####\n"
                            + "##### (dumpsys sepunion galaxyregistry) #####\n",
                        "    Service alive=");
        IBinder iBinder = this.mMonitor.mWatcher;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                m$1, iBinder != null && iBinder.isBinderAlive(), printWriter);
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final String getTargetClass() {
        try {
            Bundle bundle =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo("com.samsung.android.app.galaxyregistry", 128)
                            .metaData;
            if (bundle != null) {
                return bundle.getString(
                        "com.samsung.android.app.galaxyregistry.SYSTEM_SERVICE_CLASS", "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("GalaxyRegistryService", "app is not installed : " + e.getMessage());
        }
        return "";
    }

    public final boolean isInstalled() {
        PackageInfo packageInfo;
        Signature[] signatureArr;
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageInfo =
                    packageManager.getPackageInfo("com.samsung.android.app.galaxyregistry", 64);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("GalaxyRegistryService", "app is not installed : " + e.getMessage());
        }
        if (packageInfo != null
                && (signatureArr = packageInfo.signatures) != null
                && signatureArr.length > 0) {
            return packageManager.checkSignatures(
                            "com.samsung.android.app.galaxyregistry", "android")
                    == 0;
        }
        Slog.e("GalaxyRegistryService", "app has invalid signature");
        return false;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000 && isInstalled()) {
            Slog.d("GalaxyRegistryService", "onBootPhase()");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter.setPriority(1000);
            this.mContext.registerReceiver(this.mBootCompleteReceiver, intentFilter);
        }
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
                "GalaxyRegistryService",
                "registerListener() name="
                        + str
                        + ", cb="
                        + iBinder
                        + ", mMonitor="
                        + this.mMonitor);
        GalaxyRegistryServiceMonitor galaxyRegistryServiceMonitor = this.mMonitor;
        galaxyRegistryServiceMonitor.getClass();
        Slog.d(
                "GalaxyRegistryService",
                "registerWatcher() binder="
                        + galaxyRegistryServiceMonitor.mWatcher
                        + ", watcher="
                        + iBinder);
        try {
            if (galaxyRegistryServiceMonitor.mWatcher == null) {
                iBinder.linkToDeath(galaxyRegistryServiceMonitor, 0);
                galaxyRegistryServiceMonitor.mWatcher = iBinder;
            }
        } catch (RemoteException e) {
            Slog.d("GalaxyRegistryService", "exception on registerWatcher() e=" + e);
        }
    }

    public final void unRegisterListener(String str, IBinder iBinder) {
        Slog.d("GalaxyRegistryService", "unRegisterListener() name=" + str + ", cb=" + iBinder);
        GalaxyRegistryServiceMonitor galaxyRegistryServiceMonitor = this.mMonitor;
        galaxyRegistryServiceMonitor.getClass();
        Slog.d(
                "GalaxyRegistryService",
                "unregisterWatcher() binder="
                        + galaxyRegistryServiceMonitor.mWatcher
                        + ", watcher="
                        + iBinder);
        IBinder iBinder2 = galaxyRegistryServiceMonitor.mWatcher;
        if (iBinder2 == null || iBinder2 != iBinder) {
            return;
        }
        try {
            iBinder.unlinkToDeath(galaxyRegistryServiceMonitor, 0);
            galaxyRegistryServiceMonitor.mWatcher = null;
        } catch (IllegalArgumentException e) {
            Slog.d("GalaxyRegistryService", "exception on unregisterWatcher() e=" + e);
        }
    }
}
