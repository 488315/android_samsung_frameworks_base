package com.android.systemui.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ConvenienceExtensionsKt {
    public static final Rect getBoundsOnScreen(View view) {
        Rect rect = new Rect();
        view.getBoundsOnScreen(rect);
        return rect;
    }

    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 getChildren(ViewGroup viewGroup) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$children$1(viewGroup, null));
    }
}
