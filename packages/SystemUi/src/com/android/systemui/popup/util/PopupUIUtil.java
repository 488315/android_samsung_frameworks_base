package com.android.systemui.popup.util;

import android.os.SystemProperties;
import com.android.systemui.BasicRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PopupUIUtil {
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_TYPE;
    public static final boolean SIM_CARD_TRAY_STYLE_FOLD_TYPE;

    static {
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        SIM_CARD_TRAY_STYLE_FOLD_TYPE = z && SystemProperties.get("ro.product.name", "").startsWith("q6q") && !(z && SystemProperties.get("ro.product.name", "").startsWith("q6aq"));
        SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL = SystemProperties.get("ro.product.name", "").startsWith("b6qzcx") || SystemProperties.get("ro.product.name", "").startsWith("b6qzhx") || SystemProperties.get("ro.product.name", "").startsWith("b6qctcx");
        SIM_CARD_TRAY_STYLE_FLIP_TYPE = BasicRune.BASIC_FOLDABLE_TYPE_FLIP && SystemProperties.get("ro.product.name", "").startsWith("b6");
    }
}
