package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.util.Slog;

import com.android.internal.policy.AttributeCache;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;

import java.io.File;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AsecInstallArgs {
    public String cid;
    public final int mInstallFlags;
    public final PackageManagerService mPm;
    public String packagePath;

    public AsecInstallArgs(
            int i, InstallSource installSource, PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mInstallFlags = i;
    }

    public final boolean pendingPostDeleteLI(final int i, final boolean z) {
        List list = Collections.EMPTY_LIST;
        boolean z2 = false;
        if (z) {
            File file = new File(this.packagePath);
            if (file.exists()) {
                ParseResult parsePackageLite =
                        ApkLiteParseUtils.parsePackageLite(
                                ParseTypeImpl.forDefaultParsing().reset(), file, 0);
                if (parsePackageLite.isSuccess()) {
                    ((PackageLite) parsePackageLite.getResult()).getAllApkPaths();
                }
            }
        }
        String str = this.cid;
        boolean isContainerMounted = PackageHelperExt.isContainerMounted(str);
        if (isContainerMounted) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "AsecInstallArgs.doPostDeleteLI(", "): ");
            m.append(AsecInstallHelper.getAsecPackageName(str));
            m.append(", codePath: ");
            m.append(this.packagePath);
            Slog.d("PackageManager", m.toString());
            AttributeCache.instance().removePackage(AsecInstallHelper.getAsecPackageName(str));
            ResourcesManager.getInstance().invalidatePath(this.packagePath);
            Runtime.getRuntime().gc();
            System.runFinalization();
            if (!PackageHelperExt.unMountSdDir(str, !z)) {
                if (z) {
                    if (i < 5) {
                        this.mPm.mHandler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.pm.AsecInstallArgs$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AsecInstallArgs asecInstallArgs = AsecInstallArgs.this;
                                        boolean z3 = z;
                                        int i2 = i;
                                        PackageManagerTracedLock packageManagerTracedLock =
                                                asecInstallArgs.mPm.mInstallLock;
                                        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                        synchronized (packageManagerTracedLock) {
                                            try {
                                                asecInstallArgs.pendingPostDeleteLI(i2 + 1, z3);
                                            } catch (Throwable th) {
                                                boolean z5 =
                                                        PackageManagerService.DEBUG_COMPRESSION;
                                                throw th;
                                            }
                                        }
                                    }
                                },
                                60000 * i);
                    }
                    if (i == 4) {
                        PackageManagerServiceUtils.logCriticalInfo(
                                5,
                                "ASEC unmount failed and will try for last chance: ".concat(str));
                    }
                }
            }
            if (!z2 && z) {
                PackageHelperExt.destroySdDir(this.cid);
            }
            return !z2;
        }
        z2 = isContainerMounted;
        if (!z2) {
            PackageHelperExt.destroySdDir(this.cid);
        }
        return !z2;
    }

    public final void setMountPath(String str) {
        File file = new File(str);
        File file2 = new File(file, "pkg.apk");
        if (file2.exists()) {
            this.packagePath = file2.getAbsolutePath();
        } else {
            this.packagePath = file.getAbsolutePath();
        }
    }
}
