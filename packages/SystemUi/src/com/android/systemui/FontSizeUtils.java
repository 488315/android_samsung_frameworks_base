package com.android.systemui;

import android.util.MathUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontSizeUtils {
    private FontSizeUtils() {
    }

    public static void updateFontSize(View view, int i, int i2) {
        updateFontSize(i2, (TextView) view.findViewById(i));
    }

    public static void updateFontSize(int i, TextView textView) {
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
