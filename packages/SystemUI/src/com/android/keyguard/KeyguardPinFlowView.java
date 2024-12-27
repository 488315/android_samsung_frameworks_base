package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;

public final class KeyguardPinFlowView extends Flow {
    public KeyguardPinFlowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void applyLayoutFeatures(ConstraintLayout constraintLayout) {
    }
}
