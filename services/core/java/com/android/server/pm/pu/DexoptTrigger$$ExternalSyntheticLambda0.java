package com.android.server.pm.pu;

import android.os.CancellationSignal;
import android.util.Slog;

import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.pkg.PackageState;

import java.util.HashSet;

public final /* synthetic */ class DexoptTrigger$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DexoptTrigger f$0;
    public final /* synthetic */ ProfileUtilizationService.App f$1;

    public /* synthetic */ DexoptTrigger$$ExternalSyntheticLambda0(
            DexoptTrigger dexoptTrigger, ProfileUtilizationService.App app) {
        this.f$0 = dexoptTrigger;
        this.f$1 = app;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DexoptTrigger dexoptTrigger = this.f$0;
        ProfileUtilizationService.App app = this.f$1;
        dexoptTrigger.getClass();
        Slog.d("PU_DexoptTrigger", "Trigger dexopt for " + app.packageName);
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(app.packageName);
            boolean z = (packageState == null ? null : packageState.getAndroidPackage()) != null;
            int performDexopt = z ? DexoptTrigger.performDexopt(withFilteredSnapshot, app) : 30;
            withFilteredSnapshot.close();
            StringBuilder sb = new StringBuilder();
            sb.append(app.packageName);
            sb.append(" result: ");
            sb.append(z ? Integer.valueOf(performDexopt) : "removed");
            Slog.d("PU_DexoptTrigger", sb.toString());
            synchronized (dexoptTrigger.mRunningApps) {
                try {
                    if (performDexopt == 40) {
                        if (app.mState != ProfileUtilizationService.App.State.OPTIMIZED) {
                            app.mCancelCount++;
                            app.mState = ProfileUtilizationService.App.State.CANCELLED;
                            app.mCancellationSignal = new CancellationSignal();
                        }
                        return;
                    }
                    if (z) {
                        app.mState = ProfileUtilizationService.App.State.OPTIMIZED;
                        app.mOptimizedTimeMs = System.currentTimeMillis();
                    } else {
                        app.mState = ProfileUtilizationService.App.State.REMOVED;
                        app.mOptimizedTimeMs = System.currentTimeMillis();
                    }
                    ((HashSet) dexoptTrigger.mRunningApps).remove(app);
                    dexoptTrigger.mRunningApps.notify();
                } finally {
                }
            }
        } catch (Throwable th) {
            if (withFilteredSnapshot != null) {
                try {
                    withFilteredSnapshot.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
