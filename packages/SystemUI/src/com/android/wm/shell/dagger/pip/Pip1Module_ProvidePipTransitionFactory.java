package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTransition;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.HomeTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipTransitionFactory implements Provider {
    public final Provider contextProvider;
    public final Provider homeTransitionObserverProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMenuControllerProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenOptionalProvider;
    public final Provider transitionsProvider;

    public Pip1Module_ProvidePipTransitionFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.transitionsProvider = provider4;
        this.pipAnimationControllerProvider = provider5;
        this.pipBoundsAlgorithmProvider = provider6;
        this.pipBoundsStateProvider = provider7;
        this.pipDisplayLayoutStateProvider = provider8;
        this.pipTransitionStateProvider = provider9;
        this.pipMenuControllerProvider = provider10;
        this.pipSurfaceTransactionHelperProvider = provider11;
        this.homeTransitionObserverProvider = provider12;
        this.splitScreenOptionalProvider = provider13;
    }

    public static PipTransition providePipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipAnimationController pipAnimationController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, PhonePipMenuController phonePipMenuController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, HomeTransitionObserver homeTransitionObserver, Optional optional) {
        return new PipTransition(context, shellInit, shellTaskOrganizer, transitions, pipBoundsState, pipDisplayLayoutState, pipTransitionState, phonePipMenuController, pipBoundsAlgorithm, pipAnimationController, pipSurfaceTransactionHelper, homeTransitionObserver, optional);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        ShellInit shellInit = (ShellInit) this.shellInitProvider.get();
        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get();
        Transitions transitions = (Transitions) this.transitionsProvider.get();
        PipAnimationController pipAnimationController = (PipAnimationController) this.pipAnimationControllerProvider.get();
        return new PipTransition(context, shellInit, shellTaskOrganizer, transitions, (PipBoundsState) this.pipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PhonePipMenuController) this.pipMenuControllerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), pipAnimationController, (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get(), (HomeTransitionObserver) this.homeTransitionObserverProvider.get(), (Optional) this.splitScreenOptionalProvider.get());
    }
}
