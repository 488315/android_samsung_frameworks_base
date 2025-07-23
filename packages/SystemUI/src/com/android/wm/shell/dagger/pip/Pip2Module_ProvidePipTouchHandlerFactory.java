package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.pip2.phone.PhonePipMenuController;
import com.android.wm.shell.pip2.phone.PipMotionHelper;
import com.android.wm.shell.pip2.phone.PipScheduler;
import com.android.wm.shell.pip2.phone.PipTouchHandler;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipTouchHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuPhoneControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDesktopStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMotionHelperProvider;
    public final Provider pipPerfHintControllerOptionalProvider;
    public final Provider pipSchedulerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider sizeSpecSourceProvider;

    public Pip2Module_ProvidePipTouchHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.menuPhoneControllerProvider = provider4;
        this.pipBoundsAlgorithmProvider = provider5;
        this.pipBoundsStateProvider = provider6;
        this.pipTransitionStateProvider = provider7;
        this.pipSchedulerProvider = provider8;
        this.sizeSpecSourceProvider = provider9;
        this.pipDisplayLayoutStateProvider = provider10;
        this.pipDesktopStateProvider = provider11;
        this.displayControllerProvider = provider12;
        this.pipMotionHelperProvider = provider13;
        this.floatingContentCoordinatorProvider = provider14;
        this.pipUiEventLoggerProvider = provider15;
        this.mainExecutorProvider = provider16;
        this.pipPerfHintControllerOptionalProvider = provider17;
    }

    public static PipTouchHandler providePipTouchHandler(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipTransitionState pipTransitionState, PipScheduler pipScheduler, SizeSpecSource sizeSpecSource, PipDisplayLayoutState pipDisplayLayoutState, PipDesktopState pipDesktopState, DisplayController displayController, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Optional optional) {
        return new PipTouchHandler(context, shellInit, shellCommandHandler, phonePipMenuController, pipBoundsAlgorithm, pipBoundsState, pipTransitionState, pipScheduler, sizeSpecSource, pipDisplayLayoutState, pipDesktopState, displayController, pipMotionHelper, floatingContentCoordinator, pipUiEventLogger, shellExecutor, optional);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTouchHandler((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (PhonePipMenuController) this.menuPhoneControllerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipScheduler) this.pipSchedulerProvider.get(), (SizeSpecSource) this.sizeSpecSourceProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipDesktopState) this.pipDesktopStateProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipMotionHelper) this.pipMotionHelperProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Optional) this.pipPerfHintControllerOptionalProvider.get());
    }
}
