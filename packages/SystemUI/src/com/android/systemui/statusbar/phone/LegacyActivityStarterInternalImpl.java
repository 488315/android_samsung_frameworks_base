package com.android.systemui.statusbar.phone;

import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.ActivityTransitionAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
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
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.Optional;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LegacyActivityStarterInternalImpl implements ActivityStarterInternal {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityTransitionAnimator activityTransitionAnimator;
    public final Lazy assistManagerLazy;
    public final Lazy biometricUnlockControllerLazy;
    public final Lazy centralSurfacesOptLazy;
    public final CommandQueue commandQueue;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
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
    public final SelectedUserInteractor selectedUserInteractor;
    public final ShadeAnimationInteractor shadeAnimationInteractor;
    public final Lazy shadeControllerLazy;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final SysuiStatusBarStateController statusBarStateController;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;
    public final UserTracker userTracker;
    public final WakefulnessLifecycle wakefulnessLifecycle;

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

    public LegacyActivityStarterInternalImpl(SelectedUserInteractor selectedUserInteractor, Lazy lazy, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, CommandQueue commandQueue, ShadeAnimationInteractor shadeAnimationInteractor, Lazy lazy7, Lazy lazy8, ActivityTransitionAnimator activityTransitionAnimator, Context context, int i, NotificationLockscreenUserManager notificationLockscreenUserManager, StatusBarWindowControllerStore statusBarWindowControllerStore, WakefulnessLifecycle wakefulnessLifecycle, KeyguardUpdateMonitor keyguardUpdateMonitor, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, ActivityIntentHelper activityIntentHelper, DelayableExecutor delayableExecutor, CommunalSceneInteractor communalSceneInteractor, CommunalSettingsInteractor communalSettingsInteractor) {
        this.selectedUserInteractor = selectedUserInteractor;
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
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.deviceProvisionedController = deviceProvisionedController;
        this.userTracker = userTracker;
        this.activityIntentHelper = activityIntentHelper;
        this.mainExecutor = delayableExecutor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
    }

    public static final int access$getSubDisplayID(LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl) {
        legacyActivityStarterInternalImpl.getClass();
        SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
        Context context = legacyActivityStarterInternalImpl.context;
        subscreenUtil.getClass();
        return SubscreenUtil.getSubDisplay(context).getDisplayId();
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        CentralSurfaces centralSurfaces$1;
        Log.i("LegacyActivityStarterInternalImpl", "Invoking dismissKeyguardThenExecute, afterKeyguardGone: " + z);
        boolean willRunAnimationOnKeyguard = onDismissAction.willRunAnimationOnKeyguard();
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!willRunAnimationOnKeyguard && this.wakefulnessLifecycle.mWakefulness == 0 && ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen && !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide && ((DozeServiceHost) this.dozeServiceHostLazy.get()).mPulsing) {
            ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).startWakeAndUnlock(2, (BiometricUnlockSource) null);
        }
        boolean z2 = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        if (!z2) {
            if (keyguardUpdateMonitor.mIsDreaming && (centralSurfaces$1 = getCentralSurfaces$1()) != null) {
                ((CentralSurfacesImpl) centralSurfaces$1).awakenDreams();
            }
            onDismissAction.onDismiss();
            return;
        }
        Lazy lazy = this.shadeControllerLazy;
        if (((ShadeController) lazy.get()).isExpandedVisible() && ((!keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.getUserCanSkipBouncer(this.selectedUserInteractor.getSelectedUserId())) && !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide && ((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).mHelper.needsCollapsePanelWithNoAnimation())) {
            ((ShadeController) lazy.get()).collapseShade(false);
        }
        ((StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerLazy.get()).dismissWithAction(onDismissAction, runnable, z, true, false);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void executeRunnableDismissingKeyguard(final Runnable runnable, Runnable runnable2, final boolean z, boolean z2, final boolean z3, final boolean z4, String str) {
        int i = LogUtil.getInt(runnable);
        int i2 = LogUtil.getInt(runnable2);
        int i3 = LogUtil.getInt(z);
        int i4 = LogUtil.getInt(z2);
        int i5 = LogUtil.getInt(z3);
        int i6 = LogUtil.getInt(z4);
        int i7 = LogUtil.getInt(str);
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "dismissAction requested r=", " cancel=", " collapse=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i3, " after=", i4, " def=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i5, " will=", i6, ", msg=");
        m.append(i7);
        com.android.systemui.keyguard.Log.d("KeyguardUnlockInfo", m.toString());
        dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$executeRunnableDismissingKeyguard$onDismissAction$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Runnable runnable3 = runnable;
                boolean z5 = z;
                final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this;
                if (runnable3 != null) {
                    boolean z6 = legacyActivityStarterInternalImpl.communalSettingsInteractor.isCommunalFlagEnabled() && ((Boolean) legacyActivityStarterInternalImpl.communalSceneInteractor.isCommunalVisible.$$delegate_0.getValue()).booleanValue() && z5;
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl.keyguardStateController;
                    if (!keyguardStateControllerImpl.mShowing || !keyguardStateControllerImpl.mOccluded || legacyActivityStarterInternalImpl.isCommunalWidgetLaunch() || z6) {
                        legacyActivityStarterInternalImpl.mainExecutor.execute(runnable);
                    } else {
                        ((StatusBarKeyguardViewManager) legacyActivityStarterInternalImpl.statusBarKeyguardViewManagerLazy.get()).addAfterKeyguardGoneRunnable(runnable);
                    }
                }
                boolean z7 = z3;
                if (!z5) {
                    if (!z7) {
                        return z7;
                    }
                    Log.d("LegacyActivityStarterInternalImpl", "ignored deferred");
                    return false;
                }
                boolean isExpandedVisible = ((ShadeController) legacyActivityStarterInternalImpl.shadeControllerLazy.get()).isExpandedVisible();
                Lazy lazy = legacyActivityStarterInternalImpl.shadeControllerLazy;
                if (isExpandedVisible && !((StatusBarKeyguardViewManager) legacyActivityStarterInternalImpl.statusBarKeyguardViewManagerLazy.get()).isBouncerShowing()) {
                    Lazy lazy2 = legacyActivityStarterInternalImpl.keyguardViewMediatorLazy;
                    if (((KeyguardViewMediator) lazy2.get()).isShowing()) {
                        if (!((KeyguardViewMediator) lazy2.get()).mHelper.needsCollapsePanelWithNoAnimation()) {
                            legacyActivityStarterInternalImpl.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$executeRunnableDismissingKeyguard$onDismissAction$1$onDismiss$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((ShadeController) LegacyActivityStarterInternalImpl.this.shadeControllerLazy.get()).collapseShade(false);
                                }
                            }, 120L);
                            return z7;
                        }
                        Log.d("CentralSurfaces", "collapseShade with no animation");
                        ((ShadeController) lazy.get()).collapseShade(false);
                        return z7;
                    }
                }
                ((ShadeController) lazy.get()).collapseShadeForActivityStart();
                return z7;
            }

            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean willRunAnimationOnKeyguard() {
                return z4;
            }
        }, runnable2, z2);
    }

    public final UserHandle getActivityUserHandle$1(Intent intent) {
        for (String str : this.context.getResources().getStringArray(R.array.system_ui_packages)) {
            str.getClass();
            ComponentName component = intent.getComponent();
            if (component == null) {
                break;
            }
            if (str.equals(component.getPackageName())) {
                return new UserHandle(UserHandle.myUserId());
            }
        }
        return ((UserTrackerImpl) this.userTracker).getUserHandle();
    }

    public final CentralSurfaces getCentralSurfaces$1() {
        return (CentralSurfaces) ((Optional) this.centralSurfacesOptLazy.get()).orElse(null);
    }

    public final boolean isCommunalWidgetLaunch() {
        if (!this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            return false;
        }
        CommunalSceneInteractor communalSceneInteractor = this.communalSceneInteractor;
        return ((Boolean) communalSceneInteractor.isCommunalVisible.$$delegate_0.getValue()).booleanValue() && ((Boolean) communalSceneInteractor.isLaunchingWidget.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void registerTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie, ActivityTransitionAnimator.ControllerFactory controllerFactory, CoroutineScope coroutineScope) {
        TransitionAnimator.Companion.getClass();
        this.activityTransitionAnimator.register(transitionCookie, new LegacyActivityStarterInternalImpl$registerTransition$factory$1(controllerFactory, this, controllerFactory.cookie, controllerFactory.component, controllerFactory.launchCujType, controllerFactory.returnCujType), coroutineScope);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final boolean shouldAnimateLaunch(boolean z) {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivity(final Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle) {
        CentralSurfaces centralSurfaces$1;
        final UserHandle activityUserHandle$1 = userHandle == null ? getActivityUserHandle$1(intent) : userHandle;
        if (this.keyguardStateController.isUnlocked() || !z2) {
            ActivityStarterInternal.startActivityDismissingKeyguard$default(this, intent, z, false, null, 0, controller, null, false, activityUserHandle$1, 64);
            return;
        }
        if (z) {
            ((ShadeController) this.shadeControllerLazy.get()).cancelExpansionAndCollapseShade();
        }
        if (this.keyguardUpdateMonitor.mIsDreaming && (centralSurfaces$1 = getCentralSurfaces$1()) != null) {
            ((CentralSurfacesImpl) centralSurfaces$1).awakenDreams();
        }
        this.activityTransitionAnimator.startIntentWithAnimation(null, false, intent.getPackage(), z2, new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Intent intent2 = intent;
                UserHandle userHandle2 = activityUserHandle$1;
                LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = LegacyActivityStarterInternalImpl.this;
                return Integer.valueOf(TaskStackBuilder.create(legacyActivityStarterInternalImpl.context).addNextIntent(intent2).startActivities(CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl.displayId, (RemoteAnimationAdapter) obj), userHandle2));
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivityDismissingKeyguard(final Intent intent, boolean z, boolean z2, final ActivityStarter.Callback callback, final int i, ActivityTransitionAnimator.Controller controller, String str, final boolean z3, UserHandle userHandle) {
        final UserHandle activityUserHandle$1 = userHandle == null ? getActivityUserHandle$1(intent) : userHandle;
        if (!z2 || ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get()) {
            boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
            final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(controller, z, true);
            boolean z4 = z && wrapAnimationControllerForShadeOrStatusBar$1 == null;
            final boolean z5 = false;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                    Intent intent2 = intent;
                    intent2.setFlags((intent2.getFlags() & 131072) != 0 ? 268435456 : 335544320);
                    intent.addFlags(i);
                    final int[] iArr = {-96};
                    ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller2 = wrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z6 = z5;
                    String str2 = intent.getPackage();
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = LegacyActivityStarterInternalImpl.this;
                    final boolean z7 = z3;
                    final Intent intent3 = intent;
                    final UserHandle userHandle2 = activityUserHandle$1;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            int displayId;
                            Intent intent4 = intent3;
                            int[] iArr2 = iArr;
                            UserHandle userHandle3 = userHandle2;
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                            ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl2.displayId, (RemoteAnimationAdapter) obj));
                            activityOptions.setDismissKeyguardIfInsecure();
                            activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z7);
                            CameraIntents.Companion.getClass();
                            KeyguardShortcutManager.Companion.getClass();
                            if (Intrinsics.areEqual(intent4, KeyguardShortcutManager.INSECURE_CAMERA_INTENT)) {
                                activityOptions.setRotationAnimationHint(3);
                            }
                            if ("android.settings.panel.action.VOLUME".equals(intent4.getAction())) {
                                activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
                            }
                            intent4.collectExtraIntentKeys();
                            if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                Display display = legacyActivityStarterInternalImpl2.context.getDisplay();
                                display.getClass();
                                displayId = display.getDisplayId();
                            } else {
                                displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(legacyActivityStarterInternalImpl2);
                            }
                            activityOptions.setLaunchDisplayId(displayId);
                            try {
                                iArr2[0] = ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, legacyActivityStarterInternalImpl2.context.getBasePackageName(), legacyActivityStarterInternalImpl2.context.getAttributionTag(), intent4, intent4.resolveTypeIfNeeded(legacyActivityStarterInternalImpl2.context.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptions.toBundle(), userHandle3.getIdentifier());
                            } catch (RemoteException e) {
                                Log.w("LegacyActivityStarterInternalImpl", "Unable to start activity", e);
                            }
                            return Integer.valueOf(iArr2[0]);
                        }
                    };
                    ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
                    activityTransitionAnimator.startIntentWithAnimation(controller2, z6, str2, false, function1);
                    ActivityStarter.Callback callback2 = callback;
                    if (callback2 != null) {
                        callback2.onActivityStarted(iArr[0]);
                    }
                }
            };
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$cancelRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStarter.Callback callback2 = ActivityStarter.Callback.this;
                    if (callback2 != null) {
                        callback2.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            executeRunnableDismissingKeyguard(runnable, runnable2, z4, wouldLaunchResolverActivity, !(keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) || (this.communalSettingsInteractor.isCommunalFlagEnabled() && ((Boolean) this.communalSceneInteractor.isCommunalVisible.$$delegate_0.getValue()).booleanValue() && z4), false, str);
        }
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startCameraActivity(final Intent intent, boolean z, final ActivityStarter.Callback callback) {
        Runnable runnable;
        Runnable runnable2;
        boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
        Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startCameraActivity$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                int displayId;
                ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                try {
                    ActivityManagerNative.getDefault().resumeAppSwitches();
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setForceLaunchWindowingMode(1);
                    if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                        Display display = LegacyActivityStarterInternalImpl.this.context.getDisplay();
                        display.getClass();
                        displayId = display.getDisplayId();
                    } else {
                        displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(LegacyActivityStarterInternalImpl.this);
                    }
                    makeBasic.setLaunchDisplayId(displayId);
                    IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                    String basePackageName = LegacyActivityStarterInternalImpl.this.context.getBasePackageName();
                    Intent intent2 = intent;
                    i = iActivityManager.startActivityAsUser((IApplicationThread) null, basePackageName, intent2, intent2.resolveTypeIfNeeded(LegacyActivityStarterInternalImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                } catch (RemoteException e) {
                    Log.w("LegacyActivityStarterInternalImpl", "Unable to start activity", e);
                    i = -96;
                }
                ActivityStarter.Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.onActivityStarted(i);
                }
            }
        };
        Runnable runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startCameraActivity$cancelRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarter.Callback callback2 = ActivityStarter.Callback.this;
                if (callback2 != null) {
                    callback2.onActivityStarted(-96);
                }
            }
        };
        if (wouldLaunchResolverActivity) {
            runnable = runnable4;
            runnable2 = runnable3;
        } else {
            runnable3.run();
            runnable2 = null;
            runnable = null;
        }
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(this, runnable2, runnable, z, wouldLaunchResolverActivity, true, false, null, 96);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent, final boolean z, final Runnable runnable, View view, ActivityTransitionAnimator.Controller controller, boolean z2, boolean z3, final Intent intent, final Bundle bundle, final String str) {
        ActivityTransitionAnimator.Controller controller2;
        DelegateTransitionAnimatorController delegateTransitionAnimatorController;
        final boolean z4;
        if (view instanceof ExpandableNotificationRow) {
            CentralSurfaces centralSurfaces$1 = getCentralSurfaces$1();
            controller2 = centralSurfaces$1 != null ? ((CentralSurfacesImpl) centralSurfaces$1).mNotificationAnimationProvider.getAnimatorController((ExpandableNotificationRow) view) : null;
        } else {
            controller2 = controller;
        }
        boolean isActivity = pendingIntent.isActivity();
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockScreenUserManager;
        ActivityIntentHelper activityIntentHelper = this.activityIntentHelper;
        boolean z5 = isActivity && activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
        if (z2 && pendingIntent.isActivity() && (z3 || activityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent))) {
            delegateTransitionAnimatorController = null;
            z4 = true;
        } else {
            delegateTransitionAnimatorController = null;
            z4 = false;
        }
        if (!z5 && controller2 != null) {
            pendingIntent.isActivity();
        }
        final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(controller2, z, pendingIntent.isActivity());
        if (z4) {
            if (wrapAnimationControllerForShadeOrStatusBar$1 != null) {
                delegateTransitionAnimatorController = new DelegateTransitionAnimatorController(wrapAnimationControllerForShadeOrStatusBar$1) { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$wrapAnimationControllerForLockscreen$1$1
                    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onIntentStarted(boolean z6) {
                        this.delegate.onIntentStarted(z6);
                        if (z6) {
                            int i = LegacyActivityStarterInternalImpl.$r8$clinit;
                            CentralSurfaces centralSurfaces$12 = this.getCentralSurfaces$1();
                            if (centralSurfaces$12 != null) {
                                ((CentralSurfacesImpl) centralSurfaces$12).setIsLaunchingActivityOverLockscreen(true, z);
                            }
                        }
                    }

                    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onTransitionAnimationCancelled() {
                        int i = LegacyActivityStarterInternalImpl.$r8$clinit;
                        CentralSurfaces centralSurfaces$12 = this.getCentralSurfaces$1();
                        if (centralSurfaces$12 != null) {
                            ((CentralSurfacesImpl) centralSurfaces$12).setIsLaunchingActivityOverLockscreen(false, false);
                        }
                        this.delegate.onTransitionAnimationCancelled();
                    }

                    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationEnd(boolean z6) {
                        int i = LegacyActivityStarterInternalImpl.$r8$clinit;
                        CentralSurfaces centralSurfaces$12 = this.getCentralSurfaces$1();
                        if (centralSurfaces$12 != null) {
                            ((CentralSurfacesImpl) centralSurfaces$12).setIsLaunchingActivityOverLockscreen(false, false);
                        }
                        this.delegate.onTransitionAnimationEnd(z6);
                    }

                    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationStart(boolean z6) {
                        super.onTransitionAnimationStart(z6);
                        LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this;
                        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl.keyguardStateController;
                        if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mKeyguardGoingAway) {
                            return;
                        }
                        Log.d("LegacyActivityStarterInternalImpl", "Setting occluded = true in #startActivity.");
                        ((KeyguardViewMediator) legacyActivityStarterInternalImpl.keyguardViewMediatorLazy.get()).setOccluded(true, true);
                    }
                };
            }
            wrapAnimationControllerForShadeOrStatusBar$1 = delegateTransitionAnimatorController;
        }
        boolean z6 = isCommunalWidgetLaunch() && !z4;
        final boolean z7 = z || z6;
        final boolean z8 = false;
        final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller3 = wrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z9 = z8;
                    String creatorPackage = pendingIntent.getCreatorPackage();
                    boolean z10 = z4;
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = LegacyActivityStarterInternalImpl.this;
                    final PendingIntent pendingIntent2 = pendingIntent;
                    final Intent intent2 = intent;
                    final Bundle bundle2 = bundle;
                    ActivityTransitionAnimator.PendingIntentStarter pendingIntentStarter = new ActivityTransitionAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1.1
                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.PendingIntentStarter
                        public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                            Bundle activityOptions = CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl2.displayId, remoteAnimationAdapter);
                            Bundle bundle3 = bundle2;
                            if (bundle3 != null) {
                                activityOptions.putAll(bundle3);
                            }
                            ActivityOptions activityOptions2 = new ActivityOptions(activityOptions);
                            activityOptions2.setEligibleForLegacyPermissionPrompt(true);
                            activityOptions2.setPendingIntentBackgroundActivityStartMode(1);
                            return pendingIntent2.sendAndReturnResult(legacyActivityStarterInternalImpl2.context, 0, intent2, null, null, null, activityOptions2.toBundle());
                        }
                    };
                    activityTransitionAnimator.getClass();
                    activityTransitionAnimator.startIntentWithAnimation(controller3, z9, creatorPackage, z10, new ActivityTransitionAnimator$$ExternalSyntheticLambda0(pendingIntentStarter));
                } catch (PendingIntent.CanceledException e) {
                    Log.w("LegacyActivityStarterInternalImpl", "Sending intent failed: " + e);
                    if (!z7) {
                        ((ShadeController) LegacyActivityStarterInternalImpl.this.shadeControllerLazy.get()).collapseOnMainThread();
                    }
                }
                if (pendingIntent.isActivity()) {
                    ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                }
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                    int i = LegacyActivityStarterInternalImpl.$r8$clinit;
                    legacyActivityStarterInternalImpl2.mainExecutor.executeDelayed(runnable3, 0);
                }
            }
        };
        DelayableExecutor delayableExecutor = this.mainExecutor;
        if (z4) {
            delayableExecutor.executeDelayed(runnable2, 0);
            return;
        }
        final boolean z9 = z7;
        final boolean z10 = z5;
        final boolean z11 = z6;
        delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.executeRunnableDismissingKeyguard$default(LegacyActivityStarterInternalImpl.this, runnable2, null, z9, z10, z11, z8, str, 2);
            }
        }, 0);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void unregisterTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie) {
        TransitionAnimator.Companion.getClass();
        this.activityTransitionAnimator.unregister(transitionCookie);
    }

    public final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar$1(ActivityTransitionAnimator.Controller controller, boolean z, boolean z2) {
        if (controller == null) {
            return null;
        }
        View rootView = controller.getTransitionContainer().getRootView();
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.statusBarWindowControllerStore.getDefaultDisplay();
        rootView.getClass();
        Optional wrapAnimationControllerIfInStatusBar = ((StatusBarWindowControllerImpl) statusBarWindowController).wrapAnimationControllerIfInStatusBar(rootView, controller);
        if (wrapAnimationControllerIfInStatusBar.isPresent()) {
            return (ActivityTransitionAnimator.Controller) wrapAnimationControllerIfInStatusBar.get();
        }
        if (getCentralSurfaces$1() == null || !z) {
            return controller;
        }
        return new StatusBarTransitionAnimatorController(controller, this.shadeAnimationInteractor, (ShadeController) this.shadeControllerLazy.get(), (NotificationShadeWindowController) this.notifShadeWindowControllerLazy.get(), this.commandQueue, this.displayId, z2);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivityDismissingKeyguard(final Intent intent, boolean z, boolean z2, final boolean z3, final ActivityStarter.Callback callback, final int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, final int i2) {
        final UserHandle activityUserHandle$1 = userHandle == null ? getActivityUserHandle$1(intent) : userHandle;
        if (!z || ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get()) {
            KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
            boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
            final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(controller, z2, true);
            boolean z4 = false;
            boolean z5 = z2 && wrapAnimationControllerForShadeOrStatusBar$1 == null;
            final boolean z6 = false;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                    intent.setFlags(335544320);
                    intent.addFlags(i);
                    final int[] iArr = {-96};
                    ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller2 = wrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z7 = z6;
                    String str = intent.getPackage();
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = LegacyActivityStarterInternalImpl.this;
                    final boolean z8 = z3;
                    final Intent intent2 = intent;
                    final int i3 = i2;
                    final UserHandle userHandle2 = activityUserHandle$1;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2$$ExternalSyntheticLambda0
                        /* JADX WARN: Removed duplicated region for block: B:10:0x0056  */
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object mo779invoke(java.lang.Object r15) {
                            /*
                                r14 = this;
                                android.content.Intent r4 = r3
                                int[] r13 = r5
                                android.os.UserHandle r0 = r6
                                r1 = r15
                                android.view.RemoteAnimationAdapter r1 = (android.view.RemoteAnimationAdapter) r1
                                android.app.ActivityOptions r2 = new android.app.ActivityOptions
                                com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r3 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this
                                int r5 = r3.displayId
                                android.os.Bundle r1 = com.android.systemui.statusbar.phone.CentralSurfaces.getActivityOptions(r5, r1)
                                r2.<init>(r1)
                                r2.setDismissKeyguardIfInsecure()
                                boolean r1 = r2
                                r2.setDisallowEnterPictureInPictureWhileLaunching(r1)
                                com.android.systemui.camera.CameraIntents$Companion r1 = com.android.systemui.camera.CameraIntents.Companion
                                r1.getClass()
                                com.android.systemui.statusbar.KeyguardShortcutManager$Companion r1 = com.android.systemui.statusbar.KeyguardShortcutManager.Companion
                                r1.getClass()
                                android.content.Intent r1 = com.android.systemui.statusbar.KeyguardShortcutManager.INSECURE_CAMERA_INTENT
                                boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r1)
                                r5 = 1
                                if (r1 == 0) goto L5f
                                com.android.systemui.statusbar.phone.CentralSurfaces r1 = r3.getCentralSurfaces$1()
                                if (r1 == 0) goto L49
                                android.content.ComponentName r6 = r4.getComponent()
                                com.android.systemui.statusbar.phone.CentralSurfacesImpl r1 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r1
                                boolean r1 = r1.isForegroundComponentName(r6)
                                if (r1 != r5) goto L49
                                r1 = 270532608(0x10200000, float:3.1554436E-29)
                                r4.setFlags(r1)
                                goto L52
                            L49:
                                r1 = 536870912(0x20000000, float:1.0842022E-19)
                                android.content.Intent r1 = r4.addFlags(r1)
                                r1.getClass()
                            L52:
                                int r14 = r4
                                if (r14 != r5) goto L5b
                                java.lang.String r14 = "isQuickLaunchMode"
                                r4.putExtra(r14, r5)
                            L5b:
                                r14 = 3
                                r2.setRotationAnimationHint(r14)
                            L5f:
                                java.lang.String r14 = r4.getAction()
                                java.lang.String r1 = "android.settings.panel.action.VOLUME"
                                boolean r14 = r1.equals(r14)
                                if (r14 == 0) goto L6e
                                r2.setDisallowEnterPictureInPictureWhileLaunching(r5)
                            L6e:
                                boolean r14 = com.android.systemui.LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY
                                if (r14 == 0) goto L85
                                com.android.systemui.Dependency r14 = com.android.systemui.Dependency.sDependency
                                java.lang.Class<com.android.systemui.keyguard.DisplayLifecycle> r1 = com.android.systemui.keyguard.DisplayLifecycle.class
                                java.lang.Object r14 = r14.getDependencyInner(r1)
                                com.android.systemui.keyguard.DisplayLifecycle r14 = (com.android.systemui.keyguard.DisplayLifecycle) r14
                                boolean r14 = r14.mIsFolderOpened
                                if (r14 != 0) goto L85
                                int r14 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.access$getSubDisplayID(r3)
                                goto L92
                            L85:
                                android.content.Context r14 = r3.context
                                android.view.Display r14 = r14.getDisplay()
                                r14.getClass()
                                int r14 = r14.getDisplayId()
                            L92:
                                r2.setLaunchDisplayId(r14)
                                r14 = 0
                                r1 = r0
                                android.app.IActivityTaskManager r0 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> Lca
                                android.content.Context r5 = r3.context     // Catch: android.os.RemoteException -> Lca
                                java.lang.String r5 = r5.getBasePackageName()     // Catch: android.os.RemoteException -> Lca
                                android.content.Context r6 = r3.context     // Catch: android.os.RemoteException -> Lca
                                java.lang.String r6 = r6.getAttributionTag()     // Catch: android.os.RemoteException -> Lca
                                android.content.Context r3 = r3.context     // Catch: android.os.RemoteException -> Lca
                                android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: android.os.RemoteException -> Lca
                                java.lang.String r3 = r4.resolveTypeIfNeeded(r3)     // Catch: android.os.RemoteException -> Lca
                                android.os.Bundle r11 = r2.toBundle()     // Catch: android.os.RemoteException -> Lca
                                int r12 = r1.getIdentifier()     // Catch: android.os.RemoteException -> Lca
                                r1 = 0
                                r2 = r5
                                r5 = r3
                                r3 = r6
                                r6 = 0
                                r7 = 0
                                r8 = 0
                                r9 = 268435456(0x10000000, float:2.524355E-29)
                                r10 = 0
                                int r0 = r0.startActivityAsUser(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)     // Catch: android.os.RemoteException -> Lca
                                r13[r14] = r0     // Catch: android.os.RemoteException -> Lca
                                goto Ld2
                            Lca:
                                r0 = move-exception
                                java.lang.String r1 = "LegacyActivityStarterInternalImpl"
                                java.lang.String r2 = "Unable to start activity"
                                android.util.Log.w(r1, r2, r0)
                            Ld2:
                                r14 = r13[r14]
                                java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
                                return r14
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2$$ExternalSyntheticLambda0.mo779invoke(java.lang.Object):java.lang.Object");
                        }
                    };
                    ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
                    activityTransitionAnimator.startIntentWithAnimation(controller2, z7, str, false, function1);
                    ActivityStarter.Callback callback2 = callback;
                    if (callback2 != null) {
                        callback2.onActivityStarted(iArr[0]);
                    }
                }
            };
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$cancelRunnable$2
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStarter.Callback callback2 = ActivityStarter.Callback.this;
                    if (callback2 != null) {
                        callback2.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                z4 = true;
            }
            executeRunnableDismissingKeyguard(runnable, runnable2, z5, wouldLaunchResolverActivity, !z4, false, null);
        }
    }
}
