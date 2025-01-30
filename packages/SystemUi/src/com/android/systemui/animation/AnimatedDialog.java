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
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.AnimatedDialog;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimatedDialog {
    public final AnimatedBoundsLayoutListener backgroundLayoutListener;
    public final DialogLaunchAnimator.Callback callback;
    public final DialogLaunchAnimator.Controller controller;
    public final Lazy decorView$delegate;
    public AnimatedDialog$start$dialogContentWithBackground$2 decorViewLayoutListener;
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
    public final LaunchAnimator launchAnimator;
    public final Function1 onDialogDismissed;
    public int originalDialogBackgroundColor;
    public final AnimatedDialog parentAnimatedDialog;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimatedBoundsLayoutListener implements View.OnLayoutChangeListener {
        public ValueAnimator currentAnimator;
        public Rect lastBounds;

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

    public AnimatedDialog(LaunchAnimator launchAnimator, DialogLaunchAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogLaunchAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2, AnimationFeatureFlags animationFeatureFlags) {
        this.launchAnimator = launchAnimator;
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
        this.originalDialogBackgroundColor = EmergencyPhoneWidget.BG_COLOR;
        this.isLaunching = true;
        this.backgroundLayoutListener = z ? new AnimatedBoundsLayoutListener() : null;
    }

    public static final void access$maybeStartLaunchAnimation(final AnimatedDialog animatedDialog) {
        if (animatedDialog.isSourceDrawnInDialog && animatedDialog.isOriginalDialogViewLaidOut) {
            animatedDialog.dialog.getWindow().addFlags(2);
            animatedDialog.startAnimation(true, new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$1
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
                        DialogCuj dialogCuj = ((ViewDialogLaunchAnimatorController) animatedDialog4.controller).cuj;
                        Intrinsics.checkNotNull(dialogCuj);
                        animatedDialog4.interactionJankMonitor.end(dialogCuj.cujType);
                    }
                    return Unit.INSTANCE;
                }
            });
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void moveSourceDrawingToDialog() {
        if (getDecorView().getViewRootImpl() == null) {
            getDecorView().post(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.moveSourceDrawingToDialog();
                }
            });
            return;
        }
        ViewGroup decorView = getDecorView();
        View view = ((ViewDialogLaunchAnimatorController) this.controller).source;
        LaunchableView launchableView = view instanceof LaunchableView ? (LaunchableView) view : null;
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        GhostView.addGhost(view, decorView);
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

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0091, code lost:
    
        if ((r1 != null ? r1.isShown() : true) != false) goto L43;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDialogDismissed() {
        boolean z;
        boolean areEqual = Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper());
        Dialog dialog = this.dialog;
        if (!areEqual) {
            dialog.getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.onDialogDismissed();
                }
            });
            return;
        }
        boolean z2 = true;
        if (this.isLaunching) {
            this.dismissRequested = true;
            return;
        }
        if (this.isDismissing) {
            return;
        }
        this.isDismissing = true;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                if (((Boolean) obj).booleanValue()) {
                    AnimatedDialog.this.dialog.hide();
                }
                AnimatedDialog.this.dialog.setDismissOverride(null);
                AnimatedDialog.this.dialog.dismiss();
                return Unit.INSTANCE;
            }
        };
        if (this.decorViewLayoutListener != null) {
            getDecorView().removeOnLayoutChangeListener(this.decorViewLayoutListener);
        }
        boolean z3 = this.exitAnimationDisabled;
        DialogLaunchAnimator.Controller controller = this.controller;
        if (!z3 && dialog.isShowing()) {
            CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) this.callback;
            centralSurfacesDependenciesModule$1.getClass();
            try {
                z = centralSurfacesDependenciesModule$1.val$dreamManager.isDreaming();
            } catch (RemoteException e) {
                Log.e("DialogLaunchAnimator.Callback", "dreamManager.isDreaming failed", e);
                z = false;
            }
            if (!z) {
                View view = ((ViewDialogLaunchAnimatorController) controller).source;
                if (view.getVisibility() == 4 && view.isAttachedToWindow()) {
                    Object parent = view.getParent();
                    View view2 = parent instanceof View ? (View) parent : null;
                }
            }
        }
        z2 = false;
        if (z2) {
            startAnimation(false, new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AnimatedDialog.this.dialog.getWindow().clearFlags(2);
                    return Unit.INSTANCE;
                }
            }, new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ViewGroup viewGroup = AnimatedDialog.this.dialogContentWithBackground;
                    Intrinsics.checkNotNull(viewGroup);
                    viewGroup.setVisibility(4);
                    AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener = AnimatedDialog.this.backgroundLayoutListener;
                    if (animatedBoundsLayoutListener != null) {
                        viewGroup.removeOnLayoutChangeListener(animatedBoundsLayoutListener);
                    }
                    View view3 = ((ViewDialogLaunchAnimatorController) AnimatedDialog.this.controller).source;
                    if (view3 instanceof LaunchableView) {
                        ((LaunchableView) view3).setShouldBlockVisibilityChanges(false);
                    } else {
                        view3.setVisibility(0);
                    }
                    final AnimatedDialog animatedDialog = AnimatedDialog.this;
                    final Function1 function12 = function1;
                    animatedDialog.synchronizeNextDraw(new Function0() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Function1.this.invoke(Boolean.TRUE);
                            AnimatedDialog animatedDialog2 = animatedDialog;
                            animatedDialog2.onDialogDismissed.invoke(animatedDialog2);
                            return Unit.INSTANCE;
                        }
                    });
                    return Unit.INSTANCE;
                }
            });
            return;
        }
        Log.i("DialogLaunchAnimator", "Skipping animation of dialog into the source");
        View view3 = ((ViewDialogLaunchAnimatorController) controller).source;
        if (view3 instanceof LaunchableView) {
            ((LaunchableView) view3).setShouldBlockVisibilityChanges(false);
        } else if (view3.getVisibility() == 4) {
            view3.setVisibility(0);
        }
        function1.invoke(Boolean.FALSE);
        this.onDialogDismissed.invoke(this);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.animation.ViewDialogLaunchAnimatorController$createLaunchController$1] */
    public final void startAnimation(boolean z, final Function0 function0, final Function0 function02) {
        final GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        final GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController2;
        DialogLaunchAnimator.Controller controller = this.controller;
        if (z) {
            final ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController = (ViewDialogLaunchAnimatorController) controller;
            viewDialogLaunchAnimatorController.getClass();
            final GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController3 = new GhostedViewLaunchAnimatorController(viewDialogLaunchAnimatorController.source, null, null, 6, null);
            ghostedViewLaunchAnimatorController = new LaunchAnimator.Controller(viewDialogLaunchAnimatorController) { // from class: com.android.systemui.animation.ViewDialogLaunchAnimatorController$createLaunchController$1
                public final /* synthetic */ GhostedViewLaunchAnimatorController $$delegate_0;
                public final /* synthetic */ ViewDialogLaunchAnimatorController this$0;

                {
                    this.this$0 = viewDialogLaunchAnimatorController;
                    this.$$delegate_0 = GhostedViewLaunchAnimatorController.this;
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final LaunchAnimator.State createAnimatorState() {
                    return this.$$delegate_0.createAnimatorState();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final ViewGroup getLaunchContainer() {
                    return this.$$delegate_0.launchContainer;
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final View getOpeningWindowSyncView() {
                    this.$$delegate_0.getClass();
                    return null;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final void onLaunchAnimationEnd(boolean z2) {
                    GhostedViewLaunchAnimatorController.this.onLaunchAnimationEnd(z2);
                    ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController2 = this.this$0;
                    View view = viewDialogLaunchAnimatorController2.source;
                    if (!(view instanceof LaunchableView)) {
                        view.setVisibility(4);
                    } else {
                        ((LaunchableView) view).setShouldBlockVisibilityChanges(true);
                        viewDialogLaunchAnimatorController2.source.setTransitionVisibility(4);
                    }
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                    this.$$delegate_0.onLaunchAnimationProgress(state, f, f2);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final void onLaunchAnimationStart(boolean z2) {
                    GhostView.removeGhost(this.this$0.source);
                    GhostedViewLaunchAnimatorController.this.onLaunchAnimationStart(z2);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public final void setLaunchContainer(ViewGroup viewGroup) {
                    this.$$delegate_0.launchContainer = viewGroup;
                }
            };
        } else {
            ViewGroup viewGroup = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup);
            ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(viewGroup, null, null, 6, null);
        }
        if (z) {
            ViewGroup viewGroup2 = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup2);
            ghostedViewLaunchAnimatorController2 = new GhostedViewLaunchAnimatorController(viewGroup2, null, null, 6, null);
        } else {
            ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController2 = (ViewDialogLaunchAnimatorController) controller;
            viewDialogLaunchAnimatorController2.getClass();
            ghostedViewLaunchAnimatorController2 = new GhostedViewLaunchAnimatorController(viewDialogLaunchAnimatorController2.source, null, null, 6, null);
        }
        ghostedViewLaunchAnimatorController.setLaunchContainer(getDecorView());
        ghostedViewLaunchAnimatorController2.setLaunchContainer(getDecorView());
        final LaunchAnimator.State createAnimatorState = ghostedViewLaunchAnimatorController2.createAnimatorState();
        LaunchAnimator.Controller controller2 = new LaunchAnimator.Controller() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1
            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final LaunchAnimator.State createAnimatorState() {
                return LaunchAnimator.Controller.this.createAnimatorState();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final ViewGroup getLaunchContainer() {
                return LaunchAnimator.Controller.this.getLaunchContainer();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationEnd(final boolean z2) {
                Executor mainExecutor = this.dialog.getContext().getMainExecutor();
                final Function0 function03 = function02;
                final LaunchAnimator.Controller controller3 = LaunchAnimator.Controller.this;
                final LaunchAnimator.Controller controller4 = ghostedViewLaunchAnimatorController2;
                mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1$onLaunchAnimationEnd$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchAnimator.Controller.this.onLaunchAnimationEnd(z2);
                        controller4.onLaunchAnimationEnd(z2);
                        function03.invoke();
                    }
                });
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                LaunchAnimator.Controller.this.onLaunchAnimationProgress(state, f, f2);
                state.visible = !state.visible;
                LaunchAnimator.Controller controller3 = ghostedViewLaunchAnimatorController2;
                controller3.onLaunchAnimationProgress(state, f, f2);
                if (controller3 instanceof GhostedViewLaunchAnimatorController) {
                    ((GhostedViewLaunchAnimatorController) controller3).fillGhostedViewState(createAnimatorState);
                }
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationStart(boolean z2) {
                function0.invoke();
                LaunchAnimator.Controller.this.onLaunchAnimationStart(z2);
                ghostedViewLaunchAnimatorController2.onLaunchAnimationStart(z2);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void setLaunchContainer(ViewGroup viewGroup3) {
                LaunchAnimator.Controller.this.setLaunchContainer(viewGroup3);
                ghostedViewLaunchAnimatorController2.setLaunchContainer(viewGroup3);
            }
        };
        LaunchAnimator launchAnimator = this.launchAnimator;
        int i = this.originalDialogBackgroundColor;
        LaunchAnimator.Companion companion = LaunchAnimator.Companion;
        launchAnimator.startAnimation(controller2, createAnimatorState, i, true, false);
    }

    public final void synchronizeNextDraw(Function0 function0) {
        ViewRootImpl viewRootImpl = ((ViewDialogLaunchAnimatorController) this.controller).source.getViewRootImpl();
        View view = viewRootImpl != null ? viewRootImpl.getView() : null;
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

    public /* synthetic */ AnimatedDialog(LaunchAnimator launchAnimator, DialogLaunchAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogLaunchAnimator.Controller controller, Function1 function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2, AnimationFeatureFlags animationFeatureFlags, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(launchAnimator, callback, interactionJankMonitor, controller, function1, dialog, z, (i & 128) != 0 ? null : animatedDialog, z2, animationFeatureFlags);
    }
}
