package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItem;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider;
import androidx.compose.ui.unit.Constraints;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridMeasuredItemProvider implements LazyLayoutMeasuredItemProvider<LazyGridMeasuredItem> {
    public final int defaultMainAxisSpacing;
    public final LazyGridItemProvider itemProvider;
    public final LazyLayoutMeasureScope measureScope;

    public LazyGridMeasuredItemProvider(LazyGridItemProvider lazyGridItemProvider, LazyLayoutMeasureScope lazyLayoutMeasureScope, int i) {
        this.itemProvider = lazyGridItemProvider;
        this.measureScope = lazyLayoutMeasureScope;
        this.defaultMainAxisSpacing = i;
    }

    /* renamed from: createItem-O3s9Psw */
    public abstract LazyGridMeasuredItem mo159createItemO3s9Psw(int i, Object obj, Object obj2, int i2, int i3, List list, long j, int i4, int i5);

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider
    /* renamed from: getAndMeasure--hBUhpc */
    public final LazyLayoutMeasuredItem mo156getAndMeasurehBUhpc(int i, int i2, int i3, long j) {
        return m162getAndMeasurem8Kt_7k(i, j, i2, i3, this.defaultMainAxisSpacing);
    }

    /* renamed from: getAndMeasure-m8Kt_7k, reason: not valid java name */
    public final LazyGridMeasuredItem m162getAndMeasurem8Kt_7k(int i, long j, int i2, int i3, int i4) {
        int m822getMinHeightimpl;
        LazyGridItemProviderImpl lazyGridItemProviderImpl = (LazyGridItemProviderImpl) this.itemProvider;
        Object key = lazyGridItemProviderImpl.getKey(i);
        Object contentType = lazyGridItemProviderImpl.intervalContent.getContentType(i);
        List m169measure0kLqBqw = ((LazyLayoutMeasureScopeImpl) this.measureScope).m169measure0kLqBqw(i, j);
        if (Constraints.m819getHasFixedWidthimpl(j)) {
            m822getMinHeightimpl = Constraints.m823getMinWidthimpl(j);
        } else {
            if (!Constraints.m818getHasFixedHeightimpl(j)) {
                InlineClassHelperKt.throwIllegalArgumentException("does not have fixed height");
            }
            m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        }
        return mo159createItemO3s9Psw(i, key, contentType, m822getMinHeightimpl, i4, m169measure0kLqBqw, j, i2, i3);
    }
}
