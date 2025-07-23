package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.policy.domain.interactor.DeviceProvisioningInteractor;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActivityStarterInternalImpl implements ActivityStarterInternal {
    public final ActivityTransitionAnimator activityTransitionAnimator;
    public final Lazy assistManagerLazy;
    public final Lazy centralSurfacesOptLazy;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final Context context;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final DelayableExecutor mainExecutor;
    public final Lazy shadeControllerLazy;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ActivityStarterInternalImpl(Lazy lazy, KeyguardInteractor keyguardInteractor, Lazy lazy2, Context context, Resources resources, SelectedUserInteractor selectedUserInteractor, DeviceEntryInteractor deviceEntryInteractor, ActivityTransitionAnimator activityTransitionAnimator, int i, DeviceProvisioningInteractor deviceProvisioningInteractor, ActivityIntentHelper activityIntentHelper, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy3, DelayableExecutor delayableExecutor, Lazy lazy4, CommunalSceneInteractor communalSceneInteractor, StatusBarWindowControllerStore statusBarWindowControllerStore, Lazy lazy5, ShadeAnimationInteractor shadeAnimationInteractor, Lazy lazy6, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.statusBarKeyguardViewManagerLazy = lazy;
        this.centralSurfacesOptLazy = lazy2;
        this.context = context;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.activityTransitionAnimator = activityTransitionAnimator;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.assistManagerLazy = lazy3;
        this.mainExecutor = delayableExecutor;
        this.shadeControllerLazy = lazy4;
        this.communalSceneInteractor = communalSceneInteractor;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, String str) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void registerTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie, ActivityTransitionAnimator.ControllerFactory controllerFactory, CoroutineScope coroutineScope) {
        TransitionAnimator.Companion.getClass();
        this.activityTransitionAnimator.register(transitionCookie, new ActivityStarterInternalImpl$registerTransition$factory$1(controllerFactory, this, controllerFactory.cookie, controllerFactory.component, controllerFactory.launchCujType, controllerFactory.returnCujType), coroutineScope);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final boolean shouldAnimateLaunch(boolean z) {
        if (this.keyguardTransitionInteractor.getCurrentState() == KeyguardState.OCCLUDED) {
            return false;
        }
        if (((Boolean) this.deviceEntryInteractor.isDeviceEntered.$$delegate_0.getValue()).booleanValue()) {
            return true;
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, int i2) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startCameraActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, boolean z, Runnable runnable, View view, ActivityTransitionAnimator.Controller controller, boolean z2, boolean z3, Intent intent, Bundle bundle, String str) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void unregisterTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie) {
        TransitionAnimator.Companion.getClass();
        this.activityTransitionAnimator.unregister(transitionCookie);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, String str, boolean z3, UserHandle userHandle) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }
}
