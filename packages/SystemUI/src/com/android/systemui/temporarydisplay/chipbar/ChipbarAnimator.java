package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.util.ConvenienceExtensionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
