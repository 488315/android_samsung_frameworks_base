package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadeDisplayAwareContextFactory implements Provider {
    public final Provider contextProvider;

    public ShadeDisplayAwareModule_ProvideShadeDisplayAwareContextFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static Context provideShadeDisplayAwareContext(Context context) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            context = context.createWindowContext(context.getDisplay(), 2040, null);
            context.setTheme(R.style.Theme_SystemUI);
        }
        context.getClass();
        return context;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShadeDisplayAwareContext((Context) this.contextProvider.get());
    }
}
