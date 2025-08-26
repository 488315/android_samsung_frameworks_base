package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.QpRune;

/* loaded from: classes3.dex */
public class CapturedBlurContainer extends View {
    public CapturedBlurContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            setVisibility(0);
        }
    }
}
