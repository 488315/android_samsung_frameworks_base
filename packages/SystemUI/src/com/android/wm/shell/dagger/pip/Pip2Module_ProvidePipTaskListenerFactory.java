package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.pip2.phone.PipScheduler;
import com.android.wm.shell.pip2.phone.PipTaskListener;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipTaskListenerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipSchedulerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider shellTaskOrganizerProvider;

    public Pip2Module_ProvidePipTaskListenerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.shellTaskOrganizerProvider = provider2;
        this.pipTransitionStateProvider = provider3;
        this.pipSchedulerProvider = provider4;
        this.pipBoundsStateProvider = provider5;
        this.pipBoundsAlgorithmProvider = provider6;
        this.mainExecutorProvider = provider7;
    }

    public static PipTaskListener providePipTaskListener(Context context, ShellTaskOrganizer shellTaskOrganizer, PipTransitionState pipTransitionState, PipScheduler pipScheduler, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, ShellExecutor shellExecutor) {
        return new PipTaskListener(context, shellTaskOrganizer, pipTransitionState, pipScheduler, pipBoundsState, pipBoundsAlgorithm, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTaskListener((Context) this.contextProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipScheduler) this.pipSchedulerProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
