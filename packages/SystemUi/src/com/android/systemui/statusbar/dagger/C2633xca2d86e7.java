package com.android.systemui.statusbar.dagger;

import android.app.IActivityManager;
import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingcall.KeyguardCallChipController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallFlags;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.time.SystemClock;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideOngoingCallControllerFactory */
/* loaded from: classes2.dex */
public final class C2633xca2d86e7 implements Provider {
    public final Provider activityStarterProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider iActivityManagerProvider;
    public final Provider indicatorScaleGardenerProvider;
    public final Provider keyguardCallChipControllerProvider;
    public final Provider loggerProvider;
    public final Provider mainExecutorProvider;
    public final Provider notifCollectionProvider;
    public final Provider ongoingCallFlagsProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowControllerProvider;
    public final Provider swipeStatusBarAwayGestureHandlerProvider;
    public final Provider systemClockProvider;

    public C2633xca2d86e7(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        this.contextProvider = provider;
        this.notifCollectionProvider = provider2;
        this.systemClockProvider = provider3;
        this.activityStarterProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.iActivityManagerProvider = provider6;
        this.loggerProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.statusBarWindowControllerProvider = provider9;
        this.swipeStatusBarAwayGestureHandlerProvider = provider10;
        this.statusBarStateControllerProvider = provider11;
        this.ongoingCallFlagsProvider = provider12;
        this.keyguardCallChipControllerProvider = provider13;
        this.configurationControllerProvider = provider14;
        this.indicatorScaleGardenerProvider = provider15;
    }

    public static OngoingCallController provideOngoingCallController(Context context, CommonNotifCollection commonNotifCollection, SystemClock systemClock, ActivityStarter activityStarter, Executor executor, IActivityManager iActivityManager, OngoingCallLogger ongoingCallLogger, DumpManager dumpManager, StatusBarWindowController statusBarWindowController, SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler, StatusBarStateController statusBarStateController, OngoingCallFlags ongoingCallFlags, KeyguardCallChipController keyguardCallChipController, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        ongoingCallFlags.getClass();
        Flags flags = Flags.INSTANCE;
        flags.getClass();
        ReleasedFlag releasedFlag = Flags.ONGOING_CALL_STATUS_BAR_CHIP;
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) ongoingCallFlags.featureFlags;
        boolean z = featureFlagsRelease.isEnabled(releasedFlag) && featureFlagsRelease.isEnabled(Flags.ONGOING_CALL_IN_IMMERSIVE);
        OngoingCallController ongoingCallController = new OngoingCallController(context, commonNotifCollection, ongoingCallFlags, systemClock, activityStarter, executor, iActivityManager, ongoingCallLogger, dumpManager, z ? Optional.of(statusBarWindowController) : Optional.empty(), z ? Optional.of(swipeStatusBarAwayGestureHandler) : Optional.empty(), statusBarStateController, keyguardCallChipController, configurationController, indicatorScaleGardener);
        ongoingCallController.dumpManager.registerDumpable(ongoingCallController);
        OngoingCallFlags ongoingCallFlags2 = ongoingCallController.ongoingCallFlags;
        ongoingCallFlags2.getClass();
        flags.getClass();
        if (((FeatureFlagsRelease) ongoingCallFlags2.featureFlags).isEnabled(releasedFlag)) {
            ((NotifPipeline) ongoingCallController.notifCollection).addCollectionListener(ongoingCallController.notifListener);
            ongoingCallController.statusBarStateController.addCallback(ongoingCallController.statusBarStateListener);
            ((ConfigurationControllerImpl) ongoingCallController.configurationController).addCallback(ongoingCallController.configurationListener);
        }
        return ongoingCallController;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideOngoingCallController((Context) this.contextProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get(), (SystemClock) this.systemClockProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (Executor) this.mainExecutorProvider.get(), (IActivityManager) this.iActivityManagerProvider.get(), (OngoingCallLogger) this.loggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (StatusBarWindowController) this.statusBarWindowControllerProvider.get(), (SwipeStatusBarAwayGestureHandler) this.swipeStatusBarAwayGestureHandlerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (OngoingCallFlags) this.ongoingCallFlagsProvider.get(), (KeyguardCallChipController) this.keyguardCallChipControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (IndicatorScaleGardener) this.indicatorScaleGardenerProvider.get());
    }
}
