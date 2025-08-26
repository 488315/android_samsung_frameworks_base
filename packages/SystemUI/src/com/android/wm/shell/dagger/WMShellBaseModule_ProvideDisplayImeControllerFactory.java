package com.android.wm.shell.dagger;

import android.view.IWindowManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.sysui.ShellInit;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideDisplayImeControllerFactory implements Provider {
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellInitProvider;
    public final Provider transactionPoolProvider;
    public final Provider transitionsLazyProvider;
    public final Provider wmServiceProvider;

    public WMShellBaseModule_ProvideDisplayImeControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.wmServiceProvider = provider;
        this.shellInitProvider = provider2;
        this.displayControllerProvider = provider3;
        this.displayInsetsControllerProvider = provider4;
        this.transactionPoolProvider = provider5;
        this.mainExecutorProvider = provider6;
        this.transitionsLazyProvider = provider7;
    }

    public static DisplayImeController provideDisplayImeController(IWindowManager iWindowManager, ShellInit shellInit, DisplayController displayController, DisplayInsetsController displayInsetsController, TransactionPool transactionPool, ShellExecutor shellExecutor, Lazy lazy) {
        return new DisplayImeController(iWindowManager, shellInit, displayController, displayInsetsController, transactionPool, shellExecutor, lazy);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayImeController((IWindowManager) this.wmServiceProvider.get(), (ShellInit) this.shellInitProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (TransactionPool) this.transactionPoolProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), DoubleCheck.lazy(this.transitionsLazyProvider));
    }
}
