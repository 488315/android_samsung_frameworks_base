package com.android.systemui.util.wrapper;

import android.content.Context;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RotationPolicyWrapperImpl_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider secureSettingsProvider;

    public RotationPolicyWrapperImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.secureSettingsProvider = provider2;
    }

    public static RotationPolicyWrapperImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new RotationPolicyWrapperImpl_Factory(provider, provider2);
    }

    public static RotationPolicyWrapperImpl newInstance(Context context, SecureSettings secureSettings) {
        return new RotationPolicyWrapperImpl(context, secureSettings);
    }

    @Override // javax.inject.Provider
    public RotationPolicyWrapperImpl get() {
        return newInstance((Context) this.contextProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
    }
}
