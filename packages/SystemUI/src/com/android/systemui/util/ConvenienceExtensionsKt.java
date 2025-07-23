package com.android.systemui.util;

import android.graphics.Rect;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewGroup;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt__GroupingJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConvenienceExtensionsKt {
    public static final <T> boolean containsExactly(Collection<? extends T> collection, T... tArr) {
        return Intrinsics.areEqual(eachCountMap(collection), eachCountMap(Arrays.asList(tArr)));
    }

    public static final <T> Map<T, Integer> eachCountMap(Collection<? extends T> collection) {
        final Collection<? extends T> collection2 = collection;
        return GroupingKt__GroupingJVMKt.eachCount(new Grouping() { // from class: com.android.systemui.util.ConvenienceExtensionsKt$eachCountMap$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Iterator<Object> sourceIterator() {
                return collection2.iterator();
            }

            @Override // kotlin.collections.Grouping
            public Object keyOf(Object obj) {
                return obj;
            }
        });
    }

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
        function1.mo779invoke(printWriter);
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public static final <T> Sequence takeUntil(Sequence sequence, Function1 function1) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$takeUntil$1(sequence, function1, null));
    }
}
