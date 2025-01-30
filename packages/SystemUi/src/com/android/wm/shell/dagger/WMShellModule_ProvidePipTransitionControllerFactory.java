package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTransition;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipTransitionControllerFactory implements Provider {
    public final Provider contextProvider;
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

    public WMShellModule_ProvidePipTransitionControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
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
        this.splitScreenOptionalProvider = provider12;
    }

    public static PipTransition providePipTransitionController(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipAnimationController pipAnimationController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, PhonePipMenuController phonePipMenuController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, Optional optional) {
        return new PipTransition(context, shellInit, shellTaskOrganizer, transitions, pipBoundsState, pipDisplayLayoutState, pipTransitionState, phonePipMenuController, pipBoundsAlgorithm, pipAnimationController, pipSurfaceTransactionHelper, optional);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePipTransitionController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (Transitions) this.transitionsProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PhonePipMenuController) this.pipMenuControllerProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get(), (Optional) this.splitScreenOptionalProvider.get());
    }
}
