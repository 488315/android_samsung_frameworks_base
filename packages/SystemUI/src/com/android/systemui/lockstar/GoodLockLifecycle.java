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
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.SecLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import java.io.PrintWriter;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class GoodLockLifecycle extends SecLifecycle implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isInstalled;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final Handler mainHandler;
    public final PackageManager packageManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public GoodLockLifecycle(Context context, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager) throws PackageManager.NameNotFoundException {
        this.mainHandler = handler;
        this.packageManager = context.getPackageManager();
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.lockstar.GoodLockLifecycle$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageAdded(String str) throws PackageManager.NameNotFoundException {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageAdded: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                this.this$0.updateGoodLockInstalledState(str);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageChanged(String str) throws PackageManager.NameNotFoundException {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageChanged: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                this.this$0.updateGoodLockInstalledState(str);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageRemoved(String str, boolean z) throws PackageManager.NameNotFoundException {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onPackageRemoved: ", str, "GoodLockLifecycle");
                int i = GoodLockLifecycle.$r8$clinit;
                this.this$0.updateGoodLockInstalledState(str);
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "   isInstalled = ", this.isInstalled);
    }

    public final void updateGoodLockInstalledState(String str) throws PackageManager.NameNotFoundException {
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
                        final GoodLockLifecycle goodLockLifecycle = this.this$0;
                        goodLockLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.lockstar.GoodLockLifecycle$notifyChanged$1.1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PluginLockStarManager.AnonymousClass2 anonymousClass2 = (PluginLockStarManager.AnonymousClass2) obj;
                                boolean z2 = goodLockLifecycle.isInstalled;
                                anonymousClass2.getClass();
                                Log.i("LStar|PluginLockStarManager", "onGoodLockAppInstallStateChanged: " + z2);
                                PluginLockStarManager.this.checkGoodLockInstalledState();
                            }
                        });
                    }
                });
            }
        }
    }
}
