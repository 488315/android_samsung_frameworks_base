package com.android.systemui.animation;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.res.ColorStateList;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.WindowAnimationState;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.animation.view.LaunchableFrameLayout;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1;
import com.android.systemui.util.DialogKt;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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

    public interface Callback {
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

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

    public static AnonymousClass1 createActivityTransitionController$default(DialogTransitionAnimator dialogTransitionAnimator, View view) {
        Object next;
        View decorView;
        Iterator it = dialogTransitionAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Window window = ((AnimatedDialog) next).dialog.getWindow();
            if (Intrinsics.areEqual((window == null || (decorView = window.getDecorView()) == null) ? null : decorView.getViewRootImpl(), view.getViewRootImpl())) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) next;
        if (animatedDialog == null) {
            return null;
        }
        return dialogTransitionAnimator.createActivityTransitionController(animatedDialog);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1] */
    public final AnonymousClass1 createActivityTransitionController(AnimatedDialog animatedDialog) {
        GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorControllerFromView$default;
        animatedDialog.exitAnimationDisabled = true;
        Dialog dialog = animatedDialog.dialog;
        if (dialog.isShowing()) {
            CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) this.callback;
            if (!centralSurfacesDependenciesModule$1.val$keyguardStateController.isUnlocked()) {
                ((AlternateBouncerInteractor) centralSurfacesDependenciesModule$1.val$alternateBouncerInteractor.get()).getClass();
                return null;
            }
            ViewGroup viewGroup = animatedDialog.dialogContentWithBackground;
            if (viewGroup != null && (ghostedViewTransitionAnimatorControllerFromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, viewGroup, null, 60)) != null) {
                return new ActivityTransitionAnimator.Controller(dialog, animatedDialog) { // from class: com.android.systemui.animation.DialogTransitionAnimator.createActivityTransitionController.1
                    public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
                    public final /* synthetic */ AnimatedDialog $animatedDialog;
                    public final /* synthetic */ Dialog $dialog;

                    {
                        this.$dialog = dialog;
                        this.$animatedDialog = animatedDialog;
                        this.$$delegate_0 = this.$controller;
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
                        this.$controller.onIntentStarted(z);
                        if (z) {
                            return;
                        }
                        this.$dialog.dismiss();
                    }

                    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
                    public final void onTransitionAnimationCancelled() {
                        this.$controller.onTransitionAnimationCancelled();
                        this.$dialog.setDismissOverride(new DialogTransitionAnimator$createActivityTransitionController$1$enableDialogDismiss$1(this.$animatedDialog));
                        this.$dialog.dismiss();
                    }

                    @Override // com.android.systemui.animation.TransitionAnimator.Controller
                    public final void onTransitionAnimationEnd(boolean z) {
                        this.$controller.onTransitionAnimationEnd(z);
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
                        this.$controller.onTransitionAnimationStart(z);
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
        Object next;
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) next).dialog, dialog)) {
                    break;
                }
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) next;
        if (animatedDialog != null) {
            animatedDialog.prepareForStackDismiss();
        }
        dialog.dismiss();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Dialog dialog, Controller controller, boolean z) {
        Object next;
        Controller controller2;
        final ViewGroup viewGroupFindFirstViewGroupWithBackground;
        ColorStateList color;
        InteractionJankMonitor.Configuration.Builder builderJankConfigurationBuilder;
        ViewGroup viewGroup;
        View decorView;
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            throw new IllegalStateException("showFromView must be called from the main thread and dialog must be created in the main thread");
        }
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Window window = ((AnimatedDialog) next).dialog.getWindow();
            if (Intrinsics.areEqual((window == null || (decorView = window.getDecorView()) == null) ? null : decorView.getViewRootImpl(), controller.getViewRoot())) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) next;
        if (animatedDialog == null || (viewGroup = animatedDialog.dialogContentWithBackground) == null) {
            controller2 = controller;
        } else {
            Controller.Companion companion = Controller.Companion;
            DialogCuj cuj = controller.getCuj();
            companion.getClass();
            ViewDialogTransitionAnimatorController viewDialogTransitionAnimatorControllerFromView = Controller.Companion.fromView(viewGroup, cuj);
            if (viewDialogTransitionAnimatorControllerFromView != null) {
                controller2 = viewDialogTransitionAnimatorControllerFromView;
            }
        }
        HashSet hashSet = this.openedDialogs;
        if (hashSet == null || !hashSet.isEmpty()) {
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(((AnimatedDialog) it2.next()).controller.getSourceIdentity(), controller2.getSourceIdentity())) {
                    Log.e("DialogTransitionAnimator", "Not running dialog launch animation from source as it is already expanded into a dialog");
                    dialog.show();
                    return;
                }
            }
        }
        final AnimatedDialog animatedDialog2 = new AnimatedDialog(this.transitionAnimator, this.callback, this.interactionJankMonitor, controller2, new Function1() { // from class: com.android.systemui.animation.DialogTransitionAnimator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                this.f$0.openedDialogs.remove((AnimatedDialog) obj);
                return Unit.INSTANCE;
            }
        }, dialog, z, animatedDialog, this.isForTesting);
        this.openedDialogs.add(animatedDialog2);
        Controller controller3 = animatedDialog2.controller;
        DialogCuj cuj2 = controller3.getCuj();
        if (cuj2 != null && (builderJankConfigurationBuilder = controller3.jankConfigurationBuilder()) != null) {
            String str = cuj2.tag;
            if (str != null) {
                builderJankConfigurationBuilder.setTag(str);
            }
            animatedDialog2.interactionJankMonitor.begin(builderJankConfigurationBuilder);
            animatedDialog2.hasInstrumentedJank = true;
        }
        animatedDialog2.dialog.create();
        Window window2 = animatedDialog2.dialog.getWindow();
        window2.getClass();
        if (window2.getAttributes().width == -1 && window2.getAttributes().height == -1) {
            int childCount = animatedDialog2.getDecorView().getChildCount();
            viewGroupFindFirstViewGroupWithBackground = null;
            for (int i = 0; i < childCount; i++) {
                viewGroupFindFirstViewGroupWithBackground = AnimatedDialog.findFirstViewGroupWithBackground(animatedDialog2.getDecorView().getChildAt(i));
                if (viewGroupFindFirstViewGroupWithBackground != null) {
                    break;
                }
            }
            if (viewGroupFindFirstViewGroupWithBackground == null) {
                throw new IllegalStateException("Unable to find ViewGroup with background");
            }
            if (!(viewGroupFindFirstViewGroupWithBackground instanceof LaunchableView)) {
                throw new IllegalStateException("The animated ViewGroup with background must implement LaunchableView");
            }
        } else {
            Pair<LaunchableFrameLayout, View.OnLayoutChangeListener> pairMaybeForceFullscreen = DialogKt.maybeForceFullscreen(animatedDialog2.dialog);
            pairMaybeForceFullscreen.getClass();
            viewGroupFindFirstViewGroupWithBackground = (LaunchableFrameLayout) pairMaybeForceFullscreen.component1();
            animatedDialog2.decorViewLayoutListener = (View.OnLayoutChangeListener) pairMaybeForceFullscreen.component2();
        }
        animatedDialog2.dialogContentWithBackground = viewGroupFindFirstViewGroupWithBackground;
        viewGroupFindFirstViewGroupWithBackground.setTag(R.id.tag_dialog_background, Boolean.TRUE);
        Drawable background = viewGroupFindFirstViewGroupWithBackground.getBackground();
        GhostedViewTransitionAnimatorController.Companion companion2 = GhostedViewTransitionAnimatorController.Companion;
        background.getClass();
        companion2.getClass();
        GradientDrawable gradientDrawableFindGradientDrawable = GhostedViewTransitionAnimatorController.Companion.findGradientDrawable(background);
        animatedDialog2.originalDialogBackgroundColor = (gradientDrawableFindGradientDrawable == null || (color = gradientDrawableFindGradientDrawable.getColor()) == null) ? -16777216 : color.getDefaultColor();
        ((LaunchableView) viewGroupFindFirstViewGroupWithBackground).setShouldBlockVisibilityChanges(true);
        viewGroupFindFirstViewGroupWithBackground.setTransitionVisibility(4);
        WindowManager.LayoutParams attributes = window2.getAttributes();
        attributes.windowAnimations = R.style.Animation_LaunchAnimation;
        attributes.layoutInDisplayCutoutMode = 3;
        final boolean z2 = (attributes.getFitInsetsTypes() & WindowInsets.Type.navigationBars()) != 0;
        attributes.setFitInsetsTypes(attributes.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()));
        window2.setAttributes(window2.getAttributes());
        window2.setDecorFitsSystemWindows(false);
        ((ViewGroup) viewGroupFindFirstViewGroupWithBackground.getParent()).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Insets insets = windowInsets.getInsets(z2 ? WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars() : WindowInsets.Type.displayCutout());
                view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                return WindowInsets.CONSUMED;
            }
        });
        viewGroupFindFirstViewGroupWithBackground.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                ((View) viewGroupFindFirstViewGroupWithBackground).removeOnLayoutChangeListener(this);
                AnimatedDialog animatedDialog3 = animatedDialog2;
                animatedDialog3.isOriginalDialogViewLaidOut = true;
                animatedDialog3.maybeStartLaunchAnimation();
            }
        });
        window2.clearFlags(2);
        animatedDialog2.dialog.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$start$3
            @Override // java.lang.Runnable
            public final void run() {
                animatedDialog2.onDialogDismissed();
            }
        });
        DialogKt.registerAnimationOnBackInvoked$default(animatedDialog2.dialog, viewGroupFindFirstViewGroupWithBackground, null, 2, null);
        animatedDialog2.dialog.show();
        animatedDialog2.moveSourceDrawingToDialog();
    }

    public final void showFromDialog(Dialog dialog, Dialog dialog2) {
        Object next;
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) next).dialog, dialog2)) {
                    break;
                }
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) next;
        ViewGroup viewGroup = animatedDialog != null ? animatedDialog.dialogContentWithBackground : null;
        if (viewGroup == null) {
            Log.w("DialogTransitionAnimator", "Showing dialog " + dialog + " normally as the dialog it is shown from was not shown using DialogTransitionAnimator");
            dialog.show();
            return;
        }
        Controller.Companion.getClass();
        ViewDialogTransitionAnimatorController viewDialogTransitionAnimatorControllerFromView = Controller.Companion.fromView(viewGroup, null);
        if (viewDialogTransitionAnimatorControllerFromView == null) {
            dialog.show();
        } else {
            show(dialog, viewDialogTransitionAnimatorControllerFromView, false);
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

    public static AnonymousClass1 createActivityTransitionController$default(Dialog dialog, DialogTransitionAnimator dialogTransitionAnimator) {
        Object next;
        Iterator it = dialogTransitionAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) next).dialog, dialog)) {
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) next;
        if (animatedDialog == null) {
            return null;
        }
        return dialogTransitionAnimator.createActivityTransitionController(animatedDialog);
    }

    public /* synthetic */ DialogTransitionAnimator(Executor executor, Callback callback, InteractionJankMonitor interactionJankMonitor, TransitionAnimator transitionAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, callback, interactionJankMonitor, (i & 8) != 0 ? new TransitionAnimator(executor, TIMINGS, INTERPOLATORS, null, null, null, 56, null) : transitionAnimator, (i & 16) != 0 ? false : z);
    }
}
