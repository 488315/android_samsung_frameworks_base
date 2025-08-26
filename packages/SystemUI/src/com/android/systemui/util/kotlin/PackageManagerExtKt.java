package com.android.systemui.util.kotlin;

import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import com.android.systemui.util.Assert;

/* loaded from: classes3.dex */
public final class PackageManagerExtKt {
    public static final boolean isComponentActuallyEnabled(PackageManager packageManager, ComponentInfo componentInfo) {
        Assert.isNotMainThread();
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentInfo.getComponentName());
        return componentEnabledSetting != 0 ? componentEnabledSetting == 1 : componentInfo.isEnabled();
    }
}
