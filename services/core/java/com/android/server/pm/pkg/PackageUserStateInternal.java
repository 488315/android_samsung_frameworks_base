package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.pkg.FrameworkPackageUserState;
import android.util.Pair;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

/* loaded from: classes3.dex */
public interface PackageUserStateInternal extends PackageUserState, FrameworkPackageUserState {
  public static final PackageUserStateInternal DEFAULT = new PackageUserStateDefault();

  WatchedArraySet getDisabledComponentsNoCopy();

  WatchedArraySet getEnabledComponentsNoCopy();

  Pair getOverrideLabelIconForComponent(ComponentName componentName);

  WatchedArrayMap getSuspendParams();
}
