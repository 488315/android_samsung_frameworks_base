package com.android.systemui.decor;

import com.android.systemui.R;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RoundedCornerDecorProviderFactory extends DecorProviderFactory {
    public final RoundedCornerResDelegate roundedCornerResDelegate;

    public RoundedCornerDecorProviderFactory(RoundedCornerResDelegate roundedCornerResDelegate) {
        this.roundedCornerResDelegate = roundedCornerResDelegate;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        return roundedCornerResDelegate.hasTop || roundedCornerResDelegate.hasBottom;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        boolean z = roundedCornerResDelegate.hasTop;
        boolean z2 = roundedCornerResDelegate.hasBottom;
        return (z && z2) ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : z ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_left, 1, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_top_right, 1, 2, roundedCornerResDelegate)) : z2 ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_left, 3, 0, roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R.id.rounded_corner_bottom_right, 3, 2, roundedCornerResDelegate)) : EmptyList.INSTANCE;
    }
}
