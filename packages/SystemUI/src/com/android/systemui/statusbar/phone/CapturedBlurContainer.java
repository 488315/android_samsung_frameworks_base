package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.QpRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CapturedBlurContainer extends View {
    public CapturedBlurContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            setVisibility(0);
        }
    }
}
