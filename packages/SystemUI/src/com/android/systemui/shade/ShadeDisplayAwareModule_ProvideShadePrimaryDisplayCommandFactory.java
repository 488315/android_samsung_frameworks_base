package com.android.systemui.shade;

import com.android.systemui.CoreStartable;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadePrimaryDisplayCommandFactory implements Provider {
    public final Provider implProvider;

    public ShadeDisplayAwareModule_ProvideShadePrimaryDisplayCommandFactory(Provider provider) {
        this.implProvider = provider;
    }

    public static CoreStartable provideShadePrimaryDisplayCommand(javax.inject.Provider provider) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
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
        return provideShadePrimaryDisplayCommand(this.implProvider);
    }
}
