package com.android.systemui.statusbar.phone;

import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DelegateLaunchAnimatorController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ActivityStarterImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.Map;
import java.util.Optional;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityStarterImpl implements ActivityStarter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final ActivityStarterInternal activityStarterInternal = new ActivityStarterInternal();
    public final Lazy assistManagerLazy;
    public final Lazy biometricUnlockControllerLazy;
    public final Lazy centralSurfacesOptLazy;
    public final Context context;
    public final DeviceProvisionedController deviceProvisionedController;
    public final Lazy dozeServiceHostLazy;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Lazy keyguardViewMediatorLazy;
    public final NotificationLockscreenUserManager lockScreenUserManager;
    public final DelayableExecutor mainExecutor;
    public final Lazy shadeControllerLazy;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final SysuiStatusBarStateController statusBarStateController;
    public final StatusBarWindowController statusBarWindowController;
    public final UserTracker userTracker;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActivityStarterInternal {
        public ActivityStarterInternal() {
        }

        public static /* synthetic */ void executeRunnableDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, int i) {
            activityStarterInternal.executeRunnableDismissingKeyguard((i & 1) != 0 ? null : runnable, (i & 2) != 0 ? null : runnable2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, false, null);
        }

        public static void startActivityDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, final Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityLaunchAnimator.Controller controller, UserHandle userHandle, String str, int i2) {
            boolean z4 = false;
            boolean z5 = (i2 & 2) != 0 ? false : z;
            boolean z6 = (i2 & 4) != 0 ? false : z2;
            boolean z7 = (i2 & 8) != 0 ? false : z3;
            final ActivityStarter.Callback callback2 = (i2 & 16) != 0 ? null : callback;
            int i3 = (i2 & 32) != 0 ? 0 : i;
            ActivityLaunchAnimator.Controller controller2 = (i2 & 64) != 0 ? null : controller;
            UserHandle userHandle2 = (i2 & 128) != 0 ? null : userHandle;
            String str2 = (i2 & 256) == 0 ? str : null;
            if (userHandle2 == null) {
                userHandle2 = activityStarterInternal.getActivityUserHandle(intent);
            }
            ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            if (!z5 || ((DeviceProvisionedControllerImpl) activityStarterImpl.deviceProvisionedController).isDeviceProvisioned()) {
                KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
                boolean wouldLaunchResolverActivity = activityStarterImpl.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) activityStarterImpl.lockScreenUserManager).mCurrentUserId, intent);
                if (controller2 != null && !wouldLaunchResolverActivity) {
                    ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl);
                }
                final ActivityLaunchAnimator.Controller wrapAnimationController = activityStarterInternal.wrapAnimationController(controller2, z6, true);
                boolean z8 = z6 && wrapAnimationController == null;
                final ActivityStarterImpl activityStarterImpl2 = ActivityStarterImpl.this;
                final boolean z9 = false;
                final int i4 = i3;
                final ActivityStarter.Callback callback3 = callback2;
                final boolean z10 = z7;
                final UserHandle userHandle3 = userHandle2;
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$runnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((AssistManager) ActivityStarterImpl.this.assistManagerLazy.get()).hideAssist();
                        intent.setFlags(335544320);
                        intent.addFlags(i4);
                        final int[] iArr = {-96};
                        ActivityLaunchAnimator activityLaunchAnimator = ActivityStarterImpl.this.activityLaunchAnimator;
                        ActivityLaunchAnimator.Controller controller3 = wrapAnimationController;
                        boolean z11 = z9;
                        String str3 = intent.getPackage();
                        final ActivityStarterImpl activityStarterImpl3 = ActivityStarterImpl.this;
                        final boolean z12 = z10;
                        final Intent intent2 = intent;
                        final UserHandle userHandle4 = userHandle3;
                        activityLaunchAnimator.startIntentWithAnimation(controller3, z11, str3, false, new Function1() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$runnable$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                int displayId;
                                CentralSurfaces access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                                Intrinsics.checkNotNull(access$getCentralSurfaces);
                                ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) access$getCentralSurfaces).mDisplayId, (RemoteAnimationAdapter) obj));
                                activityOptions.setDismissKeyguard();
                                activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z12);
                                CameraIntents.Companion companion = CameraIntents.Companion;
                                Intent intent3 = intent2;
                                companion.getClass();
                                KeyguardShortcutManager.Companion.getClass();
                                if (Intrinsics.areEqual(intent3, KeyguardShortcutManager.INSECURE_CAMERA_INTENT)) {
                                    activityOptions.setRotationAnimationHint(3);
                                }
                                if (Intrinsics.areEqual("android.settings.panel.action.VOLUME", intent2.getAction())) {
                                    activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
                                }
                                if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                                    Display display = ActivityStarterImpl.this.context.getDisplay();
                                    Intrinsics.checkNotNull(display);
                                    displayId = display.getDisplayId();
                                } else {
                                    displayId = ActivityStarterImpl.access$getSubDisplayID(ActivityStarterImpl.this);
                                }
                                activityOptions.setLaunchDisplayId(displayId);
                                try {
                                    int[] iArr2 = iArr;
                                    IActivityTaskManager service = ActivityTaskManager.getService();
                                    String basePackageName = ActivityStarterImpl.this.context.getBasePackageName();
                                    String attributionTag = ActivityStarterImpl.this.context.getAttributionTag();
                                    Intent intent4 = intent2;
                                    iArr2[0] = service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent4, intent4.resolveTypeIfNeeded(ActivityStarterImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, activityOptions.toBundle(), userHandle4.getIdentifier());
                                } catch (RemoteException e) {
                                    Log.w("ActivityStarterImpl", "Unable to start activity", e);
                                }
                                return Integer.valueOf(iArr[0]);
                            }
                        });
                        ActivityStarter.Callback callback4 = callback3;
                        if (callback4 != null) {
                            callback4.onActivityStarted(iArr[0]);
                        }
                    }
                };
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$cancelRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityStarter.Callback callback4 = ActivityStarter.Callback.this;
                        if (callback4 != null) {
                            callback4.onActivityStarted(-96);
                        }
                    }
                };
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) activityStarterImpl.keyguardStateController;
                if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                    z4 = true;
                }
                activityStarterInternal.executeRunnableDismissingKeyguard(runnable, runnable2, z8, wouldLaunchResolverActivity, !z4, false, str2);
            }
        }

        public static void startPendingIntentDismissingKeyguard$default(final ActivityStarterInternal activityStarterInternal, final PendingIntent pendingIntent, Runnable runnable, View view, ActivityLaunchAnimator.Controller controller, int i) {
            boolean z;
            final Runnable runnable2 = (i & 2) != 0 ? null : runnable;
            View view2 = (i & 4) != 0 ? null : view;
            final ActivityLaunchAnimator.Controller controller2 = (i & 8) != 0 ? null : controller;
            activityStarterInternal.getClass();
            boolean z2 = view2 instanceof ExpandableNotificationRow;
            ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            if (z2) {
                CentralSurfaces access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl);
                controller2 = access$getCentralSurfaces != null ? ((CentralSurfacesImpl) access$getCentralSurfaces).mNotificationAnimationProvider.getAnimatorController((ExpandableNotificationRow) view2, null) : null;
            }
            if (pendingIntent.isActivity()) {
                if (activityStarterImpl.activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) activityStarterImpl.lockScreenUserManager).mCurrentUserId, pendingIntent) == null) {
                    z = true;
                    if (!z && controller2 != null && ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl) != null) {
                        pendingIntent.isActivity();
                    }
                    final ActivityStarterImpl activityStarterImpl2 = ActivityStarterImpl.this;
                    final boolean z3 = false;
                    final boolean z4 = true;
                    executeRunnableDismissingKeyguard$default(activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startPendingIntentDismissingKeyguard$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CentralSurfaces access$getCentralSurfaces2;
                            try {
                                ActivityLaunchAnimator.Controller wrapAnimationController = ActivityStarterImpl.ActivityStarterInternal.this.wrapAnimationController(controller2, true, pendingIntent.isActivity());
                                ActivityLaunchAnimator activityLaunchAnimator = activityStarterImpl2.activityLaunchAnimator;
                                boolean z5 = z3;
                                String creatorPackage = pendingIntent.getCreatorPackage();
                                final ActivityStarterImpl activityStarterImpl3 = activityStarterImpl2;
                                final PendingIntent pendingIntent2 = pendingIntent;
                                activityLaunchAnimator.startPendingIntentWithAnimation(wrapAnimationController, z5, creatorPackage, new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startPendingIntentDismissingKeyguard$1.1
                                    @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                    public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                        CentralSurfaces access$getCentralSurfaces3 = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                                        Intrinsics.checkNotNull(access$getCentralSurfaces3);
                                        ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) access$getCentralSurfaces3).mDisplayId, remoteAnimationAdapter));
                                        activityOptions.setEligibleForLegacyPermissionPrompt(true);
                                        activityOptions.setPendingIntentBackgroundActivityStartMode(1);
                                        return pendingIntent2.sendAndReturnResult(null, 0, null, null, null, null, activityOptions.toBundle());
                                    }
                                });
                            } catch (PendingIntent.CanceledException e) {
                                Log.w("ActivityStarterImpl", "Sending intent failed: " + e);
                                if (!z4 && (access$getCentralSurfaces2 = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl2)) != null) {
                                    ((CentralSurfacesImpl) access$getCentralSurfaces2).collapsePanelOnMainThread();
                                }
                            }
                            if (pendingIntent.isActivity()) {
                                ((AssistManager) activityStarterImpl2.assistManagerLazy.get()).hideAssist();
                            }
                            Runnable runnable3 = runnable2;
                            if (runnable3 != null) {
                                ActivityStarterImpl activityStarterImpl4 = activityStarterImpl2;
                                int i2 = ActivityStarterImpl.$r8$clinit;
                                activityStarterImpl4.postOnUiThread(0, runnable3);
                            }
                        }
                    }, null, true, z, false, 82);
                }
            }
            z = false;
            if (!z) {
                pendingIntent.isActivity();
            }
            final ActivityStarterImpl activityStarterImpl22 = ActivityStarterImpl.this;
            final boolean z32 = false;
            final boolean z42 = true;
            executeRunnableDismissingKeyguard$default(activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startPendingIntentDismissingKeyguard$1
                @Override // java.lang.Runnable
                public final void run() {
                    CentralSurfaces access$getCentralSurfaces2;
                    try {
                        ActivityLaunchAnimator.Controller wrapAnimationController = ActivityStarterImpl.ActivityStarterInternal.this.wrapAnimationController(controller2, true, pendingIntent.isActivity());
                        ActivityLaunchAnimator activityLaunchAnimator = activityStarterImpl22.activityLaunchAnimator;
                        boolean z5 = z32;
                        String creatorPackage = pendingIntent.getCreatorPackage();
                        final ActivityStarterImpl activityStarterImpl3 = activityStarterImpl22;
                        final PendingIntent pendingIntent2 = pendingIntent;
                        activityLaunchAnimator.startPendingIntentWithAnimation(wrapAnimationController, z5, creatorPackage, new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startPendingIntentDismissingKeyguard$1.1
                            @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                            public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                CentralSurfaces access$getCentralSurfaces3 = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                                Intrinsics.checkNotNull(access$getCentralSurfaces3);
                                ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) access$getCentralSurfaces3).mDisplayId, remoteAnimationAdapter));
                                activityOptions.setEligibleForLegacyPermissionPrompt(true);
                                activityOptions.setPendingIntentBackgroundActivityStartMode(1);
                                return pendingIntent2.sendAndReturnResult(null, 0, null, null, null, null, activityOptions.toBundle());
                            }
                        });
                    } catch (PendingIntent.CanceledException e) {
                        Log.w("ActivityStarterImpl", "Sending intent failed: " + e);
                        if (!z42 && (access$getCentralSurfaces2 = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl22)) != null) {
                            ((CentralSurfacesImpl) access$getCentralSurfaces2).collapsePanelOnMainThread();
                        }
                    }
                    if (pendingIntent.isActivity()) {
                        ((AssistManager) activityStarterImpl22.assistManagerLazy.get()).hideAssist();
                    }
                    Runnable runnable3 = runnable2;
                    if (runnable3 != null) {
                        ActivityStarterImpl activityStarterImpl4 = activityStarterImpl22;
                        int i2 = ActivityStarterImpl.$r8$clinit;
                        activityStarterImpl4.postOnUiThread(0, runnable3);
                    }
                }
            }, null, true, z, false, 82);
        }

        public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
            CentralSurfaces access$getCentralSurfaces;
            boolean willRunAnimationOnKeyguard = onDismissAction.willRunAnimationOnKeyguard();
            ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            if (!willRunAnimationOnKeyguard && activityStarterImpl.wakefulnessLifecycle.mWakefulness == 0 && ((KeyguardStateControllerImpl) activityStarterImpl.keyguardStateController).mCanDismissLockScreen && !((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mLeaveOpenOnKeyguardHide && ((DozeServiceHost) activityStarterImpl.dozeServiceHostLazy.get()).mPulsing) {
                ((BiometricUnlockController) activityStarterImpl.biometricUnlockControllerLazy.get()).startWakeAndUnlock(2);
            }
            if (((KeyguardStateControllerImpl) activityStarterImpl.keyguardStateController).mShowing) {
                ((StatusBarKeyguardViewManager) activityStarterImpl.statusBarKeyguardViewManagerLazy.get()).dismissWithAction(onDismissAction, runnable, z, true, false);
                return;
            }
            if (activityStarterImpl.keyguardUpdateMonitor.mIsDreaming && (access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl)) != null) {
                ((CentralSurfacesImpl) access$getCentralSurfaces).awakenDreams();
            }
            onDismissAction.onDismiss();
        }

        public final void executeRunnableDismissingKeyguard(final Runnable runnable, Runnable runnable2, final boolean z, boolean z2, final boolean z3, final boolean z4, String str) {
            Map map = LogUtil.beginTimes;
            int i = runnable != null ? 1 : 0;
            int i2 = runnable2 != null ? 1 : 0;
            int i3 = str == null ? 0 : 1;
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("dismissAction requested r=", i, " cancel=", i2, " collapse=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, z ? 1 : 0, " after=", z2 ? 1 : 0, " def=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, z3 ? 1 : 0, " will=", z4 ? 1 : 0, ", msg=");
            m45m.append(i3);
            com.android.systemui.keyguard.Log.m138d("KeyguardUnlockInfo", m45m.toString());
            final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$executeRunnableDismissingKeyguard$onDismissAction$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    final ActivityStarterImpl activityStarterImpl2 = activityStarterImpl;
                    Runnable runnable3 = runnable;
                    if (runnable3 != null) {
                        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) activityStarterImpl2.keyguardStateController;
                        if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                            ((StatusBarKeyguardViewManager) activityStarterImpl2.statusBarKeyguardViewManagerLazy.get()).addAfterKeyguardGoneRunnable(runnable3);
                        } else {
                            ((ExecutorImpl) activityStarterImpl2.mainExecutor).execute(runnable3);
                        }
                    }
                    boolean z5 = z;
                    boolean z6 = z3;
                    if (z5) {
                        if (!((ShadeControllerImpl) ((ShadeController) activityStarterImpl2.shadeControllerLazy.get())).mExpandedVisible || ((StatusBarKeyguardViewManager) activityStarterImpl2.statusBarKeyguardViewManagerLazy.get()).isBouncerShowing()) {
                            activityStarterImpl2.postOnUiThread(0, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$executeRunnableDismissingKeyguard$onDismissAction$1$onDismiss$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((ShadeControllerImpl) ((ShadeController) ActivityStarterImpl.this.shadeControllerLazy.get())).runPostCollapseRunnables();
                                }
                            });
                        } else {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = ((KeyguardViewMediator) activityStarterImpl2.keyguardViewMediatorLazy.get()).mHelper;
                            boolean z7 = ((CentralSurfacesImpl) ((CentralSurfaces) keyguardViewMediatorHelperImpl.centralSurfacesLazy.get())).isOccluded() || keyguardViewMediatorHelperImpl.getHandler().hasMessages(keyguardViewMediatorHelperImpl.getSET_OCCLUDED());
                            Lazy lazy = activityStarterImpl2.shadeControllerLazy;
                            if (z7) {
                                Log.d("CentralSurfaces", "collapseShade with no animation");
                                ((ShadeControllerImpl) ((ShadeController) lazy.get())).collapseShade(false);
                            } else {
                                ((ShadeControllerImpl) ((ShadeController) lazy.get())).animateCollapsePanels(1.0f, 2, true, true);
                            }
                        }
                    } else if (z6) {
                        Log.d("ActivityStarterImpl", "ignored deferred");
                        return false;
                    }
                    return z6;
                }

                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean willRunAnimationOnKeyguard() {
                    return z4;
                }
            }, runnable2, z2);
        }

        public final UserHandle getActivityUserHandle(Intent intent) {
            ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            for (String str : activityStarterImpl.context.getResources().getStringArray(R.array.system_ui_packages)) {
                if (intent.getComponent() == null) {
                    break;
                }
                if (Intrinsics.areEqual(str, intent.getComponent().getPackageName())) {
                    return new UserHandle(UserHandle.myUserId());
                }
            }
            return ((UserTrackerImpl) activityStarterImpl.userTracker).getUserHandle();
        }

        public final void startActivity(final Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2, UserHandle userHandle) {
            ActivityStarterInternal activityStarterInternal;
            final UserHandle userHandle2;
            CentralSurfaces access$getCentralSurfaces;
            CentralSurfaces access$getCentralSurfaces2;
            if (userHandle == null) {
                userHandle2 = getActivityUserHandle(intent);
                activityStarterInternal = this;
            } else {
                activityStarterInternal = this;
                userHandle2 = userHandle;
            }
            final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            if (activityStarterImpl.keyguardStateController.isUnlocked() || !z2) {
                startActivityDismissingKeyguard$default(this, intent, false, z, false, null, 0, controller, userHandle2, null, 256);
                return;
            }
            if (controller != null) {
                ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl);
            }
            if (z && (access$getCentralSurfaces2 = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl)) != null) {
                ((CentralSurfacesImpl) access$getCentralSurfaces2).collapseShade();
            }
            if (activityStarterImpl.keyguardUpdateMonitor.mIsDreaming && (access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl)) != null) {
                ((CentralSurfacesImpl) access$getCentralSurfaces).awakenDreams();
            }
            activityStarterImpl.activityLaunchAnimator.startIntentWithAnimation(null, false, intent.getPackage(), z2, new Function1() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivity$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    TaskStackBuilder addNextIntent = TaskStackBuilder.create(ActivityStarterImpl.this.context).addNextIntent(intent);
                    CentralSurfaces access$getCentralSurfaces3 = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                    Intrinsics.checkNotNull(access$getCentralSurfaces3);
                    return Integer.valueOf(addNextIntent.startActivities(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) access$getCentralSurfaces3).mDisplayId, (RemoteAnimationAdapter) obj), userHandle2));
                }
            });
        }

        public final ActivityLaunchAnimator.Controller wrapAnimationController(ActivityLaunchAnimator.Controller controller, boolean z, boolean z2) {
            Optional of;
            if (controller == null) {
                return null;
            }
            View rootView = controller.getLaunchContainer().getRootView();
            ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
            StatusBarWindowController statusBarWindowController = activityStarterImpl.statusBarWindowController;
            if (rootView != statusBarWindowController.mStatusBarWindowView) {
                of = Optional.empty();
            } else {
                controller.setLaunchContainer(statusBarWindowController.mLaunchAnimationContainer);
                of = Optional.of(new DelegateLaunchAnimatorController(controller) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController.2
                    public C34552(ActivityLaunchAnimator.Controller controller2) {
                        super(controller2);
                    }

                    @Override // com.android.systemui.animation.DelegateLaunchAnimatorController, com.android.systemui.animation.LaunchAnimator.Controller
                    public final void onLaunchAnimationEnd(boolean z3) {
                        this.delegate.onLaunchAnimationEnd(z3);
                        StatusBarWindowController statusBarWindowController2 = StatusBarWindowController.this;
                        State state = statusBarWindowController2.mCurrentState;
                        if (state.mIsLaunchAnimationRunning) {
                            state.mIsLaunchAnimationRunning = false;
                            statusBarWindowController2.apply(state, false);
                        }
                    }

                    @Override // com.android.systemui.animation.DelegateLaunchAnimatorController, com.android.systemui.animation.LaunchAnimator.Controller
                    public final void onLaunchAnimationStart(boolean z3) {
                        this.delegate.onLaunchAnimationStart(z3);
                        StatusBarWindowController statusBarWindowController2 = StatusBarWindowController.this;
                        State state = statusBarWindowController2.mCurrentState;
                        if (true == state.mIsLaunchAnimationRunning) {
                            return;
                        }
                        state.mIsLaunchAnimationRunning = true;
                        statusBarWindowController2.apply(state, false);
                    }
                });
            }
            if (of.isPresent()) {
                return (ActivityLaunchAnimator.Controller) of.get();
            }
            CentralSurfaces access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(activityStarterImpl);
            return (access$getCentralSurfaces == null || !z) ? controller2 : new StatusBarLaunchAnimatorController(controller2, access$getCentralSurfaces, z2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public ActivityStarterImpl(Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, ActivityLaunchAnimator activityLaunchAnimator, Context context, NotificationLockscreenUserManager notificationLockscreenUserManager, StatusBarWindowController statusBarWindowController, WakefulnessLifecycle wakefulnessLifecycle, KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, ActivityIntentHelper activityIntentHelper, DelayableExecutor delayableExecutor) {
        this.centralSurfacesOptLazy = lazy;
        this.assistManagerLazy = lazy2;
        this.dozeServiceHostLazy = lazy3;
        this.biometricUnlockControllerLazy = lazy4;
        this.keyguardViewMediatorLazy = lazy5;
        this.shadeControllerLazy = lazy6;
        this.statusBarKeyguardViewManagerLazy = lazy7;
        this.activityLaunchAnimator = activityLaunchAnimator;
        this.context = context;
        this.lockScreenUserManager = notificationLockscreenUserManager;
        this.statusBarWindowController = statusBarWindowController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardStateController = keyguardStateController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.deviceProvisionedController = deviceProvisionedController;
        this.userTracker = userTracker;
        this.activityIntentHelper = activityIntentHelper;
        this.mainExecutor = delayableExecutor;
    }

    public static final CentralSurfaces access$getCentralSurfaces(ActivityStarterImpl activityStarterImpl) {
        return (CentralSurfaces) ((Optional) activityStarterImpl.centralSurfacesOptLazy.get()).orElse(null);
    }

    public static final int access$getSubDisplayID(ActivityStarterImpl activityStarterImpl) {
        activityStarterImpl.getClass();
        ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
        return SubscreenUtil.getSubDisplay(activityStarterImpl.context).getDisplayId();
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        this.activityStarterInternal.dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3) {
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(this.activityStarterInternal, runnable, runnable2, z, z2, z3, 96);
    }

    public final void postOnUiThread(int i, Runnable runnable) {
        this.mainExecutor.executeDelayed(i, runnable);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSRunnableDismissingKeyguard(Runnable runnable) {
        postQSRunnableDismissingKeyguard(runnable, true);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent) {
        postOnUiThread(0, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, pendingIntent, null, null, null, 14);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, false, z, false, null, 0, null, null, null, 506);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(final Intent intent, boolean z, boolean z2, final boolean z3, final ActivityStarter.Callback callback, final int i, ActivityLaunchAnimator.Controller controller, UserHandle userHandle, final int i2) {
        ActivityStarterInternal activityStarterInternal = this.activityStarterInternal;
        final UserHandle activityUserHandle = userHandle == null ? activityStarterInternal.getActivityUserHandle(intent) : userHandle;
        ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
        if (!z || ((DeviceProvisionedControllerImpl) activityStarterImpl.deviceProvisionedController).isDeviceProvisioned()) {
            KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
            boolean wouldLaunchResolverActivity = activityStarterImpl.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) activityStarterImpl.lockScreenUserManager).mCurrentUserId, intent);
            if (controller != null && !wouldLaunchResolverActivity) {
                access$getCentralSurfaces(activityStarterImpl);
            }
            final ActivityLaunchAnimator.Controller wrapAnimationController = activityStarterInternal.wrapAnimationController(controller, z2, true);
            boolean z4 = false;
            boolean z5 = z2 && wrapAnimationController == null;
            final ActivityStarterImpl activityStarterImpl2 = ActivityStarterImpl.this;
            final boolean z6 = false;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$runnable$2
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) ActivityStarterImpl.this.assistManagerLazy.get()).hideAssist();
                    intent.setFlags(335544320);
                    intent.addFlags(i);
                    final int[] iArr = {-96};
                    ActivityLaunchAnimator activityLaunchAnimator = ActivityStarterImpl.this.activityLaunchAnimator;
                    ActivityLaunchAnimator.Controller controller2 = wrapAnimationController;
                    boolean z7 = z6;
                    String str = intent.getPackage();
                    final ActivityStarterImpl activityStarterImpl3 = ActivityStarterImpl.this;
                    final boolean z8 = z3;
                    final Intent intent2 = intent;
                    final int i3 = i2;
                    final UserHandle userHandle2 = activityUserHandle;
                    activityLaunchAnimator.startIntentWithAnimation(controller2, z7, str, false, new Function1() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$runnable$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:12:0x0068  */
                        /* JADX WARN: Removed duplicated region for block: B:14:0x005d  */
                        /* JADX WARN: Removed duplicated region for block: B:9:0x0055  */
                        @Override // kotlin.jvm.functions.Function1
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj) {
                            int displayId;
                            boolean z9;
                            CentralSurfaces access$getCentralSurfaces = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                            Intrinsics.checkNotNull(access$getCentralSurfaces);
                            ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) access$getCentralSurfaces).mDisplayId, (RemoteAnimationAdapter) obj));
                            activityOptions.setDismissKeyguard();
                            activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z8);
                            CameraIntents.Companion companion = CameraIntents.Companion;
                            Intent intent3 = intent2;
                            companion.getClass();
                            KeyguardShortcutManager.Companion.getClass();
                            if (Intrinsics.areEqual(intent3, KeyguardShortcutManager.INSECURE_CAMERA_INTENT)) {
                                CentralSurfaces access$getCentralSurfaces2 = ActivityStarterImpl.access$getCentralSurfaces(ActivityStarterImpl.this);
                                if (access$getCentralSurfaces2 != null) {
                                    if (((CentralSurfacesImpl) access$getCentralSurfaces2).isForegroundComponentName(intent2.getComponent())) {
                                        z9 = true;
                                        if (z9) {
                                            intent2.addFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
                                        } else {
                                            intent2.setFlags(270532608);
                                        }
                                        if (i3 == 1) {
                                            intent2.putExtra("isQuickLaunchMode", true);
                                        }
                                        activityOptions.setRotationAnimationHint(3);
                                    }
                                }
                                z9 = false;
                                if (z9) {
                                }
                                if (i3 == 1) {
                                }
                                activityOptions.setRotationAnimationHint(3);
                            }
                            if (Intrinsics.areEqual("android.settings.panel.action.VOLUME", intent2.getAction())) {
                                activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
                            }
                            if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                                Display display = ActivityStarterImpl.this.context.getDisplay();
                                Intrinsics.checkNotNull(display);
                                displayId = display.getDisplayId();
                            } else {
                                displayId = ActivityStarterImpl.access$getSubDisplayID(ActivityStarterImpl.this);
                            }
                            activityOptions.setLaunchDisplayId(displayId);
                            try {
                                int[] iArr2 = iArr;
                                IActivityTaskManager service = ActivityTaskManager.getService();
                                String basePackageName = ActivityStarterImpl.this.context.getBasePackageName();
                                String attributionTag = ActivityStarterImpl.this.context.getAttributionTag();
                                Intent intent4 = intent2;
                                iArr2[0] = service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent4, intent4.resolveTypeIfNeeded(ActivityStarterImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, activityOptions.toBundle(), userHandle2.getIdentifier());
                            } catch (RemoteException e) {
                                Log.w("ActivityStarterImpl", "Unable to start activity", e);
                            }
                            return Integer.valueOf(iArr[0]);
                        }
                    });
                    ActivityStarter.Callback callback2 = callback;
                    if (callback2 != null) {
                        callback2.onActivityStarted(iArr[0]);
                    }
                }
            };
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$ActivityStarterInternal$startActivityDismissingKeyguard$cancelRunnable$2
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStarter.Callback callback2 = ActivityStarter.Callback.this;
                    if (callback2 != null) {
                        callback2.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) activityStarterImpl.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                z4 = true;
            }
            activityStarterInternal.executeRunnableDismissingKeyguard(runnable, runnable2, z5, wouldLaunchResolverActivity, !z4, false, null);
        }
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startCameraActivity(final Intent intent, boolean z, final ActivityStarter.Callback callback) {
        Runnable runnable;
        Runnable runnable2;
        boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) this.lockScreenUserManager).mCurrentUserId, intent);
        Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$startCameraActivity$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                int displayId;
                ((AssistManager) ActivityStarterImpl.this.assistManagerLazy.get()).hideAssist();
                try {
                    ActivityManagerNative.getDefault().resumeAppSwitches();
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setForceLaunchWindowingMode(1);
                    if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                        Display display = ActivityStarterImpl.this.context.getDisplay();
                        Intrinsics.checkNotNull(display);
                        displayId = display.getDisplayId();
                    } else {
                        displayId = ActivityStarterImpl.access$getSubDisplayID(ActivityStarterImpl.this);
                    }
                    makeBasic.setLaunchDisplayId(displayId);
                    IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                    String basePackageName = ActivityStarterImpl.this.context.getBasePackageName();
                    Intent intent2 = intent;
                    i = iActivityManager.startActivityAsUser((IApplicationThread) null, basePackageName, intent2, intent2.resolveTypeIfNeeded(ActivityStarterImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                } catch (RemoteException e) {
                    Log.w("ActivityStarterImpl", "Unable to start activity", e);
                    i = -96;
                }
                ActivityStarter.Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.onActivityStarted(i);
                }
            }
        };
        Runnable runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$startCameraActivity$cancelRunnable$1
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
        executeRunnableDismissingKeyguard(runnable2, runnable, z, wouldLaunchResolverActivity, true);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, null, null, null, 14);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        this.activityStarterInternal.dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, String str) {
        this.activityStarterInternal.executeRunnableDismissingKeyguard(runnable, runnable2, z, z2, z3, z4, str);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSRunnableDismissingKeyguard(final Runnable runnable, final boolean z) {
        postOnUiThread(0, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_QUICK_TILE);
                final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
                ((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mLeaveOpenOnKeyguardHide = z;
                ActivityStarterImpl.ActivityStarterInternal activityStarterInternal = activityStarterImpl.activityStarterInternal;
                final Runnable runnable2 = runnable;
                ActivityStarterImpl.ActivityStarterInternal.executeRunnableDismissingKeyguard$default(activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            ActivityStarterImpl activityStarterImpl2 = activityStarterImpl;
                            int i = ActivityStarterImpl.$r8$clinit;
                            activityStarterImpl2.postOnUiThread(0, runnable3);
                        }
                    }
                }, null, false, true, false, 112);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, boolean z2) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, z2, false, null, 0, null, null, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, runnable, null, null, 12);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final boolean z) {
        postOnUiThread(0, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$2
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    ActivityStarterImpl activityStarterImpl = this;
                    if (((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mState == 2 && !activityStarterImpl.keyguardUpdateMonitor.isSecure()) {
                        this.statusBarStateController.setState$1(1);
                    }
                }
                ActivityStarterImpl.ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, null, null, null, 14);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, false, z, false, callback, 0, null, null, null, 490);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, runnable, view, null, 8);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, boolean z2, int i) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, z2, false, null, i, null, null, null, 472);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, runnable, null, controller, 4);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final ActivityLaunchAnimator.Controller controller) {
        postOnUiThread(0, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$3
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, pendingIntent, null, null, controller, 6);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2) {
        this.activityStarterInternal.startActivity(intent, z, controller, z2, null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i) {
        postOnUiThread(i, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$4
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, false, null, 0, null, null, null, 504);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2, UserHandle userHandle) {
        this.activityStarterInternal.startActivity(intent, z, controller, z2, userHandle);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i, final ActivityLaunchAnimator.Controller controller) {
        postOnUiThread(i, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$5
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, false, null, 0, controller, null, null, 440);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i, final ActivityLaunchAnimator.Controller controller, final String str) {
        postOnUiThread(i, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$6
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, false, null, 0, controller, null, str, 184);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, z2, false, null, 0, null, null, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityLaunchAnimator.Controller controller, UserHandle userHandle) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, z2, z3, callback, i, controller, userHandle, null, 256);
    }
}
