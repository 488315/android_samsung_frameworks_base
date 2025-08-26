package com.android.wm.shell.dagger;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIConfiguration;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.CompatUIShellCommandHandler;
import com.android.wm.shell.compatui.CompatUIStatusManager;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIControllerFactory implements Provider {
    public final Provider accessibilityManagerProvider;
    public final Provider compatUIComponentFactoryProvider;
    public final Provider compatUIConfigurationProvider;
    public final Provider compatUIRepositoryProvider;
    public final Provider compatUIShellCommandHandlerProvider;
    public final Provider compatUIStateProvider;
    public final Provider compatUIStatusManagerProvider;
    public final Provider componentIdGeneratorProvider;
    public final Provider contextProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider dockStateReaderProvider;
    public final Provider imeControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider syncQueueProvider;
    public final Provider transitionsLazyProvider;

    public WMShellBaseModule_ProvideCompatUIControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20) {
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
        this.accessibilityManagerProvider = provider13;
        this.compatUIRepositoryProvider = provider14;
        this.desktopUserRepositoriesProvider = provider15;
        this.compatUIStateProvider = provider16;
        this.componentIdGeneratorProvider = provider17;
        this.compatUIComponentFactoryProvider = provider18;
        this.compatUIStatusManagerProvider = provider19;
        this.desktopStateProvider = provider20;
    }

    public static Optional provideCompatUIController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, DisplayImeController displayImeController, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Optional optional, CompatUIStatusManager compatUIStatusManager, DesktopState desktopState) {
        Optional optionalEmpty = !context.getResources().getBoolean(R.bool.config_enableCompatUIController) ? Optional.empty() : Optional.of(new CompatUIController(context, shellInit, shellController, displayController, displayInsetsController, displayImeController, syncTransactionQueue, shellExecutor, lazy, (DockStateReader) lazy2.get(), (CompatUIConfiguration) lazy3.get(), (CompatUIShellCommandHandler) lazy4.get(), (AccessibilityManager) lazy5.get(), compatUIStatusManager, optional, desktopState));
        optionalEmpty.getClass();
        return optionalEmpty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        ShellInit shellInit = (ShellInit) this.shellInitProvider.get();
        ShellController shellController = (ShellController) this.shellControllerProvider.get();
        DisplayController displayController = (DisplayController) this.displayControllerProvider.get();
        DisplayInsetsController displayInsetsController = (DisplayInsetsController) this.displayInsetsControllerProvider.get();
        DisplayImeController displayImeController = (DisplayImeController) this.imeControllerProvider.get();
        SyncTransactionQueue syncTransactionQueue = (SyncTransactionQueue) this.syncQueueProvider.get();
        ShellExecutor shellExecutor = (ShellExecutor) this.mainExecutorProvider.get();
        Lazy lazy = DoubleCheck.lazy(this.transitionsLazyProvider);
        Lazy lazy2 = DoubleCheck.lazy(this.dockStateReaderProvider);
        Lazy lazy3 = DoubleCheck.lazy(this.compatUIConfigurationProvider);
        Lazy lazy4 = DoubleCheck.lazy(this.compatUIShellCommandHandlerProvider);
        Lazy lazy5 = DoubleCheck.lazy(this.accessibilityManagerProvider);
        Optional optional = (Optional) this.desktopUserRepositoriesProvider.get();
        return provideCompatUIController(context, shellInit, shellController, displayController, displayInsetsController, displayImeController, syncTransactionQueue, shellExecutor, lazy, lazy2, lazy3, lazy4, lazy5, optional, (CompatUIStatusManager) this.compatUIStatusManagerProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
