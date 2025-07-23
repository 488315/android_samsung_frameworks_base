package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActivityStarterImpl implements ActivityStarter {
    public final ActivityStarterInternal activityStarterInternal;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final DelayableExecutor mainExecutor;
    public final Lazy shadeInteractorLazy;
    public final SysuiStatusBarStateController statusBarStateController;

    public ActivityStarterImpl(SysuiStatusBarStateController sysuiStatusBarStateController, DelayableExecutor delayableExecutor, Lazy lazy, Lazy lazy2, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy3) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.mainExecutor = delayableExecutor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.shadeInteractorLazy = lazy3;
        Object obj = lazy2.get();
        obj.getClass();
        this.activityStarterInternal = (ActivityStarterInternal) obj;
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        this.activityStarterInternal.dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3) {
        ActivityStarterInternal.executeRunnableDismissingKeyguard$default(this.activityStarterInternal, runnable, runnable2, z, z2, z3, false, null, 96);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSCustomizerRunnableDismissingKeyguard(final Runnable runnable) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSCustomizerRunnableDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
                SysuiStatusBarStateController sysuiStatusBarStateController = activityStarterImpl.statusBarStateController;
                boolean z = true;
                if (!activityStarterImpl.keyguardUpdateMonitor.mKeyguardOccluded && ((Number) ((ShadeInteractorImpl) ((ShadeInteractor) activityStarterImpl.shadeInteractorLazy.get())).baseShadeInteractor.getShadeExpansion().getValue()).floatValue() <= 0.0f) {
                    z = false;
                }
                ((StatusBarStateControllerImpl) sysuiStatusBarStateController).setLeaveOpenOnKeyguardHide(z);
                final ActivityStarterImpl activityStarterImpl2 = ActivityStarterImpl.this;
                ActivityStarterInternal activityStarterInternal = activityStarterImpl2.activityStarterInternal;
                final Runnable runnable2 = runnable;
                ActivityStarterInternal.executeRunnableDismissingKeyguard$default(activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSCustomizerRunnableDismissingKeyguard$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            activityStarterImpl2.mainExecutor.executeDelayed(runnable3, 0);
                        }
                    }
                }, null, false, false, false, false, null, 126);
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
                ActivityStarterImpl.this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : null, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : null, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void registerTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie, ActivityTransitionAnimator.ControllerFactory controllerFactory, CoroutineScope coroutineScope) {
        TransitionAnimator.Companion.getClass();
        this.activityStarterInternal.registerTransition(transitionCookie, controllerFactory, coroutineScope);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final boolean shouldAnimateLaunch(boolean z) {
        return this.activityStarterInternal.shouldAnimateLaunch(z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2) {
        this.activityStarterInternal.startActivity(intent, z, controller, z2, null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, String str) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, null, 0, null, str, false, null, 440);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startCameraActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        this.activityStarterInternal.startCameraActivity(intent, z, callback);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : null, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : null, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : controller, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentWithoutDismissing(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, z, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : controller, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : intent, (r23 & 256) != 0 ? null : bundle, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void unregisterTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie) {
        TransitionAnimator.Companion.getClass();
        this.activityStarterInternal.unregisterTransition(transitionCookie);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        this.activityStarterInternal.dismissKeyguardThenExecute(onDismissAction, runnable, z);
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
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : null, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle, String str) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, z, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : controller, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : intent, (r23 & 256) != 0 ? null : bundle, (r23 & 512) != 0 ? null : str);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final ActivityTransitionAnimator.Controller controller) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$2
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterImpl.this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : null, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : controller, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
            }
        }, 0);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, boolean z2) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z2, z, null, 0, null, null, false, null, 504);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, int i2) {
        this.activityStarterInternal.startActivityDismissingKeyguard(intent, z, z2, z3, callback, i, controller, userHandle, i2);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : view, (r23 & 16) != 0 ? null : null, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        ActivityStarterInternal.startActivityDismissingKeyguard$default(this.activityStarterInternal, intent, z, false, callback, 0, null, null, false, null, 500);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller) {
        this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : runnable, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : controller, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postQSRunnableDismissingKeyguard(final Runnable runnable, final boolean z) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1
            @Override // java.lang.Runnable
            public final void run() {
                ((StatusBarStateControllerImpl) ActivityStarterImpl.this.statusBarStateController).setLeaveOpenOnKeyguardHide(z);
                final ActivityStarterImpl activityStarterImpl = ActivityStarterImpl.this;
                ActivityStarterInternal activityStarterInternal = activityStarterImpl.activityStarterInternal;
                final Runnable runnable2 = runnable;
                ActivityStarterInternal.executeRunnableDismissingKeyguard$default(activityStarterInternal, new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postQSRunnableDismissingKeyguard$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            activityStarterImpl.mainExecutor.executeDelayed(runnable3, 0);
                        }
                    }
                }, null, false, true, false, false, null, 112);
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
        this.activityStarterInternal.startActivity(intent, z, controller, z2, userHandle);
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
    public final void postStartActivityDismissingKeyguard(final Intent intent, int i, final ActivityTransitionAnimator.Controller controller, final String str, final UserHandle userHandle) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$6
            @Override // java.lang.Runnable
            public final void run() {
                ActivityStarterInternal.startActivityDismissingKeyguard$default(ActivityStarterImpl.this.activityStarterInternal, intent, true, true, null, 0, controller, str, false, userHandle, 152);
            }
        }, i);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public final void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final boolean z) {
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ActivityStarterImpl$postStartActivityDismissingKeyguard$7
            @Override // java.lang.Runnable
            public final void run() {
                if (z && this.statusBarStateController.getState() == 2 && !this.keyguardUpdateMonitor.isSecure()) {
                    this.statusBarStateController.setState(1);
                }
                this.activityStarterInternal.startPendingIntentDismissingKeyguard(pendingIntent, true, (r23 & 4) != 0 ? null : null, (r23 & 8) != 0 ? null : null, (r23 & 16) != 0 ? null : null, (r23 & 32) == 0, (r23 & 64) == 0, (r23 & 128) != 0 ? null : null, (r23 & 256) != 0 ? null : null, (r23 & 512) != 0 ? null : null);
            }
        }, 0);
    }
}
