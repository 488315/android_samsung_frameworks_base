package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PhonePipKeepClearAlgorithm;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
