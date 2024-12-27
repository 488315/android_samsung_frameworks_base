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
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.systemui.R;
import com.android.systemui.util.leak.RotationUtils;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class FullscreenMagnificationController implements ComponentCallbacks {
    public static final Region sEmptyRegion = new Region();
    public final AccessibilityManager mAccessibilityManager;
    public int mBorderOffset;
    public SurfaceControl mBorderSurfaceControl;
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public final Executor mExecutor;
    public View mFullscreenBorder;
    public boolean mFullscreenMagnificationActivated;
    public final Handler mHandler;
    public final IWindowManager mIWindowManager;
    public final long mLongAnimationTimeMs;
    public int mRotation;
    public final AnonymousClass1 mRotationWatcher;
    public final Supplier mScvhSupplier;
    public final FullscreenMagnificationController$$ExternalSyntheticLambda0 mShowBorderRunnable;
    public final ValueAnimator mShowHideBorderAnimator;
    public SurfaceControlViewHost mSurfaceControlViewHost;
    public final SurfaceControl.Transaction mTransaction;
    public final Rect mWindowBounds;
    public final WindowManager mWindowManager;

    public FullscreenMagnificationController(Context context, Handler handler, Executor executor, AccessibilityManager accessibilityManager, WindowManager windowManager, IWindowManager iWindowManager, Supplier<SurfaceControlViewHost> supplier) {
        this(context, handler, executor, accessibilityManager, windowManager, iWindowManager, supplier, new SurfaceControl.Transaction(), null);
    }

    public final void handleScreenRotation() {
        if (this.mFullscreenBorder != null) {
            if (this.mHandler.hasCallbacks(this.mShowBorderRunnable)) {
                this.mHandler.removeCallbacks(this.mShowBorderRunnable);
            }
            this.mHandler.postAtFrontOfQueue(new FullscreenMagnificationController$$ExternalSyntheticLambda0(this, 0));
            this.mHandler.postDelayed(this.mShowBorderRunnable, this.mLongAnimationTimeMs);
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(diff);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.accessibility.FullscreenMagnificationController$1] */
    public FullscreenMagnificationController(Context context, Handler handler, Executor executor, AccessibilityManager accessibilityManager, WindowManager windowManager, IWindowManager iWindowManager, Supplier<SurfaceControlViewHost> supplier, SurfaceControl.Transaction transaction, ValueAnimator valueAnimator) {
        this.mSurfaceControlViewHost = null;
        this.mBorderSurfaceControl = null;
        this.mFullscreenBorder = null;
        this.mFullscreenMagnificationActivated = false;
        this.mShowBorderRunnable = new FullscreenMagnificationController$$ExternalSyntheticLambda0(this, 1);
        this.mRotationWatcher = new IRotationWatcher.Stub() { // from class: com.android.systemui.accessibility.FullscreenMagnificationController.1
            public final void onRotationChanged(int i) {
                FullscreenMagnificationController fullscreenMagnificationController = FullscreenMagnificationController.this;
                Region region = FullscreenMagnificationController.sEmptyRegion;
                fullscreenMagnificationController.handleScreenRotation();
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mExecutor = executor;
        this.mAccessibilityManager = accessibilityManager;
        this.mWindowManager = windowManager;
        this.mIWindowManager = iWindowManager;
        this.mWindowBounds = windowManager.getCurrentWindowMetrics().getBounds();
        this.mTransaction = transaction;
        this.mScvhSupplier = supplier;
        this.mBorderOffset = context.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen_with_offset) - context.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen);
        this.mDisplayId = context.getDisplayId();
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        long integer = context.getResources().getInteger(android.R.integer.config_longAnimTime);
        this.mLongAnimationTimeMs = integer;
        if (valueAnimator == null) {
            valueAnimator = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) View.ALPHA, 0.0f, 1.0f);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(integer);
        }
        this.mShowHideBorderAnimator = valueAnimator;
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.accessibility.FullscreenMagnificationController.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z) {
                if (z) {
                    FullscreenMagnificationController fullscreenMagnificationController = FullscreenMagnificationController.this;
                    SurfaceControlViewHost surfaceControlViewHost = fullscreenMagnificationController.mSurfaceControlViewHost;
                    if (surfaceControlViewHost != null) {
                        surfaceControlViewHost.release();
                        fullscreenMagnificationController.mSurfaceControlViewHost = null;
                    }
                    if (fullscreenMagnificationController.mFullscreenBorder != null) {
                        fullscreenMagnificationController.mFullscreenBorder = null;
                        try {
                            fullscreenMagnificationController.mIWindowManager.removeRotationWatcher(fullscreenMagnificationController.mRotationWatcher);
                        } catch (Exception e) {
                            Log.w("FullscreenMagnificationController", "Failed to remove rotation watcher", e);
                        }
                    }
                }
            }
        });
    }

    public void onConfigurationChanged(int i) {
        boolean z;
        if ((i & 4096) == 0 && (i & 1024) == 0 && (i & 128) == 0) {
            z = false;
        } else {
            this.mBorderOffset = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen_with_offset) - this.mContext.getResources().getDimensionPixelSize(R.dimen.magnifier_border_width_fullscreen);
            this.mWindowBounds.set(this.mWindowManager.getCurrentWindowMetrics().getBounds());
            z = true;
        }
        if (this.mFullscreenBorder == null) {
            return;
        }
        if (z) {
            this.mSurfaceControlViewHost.relayout((this.mBorderOffset * 2) + this.mWindowBounds.width(), (this.mBorderOffset * 2) + this.mWindowBounds.height());
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
