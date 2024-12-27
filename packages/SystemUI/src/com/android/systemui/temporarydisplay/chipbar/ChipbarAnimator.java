package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.util.ConvenienceExtensionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class ChipbarAnimator {
    public static void forceDisplayView(View view) {
        view.setAlpha(1.0f);
        if (view instanceof ViewGroup) {
            for (View view2 : ConvenienceExtensionsKt.getChildren((ViewGroup) view)) {
                Intrinsics.checkNotNull(view2);
                forceDisplayView(view2);
            }
        }
    }
}
