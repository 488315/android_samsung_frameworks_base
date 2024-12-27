package com.android.systemui.volume.panel.component.anc.ui.composable;

import android.view.View;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class OnWidthChangedLayoutListener implements View.OnLayoutChangeListener {
    public final Function1 widthChanged;

    public OnWidthChangedLayoutListener(Function1 function1) {
        this.widthChanged = function1;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = i3 - i;
        if (i7 - i5 != i9) {
            this.widthChanged.invoke(Integer.valueOf(i9));
        }
    }
}
