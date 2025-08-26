package com.android.wm.shell.desktopmode.common;

import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class ToggleTaskSizeUtilsKt$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        int[] iArr = new int[ToggleTaskSizeInteraction.AmbiguousSource.values().length];
        try {
            iArr[ToggleTaskSizeInteraction.AmbiguousSource.HEADER_BUTTON.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr[ToggleTaskSizeInteraction.AmbiguousSource.MAXIMIZE_MENU.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr[ToggleTaskSizeInteraction.AmbiguousSource.DOUBLE_TAP.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        $EnumSwitchMapping$0 = iArr;
    }
}
