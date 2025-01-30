package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p038wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideStartingWindowTypeAlgorithmFactory implements Provider {
    public final Provider startingWindowTypeAlgorithmProvider;

    public WMShellBaseModule_ProvideStartingWindowTypeAlgorithmFactory(Provider provider) {
        this.startingWindowTypeAlgorithmProvider = provider;
    }

    public static StartingWindowTypeAlgorithm provideStartingWindowTypeAlgorithm(Optional optional) {
        StartingWindowTypeAlgorithm phoneStartingWindowTypeAlgorithm = optional.isPresent() ? (StartingWindowTypeAlgorithm) optional.get() : new PhoneStartingWindowTypeAlgorithm();
        Preconditions.checkNotNullFromProvides(phoneStartingWindowTypeAlgorithm);
        return phoneStartingWindowTypeAlgorithm;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStartingWindowTypeAlgorithm((Optional) this.startingWindowTypeAlgorithmProvider.get());
    }
}
