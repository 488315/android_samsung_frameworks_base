package com.android.systemui.qs.ui.compose;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes2.dex */
public abstract class BorderOnFocusKt {
    /* renamed from: borderOnFocus-POIbLQ4$default, reason: not valid java name */
    public static Modifier m2935borderOnFocusPOIbLQ4$default(Modifier modifier, long j, CornerSize cornerSize) {
        Dp.Companion companion = Dp.Companion;
        return modifier.then(new BorderOnFocusElement(j, cornerSize, 3, 2, null));
    }
}
