package com.android.systemui.dreams;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DreamOverlayContainerView extends ConstraintLayout {
    public DreamOverlayContainerView(Context context) {
        this(context, null);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DreamOverlayContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
