package com.android.keyguard.dagger;

import android.content.Context;
import android.view.Display;
import dagger.internal.Provider;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardDisplayModule_Companion_GetDisplayContextFactory implements Provider {
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider optionalDisplayProvider;

    public KeyguardDisplayModule_Companion_GetDisplayContextFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.optionalDisplayProvider = provider2;
    }

    public static Context getDisplayContext(Context context, Optional optional) {
        KeyguardDisplayModule.Companion.getClass();
        Display display = (Display) optional.orElse(null);
        if (display == null || context.getDisplayId() == display.getDisplayId()) {
            return context;
        }
        Context createDisplayContext = context.createDisplayContext(display);
        Intrinsics.checkNotNull(createDisplayContext);
        return createDisplayContext;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getDisplayContext((Context) this.contextProvider.get(), (Optional) this.optionalDisplayProvider.get());
    }
}
