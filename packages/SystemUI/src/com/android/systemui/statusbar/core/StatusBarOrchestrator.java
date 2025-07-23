package com.android.systemui.statusbar.core;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepository;
import com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepositoryImpl;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Optional;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarOrchestrator implements Dumpable {
    public final AutoHideController autoHideController;
    public final DistinctFlowImpl barModeUpdate;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 barTransitionsAndDeviceAsleep;
    public final Optional bubblesOptional;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 controllerAndBouncerShowing;
    public final DemoModeController demoModeController;
    public final DumpManager dumpManager;
    public final String dumpableName;
    public final Lazy notificationShadeWindowViewControllerLazy;
    public final StateFlowImpl phoneStatusBarTransitions;
    public final StateFlowImpl phoneStatusBarViewController;
    public final NotificationRemoteInputManager remoteInputManager;
    public final ShadeSurface shadeSurface;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 shouldAnimateNextBarModeChange;
    public final StatusBarModePerDisplayRepository statusBarModeRepository;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 statusBarVisible;
    public final StatusBarWindowStatePerDisplayRepository statusBarWindowStateRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        StatusBarOrchestrator create(int i, CoroutineScope coroutineScope, StatusBarWindowStatePerDisplayRepository statusBarWindowStatePerDisplayRepository, StatusBarModePerDisplayRepository statusBarModePerDisplayRepository, StatusBarInitializer statusBarInitializer, StatusBarWindowController statusBarWindowController, AutoHideController autoHideController);
    }

    public StatusBarOrchestrator(int i, CoroutineScope coroutineScope, StatusBarWindowStatePerDisplayRepository statusBarWindowStatePerDisplayRepository, StatusBarModePerDisplayRepository statusBarModePerDisplayRepository, StatusBarInitializer statusBarInitializer, StatusBarWindowController statusBarWindowController, CoroutineContext coroutineContext, AutoHideController autoHideController, DemoModeController demoModeController, PluginDependencyProvider pluginDependencyProvider, NotificationRemoteInputManager notificationRemoteInputManager, Lazy lazy, ShadeSurface shadeSurface, Optional<Bubbles> optional, DumpManager dumpManager, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.statusBarWindowStateRepository = statusBarWindowStatePerDisplayRepository;
        this.statusBarModeRepository = statusBarModePerDisplayRepository;
        this.autoHideController = autoHideController;
        this.demoModeController = demoModeController;
        this.remoteInputManager = notificationRemoteInputManager;
        this.notificationShadeWindowViewControllerLazy = lazy;
        this.shadeSurface = shadeSurface;
        this.bubblesOptional = optional;
        this.dumpManager = dumpManager;
        this.dumpableName = i != 0 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "StatusBarOrchestrator") : "StatusBarOrchestrator";
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.phoneStatusBarViewController = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this.phoneStatusBarTransitions = MutableStateFlow2;
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = (StatusBarModePerDisplayRepositoryImpl) statusBarModePerDisplayRepository;
        StatusBarWindowStatePerDisplayRepositoryImpl statusBarWindowStatePerDisplayRepositoryImpl = (StatusBarWindowStatePerDisplayRepositoryImpl) statusBarWindowStatePerDisplayRepository;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(statusBarModePerDisplayRepositoryImpl.isTransientShown, powerInteractor.isAwake, statusBarWindowStatePerDisplayRepositoryImpl.windowState, new StatusBarOrchestrator$shouldAnimateNextBarModeChange$1(null));
        this.shouldAnimateNextBarModeChange = combine;
        this.controllerAndBouncerShowing = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow), primaryBouncerInteractor.isShowing, StatusBarOrchestrator$controllerAndBouncerShowing$3.INSTANCE);
        this.barTransitionsAndDeviceAsleep = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow2), powerInteractor.isAsleep, StatusBarOrchestrator$barTransitionsAndDeviceAsleep$3.INSTANCE);
        this.statusBarVisible = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(statusBarModePerDisplayRepositoryImpl.statusBarMode, statusBarWindowStatePerDisplayRepositoryImpl.windowState, new StatusBarOrchestrator$statusBarVisible$1(null));
        this.barModeUpdate = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(FlowKt.combine(combine, new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow2), statusBarModePerDisplayRepositoryImpl.statusBarMode, StatusBarOrchestrator$barModeUpdate$3.INSTANCE), new StatusBarOrchestrator$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(((StatusBarWindowStatePerDisplayRepositoryImpl) this.statusBarWindowStateRepository).windowState.$$delegate_0.getValue());
        CentralSurfaces.dumpBarTransitions(printWriter, "PhoneStatusBarTransitions", (BarTransitions) this.phoneStatusBarTransitions.getValue());
    }
}
