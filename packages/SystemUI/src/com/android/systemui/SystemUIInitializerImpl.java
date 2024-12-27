package com.android.systemui;

import android.content.Context;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dagger.GlobalRootComponent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
