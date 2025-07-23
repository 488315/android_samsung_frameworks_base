package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import android.view.Choreographer;
import android.view.IWindowManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.apptoweb.AssistContentRequester;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.education.AppHandleEducationController;
import com.android.wm.shell.desktopmode.education.AppToWebEducationController;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.common.AppHandleAndHeaderVisibilityHelper;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import dagger.internal.Provider;
import java.util.Optional;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopModeWindowDecorViewModelFactory implements Provider {
    public final Provider activityOrientationChangeHandlerProvider;
    public final Provider animExecutorProvider;
    public final Provider appHandleAndHeaderVisibilityHelperProvider;
    public final Provider appHandleEducationControllerProvider;
    public final Provider appToWebEducationControllerProvider;
    public final Provider assistContentRequesterProvider;
    public final Provider bgExecutorProvider;
    public final Provider bgScopeProvider;
    public final Provider compatUIProvider;
    public final Provider contextProvider;
    public final Provider desksOrganizerProvider;
    public final Provider desktopConfigProvider;
    public final Provider desktopImmersiveControllerProvider;
    public final Provider desktopModeCompatPolicyProvider;
    public final Provider desktopModeEventLoggerProvider;
    public final Provider desktopModeUiEventLoggerProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopTasksControllerProvider;
    public final Provider desktopTasksLimiterProvider;
    public final Provider desktopTilingDecorViewModelProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider focusTransitionObserverProvider;
    public final Provider genericLinksParserProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider mainChoreographerProvider;
    public final Provider mainDispatcherProvider;
    public final Provider mainHandlerProvider;
    public final Provider multiDisplayDragMoveIndicatorControllerProvider;
    public final Provider multiInstanceHelperProvider;
    public final Provider nsControllerProvider;
    public final Provider recentsTransitionHandlerProvider;
    public final Provider rootTaskDisplayAreaOrganizerProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellExecutorProvider;
    public final Provider shellInitProvider;
    public final Provider syncQueueProvider;
    public final Provider taskOrganizerProvider;
    public final Provider taskResourceLoaderProvider;
    public final Provider transitionsProvider;
    public final Provider windowDecorCaptionHandleRepositoryProvider;
    public final Provider windowDecorViewHostSupplierProvider;
    public final Provider windowManagerProvider;

    public WMShellModule_ProvideDesktopModeWindowDecorViewModelFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42, Provider provider43, Provider provider44, Provider provider45) {
        this.contextProvider = provider;
        this.shellExecutorProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.mainChoreographerProvider = provider4;
        this.mainDispatcherProvider = provider5;
        this.bgScopeProvider = provider6;
        this.bgExecutorProvider = provider7;
        this.shellInitProvider = provider8;
        this.shellCommandHandlerProvider = provider9;
        this.windowManagerProvider = provider10;
        this.taskOrganizerProvider = provider11;
        this.desktopUserRepositoriesProvider = provider12;
        this.displayControllerProvider = provider13;
        this.shellControllerProvider = provider14;
        this.displayInsetsControllerProvider = provider15;
        this.syncQueueProvider = provider16;
        this.transitionsProvider = provider17;
        this.desktopTasksControllerProvider = provider18;
        this.desktopImmersiveControllerProvider = provider19;
        this.rootTaskDisplayAreaOrganizerProvider = provider20;
        this.interactionJankMonitorProvider = provider21;
        this.genericLinksParserProvider = provider22;
        this.assistContentRequesterProvider = provider23;
        this.windowDecorViewHostSupplierProvider = provider24;
        this.multiInstanceHelperProvider = provider25;
        this.desktopTasksLimiterProvider = provider26;
        this.appHandleEducationControllerProvider = provider27;
        this.appToWebEducationControllerProvider = provider28;
        this.appHandleAndHeaderVisibilityHelperProvider = provider29;
        this.windowDecorCaptionHandleRepositoryProvider = provider30;
        this.activityOrientationChangeHandlerProvider = provider31;
        this.focusTransitionObserverProvider = provider32;
        this.desktopModeEventLoggerProvider = provider33;
        this.desktopModeUiEventLoggerProvider = provider34;
        this.taskResourceLoaderProvider = provider35;
        this.recentsTransitionHandlerProvider = provider36;
        this.desktopModeCompatPolicyProvider = provider37;
        this.desktopTilingDecorViewModelProvider = provider38;
        this.nsControllerProvider = provider39;
        this.animExecutorProvider = provider40;
        this.multiDisplayDragMoveIndicatorControllerProvider = provider41;
        this.compatUIProvider = provider42;
        this.desksOrganizerProvider = provider43;
        this.desktopStateProvider = provider44;
        this.desktopConfigProvider = provider45;
    }

    public static Optional provideDesktopModeWindowDecorViewModel(Context context, ShellExecutor shellExecutor, Handler handler, Choreographer choreographer, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, ShellInit shellInit, ShellCommandHandler shellCommandHandler, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellController shellController, DisplayInsetsController displayInsetsController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional optional, Optional optional2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, InteractionJankMonitor interactionJankMonitor, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, Optional optional3, AppHandleEducationController appHandleEducationController, AppToWebEducationController appToWebEducationController, AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, Optional optional4, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, RecentsTransitionHandler recentsTransitionHandler, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopTilingDecorViewModel desktopTilingDecorViewModel, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, ShellExecutor shellExecutor3, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, Optional optional5, DesksOrganizer desksOrganizer, DesktopState desktopState, DesktopConfig desktopConfig) {
        Optional empty = !desktopState.canEnterDesktopModeOrShowAppHandle() ? Optional.empty() : Optional.of(new DesktopModeWindowDecorViewModel(context, shellExecutor, handler, choreographer, mainCoroutineDispatcher, coroutineScope, shellExecutor2, shellInit, shellCommandHandler, iWindowManager, shellTaskOrganizer, desktopUserRepositories, displayController, shellController, displayInsetsController, syncTransactionQueue, transitions, optional, (DesktopImmersiveController) optional2.get(), rootTaskDisplayAreaOrganizer, interactionJankMonitor, appToWebGenericLinksParser, assistContentRequester, windowDecorViewHostSupplier, multiInstanceHelper, optional3, appHandleEducationController, appToWebEducationController, appHandleAndHeaderVisibilityHelper, windowDecorCaptionHandleRepository, optional4, focusTransitionObserver, desktopModeEventLogger, desktopModeUiEventLogger, windowDecorTaskResourceLoader, recentsTransitionHandler, naturalSwitchingDropTargetController, shellExecutor3, desktopModeCompatPolicy, desktopTilingDecorViewModel, multiDisplayDragMoveIndicatorController, (CompatUIHandler) optional5.orElse(null), desksOrganizer, desktopState, desktopConfig));
        empty.getClass();
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopModeWindowDecorViewModel((Context) this.contextProvider.get(), (ShellExecutor) this.shellExecutorProvider.get(), (Handler) this.mainHandlerProvider.get(), (Choreographer) this.mainChoreographerProvider.get(), (MainCoroutineDispatcher) this.mainDispatcherProvider.get(), (CoroutineScope) this.bgScopeProvider.get(), (ShellExecutor) this.bgExecutorProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (IWindowManager) this.windowManagerProvider.get(), (ShellTaskOrganizer) this.taskOrganizerProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (Transitions) this.transitionsProvider.get(), (Optional) this.desktopTasksControllerProvider.get(), (Optional) this.desktopImmersiveControllerProvider.get(), (RootTaskDisplayAreaOrganizer) this.rootTaskDisplayAreaOrganizerProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (AppToWebGenericLinksParser) this.genericLinksParserProvider.get(), (AssistContentRequester) this.assistContentRequesterProvider.get(), (WindowDecorViewHostSupplier) this.windowDecorViewHostSupplierProvider.get(), (MultiInstanceHelper) this.multiInstanceHelperProvider.get(), (Optional) this.desktopTasksLimiterProvider.get(), (AppHandleEducationController) this.appHandleEducationControllerProvider.get(), (AppToWebEducationController) this.appToWebEducationControllerProvider.get(), (AppHandleAndHeaderVisibilityHelper) this.appHandleAndHeaderVisibilityHelperProvider.get(), (WindowDecorCaptionHandleRepository) this.windowDecorCaptionHandleRepositoryProvider.get(), (Optional) this.activityOrientationChangeHandlerProvider.get(), (FocusTransitionObserver) this.focusTransitionObserverProvider.get(), (DesktopModeEventLogger) this.desktopModeEventLoggerProvider.get(), (DesktopModeUiEventLogger) this.desktopModeUiEventLoggerProvider.get(), (WindowDecorTaskResourceLoader) this.taskResourceLoaderProvider.get(), (RecentsTransitionHandler) this.recentsTransitionHandlerProvider.get(), (DesktopModeCompatPolicy) this.desktopModeCompatPolicyProvider.get(), (DesktopTilingDecorViewModel) this.desktopTilingDecorViewModelProvider.get(), (NaturalSwitchingDropTargetController) this.nsControllerProvider.get(), (ShellExecutor) this.animExecutorProvider.get(), (MultiDisplayDragMoveIndicatorController) this.multiDisplayDragMoveIndicatorControllerProvider.get(), (Optional) this.compatUIProvider.get(), (DesksOrganizer) this.desksOrganizerProvider.get(), (DesktopState) this.desktopStateProvider.get(), (DesktopConfig) this.desktopConfigProvider.get());
    }
}
