package com.android.systemui.accessibility.floatingmenu;

import android.text.TextUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class Position {
    public static final TextUtils.SimpleStringSplitter sStringCommaSplitter = new TextUtils.SimpleStringSplitter(',');
    public float mPercentageX;
    public float mPercentageY;

    public Position(float f, float f2) {
        this.mPercentageX = f;
        this.mPercentageY = f2;
    }

    public final String toString() {
        return this.mPercentageX + ", " + this.mPercentageY;
    }
}
