package com.android.systemui.statusbar.phone;

import android.app.ActivityManagerNative;
import android.app.ActivityOptions;
import android.app.IActivityManager;
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
import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityStarterImpl implements ActivityStarter {
    public final ActivityStarterInternal activityStarterInternal;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final DelayableExecutor mainExecutor;
    public final Lazy shadeInteractorLazy;
    public final SysuiStatusBarStateController statusBarStateController;

    public ActivityStarterImpl(SysuiStatusBarStateController sysuiStatusBarStateController, DelayableExecutor delayableExecutor, Lazy lazy, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy2) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.mainExecutor = delayableExecutor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.shadeInteractorLazy = lazy2;
        this.activityStarterInternal = (ActivityStarterInternal) lazy.get();
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        ((LegacyActivityStarterInternalImpl) this.activityStarterInternal).dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3) {
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(this.activityStarterInternal, runnable, runnable2, z, z2, z3, false, 96);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSCustomizerRunnableDismissingKeyguard(final Runnable runnable) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSCustomizerRunnableDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
                ((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mLeaveOpenOnKeyguardHide = true;
                final Runnable runnable2 = runnable;
                ActivityStarterInternal.executeRunnableDismissingKeyguard$default(activityStarterImpl.activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSCustomizerRunnableDismissingKeyguard$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            activityStarterImpl.mainExecutor.executeDelayed(runnable3, 0);
                        }
                    }
                }, null, false, false, false, false, 126);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSRunnableDismissingKeyguard(Runnable runnable) {
        postQSRunnableDismissingKeyguard(runnable, ((Number) ((ShadeInteractorImpl) ((ShadeInteractor) this.shadeInteractorLazy.get())).baseShadeInteractor.getShadeExpansion().getValue()).floatValue() > 0.0f);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, pendingIntent, true, null, null, null, false, false, null, null, 508);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final boolean shouldAnimateLaunch(boolean z) {
        this.activityStarterInternal.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2) {
        ((LegacyActivityStarterInternalImpl) this.activityStarterInternal).startActivity(intent, z, controller, z2, null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, null, 0, null, null, false, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startCameraActivity(final Intent intent, boolean z, final ActivityStarter.Callback callback) {
        Runnable runnable;
        Runnable runnable2;
        final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = (LegacyActivityStarterInternalImpl) this.activityStarterInternal;
        boolean wouldLaunchResolverActivity = legacyActivityStarterInternalImpl.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) legacyActivityStarterInternalImpl.lockScreenUserManager).mCurrentUserId, intent);
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
                        Intrinsics.checkNotNull(display);
                        displayId = display.getDisplayId();
                    } else {
                        displayId = LegacyActivityStarterInternalImpl.access$getSubDisplayID(LegacyActivityStarterInternalImpl.this);
                    }
                    makeBasic.setLaunchDisplayId(displayId);
                    IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                    String basePackageName = LegacyActivityStarterInternalImpl.this.context.getBasePackageName();
                    Intent intent2 = intent;
                    i = iActivityManager.startActivityAsUser((IApplicationThread) null, basePackageName, intent2, intent2.resolveTypeIfNeeded(LegacyActivityStarterInternalImpl.this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
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
            runnable = runnable3;
            runnable2 = runnable4;
        } else {
            runnable3.run();
            runnable = null;
            runnable2 = null;
        }
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(legacyActivityStarterInternalImpl, runnable, runnable2, z, wouldLaunchResolverActivity, true, false, 96);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, null, null, null, false, false, null, null, 508);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, runnable, null, controller, true, false, null, null, 456);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentWithoutDismissing(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, z, runnable, null, controller, true, true, intent, bundle, 8);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        ((LegacyActivityStarterInternalImpl) this.activityStarterInternal).dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, false, null, 0, null, null, false, null, 508);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, callback, i, controller, null, z3, userHandle, 64);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, runnable, null, null, false, false, null, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, z, runnable, null, controller, true, false, intent, bundle, 72);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final ActivityTransitionAnimator.Controller controller) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$2
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, pendingIntent, true, null, null, controller, false, false, null, null, 492);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, boolean z2) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, null, 0, null, null, false, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(final Intent intent, boolean z, boolean z2, final boolean z3, final ActivityStarter.Callback callback, final int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, final int i2) {
        final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl = (LegacyActivityStarterInternalImpl) this.activityStarterInternal;
        final UserHandle activityUserHandle = userHandle == null ? legacyActivityStarterInternalImpl.getActivityUserHandle(intent) : userHandle;
        if (z) {
            if (!((DeviceProvisionedControllerImpl) legacyActivityStarterInternalImpl.deviceProvisionedController).deviceProvisioned.get()) {
                return;
            }
        } else {
            legacyActivityStarterInternalImpl.getClass();
        }
        KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_INTERNAL);
        boolean wouldLaunchResolverActivity = legacyActivityStarterInternalImpl.activityIntentHelper.wouldLaunchResolverActivity(((NotificationLockscreenUserManagerImpl) legacyActivityStarterInternalImpl.lockScreenUserManager).mCurrentUserId, intent);
        final ActivityTransitionAnimator.Controller wrapAnimationControllerForShadeOrStatusBar = legacyActivityStarterInternalImpl.wrapAnimationControllerForShadeOrStatusBar(controller, z2, true);
        boolean z4 = false;
        boolean z5 = z2 && wrapAnimationControllerForShadeOrStatusBar == null;
        final boolean z6 = false;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2
            @Override // java.lang.Runnable
            public final void run() {
                ((AssistManager) LegacyActivityStarterInternalImpl.this.assistManagerLazy.get()).hideAssist();
                intent.setFlags(335544320);
                intent.addFlags(i);
                final int[] iArr = {-96};
                ActivityTransitionAnimator activityTransitionAnimator = LegacyActivityStarterInternalImpl.this.activityTransitionAnimator;
                ActivityTransitionAnimator.Controller controller2 = wrapAnimationControllerForShadeOrStatusBar;
                boolean z7 = z6;
                String str = intent.getPackage();
                final LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl2 = LegacyActivityStarterInternalImpl.this;
                final boolean z8 = z3;
                final Intent intent2 = intent;
                final int i3 = i2;
                final UserHandle userHandle2 = activityUserHandle;
                Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:10:0x0059  */
                    @Override // kotlin.jvm.functions.Function1
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r18) {
                        /*
                            r17 = this;
                            r1 = r17
                            r0 = r18
                            android.view.RemoteAnimationAdapter r0 = (android.view.RemoteAnimationAdapter) r0
                            android.app.ActivityOptions r2 = new android.app.ActivityOptions
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r3 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this
                            int r3 = r3.displayId
                            android.os.Bundle r0 = com.android.systemui.statusbar.phone.CentralSurfaces.getActivityOptions(r3, r0)
                            r2.<init>(r0)
                            r2.setDismissKeyguardIfInsecure()
                            boolean r0 = r2
                            r2.setDisallowEnterPictureInPictureWhileLaunching(r0)
                            com.android.systemui.camera.CameraIntents$Companion r0 = com.android.systemui.camera.CameraIntents.Companion
                            android.content.Intent r3 = r3
                            r0.getClass()
                            com.android.systemui.statusbar.KeyguardShortcutManager$Companion r0 = com.android.systemui.statusbar.KeyguardShortcutManager.Companion
                            r0.getClass()
                            android.content.Intent r0 = com.android.systemui.statusbar.KeyguardShortcutManager.INSECURE_CAMERA_INTENT
                            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
                            r3 = 1
                            if (r0 == 0) goto L64
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r0 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this
                            com.android.systemui.statusbar.phone.CentralSurfaces r0 = r0.getCentralSurfaces()
                            if (r0 == 0) goto L4e
                            android.content.Intent r4 = r3
                            android.content.ComponentName r4 = r4.getComponent()
                            com.android.systemui.statusbar.phone.CentralSurfacesImpl r0 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r0
                            boolean r0 = r0.isForegroundComponentName(r4)
                            if (r0 != r3) goto L4e
                            android.content.Intent r0 = r3
                            r4 = 270532608(0x10200000, float:3.1554436E-29)
                            r0.setFlags(r4)
                            goto L55
                        L4e:
                            android.content.Intent r0 = r3
                            r4 = 536870912(0x20000000, float:1.0842022E-19)
                            r0.addFlags(r4)
                        L55:
                            int r0 = r4
                            if (r0 != r3) goto L60
                            android.content.Intent r0 = r3
                            java.lang.String r4 = "isQuickLaunchMode"
                            r0.putExtra(r4, r3)
                        L60:
                            r0 = 3
                            r2.setRotationAnimationHint(r0)
                        L64:
                            android.content.Intent r0 = r3
                            java.lang.String r0 = r0.getAction()
                            java.lang.String r4 = "android.settings.panel.action.VOLUME"
                            boolean r0 = r4.equals(r0)
                            if (r0 == 0) goto L75
                            r2.setDisallowEnterPictureInPictureWhileLaunching(r3)
                        L75:
                            boolean r0 = com.android.systemui.LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY
                            if (r0 == 0) goto L8e
                            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
                            java.lang.Class<com.android.systemui.keyguard.DisplayLifecycle> r3 = com.android.systemui.keyguard.DisplayLifecycle.class
                            java.lang.Object r0 = r0.getDependencyInner(r3)
                            com.android.systemui.keyguard.DisplayLifecycle r0 = (com.android.systemui.keyguard.DisplayLifecycle) r0
                            boolean r0 = r0.mIsFolderOpened
                            if (r0 != 0) goto L8e
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r0 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this
                            int r0 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.access$getSubDisplayID(r0)
                            goto L9d
                        L8e:
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r0 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this
                            android.content.Context r0 = r0.context
                            android.view.Display r0 = r0.getDisplay()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                            int r0 = r0.getDisplayId()
                        L9d:
                            r2.setLaunchDisplayId(r0)
                            r3 = 0
                            int[] r0 = r5     // Catch: android.os.RemoteException -> Ldd
                            android.app.IActivityTaskManager r4 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> Ldd
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r5 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this     // Catch: android.os.RemoteException -> Ldd
                            android.content.Context r5 = r5.context     // Catch: android.os.RemoteException -> Ldd
                            java.lang.String r6 = r5.getBasePackageName()     // Catch: android.os.RemoteException -> Ldd
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r5 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this     // Catch: android.os.RemoteException -> Ldd
                            android.content.Context r5 = r5.context     // Catch: android.os.RemoteException -> Ldd
                            java.lang.String r7 = r5.getAttributionTag()     // Catch: android.os.RemoteException -> Ldd
                            android.content.Intent r8 = r3     // Catch: android.os.RemoteException -> Ldd
                            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r5 = com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl.this     // Catch: android.os.RemoteException -> Ldd
                            android.content.Context r5 = r5.context     // Catch: android.os.RemoteException -> Ldd
                            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch: android.os.RemoteException -> Ldd
                            java.lang.String r9 = r8.resolveTypeIfNeeded(r5)     // Catch: android.os.RemoteException -> Ldd
                            android.os.Bundle r15 = r2.toBundle()     // Catch: android.os.RemoteException -> Ldd
                            android.os.UserHandle r2 = r6     // Catch: android.os.RemoteException -> Ldd
                            int r16 = r2.getIdentifier()     // Catch: android.os.RemoteException -> Ldd
                            r11 = 0
                            r12 = 0
                            r13 = 268435456(0x10000000, float:2.5243549E-29)
                            r14 = 0
                            r5 = 0
                            r10 = 0
                            int r2 = r4.startActivityAsUser(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch: android.os.RemoteException -> Ldd
                            r0[r3] = r2     // Catch: android.os.RemoteException -> Ldd
                            goto Le5
                        Ldd:
                            r0 = move-exception
                            java.lang.String r2 = "LegacyActivityStarterInternalImpl"
                            java.lang.String r4 = "Unable to start activity"
                            android.util.Log.w(r2, r4, r0)
                        Le5:
                            int[] r0 = r5
                            r0 = r0[r3]
                            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$startActivityDismissingKeyguard$runnable$2.AnonymousClass1.invoke(java.lang.Object):java.lang.Object");
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
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) legacyActivityStarterInternalImpl.keyguardStateController;
        if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mOccluded) {
            z4 = true;
        }
        legacyActivityStarterInternalImpl.executeRunnableDismissingKeyguard(runnable, runnable2, z5, wouldLaunchResolverActivity, !z4, false, null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, runnable, view, null, false, false, null, null, 496);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, false, callback, 0, null, null, false, null, 500);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller) {
        ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, runnable, null, controller, false, false, null, null, VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSRunnableDismissingKeyguard(final Runnable runnable, final boolean z) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
                ((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mLeaveOpenOnKeyguardHide = z;
                final Runnable runnable2 = runnable;
                ActivityStarterInternal.executeRunnableDismissingKeyguard$default(activityStarterImpl.activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            activityStarterImpl.mainExecutor.executeDelayed(runnable3, 0);
                        }
                    }
                }, null, false, true, false, false, 112);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$3
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, null, 0, null, null, false, null, 504);
            }
        }, i);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, boolean z2, int i) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, null, i, null, null, false, null, VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle) {
        ((LegacyActivityStarterInternalImpl) this.activityStarterInternal).startActivity(intent, z, controller, z2, userHandle);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i, final ActivityTransitionAnimator.Controller controller) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$4
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, null, 0, controller, null, false, null, 472);
            }
        }, i);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i, final ActivityTransitionAnimator.Controller controller, final String str) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$5
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, null, 0, controller, str, false, null, VolteConstants.ErrorCode.REQUEST_TIMEOUT);
            }
        }, i);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final boolean z) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$6
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    ActivityStarterImpl activityStarterImpl = this;
                    if (((StatusBarStateControllerImpl) activityStarterImpl.statusBarStateController).mState == 2 && !activityStarterImpl.keyguardUpdateMonitor.isSecure()) {
                        this.statusBarStateController.setState(1);
                    }
                }
                ActivityStarterInternal.startPendingIntentDismissingKeyguard$default(this.activityStarterInternal, pendingIntent, true, null, null, null, false, false, null, null, 508);
            }
        }, 0);
    }
}
