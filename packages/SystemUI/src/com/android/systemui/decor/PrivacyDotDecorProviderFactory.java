package com.android.systemui.decor;

import android.content.res.Resources;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PrivacyDotDecorProviderFactory implements DecorProviderFactory {
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
        return getHasProviders() ? Arrays.asList(new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_left_container, 1, 0, R.layout.privacy_dot_top_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_top_right_container, 1, 2, R.layout.privacy_dot_top_right), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_left_container, 3, 0, R.layout.privacy_dot_bottom_left), new PrivacyDotCornerDecorProviderImpl(R.id.privacy_dot_bottom_right_container, 3, 2, R.layout.privacy_dot_bottom_right)) : EmptyList.INSTANCE;
    }
}
