package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ExtensionsKt {
    public static final void removeView(ConstraintLayout constraintLayout, int i) {
        View findViewById = constraintLayout.findViewById(i);
        if (findViewById != null) {
            constraintLayout.removeView(findViewById);
        }
    }
}
