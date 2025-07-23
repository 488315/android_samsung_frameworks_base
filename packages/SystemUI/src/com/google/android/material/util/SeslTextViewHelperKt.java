package com.google.android.material.util;

import android.widget.TextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SeslTextViewHelperKt {
    public static final void checkMaxFontScale(TextView textView, int i, MaxFontScaleRatio maxFontScaleRatio) {
        float f = textView.getResources().getConfiguration().fontScale;
        float ratio = maxFontScaleRatio.getRatio();
        if (f > ratio) {
            f = ratio;
        }
        textView.setTextSize(0, textView.getResources().getDimensionPixelSize(i) * f);
    }
}
