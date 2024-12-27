package com.android.systemui;

import android.util.MathUtils;
import android.util.TypedValue;
import android.widget.TextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
