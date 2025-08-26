package com.android.systemui.shade;

import android.content.Context;
import android.window.WindowContext;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadeDisplayAwareWindowContextFactory implements Provider {
    public final Provider contextProvider;

    public ShadeDisplayAwareModule_ProvideShadeDisplayAwareWindowContextFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static WindowContext provideShadeDisplayAwareWindowContext(Context context) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        WindowContext windowContext = context instanceof WindowContext ? (WindowContext) context : null;
        if (windowContext != null) {
            return windowContext;
        }
        throw new IllegalStateException("ShadeDisplayAware context must be a window context to allow window reparenting.");
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShadeDisplayAwareWindowContext((Context) this.contextProvider.get());
    }
}
