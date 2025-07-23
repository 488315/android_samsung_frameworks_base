package com.android.systemui.shade;

import com.android.systemui.CoreStartable;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
