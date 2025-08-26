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
import android.content.res.Resources;
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
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import dagger.Lazy;
import java.util.Optional;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

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
        boolean zWillRunAnimationOnKeyguard = onDismissAction.willRunAnimationOnKeyguard();
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!zWillRunAnimationOnKeyguard && this.wakefulnessLifecycle.mWakefulness == 0 && ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen && !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide && ((DozeServiceHost) this.dozeServiceHostLazy.get()).mPulsing) {
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
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "dismissAction requested r=", " cancel=", " collapse=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i3, " after=", i4, " def=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i5, " will=", i6, ", msg=");
        sbM.append(i7);
        com.android.systemui.keyguard.Log.d("KeyguardUnlockInfo", sbM.toString());
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
                boolean zIsExpandedVisible = ((ShadeController) legacyActivityStarterInternalImpl.shadeControllerLazy.get()).isExpandedVisible();
                Lazy lazy = legacyActivityStarterInternalImpl.shadeControllerLazy;
                if (zIsExpandedVisible && !((StatusBarKeyguardViewManager) legacyActivityStarterInternalImpl.statusBarKeyguardViewManagerLazy.get()).isBouncerShowing()) {
                    Lazy lazy2 = legacyActivityStarterInternalImpl.keyguardViewMediatorLazy;
                    if (((KeyguardViewMediator) lazy2.get()).isShowing()) {
                        if (!((KeyguardViewMediator) lazy2.get()).mHelper.needsCollapsePanelWithNoAnimation()) {
                            legacyActivityStarterInternalImpl.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$executeRunnableDismissingKeyguard$onDismissAction$1$onDismiss$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((ShadeController) legacyActivityStarterInternalImpl.shadeControllerLazy.get()).collapseShade(false);
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

    public final UserHandle getActivityUserHandle$1(Intent intent) throws Resources.NotFoundException {
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
            public final Object mo781invoke(Object obj) {
                Intent intent2 = intent;
                UserHandle userHandle2 = activityUserHandle$1;
                LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this.f$0;
                return Integer.valueOf(TaskStackBuilder.create(legacyActivityStarterInternalImpl.context).addNextIntent(intent2).startActivities(CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl.displayId, (RemoteAnimationAdapter) obj), userHandle2));
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startActivityDismissingKeyguard(final Intent intent, boolean z, boolean z2, final ActivityStarter.Callback callback, final int i, ActivityTransitionAnimator.Controller controller, String str, final boolean z3, UserHandle userHandle) {
        final UserHandle activityUserHandle$1 = userHandle == null ? getActivityUserHandle$1(intent) : userHandle;
        if (!z2 || ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get()) {
            boolean zWouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
            final ActivityTransitionAnimator.Controller controllerWrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(controller, z, true);
            boolean z4 = z && controllerWrapAnimationControllerForShadeOrStatusBar$1 == null;
            final boolean z5 = false;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) this.this$0.assistManagerLazy.get()).hideAssist();
                    Intent intent2 = intent;
                    intent2.setFlags((intent2.getFlags() & 131072) != 0 ? 268435456 : 335544320);
                    intent.addFlags(i);
                    final int[] iArr = {-96};
                    ActivityTransitionAnimator activityTransitionAnimator = this.this$0.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller2 = controllerWrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z6 = z5;
                    String str2 = intent.getPackage();
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this.this$0;
                    final boolean z7 = z3;
                    final Intent intent3 = intent;
                    final UserHandle userHandle2 = activityUserHandle$1;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            int displayId;
                            Intent intent4 = intent3;
                            int[] iArr2 = iArr;
                            UserHandle userHandle3 = userHandle2;
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = legacyActivityStarterInternalImpl;
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
                    ActivityStarter.Callback callback2 = callback;
                    if (callback2 != null) {
                        callback2.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            executeRunnableDismissingKeyguard(runnable, runnable2, z4, zWouldLaunchResolverActivity, !(keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) || (this.communalSettingsInteractor.isCommunalFlagEnabled() && ((Boolean) this.communalSceneInteractor.isCommunalVisible.$$delegate_0.getValue()).booleanValue() && z4), false, str);
        }
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startCameraActivity(final Intent intent, boolean z, final ActivityStarter.Callback callback) {
        Runnable runnable;
        Runnable runnable2;
        boolean zWouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
        Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startCameraActivity$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                int iStartActivityAsUser;
                int displayId;
                ((AssistManager) this.this$0.assistManagerLazy.get()).hideAssist();
                try {
                    ActivityManagerNative.getDefault().resumeAppSwitches();
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setForceLaunchWindowingMode(1);
                    if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                        Display display = this.this$0.context.getDisplay();
                        display.getClass();
                        displayId = display.getDisplayId();
                    } else {
                        displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(this.this$0);
                    }
                    activityOptionsMakeBasic.setLaunchDisplayId(displayId);
                    IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                    String basePackageName = this.this$0.context.getBasePackageName();
                    Intent intent2 = intent;
                    iStartActivityAsUser = iActivityManager.startActivityAsUser((IApplicationThread) null, basePackageName, intent2, intent2.resolveTypeIfNeeded(this.this$0.context.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                } catch (RemoteException e) {
                    Log.w("LegacyActivityStarterInternalImpl", "Unable to start activity", e);
                    iStartActivityAsUser = -96;
                }
                ActivityStarter.Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.onActivityStarted(iStartActivityAsUser);
                }
            }
        };
        Runnable runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startCameraActivity$cancelRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarter.Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.onActivityStarted(-96);
                }
            }
        };
        if (zWouldLaunchResolverActivity) {
            runnable = runnable4;
            runnable2 = runnable3;
        } else {
            runnable3.run();
            runnable2 = null;
            runnable = null;
        }
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(this, runnable2, runnable, z, zWouldLaunchResolverActivity, true, false, null, 96);
    }

    @Override // com.android.systemui.statusbar.phone.ActivityStarterInternal
    public final void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent, final boolean z, final Runnable runnable, View view, ActivityTransitionAnimator.Controller controller, boolean z2, boolean z3, final Intent intent, final Bundle bundle, final String str) {
        ActivityTransitionAnimator.Controller animatorController;
        DelegateTransitionAnimatorController delegateTransitionAnimatorController;
        final boolean z4;
        if (view instanceof ExpandableNotificationRow) {
            CentralSurfaces centralSurfaces$1 = getCentralSurfaces$1();
            animatorController = centralSurfaces$1 != null ? ((CentralSurfacesImpl) centralSurfaces$1).mNotificationAnimationProvider.getAnimatorController((ExpandableNotificationRow) view) : null;
        } else {
            animatorController = controller;
        }
        boolean zIsActivity = pendingIntent.isActivity();
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockScreenUserManager;
        ActivityIntentHelper activityIntentHelper = this.activityIntentHelper;
        boolean z5 = zIsActivity && activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
        if (z2 && pendingIntent.isActivity() && (z3 || activityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent))) {
            delegateTransitionAnimatorController = null;
            z4 = true;
        } else {
            delegateTransitionAnimatorController = null;
            z4 = false;
        }
        if (!z5 && animatorController != null) {
            pendingIntent.isActivity();
        }
        final ActivityTransitionAnimator.Controller controllerWrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(animatorController, z, pendingIntent.isActivity());
        if (z4) {
            if (controllerWrapAnimationControllerForShadeOrStatusBar$1 != null) {
                delegateTransitionAnimatorController = new DelegateTransitionAnimatorController(controllerWrapAnimationControllerForShadeOrStatusBar$1) { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$wrapAnimationControllerForLockscreen$1$1
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
            controllerWrapAnimationControllerForShadeOrStatusBar$1 = delegateTransitionAnimatorController;
        }
        boolean z6 = isCommunalWidgetLaunch() && !z4;
        final boolean z7 = z || z6;
        final boolean z8 = false;
        final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTransitionAnimator activityTransitionAnimator = this.this$0.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller2 = controllerWrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z9 = z8;
                    String creatorPackage = pendingIntent.getCreatorPackage();
                    boolean z10 = z4;
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this.this$0;
                    final PendingIntent pendingIntent2 = pendingIntent;
                    final Intent intent2 = intent;
                    final Bundle bundle2 = bundle;
                    ActivityTransitionAnimator.PendingIntentStarter pendingIntentStarter = new ActivityTransitionAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1.1
                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.PendingIntentStarter
                        public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = legacyActivityStarterInternalImpl;
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
                    activityTransitionAnimator.startIntentWithAnimation(controller2, z9, creatorPackage, z10, new ActivityTransitionAnimator$$ExternalSyntheticLambda0(pendingIntentStarter));
                } catch (PendingIntent.CanceledException e) {
                    Log.w("LegacyActivityStarterInternalImpl", "Sending intent failed: " + e);
                    if (!z7) {
                        ((ShadeController) this.this$0.shadeControllerLazy.get()).collapseOnMainThread();
                    }
                }
                if (pendingIntent.isActivity()) {
                    ((AssistManager) this.this$0.assistManagerLazy.get()).hideAssist();
                }
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = this.this$0;
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
        delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.startPendingIntentDismissingKeyguard.1
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
        Optional optionalWrapAnimationControllerIfInStatusBar = ((StatusBarWindowControllerImpl) statusBarWindowController).wrapAnimationControllerIfInStatusBar(rootView, controller);
        if (optionalWrapAnimationControllerIfInStatusBar.isPresent()) {
            return (ActivityTransitionAnimator.Controller) optionalWrapAnimationControllerIfInStatusBar.get();
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
            boolean zWouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
            final ActivityTransitionAnimator.Controller controllerWrapAnimationControllerForShadeOrStatusBar$1 = wrapAnimationControllerForShadeOrStatusBar$1(controller, z2, true);
            boolean z4 = false;
            boolean z5 = z2 && controllerWrapAnimationControllerForShadeOrStatusBar$1 == null;
            final boolean z6 = false;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) this.this$0.assistManagerLazy.get()).hideAssist();
                    intent.setFlags(335544320);
                    intent.addFlags(i);
                    final int[] iArr = {-96};
                    ActivityTransitionAnimator activityTransitionAnimator = this.this$0.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller2 = controllerWrapAnimationControllerForShadeOrStatusBar$1;
                    boolean z7 = z6;
                    String str = intent.getPackage();
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = this.this$0;
                    final boolean z8 = z3;
                    final Intent intent2 = intent;
                    final int i3 = i2;
                    final UserHandle userHandle2 = activityUserHandle$1;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2$$ExternalSyntheticLambda0
                        /* JADX WARN: Removed duplicated region for block: B:9:0x0049  */
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object mo781invoke(Object obj) {
                            int displayId;
                            Intent intent3 = intent2;
                            int[] iArr2 = iArr;
                            UserHandle userHandle3 = userHandle2;
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = legacyActivityStarterInternalImpl;
                            ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl2.displayId, (RemoteAnimationAdapter) obj));
                            activityOptions.setDismissKeyguardIfInsecure();
                            activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z8);
                            CameraIntents.Companion.getClass();
                            KeyguardShortcutManager.Companion.getClass();
                            if (Intrinsics.areEqual(intent3, KeyguardShortcutManager.INSECURE_CAMERA_INTENT)) {
                                CentralSurfaces centralSurfaces$1 = legacyActivityStarterInternalImpl2.getCentralSurfaces$1();
                                if (centralSurfaces$1 != null) {
                                    if (((CentralSurfacesImpl) centralSurfaces$1).isForegroundComponentName(intent3.getComponent())) {
                                        intent3.setFlags(270532608);
                                    } else {
                                        intent3.addFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS).getClass();
                                    }
                                    if (i3 == 1) {
                                        intent3.putExtra("isQuickLaunchMode", true);
                                    }
                                    activityOptions.setRotationAnimationHint(3);
                                }
                            }
                            if ("android.settings.panel.action.VOLUME".equals(intent3.getAction())) {
                                activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
                            }
                            if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                Display display = legacyActivityStarterInternalImpl2.context.getDisplay();
                                display.getClass();
                                displayId = display.getDisplayId();
                            } else {
                                displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(legacyActivityStarterInternalImpl2);
                            }
                            activityOptions.setLaunchDisplayId(displayId);
                            try {
                                iArr2[0] = ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, legacyActivityStarterInternalImpl2.context.getBasePackageName(), legacyActivityStarterInternalImpl2.context.getAttributionTag(), intent3, intent3.resolveTypeIfNeeded(legacyActivityStarterInternalImpl2.context.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptions.toBundle(), userHandle3.getIdentifier());
                            } catch (RemoteException e) {
                                Log.w("LegacyActivityStarterInternalImpl", "Unable to start activity", e);
                            }
                            return Integer.valueOf(iArr2[0]);
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
                    ActivityStarter.Callback callback2 = callback;
                    if (callback2 != null) {
                        callback2.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                z4 = true;
            }
            executeRunnableDismissingKeyguard(runnable, runnable2, z5, zWouldLaunchResolverActivity, !z4, false, null);
        }
    }
}
