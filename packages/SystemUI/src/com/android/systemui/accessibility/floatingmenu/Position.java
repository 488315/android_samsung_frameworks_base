package com.android.systemui.accessibility.floatingmenu;

import android.text.TextUtils;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Position {
    public static final TextUtils.SimpleStringSplitter sStringCommaSplitter = new TextUtils.SimpleStringSplitter(',');
    public float mPercentageX;
    public float mPercentageY;

    public Position(float f, float f2) {
        this.mPercentageX = f;
        this.mPercentageY = f2;
    }

    public static Position fromString(String str) {
        TextUtils.SimpleStringSplitter simpleStringSplitter = sStringCommaSplitter;
        simpleStringSplitter.setString(str);
        if (simpleStringSplitter.hasNext()) {
            return new Position(Float.parseFloat(simpleStringSplitter.next()), Float.parseFloat(simpleStringSplitter.next()));
        }
        throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid Position string: ", str));
    }

    public final String toString() {
        return this.mPercentageX + ", " + this.mPercentageY;
    }
}
