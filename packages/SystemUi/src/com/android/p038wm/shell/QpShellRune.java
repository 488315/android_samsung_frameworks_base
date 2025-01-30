package com.android.p038wm.shell;

import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.util.SemViewUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QpShellRune {
    public static final boolean NOTI_BUBBLE_STYLE_FLIP;
    public static final boolean NOTI_BUBBLE_STYLE_TABLET;

    static {
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        NOTI_BUBBLE_STYLE_TABLET = SemViewUtils.isTablet();
        SystemProperties.get("ro.product.name", "").startsWith("gthh");
        NOTI_BUBBLE_STYLE_FLIP = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("WATCHFACE") && SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN");
    }
}
