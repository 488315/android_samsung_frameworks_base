package androidx.compose.foundation.lazy.layout;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final /* synthetic */ class LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        LazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 lazyLayoutMeasuredItemKt$$ExternalSyntheticLambda0 = LazyLayoutMeasuredItemKt.LazyLayoutMeasuredItemIndexComparator;
        return Intrinsics.compare(((LazyLayoutMeasuredItem) obj).getIndex(), ((LazyLayoutMeasuredItem) obj2).getIndex());
    }
}
