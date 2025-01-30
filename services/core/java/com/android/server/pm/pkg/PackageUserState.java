package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.overlay.OverlayPaths;
import android.util.ArraySet;
import java.util.Map;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public interface PackageUserState {
    public static final PackageUserState DEFAULT = PackageUserStateInternal.DEFAULT;

    OverlayPaths getAllOverlayPaths();

    long getCeDataInode();

    ArraySet getDisabledComponents();

    int getDistractionFlags();

    ArraySet getEnabledComponents();

    int getEnabledState();

    long getFirstInstallTimeMillis();

    String getHarmfulAppWarning();

    int getInstallReason();

    String getLastDisableAppCaller();

    OverlayPaths getOverlayPaths();

    Map getSharedLibraryOverlayPaths();

    String getSplashScreenTheme();

    int getUninstallReason();

    boolean isComponentDisabled(String str);

    boolean isComponentEnabled(String str);

    boolean isHidden();

    boolean isInstalled();

    boolean isInstantApp();

    boolean isNotLaunched();

    boolean isStopped();

    boolean isSuspended();

    boolean isVirtualPreload();
}
