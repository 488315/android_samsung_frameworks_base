package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.android.HandlerContext;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopTilingViewModelFactory implements Provider {
    public final Provider bgScopeProvider;
    public final Provider contextProvider;
    public final Provider desktopModeEventLoggerProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayControllerProvider;
    public final Provider focusTransitionObserverProvider;
    public final Provider mainDispatcherProvider;
    public final Provider mainExecutorProvider;
    public final Provider returnToDragStartAnimatorProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;
    public final Provider toggleResizeDesktopTaskTransitionHandlerProvider;
    public final Provider transitionsProvider;
    public final Provider windowDecorTaskResourceLoaderProvider;

    public WMShellModule_ProvideDesktopTilingViewModelFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.mainDispatcherProvider = provider2;
        this.bgScopeProvider = provider3;
        this.displayControllerProvider = provider4;
        this.rootTaskDisplayAreaOrganizerProvider = provider5;
        this.syncQueueProvider = provider6;
        this.transitionsProvider = provider7;
        this.shellTaskOrganizerProvider = provider8;
        this.toggleResizeDesktopTaskTransitionHandlerProvider = provider9;
        this.returnToDragStartAnimatorProvider = provider10;
        this.desktopUserRepositoriesProvider = provider11;
        this.desktopModeEventLoggerProvider = provider12;
        this.windowDecorTaskResourceLoaderProvider = provider13;
        this.focusTransitionObserverProvider = provider14;
        this.mainExecutorProvider = provider15;
        this.desktopStateProvider = provider16;
    }

    public static DesktopTilingDecorViewModel provideDesktopTilingViewModel(Context context, HandlerContext handlerContext, CoroutineScope coroutineScope, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SyncTransactionQueue syncTransactionQueue, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, ReturnToDragStartAnimator returnToDragStartAnimator, DesktopUserRepositories desktopUserRepositories, DesktopModeEventLogger desktopModeEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, FocusTransitionObserver focusTransitionObserver, ShellExecutor shellExecutor, DesktopState desktopState) {
        return new DesktopTilingDecorViewModel(context, handlerContext, coroutineScope, displayController, rootTaskDisplayAreaOrganizer, syncTransactionQueue, transitions, shellTaskOrganizer, toggleResizeDesktopTaskTransitionHandler, returnToDragStartAnimator, desktopUserRepositories, desktopModeEventLogger, windowDecorTaskResourceLoader, focusTransitionObserver, shellExecutor, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopTilingDecorViewModel((Context) this.contextProvider.get(), (MainCoroutineDispatcher) this.mainDispatcherProvider.get(), (CoroutineScope) this.bgScopeProvider.get(), (DisplayController) this.displayControllerProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (Transitions) this.transitionsProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (ToggleResizeDesktopTaskTransitionHandler) this.toggleResizeDesktopTaskTransitionHandlerProvider.get(), (ReturnToDragStartAnimator) this.returnToDragStartAnimatorProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (DesktopModeEventLogger) this.desktopModeEventLoggerProvider.get(), (WindowDecorTaskResourceLoader) this.windowDecorTaskResourceLoaderProvider.get(), (FocusTransitionObserver) this.focusTransitionObserverProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
