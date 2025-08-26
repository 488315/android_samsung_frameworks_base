package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class AnimatedDialog {
    public final AnimatedBoundsLayoutListener backgroundLayoutListener;
    public final DialogTransitionAnimator.Callback callback;
    public final DialogTransitionAnimator.Controller controller;
    public final Lazy decorView$delegate;
    public View.OnLayoutChangeListener decorViewLayoutListener;
    public final Dialog dialog;
    public ViewGroup dialogContentWithBackground;
    public boolean dismissRequested;
    public boolean exitAnimationDisabled;
    public final boolean forceDisableSynchronization;
    public boolean hasInstrumentedJank;
    public final InteractionJankMonitor interactionJankMonitor;
    public boolean isDismissing;
    public boolean isLaunching;
    public boolean isOriginalDialogViewLaidOut;
    public boolean isSourceDrawnInDialog;
    public final Function1 onDialogDismissed;
    public int originalDialogBackgroundColor;
    public final AnimatedDialog parentAnimatedDialog;
    public final TransitionAnimator transitionAnimator;

    public final class AnimatedBoundsLayoutListener implements View.OnLayoutChangeListener {
        public ValueAnimator currentAnimator;
        public Rect lastBounds;

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

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(final View view, final int i, final int i2, final int i3, int i4, int i5, int i6, int i7, int i8) {
            final int i9;
            if (i == i5 && i2 == i6) {
                i9 = i4;
                if (i3 == i7 && i9 == i8) {
                    Rect rect = this.lastBounds;
                    if (rect != null) {
                        view.setLeft(rect.left);
                        view.setTop(rect.top);
                        view.setRight(rect.right);
                        view.setBottom(rect.bottom);
                        return;
                    }
                    return;
                }
            } else {
                i9 = i4;
            }
            if (this.lastBounds == null) {
                this.lastBounds = new Rect(i5, i6, i7, i8);
            }
            final Rect rect2 = this.lastBounds;
            rect2.getClass();
            final int i10 = rect2.left;
            final int i11 = rect2.top;
            final int i12 = rect2.right;
            final int i13 = rect2.bottom;
            ValueAnimator valueAnimator = this.currentAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.currentAnimator = null;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(500L);
            valueAnimatorOfFloat.setInterpolator(Interpolators.STANDARD);
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    this.this$0.currentAnimator = null;
                }
            });
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    rect2.left = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i10, i, animatedFraction));
                    rect2.top = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i11, i2, animatedFraction));
                    rect2.right = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i12, i3, animatedFraction));
                    rect2.bottom = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i13, i9, animatedFraction));
                    view.setLeft(rect2.left);
                    view.setTop(rect2.top);
                    view.setRight(rect2.right);
                    view.setBottom(rect2.bottom);
                }
            });
            this.currentAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.start();
        }
    }

    public AnimatedDialog(TransitionAnimator transitionAnimator, DialogTransitionAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogTransitionAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2) {
        this.transitionAnimator = transitionAnimator;
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.controller = controller;
        this.onDialogDismissed = function1;
        this.dialog = dialog;
        this.parentAnimatedDialog = animatedDialog;
        this.forceDisableSynchronization = z2;
        this.decorView$delegate = LazyKt__LazyJVMKt.lazy(new AnimatedDialog$$ExternalSyntheticLambda0(this, 0));
        this.originalDialogBackgroundColor = -16777216;
        this.isLaunching = true;
        this.backgroundLayoutListener = z ? new AnimatedBoundsLayoutListener() : null;
    }

    public static ViewGroup findFirstViewGroupWithBackground(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.getBackground() != null) {
            return viewGroup;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewGroup viewGroupFindFirstViewGroupWithBackground = findFirstViewGroupWithBackground(viewGroup.getChildAt(i));
            if (viewGroupFindFirstViewGroupWithBackground != null) {
                return viewGroupFindFirstViewGroupWithBackground;
            }
        }
        return null;
    }

    public final ViewGroup getDecorView() {
        return (ViewGroup) this.decorView$delegate.getValue();
    }

    public final void maybeStartLaunchAnimation() {
        if (this.isSourceDrawnInDialog && this.isOriginalDialogViewLaidOut) {
            Window window = this.dialog.getWindow();
            if (window != null) {
                window.addFlags(2);
            }
            startAnimation(new AnimatedDialog$$ExternalSyntheticLambda7(), new AnimatedDialog$$ExternalSyntheticLambda0(this, 2), true);
        }
    }

    public final void moveSourceDrawingToDialog() {
        if (getDecorView().getViewRootImpl() == null) {
            getDecorView().post(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog.moveSourceDrawingToDialog.1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.moveSourceDrawingToDialog();
                }
            });
        } else {
            this.controller.startDrawingInOverlayOf(getDecorView());
            synchronizeNextDraw(new AnimatedDialog$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDialogDismissed() {
        boolean zShouldAnimateExit;
        boolean zIsDreaming;
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            this.dialog.getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog.onDialogDismissed.1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.onDialogDismissed();
                }
            });
            return;
        }
        if (this.isLaunching) {
            this.dismissRequested = true;
            return;
        }
        if (this.isDismissing) {
            return;
        }
        this.isDismissing = true;
        AnimatedDialog$$ExternalSyntheticLambda1 animatedDialog$$ExternalSyntheticLambda1 = new AnimatedDialog$$ExternalSyntheticLambda1(this);
        if (this.decorViewLayoutListener != null) {
            getDecorView().removeOnLayoutChangeListener(this.decorViewLayoutListener);
        }
        boolean z = this.exitAnimationDisabled;
        DialogTransitionAnimator.Controller controller = this.controller;
        if (z || !this.dialog.isShowing()) {
            zShouldAnimateExit = false;
        } else {
            CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) this.callback;
            centralSurfacesDependenciesModule$1.getClass();
            try {
                zIsDreaming = centralSurfacesDependenciesModule$1.val$dreamManager.isDreaming();
            } catch (RemoteException e) {
                Log.e("DialogTransitionAnimator.Callback", "dreamManager.isDreaming failed", e);
                zIsDreaming = false;
            }
            if (!zIsDreaming) {
                zShouldAnimateExit = controller.shouldAnimateExit();
            }
        }
        if (zShouldAnimateExit) {
            startAnimation(new AnimatedDialog$$ExternalSyntheticLambda0(this, 3), new AnimatedDialog$$ExternalSyntheticLambda5(this, animatedDialog$$ExternalSyntheticLambda1), false);
            return;
        }
        Log.i("DialogTransitionAnimator", "Skipping animation of dialog into the source");
        controller.onExitAnimationCancelled();
        animatedDialog$$ExternalSyntheticLambda1.mo781invoke(Boolean.FALSE);
        this.onDialogDismissed.mo781invoke(this);
    }

    public final void prepareForStackDismiss() {
        AnimatedDialog animatedDialog = this.parentAnimatedDialog;
        if (animatedDialog == null) {
            return;
        }
        animatedDialog.exitAnimationDisabled = true;
        animatedDialog.dialog.hide();
        animatedDialog.prepareForStackDismiss();
        animatedDialog.dialog.dismiss();
    }

    public final void startAnimation(final Function0 function0, final Function0 function02, boolean z) {
        final TransitionAnimator.Controller ghostedViewTransitionAnimatorController;
        TransitionAnimator.Controller controllerCreateExitController;
        DialogTransitionAnimator.Controller controller = this.controller;
        if (z) {
            ghostedViewTransitionAnimatorController = controller.createTransitionController();
        } else {
            ViewGroup viewGroup = this.dialogContentWithBackground;
            viewGroup.getClass();
            ghostedViewTransitionAnimatorController = new GhostedViewTransitionAnimatorController(viewGroup, null, null, null, null, false, null, null, 254, null);
        }
        if (z) {
            ViewGroup viewGroup2 = this.dialogContentWithBackground;
            viewGroup2.getClass();
            controllerCreateExitController = new GhostedViewTransitionAnimatorController(viewGroup2, null, null, null, null, false, null, null, 254, null);
        } else {
            controllerCreateExitController = controller.createExitController();
        }
        final TransitionAnimator.Controller controller2 = controllerCreateExitController;
        ghostedViewTransitionAnimatorController.setTransitionContainer(getDecorView());
        controller2.setTransitionContainer(getDecorView());
        final TransitionAnimator.State stateCreateAnimatorState = controller2.createAnimatorState();
        TransitionAnimator.Controller controller3 = new TransitionAnimator.Controller() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1
            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                return ghostedViewTransitionAnimatorController.createAnimatorState();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return ghostedViewTransitionAnimatorController.getTransitionContainer();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return true;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(final boolean z2) {
                final TransitionAnimator.Controller controller4 = controller2;
                final Function0 function03 = function02;
                final TransitionAnimator.Controller controller5 = ghostedViewTransitionAnimatorController;
                final Function0 function04 = new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        TransitionAnimator.Controller controller6 = controller5;
                        boolean z3 = z2;
                        controller6.onTransitionAnimationEnd(z3);
                        controller4.onTransitionAnimationEnd(z3);
                        function03.invoke();
                        return Unit.INSTANCE;
                    }
                };
                this.dialog.getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1$onTransitionAnimationEnd$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        function04.invoke();
                    }
                });
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                ghostedViewTransitionAnimatorController.onTransitionAnimationProgress(state, f, f2);
                state.visible = !state.visible;
                TransitionAnimator.Controller controller4 = controller2;
                controller4.onTransitionAnimationProgress(state, f, f2);
                if (controller4 instanceof GhostedViewTransitionAnimatorController) {
                    ((GhostedViewTransitionAnimatorController) controller4).fillGhostedViewState(stateCreateAnimatorState);
                }
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z2) {
                function0.invoke();
                ghostedViewTransitionAnimatorController.onTransitionAnimationStart(z2);
                controller2.onTransitionAnimationStart(z2);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup3) {
                ghostedViewTransitionAnimatorController.setTransitionContainer(viewGroup3);
                controller2.setTransitionContainer(viewGroup3);
            }
        };
        int i = this.originalDialogBackgroundColor;
        TransitionAnimator.Companion companion = TransitionAnimator.Companion;
        this.transitionAnimator.startAnimation(controller3, stateCreateAnimatorState, i, true, false, null, -1L);
    }

    public final void synchronizeNextDraw(Function0 function0) {
        ViewRootImpl viewRoot = this.controller.getViewRoot();
        View view = viewRoot != null ? viewRoot.getView() : null;
        if (this.forceDisableSynchronization || view == null) {
            function0.invoke();
            return;
        }
        ViewRootSync viewRootSync = ViewRootSync.INSTANCE;
        ViewGroup decorView = getDecorView();
        viewRootSync.getClass();
        ViewRootSync.synchronizeNextDraw(view, decorView, function0);
        getDecorView().invalidate();
        view.invalidate();
    }

    public /* synthetic */ AnimatedDialog(TransitionAnimator transitionAnimator, DialogTransitionAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogTransitionAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionAnimator, callback, interactionJankMonitor, controller, function1, dialog, z, (i & 128) != 0 ? null : animatedDialog, z2);
    }
}
