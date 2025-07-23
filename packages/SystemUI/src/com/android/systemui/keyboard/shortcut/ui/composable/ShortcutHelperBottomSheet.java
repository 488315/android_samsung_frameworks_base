package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperBottomSheet {
    public static final float DefaultWidth;
    public static final ShortcutHelperBottomSheet INSTANCE = new ShortcutHelperBottomSheet();
    public static final float LargeScreenWidthLandscape;
    public static final float LargeScreenWidthPortrait;

    static {
        Dp.Companion companion = Dp.Companion;
        DefaultWidth = 412;
        LargeScreenWidthPortrait = KnoxEnterpriseLicenseManager.ERROR_LICENSE_QUANTITY_EXHAUSTED_ON_AUTO_RELEASE;
        LargeScreenWidthLandscape = 960;
    }

    private ShortcutHelperBottomSheet() {
    }
}
