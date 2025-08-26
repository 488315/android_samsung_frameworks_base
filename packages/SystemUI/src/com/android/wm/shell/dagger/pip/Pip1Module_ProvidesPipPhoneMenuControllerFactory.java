package com.android.wm.shell.dagger.pip;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class Pip1Module_ProvidesPipPhoneMenuControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMediaControllerProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider systemWindowsProvider;

    public Pip1Module_ProvidesPipPhoneMenuControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipMediaControllerProvider = provider3;
        this.systemWindowsProvider = provider4;
        this.displayControllerProvider = provider5;
        this.displayInsetsControllerProvider = provider6;
        this.pipDisplayLayoutStateProvider = provider7;
        this.pipUiEventLoggerProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.mainHandlerProvider = provider10;
        this.splitScreenOptionalProvider = provider11;
    }

    public static PhonePipMenuController providesPipPhoneMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, DisplayController displayController, DisplayInsetsController displayInsetsController, PipDisplayLayoutState pipDisplayLayoutState, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Handler handler, Optional optional) {
        return new PhonePipMenuController(context, pipBoundsState, pipMediaController, systemWindows, displayController, displayInsetsController, pipDisplayLayoutState, pipUiEventLogger, shellExecutor, handler, optional);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhonePipMenuController((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (SystemWindows) this.systemWindowsProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Handler) this.mainHandlerProvider.get(), (Optional) this.splitScreenOptionalProvider.get());
    }
}
