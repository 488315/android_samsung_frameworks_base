package com.google.android.material.util;

import android.widget.TextView;

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
