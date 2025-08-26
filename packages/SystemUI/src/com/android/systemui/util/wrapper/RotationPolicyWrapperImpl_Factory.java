package com.android.systemui.util.wrapper;

import android.content.Context;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class RotationPolicyWrapperImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider secureSettingsProvider;

    public RotationPolicyWrapperImpl_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.secureSettingsProvider = provider2;
    }

    public static RotationPolicyWrapperImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new RotationPolicyWrapperImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static RotationPolicyWrapperImpl newInstance(Context context, SecureSettings secureSettings) {
        return new RotationPolicyWrapperImpl(context, secureSettings);
    }

    public static RotationPolicyWrapperImpl_Factory create(Provider provider, Provider provider2) {
        return new RotationPolicyWrapperImpl_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public RotationPolicyWrapperImpl get() {
        return newInstance((Context) this.contextProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
    }
}
