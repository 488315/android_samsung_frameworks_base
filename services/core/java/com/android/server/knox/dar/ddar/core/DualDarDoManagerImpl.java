package com.android.server.knox.dar.ddar.core;

import android.os.ServiceManager;

import com.android.server.knox.dar.DarDatabaseCache;
import com.android.server.knox.dar.DarManagerService;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DualDarDoManagerImpl {
    public final DarManagerService.Injector mInjector;
    public boolean mHasTokenSetForInner = false;
    public final List mNonClearablePackages =
            new ArrayList(
                    Arrays.asList(
                            KnoxCustomManagerService.SETTING_PKG_NAME,
                            "com.google.android.gms",
                            "com.samsung.android.knox.containercore",
                            "com.google.android.providers.media.module",
                            "com.google.android.apps.work.clouddpc",
                            KnoxCustomManagerService.SBROWSER_CSC_PACKAGE_NAME,
                            "com.google.android.webview"));

    public DualDarDoManagerImpl(DarManagerService.Injector injector) {
        this.mInjector = injector;
    }

    public final int getInnerAuthUserId(int i) {
        DarDatabaseCache darDatabaseCache = this.mInjector.mDarDatabaseCache;
        darDatabaseCache.getClass();
        try {
            String internal = darDatabaseCache.getInternal(i, "ddar.inner.auth.userid");
            if (internal != null) {
                return Integer.parseInt(internal);
            }
            return -10000;
        } catch (NumberFormatException unused) {
            return -10000;
        }
    }

    public final int getPasswordMinimumLengthForInner() {
        if (!DualDarManager.isOnDeviceOwnerEnabled()) {
            return 0;
        }
        DarManagerService.Injector injector = this.mInjector;
        if (injector.mDualDARPolicyService == null) {
            injector.mDualDARPolicyService =
                    IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
        }
        return ((Integer)
                        Optional.ofNullable(injector.mDualDARPolicyService)
                                .map(new DualDarDoManagerImpl$$ExternalSyntheticLambda0())
                                .orElse(0))
                .intValue();
    }
}
