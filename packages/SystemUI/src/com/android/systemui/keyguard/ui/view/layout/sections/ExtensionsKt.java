package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class ExtensionsKt {
    public static final void removeView(ConstraintLayout constraintLayout, int i) {
        View findViewById = constraintLayout.findViewById(i);
        if (findViewById != null) {
            constraintLayout.removeView(findViewById);
        }
    }
}
