package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipMotionHelper;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipMotionHelperFactory implements Provider {
    public final Provider contextProvider;
    public final Provider floatingContentCoordinatorProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuControllerProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipPerfHintControllerOptionalProvider;
    public final Provider pipSnapAlgorithmProvider;
    public final Provider pipTaskOrganizerProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider sizeSpecSourceProvider;

    public Pip1Module_ProvidePipMotionHelperFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.pipBoundsStateProvider = provider3;
        this.pipTaskOrganizerProvider = provider4;
        this.menuControllerProvider = provider5;
        this.pipSnapAlgorithmProvider = provider6;
        this.pipTransitionControllerProvider = provider7;
        this.floatingContentCoordinatorProvider = provider8;
        this.pipPerfHintControllerOptionalProvider = provider9;
        this.sizeSpecSourceProvider = provider10;
    }

    public static PipMotionHelper providePipMotionHelper(Context context, ShellExecutor shellExecutor, PipBoundsState pipBoundsState, PipTaskOrganizer pipTaskOrganizer, PhonePipMenuController phonePipMenuController, PipSnapAlgorithm pipSnapAlgorithm, PipTransitionController pipTransitionController, FloatingContentCoordinator floatingContentCoordinator, Optional optional, SizeSpecSource sizeSpecSource) {
        return new PipMotionHelper(context, shellExecutor, pipBoundsState, pipTaskOrganizer, phonePipMenuController, pipSnapAlgorithm, pipTransitionController, floatingContentCoordinator, optional, sizeSpecSource);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipMotionHelper((Context) this.contextProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipTaskOrganizer) this.pipTaskOrganizerProvider.get(), (PhonePipMenuController) this.menuControllerProvider.get(), (PipSnapAlgorithm) this.pipSnapAlgorithmProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (FloatingContentCoordinator) this.floatingContentCoordinatorProvider.get(), (Optional) this.pipPerfHintControllerOptionalProvider.get(), (SizeSpecSource) this.sizeSpecSourceProvider.get());
    }
}
