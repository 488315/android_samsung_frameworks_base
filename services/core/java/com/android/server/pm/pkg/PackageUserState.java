package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.overlay.OverlayPaths;
import android.util.ArraySet;

import java.util.Map;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface PackageUserState {
    public static final PackageUserState DEFAULT = PackageUserStateInternal.DEFAULT;

    boolean dataExists();

    OverlayPaths getAllOverlayPaths();

    ArchiveState getArchiveState();

    long getCeDataInode();

    long getDeDataInode();

    ArraySet getDisabledComponents();

    int getDistractionFlags();

    ArraySet getEnabledComponents();

    int getEnabledState();

    long getFirstInstallTimeMillis();

    String getHarmfulAppWarning();

    int getInstallReason();

    String getLastDisableAppCaller();

    int getMinAspectRatio();

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

    boolean isQuarantined();

    boolean isStopped();

    boolean isSuspended();

    boolean isVirtualPreload();
}
