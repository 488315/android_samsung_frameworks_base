package androidx.compose.foundation.lazy.layout;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 lazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 = LazyLayoutMeasuredItemKt.LazyLayoutMeasuredItemIndexComparator;
        return Intrinsics.compare(((LazyLayoutMeasuredItem) obj).getIndex(), ((LazyLayoutMeasuredItem) obj2).getIndex());
    }
}
