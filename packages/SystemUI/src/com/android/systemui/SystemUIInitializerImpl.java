package com.android.systemui;

import android.content.Context;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dagger.GlobalRootComponent;

public final class SystemUIInitializerImpl extends SystemUIInitializer {
    public static final int $stable = 0;

    public SystemUIInitializerImpl(Context context) {
        super(context);
    }

    @Override // com.android.systemui.SystemUIInitializer
    public GlobalRootComponent.Builder getGlobalRootComponentBuilder() {
        return DaggerReferenceGlobalRootComponent.builder();
    }
}
