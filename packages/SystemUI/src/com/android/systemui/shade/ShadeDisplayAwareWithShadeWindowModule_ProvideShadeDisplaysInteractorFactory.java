package com.android.systemui.shade;

import com.android.systemui.CoreStartable;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareWithShadeWindowModule_ProvideShadeDisplaysInteractorFactory implements Provider {
    public final Provider implProvider;

    public ShadeDisplayAwareWithShadeWindowModule_ProvideShadeDisplaysInteractorFactory(Provider provider) {
        this.implProvider = provider;
    }

    public static CoreStartable provideShadeDisplaysInteractor(javax.inject.Provider provider) {
        ShadeDisplayAwareWithShadeWindowModule.INSTANCE.getClass();
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            Object obj = provider.get();
            obj.getClass();
            return (CoreStartable) obj;
        }
        CoreStartable.Nop nop = CoreStartable.NOP;
        nop.getClass();
        return nop;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShadeDisplaysInteractor(this.implProvider);
    }
}
