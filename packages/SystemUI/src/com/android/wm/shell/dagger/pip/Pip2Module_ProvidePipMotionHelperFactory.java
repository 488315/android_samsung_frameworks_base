package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip2.phone.PhonePipMenuController;
import com.android.wm.shell.pip2.phone.PipMotionHelper;
import com.android.wm.shell.pip2.phone.PipScheduler;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip2Module_ProvidePipMotionHelperFactory implements Provider {
    public final Provider contextProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider menuControllerProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipPerfHintControllerOptionalProvider;
    public final Provider pipSchedulerProvider;
    public final Provider pipSnapAlgorithmProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;

    public Pip2Module_ProvidePipMotionHelperFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.menuControllerProvider = provider3;
        this.pipSnapAlgorithmProvider = provider4;
        this.floatingContentCoordinatorProvider = provider5;
        this.pipSchedulerProvider = provider6;
        this.pipPerfHintControllerOptionalProvider = provider7;
        this.pipTransitionStateProvider = provider8;
        this.pipUiEventLoggerProvider = provider9;
    }

    public static PipMotionHelper providePipMotionHelper(Context context, PipBoundsState pipBoundsState, PhonePipMenuController phonePipMenuController, PipSnapAlgorithm pipSnapAlgorithm, FloatingContentCoordinator floatingContentCoordinator, PipScheduler pipScheduler, Optional optional, PipTransitionState pipTransitionState, PipUiEventLogger pipUiEventLogger) {
        return new PipMotionHelper(context, pipBoundsState, phonePipMenuController, pipSnapAlgorithm, floatingContentCoordinator, pipScheduler, optional, pipTransitionState, pipUiEventLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipMotionHelper((Context) this.contextProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PhonePipMenuController) this.menuControllerProvider.get(), (PipSnapAlgorithm) this.pipSnapAlgorithmProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (PipScheduler) this.pipSchedulerProvider.get(), (Optional) this.pipPerfHintControllerOptionalProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get());
    }
}
