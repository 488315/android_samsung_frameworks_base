package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p038wm.shell.pip.PipTransitionState;
import com.android.p038wm.shell.pip.p039tv.TvPipBoundsAlgorithm;
import com.android.p038wm.shell.pip.p039tv.TvPipBoundsState;
import com.android.p038wm.shell.pip.p039tv.TvPipMenuController;
import com.android.p038wm.shell.pip.p039tv.TvPipTransition;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipModule_ProvideTvPipTransitionFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMenuControllerProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider transitionsProvider;
    public final Provider tvPipBoundsAlgorithmProvider;
    public final Provider tvPipBoundsStateProvider;

    public TvPipModule_ProvideTvPipTransitionFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.transitionsProvider = provider4;
        this.tvPipBoundsStateProvider = provider5;
        this.pipDisplayLayoutStateProvider = provider6;
        this.pipTransitionStateProvider = provider7;
        this.pipMenuControllerProvider = provider8;
        this.tvPipBoundsAlgorithmProvider = provider9;
        this.pipAnimationControllerProvider = provider10;
        this.pipSurfaceTransactionHelperProvider = provider11;
    }

    public static TvPipTransition provideTvPipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, TvPipMenuController tvPipMenuController, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        return new TvPipTransition(context, shellInit, shellTaskOrganizer, transitions, tvPipBoundsState, pipDisplayLayoutState, pipTransitionState, tvPipMenuController, tvPipBoundsAlgorithm, pipAnimationController, pipSurfaceTransactionHelper, Optional.empty());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideTvPipTransition((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (Transitions) this.transitionsProvider.get(), (TvPipBoundsState) this.tvPipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (TvPipMenuController) this.pipMenuControllerProvider.get(), (TvPipBoundsAlgorithm) this.tvPipBoundsAlgorithmProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get());
    }
}
