package com.android.systemui.keyguard.ui.view.layout.sections;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class ExtensionsKt {
    public static final void removeView(ConstraintLayout constraintLayout, int i) {
        View findViewById = constraintLayout.findViewById(i);
        if (findViewById != null) {
            constraintLayout.removeView(findViewById);
        }
    }
}
