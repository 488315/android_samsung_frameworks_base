package com.android.systemui.util.kotlin;

import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import com.android.systemui.util.Assert;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PackageManagerExtKt {
    public static final boolean isComponentActuallyEnabled(PackageManager packageManager, ComponentInfo componentInfo) {
        Assert.isNotMainThread();
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentInfo.getComponentName());
        return componentEnabledSetting != 0 ? componentEnabledSetting == 1 : componentInfo.isEnabled();
    }
}
