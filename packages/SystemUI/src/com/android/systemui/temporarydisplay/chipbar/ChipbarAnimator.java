package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.util.ConvenienceExtensionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
