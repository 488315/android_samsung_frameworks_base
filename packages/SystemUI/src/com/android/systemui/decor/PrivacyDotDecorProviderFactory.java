package com.android.systemui.decor;

import android.content.res.Resources;
import com.android.systemui.R;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;

public class PrivacyDotDecorProviderFactory extends DecorProviderFactory {
    public final Resources res;

    public PrivacyDotDecorProviderFactory(Resources resources) {
        this.res = resources;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public boolean getHasProviders() {
        return this.res.getBoolean(R.bool.config_enablePrivacyDot);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public List getProviders() {
        return getHasProviders() ? CollectionsKt__CollectionsKt.listOf(new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_left_container, 1, 0, R.layout.privacy_dot_top_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_right_container, 1, 2, R.layout.privacy_dot_top_right), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_left_container, 3, 0, R.layout.privacy_dot_bottom_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_right_container, 3, 2, R.layout.privacy_dot_bottom_right)) : EmptyList.INSTANCE;
    }
}
