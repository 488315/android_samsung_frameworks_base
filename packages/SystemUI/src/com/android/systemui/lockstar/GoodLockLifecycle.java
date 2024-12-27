package com.android.systemui.lockstar;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.SecLifecycle;
import java.io.PrintWriter;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GoodLockLifecycle extends SecLifecycle implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isInstalled;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final Handler mainHandler;
    public final PackageManager packageManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public GoodLockLifecycle(Context context, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager) {
        this.mainHandler = handler;
        this.packageManager = context.getPackageManager();
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.lockstar.GoodLockLifecycle$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageAdded(String str) {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageAdded: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                GoodLockLifecycle.this.updateGoodLockInstalledState(str);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageChanged(String str) {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageChanged: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                GoodLockLifecycle.this.updateGoodLockInstalledState(str);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageRemoved(String str, boolean z) {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageRemoved: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                GoodLockLifecycle.this.updateGoodLockInstalledState(str);
            }
        };
        this.keyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        Log.i("GoodLockLifecycle", "GoodLockLifecycle: init");
        dumpManager.registerNormalDumpable("GoodLockLifecycle", this);
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        updateGoodLockInstalledState("com.samsung.android.goodlock");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("GoodLockLifecycle:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   isInstalled = ", this.isInstalled, printWriter);
    }

    public final void updateGoodLockInstalledState(String str) {
        if (str != null && TextUtils.equals("com.samsung.android.goodlock", str)) {
            Log.i("GoodLockLifecycle", "updateGoodLockInstalledState: GOOD_LOCK");
            boolean z = false;
            try {
                this.packageManager.getPackageInfo(str, 0);
                z = true;
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (z != this.isInstalled) {
                this.isInstalled = z;
                Log.i("GoodLockLifecycle", "updateGoodLockInstalledState: " + z);
                this.mainHandler.post(new Runnable() { // from class: com.android.systemui.lockstar.GoodLockLifecycle$notifyChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final GoodLockLifecycle goodLockLifecycle = GoodLockLifecycle.this;
                        goodLockLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.lockstar.GoodLockLifecycle$notifyChanged$1.1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onGoodLockAppInstallStateChanged: ", "LStar|PluginLockStarManager", GoodLockLifecycle.this.isInstalled);
                                PluginLockStarManager.this.checkGoodLockInstalledState();
                            }
                        });
                    }
                });
            }
        }
    }
}
