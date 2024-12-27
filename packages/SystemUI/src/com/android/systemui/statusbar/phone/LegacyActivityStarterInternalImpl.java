package com.android.systemui.statusbar.phone;

import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.Optional;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LegacyActivityStarterInternalImpl implements ActivityStarterInternal {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityTransitionAnimator activityTransitionAnimator;
    public final Lazy assistManagerLazy;
    public final Lazy biometricUnlockControllerLazy;
    public final Lazy centralSurfacesOptLazy;
    public final CommandQueue commandQueue;
    public final Context context;
    public final DeviceProvisionedController deviceProvisionedController;
    public final int displayId;
    public final Lazy dozeServiceHostLazy;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Lazy keyguardViewMediatorLazy;
    public final NotificationLockscreenUserManager lockScreenUserManager;
    public final DelayableExecutor mainExecutor;
    public final Lazy notifShadeWindowControllerLazy;
    public final ShadeAnimationInteractor shadeAnimationInteractor;
    public final Lazy shadeControllerLazy;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final SysuiStatusBarStateController statusBarStateController;
    public final StatusBarWindowController statusBarWindowController;
    public final UserTracker userTracker;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LegacyActivityStarterInternalImpl(Lazy lazy, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, CommandQueue commandQueue, ShadeAnimationInteractor shadeAnimationInteractor, Lazy lazy7, Lazy lazy8, ActivityTransitionAnimator activityTransitionAnimator, Context context, int i, NotificationLockscreenUserManager notificationLockscreenUserManager, StatusBarWindowController statusBarWindowController, WakefulnessLifecycle wakefulnessLifecycle, KeyguardUpdateMonitor keyguardUpdateMonitor, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, ActivityIntentHelper activityIntentHelper, DelayableExecutor delayableExecutor, CommunalSceneInteractor communalSceneInteractor) {
        this.centralSurfacesOptLazy = lazy;
        this.keyguardStateController = keyguardStateController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.assistManagerLazy = lazy2;
        this.dozeServiceHostLazy = lazy3;
        this.biometricUnlockControllerLazy = lazy4;
        this.keyguardViewMediatorLazy = lazy5;
        this.shadeControllerLazy = lazy6;
        this.commandQueue = commandQueue;
        this.shadeAnimationInteractor = shadeAnimationInteractor;
        this.statusBarKeyguardViewManagerLazy = lazy7;
        this.notifShadeWindowControllerLazy = lazy8;
        this.activityTransitionAnimator = activityTransitionAnimator;
        this.context = context;
        this.displayId = i;
        this.lockScreenUserManager = notificationLockscreenUserManager;
        this.statusBarWindowController = statusBarWindowController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.deviceProvisionedController = deviceProvisionedController;
        this.userTracker = userTracker;
        this.activityIntentHelper = activityIntentHelper;
        this.mainExecutor = delayableExecutor;
    }

    public static final int access$getSubDisplayID(LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl) {
        legacyActivityStarterInternalImpl.getClass();
        SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
        Context context = legacyActivityStarterInternalImpl.context;
        subscreenUtil.getClass();
        return SubscreenUtil.getSubDisplay(context).getDisplayId();
    }

    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        CentralSurfaces centralSurfaces;
        Log.i("LegacyActivityStarterInternalImpl", "Invoking dismissKeyguardThenExecute, afterKeyguardGone: " + z);
        boolean willRunAnimationOnKeyguard = onDismissAction.willRunAnimationOnKeyguard();
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!willRunAnimationOnKeyguard && this.wakefulnessLifecycle.mWakefulness == 0 && ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen && !((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide && ((DozeServiceHost) this.dozeServiceHostLazy.get()).mPulsing) {
            ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).startWakeAndUnlock(2, (BiometricUnlockSource) null);
        }
        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
            ((StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerLazy.get()).dismissWithAction(onDismissAction, runnable, z, true, false);
            return;
        }
        if (this.keyguardUpdateMonitor.mIsDreaming && (centralSurfaces = getCentralSurfaces()) != null) {
            ((CentralSurfacesImpl) centralSurfaces).awakenDreams();
        }
        onDismissAction.onDismiss();
    }

    public final void executeRunnableDismissingKeyguard(final Runnable runnable, Runnable runnable2, final boolean z, boolean z2, final boolean z3, final boolean z4, String str) {
        int i = LogUtil.getInt(runnable);
        int i2 = LogUtil.getInt(runnable2);
        int i3 = LogUtil.getInt(z);
        int i4 = LogUtil.getInt(z2);
        int i5 = LogUtil.getInt(z3);
        int i6 = LogUtil.getInt(z4);
        int i7 = LogUtil.getInt(str);
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "dismissAction requested r=", " cancel=", " collapse=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i3, " after=", i4, " def=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i5, " will=", i6, ", msg=");
        m.append(i7);
        com.android.systemui.keyguard.Log.d("KeyguardUnlockInfo", m.toString());
        dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$executeRunnableDismissingKeyguard$onDismissAction$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Runnable runnable3 = runnable;
                LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this;
                if (runnable3 != null) {
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl.keyguardStateController;
                    if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                        ((StatusBarKeyguardViewManager) legacyActivityStarterInternalImpl.statusBarKeyguardViewManagerLazy.get()).addAfterKeyguardGoneRunnable(runnable);
                    } else {
                        legacyActivityStarterInternalImpl.mainExecutor.execute(runnable3);
                    }
                }
                boolean z5 = z;
                boolean z6 = z3;
                if (z5) {
                    boolean isExpandedVisible = ((ShadeController) legacyActivityStarterInternalImpl.shadeControllerLazy.get()).isExpandedVisible();
                    Lazy lazy = legacyActivityStarterInternalImpl.shadeControllerLazy;
                    if (!isExpandedVisible || ((StatusBarKeyguardViewManager) legacyActivityStarterInternalImpl.statusBarKeyguardViewManagerLazy.get()).isBouncerShowing()) {
                        ((ShadeController) lazy.get()).collapseShadeForActivityStart();
                    } else if (((KeyguardViewMediator) legacyActivityStarterInternalImpl.keyguardViewMediatorLazy.get()).mHelper.needsCollapsePanelWithNoAnimation()) {
                        Log.d("CentralSurfaces", "collapseShade with no animation");
                        ((ShadeController) lazy.get()).collapseShade(false);
                    } else {
                        ((ShadeController) lazy.get()).cancelExpansionAndCollapseShade();
                    }
                } else if (z6) {
                    Log.d("LegacyActivityStarterInternalImpl", "ignored deferred");
                    return false;
                }
                Flags.communalHub();
                return z6;
            }

            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean willRunAnimationOnKeyguard() {
                return z4;
            }
        }, runnable2, z2);
    }

    public final UserHandle getActivityUserHandle(Intent intent) {
        for (String str : this.context.getResources().getStringArray(R.array.system_ui_packages)) {
            ComponentName component = intent.getComponent();
            if (component == null) {
                break;
            }
            if (Intrinsics.areEqual(str, component.getPackageName())) {
                return new UserHandle(UserHandle.myUserId());
            }
        }
        return ((UserTrackerImpl) this.userTracker).getUserHandle();
    }

    public final CentralSurfaces getCentralSurfaces() {
        return (CentralSurfaces) ((Optional) this.centralSurfacesOptLazy.get()).orElse(null);
    }

    public final void startActivity(final Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle) {
        CentralSurfaces centralSurfaces;
        final UserHandle activityUserHandle = userHandle == null ? getActivityUserHandle(intent) : userHandle;
        if (this.keyguardStateController.isUnlocked() || !z2) {
            ActivityStarterInternal.startActivityDismissingKeyguard$default(this, intent, z, false, null, 0, controller, null, false, activityUserHandle, 64);
            return;
        }
        if (z) {
            ((ShadeController) this.shadeControllerLazy.get()).cancelExpansionAndCollapseShade();
        }
        if (this.keyguardUpdateMonitor.mIsDreaming && (centralSurfaces = getCentralSurfaces()) != null) {
            ((CentralSurfacesImpl) centralSurfaces).awakenDreams();
        }
        this.activityTransitionAnimator.startIntentWithAnimation(null, false, intent.getPackage(), z2, new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivity$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Integer.valueOf(TaskStackBuilder.create(LegacyActivityStarterInternalImpl.this.context).addNextIntent(intent).startActivities(CentralSurfaces.getActivityOptions(LegacyActivityStarterInternalImpl.this.displayId, (RemoteAnimationAdapter) obj), activityUserHandle));
            }
        });
    }

    public final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar(ActivityTransitionAnimator.Controller controller, boolean z, boolean z2) {
        Optional of;
        if (controller == null) {
            return null;
        }
        View rootView = controller.getTransitionContainer().getRootView();
        StatusBarWindowController statusBarWindowController = this.statusBarWindowController;
        if (rootView != statusBarWindowController.mStatusBarWindowView) {
            of = Optional.empty();
        } else {
            controller.setTransitionContainer(statusBarWindowController.mLaunchAnimationContainer);
            of = Optional.of(new DelegateTransitionAnimatorController(controller) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController.2
                public AnonymousClass2(ActivityTransitionAnimator.Controller controller2) {
                    super(controller2);
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final void onTransitionAnimationEnd(boolean z3) {
                    this.delegate.onTransitionAnimationEnd(z3);
                    StatusBarWindowController statusBarWindowController2 = StatusBarWindowController.this;
                    State state = statusBarWindowController2.mCurrentState;
                    if (state.mIsLaunchAnimationRunning) {
                        state.mIsLaunchAnimationRunning = false;
                        statusBarWindowController2.apply(state);
                    }
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final void onTransitionAnimationStart(boolean z3) {
                    this.delegate.onTransitionAnimationStart(z3);
                    StatusBarWindowController statusBarWindowController2 = StatusBarWindowController.this;
                    State state = statusBarWindowController2.mCurrentState;
                    if (true == state.mIsLaunchAnimationRunning) {
                        return;
                    }
                    state.mIsLaunchAnimationRunning = true;
                    statusBarWindowController2.apply(state);
                }
            });
        }
        if (of.isPresent()) {
            return (ActivityTransitionAnimator.Controller) of.get();
        }
        if (getCentralSurfaces() == null || !z) {
            return controller2;
        }
        return new StatusBarTransitionAnimatorController(controller2, this.shadeAnimationInteractor, (ShadeController) this.shadeControllerLazy.get(), (NotificationShadeWindowController) this.notifShadeWindowControllerLazy.get(), this.commandQueue, this.displayId, z2);
    }
}
