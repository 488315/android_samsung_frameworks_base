package com.android.systemui;

import android.content.Context;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dagger.GlobalRootComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
