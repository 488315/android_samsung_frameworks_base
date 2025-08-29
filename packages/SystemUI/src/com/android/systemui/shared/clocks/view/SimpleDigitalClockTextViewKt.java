package com.android.systemui.shared.clocks.view;

import android.graphics.Paint;
import android.graphics.Rect;
import com.android.systemui.plugins.clocks.VRectF;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public abstract class SimpleDigitalClockTextViewKt {
    public static final String TAG;
    public static final Rect tempRect;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(SimpleDigitalClockTextView.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
        tempRect = new Rect();
    }

    public static final long access$getTextBounds(Paint paint, CharSequence charSequence) {
        int length = charSequence.length();
        Rect rect = tempRect;
        paint.getTextBounds(charSequence, 0, length, rect);
        return VRectF.m2828constructorimpl(rect);
    }
}
