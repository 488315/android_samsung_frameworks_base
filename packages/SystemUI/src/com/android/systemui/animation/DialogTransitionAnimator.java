package com.android.systemui.animation;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.animation.Interpolator;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DialogTransitionAnimator {
    public static final TransitionAnimator.Interpolators INTERPOLATORS;
    public static final TransitionAnimator.Timings TIMINGS;
    public final Callback callback;
    public final AnimationFeatureFlags featureFlags;
    public final InteractionJankMonitor interactionJankMonitor;
    public final boolean isForTesting;
    public final Executor mainExecutor;
    public final HashSet openedDialogs;
    public final TransitionAnimator transitionAnimator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
        companion.getClass();
        TransitionAnimator.Interpolators interpolators = ActivityTransitionAnimator.INTERPOLATORS;
        companion.getClass();
        Interpolator interpolator = interpolators.positionInterpolator;
        Interpolator interpolator2 = interpolators.contentBeforeFadeOutInterpolator;
        Interpolator interpolator3 = interpolators.contentAfterFadeInInterpolator;
        interpolators.getClass();
        INTERPOLATORS = new TransitionAnimator.Interpolators(interpolator, interpolator, interpolator2, interpolator3);
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags) {
        this(executor, callback, interactionJankMonitor, animationFeatureFlags, null, false, 48, null);
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
        return dialogTransitionAnimator.createActivityTransitionController(animatedDialog, null);
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1] */
    public final DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController(final AnimatedDialog animatedDialog, Integer num) {
        final GhostedViewTransitionAnimatorController fromView$default;
        animatedDialog.exitAnimationDisabled = true;
        final Dialog dialog = animatedDialog.dialog;
        if (dialog.isShowing()) {
            CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) this.callback;
            if (centralSurfacesDependenciesModule$1.val$keyguardStateController.isUnlocked()) {
                ViewGroup viewGroup = animatedDialog.dialogContentWithBackground;
                if (viewGroup == null || (fromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, viewGroup, num, 28)) == null) {
                    return null;
                }
                return new ActivityTransitionAnimator.Controller(dialog, animatedDialog) { // from class: com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1
                    public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
                    public final /* synthetic */ AnimatedDialog $animatedDialog;
                    public final /* synthetic */ Dialog $dialog;
                    public final boolean isDialogLaunch = true;

                    {
                        this.$dialog = dialog;
                        this.$animatedDialog = animatedDialog;
                        this.$$delegate_0 = ActivityTransitionAnimator.Controller.this;
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final TransitionAnimator.State createAnimatorState() {
                        return this.$$delegate_0.createAnimatorState();
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

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final boolean isBelowAnimatingWindow() {
                        return this.$$delegate_0.isBelowAnimatingWindow();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final boolean isDialogLaunch() {
                        return this.isDialogLaunch;
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final boolean isLaunching() {
                        return this.$$delegate_0.isLaunching();
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
                    public final void onTransitionAnimationCancelled(Boolean bool) {
                        ActivityTransitionAnimator.Controller.this.onTransitionAnimationCancelled(null);
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
            ((AlternateBouncerInteractor) centralSurfacesDependenciesModule$1.val$alternateBouncerInteractor.get()).getClass();
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void show(android.app.Dialog r16, com.android.systemui.animation.DialogTransitionAnimator.Controller r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.DialogTransitionAnimator.show(android.app.Dialog, com.android.systemui.animation.DialogTransitionAnimator$Controller, boolean):void");
    }

    public final void showFromDialog(Dialog dialog, Dialog dialog2, DialogCuj dialogCuj, boolean z) {
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
        ViewDialogTransitionAnimatorController fromView = Controller.Companion.fromView(viewGroup, dialogCuj);
        if (fromView == null) {
            dialog.show();
        } else {
            show(dialog, fromView, z);
        }
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, TransitionAnimator transitionAnimator) {
        this(executor, callback, interactionJankMonitor, animationFeatureFlags, transitionAnimator, false, 32, null);
    }

    public DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, TransitionAnimator transitionAnimator, boolean z) {
        this.mainExecutor = executor;
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.featureFlags = animationFeatureFlags;
        this.transitionAnimator = transitionAnimator;
        this.isForTesting = z;
        this.openedDialogs = new HashSet();
    }

    public /* synthetic */ DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, TransitionAnimator transitionAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, callback, interactionJankMonitor, animationFeatureFlags, (i & 16) != 0 ? new TransitionAnimator(executor, TIMINGS, INTERPOLATORS) : transitionAnimator, (i & 32) != 0 ? false : z);
    }
}
