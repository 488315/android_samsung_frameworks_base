package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes2.dex */
public abstract class ExtensionsKt {
    public static final void removeView(ConstraintLayout constraintLayout, int i) {
        View viewFindViewById = constraintLayout.findViewById(i);
        if (viewFindViewById != null) {
            constraintLayout.removeView(viewFindViewById);
        }
    }
}
