package com.google.android.material.snackbar;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.SnackbarManager;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BaseTransientBottomBar {
    public static int TRANSITION_HEIGHT;
    public final AccessibilityManager accessibilityManager;
    public final int animationFadeInDuration;
    public final TimeInterpolator animationFadeInterpolator;
    public final int animationFadeOutDuration;
    public final TimeInterpolator animationScaleInterpolator;
    public final int animationSlideDuration;
    public final TimeInterpolator animationSlideInterpolator;
    public int appliedBottomMarginGestureInset;
    public final AnonymousClass2 bottomMarginGestureInsetRunnable;
    public List callbacks;
    public final ContentViewCallback contentViewCallback;
    public final Context context;
    public int duration;
    public int extraBottomMarginGestureInset;
    public int extraBottomMarginWindowInset;
    public int extraLeftMarginWindowInset;
    public int extraRightMarginWindowInset;
    public final AnonymousClass5 managerCallback;
    public boolean pendingShowingView;
    public final ViewGroup targetParent;
    public final SnackbarBaseLayout view;
    public static final FastOutSlowInInterpolator DEFAULT_ANIMATION_SLIDE_INTERPOLATOR = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    public static final TimeInterpolator DEFAULT_ANIMATION_FADE_INTERPOLATOR = AnimationUtils.LINEAR_INTERPOLATOR;
    public static final LinearOutSlowInInterpolator DEFAULT_ANIMATION_SCALE_INTERPOLATOR = AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
    public static final int[] SNACKBAR_STYLE_ATTR = {R.attr.snackbarStyle};
    public static final String TAG = "BaseTransientBottomBar";
    public static final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            AccessibilityManager accessibilityManager;
            List<AccessibilityServiceInfo> enabledAccessibilityServiceList;
            int i = message.what;
            if (i == 0) {
                BaseTransientBottomBar baseTransientBottomBar = (BaseTransientBottomBar) message.obj;
                SnackbarBaseLayout snackbarBaseLayout = baseTransientBottomBar.view;
                if (snackbarBaseLayout.getParent() == null) {
                    ViewGroup.LayoutParams layoutParams = snackbarBaseLayout.getLayoutParams();
                    if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) layoutParams;
                        Behavior behavior = new Behavior();
                        BehaviorDelegate behaviorDelegate = behavior.delegate;
                        behaviorDelegate.getClass();
                        behaviorDelegate.managerCallback = baseTransientBottomBar.managerCallback;
                        behavior.listener = baseTransientBottomBar.new AnonymousClass7();
                        layoutParams2.setBehavior(behavior);
                        layoutParams2.insetEdge = 80;
                    }
                    ViewGroup viewGroup = baseTransientBottomBar.targetParent;
                    snackbarBaseLayout.addingToTargetParent = true;
                    viewGroup.addView(snackbarBaseLayout);
                    snackbarBaseLayout.addingToTargetParent = false;
                    baseTransientBottomBar.updateMargins();
                    snackbarBaseLayout.setVisibility(4);
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (snackbarBaseLayout.isLaidOut()) {
                    baseTransientBottomBar.showViewImpl();
                    return true;
                }
                baseTransientBottomBar.pendingShowingView = true;
                return true;
            }
            if (i != 1) {
                return false;
            }
            final BaseTransientBottomBar baseTransientBottomBar2 = (BaseTransientBottomBar) message.obj;
            final int i2 = message.arg1;
            if (baseTransientBottomBar2.view.animationMode == 2 || (accessibilityManager = baseTransientBottomBar2.accessibilityManager) == null || ((enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(1)) != null && enabledAccessibilityServiceList.isEmpty())) {
                SnackbarBaseLayout snackbarBaseLayout2 = baseTransientBottomBar2.view;
                if (snackbarBaseLayout2.getVisibility() == 0) {
                    int i3 = snackbarBaseLayout2.animationMode;
                    if (i3 == 1 || i3 == 2) {
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        ofFloat.setInterpolator(baseTransientBottomBar2.animationFadeInterpolator);
                        ofFloat.addUpdateListener(baseTransientBottomBar2.new AnonymousClass11());
                        ofFloat.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(baseTransientBottomBar2.context, android.R.interpolator.accelerate_quad));
                        ofFloat.setDuration(baseTransientBottomBar2.animationFadeOutDuration);
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.10
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                BaseTransientBottomBar.this.onViewHidden(i2);
                            }
                        });
                        ofFloat.start();
                        return true;
                    }
                    ValueAnimator valueAnimator = new ValueAnimator();
                    int height = snackbarBaseLayout2.getHeight();
                    ViewGroup.LayoutParams layoutParams3 = snackbarBaseLayout2.getLayoutParams();
                    if (layoutParams3 instanceof ViewGroup.MarginLayoutParams) {
                        height += ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin;
                    }
                    valueAnimator.setIntValues(0, height);
                    valueAnimator.setInterpolator(baseTransientBottomBar2.animationSlideInterpolator);
                    valueAnimator.setDuration(baseTransientBottomBar2.animationSlideDuration);
                    valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.15
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            BaseTransientBottomBar.this.onViewHidden(i2);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            BaseTransientBottomBar baseTransientBottomBar3 = BaseTransientBottomBar.this;
                            ContentViewCallback contentViewCallback = baseTransientBottomBar3.contentViewCallback;
                            int i4 = baseTransientBottomBar3.animationFadeOutDuration;
                            SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) contentViewCallback;
                            snackbarContentLayout.messageView.setAlpha(1.0f);
                            long j = i4;
                            long j2 = 0;
                            snackbarContentLayout.messageView.animate().alpha(0.0f).setDuration(j).setInterpolator(snackbarContentLayout.contentInterpolator).setStartDelay(j2).start();
                            if (snackbarContentLayout.actionView.getVisibility() == 0) {
                                snackbarContentLayout.actionView.setAlpha(1.0f);
                                snackbarContentLayout.actionView.animate().alpha(0.0f).setDuration(j).setInterpolator(snackbarContentLayout.contentInterpolator).setStartDelay(j2).start();
                            }
                        }
                    });
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.16
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                            int i4 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                            BaseTransientBottomBar.this.view.setTranslationY(intValue);
                        }
                    });
                    valueAnimator.start();
                    return true;
                }
            }
            baseTransientBottomBar2.onViewHidden(i2);
            return true;
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.snackbar.BaseTransientBottomBar$11, reason: invalid class name */
    public class AnonymousClass11 implements ValueAnimator.AnimatorUpdateListener {
        public AnonymousClass11() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            BaseTransientBottomBar.this.view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.snackbar.BaseTransientBottomBar$5, reason: invalid class name */
    public class AnonymousClass5 implements SnackbarManager.Callback {
        public AnonymousClass5() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.snackbar.BaseTransientBottomBar$7, reason: invalid class name */
    public class AnonymousClass7 implements SwipeDismissBehavior.OnDismissListener {
        public AnonymousClass7() {
        }

        public final void onDismiss(View view) {
            if (view.getParent() != null) {
                view.setVisibility(8);
            }
            BaseTransientBottomBar.this.dispatchDismiss(0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Behavior extends SwipeDismissBehavior<View> {
        public final BehaviorDelegate delegate = new BehaviorDelegate(this);

        @Override // com.google.android.material.behavior.SwipeDismissBehavior
        public final boolean canSwipeDismissView(View view) {
            this.delegate.getClass();
            return view instanceof SnackbarBaseLayout;
        }

        @Override // com.google.android.material.behavior.SwipeDismissBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            BehaviorDelegate behaviorDelegate = this.delegate;
            behaviorDelegate.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked == 1 || actionMasked == 3) {
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(behaviorDelegate.managerCallback);
                }
            } else if (coordinatorLayout.isPointInChildBounds(view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                SnackbarManager.getInstance().pauseTimeout(behaviorDelegate.managerCallback);
            }
            return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BehaviorDelegate {
        public AnonymousClass5 managerCallback;

        public BehaviorDelegate(SwipeDismissBehavior<?> swipeDismissBehavior) {
            swipeDismissBehavior.getClass();
            swipeDismissBehavior.alphaStartSwipeDistance = Math.min(Math.max(0.0f, 0.1f), 1.0f);
            swipeDismissBehavior.alphaEndSwipeDistance = Math.min(Math.max(0.0f, 0.6f), 1.0f);
            swipeDismissBehavior.swipeDirection = 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SnackbarBaseLayout extends FrameLayout {
        public static final AnonymousClass1 consumeAllTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.SnackbarBaseLayout.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                View view2;
                if (!(view instanceof ViewGroup)) {
                    return true;
                }
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                ViewGroup viewGroup = (ViewGroup) view;
                AnonymousClass1 anonymousClass1 = SnackbarBaseLayout.consumeAllTouchListener;
                int childCount = viewGroup.getChildCount() - 1;
                while (true) {
                    if (childCount < 0) {
                        view2 = null;
                        break;
                    }
                    view2 = viewGroup.getChildAt(childCount);
                    float x2 = view2.getX();
                    float y2 = view2.getY();
                    float width = view2.getWidth() + x2;
                    float height = view2.getHeight() + y2;
                    if (x >= x2 && y >= y2 && x < width && y < height) {
                        break;
                    }
                    childCount--;
                }
                return view2 != null;
            }
        };
        public final float actionTextColorAlpha;
        public boolean addingToTargetParent;
        public int animationMode;
        public ColorStateList backgroundTint;
        public PorterDuff.Mode backgroundTintMode;
        public BaseTransientBottomBar baseTransientBottomBar;
        public Rect originalMargins;
        public final ShapeAppearanceModel shapeAppearanceModel;

        public SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            WindowInsets rootWindowInsets;
            super.onAttachedToWindow();
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null && (rootWindowInsets = baseTransientBottomBar.view.getRootWindowInsets()) != null) {
                baseTransientBottomBar.extraBottomMarginGestureInset = rootWindowInsets.getMandatorySystemGestureInsets().bottom;
                baseTransientBottomBar.updateMargins();
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api20Impl.requestApplyInsets(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onDetachedFromWindow() {
            boolean z;
            super.onDetachedFromWindow();
            final BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null) {
                SnackbarManager snackbarManager = SnackbarManager.getInstance();
                AnonymousClass5 anonymousClass5 = baseTransientBottomBar.managerCallback;
                synchronized (snackbarManager.lock) {
                    z = true;
                    if (!snackbarManager.isCurrentSnackbarLocked(anonymousClass5)) {
                        SnackbarManager.SnackbarRecord snackbarRecord = snackbarManager.nextSnackbar;
                        if (!((snackbarRecord == null || anonymousClass5 == null || snackbarRecord.callback.get() != anonymousClass5) ? false : true)) {
                            z = false;
                        }
                    }
                }
                if (z) {
                    BaseTransientBottomBar.handler.post(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.6
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseTransientBottomBar.this.onViewHidden(3);
                        }
                    });
                }
            }
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar == null || !baseTransientBottomBar.pendingShowingView) {
                return;
            }
            baseTransientBottomBar.showViewImpl();
            baseTransientBottomBar.pendingShowingView = false;
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            super.onMeasure(i, i2);
        }

        @Override // android.view.View
        public final void setBackground(Drawable drawable) {
            setBackgroundDrawable(drawable);
        }

        @Override // android.view.View
        public final void setBackgroundDrawable(Drawable drawable) {
            if (drawable != null && this.backgroundTint != null) {
                drawable = drawable.mutate();
                drawable.setTintList(this.backgroundTint);
                drawable.setTintMode(this.backgroundTintMode);
            }
            super.setBackgroundDrawable(drawable);
        }

        @Override // android.view.View
        public final void setBackgroundTintList(ColorStateList colorStateList) {
            this.backgroundTint = colorStateList;
            if (getBackground() != null) {
                Drawable mutate = getBackground().mutate();
                mutate.setTintList(colorStateList);
                mutate.setTintMode(this.backgroundTintMode);
                if (mutate != getBackground()) {
                    super.setBackgroundDrawable(mutate);
                }
            }
        }

        @Override // android.view.View
        public final void setBackgroundTintMode(PorterDuff.Mode mode) {
            this.backgroundTintMode = mode;
            if (getBackground() != null) {
                Drawable mutate = getBackground().mutate();
                mutate.setTintMode(mode);
                if (mutate != getBackground()) {
                    super.setBackgroundDrawable(mutate);
                }
            }
        }

        @Override // android.view.View
        public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            super.setLayoutParams(layoutParams);
            if (this.addingToTargetParent || !(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                return;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            this.originalMargins = new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null) {
                int i = BaseTransientBottomBar.TRANSITION_HEIGHT;
                baseTransientBottomBar.updateMargins();
            }
        }

        @Override // android.view.View
        public final void setOnClickListener(View.OnClickListener onClickListener) {
            setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
            super.setOnClickListener(onClickListener);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
            super(MaterialThemeOverlay.wrap(context, attributeSet, 0, 0), attributeSet);
            GradientDrawable gradientDrawable;
            Context context2 = getContext();
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
            if (obtainStyledAttributes.hasValue(6)) {
                float dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(6, 0);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setElevation(this, dimensionPixelSize);
            }
            this.animationMode = obtainStyledAttributes.getInt(2, 1);
            if (obtainStyledAttributes.hasValue(8) || obtainStyledAttributes.hasValue(9)) {
                this.shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attributeSet, 0, 0).build();
            }
            float f = obtainStyledAttributes.getFloat(3, 1.0f);
            setBackgroundTintList(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 4));
            setBackgroundTintMode(ViewUtils.parseTintMode(obtainStyledAttributes.getInt(5, -1), PorterDuff.Mode.SRC_IN));
            this.actionTextColorAlpha = obtainStyledAttributes.getFloat(1, 1.0f);
            obtainStyledAttributes.getDimensionPixelSize(0, -1);
            obtainStyledAttributes.getDimensionPixelSize(7, -1);
            BaseTransientBottomBar.TRANSITION_HEIGHT = obtainStyledAttributes.getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_suggest_transition_height);
            obtainStyledAttributes.recycle();
            setOnTouchListener(consumeAllTouchListener);
            setFocusable(true);
            if (getBackground() == null) {
                int layer = MaterialColors.layer(f, MaterialColors.getColor(this, R.attr.colorSurface), MaterialColors.getColor(this, R.attr.colorOnSurface));
                ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
                if (shapeAppearanceModel != null) {
                    MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
                    materialShapeDrawable.setFillColor(ColorStateList.valueOf(layer));
                    gradientDrawable = materialShapeDrawable;
                } else {
                    float dimension = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
                    GradientDrawable gradientDrawable2 = new GradientDrawable();
                    gradientDrawable2.setShape(0);
                    gradientDrawable2.setCornerRadius(dimension);
                    gradientDrawable2.setColor(layer);
                    gradientDrawable = gradientDrawable2;
                }
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    gradientDrawable.setTintList(colorStateList);
                }
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                setBackgroundDrawable(gradientDrawable);
            }
        }
    }

    public BaseTransientBottomBar(ViewGroup viewGroup, View view, ContentViewCallback contentViewCallback) {
        this(viewGroup.getContext(), viewGroup, view, contentViewCallback);
    }

    public final void addCallback(BaseCallback baseCallback) {
        if (this.callbacks == null) {
            this.callbacks = new ArrayList();
        }
        ((ArrayList) this.callbacks).add(baseCallback);
    }

    public void dismiss() {
        dispatchDismiss(3);
    }

    public final void dispatchDismiss(int i) {
        SnackbarManager snackbarManager = SnackbarManager.getInstance();
        AnonymousClass5 anonymousClass5 = this.managerCallback;
        synchronized (snackbarManager.lock) {
            try {
                if (snackbarManager.isCurrentSnackbarLocked(anonymousClass5)) {
                    snackbarManager.cancelSnackbarLocked(snackbarManager.currentSnackbar, i);
                } else {
                    SnackbarManager.SnackbarRecord snackbarRecord = snackbarManager.nextSnackbar;
                    if ((snackbarRecord == null || anonymousClass5 == null || snackbarRecord.callback.get() != anonymousClass5) ? false : true) {
                        snackbarManager.cancelSnackbarLocked(snackbarManager.nextSnackbar, i);
                    }
                }
            } finally {
            }
        }
    }

    public final void onViewHidden(int i) {
        SnackbarManager snackbarManager = SnackbarManager.getInstance();
        AnonymousClass5 anonymousClass5 = this.managerCallback;
        synchronized (snackbarManager.lock) {
            try {
                if (snackbarManager.isCurrentSnackbarLocked(anonymousClass5)) {
                    snackbarManager.currentSnackbar = null;
                    if (snackbarManager.nextSnackbar != null) {
                        snackbarManager.showNextSnackbarLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        List list = this.callbacks;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((BaseCallback) ((ArrayList) this.callbacks).get(size)).onDismissed(this, i);
            }
        }
        ViewParent parent = this.view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.view);
        }
    }

    public final void onViewShown() {
        SnackbarManager snackbarManager = SnackbarManager.getInstance();
        AnonymousClass5 anonymousClass5 = this.managerCallback;
        synchronized (snackbarManager.lock) {
            try {
                if (snackbarManager.isCurrentSnackbarLocked(anonymousClass5)) {
                    snackbarManager.scheduleTimeoutLocked(snackbarManager.currentSnackbar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        List list = this.callbacks;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((BaseCallback) ((ArrayList) this.callbacks).get(size)).onShown(this);
            }
        }
    }

    public final void showViewImpl() {
        AccessibilityManager accessibilityManager;
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList;
        SnackbarBaseLayout snackbarBaseLayout = this.view;
        boolean z = true;
        if (snackbarBaseLayout.animationMode != 2 && (accessibilityManager = this.accessibilityManager) != null && ((enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(1)) == null || !enabledAccessibilityServiceList.isEmpty())) {
            z = false;
        }
        if (z) {
            snackbarBaseLayout.post(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.8
                @Override // java.lang.Runnable
                public final void run() {
                    SnackbarBaseLayout snackbarBaseLayout2 = BaseTransientBottomBar.this.view;
                    if (snackbarBaseLayout2 == null) {
                        return;
                    }
                    if (snackbarBaseLayout2.getParent() != null) {
                        BaseTransientBottomBar.this.view.setVisibility(0);
                    }
                    final BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                    SnackbarBaseLayout snackbarBaseLayout3 = baseTransientBottomBar.view;
                    int i = snackbarBaseLayout3.animationMode;
                    if (i == 1) {
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        ofFloat.setInterpolator(baseTransientBottomBar.animationFadeInterpolator);
                        ofFloat.addUpdateListener(baseTransientBottomBar.new AnonymousClass11());
                        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.2f, 1.0f);
                        ofFloat2.setInterpolator(baseTransientBottomBar.animationScaleInterpolator);
                        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.12
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                BaseTransientBottomBar.this.view.setScaleX(floatValue);
                                BaseTransientBottomBar.this.view.setScaleY(floatValue);
                            }
                        });
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloat);
                        animatorSet.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(baseTransientBottomBar.context, android.R.interpolator.decelerate_quad));
                        animatorSet.setDuration(baseTransientBottomBar.animationFadeInDuration);
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.9
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                BaseTransientBottomBar.this.onViewShown();
                            }
                        });
                        animatorSet.start();
                        return;
                    }
                    if (i == 2) {
                        final SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) snackbarBaseLayout3.findViewById(R.id.snackbar_content_layout);
                        final TextView textView = (TextView) snackbarContentLayout.findViewById(R.id.snackbar_text);
                        final Button button = (Button) snackbarContentLayout.findViewById(R.id.snackbar_action);
                        snackbarContentLayout.setAlpha(0.0f);
                        textView.setAlpha(0.0f);
                        button.setAlpha(0.0f);
                        textView.setVisibility(0);
                        button.setVisibility(0);
                        snackbarContentLayout.post(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                final BaseTransientBottomBar baseTransientBottomBar2 = BaseTransientBottomBar.this;
                                final SnackbarContentLayout snackbarContentLayout2 = snackbarContentLayout;
                                final TextView textView2 = textView;
                                final Button button2 = button;
                                int i2 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                                baseTransientBottomBar2.getClass();
                                final int measuredWidth = snackbarContentLayout2.getMeasuredWidth();
                                final int measuredHeight = snackbarContentLayout2.getMeasuredHeight();
                                boolean z2 = baseTransientBottomBar2.context.getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_suggest_background_radius) <= 22;
                                final int i3 = 44;
                                final int i4 = z2 ? 44 : 100;
                                if (!z2) {
                                    i3 = 100;
                                }
                                baseTransientBottomBar2.updateContentBackground(snackbarContentLayout2, i4, i3);
                                snackbarContentLayout2.setAlpha(1.0f);
                                BaseTransientBottomBar.SnackbarBaseLayout snackbarBaseLayout4 = baseTransientBottomBar2.view;
                                snackbarBaseLayout4.setAlpha(1.0f);
                                snackbarBaseLayout4.setTranslationY(BaseTransientBottomBar.TRANSITION_HEIGHT + measuredHeight);
                                SpringAnimation springAnimation = new SpringAnimation(snackbarBaseLayout4, DynamicAnimation.TRANSLATION_Y);
                                springAnimation.cancel();
                                SpringForce springForce = new SpringForce();
                                springForce.setStiffness(350.0f);
                                springForce.setDampingRatio(1.0f);
                                springAnimation.mSpring = springForce;
                                springAnimation.animateToFinalPosition(0.0f - BaseTransientBottomBar.TRANSITION_HEIGHT);
                                springAnimation.mVelocity = 0.1f;
                                springAnimation.start();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        final BaseTransientBottomBar baseTransientBottomBar3 = BaseTransientBottomBar.this;
                                        TextView textView3 = textView2;
                                        Button button3 = button2;
                                        final SnackbarContentLayout snackbarContentLayout3 = snackbarContentLayout2;
                                        final int i5 = i4;
                                        final int i6 = measuredWidth;
                                        final int i7 = i3;
                                        final int i8 = measuredHeight;
                                        int i9 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                                        baseTransientBottomBar3.getClass();
                                        textView3.setAlpha(0.0f);
                                        textView3.animate().alpha(1.0f).setDuration(150L).setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(baseTransientBottomBar3.context, R.interpolator.sesl_snackbar_suggestion_interpolator)).setStartDelay(150L).start();
                                        button3.setAlpha(0.0f);
                                        button3.animate().alpha(1.0f).setDuration(150L).setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(baseTransientBottomBar3.context, R.interpolator.sesl_snackbar_suggestion_interpolator)).setStartDelay(150L).start();
                                        SpringAnimation springAnimation2 = new SpringAnimation(snackbarContentLayout3, new FloatPropertyCompat("size") { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.17
                                            public float _value = 0.0f;

                                            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                                            public final float getValue(Object obj) {
                                                return this._value;
                                            }

                                            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                                            public final void setValue(Object obj, float f) {
                                                float min = Math.min(4.0f * f, 1.0f);
                                                int lerp = (int) MathUtils.lerp(i5, i6, min);
                                                int lerp2 = (int) MathUtils.lerp(i7, i8, min);
                                                int i10 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                                                BaseTransientBottomBar.this.updateContentBackground(snackbarContentLayout3, lerp, lerp2);
                                                this._value = f;
                                            }
                                        });
                                        springAnimation2.setStartValue(0.0f);
                                        SpringForce springForce2 = new SpringForce();
                                        springForce2.setStiffness(50.0f);
                                        springForce2.setDampingRatio(0.72f);
                                        springAnimation2.mSpring = springForce2;
                                        springAnimation2.animateToFinalPosition(1.0f);
                                        springAnimation2.start();
                                        SpringAnimation springAnimation3 = new SpringAnimation(baseTransientBottomBar3.view, DynamicAnimation.TRANSLATION_Y);
                                        springAnimation3.cancel();
                                        SpringForce springForce3 = new SpringForce();
                                        springForce3.setStiffness(300.0f);
                                        springForce3.setDampingRatio(0.72f);
                                        springAnimation3.mSpring = springForce3;
                                        springAnimation3.animateToFinalPosition(0.0f);
                                        springAnimation3.mVelocity = 0.1f;
                                        springAnimation3.start();
                                        springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar$$ExternalSyntheticLambda2
                                            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                                            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z3, float f, float f2) {
                                                int i10 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                                                BaseTransientBottomBar.this.onViewShown();
                                            }
                                        });
                                    }
                                }, 200L);
                            }
                        });
                        return;
                    }
                    int height = snackbarBaseLayout3.getHeight();
                    ViewGroup.LayoutParams layoutParams = snackbarBaseLayout3.getLayoutParams();
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        height += ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                    }
                    baseTransientBottomBar.view.setTranslationY(height);
                    ValueAnimator valueAnimator = new ValueAnimator();
                    valueAnimator.setIntValues(height, 0);
                    valueAnimator.setInterpolator(baseTransientBottomBar.animationSlideInterpolator);
                    valueAnimator.setDuration(baseTransientBottomBar.animationSlideDuration);
                    valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.13
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            BaseTransientBottomBar.this.onViewShown();
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            BaseTransientBottomBar baseTransientBottomBar2 = BaseTransientBottomBar.this;
                            ContentViewCallback contentViewCallback = baseTransientBottomBar2.contentViewCallback;
                            int i2 = baseTransientBottomBar2.animationSlideDuration;
                            int i3 = baseTransientBottomBar2.animationFadeInDuration;
                            SnackbarContentLayout snackbarContentLayout2 = (SnackbarContentLayout) contentViewCallback;
                            snackbarContentLayout2.messageView.setAlpha(0.0f);
                            long j = i3;
                            long j2 = i2 - i3;
                            snackbarContentLayout2.messageView.animate().alpha(1.0f).setDuration(j).setInterpolator(snackbarContentLayout2.contentInterpolator).setStartDelay(j2).start();
                            if (snackbarContentLayout2.actionView.getVisibility() == 0) {
                                snackbarContentLayout2.actionView.setAlpha(0.0f);
                                snackbarContentLayout2.actionView.animate().alpha(1.0f).setDuration(j).setInterpolator(snackbarContentLayout2.contentInterpolator).setStartDelay(j2).start();
                            }
                        }
                    });
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(height) { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.14
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                            int i2 = BaseTransientBottomBar.TRANSITION_HEIGHT;
                            BaseTransientBottomBar.this.view.setTranslationY(intValue);
                        }
                    });
                    valueAnimator.start();
                }
            });
            return;
        }
        if (snackbarBaseLayout.getParent() != null) {
            snackbarBaseLayout.setVisibility(0);
        }
        onViewShown();
    }

    public final void updateContentBackground(View view, int i, int i2) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        float min = Math.min(i, i2) / 2.0f;
        float dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_suggest_background_radius);
        if (min > dimensionPixelSize) {
            min = dimensionPixelSize;
        }
        gradientDrawable.setCornerRadius(min);
        Rect bounds = gradientDrawable.getBounds();
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();
        int i3 = i / 2;
        int i4 = i2 / 2;
        gradientDrawable.setBounds(centerX - i3, centerY - i4, centerX + i3, centerY + i4);
        gradientDrawable.invalidateSelf();
    }

    public final void updateMargins() {
        SnackbarBaseLayout snackbarBaseLayout = this.view;
        ViewGroup.LayoutParams layoutParams = snackbarBaseLayout.getLayoutParams();
        boolean z = layoutParams instanceof ViewGroup.MarginLayoutParams;
        String str = TAG;
        if (!z) {
            Log.w(str, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }
        if (snackbarBaseLayout.originalMargins == null) {
            Log.w(str, "Unable to update margins because original view margins are not set");
            return;
        }
        if (snackbarBaseLayout.getParent() == null) {
            return;
        }
        int i = this.extraBottomMarginWindowInset;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        Rect rect = snackbarBaseLayout.originalMargins;
        int i2 = rect.bottom + i;
        int i3 = rect.left + this.extraLeftMarginWindowInset;
        int i4 = rect.right + this.extraRightMarginWindowInset;
        int i5 = rect.top;
        boolean z2 = (marginLayoutParams.bottomMargin == i2 && marginLayoutParams.leftMargin == i3 && marginLayoutParams.rightMargin == i4 && marginLayoutParams.topMargin == i5) ? false : true;
        if (z2) {
            marginLayoutParams.bottomMargin = i2;
            marginLayoutParams.leftMargin = i3;
            marginLayoutParams.rightMargin = i4;
            marginLayoutParams.topMargin = i5;
            snackbarBaseLayout.requestLayout();
        }
        snackbarBaseLayout.requestLayout();
        if ((z2 || this.appliedBottomMarginGestureInset != this.extraBottomMarginGestureInset) && this.extraBottomMarginGestureInset > 0) {
            ViewGroup.LayoutParams layoutParams2 = snackbarBaseLayout.getLayoutParams();
            if ((layoutParams2 instanceof CoordinatorLayout.LayoutParams) && (((CoordinatorLayout.LayoutParams) layoutParams2).mBehavior instanceof SwipeDismissBehavior)) {
                AnonymousClass2 anonymousClass2 = this.bottomMarginGestureInsetRunnable;
                snackbarBaseLayout.removeCallbacks(anonymousClass2);
                snackbarBaseLayout.post(anonymousClass2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.snackbar.BaseTransientBottomBar$2] */
    public BaseTransientBottomBar(Context context, ViewGroup viewGroup, View view, ContentViewCallback contentViewCallback) {
        this.bottomMarginGestureInsetRunnable = new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.2
            @Override // java.lang.Runnable
            public final void run() {
                Context context2;
                BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                if (baseTransientBottomBar.view == null || (context2 = baseTransientBottomBar.context) == null) {
                    return;
                }
                int height = ((WindowManager) context2.getSystemService("window")).getCurrentWindowMetrics().getBounds().height();
                int[] iArr = new int[2];
                SnackbarBaseLayout snackbarBaseLayout = BaseTransientBottomBar.this.view;
                snackbarBaseLayout.getLocationInWindow(iArr);
                int height2 = (height - (snackbarBaseLayout.getHeight() + iArr[1])) + ((int) BaseTransientBottomBar.this.view.getTranslationY());
                BaseTransientBottomBar baseTransientBottomBar2 = BaseTransientBottomBar.this;
                int i = baseTransientBottomBar2.extraBottomMarginGestureInset;
                if (height2 >= i) {
                    baseTransientBottomBar2.appliedBottomMarginGestureInset = i;
                    return;
                }
                ViewGroup.LayoutParams layoutParams = baseTransientBottomBar2.view.getLayoutParams();
                if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                    Log.w(BaseTransientBottomBar.TAG, "Unable to apply gesture inset because layout params are not MarginLayoutParams");
                    return;
                }
                BaseTransientBottomBar baseTransientBottomBar3 = BaseTransientBottomBar.this;
                int i2 = baseTransientBottomBar3.extraBottomMarginGestureInset;
                baseTransientBottomBar3.appliedBottomMarginGestureInset = i2;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.bottomMargin = (i2 - height2) + marginLayoutParams.bottomMargin;
                baseTransientBottomBar3.view.requestLayout();
            }
        };
        this.managerCallback = new AnonymousClass5();
        if (viewGroup == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        }
        if (view == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        }
        if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
        this.targetParent = viewGroup;
        this.contentViewCallback = contentViewCallback;
        this.context = context;
        ThemeEnforcement.checkTheme(context, ThemeEnforcement.APPCOMPAT_CHECK_ATTRS, "Theme.AppCompat");
        LayoutInflater from = LayoutInflater.from(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        SnackbarBaseLayout snackbarBaseLayout = (SnackbarBaseLayout) from.inflate(resourceId != -1 ? R.layout.mtrl_layout_snackbar : R.layout.design_layout_snackbar, viewGroup, false);
        this.view = snackbarBaseLayout;
        snackbarBaseLayout.baseTransientBottomBar = this;
        if (view instanceof SnackbarContentLayout) {
            SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) view;
            float f = snackbarBaseLayout.actionTextColorAlpha;
            if (f != 1.0f) {
                snackbarContentLayout.actionView.setTextColor(MaterialColors.layer(f, MaterialColors.getColor(snackbarContentLayout, R.attr.colorSurface), snackbarContentLayout.actionView.getCurrentTextColor()));
            }
        }
        snackbarBaseLayout.addView(view);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        snackbarBaseLayout.setAccessibilityLiveRegion(1);
        snackbarBaseLayout.setImportantForAccessibility(1);
        snackbarBaseLayout.setFitsSystemWindows(true);
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(snackbarBaseLayout, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view2) {
                int systemWindowInsetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                baseTransientBottomBar.extraBottomMarginWindowInset = systemWindowInsetBottom;
                baseTransientBottomBar.extraLeftMarginWindowInset = windowInsetsCompat.getSystemWindowInsetLeft();
                baseTransientBottomBar.extraRightMarginWindowInset = windowInsetsCompat.getSystemWindowInsetRight();
                baseTransientBottomBar.updateMargins();
                return windowInsetsCompat;
            }
        });
        ViewCompat.setAccessibilityDelegate(snackbarBaseLayout, new AccessibilityDelegateCompat() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.4
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(1048576);
                accessibilityNodeInfoCompat.mInfo.setDismissable(true);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                if (i != 1048576) {
                    return super.performAccessibilityAction(view2, i, bundle);
                }
                BaseTransientBottomBar.this.dismiss();
                return true;
            }
        });
        this.accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.animationSlideDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
        this.animationFadeInDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2, 180);
        this.animationFadeOutDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationMedium1, 180);
        this.animationFadeInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, DEFAULT_ANIMATION_FADE_INTERPOLATOR);
        this.animationScaleInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, DEFAULT_ANIMATION_SCALE_INTERPOLATOR);
        this.animationSlideInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, DEFAULT_ANIMATION_SLIDE_INTERPOLATOR);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class BaseCallback {
        public void onShown(BaseTransientBottomBar baseTransientBottomBar) {
        }

        public void onDismissed(BaseTransientBottomBar baseTransientBottomBar, int i) {
        }
    }
}
