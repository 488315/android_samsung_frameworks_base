package com.android.systemui.tv;

import android.content.Context;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.dagger.GlobalRootComponent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvSystemUIInitializer extends SystemUIInitializer {
    public TvSystemUIInitializer(Context context) {
        super(context);
    }

    @Override // com.android.systemui.SystemUIInitializer
    public GlobalRootComponent.Builder getGlobalRootComponentBuilder() {
        return DaggerTvGlobalRootComponent.builder();
    }
}
