package com.android.systemui.mediaprojection.permission;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ScreenShareOption {
    public final int mode;
    public final String spinnerDisabledText;
    public final int spinnerText;
    public final int warningText;

    public ScreenShareOption(int i, int i2, int i3, String str) {
        this.mode = i;
        this.spinnerText = i2;
        this.warningText = i3;
        this.spinnerDisabledText = str;
    }

    public /* synthetic */ ScreenShareOption(int i, int i2, int i3, String str, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i4 & 8) != 0 ? null : str);
    }
}
