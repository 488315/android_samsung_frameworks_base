package com.android.systemui.controls.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsCard extends RelativeLayout {
    public ControlsCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
        getLayoutParams().height = getWidth();
    }

    public ControlsCard(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ControlsCard(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
