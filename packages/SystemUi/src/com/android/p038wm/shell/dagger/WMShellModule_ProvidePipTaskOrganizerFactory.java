package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipBoundsAlgorithm;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.PipParamsChangedForwarder;
import com.android.p038wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p038wm.shell.pip.PipTaskOrganizer;
import com.android.p038wm.shell.pip.PipTransitionController;
import com.android.p038wm.shell.pip.PipTransitionState;
import com.android.p038wm.shell.pip.PipUiEventLogger;
import com.android.p038wm.shell.pip.phone.PhonePipMenuController;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipTaskOrganizerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider menuPhoneControllerProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipParamsChangedForwarderProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider pipUiEventLoggerProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider splitScreenControllerOptionalProvider;
    public final Provider syncTransactionQueueProvider;

    public WMShellModule_ProvidePipTaskOrganizerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
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
        this.displayControllerProvider = provider13;
        this.pipUiEventLoggerProvider = provider14;
        this.shellTaskOrganizerProvider = provider15;
        this.mainExecutorProvider = provider16;
    }

    public static PipTaskOrganizer providePipTaskOrganizer(Context context, SyncTransactionQueue syncTransactionQueue, PipTransitionState pipTransitionState, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipBoundsAlgorithm pipBoundsAlgorithm, PhonePipMenuController phonePipMenuController, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, PipTransitionController pipTransitionController, PipParamsChangedForwarder pipParamsChangedForwarder, Optional optional, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        return new PipTaskOrganizer(context, syncTransactionQueue, pipTransitionState, pipBoundsState, pipDisplayLayoutState, pipBoundsAlgorithm, phonePipMenuController, pipAnimationController, pipSurfaceTransactionHelper, pipTransitionController, pipParamsChangedForwarder, optional, displayController, pipUiEventLogger, shellTaskOrganizer, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePipTaskOrganizer((Context) this.contextProvider.get(), (SyncTransactionQueue) this.syncTransactionQueueProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PhonePipMenuController) this.menuPhoneControllerProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (PipParamsChangedForwarder) this.pipParamsChangedForwarderProvider.get(), (Optional) this.splitScreenControllerOptionalProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipUiEventLogger) this.pipUiEventLoggerProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
