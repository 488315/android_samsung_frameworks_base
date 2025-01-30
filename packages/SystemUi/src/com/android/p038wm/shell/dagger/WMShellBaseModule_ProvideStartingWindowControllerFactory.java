package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.startingsurface.StartingWindowController;
import com.android.p038wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideStartingWindowControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider iconProvider;
    public final Provider poolProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splashScreenExecutorProvider;
    public final Provider startingWindowTypeAlgorithmProvider;

    public WMShellBaseModule_ProvideStartingWindowControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellControllerProvider = provider3;
        this.shellTaskOrganizerProvider = provider4;
        this.splashScreenExecutorProvider = provider5;
        this.startingWindowTypeAlgorithmProvider = provider6;
        this.iconProvider = provider7;
        this.poolProvider = provider8;
    }

    public static StartingWindowController provideStartingWindowController(Context context, ShellInit shellInit, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, StartingWindowTypeAlgorithm startingWindowTypeAlgorithm, IconProvider iconProvider, TransactionPool transactionPool) {
        return new StartingWindowController(context, shellInit, shellController, shellTaskOrganizer, shellExecutor, startingWindowTypeAlgorithm, iconProvider, transactionPool);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStartingWindowController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (ShellExecutor) this.splashScreenExecutorProvider.get(), (StartingWindowTypeAlgorithm) this.startingWindowTypeAlgorithmProvider.get(), (IconProvider) this.iconProvider.get(), (TransactionPool) this.poolProvider.get());
    }
}
