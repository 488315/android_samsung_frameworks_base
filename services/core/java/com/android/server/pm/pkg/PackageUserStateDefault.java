package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.overlay.OverlayPaths;
import android.util.ArraySet;
import android.util.Pair;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageUserStateDefault implements PackageUserStateInternal {
    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean dataExists() {
        return true;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final OverlayPaths getAllOverlayPaths() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final ArchiveState getArchiveState() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getCeDataInode() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getDeDataInode() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final ArraySet getDisabledComponents() {
        return new ArraySet();
    }

    /* renamed from: getDisabledComponents, reason: collision with other method in class */
    public final Set m785getDisabledComponents() {
        return new ArraySet();
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArraySet getDisabledComponentsNoCopy() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getDistractionFlags() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final ArraySet getEnabledComponents() {
        return new ArraySet();
    }

    /* renamed from: getEnabledComponents, reason: collision with other method in class */
    public final Set m786getEnabledComponents() {
        return new ArraySet();
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArraySet getEnabledComponentsNoCopy() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getEnabledState() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getFirstInstallTimeMillis() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getHarmfulAppWarning() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getInstallReason() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getLastDisableAppCaller() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getMinAspectRatio() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final OverlayPaths getOverlayPaths() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final Pair getOverrideLabelIconForComponent(ComponentName componentName) {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final Map getSharedLibraryOverlayPaths() {
        return Collections.emptyMap();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getSplashScreenTheme() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArrayMap getSuspendParams() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getUninstallReason() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isComponentDisabled(String str) {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isComponentEnabled(String str) {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isHidden() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isInstalled() {
        return true;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isInstantApp() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isNotLaunched() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isQuarantined() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isStopped() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isSuspended() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isVirtualPreload() {
        return false;
    }
}
