package com.android.server.pm;

import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.WorkSource;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.apphibernation.AppHibernationService;
import com.android.server.pm.dex.ArtStatsLogUtils$ArtStatsLogger;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import java.util.Random;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PackageDexOptimizer {
    public static final Random sRandom = null;
    public final ArtStatsLogUtils$ArtStatsLogger mArtStatsLogger;
    public final Context mContext;
    public final PowerManager.WakeLock mDexoptWakeLock;
    public final Injector mInjector;
    public final PackageManagerTracedLock mInstallLock;
    public final Installer mInstaller;
    public volatile boolean mSystemReady;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PackageDexOptimizer$1, reason: invalid class name */
    public final class AnonymousClass1 implements Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {
    }

    static {
        new Random();
    }

    public PackageDexOptimizer(Installer installer, PackageManagerTracedLock packageManagerTracedLock, Context context, String str) {
        this(new AnonymousClass1(), installer, packageManagerTracedLock, context, str);
    }

    public PackageDexOptimizer(Injector injector, Installer installer, PackageManagerTracedLock packageManagerTracedLock, Context context, String str) {
        this.mContext = context;
        this.mInstaller = installer;
        this.mInstallLock = packageManagerTracedLock;
        ((AnonymousClass1) injector).getClass();
        this.mDexoptWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, str);
        this.mInjector = injector;
    }

    public static String getAugmentedReasonName(int i, boolean z) {
        String str;
        String str2 = z ? "-dm" : "";
        StringBuilder sb = new StringBuilder();
        String[] strArr = PackageManagerServiceCompilerMapping.REASON_STRINGS;
        if (i == 22) {
            str = "install-speg";
        } else if (i == 21) {
            str = "install-spqr";
        } else if (i == 23) {
            str = "adcp";
        } else if (i == 24) {
            str = "install-repair";
        } else if (i == 25) {
            str = "labs";
        } else {
            if (i < 0 || i >= 15) {
                throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "reason ", " invalid"));
            }
            str = PackageManagerServiceCompilerMapping.REASON_STRINGS[i];
        }
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, str2);
    }

    public int adjustDexoptFlags(int i) {
        return i;
    }

    public int adjustDexoptNeeded(int i) {
        return i;
    }

    public final boolean canOptimizePackage(AndroidPackage androidPackage) {
        if ("android".equals(androidPackage.getPackageName()) || !androidPackage.isDeclaredHavingCode() || androidPackage.isApex()) {
            return false;
        }
        ((AnonymousClass1) this.mInjector).getClass();
        AppHibernationService.LocalService localService = (AppHibernationService.LocalService) LocalServices.getService(AppHibernationService.LocalService.class);
        if (localService == null) {
            return true;
        }
        String packageName = androidPackage.getPackageName();
        AppHibernationService appHibernationService = localService.mService;
        if (!appHibernationService.isHibernatingGlobally(packageName)) {
            return true;
        }
        appHibernationService.getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        return !appHibernationService.mOatArtifactDeletionEnabled;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00e3 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int dexOptPath(com.android.server.pm.pkg.AndroidPackage r25, com.android.server.pm.pkg.PackageStateInternal r26, java.lang.String r27, java.lang.String r28, java.lang.String r29, java.lang.String r30, int r31, int r32, boolean r33, java.lang.String r34, java.lang.String r35, int r36) {
        /*
            Method dump skipped, instructions count: 513
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageDexOptimizer.dexOptPath(com.android.server.pm.pkg.AndroidPackage, com.android.server.pm.pkg.PackageStateInternal, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, boolean, java.lang.String, java.lang.String, int):int");
    }

    public final void performDexOpt(AndroidPackage androidPackage, PackageStateInternal packageStateInternal, DexoptOptions dexoptOptions) {
        long elapsedRealtime;
        if ("android".equals(androidPackage.getPackageName())) {
            throw new IllegalArgumentException("System server dexopting should be done via odrefresh");
        }
        if (androidPackage.getUid() == -1) {
            throw new IllegalArgumentException("Dexopt for " + androidPackage.getPackageName() + " has invalid uid.");
        }
        if (canOptimizePackage(androidPackage)) {
            PackageManagerTracedLock packageManagerTracedLock = this.mInstallLock;
            packageManagerTracedLock.mLock.lock();
            try {
                int uid = androidPackage.getUid();
                if (this.mSystemReady) {
                    this.mDexoptWakeLock.setWorkSource(new WorkSource(uid));
                    this.mDexoptWakeLock.acquire(660000L);
                    elapsedRealtime = SystemClock.elapsedRealtime();
                } else {
                    elapsedRealtime = -1;
                }
                try {
                    performDexOptLI(androidPackage, packageStateInternal, dexoptOptions);
                    packageManagerTracedLock.close();
                } finally {
                    releaseWakeLockLI(elapsedRealtime);
                }
            } catch (Throwable th) {
                try {
                    packageManagerTracedLock.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03d8 A[Catch: all -> 0x044f, TryCatch #2 {all -> 0x044f, blocks: (B:73:0x033e, B:75:0x0354, B:85:0x0369, B:96:0x0383, B:103:0x039f, B:106:0x03ab, B:109:0x03b4, B:112:0x03bb, B:115:0x03c2, B:118:0x03cb, B:120:0x03d8, B:123:0x03e7), top: B:72:0x033e }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0475 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x047d  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0368  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int performDexOptLI(com.android.server.pm.pkg.AndroidPackage r35, com.android.server.pm.pkg.PackageStateInternal r36, com.android.server.pm.dex.DexoptOptions r37) {
        /*
            Method dump skipped, instructions count: 1192
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageDexOptimizer.performDexOptLI(com.android.server.pm.pkg.AndroidPackage, com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.dex.DexoptOptions):int");
    }

    public final void releaseWakeLockLI(long j) {
        if (j < 0) {
            return;
        }
        try {
            if (this.mDexoptWakeLock.isHeld()) {
                this.mDexoptWakeLock.release();
            }
            long elapsedRealtime = SystemClock.elapsedRealtime() - j;
            if (elapsedRealtime >= 660000) {
                Slog.wtf("PackageDexOptimizer", "WakeLock " + this.mDexoptWakeLock.getTag() + " time out. Operation took " + elapsedRealtime + " ms. Thread: " + Thread.currentThread().getName());
            }
        } catch (RuntimeException e) {
            Slog.wtf("PackageDexOptimizer", "Error while releasing " + this.mDexoptWakeLock.getTag() + " lock", e);
        }
    }
}
