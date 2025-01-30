package com.android.systemui.statusbar.phone.dagger;

import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.OperatorNameViewController$Factory;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.phone.CarrierHomeLogoViewController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinder;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModel;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory implements Provider {
    public final Provider animationSchedulerProvider;
    public final Provider carrierConfigTrackerProvider;
    public final Provider carrierInfraMediatorProvider;
    public final Provider carrierLogoViewControllerFactoryProvider;
    public final Provider collapsedStatusBarFragmentLoggerProvider;
    public final Provider collapsedStatusBarViewBinderProvider;
    public final Provider collapsedStatusBarViewModelProvider;
    public final Provider commandQueueProvider;
    public final Provider darkIconManagerFactoryProvider;
    public final Provider displayLifecycleProvider;
    public final Provider dumpManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider handlerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider locationPublisherProvider;
    public final Provider mainExecutorProvider;
    public final Provider notificationIconAreaControllerProvider;
    public final Provider ongoingCallControllerProvider;
    public final Provider operatorNameViewControllerFactoryProvider;
    public final Provider privacyLoggerProvider;
    public final Provider secureSettingsProvider;
    public final Provider shadeExpansionStateManagerProvider;
    public final Provider shadeViewControllerProvider;
    public final Provider statusBarFragmentComponentFactoryProvider;
    public final Provider statusBarHideIconsForBouncerManagerProvider;
    public final Provider statusBarIconControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowStateControllerProvider;

    public StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29) {
        this.statusBarFragmentComponentFactoryProvider = provider;
        this.ongoingCallControllerProvider = provider2;
        this.animationSchedulerProvider = provider3;
        this.locationPublisherProvider = provider4;
        this.notificationIconAreaControllerProvider = provider5;
        this.shadeExpansionStateManagerProvider = provider6;
        this.featureFlagsProvider = provider7;
        this.statusBarIconControllerProvider = provider8;
        this.darkIconManagerFactoryProvider = provider9;
        this.collapsedStatusBarViewModelProvider = provider10;
        this.collapsedStatusBarViewBinderProvider = provider11;
        this.statusBarHideIconsForBouncerManagerProvider = provider12;
        this.keyguardStateControllerProvider = provider13;
        this.shadeViewControllerProvider = provider14;
        this.statusBarStateControllerProvider = provider15;
        this.commandQueueProvider = provider16;
        this.carrierConfigTrackerProvider = provider17;
        this.collapsedStatusBarFragmentLoggerProvider = provider18;
        this.operatorNameViewControllerFactoryProvider = provider19;
        this.secureSettingsProvider = provider20;
        this.mainExecutorProvider = provider21;
        this.dumpManagerProvider = provider22;
        this.statusBarWindowStateControllerProvider = provider23;
        this.keyguardUpdateMonitorProvider = provider24;
        this.handlerProvider = provider25;
        this.privacyLoggerProvider = provider26;
        this.carrierInfraMediatorProvider = provider27;
        this.carrierLogoViewControllerFactoryProvider = provider28;
        this.displayLifecycleProvider = provider29;
    }

    public static CollapsedStatusBarFragment createCollapsedStatusBarFragment(StatusBarFragmentComponent.Factory factory, OngoingCallController ongoingCallController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, StatusBarLocationPublisher statusBarLocationPublisher, NotificationIconAreaController notificationIconAreaController, ShadeExpansionStateManager shadeExpansionStateManager, FeatureFlags featureFlags, StatusBarIconController statusBarIconController, StatusBarIconController.DarkIconManager.Factory factory2, CollapsedStatusBarViewModel collapsedStatusBarViewModel, CollapsedStatusBarViewBinder collapsedStatusBarViewBinder, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, KeyguardStateController keyguardStateController, ShadeViewController shadeViewController, StatusBarStateController statusBarStateController, CommandQueue commandQueue, CarrierConfigTracker carrierConfigTracker, CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger, OperatorNameViewController$Factory operatorNameViewController$Factory, SecureSettings secureSettings, Executor executor, DumpManager dumpManager, StatusBarWindowStateController statusBarWindowStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, PrivacyLogger privacyLogger, CarrierInfraMediator carrierInfraMediator, CarrierHomeLogoViewController.Factory factory3, DisplayLifecycle displayLifecycle) {
        return new CollapsedStatusBarFragment(factory, ongoingCallController, systemStatusAnimationScheduler, statusBarLocationPublisher, notificationIconAreaController, shadeExpansionStateManager, featureFlags, statusBarIconController, factory2, collapsedStatusBarViewModel, collapsedStatusBarViewBinder, statusBarHideIconsForBouncerManager, keyguardStateController, shadeViewController, statusBarStateController, commandQueue, carrierConfigTracker, collapsedStatusBarFragmentLogger, operatorNameViewController$Factory, secureSettings, executor, dumpManager, statusBarWindowStateController, keyguardUpdateMonitor, handler, privacyLogger, carrierInfraMediator, factory3, displayLifecycle);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return createCollapsedStatusBarFragment((StatusBarFragmentComponent.Factory) this.statusBarFragmentComponentFactoryProvider.get(), (OngoingCallController) this.ongoingCallControllerProvider.get(), (SystemStatusAnimationScheduler) this.animationSchedulerProvider.get(), (StatusBarLocationPublisher) this.locationPublisherProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (StatusBarIconController) this.statusBarIconControllerProvider.get(), (StatusBarIconController.DarkIconManager.Factory) this.darkIconManagerFactoryProvider.get(), (CollapsedStatusBarViewModel) this.collapsedStatusBarViewModelProvider.get(), (CollapsedStatusBarViewBinder) this.collapsedStatusBarViewBinderProvider.get(), (StatusBarHideIconsForBouncerManager) this.statusBarHideIconsForBouncerManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ShadeViewController) this.shadeViewControllerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (CarrierConfigTracker) this.carrierConfigTrackerProvider.get(), (CollapsedStatusBarFragmentLogger) this.collapsedStatusBarFragmentLoggerProvider.get(), (OperatorNameViewController$Factory) this.operatorNameViewControllerFactoryProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (Executor) this.mainExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (StatusBarWindowStateController) this.statusBarWindowStateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (Handler) this.handlerProvider.get(), (PrivacyLogger) this.privacyLoggerProvider.get(), (CarrierInfraMediator) this.carrierInfraMediatorProvider.get(), (CarrierHomeLogoViewController.Factory) this.carrierLogoViewControllerFactoryProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get());
    }
}
