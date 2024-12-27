package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.pkg.FrameworkPackageUserState;
import android.util.Pair;

import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface PackageUserStateInternal extends PackageUserState, FrameworkPackageUserState {
    public static final PackageUserStateDefault DEFAULT = new PackageUserStateDefault();

    WatchedArraySet getDisabledComponentsNoCopy();

    WatchedArraySet getEnabledComponentsNoCopy();

    Pair getOverrideLabelIconForComponent(ComponentName componentName);

    WatchedArrayMap getSuspendParams();
}
