package com.android.systemui.animation;

import android.app.Dialog;
import android.content.ComponentName;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.window.WindowAnimationState;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DialogTransitionAnimator {
    public static final TransitionAnimator.Interpolators INTERPOLATORS;
    public static final TransitionAnimator.Timings TIMINGS;
    public final Callback callback;
    public final InteractionJankMonitor interactionJankMonitor;
    public final boolean isForTesting;
    public final Executor mainExecutor;
    public final HashSet openedDialogs;
    public final TransitionAnimator transitionAnimator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public static ViewDialogTransitionAnimatorController fromView(View view, DialogCuj dialogCuj) {
                if (!(view instanceof LaunchableView)) {
                    throw new IllegalArgumentException("A DialogTransitionAnimator.Controller was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
                }
                if (view.getParent() instanceof ViewGroup) {
                    return new ViewDialogTransitionAnimatorController(view, dialogCuj);
                }
                Log.e("DialogTransitionAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }
        }

        TransitionAnimator.Controller createExitController();

        TransitionAnimator.Controller createTransitionController();

        DialogCuj getCuj();

        Object getSourceIdentity();

        ViewRootImpl getViewRoot();

        InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder();

        void onExitAnimationCancelled();

        boolean shouldAnimateExit();

        void startDrawingInOverlayOf(ViewGroup viewGroup);

        void stopDrawingInOverlay();
    }

    static {
        new Companion(null);
        TIMINGS = ActivityTransitionAnimator.TIMINGS;
        ActivityTransitionAnimator.Companion.getClass();
        TransitionAnimator.Interpolators interpolators = ActivityTransitionAnimator.INTERPOLATORS;
        INTERPOLATORS = TransitionAnimator.Interpolators.copy$default(interpolators, interpolators.positionInterpolator, null, null, 13);
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor) {
        this(executor, callback, interactionJankMonitor, null, false, 24, null);
    }

    public static DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default(DialogTransitionAnimator dialogTransitionAnimator, View view) {
        Object obj;
        View decorView;
        Iterator it = dialogTransitionAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            Window window = ((AnimatedDialog) obj).dialog.getWindow();
            if (Intrinsics.areEqual((window == null || (decorView = window.getDecorView()) == null) ? null : decorView.getViewRootImpl(), view.getViewRootImpl())) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog == null) {
            return null;
        }
        return dialogTransitionAnimator.createActivityTransitionController(animatedDialog);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1] */
    public final DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController(final AnimatedDialog animatedDialog) {
        final GhostedViewTransitionAnimatorController fromView$default;
        animatedDialog.exitAnimationDisabled = true;
        final Dialog dialog = animatedDialog.dialog;
        if (dialog.isShowing()) {
            CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) this.callback;
            if (!centralSurfacesDependenciesModule$1.val$keyguardStateController.isUnlocked()) {
                ((AlternateBouncerInteractor) centralSurfacesDependenciesModule$1.val$alternateBouncerInteractor.get()).getClass();
                return null;
            }
            ViewGroup viewGroup = animatedDialog.dialogContentWithBackground;
            if (viewGroup != null && (fromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, viewGroup, null, 60)) != null) {
                return new ActivityTransitionAnimator.Controller(dialog, animatedDialog) { // from class: com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1
                    public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
                    public final /* synthetic */ AnimatedDialog $animatedDialog;
                    public final /* synthetic */ Dialog $dialog;

                    {
                        this.$dialog = dialog;
                        this.$animatedDialog = animatedDialog;
                        this.$$delegate_0 = ActivityTransitionAnimator.Controller.this;
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final TransitionAnimator.State createAnimatorState() {
                        return this.$$delegate_0.createAnimatorState();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final ComponentName getComponent() {
                        return this.$$delegate_0.getComponent();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final View getOpeningWindowSyncView() {
                        return this.$$delegate_0.getOpeningWindowSyncView();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final ViewGroup getTransitionContainer() {
                        return this.$$delegate_0.getTransitionContainer();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
                        return this.$$delegate_0.getTransitionCookie();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final WindowAnimationState getWindowAnimatorState() {
                        return this.$$delegate_0.getWindowAnimatorState();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final boolean isBelowAnimatingWindow() {
                        return this.$$delegate_0.isBelowAnimatingWindow();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final boolean isDialogLaunch() {
                        return true;
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final boolean isLaunching() {
                        return this.$$delegate_0.isLaunching();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onDispose() {
                        this.$$delegate_0.onDispose();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onIntentStarted(boolean z) {
                        ActivityTransitionAnimator.Controller.this.onIntentStarted(z);
                        if (z) {
                            return;
                        }
                        this.$dialog.dismiss();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onTransitionAnimationCancelled() {
                        ActivityTransitionAnimator.Controller.this.onTransitionAnimationCancelled();
                        this.$dialog.setDismissOverride(new DialogTransitionAnimator$createActivityTransitionController$1$enableDialogDismiss$1(this.$animatedDialog));
                        this.$dialog.dismiss();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationEnd(boolean z) {
                        ActivityTransitionAnimator.Controller.this.onTransitionAnimationEnd(z);
                        this.$dialog.hide();
                        this.$dialog.setDismissOverride(new DialogTransitionAnimator$createActivityTransitionController$1$enableDialogDismiss$1(this.$animatedDialog));
                        this.$dialog.dismiss();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                        this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationStart(boolean z) {
                        ActivityTransitionAnimator.Controller.this.onTransitionAnimationStart(z);
                        this.$dialog.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1$disableDialogDismiss$1
                            @Override // java.lang.Runnable
                            public final void run() {
                            }
                        });
                        this.$animatedDialog.prepareForStackDismiss();
                        Window window = this.$dialog.getWindow();
                        if (window != null) {
                            window.clearFlags(2);
                        }
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final void setTransitionContainer(ViewGroup viewGroup2) {
                        this.$$delegate_0.setTransitionContainer(viewGroup2);
                    }
                };
            }
        }
        return null;
    }

    public final void disableAllCurrentDialogsExitAnimations() {
        Iterator it = this.openedDialogs.iterator();
        while (it.hasNext()) {
            ((AnimatedDialog) it.next()).exitAnimationDisabled = true;
        }
    }

    public final void dismissStack(Dialog dialog) {
        Object obj;
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog, dialog)) {
                    break;
                }
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog != null) {
            animatedDialog.prepareForStackDismiss();
        }
        dialog.dismiss();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void show(android.app.Dialog r14, com.android.systemui.animation.DialogTransitionAnimator.Controller r15, boolean r16) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.DialogTransitionAnimator.show(android.app.Dialog, com.android.systemui.animation.DialogTransitionAnimator$Controller, boolean):void");
    }

    public final void showFromDialog(Dialog dialog, Dialog dialog2) {
        Object obj;
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog, dialog2)) {
                    break;
                }
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        ViewGroup viewGroup = animatedDialog != null ? animatedDialog.dialogContentWithBackground : null;
        if (viewGroup == null) {
            Log.w("DialogTransitionAnimator", "Showing dialog " + dialog + " normally as the dialog it is shown from was not shown using DialogTransitionAnimator");
            dialog.show();
            return;
        }
        Controller.Companion.getClass();
        ViewDialogTransitionAnimatorController fromView = Controller.Companion.fromView(viewGroup, null);
        if (fromView == null) {
            dialog.show();
        } else {
            show(dialog, fromView, false);
        }
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, TransitionAnimator transitionAnimator) {
        this(executor, callback, interactionJankMonitor, transitionAnimator, false, 16, null);
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, TransitionAnimator transitionAnimator, boolean z) {
        this.mainExecutor = executor;
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.transitionAnimator = transitionAnimator;
        this.isForTesting = z;
        this.openedDialogs = new HashSet();
    }

    public static DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default(Dialog dialog, DialogTransitionAnimator dialogTransitionAnimator) {
        Object obj;
        Iterator it = dialogTransitionAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog, dialog)) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog == null) {
            return null;
        }
        return dialogTransitionAnimator.createActivityTransitionController(animatedDialog);
    }

    public /* synthetic */ DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, TransitionAnimator transitionAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, callback, interactionJankMonitor, (i & 8) != 0 ? new TransitionAnimator(executor, TIMINGS, INTERPOLATORS, null, null, null, 56, null) : transitionAnimator, (i & 16) != 0 ? false : z);
    }
}
