package com.android.settingslib;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public class SignalIcon$IconGroup {
    public int[] activityIcons;
    public final int[] contentDesc;
    public final int discContentDesc;
    public final String name;
    public final int qsDiscState;
    public final int[][] qsIcons;
    public final int qsNullState;
    public final int sbDiscState;
    public final int[][] sbIcons;
    public final int sbNullState;

    public SignalIcon$IconGroup(String str, int[][] iArr, int[][] iArr2, int[] iArr3, int i, int i2, int i3, int i4, int i5, int[] iArr4) {
        this.name = str;
        this.sbIcons = iArr;
        this.qsIcons = iArr2;
        this.contentDesc = iArr3;
        this.sbNullState = i;
        this.qsNullState = i2;
        this.sbDiscState = i3;
        this.qsDiscState = i4;
        this.discContentDesc = i5;
        this.activityIcons = iArr4;
    }

    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("IconGroup("), this.name, ")");
    }
}
