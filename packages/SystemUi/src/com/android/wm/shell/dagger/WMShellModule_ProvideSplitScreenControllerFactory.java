package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideSplitScreenControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayImeControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider dragAndDropControllerProvider;
    public final Provider iconProvider;
    public final Provider mainExecutorProvider;
    public final Provider recentTasksProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;
    public final Provider transactionPoolProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideSplitScreenControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.shellControllerProvider = provider4;
        this.shellTaskOrganizerProvider = provider5;
        this.syncQueueProvider = provider6;
        this.rootTaskDisplayAreaOrganizerProvider = provider7;
        this.displayControllerProvider = provider8;
        this.displayImeControllerProvider = provider9;
        this.displayInsetsControllerProvider = provider10;
        this.dragAndDropControllerProvider = provider11;
        this.transitionsProvider = provider12;
        this.transactionPoolProvider = provider13;
        this.iconProvider = provider14;
        this.recentTasksProvider = provider15;
        this.mainExecutorProvider = provider16;
    }

    public static SplitScreenController provideSplitScreenController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Optional optional, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, Optional optional2, ShellExecutor shellExecutor) {
        return new SplitScreenController(context, shellInit, shellCommandHandler, shellController, shellTaskOrganizer, syncTransactionQueue, rootTaskDisplayAreaOrganizer, displayController, displayImeController, displayInsetsController, optional, transitions, transactionPool, iconProvider, optional2, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSplitScreenController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DisplayImeController) this.displayImeControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (Optional) this.dragAndDropControllerProvider.get(), (Transitions) this.transitionsProvider.get(), (TransactionPool) this.transactionPoolProvider.get(), (IconProvider) this.iconProvider.get(), (Optional) this.recentTasksProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
