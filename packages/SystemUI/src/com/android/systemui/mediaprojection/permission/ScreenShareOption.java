package com.android.systemui.mediaprojection.permission;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
