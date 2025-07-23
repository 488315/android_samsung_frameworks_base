package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.pip2.phone.PipScheduler;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipSchedulerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDesktopStateProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider splitScreenControllerOptionalProvider;

    public Pip2Module_ProvidePipSchedulerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.pipTransitionStateProvider = provider4;
        this.splitScreenControllerOptionalProvider = provider5;
        this.pipDesktopStateProvider = provider6;
    }

    public static PipScheduler providePipScheduler(Context context, PipBoundsState pipBoundsState, ShellExecutor shellExecutor, PipTransitionState pipTransitionState, Optional optional, PipDesktopState pipDesktopState) {
        return new PipScheduler(context, pipBoundsState, shellExecutor, pipTransitionState, optional, pipDesktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipScheduler((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (Optional) this.splitScreenControllerOptionalProvider.get(), (PipDesktopState) this.pipDesktopStateProvider.get());
    }
}
