package com.android.compose.theme;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

/* loaded from: classes.dex */
public abstract class ColorKt {
    public static final long colorAttr(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.theme.colorAttr (Color.kt:29)");
        }
        TypedArray typedArrayObtainStyledAttributes = ((Context) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalContext)).obtainStyledAttributes(new int[]{i});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        long jColor = androidx.compose.ui.graphics.ColorKt.Color(color);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return jColor;
    }
}
