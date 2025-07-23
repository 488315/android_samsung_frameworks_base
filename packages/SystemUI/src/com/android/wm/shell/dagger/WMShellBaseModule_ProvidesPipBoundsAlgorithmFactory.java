package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PhonePipKeepClearAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.SizeSpecSource;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidesPipBoundsAlgorithmFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipKeepClearAlgorithmProvider;
    public final Provider pipSnapAlgorithmProvider;
    public final Provider sizeSpecSourceProvider;

    public WMShellBaseModule_ProvidesPipBoundsAlgorithmFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipSnapAlgorithmProvider = provider3;
        this.pipKeepClearAlgorithmProvider = provider4;
        this.pipDisplayLayoutStateProvider = provider5;
        this.sizeSpecSourceProvider = provider6;
    }

    public static PipBoundsAlgorithm providesPipBoundsAlgorithm(Context context, PipBoundsState pipBoundsState, PipSnapAlgorithm pipSnapAlgorithm, PhonePipKeepClearAlgorithm phonePipKeepClearAlgorithm, PipDisplayLayoutState pipDisplayLayoutState, SizeSpecSource sizeSpecSource) {
        return new PipBoundsAlgorithm(context, pipBoundsState, pipSnapAlgorithm, phonePipKeepClearAlgorithm, pipDisplayLayoutState, sizeSpecSource);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipBoundsAlgorithm((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipSnapAlgorithm) this.pipSnapAlgorithmProvider.get(), (PhonePipKeepClearAlgorithm) this.pipKeepClearAlgorithmProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (SizeSpecSource) this.sizeSpecSourceProvider.get());
    }
}
