package com.android.systemui.animation;

import android.R;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$1;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule$2;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DialogLaunchAnimator {
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    public static final LaunchAnimator.Timings TIMINGS;
    public final Callback callback;
    public final AnimationFeatureFlags featureFlags;
    public final InteractionJankMonitor interactionJankMonitor;
    public final boolean isForTesting;
    public final LaunchAnimator launchAnimator;
    public final HashSet openedDialogs;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public static ViewDialogLaunchAnimatorController fromView(View view, DialogCuj dialogCuj) {
                if (!(view instanceof LaunchableView)) {
                    throw new IllegalArgumentException("A DialogLaunchAnimator.Controller was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
                }
                if (view.getParent() instanceof ViewGroup) {
                    return new ViewDialogLaunchAnimatorController(view, dialogCuj);
                }
                Log.e("DialogLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }
        }
    }

    static {
        new Companion(null);
        TIMINGS = ActivityLaunchAnimator.TIMINGS;
        ActivityLaunchAnimator.Companion companion = ActivityLaunchAnimator.Companion;
        companion.getClass();
        LaunchAnimator.Interpolators interpolators = ActivityLaunchAnimator.INTERPOLATORS;
        companion.getClass();
        Interpolator interpolator = interpolators.positionInterpolator;
        Interpolator interpolator2 = interpolators.contentBeforeFadeOutInterpolator;
        Interpolator interpolator3 = interpolators.contentAfterFadeInInterpolator;
        interpolators.getClass();
        INTERPOLATORS = new LaunchAnimator.Interpolators(interpolator, interpolator, interpolator2, interpolator3);
    }

    public DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags) {
        this(callback, interactionJankMonitor, animationFeatureFlags, null, false, 24, null);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1] */
    public static DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default(DialogLaunchAnimator dialogLaunchAnimator, View view) {
        Object obj;
        Iterator it = dialogLaunchAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog.getWindow().getDecorView().getViewRootImpl(), view.getViewRootImpl())) {
                break;
            }
        }
        final AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog == null) {
            return null;
        }
        animatedDialog.exitAnimationDisabled = true;
        final Dialog dialog = animatedDialog.dialog;
        if (!dialog.isShowing()) {
            return null;
        }
        CentralSurfacesDependenciesModule$1 centralSurfacesDependenciesModule$1 = (CentralSurfacesDependenciesModule$1) dialogLaunchAnimator.callback;
        if (!centralSurfacesDependenciesModule$1.val$keyguardStateController.isUnlocked()) {
            ((AlternateBouncerInteractor) centralSurfacesDependenciesModule$1.val$alternateBouncerInteractor.get()).getClass();
            return null;
        }
        ViewGroup viewGroup = animatedDialog.dialogContentWithBackground;
        if (viewGroup == null) {
            return null;
        }
        ActivityLaunchAnimator.Controller.Companion.getClass();
        final GhostedViewLaunchAnimatorController fromView = ActivityLaunchAnimator.Controller.Companion.fromView(viewGroup, null);
        if (fromView == null) {
            return null;
        }
        return new ActivityLaunchAnimator.Controller(dialog, animatedDialog) { // from class: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1
            public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
            public final /* synthetic */ AnimatedDialog $animatedDialog;
            public final /* synthetic */ Dialog $dialog;
            public final boolean isDialogLaunch = true;

            {
                this.$dialog = dialog;
                this.$animatedDialog = animatedDialog;
                this.$$delegate_0 = ActivityLaunchAnimator.Controller.this;
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final LaunchAnimator.State createAnimatorState() {
                return this.$$delegate_0.createAnimatorState();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final ViewGroup getLaunchContainer() {
                return this.$$delegate_0.getLaunchContainer();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final View getOpeningWindowSyncView() {
                return this.$$delegate_0.getOpeningWindowSyncView();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final boolean isBelowAnimatingWindow() {
                return this.$$delegate_0.isBelowAnimatingWindow();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final boolean isDialogLaunch() {
                return this.isDialogLaunch;
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final void onIntentStarted(boolean z) {
                ActivityLaunchAnimator.Controller.this.onIntentStarted(z);
                if (z) {
                    return;
                }
                this.$dialog.dismiss();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final void onLaunchAnimationCancelled(Boolean bool) {
                ActivityLaunchAnimator.Controller.this.onLaunchAnimationCancelled(null);
                RunnableC1030xf3619ef1 runnableC1030xf3619ef1 = new RunnableC1030xf3619ef1(this.$animatedDialog);
                Dialog dialog2 = this.$dialog;
                dialog2.setDismissOverride(runnableC1030xf3619ef1);
                dialog2.dismiss();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationEnd(boolean z) {
                ActivityLaunchAnimator.Controller.this.onLaunchAnimationEnd(z);
                Dialog dialog2 = this.$dialog;
                dialog2.hide();
                dialog2.setDismissOverride(new RunnableC1030xf3619ef1(this.$animatedDialog));
                dialog2.dismiss();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                this.$$delegate_0.onLaunchAnimationProgress(state, f, f2);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationStart(boolean z) {
                ActivityLaunchAnimator.Controller.this.onLaunchAnimationStart(z);
                RunnableC1029x59753e02 runnableC1029x59753e02 = new Runnable() { // from class: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1$disableDialogDismiss$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
                Dialog dialog2 = this.$dialog;
                dialog2.setDismissOverride(runnableC1029x59753e02);
                this.$animatedDialog.prepareForStackDismiss();
                dialog2.getWindow().clearFlags(2);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void setLaunchContainer(ViewGroup viewGroup2) {
                this.$$delegate_0.setLaunchContainer(viewGroup2);
            }
        };
    }

    public static void showFromView$default(DialogLaunchAnimator dialogLaunchAnimator, Dialog dialog, View view, DialogCuj dialogCuj, boolean z, int i) {
        if ((i & 4) != 0) {
            dialogCuj = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        dialogLaunchAnimator.getClass();
        Controller.Companion.getClass();
        ViewDialogLaunchAnimatorController fromView = Controller.Companion.fromView(view, dialogCuj);
        if (fromView == null) {
            dialog.show();
        } else {
            dialogLaunchAnimator.show(dialog, fromView, z);
        }
    }

    public final void disableAllCurrentDialogsExitAnimations() {
        Iterator it = this.openedDialogs.iterator();
        while (it.hasNext()) {
            ((AnimatedDialog) it.next()).exitAnimationDisabled = true;
        }
    }

    public final void dismissStack(SystemUIDialog systemUIDialog) {
        Object obj;
        Iterator it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog, systemUIDialog)) {
                    break;
                }
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog != null) {
            animatedDialog.prepareForStackDismiss();
        }
        systemUIDialog.dismiss();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0093  */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Dialog dialog, ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController, boolean z) {
        final ViewGroup viewGroup;
        Object obj;
        ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController2;
        boolean z2;
        ColorStateList color;
        ViewGroup viewGroup2;
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            throw new IllegalStateException("showFromView must be called from the main thread and dialog must be created in the main thread");
        }
        HashSet hashSet = this.openedDialogs;
        Iterator it = hashSet.iterator();
        while (true) {
            viewGroup = null;
            if (it.hasNext()) {
                obj = it.next();
                if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog.getWindow().getDecorView().getViewRootImpl(), viewDialogLaunchAnimatorController.source.getViewRootImpl())) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        AnimatedDialog animatedDialog = (AnimatedDialog) obj;
        if (animatedDialog != null && (viewGroup2 = animatedDialog.dialogContentWithBackground) != null) {
            Controller.Companion.getClass();
            ViewDialogLaunchAnimatorController fromView = Controller.Companion.fromView(viewGroup2, viewDialogLaunchAnimatorController.cuj);
            if (fromView != null) {
                viewDialogLaunchAnimatorController2 = fromView;
                if (!hashSet.isEmpty()) {
                    Iterator it2 = hashSet.iterator();
                    while (it2.hasNext()) {
                        if (Intrinsics.areEqual(((ViewDialogLaunchAnimatorController) ((AnimatedDialog) it2.next()).controller).sourceIdentity, viewDialogLaunchAnimatorController2.sourceIdentity)) {
                            z2 = true;
                            break;
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                    Log.e("DialogLaunchAnimator", "Not running dialog launch animation from source as it is already expanded into a dialog");
                    dialog.show();
                    return;
                }
                final AnimatedDialog animatedDialog2 = new AnimatedDialog(this.launchAnimator, this.callback, this.interactionJankMonitor, viewDialogLaunchAnimatorController2, new Function1() { // from class: com.android.systemui.animation.DialogLaunchAnimator$show$animatedDialog$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        DialogLaunchAnimator.this.openedDialogs.remove((AnimatedDialog) obj2);
                        return Unit.INSTANCE;
                    }
                }, dialog, z, animatedDialog, this.isForTesting, this.featureFlags);
                hashSet.add(animatedDialog2);
                ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController3 = (ViewDialogLaunchAnimatorController) animatedDialog2.controller;
                DialogCuj dialogCuj = viewDialogLaunchAnimatorController3.cuj;
                if (dialogCuj != null) {
                    InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(dialogCuj.cujType, viewDialogLaunchAnimatorController3.source);
                    if (withView != null) {
                        String str = dialogCuj.tag;
                        if (str != null) {
                            withView.setTag(str);
                        }
                        animatedDialog2.interactionJankMonitor.begin(withView);
                        animatedDialog2.hasInstrumentedJank = true;
                    }
                }
                Dialog dialog2 = animatedDialog2.dialog;
                dialog2.create();
                final Window window = dialog2.getWindow();
                Intrinsics.checkNotNull(window);
                if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
                    int childCount = animatedDialog2.getDecorView().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        viewGroup = AnimatedDialog.findFirstViewGroupWithBackground(animatedDialog2.getDecorView().getChildAt(i));
                        if (viewGroup != null) {
                            break;
                        }
                    }
                    if (viewGroup == null) {
                        throw new IllegalStateException("Unable to find ViewGroup with background".toString());
                    }
                    if (!(viewGroup instanceof LaunchableView)) {
                        throw new IllegalStateException("The animated ViewGroup with background must implement LaunchableView".toString());
                    }
                } else {
                    FrameLayout frameLayout = new FrameLayout(dialog2.getContext());
                    animatedDialog2.getDecorView().addView(frameLayout, 0, new FrameLayout.LayoutParams(-1, -1));
                    final LaunchableFrameLayout launchableFrameLayout = new LaunchableFrameLayout(dialog2.getContext());
                    launchableFrameLayout.setBackground(animatedDialog2.getDecorView().getBackground());
                    window.setBackgroundDrawableResource(R.color.transparent);
                    frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AnimatedDialog.this.dialog.dismiss();
                        }
                    });
                    launchableFrameLayout.setClickable(true);
                    frameLayout.setImportantForAccessibility(2);
                    launchableFrameLayout.setImportantForAccessibility(2);
                    frameLayout.addView(launchableFrameLayout, new FrameLayout.LayoutParams(window.getAttributes().width, window.getAttributes().height, window.getAttributes().gravity));
                    int childCount2 = animatedDialog2.getDecorView().getChildCount();
                    for (int i2 = 1; i2 < childCount2; i2++) {
                        View childAt = animatedDialog2.getDecorView().getChildAt(1);
                        animatedDialog2.getDecorView().removeViewAt(1);
                        launchableFrameLayout.addView(childAt);
                    }
                    window.setLayout(-1, -1);
                    animatedDialog2.decorViewLayoutListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                            if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
                                return;
                            }
                            ViewGroup.LayoutParams layoutParams = launchableFrameLayout.getLayoutParams();
                            layoutParams.width = window.getAttributes().width;
                            layoutParams.height = window.getAttributes().height;
                            launchableFrameLayout.setLayoutParams(layoutParams);
                            window.setLayout(-1, -1);
                        }
                    };
                    animatedDialog2.getDecorView().addOnLayoutChangeListener(animatedDialog2.decorViewLayoutListener);
                    viewGroup = launchableFrameLayout;
                }
                animatedDialog2.dialogContentWithBackground = viewGroup;
                viewGroup.setTag(com.android.systemui.R.id.tag_dialog_background, Boolean.TRUE);
                Drawable background = viewGroup.getBackground();
                GhostedViewLaunchAnimatorController.Companion.getClass();
                GradientDrawable findGradientDrawable = GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(background);
                animatedDialog2.originalDialogBackgroundColor = (findGradientDrawable == null || (color = findGradientDrawable.getColor()) == null) ? EmergencyPhoneWidget.BG_COLOR : color.getDefaultColor();
                ((LaunchableView) viewGroup).setShouldBlockVisibilityChanges(true);
                viewGroup.setTransitionVisibility(4);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.windowAnimations = 2132017165;
                attributes.layoutInDisplayCutoutMode = 3;
                final boolean z3 = (attributes.getFitInsetsTypes() & WindowInsets.Type.navigationBars()) != 0;
                attributes.setFitInsetsTypes(attributes.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()));
                window.setAttributes(window.getAttributes());
                window.setDecorFitsSystemWindows(false);
                ((ViewGroup) viewGroup.getParent()).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        Insets insets = windowInsets.getInsets(z3 ? WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars() : WindowInsets.Type.displayCutout());
                        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                        return WindowInsets.CONSUMED;
                    }
                });
                viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$2
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                        ((ViewGroup) viewGroup).removeOnLayoutChangeListener(this);
                        AnimatedDialog animatedDialog3 = animatedDialog2;
                        animatedDialog3.isOriginalDialogViewLaidOut = true;
                        AnimatedDialog.access$maybeStartLaunchAnimation(animatedDialog3);
                    }
                });
                window.clearFlags(2);
                dialog2.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$start$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AnimatedDialog.this.onDialogDismissed();
                    }
                });
                CentralSurfacesDependenciesModule$2 centralSurfacesDependenciesModule$2 = (CentralSurfacesDependenciesModule$2) animatedDialog2.featureFlags;
                centralSurfacesDependenciesModule$2.getClass();
                Flags flags = Flags.INSTANCE;
                centralSurfacesDependenciesModule$2.val$featureFlags.getClass();
                dialog2.show();
                animatedDialog2.moveSourceDrawingToDialog();
                return;
            }
        }
        viewDialogLaunchAnimatorController2 = viewDialogLaunchAnimatorController;
        if (!hashSet.isEmpty()) {
        }
        z2 = false;
        if (!z2) {
        }
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
            Log.w("DialogLaunchAnimator", "Showing dialog " + dialog + " normally as the dialog it is shown from was not shown using DialogLaunchAnimator");
            dialog.show();
            return;
        }
        Controller.Companion.getClass();
        ViewDialogLaunchAnimatorController fromView = Controller.Companion.fromView(viewGroup, dialogCuj);
        if (fromView == null) {
            dialog.show();
        } else {
            show(dialog, fromView, z);
        }
    }

    public DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, LaunchAnimator launchAnimator) {
        this(callback, interactionJankMonitor, animationFeatureFlags, launchAnimator, false, 16, null);
    }

    public DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, LaunchAnimator launchAnimator, boolean z) {
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.featureFlags = animationFeatureFlags;
        this.launchAnimator = launchAnimator;
        this.isForTesting = z;
        this.openedDialogs = new HashSet();
    }

    public /* synthetic */ DialogLaunchAnimator(Callback callback, InteractionJankMonitor interactionJankMonitor, AnimationFeatureFlags animationFeatureFlags, LaunchAnimator launchAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(callback, interactionJankMonitor, animationFeatureFlags, (i & 8) != 0 ? new LaunchAnimator(TIMINGS, INTERPOLATORS) : launchAnimator, (i & 16) != 0 ? false : z);
    }
}
