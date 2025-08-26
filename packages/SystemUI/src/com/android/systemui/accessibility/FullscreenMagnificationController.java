package com.android.systemui.accessibility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Log;
import android.util.Property;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.leak.RotationUtils;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FullscreenMagnificationController implements ComponentCallbacks {
    public static final boolean DEBUG = Log.isLoggable("FullscreenMagController", 3);
    public static final Region sEmptyRegion = new Region();
    public final AccessibilityManager mAccessibilityManager;
    public int mActivationState;
    public int mBorderOffset;
    public int mBorderStoke;
    public SurfaceControl mBorderSurfaceControl;
    public final Configuration mConfiguration;
    public final Context mContext;
    public String mCurrentDisplayUniqueId;
    public final int mDisplayId;
    public final AnonymousClass2 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final Executor mExecutor;
    public View mFullscreenBorder;
    public final Handler mHandler;
    public final FullscreenMagnificationController$$ExternalSyntheticLambda1 mHideBorderImmediatelyRunnable;
    public final IWindowManager mIWindowManager;
    public final long mLongAnimationTimeMs;
    public int mRotation;
    public final AnonymousClass1 mRotationWatcher;
    public final Supplier mScvhSupplier;
    public final FullscreenMagnificationController$$ExternalSyntheticLambda1 mShowBorderRunnable;
    ValueAnimator mShowHideBorderAnimator;
    public SurfaceControlViewHost mSurfaceControlViewHost;
    public final SurfaceControl.Transaction mTransaction;
    public final Rect mWindowBounds;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.systemui.accessibility.FullscreenMagnificationController$3, reason: invalid class name */
    public class AnonymousClass3 extends AnimatorListenerAdapter {
        public AnonymousClass3() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            FullscreenMagnificationController.this.mHandler.post(new FullscreenMagnificationController$3$$ExternalSyntheticLambda0(this));
        }
    }

    /* renamed from: com.android.systemui.accessibility.FullscreenMagnificationController$4, reason: invalid class name */
    public class AnonymousClass4 extends AnimatorListenerAdapter {
        public AnonymousClass4() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            FullscreenMagnificationController.this.mHandler.post(new FullscreenMagnificationController$3$$ExternalSyntheticLambda0(this));
        }
    }

    public FullscreenMagnificationController(Context context, Handler handler, Executor executor, DisplayManager displayManager, AccessibilityManager accessibilityManager, WindowManager windowManager, IWindowManager iWindowManager, Supplier<SurfaceControlViewHost> supplier) {
        this(context, handler, executor, displayManager, accessibilityManager, windowManager, iWindowManager, supplier, new SurfaceControl.Transaction());
    }

    public void applyCornerRadiusToBorder() {
        View view = this.mFullscreenBorder;
        if (view == null || !(view.getBackground() instanceof GradientDrawable)) {
            return;
        }
        float windowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(this.mContext);
        GradientDrawable gradientDrawable = (GradientDrawable) this.mFullscreenBorder.getBackground();
        gradientDrawable.setStroke(this.mBorderStoke, this.mContext.getResources().getColor(R.color.magnification_border_color, this.mContext.getTheme()));
        gradientDrawable.setCornerRadius(windowCornerRadius);
    }

    public void cleanUpBorder() {
        this.mContext.unregisterComponentCallbacks(this);
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        SurfaceControlViewHost surfaceControlViewHost = this.mSurfaceControlViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            this.mSurfaceControlViewHost = null;
        }
        if (this.mFullscreenBorder != null) {
            if (this.mHandler.hasCallbacks(this.mHideBorderImmediatelyRunnable)) {
                this.mHandler.removeCallbacks(this.mHideBorderImmediatelyRunnable);
            }
            if (this.mHandler.hasCallbacks(this.mShowBorderRunnable)) {
                this.mHandler.removeCallbacks(this.mShowBorderRunnable);
            }
            this.mFullscreenBorder = null;
            try {
                this.mIWindowManager.removeRotationWatcher(this.mRotationWatcher);
            } catch (Exception e) {
                Log.w("FullscreenMagController", "Failed to remove rotation watcher", e);
            }
        }
        setState(0);
    }

    public ValueAnimator createHideTargetAnimator(View view) {
        ValueAnimator valueAnimator = this.mShowHideBorderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfFloat.setDuration(this.mLongAnimationTimeMs);
        objectAnimatorOfFloat.addListener(new AnonymousClass4());
        return objectAnimatorOfFloat;
    }

    public ValueAnimator createShowTargetAnimator(View view) {
        ValueAnimator valueAnimator = this.mShowHideBorderAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
        objectAnimatorOfFloat.setDuration(this.mLongAnimationTimeMs);
        objectAnimatorOfFloat.addListener(new AnonymousClass3());
        return objectAnimatorOfFloat;
    }

    public int getState() {
        return this.mActivationState;
    }

    public final void handleScreenRotation() {
        if (this.mFullscreenBorder != null) {
            if (this.mHandler.hasCallbacks(this.mShowBorderRunnable)) {
                this.mHandler.removeCallbacks(this.mShowBorderRunnable);
            }
            this.mHandler.postAtFrontOfQueue(this.mHideBorderImmediatelyRunnable);
            this.mHandler.postDelayed(this.mShowBorderRunnable, this.mLongAnimationTimeMs);
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        int iDiff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(iDiff);
    }

    public final void setState(int i) {
        if (DEBUG) {
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("setState from "), this.mActivationState, " to ", i, "FullscreenMagController");
        }
        this.mActivationState = i;
    }

    public final void updateDimensions() {
        this.mBorderOffset = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen_with_offset) - this.mContext.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen);
        this.mBorderStoke = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen_with_offset);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.accessibility.FullscreenMagnificationController$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.accessibility.FullscreenMagnificationController$2] */
    public FullscreenMagnificationController(Context context, Handler handler, Executor executor, DisplayManager displayManager, AccessibilityManager accessibilityManager, WindowManager windowManager, IWindowManager iWindowManager, Supplier<SurfaceControlViewHost> supplier, SurfaceControl.Transaction transaction) {
        this.mSurfaceControlViewHost = null;
        this.mBorderSurfaceControl = null;
        this.mFullscreenBorder = null;
        this.mHideBorderImmediatelyRunnable = new FullscreenMagnificationController$$ExternalSyntheticLambda1(this, 0);
        this.mShowBorderRunnable = new FullscreenMagnificationController$$ExternalSyntheticLambda1(this, 1);
        this.mRotationWatcher = new IRotationWatcher.Stub() { // from class: com.android.systemui.accessibility.FullscreenMagnificationController.1
            public final void onRotationChanged(int i) {
                FullscreenMagnificationController fullscreenMagnificationController = FullscreenMagnificationController.this;
                boolean z = FullscreenMagnificationController.DEBUG;
                fullscreenMagnificationController.handleScreenRotation();
            }
        };
        this.mActivationState = 0;
        this.mContext = context;
        this.mHandler = handler;
        this.mExecutor = executor;
        this.mAccessibilityManager = accessibilityManager;
        this.mWindowManager = windowManager;
        this.mIWindowManager = iWindowManager;
        this.mWindowBounds = windowManager.getCurrentWindowMetrics().getBounds();
        this.mTransaction = transaction;
        this.mScvhSupplier = supplier;
        updateDimensions();
        this.mDisplayId = context.getDisplayId();
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mLongAnimationTimeMs = context.getResources().getInteger(android.R.integer.config_longAnimTime);
        this.mCurrentDisplayUniqueId = context.getDisplayNoVerify().getUniqueId();
        this.mDisplayManager = displayManager;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.accessibility.FullscreenMagnificationController.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                String uniqueId = FullscreenMagnificationController.this.mContext.getDisplayNoVerify().getUniqueId();
                if (uniqueId.equals(FullscreenMagnificationController.this.mCurrentDisplayUniqueId)) {
                    return;
                }
                FullscreenMagnificationController fullscreenMagnificationController = FullscreenMagnificationController.this;
                fullscreenMagnificationController.mCurrentDisplayUniqueId = uniqueId;
                fullscreenMagnificationController.mHandler.post(new FullscreenMagnificationController$$ExternalSyntheticLambda1(fullscreenMagnificationController, 2));
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
    }

    public void onConfigurationChanged(int i) {
        boolean z;
        if ((i & 4096) == 0 && (i & 1024) == 0 && (i & 128) == 0) {
            z = false;
        } else {
            updateDimensions();
            this.mWindowBounds.set(this.mWindowManager.getCurrentWindowMetrics().getBounds());
            z = true;
        }
        if (this.mFullscreenBorder == null) {
            return;
        }
        if (z) {
            this.mSurfaceControlViewHost.relayout((this.mBorderOffset * 2) + this.mWindowBounds.width(), (this.mBorderOffset * 2) + this.mWindowBounds.height());
            SurfaceControl.Transaction transaction = this.mTransaction;
            SurfaceControl surfaceControl = this.mBorderSurfaceControl;
            int i2 = this.mBorderOffset;
            transaction.setPosition(surfaceControl, -i2, -i2).apply();
        }
        int rotation = RotationUtils.getRotation(this.mContext);
        if (rotation != this.mRotation) {
            this.mRotation = rotation;
            handleScreenRotation();
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }
}
