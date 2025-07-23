package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.SizeSpecSource;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePipBoundsStateFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider sizeSpecSourceProvider;

    public WMShellBaseModule_ProvidePipBoundsStateFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.sizeSpecSourceProvider = provider2;
        this.pipDisplayLayoutStateProvider = provider3;
    }

    public static PipBoundsState providePipBoundsState(Context context, SizeSpecSource sizeSpecSource, PipDisplayLayoutState pipDisplayLayoutState) {
        return new PipBoundsState(context, sizeSpecSource, pipDisplayLayoutState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipBoundsState((Context) this.contextProvider.get(), (SizeSpecSource) this.sizeSpecSourceProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get());
    }
}
