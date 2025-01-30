package com.android.systemui.dagger;

import android.content.Context;
import android.telecom.TelecomManager;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideOptionalTelecomManagerFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvideOptionalTelecomManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static Optional provideOptionalTelecomManager(Context context) {
        Optional ofNullable = Optional.ofNullable((TelecomManager) context.getSystemService(TelecomManager.class));
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideOptionalTelecomManager((Context) this.contextProvider.get());
    }
}
