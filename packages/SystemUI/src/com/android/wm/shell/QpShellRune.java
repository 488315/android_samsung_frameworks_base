package com.android.wm.shell;

import android.os.SemSystemProperties;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class QpShellRune {
    public static final boolean NOTI_BUBBLE_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT;
    public static final boolean NOTI_BUBBLE_STYLE_TABLET = SemSystemProperties.get("ro.build.characteristics").contains("tablet");

    static {
        NOTI_BUBBLE_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") && CoreRune.FW_SET_DEFAULT_CUTOUT_POLICY_TO_ALWAYS;
    }
}
