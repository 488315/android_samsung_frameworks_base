package com.android.systemui.accessibility.floatingmenu;

import android.text.TextUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
