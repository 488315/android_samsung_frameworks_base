package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.pip.tv.TvPipBoundsAlgorithm;
import com.android.wm.shell.pip.tv.TvPipBoundsState;
import com.android.wm.shell.pip.tv.TvPipMenuController;
import com.android.wm.shell.pip.tv.TvPipTaskOrganizer;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipModule_ProvidePipTaskOrganizerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipParamsChangedForwarderProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenControllerOptionalProvider;
    public final Provider syncTransactionQueueProvider;
    public final Provider tvPipBoundsAlgorithmProvider;
    public final Provider tvPipBoundsStateProvider;
    public final Provider tvPipMenuControllerProvider;

    public TvPipModule_ProvidePipTaskOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.tvPipMenuControllerProvider = provider2;
        this.syncTransactionQueueProvider = provider3;
        this.tvPipBoundsStateProvider = provider4;
        this.pipDisplayLayoutStateProvider = provider5;
        this.pipTransitionStateProvider = provider6;
        this.tvPipBoundsAlgorithmProvider = provider7;
        this.pipAnimationControllerProvider = provider8;
        this.pipTransitionControllerProvider = provider9;
        this.pipParamsChangedForwarderProvider = provider10;
        this.pipSurfaceTransactionHelperProvider = provider11;
        this.splitScreenControllerOptionalProvider = provider12;
        this.displayControllerProvider = provider13;
        this.pipUiEventLoggerProvider = provider14;
        this.shellTaskOrganizerProvider = provider15;
        this.mainExecutorProvider = provider16;
    }

    public static TvPipTaskOrganizer providePipTaskOrganizer(Context context, TvPipMenuController tvPipMenuController, SyncTransactionQueue syncTransactionQueue, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, PipAnimationController pipAnimationController, PipTransitionController pipTransitionController, PipParamsChangedForwarder pipParamsChangedForwarder, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, Optional optional, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        return new TvPipTaskOrganizer(context, syncTransactionQueue, pipTransitionState, tvPipBoundsState, pipDisplayLayoutState, tvPipBoundsAlgorithm, tvPipMenuController, pipAnimationController, pipSurfaceTransactionHelper, pipTransitionController, pipParamsChangedForwarder, optional, displayController, pipUiEventLogger, shellTaskOrganizer, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePipTaskOrganizer((Context) this.contextProvider.get(), (TvPipMenuController) this.tvPipMenuControllerProvider.get(), (SyncTransactionQueue) this.syncTransactionQueueProvider.get(), (TvPipBoundsState) this.tvPipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (TvPipBoundsAlgorithm) this.tvPipBoundsAlgorithmProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (PipParamsChangedForwarder) this.pipParamsChangedForwarderProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get(), (Optional) this.splitScreenControllerOptionalProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
