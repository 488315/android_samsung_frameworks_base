package com.android.keyguard.dagger;

import android.content.Context;
import android.view.Display;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardDisplayModule_Companion_GetDisplayContextFactory implements Provider {
    public final Provider contextProvider;
    public final Provider optionalDisplayProvider;

    public KeyguardDisplayModule_Companion_GetDisplayContextFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.optionalDisplayProvider = provider2;
    }

    public static Context getDisplayContext(Context context, Optional optional) {
        KeyguardDisplayModule.Companion.getClass();
        Display display = (Display) optional.orElse(null);
        if (display != null && context.getDisplayId() != display.getDisplayId()) {
            context = context.createDisplayContext(display);
            context.getClass();
        }
        context.getClass();
        return context;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getDisplayContext((Context) this.contextProvider.get(), (Optional) this.optionalDisplayProvider.get());
    }
}
