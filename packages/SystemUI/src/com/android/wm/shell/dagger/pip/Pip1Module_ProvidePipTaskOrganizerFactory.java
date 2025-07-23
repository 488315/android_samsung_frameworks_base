package com.android.wm.shell.dagger.pip;

import android.content.Context;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePipTaskOrganizerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopUserRepositoriesOptionalProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuPhoneControllerProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipParamsChangedForwarderProvider;
    public final Provider pipPerfHintControllerOptionalProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenControllerOptionalProvider;
    public final Provider syncTransactionQueueProvider;

    public Pip1Module_ProvidePipTaskOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.contextProvider = provider;
        this.syncTransactionQueueProvider = provider2;
        this.pipTransitionStateProvider = provider3;
        this.pipBoundsStateProvider = provider4;
        this.pipDisplayLayoutStateProvider = provider5;
        this.pipBoundsAlgorithmProvider = provider6;
        this.menuPhoneControllerProvider = provider7;
        this.pipAnimationControllerProvider = provider8;
        this.pipSurfaceTransactionHelperProvider = provider9;
        this.pipTransitionControllerProvider = provider10;
        this.pipParamsChangedForwarderProvider = provider11;
        this.splitScreenControllerOptionalProvider = provider12;
        this.pipPerfHintControllerOptionalProvider = provider13;
        this.desktopUserRepositoriesOptionalProvider = provider14;
        this.rootTaskDisplayAreaOrganizerProvider = provider15;
        this.displayControllerProvider = provider16;
        this.pipUiEventLoggerProvider = provider17;
        this.shellTaskOrganizerProvider = provider18;
        this.mainExecutorProvider = provider19;
    }

    public static PipTaskOrganizer providePipTaskOrganizer(Context context, SyncTransactionQueue syncTransactionQueue, PipTransitionState pipTransitionState, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipBoundsAlgorithm pipBoundsAlgorithm, PhonePipMenuController phonePipMenuController, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, PipTransitionController pipTransitionController, PipParamsChangedForwarder pipParamsChangedForwarder, Optional optional, Optional optional2, Optional optional3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        return new PipTaskOrganizer(context, syncTransactionQueue, pipTransitionState, pipBoundsState, pipDisplayLayoutState, pipBoundsAlgorithm, phonePipMenuController, pipAnimationController, pipSurfaceTransactionHelper, pipTransitionController, pipParamsChangedForwarder, optional, optional2, optional3, rootTaskDisplayAreaOrganizer, displayController, pipUiEventLogger, shellTaskOrganizer, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTaskOrganizer((Context) this.contextProvider.get(), (SyncTransactionQueue) this.syncTransactionQueueProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PhonePipMenuController) this.menuPhoneControllerProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (PipParamsChangedForwarder) this.pipParamsChangedForwarderProvider.get(), (Optional) this.splitScreenControllerOptionalProvider.get(), (Optional) this.pipPerfHintControllerOptionalProvider.get(), (Optional) this.desktopUserRepositoriesOptionalProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
