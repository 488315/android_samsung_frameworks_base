package com.android.systemui.decor;

import android.content.res.Resources;
import com.android.systemui.R;
import java.util.Collections;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverRoundedCornerDecorProviderFactory extends DecorProviderFactory {
    public final boolean hasProviders;

    public CoverRoundedCornerDecorProviderFactory(Resources resources) {
        this.hasProviders = resources.getBoolean(R.bool.config_enableCoverScreenRoundedCorner);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        return this.hasProviders;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        return this.hasProviders ? Collections.singletonList(new CoverRoundedCornerDecorProviderImpl(R.id.rounded_corner_cover)) : EmptyList.INSTANCE;
    }
}
