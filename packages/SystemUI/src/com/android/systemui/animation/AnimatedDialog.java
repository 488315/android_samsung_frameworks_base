package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.AnimatedDialog;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

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
    public final AnimationFeatureFlags featureFlags;
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
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            new Companion(null);
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(final View view, final int i, final int i2, final int i3, final int i4, int i5, int i6, int i7, int i8) {
            if (i == i5 && i2 == i6) {
                if (i3 == i7 && i4 == i8) {
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
            }
            if (this.lastBounds == null) {
                this.lastBounds = new Rect(i5, i6, i7, i8);
            }
            final Rect rect2 = this.lastBounds;
            Intrinsics.checkNotNull(rect2);
            final int i9 = rect2.left;
            final int i10 = rect2.top;
            final int i11 = rect2.right;
            final int i12 = rect2.bottom;
            ValueAnimator valueAnimator = this.currentAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.currentAnimator = null;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.STANDARD);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    AnimatedDialog.AnimatedBoundsLayoutListener.this.currentAnimator = null;
                }
            });
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    rect2.left = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i9, i, animatedFraction));
                    rect2.top = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i10, i2, animatedFraction));
                    rect2.right = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i11, i3, animatedFraction));
                    rect2.bottom = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i12, i4, animatedFraction));
                    view.setLeft(rect2.left);
                    view.setTop(rect2.top);
                    view.setRight(rect2.right);
                    view.setBottom(rect2.bottom);
                }
            });
            this.currentAnimator = ofFloat;
            ofFloat.start();
        }
    }

    public AnimatedDialog(TransitionAnimator transitionAnimator, DialogTransitionAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogTransitionAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2, AnimationFeatureFlags animationFeatureFlags) {
        this.transitionAnimator = transitionAnimator;
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.controller = controller;
        this.onDialogDismissed = function1;
        this.dialog = dialog;
        this.parentAnimatedDialog = animatedDialog;
        this.forceDisableSynchronization = z2;
        this.featureFlags = animationFeatureFlags;
        this.decorView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$decorView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Window window = AnimatedDialog.this.dialog.getWindow();
                Intrinsics.checkNotNull(window);
                return (ViewGroup) window.getDecorView();
            }
        });
        this.originalDialogBackgroundColor = -16777216;
        this.isLaunching = true;
        this.backgroundLayoutListener = z ? new AnimatedBoundsLayoutListener() : null;
    }

    public static final void access$maybeStartLaunchAnimation(final AnimatedDialog animatedDialog) {
        if (animatedDialog.isSourceDrawnInDialog && animatedDialog.isOriginalDialogViewLaidOut) {
            Window window = animatedDialog.dialog.getWindow();
            if (window != null) {
                window.addFlags(2);
            }
            animatedDialog.startAnimation(new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$maybeStartLaunchAnimation$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AnimatedDialog animatedDialog2 = AnimatedDialog.this;
                    animatedDialog2.isLaunching = false;
                    if (animatedDialog2.dismissRequested) {
                        animatedDialog2.dialog.dismiss();
                    }
                    AnimatedDialog animatedDialog3 = AnimatedDialog.this;
                    if (animatedDialog3.backgroundLayoutListener != null) {
                        ViewGroup viewGroup = animatedDialog3.dialogContentWithBackground;
                        Intrinsics.checkNotNull(viewGroup);
                        viewGroup.addOnLayoutChangeListener(AnimatedDialog.this.backgroundLayoutListener);
                    }
                    AnimatedDialog animatedDialog4 = AnimatedDialog.this;
                    if (animatedDialog4.hasInstrumentedJank) {
                        InteractionJankMonitor interactionJankMonitor = animatedDialog4.interactionJankMonitor;
                        DialogCuj cuj = animatedDialog4.controller.getCuj();
                        Intrinsics.checkNotNull(cuj);
                        interactionJankMonitor.end(cuj.cujType);
                    }
                    return Unit.INSTANCE;
                }
            }, true);
        }
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
            ViewGroup findFirstViewGroupWithBackground = findFirstViewGroupWithBackground(viewGroup.getChildAt(i));
            if (findFirstViewGroupWithBackground != null) {
                return findFirstViewGroupWithBackground;
            }
        }
        return null;
    }

    public final ViewGroup getDecorView() {
        return (ViewGroup) this.decorView$delegate.getValue();
    }

    public final void moveSourceDrawingToDialog() {
        if (getDecorView().getViewRootImpl() == null) {
            getDecorView().post(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.moveSourceDrawingToDialog();
                }
            });
        } else {
            this.controller.startDrawingInOverlayOf(getDecorView());
            synchronizeNextDraw(new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AnimatedDialog animatedDialog = AnimatedDialog.this;
                    animatedDialog.isSourceDrawnInDialog = true;
                    AnimatedDialog.access$maybeStartLaunchAnimation(animatedDialog);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDialogDismissed() {
        /*
            r6 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 != 0) goto L21
            android.app.Dialog r0 = r6.dialog
            android.content.Context r0 = r0.getContext()
            java.util.concurrent.Executor r0 = r0.getMainExecutor()
            com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1 r1 = new com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1
            r1.<init>()
            r0.execute(r1)
            return
        L21:
            boolean r0 = r6.isLaunching
            r1 = 1
            if (r0 == 0) goto L29
            r6.dismissRequested = r1
            return
        L29:
            boolean r0 = r6.isDismissing
            if (r0 == 0) goto L2e
            return
        L2e:
            r6.isDismissing = r1
            com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2 r0 = new com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2
            r0.<init>()
            android.view.View$OnLayoutChangeListener r1 = r6.decorViewLayoutListener
            if (r1 == 0) goto L42
            android.view.ViewGroup r1 = r6.getDecorView()
            android.view.View$OnLayoutChangeListener r2 = r6.decorViewLayoutListener
            r1.removeOnLayoutChangeListener(r2)
        L42:
            boolean r1 = r6.exitAnimationDisabled
            com.android.systemui.animation.DialogTransitionAnimator$Controller r2 = r6.controller
            r3 = 0
            if (r1 != 0) goto L6b
            android.app.Dialog r1 = r6.dialog
            boolean r1 = r1.isShowing()
            if (r1 != 0) goto L52
            goto L6b
        L52:
            com.android.systemui.animation.DialogTransitionAnimator$Callback r1 = r6.callback
            com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1 r1 = (com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1) r1
            r1.getClass()
            android.service.dreams.IDreamManager r1 = r1.val$dreamManager     // Catch: android.os.RemoteException -> L60
            boolean r1 = r1.isDreaming()     // Catch: android.os.RemoteException -> L60
            goto L69
        L60:
            r1 = move-exception
            java.lang.String r4 = "DialogTransitionAnimator.Callback"
            java.lang.String r5 = "dreamManager.isDreaming failed"
            android.util.Log.e(r4, r5, r1)
            r1 = r3
        L69:
            if (r1 == 0) goto L6d
        L6b:
            r1 = r3
            goto L71
        L6d:
            boolean r1 = r2.shouldAnimateExit()
        L71:
            if (r1 != 0) goto L88
            java.lang.String r1 = "DialogTransitionAnimator"
            java.lang.String r3 = "Skipping animation of dialog into the source"
            android.util.Log.i(r1, r3)
            r2.onExitAnimationCancelled()
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r0.invoke(r1)
            kotlin.jvm.functions.Function1 r0 = r6.onDialogDismissed
            r0.invoke(r6)
            goto L95
        L88:
            com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1 r1 = new com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1
            r1.<init>()
            com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2 r2 = new com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2
            r2.<init>()
            r6.startAnimation(r1, r2, r3)
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.AnimatedDialog.onDialogDismissed():void");
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
        TransitionAnimator.Controller createExitController;
        DialogTransitionAnimator.Controller controller = this.controller;
        if (z) {
            ghostedViewTransitionAnimatorController = controller.createTransitionController();
        } else {
            ViewGroup viewGroup = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup);
            ghostedViewTransitionAnimatorController = new GhostedViewTransitionAnimatorController(viewGroup, null, null, null, null, null, 62, null);
        }
        if (z) {
            ViewGroup viewGroup2 = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup2);
            createExitController = new GhostedViewTransitionAnimatorController(viewGroup2, null, null, null, null, null, 62, null);
        } else {
            createExitController = controller.createExitController();
        }
        final TransitionAnimator.Controller controller2 = createExitController;
        ghostedViewTransitionAnimatorController.setTransitionContainer(getDecorView());
        controller2.setTransitionContainer(getDecorView());
        final TransitionAnimator.State createAnimatorState = controller2.createAnimatorState();
        TransitionAnimator.Controller controller3 = new TransitionAnimator.Controller() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1
            public final boolean isLaunching = true;

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                return TransitionAnimator.Controller.this.createAnimatorState();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return TransitionAnimator.Controller.this.getTransitionContainer();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return this.isLaunching;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(final boolean z2) {
                Executor mainExecutor = this.dialog.getContext().getMainExecutor();
                final TransitionAnimator.Controller controller4 = controller2;
                final Function0 function03 = function02;
                final TransitionAnimator.Controller controller5 = TransitionAnimator.Controller.this;
                mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1$onTransitionAnimationEnd$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TransitionAnimator.Controller.this.onTransitionAnimationEnd(z2);
                        controller4.onTransitionAnimationEnd(z2);
                        function03.invoke();
                    }
                });
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                TransitionAnimator.Controller.this.onTransitionAnimationProgress(state, f, f2);
                state.visible = !state.visible;
                TransitionAnimator.Controller controller4 = controller2;
                controller4.onTransitionAnimationProgress(state, f, f2);
                if (controller4 instanceof GhostedViewTransitionAnimatorController) {
                    ((GhostedViewTransitionAnimatorController) controller4).fillGhostedViewState(createAnimatorState);
                }
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z2) {
                function0.invoke();
                TransitionAnimator.Controller.this.onTransitionAnimationStart(z2);
                controller2.onTransitionAnimationStart(z2);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup3) {
                TransitionAnimator.Controller.this.setTransitionContainer(viewGroup3);
                controller2.setTransitionContainer(viewGroup3);
            }
        };
        int i = this.originalDialogBackgroundColor;
        TransitionAnimator.Companion companion = TransitionAnimator.Companion;
        this.transitionAnimator.startAnimation(controller3, createAnimatorState, i, true, false);
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

    public /* synthetic */ AnimatedDialog(TransitionAnimator transitionAnimator, DialogTransitionAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogTransitionAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2, AnimationFeatureFlags animationFeatureFlags, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionAnimator, callback, interactionJankMonitor, controller, function1, dialog, z, (i & 128) != 0 ? null : animatedDialog, z2, animationFeatureFlags);
    }
}
