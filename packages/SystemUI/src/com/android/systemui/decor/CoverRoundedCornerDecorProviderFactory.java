package com.android.systemui.decor;

import com.android.systemui.R;
import java.util.Collections;
import java.util.List;
import kotlin.collections.EmptyList;

/* loaded from: classes2.dex */
public final class CoverRoundedCornerDecorProviderFactory implements DecorProviderFactory {
    public final RoundedCornerResDelegate roundedCornerResDelegate;

    public CoverRoundedCornerDecorProviderFactory(RoundedCornerResDelegate roundedCornerResDelegate) {
        this.roundedCornerResDelegate = roundedCornerResDelegate;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        return this.roundedCornerResDelegate.getHasTop();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        return this.roundedCornerResDelegate.getHasTop() ? Collections.singletonList(new CoverRoundedCornerDecorProviderImpl(R.id.rounded_corner_cover)) : EmptyList.INSTANCE;
    }
}
