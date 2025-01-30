package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.pip.phone.PhonePipKeepClearAlgorithm;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory(Provider provider) {
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
