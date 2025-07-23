package androidx.compose.foundation.lazy;

import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyListMeasuredItemProvider implements LazyLayoutMeasuredItemProvider<LazyListMeasuredItem> {
    public final long childConstraints;
    public final LazyListItemProvider itemProvider;
    public final LazyLayoutMeasureScope measureScope;

    public /* synthetic */ LazyListMeasuredItemProvider(long j, boolean z, LazyListItemProvider lazyListItemProvider, LazyLayoutMeasureScope lazyLayoutMeasureScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, z, lazyListItemProvider, lazyLayoutMeasureScope);
    }

    /* renamed from: getAndMeasure-0kLqBqw$default, reason: not valid java name */
    public static LazyListMeasuredItem m155getAndMeasure0kLqBqw$default(LazyListMeasuredItemProvider lazyListMeasuredItemProvider, int i) {
        long j = lazyListMeasuredItemProvider.childConstraints;
        LazyListItemProviderImpl lazyListItemProviderImpl = (LazyListItemProviderImpl) lazyListMeasuredItemProvider.itemProvider;
        return lazyListMeasuredItemProvider.mo150createItemX9ElhV4(i, lazyListItemProviderImpl.getKey(i), lazyListItemProviderImpl.intervalContent.getContentType(i), ((LazyLayoutMeasureScopeImpl) lazyListMeasuredItemProvider.measureScope).m169measure0kLqBqw(i, j), j);
    }

    /* renamed from: createItem-X9ElhV4 */
    public abstract LazyListMeasuredItem mo150createItemX9ElhV4(int i, Object obj, Object obj2, List list, long j);

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider
    /* renamed from: getAndMeasure--hBUhpc, reason: not valid java name */
    public final LazyLayoutMeasuredItem mo156getAndMeasurehBUhpc(int i, int i2, int i3, long j) {
        LazyListItemProviderImpl lazyListItemProviderImpl = (LazyListItemProviderImpl) this.itemProvider;
        return mo150createItemX9ElhV4(i, lazyListItemProviderImpl.getKey(i), lazyListItemProviderImpl.intervalContent.getContentType(i), ((LazyLayoutMeasureScopeImpl) this.measureScope).m169measure0kLqBqw(i, j), j);
    }

    private LazyListMeasuredItemProvider(long j, boolean z, LazyListItemProvider lazyListItemProvider, LazyLayoutMeasureScope lazyLayoutMeasureScope) {
        this.itemProvider = lazyListItemProvider;
        this.measureScope = lazyLayoutMeasureScope;
        this.childConstraints = ConstraintsKt.Constraints$default(0, z ? Constraints.m821getMaxWidthimpl(j) : Integer.MAX_VALUE, 0, z ? Integer.MAX_VALUE : Constraints.m820getMaxHeightimpl(j), 5);
    }
}
