package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PhonePipKeepClearAlgorithm;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePhonePipKeepClearAlgorithmFactory implements Provider {
    public final Provider contextProvider;

    public WMShellBaseModule_ProvidePhonePipKeepClearAlgorithmFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PhonePipKeepClearAlgorithm providePhonePipKeepClearAlgorithm(Context context) {
        return new PhonePipKeepClearAlgorithm(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhonePipKeepClearAlgorithm((Context) this.contextProvider.get());
    }
}
