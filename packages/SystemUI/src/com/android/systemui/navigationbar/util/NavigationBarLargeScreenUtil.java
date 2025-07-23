package com.android.systemui.navigationbar.util;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
