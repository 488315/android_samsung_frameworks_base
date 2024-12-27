package com.android.server.integrity;

import android.content.pm.ApplicationInfo;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.Slog;

import com.android.internal.compat.IPlatformCompat;
import com.android.internal.pm.parsing.IPackageCacher;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.server.SystemConfig;

import java.util.Set;
import java.util.function.Supplier;

public final /* synthetic */ class AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda0
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        final IPlatformCompat asInterface =
                IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        return new PackageParser2(
                (String[]) null,
                (DisplayMetrics) null,
                (IPackageCacher) null,
                new PackageParser2
                        .Callback() { // from class:
                                      // com.android.server.pm.parsing.PackageParserUtils$1
                    public final Set getHiddenApiWhitelistedApps() {
                        return SystemConfig.getInstance().mHiddenApiPackageWhitelist;
                    }

                    public final Set getInstallConstraintsAllowlist() {
                        return SystemConfig.getInstance().mInstallConstraintsAllowlist;
                    }

                    public final boolean hasFeature(String str) {
                        return false;
                    }

                    public final boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) {
                        try {
                            return asInterface.isChangeEnabled(j, applicationInfo);
                        } catch (Exception e) {
                            Slog.wtf("PackageParsing", "IPlatformCompat query failed", e);
                            return true;
                        }
                    }
                });
    }
}
