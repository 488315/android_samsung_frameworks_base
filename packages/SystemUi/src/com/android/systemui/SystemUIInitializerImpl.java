package com.android.systemui;

import android.content.Context;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dagger.GlobalRootComponent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIInitializerImpl extends SystemUIInitializer {
    public SystemUIInitializerImpl(Context context) {
        super(context);
    }

    @Override // com.android.systemui.SystemUIInitializer
    public GlobalRootComponent.Builder getGlobalRootComponentBuilder() {
        return DaggerReferenceGlobalRootComponent.builder();
    }
}
