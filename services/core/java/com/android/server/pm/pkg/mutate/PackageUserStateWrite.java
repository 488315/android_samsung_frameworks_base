package com.android.server.pm.pkg.mutate;

import android.content.ComponentName;
import android.content.pm.overlay.OverlayPaths;
import com.android.server.pm.pkg.SuspendParams;

/* loaded from: classes3.dex */
public interface PackageUserStateWrite {
  PackageUserStateWrite putSuspendParams(String str, SuspendParams suspendParams);

  PackageUserStateWrite removeSuspension(String str);

  PackageUserStateWrite setComponentLabelIcon(ComponentName componentName, String str, Integer num);

  PackageUserStateWrite setDistractionFlags(int i);

  PackageUserStateWrite setHarmfulAppWarning(String str);

  PackageUserStateWrite setHidden(boolean z);

  PackageUserStateWrite setInstalled(boolean z);

  PackageUserStateWrite setNotLaunched(boolean z);

  PackageUserStateWrite setOverlayPaths(OverlayPaths overlayPaths);

  PackageUserStateWrite setOverlayPathsForLibrary(String str, OverlayPaths overlayPaths);

  PackageUserStateWrite setSplashScreenTheme(String str);

  PackageUserStateWrite setStopped(boolean z);

  PackageUserStateWrite setUninstallReason(int i);
}
