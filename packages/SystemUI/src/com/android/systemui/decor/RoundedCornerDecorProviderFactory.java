package com.android.systemui.decor;

import com.android.systemui.R;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RoundedCornerDecorProviderFactory extends DecorProviderFactory {
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
        return (hasTop && hasBottom) ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : hasTop ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate)) : hasBottom ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : EmptyList.INSTANCE;
    }
}
