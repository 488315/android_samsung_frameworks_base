package com.android.systemui.qs.ui.compose;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BorderOnFocusKt {
    /* renamed from: borderOnFocus-POIbLQ4$default, reason: not valid java name */
    public static Modifier m2920borderOnFocusPOIbLQ4$default(Modifier modifier, long j, CornerSize cornerSize) {
        Dp.Companion companion = Dp.Companion;
        return modifier.then(new BorderOnFocusElement(j, cornerSize, 3, 2, null));
    }
}
