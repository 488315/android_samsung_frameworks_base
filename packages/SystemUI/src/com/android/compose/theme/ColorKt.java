package com.android.compose.theme;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ColorKt {
    public static final long colorAttr(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.theme.colorAttr (Color.kt:29)");
        }
        TypedArray obtainStyledAttributes = ((Context) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalContext)).obtainStyledAttributes(new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        long Color = androidx.compose.ui.graphics.ColorKt.Color(color);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return Color;
    }
}
