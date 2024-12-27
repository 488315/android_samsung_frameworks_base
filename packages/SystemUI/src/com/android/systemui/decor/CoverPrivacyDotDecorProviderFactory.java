package com.android.systemui.decor;

import android.content.res.Resources;
import com.android.systemui.R;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverPrivacyDotDecorProviderFactory extends PrivacyDotDecorProviderFactory {
    public final Resources res;

    public CoverPrivacyDotDecorProviderFactory(Resources resources) {
        super(resources);
        this.res = resources;
    }

    @Override // com.android.systemui.decor.PrivacyDotDecorProviderFactory, com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        return this.res.getBoolean(R.bool.config_enableCoverScreenPrivacyDot);
    }

    @Override // com.android.systemui.decor.PrivacyDotDecorProviderFactory, com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        return getHasProviders() ? CollectionsKt__CollectionsKt.listOf(new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_left_container, 1, 0, R.layout.privacy_dot_top_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_right_container, 1, 2, R.layout.privacy_dot_top_right), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_left_container, 1, 0, R.layout.privacy_dot_bottom_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_right_container, 1, 2, R.layout.privacy_dot_bottom_right)) : EmptyList.INSTANCE;
    }
}
