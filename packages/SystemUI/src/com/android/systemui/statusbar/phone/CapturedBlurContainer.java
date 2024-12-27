package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.QpRune;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class CapturedBlurContainer extends View {
    public CapturedBlurContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            setVisibility(0);
        }
    }
}
