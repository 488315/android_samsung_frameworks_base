package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import android.view.Choreographer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeStatus;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.shortcut.DexCompatRestartDialogUtils;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.rune.CoreRune;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvideWindowDecorViewModelFactory implements Provider {
    public final Provider animExecutorProvider;
    public final Provider contextProvider;
    public final Provider desktopModeControllerProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider dexCompatRestartDialogUtilsProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider mainChoreographerProvider;
    public final Provider mainHandlerProvider;
    public final Provider nsControllerProvider;
    public final Provider pipOptionalProvider;
    public final Provider shellControllerProvider;
    public final Provider splitScreenControllerProvider;
    public final Provider syncQueueProvider;
    public final Provider taskOrganizerProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideWindowDecorViewModelFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.mainChoreographerProvider = provider3;
        this.taskOrganizerProvider = provider4;
        this.displayControllerProvider = provider5;
        this.syncQueueProvider = provider6;
        this.transitionsProvider = provider7;
        this.desktopModeControllerProvider = provider8;
        this.desktopTasksControllerProvider = provider9;
        this.splitScreenControllerProvider = provider10;
        this.animExecutorProvider = provider11;
        this.displayInsetsControllerProvider = provider12;
        this.pipOptionalProvider = provider13;
        this.nsControllerProvider = provider14;
        this.dexCompatRestartDialogUtilsProvider = provider15;
        this.shellControllerProvider = provider16;
    }

    public static WindowDecorViewModel provideWindowDecorViewModel(Context context, Handler handler, Choreographer choreographer, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional optional, Optional optional2, Optional optional3, ShellExecutor shellExecutor, DisplayInsetsController displayInsetsController, Optional optional4, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, DexCompatRestartDialogUtils dexCompatRestartDialogUtils, ShellController shellController) {
        return (DesktopModeStatus.isAnyEnabled() && CoreRune.MT_NEW_DEX_USE_DESKTOP_MODE_WINDOW_DECOR_VIEW_MODEL) ? new DesktopModeWindowDecorViewModel(context, handler, choreographer, shellTaskOrganizer, displayController, syncTransactionQueue, transitions, optional, optional2, optional3) : CoreRune.MW_CAPTION_SHELL ? new MultitaskingWindowDecorViewModel(context, handler, choreographer, shellTaskOrganizer, displayController, syncTransactionQueue, shellExecutor, optional3, displayInsetsController, optional4, naturalSwitchingDropTargetController, optional, dexCompatRestartDialogUtils, transitions, shellController) : new CaptionWindowDecorViewModel(context, handler, choreographer, shellTaskOrganizer, displayController, syncTransactionQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideWindowDecorViewModel((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (Choreographer) this.mainChoreographerProvider.get(), (ShellTaskOrganizer) this.taskOrganizerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (Transitions) this.transitionsProvider.get(), (Optional) this.desktopModeControllerProvider.get(), (Optional) this.desktopTasksControllerProvider.get(), (Optional) this.splitScreenControllerProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (Optional) this.pipOptionalProvider.get(), (NaturalSwitchingDropTargetController) this.nsControllerProvider.get(), (DexCompatRestartDialogUtils) this.dexCompatRestartDialogUtilsProvider.get(), (ShellController) this.shellControllerProvider.get());
    }
}
