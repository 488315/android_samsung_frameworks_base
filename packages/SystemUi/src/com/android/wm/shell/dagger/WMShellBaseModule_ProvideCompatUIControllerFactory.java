package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIConfiguration;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.CompatUIShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideCompatUIControllerFactory implements Provider {
    public final Provider compatUIConfigurationProvider;
    public final Provider compatUIShellCommandHandlerProvider;
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider dockStateReaderProvider;
    public final Provider imeControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider syncQueueProvider;
    public final Provider transitionsLazyProvider;

    public WMShellBaseModule_ProvideCompatUIControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellControllerProvider = provider3;
        this.displayControllerProvider = provider4;
        this.displayInsetsControllerProvider = provider5;
        this.imeControllerProvider = provider6;
        this.syncQueueProvider = provider7;
        this.mainExecutorProvider = provider8;
        this.transitionsLazyProvider = provider9;
        this.dockStateReaderProvider = provider10;
        this.compatUIConfigurationProvider = provider11;
        this.compatUIShellCommandHandlerProvider = provider12;
    }

    public static CompatUIController provideCompatUIController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, DisplayImeController displayImeController, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Lazy lazy, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration, CompatUIShellCommandHandler compatUIShellCommandHandler) {
        return new CompatUIController(context, shellInit, shellController, displayController, displayInsetsController, displayImeController, syncTransactionQueue, shellExecutor, lazy, dockStateReader, compatUIConfiguration, compatUIShellCommandHandler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCompatUIController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (DisplayImeController) this.imeControllerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), DoubleCheck.lazy(this.transitionsLazyProvider), (DockStateReader) this.dockStateReaderProvider.get(), (CompatUIConfiguration) this.compatUIConfigurationProvider.get(), (CompatUIShellCommandHandler) this.compatUIShellCommandHandlerProvider.get());
    }
}
