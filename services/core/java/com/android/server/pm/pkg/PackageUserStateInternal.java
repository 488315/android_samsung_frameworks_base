package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.pkg.FrameworkPackageUserState;
import android.util.Pair;

import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

public interface PackageUserStateInternal extends PackageUserState, FrameworkPackageUserState {
    public static final PackageUserStateDefault DEFAULT = new PackageUserStateDefault();

    WatchedArraySet getDisabledComponentsNoCopy();

    WatchedArraySet getEnabledComponentsNoCopy();

    Pair getOverrideLabelIconForComponent(ComponentName componentName);

    WatchedArrayMap getSuspendParams();
}
