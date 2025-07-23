package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideNaturalSwitchingDropTargetControllerFactory implements Provider {
    public final Provider bgExecutorProvider;
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider mainHandlerProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideNaturalSwitchingDropTargetControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.bgExecutorProvider = provider3;
        this.shellTaskOrganizerProvider = provider4;
        this.displayControllerProvider = provider5;
        this.transitionsProvider = provider6;
        this.syncQueueProvider = provider7;
    }

    public static NaturalSwitchingDropTargetController provideNaturalSwitchingDropTargetController(Context context, ShellTaskOrganizer shellTaskOrganizer, Handler handler, ShellExecutor shellExecutor, DisplayController displayController, Transitions transitions, SyncTransactionQueue syncTransactionQueue) {
        return new NaturalSwitchingDropTargetController(context, shellTaskOrganizer, handler, shellExecutor, displayController, transitions, syncTransactionQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NaturalSwitchingDropTargetController((Context) this.contextProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShellExecutor) this.bgExecutorProvider.get(), (DisplayController) this.displayControllerProvider.get(), (Transitions) this.transitionsProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get());
    }
}
