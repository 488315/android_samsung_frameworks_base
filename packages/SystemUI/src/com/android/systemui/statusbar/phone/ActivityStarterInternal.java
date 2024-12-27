package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public interface ActivityStarterInternal {
    static /* synthetic */ void executeRunnableDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, int i) {
        if ((i & 2) != 0) {
            runnable2 = null;
        }
        ((LegacyActivityStarterInternalImpl) activityStarterInternal).executeRunnableDismissingKeyguard(runnable, runnable2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, null);
    }

    static void startActivityDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, final Intent intent, boolean z, boolean z2, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, String str, boolean z3, UserHandle userHandle, int i2) {
        boolean z4 = false;
        boolean z5 = (i2 & 4) != 0 ? false : z2;
        final ActivityStarter.Callback callback2 = (i2 & 8) != 0 ? null : callback;
        int i3 = (i2 & 16) != 0 ? 0 : i;
        ActivityTransitionAnimator.Controller controller2 = (i2 & 32) != 0 ? null : controller;
        String str2 = (i2 & 64) != 0 ? null : str;
        boolean z6 = (i2 & 128) != 0 ? false : z3;
        UserHandle userHandle2 = (i2 & 256) == 0 ? userHandle : null;
        final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = (LegacyActivityStarterInternalImpl) activityStarterInternal;
        if (userHandle2 == null) {
            userHandle2 = legacyActivityStarterInternalImpl.getActivityUserHandle(intent);
        }
        if (!z5 || ((DeviceProvisionedControllerImpl) legacyActivityStarterInternalImpl.deviceProvisionedController).deviceProvisioned.get()) {
            boolean wouldLaunchResolverActivity = legacyActivityStarterInternalImpl.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) legacyActivityStarterInternalImpl.lockScreenUserManager).mCurrentUserId, intent);
            final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar = legacyActivityStarterInternalImpl.wrapAnimationControllerForShadeOrStatusBar(controller2, z, true);
            boolean z7 = z && wrapAnimationControllerForShadeOrStatusBar == null;
            final boolean z8 = false;
            final int i4 = i3;
            final ActivityStarter.Callback callback3 = callback2;
            final boolean z9 = z6;
            final UserHandle userHandle3 = userHandle2;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                    Intent intent2 = intent;
                    intent2.setFlags((intent2.getFlags() & 131072) != 0 ? QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE : 335544320);
                    intent.addFlags(i4);
                    final int[] iArr = {-96};
                    ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller3 = wrapAnimationControllerForShadeOrStatusBar;
                    boolean z10 = z8;
                    String str3 = intent.getPackage();
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                    final boolean z11 = z9;
                    final Intent intent3 = intent;
                    final UserHandle userHandle4 = userHandle3;
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            int displayId;
                            ActivityOptions activityOptions = new ActivityOptions(CentralSurfaces.getActivityOptions(LegacyActivityStarterInternalImpl.this.displayId, (RemoteAnimationAdapter) obj));
                            activityOptions.setDismissKeyguardIfInsecure();
                            activityOptions.setDisallowEnterPictureInPictureWhileLaunching(z11);
                            CameraIntents.Companion companion = CameraIntents.Companion;
                            Intent intent4 = intent3;
                            companion.getClass();
                            KeyguardShortcutManager.Companion.getClass();
                            if (Intrinsics.areEqual(intent4, KeyguardShortcutManager.INSECURE_CAMERA_INTENT)) {
                                activityOptions.setRotationAnimationHint(3);
                            }
                            if ("android.settings.panel.action.VOLUME".equals(intent3.getAction())) {
                                activityOptions.setDisallowEnterPictureInPictureWhileLaunching(true);
                            }
                            if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                Display display = LegacyActivityStarterInternalImpl.this.context.getDisplay();
                                Intrinsics.checkNotNull(display);
                                displayId = display.getDisplayId();
                            } else {
                                displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(LegacyActivityStarterInternalImpl.this);
                            }
                            activityOptions.setLaunchDisplayId(displayId);
                            try {
                                int[] iArr2 = iArr;
                                IActivityTaskManager service = ActivityTaskManager.getService();
                                String basePackageName = LegacyActivityStarterInternalImpl.this.context.getBasePackageName();
                                String attributionTag = LegacyActivityStarterInternalImpl.this.context.getAttributionTag();
                                Intent intent5 = intent3;
                                iArr2[0] = service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent5, intent5.resolveTypeIfNeeded(LegacyActivityStarterInternalImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, activityOptions.toBundle(), userHandle4.getIdentifier());
                            } catch (RemoteException e) {
                                Log.w("LegacyActivityStarterInternalImpl", "Unable to start activity", e);
                            }
                            return Integer.valueOf(iArr[0]);
                        }
                    };
                    ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
                    activityTransitionAnimator.startIntentWithAnimation(controller3, z10, str3, false, function1);
                    ActivityStarter.Callback callback4 = callback3;
                    if (callback4 != null) {
                        callback4.onActivityStarted(iArr[0]);
                    }
                }
            };
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$cancelRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStarter.Callback callback4 = ActivityStarter.Callback.this;
                    if (callback4 != null) {
                        callback4.onActivityStarted(-96);
                    }
                }
            };
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
                z4 = true;
            }
            legacyActivityStarterInternalImpl.executeRunnableDismissingKeyguard(runnable, runnable2, z7, wouldLaunchResolverActivity, !z4, false, str2);
        }
    }

    static void startPendingIntentDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, final PendingIntent pendingIntent, final boolean z, Runnable runnable, View view, ActivityTransitionAnimator.Controller controller, boolean z2, boolean z3, Intent intent, Bundle bundle, int i) {
        final ActivityTransitionAnimator.Controller controller2 = null;
        final Runnable runnable2 = (i & 4) != 0 ? null : runnable;
        View view2 = (i & 8) != 0 ? null : view;
        ActivityTransitionAnimator.Controller controller3 = (i & 16) != 0 ? null : controller;
        boolean z4 = (i & 32) != 0 ? false : z2;
        boolean z5 = (i & 64) != 0 ? false : z3;
        final Intent intent2 = (i & 128) != 0 ? null : intent;
        final Bundle bundle2 = (i & 256) != 0 ? null : bundle;
        final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = (LegacyActivityStarterInternalImpl) activityStarterInternal;
        legacyActivityStarterInternalImpl.getClass();
        if (view2 instanceof ExpandableNotificationRow) {
            CentralSurfaces centralSurfaces = legacyActivityStarterInternalImpl.getCentralSurfaces();
            controller3 = centralSurfaces != null ? ((CentralSurfacesImpl) centralSurfaces).mNotificationAnimationProvider.getAnimatorController((ExpandableNotificationRow) view2, null) : null;
        }
        boolean isActivity = pendingIntent.isActivity();
        NotificationLockscreenUserManager notificationLockscreenUserManager = legacyActivityStarterInternalImpl.lockScreenUserManager;
        ActivityIntentHelper activityIntentHelper = legacyActivityStarterInternalImpl.activityIntentHelper;
        boolean z6 = isActivity && activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
        boolean z7 = z4 && pendingIntent.isActivity() && (z5 || activityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent));
        if (!z6 && controller3 != null) {
            pendingIntent.isActivity();
        }
        final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar = legacyActivityStarterInternalImpl.wrapAnimationControllerForShadeOrStatusBar(controller3, z, pendingIntent.isActivity());
        if (!z7) {
            controller2 = wrapAnimationControllerForShadeOrStatusBar;
        } else if (wrapAnimationControllerForShadeOrStatusBar != null) {
            controller2 = new DelegateTransitionAnimatorController(wrapAnimationControllerForShadeOrStatusBar) { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$wrapAnimationControllerForLockscreen$1$1
                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                public final void onIntentStarted(boolean z8) {
                    this.delegate.onIntentStarted(z8);
                    if (z8) {
                        int i2 = LegacyActivityStarterInternalImpl.$r8$clinit;
                        CentralSurfaces centralSurfaces2 = legacyActivityStarterInternalImpl.getCentralSurfaces();
                        if (centralSurfaces2 != null) {
                            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                            centralSurfacesImpl.mIsLaunchingActivityOverLockscreen = true;
                            centralSurfacesImpl.mDismissingShadeForActivityLaunch = z;
                            centralSurfacesImpl.mKeyguardViewMediator.launchingActivityOverLockscreen(true);
                        }
                    }
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                public final void onTransitionAnimationCancelled(Boolean bool) {
                    LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = legacyActivityStarterInternalImpl;
                    if (bool != null) {
                        ((KeyguardViewMediator) legacyActivityStarterInternalImpl2.keyguardViewMediatorLazy.get()).setOccluded(bool.booleanValue(), false);
                    }
                    int i2 = LegacyActivityStarterInternalImpl.$r8$clinit;
                    CentralSurfaces centralSurfaces2 = legacyActivityStarterInternalImpl2.getCentralSurfaces();
                    if (centralSurfaces2 != null) {
                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                        centralSurfacesImpl.mIsLaunchingActivityOverLockscreen = false;
                        centralSurfacesImpl.mDismissingShadeForActivityLaunch = false;
                        centralSurfacesImpl.mKeyguardViewMediator.launchingActivityOverLockscreen(false);
                    }
                    this.delegate.onTransitionAnimationCancelled(bool);
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final void onTransitionAnimationEnd(boolean z8) {
                    int i2 = LegacyActivityStarterInternalImpl.$r8$clinit;
                    CentralSurfaces centralSurfaces2 = legacyActivityStarterInternalImpl.getCentralSurfaces();
                    if (centralSurfaces2 != null) {
                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                        centralSurfacesImpl.mIsLaunchingActivityOverLockscreen = false;
                        centralSurfacesImpl.mDismissingShadeForActivityLaunch = false;
                        centralSurfacesImpl.mKeyguardViewMediator.launchingActivityOverLockscreen(false);
                    }
                    this.delegate.onTransitionAnimationEnd(z8);
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final void onTransitionAnimationStart(boolean z8) {
                    super.onTransitionAnimationStart(z8);
                    Flags.communalHub();
                    LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = legacyActivityStarterInternalImpl;
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl2.keyguardStateController;
                    if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mKeyguardGoingAway) {
                        return;
                    }
                    Log.d("LegacyActivityStarterInternalImpl", "Setting occluded = true in #startActivity.");
                    ((KeyguardViewMediator) legacyActivityStarterInternalImpl2.keyguardViewMediatorLazy.get()).setOccluded(true, true);
                }
            };
        }
        final boolean z8 = false;
        final boolean z9 = z7;
        final Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                    ActivityTransitionAnimator.Controller controller4 = controller2;
                    boolean z10 = z8;
                    String creatorPackage = pendingIntent.getCreatorPackage();
                    boolean z11 = z9;
                    final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                    final PendingIntent pendingIntent2 = pendingIntent;
                    final Intent intent3 = intent2;
                    final Bundle bundle3 = bundle2;
                    activityTransitionAnimator.startPendingIntentWithAnimation(controller4, z10, creatorPackage, z11, new ActivityTransitionAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$runnable$1.1
                        @Override // com.android.systemui.animation.ActivityTransitionAnimator.PendingIntentStarter
                        public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                            LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl3 = LegacyActivityStarterInternalImpl.this;
                            Bundle activityOptions = CentralSurfaces.getActivityOptions(legacyActivityStarterInternalImpl3.displayId, remoteAnimationAdapter);
                            Bundle bundle4 = bundle3;
                            if (bundle4 != null) {
                                activityOptions.putAll(bundle4);
                            }
                            ActivityOptions activityOptions2 = new ActivityOptions(activityOptions);
                            activityOptions2.setEligibleForLegacyPermissionPrompt(true);
                            activityOptions2.setPendingIntentBackgroundActivityStartMode(1);
                            return pendingIntent2.sendAndReturnResult(legacyActivityStarterInternalImpl3.context, 0, intent3, null, null, null, activityOptions2.toBundle());
                        }
                    });
                } catch (PendingIntent.CanceledException e) {
                    Log.w("LegacyActivityStarterInternalImpl", "Sending intent failed: " + e);
                    if (!z) {
                        ((ShadeController) LegacyActivityStarterInternalImpl.this.shadeControllerLazy.get()).collapseOnMainThread();
                    }
                }
                if (pendingIntent.isActivity()) {
                    ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                    KeyguardUpdateMonitor keyguardUpdateMonitor = LegacyActivityStarterInternalImpl.this.keyguardUpdateMonitor;
                    if (keyguardUpdateMonitor.mIsDreaming) {
                        try {
                            keyguardUpdateMonitor.mDreamManager.awaken();
                        } catch (RemoteException e2) {
                            keyguardUpdateMonitor.mLogger.logException("Unable to awaken from dream", e2);
                        }
                    }
                }
                Runnable runnable4 = runnable2;
                if (runnable4 != null) {
                    LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl3 = LegacyActivityStarterInternalImpl.this;
                    int i2 = LegacyActivityStarterInternalImpl.$r8$clinit;
                    legacyActivityStarterInternalImpl3.mainExecutor.executeDelayed(runnable4, 0);
                }
            }
        };
        DelayableExecutor delayableExecutor = legacyActivityStarterInternalImpl.mainExecutor;
        if (z7) {
            delayableExecutor.executeDelayed(runnable3, 0);
        } else {
            final boolean z10 = z6;
            delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startPendingIntentDismissingKeyguard$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityStarterInternal.executeRunnableDismissingKeyguard$default(LegacyActivityStarterInternalImpl.this, runnable3, null, z, z10, false, z8, 82);
                }
            }, 0);
        }
    }
}
