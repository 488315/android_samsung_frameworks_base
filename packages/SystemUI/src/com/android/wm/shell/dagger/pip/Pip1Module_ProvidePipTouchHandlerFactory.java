package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipMotionHelper;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipTouchHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuPhoneControllerProvider;
    public final Provider nsControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipMotionHelperProvider;
    public final Provider pipPerfHintControllerOptionalProvider;
    public final Provider pipTaskOrganizerProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider shellInitProvider;
    public final Provider sizeSpecSourceProvider;

    public Pip1Module_ProvidePipTouchHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.menuPhoneControllerProvider = provider3;
        this.pipBoundsAlgorithmProvider = provider4;
        this.pipBoundsStateProvider = provider5;
        this.sizeSpecSourceProvider = provider6;
        this.pipTaskOrganizerProvider = provider7;
        this.pipMotionHelperProvider = provider8;
        this.floatingContentCoordinatorProvider = provider9;
        this.pipUiEventLoggerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.nsControllerProvider = provider12;
        this.pipPerfHintControllerOptionalProvider = provider13;
    }

    public static PipTouchHandler providePipTouchHandler(Context context, ShellInit shellInit, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, SizeSpecSource sizeSpecSource, PipTaskOrganizer pipTaskOrganizer, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Optional optional, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController) {
        return new PipTouchHandler(context, shellInit, phonePipMenuController, pipBoundsAlgorithm, pipBoundsState, sizeSpecSource, pipTaskOrganizer, pipMotionHelper, floatingContentCoordinator, pipUiEventLogger, shellExecutor, optional, naturalSwitchingDropTargetController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTouchHandler((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (PhonePipMenuController) this.menuPhoneControllerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (SizeSpecSource) this.sizeSpecSourceProvider.get(), (PipTaskOrganizer) this.pipTaskOrganizerProvider.get(), (PipMotionHelper) this.pipMotionHelperProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Optional) this.pipPerfHintControllerOptionalProvider.get(), (NaturalSwitchingDropTargetController) this.nsControllerProvider.get());
    }
}
