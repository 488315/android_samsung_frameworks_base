package com.android.systemui.decor;

import com.android.systemui.R;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RoundedCornerDecorProviderFactory implements DecorProviderFactory {
    public final RoundedCornerResDelegate roundedCornerResDelegate;

    public RoundedCornerDecorProviderFactory(RoundedCornerResDelegate roundedCornerResDelegate) {
        this.roundedCornerResDelegate = roundedCornerResDelegate;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        return roundedCornerResDelegate.getHasTop() || roundedCornerResDelegate.getHasBottom();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        boolean hasTop = roundedCornerResDelegate.getHasTop();
        boolean hasBottom = roundedCornerResDelegate.getHasBottom();
        return (hasTop && hasBottom) ? Arrays.asList(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : hasTop ? Arrays.asList(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate)) : hasBottom ? Arrays.asList(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : EmptyList.INSTANCE;
    }
}
