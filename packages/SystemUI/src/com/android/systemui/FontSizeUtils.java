package com.android.systemui;

import android.util.MathUtils;
import android.util.TypedValue;
import android.widget.TextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontSizeUtils {
    private FontSizeUtils() {
    }

    public static void updateFontSize(TextView textView, int i) {
        if (textView != null) {
            textView.setTextSize(0, textView.getResources().getDimensionPixelSize(i));
        }
    }

    public static void updateFontSize(TextView textView, int i, float f, float f2) {
        if (textView == null) {
            return;
        }
        float f3 = textView.getResources().getConfiguration().fontScale;
        float constrain = MathUtils.constrain(textView.getResources().getConfiguration().fontScale, f, f2);
        TypedValue typedValue = new TypedValue();
        textView.getResources().getValue(i, typedValue, true);
        textView.getPaint().measureText(textView.getText().toString());
        if (typedValue.getComplexUnit() == 1) {
            textView.setTextSize(0, textView.getResources().getDimensionPixelSize(i) * constrain);
        } else if (typedValue.getComplexUnit() == 2) {
            textView.setTextSize(0, (textView.getResources().getDimensionPixelSize(i) / f3) * constrain);
        }
    }
}
