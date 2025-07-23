package com.android.wm.shell.dagger.pip;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip1Module_ProvidesPipPhoneMenuControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipMediaControllerProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider systemWindowsProvider;

    public Pip1Module_ProvidesPipPhoneMenuControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipMediaControllerProvider = provider3;
        this.systemWindowsProvider = provider4;
        this.pipUiEventLoggerProvider = provider5;
        this.mainExecutorProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.splitScreenOptionalProvider = provider8;
    }

    public static PhonePipMenuController providesPipPhoneMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Handler handler, Optional optional) {
        return new PhonePipMenuController(context, pipBoundsState, pipMediaController, systemWindows, pipUiEventLogger, shellExecutor, handler, optional);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhonePipMenuController((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (SystemWindows) this.systemWindowsProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Handler) this.mainHandlerProvider.get(), (Optional) this.splitScreenOptionalProvider.get());
    }
}
