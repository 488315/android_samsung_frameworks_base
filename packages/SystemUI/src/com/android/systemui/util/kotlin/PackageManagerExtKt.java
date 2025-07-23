package com.android.systemui.util.kotlin;

import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import com.android.systemui.util.Assert;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PackageManagerExtKt {
    public static final boolean isComponentActuallyEnabled(PackageManager packageManager, ComponentInfo componentInfo) {
        Assert.isNotMainThread();
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentInfo.getComponentName());
        return componentEnabledSetting != 0 ? componentEnabledSetting == 1 : componentInfo.isEnabled();
    }
}
