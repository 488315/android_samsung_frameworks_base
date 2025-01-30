package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.QpRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CapturedBlurContainer extends View {
    public CapturedBlurContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            setVisibility(0);
        }
    }
}
