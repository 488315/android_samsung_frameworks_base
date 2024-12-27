package com.android.systemui.util;

import android.graphics.Rect;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewGroup;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ConvenienceExtensionsKt {
    public static final Rect getBoundsOnScreen(View view) {
        Rect rect = new Rect();
        view.getBoundsOnScreen(rect);
        return rect;
    }

    public static final Sequence getChildren(ViewGroup viewGroup) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$children$1(viewGroup, null));
    }

    public static final void indentIfPossible(PrintWriter printWriter, Function1 function1) {
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        function1.invoke(printWriter);
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public static final <T> Sequence takeUntil(Sequence sequence, Function1 function1) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$takeUntil$1(sequence, function1, null));
    }
}
