package com.android.systemui.navigationbar.util;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

/* loaded from: classes2.dex */
public final class NavigationBarLargeScreenUtil {
    static {
        new NavigationBarLargeScreenUtil();
    }

    private NavigationBarLargeScreenUtil() {
    }

    public static final boolean isLargeScreen(Context context) {
        if (DeviceType.isTablet()) {
            return true;
        }
        return BasicRune.BASIC_FOLDABLE_TYPE_FOLD && !DeviceState.isSubDisplay(context);
    }
}
