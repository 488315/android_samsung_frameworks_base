package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.util.ConvenienceExtensionsKt;

/* loaded from: classes3.dex */
public class ChipbarAnimator {
    public static void forceDisplayView(View view) {
        view.setAlpha(1.0f);
        if (view instanceof ViewGroup) {
            for (View view2 : ConvenienceExtensionsKt.getChildren((ViewGroup) view)) {
                view2.getClass();
                forceDisplayView(view2);
            }
        }
    }
}
