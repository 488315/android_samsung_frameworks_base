package com.android.systemui.animation;

import android.content.ComponentName;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.GhostView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.LinkedList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GhostedViewTransitionAnimatorController implements ActivityTransitionAnimator.Controller {
    public static final Companion Companion = new Companion(null);
    public final View _ghostedView;
    public final Drawable background;
    public WrappedDrawable backgroundDrawable;
    public final Lazy backgroundInsets$delegate;
    public FrameLayout backgroundView;
    public final ComponentName component;
    public final GhostedViewTransitionAnimatorController$detachListener$1 detachListener;
    public GhostView ghostView;
    public final Matrix ghostViewMatrix;
    public final int[] ghostedViewLocation;
    public final TransitionAnimator.State ghostedViewState;
    public final float[] initialGhostViewMatrixValues;
    public final InteractionJankMonitor interactionJankMonitor;
    public final boolean isEphemeral;
    public final boolean isLaunching;
    public final Integer launchCujType;
    public final Integer returnCujType;
    public int startBackgroundAlpha;
    public final int[] transitionContainerLocation;
    public final ActivityTransitionAnimator.TransitionCookie transitionCookie;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static GradientDrawable findGradientDrawable(Drawable drawable) {
            if (drawable instanceof GradientDrawable) {
                return (GradientDrawable) drawable;
            }
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 == null) {
                    return null;
                }
                GhostedViewTransitionAnimatorController.Companion.getClass();
                return findGradientDrawable(drawable2);
            }
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                for (int i = 0; i < numberOfLayers; i++) {
                    GradientDrawable findGradientDrawable = findGradientDrawable(layerDrawable.getDrawable(i));
                    if (findGradientDrawable != null) {
                        return findGradientDrawable;
                    }
                }
            }
            if (drawable instanceof StateListDrawable) {
                return findGradientDrawable(((StateListDrawable) drawable).getCurrent());
            }
            return null;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrappedDrawable extends Drawable {
        public final float[] cornerRadii;
        public int currentAlpha = 255;
        public final Rect previousBounds = new Rect();
        public final float[] previousCornerRadii;
        public final Drawable wrapped;

        public WrappedDrawable(Drawable drawable) {
            this.wrapped = drawable;
            float[] fArr = new float[8];
            for (int i = 0; i < 8; i++) {
                fArr[i] = -1.0f;
            }
            this.cornerRadii = fArr;
            this.previousCornerRadii = new float[8];
        }

        public static void applyBackgroundRadii(Drawable drawable, float[] fArr) {
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadii(fArr);
                return;
            }
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 != null) {
                    applyBackgroundRadii(drawable2, fArr);
                    return;
                }
                return;
            }
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                for (int i = 0; i < numberOfLayers; i++) {
                    applyBackgroundRadii(layerDrawable.getDrawable(i), fArr);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            Drawable drawable;
            Drawable drawable2;
            Drawable drawable3 = this.wrapped;
            if (drawable3 == null) {
                return;
            }
            drawable3.copyBounds(this.previousBounds);
            drawable3.setAlpha(this.currentAlpha);
            drawable3.setBounds(getBounds());
            if (this.cornerRadii[0] >= 0.0f && (drawable2 = this.wrapped) != null) {
                GhostedViewTransitionAnimatorController.Companion.getClass();
                GradientDrawable findGradientDrawable = Companion.findGradientDrawable(drawable2);
                if (findGradientDrawable != null) {
                    float[] cornerRadii = findGradientDrawable.getCornerRadii();
                    if (cornerRadii != null) {
                        System.arraycopy(cornerRadii, 0, this.previousCornerRadii, 0, (r3 & 8) != 0 ? cornerRadii.length : 6);
                    } else {
                        float cornerRadius = findGradientDrawable.getCornerRadius();
                        float[] fArr = this.previousCornerRadii;
                        fArr[0] = cornerRadius;
                        fArr[1] = cornerRadius;
                        fArr[2] = cornerRadius;
                        fArr[3] = cornerRadius;
                        fArr[4] = cornerRadius;
                        fArr[5] = cornerRadius;
                        fArr[6] = cornerRadius;
                        fArr[7] = cornerRadius;
                    }
                }
                applyBackgroundRadii(this.wrapped, this.cornerRadii);
            }
            drawable3.draw(canvas);
            drawable3.setAlpha(0);
            drawable3.setBounds(this.previousBounds);
            if (this.cornerRadii[0] < 0.0f || (drawable = this.wrapped) == null) {
                return;
            }
            applyBackgroundRadii(drawable, this.previousCornerRadii);
        }

        @Override // android.graphics.drawable.Drawable
        public final int getAlpha() {
            return this.currentAlpha;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            Drawable drawable = this.wrapped;
            if (drawable == null) {
                return -2;
            }
            int alpha = drawable.getAlpha();
            drawable.setAlpha(this.currentAlpha);
            int opacity = drawable.getOpacity();
            drawable.setAlpha(alpha);
            return opacity;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            if (i != this.currentAlpha) {
                this.currentAlpha = i;
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            Drawable drawable = this.wrapped;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public GhostedViewTransitionAnimatorController(View view) {
        this(view, null, null, null, null, false, null, null, 254, null);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        int i = 0;
        int i2 = 0;
        TransitionAnimator.State state = new TransitionAnimator.State(i, i2, 0, 0, getCurrentTopCornerRadius(), getCurrentBottomCornerRadius(), 15, null);
        fillGhostedViewState(state);
        return state;
    }

    public final void fillGhostedViewState(TransitionAnimator.State state) {
        Rect rect;
        View view = this._ghostedView;
        view.getClass();
        int[] iArr = this.ghostedViewLocation;
        view.getLocationOnScreen(iArr);
        Insets insets = (Insets) this.backgroundInsets$delegate.getValue();
        View view2 = this._ghostedView;
        view2.getClass();
        if (view2 instanceof LaunchableView) {
            KeyEvent.Callback callback = this._ghostedView;
            callback.getClass();
            rect = ((LaunchableView) callback).getPaddingForLaunchAnimation();
        } else {
            rect = new Rect();
        }
        int i = iArr[1];
        state.top = insets.top + i + rect.top;
        View view3 = this._ghostedView;
        view3.getClass();
        float height = view3.getHeight();
        View view4 = this._ghostedView;
        view4.getClass();
        state.bottom = ((MathKt__MathJVMKt.roundToInt(view4.getScaleY() * height) + i) - insets.bottom) + rect.bottom;
        int i2 = iArr[0];
        state.left = insets.left + i2 + rect.left;
        View view5 = this._ghostedView;
        view5.getClass();
        float width = view5.getWidth();
        View view6 = this._ghostedView;
        view6.getClass();
        state.right = ((MathKt__MathJVMKt.roundToInt(view6.getScaleX() * width) + i2) - insets.right) + rect.right;
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ComponentName getComponent() {
        return this.component;
    }

    public float getCurrentBottomCornerRadius() {
        Drawable drawable = this.background;
        if (drawable == null) {
            return 0.0f;
        }
        Companion.getClass();
        GradientDrawable findGradientDrawable = Companion.findGradientDrawable(drawable);
        if (findGradientDrawable == null) {
            return 0.0f;
        }
        float[] cornerRadii = findGradientDrawable.getCornerRadii();
        float cornerRadius = cornerRadii != null ? cornerRadii[4] : findGradientDrawable.getCornerRadius();
        View view = this._ghostedView;
        view.getClass();
        return view.getScaleX() * cornerRadius;
    }

    public float getCurrentTopCornerRadius() {
        Drawable drawable = this.background;
        if (drawable == null) {
            return 0.0f;
        }
        Companion.getClass();
        GradientDrawable findGradientDrawable = Companion.findGradientDrawable(drawable);
        if (findGradientDrawable == null) {
            return 0.0f;
        }
        float[] cornerRadii = findGradientDrawable.getCornerRadii();
        float cornerRadius = cornerRadii != null ? cornerRadii[0] : findGradientDrawable.getCornerRadius();
        View view = this._ghostedView;
        view.getClass();
        return view.getScaleX() * cornerRadius;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        View view = this._ghostedView;
        view.getClass();
        return (ViewGroup) view.getRootView();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
        return this.transitionCookie;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.isLaunching;
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onDispose() {
        TransitionAnimator.Companion.getClass();
        View view = this._ghostedView;
        view.getClass();
        view.removeOnAttachStateChangeListener(this.detachListener);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        Drawable drawable;
        if (this.ghostView == null) {
            return;
        }
        Integer num = this.isLaunching ? this.launchCujType : this.returnCujType;
        if (num != null) {
            this.interactionJankMonitor.end(num.intValue());
        }
        WrappedDrawable wrappedDrawable = this.backgroundDrawable;
        if (wrappedDrawable != null && (drawable = wrappedDrawable.wrapped) != null) {
            drawable.setAlpha(this.startBackgroundAlpha);
        }
        View view = this._ghostedView;
        view.getClass();
        GhostView.removeGhost(view);
        FrameLayout frameLayout = this.backgroundView;
        if (frameLayout != null) {
            getTransitionContainer().getOverlay().remove(frameLayout);
        }
        View view2 = this._ghostedView;
        view2.getClass();
        if (view2 instanceof LaunchableView) {
            KeyEvent.Callback callback = this._ghostedView;
            callback.getClass();
            ((LaunchableView) callback).setShouldBlockVisibilityChanges(false);
            KeyEvent.Callback callback2 = this._ghostedView;
            callback2.getClass();
            ((LaunchableView) callback2).onActivityLaunchAnimationEnd();
        } else {
            View view3 = this._ghostedView;
            view3.getClass();
            view3.setVisibility(4);
            View view4 = this._ghostedView;
            view4.getClass();
            view4.setVisibility(0);
            View view5 = this._ghostedView;
            view5.getClass();
            view5.invalidate();
        }
        if (this.isEphemeral) {
            onDispose();
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        GhostView ghostView = this.ghostView;
        if (ghostView == null) {
            return;
        }
        FrameLayout frameLayout = this.backgroundView;
        frameLayout.getClass();
        if (state.visible) {
            View view = this._ghostedView;
            view.getClass();
            if (view.isAttachedToWindow()) {
                if (ghostView.getVisibility() == 4) {
                    ghostView.setVisibility(0);
                    frameLayout.setVisibility(0);
                }
                TransitionAnimator.State state2 = this.ghostedViewState;
                fillGhostedViewState(state2);
                int i = state.left - state2.left;
                int i2 = state.right - state2.right;
                int i3 = state.top - state2.top;
                int i4 = state.bottom - state2.bottom;
                float min = Math.min(state.getWidth() / state2.getWidth(), state.getHeight() / state2.getHeight());
                View view2 = this._ghostedView;
                view2.getClass();
                if (view2.getParent() instanceof ViewGroup) {
                    View view3 = this._ghostedView;
                    view3.getClass();
                    GhostView.calculateMatrix(view3, getTransitionContainer(), this.ghostViewMatrix);
                }
                ViewGroup transitionContainer = getTransitionContainer();
                int[] iArr = this.transitionContainerLocation;
                transitionContainer.getLocationOnScreen(iArr);
                this.ghostViewMatrix.postScale(min, min, state2.getCenterX() - iArr[0], state2.getCenterY() - iArr[1]);
                this.ghostViewMatrix.postTranslate((i + i2) / 2.0f, (i3 + i4) / 2.0f);
                ghostView.setAnimationMatrix(this.ghostViewMatrix);
                Insets insets = (Insets) this.backgroundInsets$delegate.getValue();
                int i5 = state.top - insets.top;
                int i6 = state.left - insets.left;
                int i7 = state.right + insets.right;
                int i8 = state.bottom + insets.bottom;
                frameLayout.setTop(i5 - iArr[1]);
                frameLayout.setBottom(i8 - iArr[1]);
                frameLayout.setLeft(i6 - iArr[0]);
                frameLayout.setRight(i7 - iArr[0]);
                WrappedDrawable wrappedDrawable = this.backgroundDrawable;
                wrappedDrawable.getClass();
                if (wrappedDrawable.wrapped != null) {
                    float f3 = state.topCornerRadius;
                    float f4 = state.bottomCornerRadius;
                    WrappedDrawable wrappedDrawable2 = this.backgroundDrawable;
                    if (wrappedDrawable2 != null) {
                        float[] fArr = wrappedDrawable2.cornerRadii;
                        fArr[0] = f3;
                        fArr[1] = f3;
                        fArr[2] = f3;
                        fArr[3] = f3;
                        fArr[4] = f4;
                        fArr[5] = f4;
                        fArr[6] = f4;
                        fArr[7] = f4;
                        wrappedDrawable2.invalidateSelf();
                        return;
                    }
                    return;
                }
                return;
            }
        }
        if (ghostView.getVisibility() == 0) {
            ghostView.setVisibility(4);
            View view4 = this._ghostedView;
            view4.getClass();
            view4.setTransitionVisibility(4);
            frameLayout.setVisibility(4);
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        Matrix matrix;
        ViewParent parent;
        View view = this._ghostedView;
        view.getClass();
        if (!(view.getParent() instanceof ViewGroup)) {
            Log.w("GhostedViewTransitionAnimatorController", "Skipping animation as ghostedView is not attached to a ViewGroup");
            return;
        }
        FrameLayout frameLayout = new FrameLayout(getTransitionContainer().getContext());
        getTransitionContainer().getOverlay().add(frameLayout);
        this.backgroundView = frameLayout;
        Drawable drawable = this.background;
        this.startBackgroundAlpha = drawable != null ? drawable.getAlpha() : 255;
        WrappedDrawable wrappedDrawable = new WrappedDrawable(this.background);
        this.backgroundDrawable = wrappedDrawable;
        FrameLayout frameLayout2 = this.backgroundView;
        if (frameLayout2 != null) {
            frameLayout2.setBackground(wrappedDrawable);
        }
        KeyEvent.Callback callback = this._ghostedView;
        callback.getClass();
        LaunchableView launchableView = callback instanceof LaunchableView ? (LaunchableView) callback : null;
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        try {
            View view2 = this._ghostedView;
            view2.getClass();
            this.ghostView = GhostView.addGhost(view2, getTransitionContainer());
        } catch (Exception e) {
            Log.e("GhostedViewTransitionAnimatorController", "Failed to create ghostView", e);
        }
        GhostView ghostView = this.ghostView;
        Object parent2 = (ghostView == null || (parent = ghostView.getParent()) == null) ? null : parent.getParent();
        ViewGroup viewGroup = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
        if (viewGroup != null) {
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
        GhostView ghostView2 = this.ghostView;
        if (ghostView2 == null || (matrix = ghostView2.getAnimationMatrix()) == null) {
            matrix = Matrix.IDENTITY_MATRIX;
        }
        matrix.getValues(this.initialGhostViewMatrixValues);
        Integer num = this.isLaunching ? this.launchCujType : this.returnCujType;
        if (num != null) {
            int intValue = num.intValue();
            InteractionJankMonitor interactionJankMonitor = this.interactionJankMonitor;
            View view3 = this._ghostedView;
            view3.getClass();
            interactionJankMonitor.begin(view3, intValue);
        }
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num) {
        this(view, num, null, null, null, false, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList, null);
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie) {
        this(view, num, transitionCookie, null, null, false, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut, null);
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName) {
        this(view, num, transitionCookie, componentName, null, false, null, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, null);
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2) {
        this(view, num, transitionCookie, componentName, num2, false, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, null);
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
        this(view, num, transitionCookie, componentName, num2, z, null, null, 192, null);
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z, InteractionJankMonitor interactionJankMonitor) {
        this(view, num, transitionCookie, componentName, num2, z, interactionJankMonitor, null, 128, null);
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.animation.GhostedViewTransitionAnimatorController$detachListener$1] */
    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z, InteractionJankMonitor interactionJankMonitor, IViewTransitionRegistry iViewTransitionRegistry) {
        Drawable drawable;
        this.launchCujType = num;
        this.transitionCookie = transitionCookie;
        this.component = componentName;
        this.returnCujType = num2;
        this.isEphemeral = z;
        this.interactionJankMonitor = interactionJankMonitor;
        this.isLaunching = true;
        this.transitionContainerLocation = new int[2];
        float[] fArr = new float[9];
        for (int i = 0; i < 9; i++) {
            fArr[i] = 0.0f;
        }
        this.initialGhostViewMatrixValues = fArr;
        this.ghostViewMatrix = new Matrix();
        this.backgroundInsets$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.GhostedViewTransitionAnimatorController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Insets opticalInsets;
                Drawable drawable2 = GhostedViewTransitionAnimatorController.this.background;
                return (drawable2 == null || (opticalInsets = drawable2.getOpticalInsets()) == null) ? Insets.NONE : opticalInsets;
            }
        });
        this.startBackgroundAlpha = 255;
        this.ghostedViewLocation = new int[2];
        this.ghostedViewState = new TransitionAnimator.State(0, 0, 0, 0, 0.0f, 0.0f, 63, null);
        this.detachListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.animation.GhostedViewTransitionAnimatorController$detachListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                GhostedViewTransitionAnimatorController.this.onDispose();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
            }
        };
        this._ghostedView = view;
        if (view instanceof LaunchableView) {
            view.getClass();
            if (view.getBackground() != null) {
                drawable = view.getBackground();
            } else {
                LinkedList linkedList = new LinkedList();
                linkedList.add(view);
                while (true) {
                    if (linkedList.isEmpty()) {
                        drawable = null;
                        break;
                    }
                    View view2 = (View) linkedList.remove(0);
                    if (view2.getBackground() != null) {
                        drawable = view2.getBackground();
                        break;
                    } else if (view2 instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) view2;
                        int childCount = viewGroup.getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
                            linkedList.add(viewGroup.getChildAt(i2));
                        }
                    }
                }
            }
            this.background = drawable;
            TransitionAnimator.Companion.getClass();
            if (this.isEphemeral) {
                View view3 = this._ghostedView;
                view3.getClass();
                view3.addOnAttachStateChangeListener(this.detachListener);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("A GhostedViewLaunchAnimatorController was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
    }

    public GhostedViewTransitionAnimatorController(View view, Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z, InteractionJankMonitor interactionJankMonitor, IViewTransitionRegistry iViewTransitionRegistry, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : transitionCookie, (i & 8) != 0 ? null : componentName, (i & 16) != 0 ? null : num2, (i & 32) != 0 ? false : z, (i & 64) != 0 ? InteractionJankMonitor.getInstance() : interactionJankMonitor, (i & 128) != 0 ? null : iViewTransitionRegistry);
    }
}
