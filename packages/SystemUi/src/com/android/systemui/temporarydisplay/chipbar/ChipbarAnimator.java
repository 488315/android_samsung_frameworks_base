package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.Iterator;
import kotlin.sequences.SequenceBuilderIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChipbarAnimator {
    public static void forceDisplayView(View view) {
        view.setAlpha(1.0f);
        if (!(view instanceof ViewGroup)) {
            return;
        }
        Iterator it = ConvenienceExtensionsKt.getChildren((ViewGroup) view).iterator();
        while (true) {
            SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
            if (!sequenceBuilderIterator.hasNext()) {
                return;
            } else {
                forceDisplayView((View) sequenceBuilderIterator.next());
            }
        }
    }
}
