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
import com.android.wm.shell.pip2.phone.PhonePipMenuController;
import com.android.wm.shell.pip2.phone.PipTaskListener;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipPhoneMenuControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMediaControllerProvider;
    public final Provider pipTaskListenerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider systemWindowsProvider;

    public Pip2Module_ProvidePipPhoneMenuControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipMediaControllerProvider = provider3;
        this.systemWindowsProvider = provider4;
        this.pipUiEventLoggerProvider = provider5;
        this.pipTaskListenerProvider = provider6;
        this.pipTransitionStateProvider = provider7;
        this.displayControllerProvider = provider8;
        this.displayInsetsControllerProvider = provider9;
        this.pipDisplayLayoutStateProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.mainHandlerProvider = provider12;
    }

    public static PhonePipMenuController providePipPhoneMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, PipUiEventLogger pipUiEventLogger, PipTaskListener pipTaskListener, PipTransitionState pipTransitionState, DisplayController displayController, DisplayInsetsController displayInsetsController, PipDisplayLayoutState pipDisplayLayoutState, ShellExecutor shellExecutor, Handler handler) {
        return new PhonePipMenuController(context, pipBoundsState, pipMediaController, systemWindows, pipUiEventLogger, pipTaskListener, pipTransitionState, displayController, displayInsetsController, pipDisplayLayoutState, shellExecutor, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhonePipMenuController((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (SystemWindows) this.systemWindowsProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (PipTaskListener) this.pipTaskListenerProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
