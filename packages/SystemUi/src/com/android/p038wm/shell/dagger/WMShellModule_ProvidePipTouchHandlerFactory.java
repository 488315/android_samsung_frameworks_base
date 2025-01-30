package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.common.FloatingContentCoordinator;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.p038wm.shell.pip.PipBoundsAlgorithm;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipTaskOrganizer;
import com.android.p038wm.shell.pip.PipUiEventLogger;
import com.android.p038wm.shell.pip.phone.PhonePipMenuController;
import com.android.p038wm.shell.pip.phone.PipMotionHelper;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.p038wm.shell.pip.phone.PipTouchHandler;
import com.android.p038wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipTouchHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuPhoneControllerProvider;
    public final Provider nsControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipMotionHelperProvider;
    public final Provider pipSizeSpecHandlerProvider;
    public final Provider pipTaskOrganizerProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider shellInitProvider;

    public WMShellModule_ProvidePipTouchHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.menuPhoneControllerProvider = provider3;
        this.pipBoundsAlgorithmProvider = provider4;
        this.pipBoundsStateProvider = provider5;
        this.pipSizeSpecHandlerProvider = provider6;
        this.pipTaskOrganizerProvider = provider7;
        this.pipMotionHelperProvider = provider8;
        this.floatingContentCoordinatorProvider = provider9;
        this.pipUiEventLoggerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.nsControllerProvider = provider12;
    }

    public static PipTouchHandler providePipTouchHandler(Context context, ShellInit shellInit, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipSizeSpecHandler pipSizeSpecHandler, PipTaskOrganizer pipTaskOrganizer, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController) {
        return new PipTouchHandler(context, shellInit, phonePipMenuController, pipBoundsAlgorithm, pipBoundsState, pipSizeSpecHandler, pipTaskOrganizer, pipMotionHelper, floatingContentCoordinator, pipUiEventLogger, shellExecutor, naturalSwitchingDropTargetController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePipTouchHandler((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (PhonePipMenuController) this.menuPhoneControllerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipSizeSpecHandler) this.pipSizeSpecHandlerProvider.get(), (PipTaskOrganizer) this.pipTaskOrganizerProvider.get(), (PipMotionHelper) this.pipMotionHelperProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (NaturalSwitchingDropTargetController) this.nsControllerProvider.get());
    }
}
