package com.android.server.pm.pu;

import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.art.model.DexoptParams;
import com.android.server.art.model.DexoptResult;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.pu.ProfileUtilizationService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexoptTrigger {
    public static final int DEXOPT_CONCURRENCY = SystemProperties.getInt("pm.dexopt.boot-after-mainline-update.concurrency", 1);
    public boolean mDexopting;
    public final ExecutorService mExecutor;
    public final Set mRunningApps;
    public final DeviceStatusWatcher mWatcher;
    public HotAppsWrapper mWrapper;

    public DexoptTrigger(DeviceStatusWatcher deviceStatusWatcher) {
        int i = DEXOPT_CONCURRENCY;
        this.mExecutor = Executors.newFixedThreadPool(i);
        this.mRunningApps = new HashSet(i);
        this.mWatcher = deviceStatusWatcher;
    }

    public static int performDexopt(PackageManagerLocal.FilteredSnapshot filteredSnapshot, ProfileUtilizationService.App app) {
        try {
            DexoptResult dexoptPackage = DexOptHelper.getArtManagerLocal().dexoptPackage(filteredSnapshot, app.packageName, new DexoptParams.Builder("profile-utilization").setCompilerFilter("speed-profile").setPriorityClass(60).build(), app.mCancellationSignal);
            app.mResult = dexoptPackage;
            return dexoptPackage.getFinalStatus();
        } catch (IllegalArgumentException | IllegalStateException e) {
            Slog.e("PU_DexoptTrigger", "Dexopt failed to optimize package " + app.packageName, e);
            return 30;
        }
    }

    public final void pauseOptimizing() {
        if (this.mDexopting) {
            Slog.d("PU_DexoptTrigger", "Pause dexopting");
            synchronized (this.mRunningApps) {
                try {
                    this.mDexopting = false;
                    Iterator it = ((HashSet) this.mRunningApps).iterator();
                    while (it.hasNext()) {
                        ((ProfileUtilizationService.App) it.next()).mCancellationSignal.cancel();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void resumeOptimizing() {
        if (this.mDexopting) {
            return;
        }
        Slog.d("PU_DexoptTrigger", "Resume dexopting");
        synchronized (this.mRunningApps) {
            try {
                this.mDexopting = true;
                Iterator it = ((HashSet) this.mRunningApps).iterator();
                while (it.hasNext()) {
                    ProfileUtilizationService.App app = (ProfileUtilizationService.App) it.next();
                    ProfileUtilizationService.App.State state = app.mState;
                    ProfileUtilizationService.App.State state2 = ProfileUtilizationService.App.State.OPTIMIZING;
                    if (state != state2) {
                        app.mState = state2;
                        this.mExecutor.execute(new DexoptTrigger$$ExternalSyntheticLambda0(this, app));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Dexopt concurrency: ");
        sb.append(DEXOPT_CONCURRENCY);
        synchronized (this.mRunningApps) {
            try {
                sb.append("\nDexopting: ");
                sb.append(this.mDexopting);
                if (!((HashSet) this.mRunningApps).isEmpty()) {
                    sb.append("\nRunning apps:");
                    Iterator it = ((HashSet) this.mRunningApps).iterator();
                    while (it.hasNext()) {
                        sb.append("\n  " + ((ProfileUtilizationService.App) it.next()));
                    }
                }
                if (this.mWrapper != null) {
                    sb.append("\n" + this.mWrapper.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sb.toString();
    }
}
