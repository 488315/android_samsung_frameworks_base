package com.android.server.accessibility.magnification;

import android.R;
import android.accessibilityservice.MagnificationConfig;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerInternal;
import android.os.Binder;
import android.os.Handler;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.MagnificationSpec;
import android.view.WindowManager;
import android.view.accessibility.MagnificationAnimationCallback;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.wm.WindowManagerInternal;
import com.android.window.flags.Flags;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FullScreenMagnificationController
        implements WindowManagerInternal.AccessibilityControllerInternal
                .UiChangesForAccessibilityCallbacks {
    public static boolean SEC_DEBUG;
    public boolean mAlwaysOnMagnificationEnabled;
    public final ControllerContext mControllerCtx;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public final SparseArray mDisplays;
    public final Object mLock;
    public final Supplier mMagnificationConnectionStateSupplier;
    public boolean mMagnificationFollowTypingEnabled;
    public final ArrayList mMagnificationInfoChangedCallbacks;
    public final MagnificationThumbnailFeatureFlag mMagnificationThumbnailFeatureFlag;
    public final long mMainThreadId;
    public final MagnificationScaleProvider mScaleProvider;
    public final ScreenStateObserver mScreenStateObserver;
    public final Supplier mScrollerSupplier;
    public final Rect mTempRect;
    public final Supplier mThumbnailSupplier;
    public final Supplier mTimeAnimatorSupplier;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ControllerContext {
        public final Long mAnimationDuration;
        public final Context mContext;
        public final Handler mHandler;
        public final AccessibilityTraceManager mTrace;
        public final WindowManagerInternal mWindowManager;

        public ControllerContext(
                Context context,
                AccessibilityTraceManager accessibilityTraceManager,
                WindowManagerInternal windowManagerInternal,
                Handler handler,
                long j) {
            this.mContext = context;
            this.mTrace = accessibilityTraceManager;
            this.mWindowManager = windowManagerInternal;
            this.mHandler = handler;
            this.mAnimationDuration = Long.valueOf(j);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayMagnification
            implements WindowManagerInternal.MagnificationCallbacks {
        public boolean mDeleteAfterUnregister;
        public final int mDisplayId;
        public MagnificationThumbnail mMagnificationThumbnail;
        public boolean mRegistered;
        public final SpecAnimationBridge mSpecAnimationBridge;
        public boolean mUnregisterPending;
        public final MagnificationSpec mCurrentMagnificationSpec = new MagnificationSpec();
        public final Region mMagnificationRegion = Region.obtain();
        public final Rect mMagnificationBounds = new Rect();
        public final Rect mTempRect = new Rect();
        public final Rect mTempRect1 = new Rect();
        public int mIdOfLastServiceToMagnify = -1;
        public boolean mMagnificationActivated = false;
        public boolean mZoomedOutFromService = false;

        public DisplayMagnification(int i) {
            this.mDisplayId = i;
            this.mSpecAnimationBridge =
                    new SpecAnimationBridge(
                            FullScreenMagnificationController.this.mControllerCtx,
                            FullScreenMagnificationController.this.mLock,
                            i);
        }

        public final float getCenterX() {
            float width =
                    (this.mMagnificationBounds.width() / 2.0f) + this.mMagnificationBounds.left;
            MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            return (width - magnificationSpec.offsetX) / magnificationSpec.scale;
        }

        public final float getCenterY() {
            float height =
                    (this.mMagnificationBounds.height() / 2.0f) + this.mMagnificationBounds.top;
            MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            return (height - magnificationSpec.offsetY) / magnificationSpec.scale;
        }

        public final float getMaxOffsetXLocked() {
            int i = this.mMagnificationBounds.left;
            return i - (i * this.mCurrentMagnificationSpec.scale);
        }

        public final float getMaxOffsetYLocked() {
            int i = this.mMagnificationBounds.top;
            return i - (i * this.mCurrentMagnificationSpec.scale);
        }

        public final float getMinOffsetXLocked() {
            float width = this.mMagnificationBounds.left + this.mMagnificationBounds.width();
            return width - (this.mCurrentMagnificationSpec.scale * width);
        }

        public final float getMinOffsetYLocked() {
            float height = this.mMagnificationBounds.top + this.mMagnificationBounds.height();
            return height - (this.mCurrentMagnificationSpec.scale * height);
        }

        public final void offsetMagnifiedRegion(int i, float f, float f2) {
            if (this.mRegistered) {
                MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
                if (updateCurrentSpecWithOffsetsLocked(
                        magnificationSpec.offsetX - f, magnificationSpec.offsetY - f2)) {
                    onMagnificationChangedLocked();
                }
                if (i != -1) {
                    this.mIdOfLastServiceToMagnify = i;
                }
                sendSpecToAnimation(this.mCurrentMagnificationSpec, null);
            }
        }

        public final void onMagnificationChangedLocked() {
            final float f = this.mCurrentMagnificationSpec.scale;
            final float centerX = getCenterX();
            final float centerY = getCenterY();
            final MagnificationConfig build =
                    new MagnificationConfig.Builder()
                            .setMode(1)
                            .setActivated(this.mMagnificationActivated)
                            .setScale(f)
                            .setCenterX(centerX)
                            .setCenterY(centerY)
                            .build();
            FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FullScreenMagnificationController.DisplayMagnification
                                    displayMagnification =
                                            FullScreenMagnificationController.DisplayMagnification
                                                    .this;
                            ((FullScreenMagnificationController.MagnificationInfoChangedCallback)
                                            obj)
                                    .onFullScreenMagnificationChanged(
                                            displayMagnification.mDisplayId,
                                            displayMagnification.mMagnificationRegion,
                                            build);
                        }
                    });
            if (this.mUnregisterPending && !this.mMagnificationActivated) {
                unregister(this.mDeleteAfterUnregister);
            }
            if (this.mMagnificationActivated) {
                final MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
                if (magnificationThumbnail != null) {
                    magnificationThumbnail.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MagnificationThumbnail.this.updateThumbnailMainThread(
                                            f, centerX, centerY);
                                }
                            });
                    return;
                }
                return;
            }
            MagnificationThumbnail magnificationThumbnail2 = this.mMagnificationThumbnail;
            if (magnificationThumbnail2 != null) {
                magnificationThumbnail2.mHandler.post(
                        new MagnificationThumbnail$$ExternalSyntheticLambda0(
                                magnificationThumbnail2, 0));
            }
        }

        public final void onRectangleOnScreenRequested(int i, int i2, int i3, int i4) {
            FullScreenMagnificationController.this.mControllerCtx.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda4(),
                            this,
                            Integer.valueOf(i),
                            Integer.valueOf(i2),
                            Integer.valueOf(i3),
                            Integer.valueOf(i4)));
        }

        public final void onUserContextChanged() {
            FullScreenMagnificationController.this.mControllerCtx.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new FullScreenMagnificationController$$ExternalSyntheticLambda4(1),
                            FullScreenMagnificationController.this,
                            Integer.valueOf(this.mDisplayId)));
            synchronized (FullScreenMagnificationController.this.mLock) {
                refreshThumbnail();
            }
        }

        public final void refreshThumbnail() {
            final MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
            if (magnificationThumbnail != null) {
                final Rect rect = this.mMagnificationBounds;
                final float f = this.mCurrentMagnificationSpec.scale;
                final float centerX = getCenterX();
                final float centerY = getCenterY();
                magnificationThumbnail.getClass();
                magnificationThumbnail.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                MagnificationThumbnail magnificationThumbnail2 =
                                        MagnificationThumbnail.this;
                                Rect rect2 = rect;
                                float f2 = f;
                                float f3 = centerX;
                                float f4 = centerY;
                                magnificationThumbnail2.mWindowBounds = rect2;
                                Point point = new Point(0, 0);
                                int dimensionPixelSize =
                                        magnificationThumbnail2
                                                .mContext
                                                .getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen.notification_heading_margin_end);
                                point.x = dimensionPixelSize;
                                point.y = dimensionPixelSize;
                                magnificationThumbnail2.mThumbnailWidth =
                                        (int)
                                                (magnificationThumbnail2.mWindowBounds.width()
                                                        / 7.0f);
                                int height =
                                        (int)
                                                (magnificationThumbnail2.mWindowBounds.height()
                                                        / 7.0f);
                                magnificationThumbnail2.mThumbnailHeight = height;
                                int i = point.x;
                                int i2 = point.y;
                                WindowManager.LayoutParams layoutParams =
                                        magnificationThumbnail2.mBackgroundParams;
                                layoutParams.width = magnificationThumbnail2.mThumbnailWidth;
                                layoutParams.height = height;
                                layoutParams.x = i;
                                layoutParams.y = i2;
                                if (magnificationThumbnail2.mVisible) {
                                    magnificationThumbnail2.mWindowManager.updateViewLayout(
                                            magnificationThumbnail2.mThumbnailLayout, layoutParams);
                                }
                                if (magnificationThumbnail2.mVisible) {
                                    magnificationThumbnail2.updateThumbnailMainThread(f2, f3, f4);
                                    magnificationThumbnail2.mHandler.postDelayed(
                                            new MagnificationThumbnail$$ExternalSyntheticLambda0(
                                                    magnificationThumbnail2, 3),
                                            200L);
                                }
                            }
                        });
            }
        }

        public final boolean register() {
            FullScreenMagnificationController fullScreenMagnificationController =
                    FullScreenMagnificationController.this;
            boolean isA11yTracingEnabledForTypes =
                    fullScreenMagnificationController.mControllerCtx.mTrace
                            .isA11yTracingEnabledForTypes(512L);
            int i = this.mDisplayId;
            if (isA11yTracingEnabledForTypes) {
                FullScreenMagnificationController.m135$$Nest$mlogTrace(
                        fullScreenMagnificationController,
                        "setMagnificationCallbacks",
                        "displayID=" + i + ";callback=" + this);
            }
            ControllerContext controllerContext = fullScreenMagnificationController.mControllerCtx;
            boolean magnificationCallbacks =
                    controllerContext.mWindowManager.setMagnificationCallbacks(i, this);
            this.mRegistered = magnificationCallbacks;
            if (!magnificationCallbacks) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i,
                        "set magnification callbacks fail, displayId:",
                        "FullScreenMagnificationController");
                return false;
            }
            this.mSpecAnimationBridge.setEnabled(true);
            if (controllerContext.mTrace.isA11yTracingEnabledForTypes(512L)) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(i, "displayID=", ";region=");
                m.append(this.mMagnificationRegion);
                FullScreenMagnificationController.m135$$Nest$mlogTrace(
                        fullScreenMagnificationController, "getMagnificationRegion", m.toString());
            }
            controllerContext.mWindowManager.getMagnificationRegion(i, this.mMagnificationRegion);
            this.mMagnificationRegion.getBounds(this.mMagnificationBounds);
            if (this.mMagnificationThumbnail == null) {
                this.mMagnificationThumbnail =
                        (MagnificationThumbnail)
                                fullScreenMagnificationController.mThumbnailSupplier.get();
                refreshThumbnail();
            }
            return true;
        }

        public final boolean reset(MagnificationAnimationCallback magnificationAnimationCallback) {
            if (!this.mRegistered) {
                return false;
            }
            MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            boolean z = this.mMagnificationActivated;
            setActivated(false);
            if (z) {
                magnificationSpec.clear();
                onMagnificationChangedLocked();
            }
            this.mIdOfLastServiceToMagnify = -1;
            sendSpecToAnimation(magnificationSpec, magnificationAnimationCallback);
            MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
            if (magnificationThumbnail != null) {
                magnificationThumbnail.mHandler.post(
                        new MagnificationThumbnail$$ExternalSyntheticLambda0(
                                magnificationThumbnail, 1));
            }
            return z;
        }

        public final void sendSpecToAnimation(
                MagnificationSpec magnificationSpec,
                MagnificationAnimationCallback magnificationAnimationCallback) {
            long id = Thread.currentThread().getId();
            FullScreenMagnificationController fullScreenMagnificationController =
                    FullScreenMagnificationController.this;
            long j = fullScreenMagnificationController.mMainThreadId;
            SpecAnimationBridge specAnimationBridge = this.mSpecAnimationBridge;
            if (id == j) {
                specAnimationBridge.updateSentSpecMainThread(
                        magnificationSpec, magnificationAnimationCallback);
            } else {
                fullScreenMagnificationController.mControllerCtx.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2(
                                        0),
                                specAnimationBridge,
                                magnificationSpec,
                                magnificationAnimationCallback));
            }
        }

        public final boolean setActivated(boolean z) {
            boolean z2 = this.mMagnificationActivated != z;
            if (z2) {
                this.mMagnificationActivated = z;
                FullScreenMagnificationController fullScreenMagnificationController =
                        FullScreenMagnificationController.this;
                fullScreenMagnificationController.mMagnificationInfoChangedCallbacks.forEach(
                        new FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1(
                                this, 1));
                fullScreenMagnificationController.mControllerCtx.mWindowManager
                        .setFullscreenMagnificationActivated(this.mDisplayId, z);
            }
            return z2;
        }

        public final boolean setScale(float f, float f2, float f3) {
            if (!this.mRegistered) {
                return false;
            }
            float f4 = MagnificationScaleProvider.MAX_SCALE;
            float constrain = MathUtils.constrain(f, 1.0f, MagnificationConstants.SCALE_MAX_VALUE);
            this.mMagnificationRegion.getBounds(this.mTempRect);
            MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            float f5 = magnificationSpec.scale;
            float width = (((r9.width() / 2.0f) - magnificationSpec.offsetX) + r9.left) / f5;
            float f6 = magnificationSpec.offsetY;
            float height = (((r9.height() / 2.0f) - f6) + r9.top) / f5;
            float f7 = (f2 - magnificationSpec.offsetX) / f5;
            float f8 = (f3 - f6) / f5;
            float f9 = f5 / constrain;
            this.mIdOfLastServiceToMagnify = 0;
            return setScaleAndCenter(
                    constrain, f7 + ((width - f7) * f9), ((height - f8) * f9) + f8, null, 0);
        }

        public final boolean setScaleAndCenter(
                float f,
                float f2,
                float f3,
                MagnificationAnimationCallback magnificationAnimationCallback,
                int i) {
            if (!this.mRegistered) {
                return false;
            }
            boolean alwaysDrawMagnificationFullscreenBorder =
                    Flags.alwaysDrawMagnificationFullscreenBorder();
            FullScreenMagnificationController fullScreenMagnificationController =
                    FullScreenMagnificationController.this;
            if (alwaysDrawMagnificationFullscreenBorder
                    && !((Boolean)
                                    fullScreenMagnificationController
                                            .mMagnificationConnectionStateSupplier.get())
                            .booleanValue()) {
                return false;
            }
            boolean z = true;
            boolean activated = setActivated(true);
            if (Float.isNaN(f2)) {
                f2 = getCenterX();
            }
            if (Float.isNaN(f3)) {
                f3 = getCenterY();
            }
            if (Float.isNaN(f)) {
                f = this.mCurrentMagnificationSpec.scale;
            }
            float f4 = MagnificationScaleProvider.MAX_SCALE;
            float constrain = MathUtils.constrain(f, 1.0f, MagnificationConstants.SCALE_MAX_VALUE);
            if (Float.compare(this.mCurrentMagnificationSpec.scale, constrain) != 0) {
                this.mCurrentMagnificationSpec.scale = constrain;
            } else {
                z = false;
            }
            Rect rect = this.mMagnificationBounds;
            boolean updateCurrentSpecWithOffsetsLocked =
                    updateCurrentSpecWithOffsetsLocked(
                                    ((this.mMagnificationBounds.width() / 2.0f) + rect.left)
                                            - (f2 * constrain),
                                    ((rect.height() / 2.0f) + this.mMagnificationBounds.top)
                                            - (f3 * constrain))
                            | z;
            if (updateCurrentSpecWithOffsetsLocked) {
                onMagnificationChangedLocked();
            }
            boolean z2 = updateCurrentSpecWithOffsetsLocked | activated;
            sendSpecToAnimation(this.mCurrentMagnificationSpec, magnificationAnimationCallback);
            if (this.mMagnificationActivated && i != -1) {
                this.mIdOfLastServiceToMagnify = i;
                fullScreenMagnificationController.mMagnificationInfoChangedCallbacks.forEach(
                        new FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1(
                                this, 0));
            }
            this.mZoomedOutFromService = false;
            return z2;
        }

        public final String toString() {
            return "DisplayMagnification[mCurrentMagnificationSpec="
                    + this.mCurrentMagnificationSpec
                    + ", mMagnificationRegion="
                    + this.mMagnificationRegion
                    + ", mMagnificationBounds="
                    + this.mMagnificationBounds
                    + ", mDisplayId="
                    + this.mDisplayId
                    + ", mIdOfLastServiceToMagnify="
                    + this.mIdOfLastServiceToMagnify
                    + ", mRegistered="
                    + this.mRegistered
                    + ", mUnregisterPending="
                    + this.mUnregisterPending
                    + ']';
        }

        public final void unregister(boolean z) {
            if (this.mRegistered) {
                this.mSpecAnimationBridge.setEnabled(false);
                FullScreenMagnificationController fullScreenMagnificationController =
                        FullScreenMagnificationController.this;
                boolean isA11yTracingEnabledForTypes =
                        fullScreenMagnificationController.mControllerCtx.mTrace
                                .isA11yTracingEnabledForTypes(512L);
                int i = this.mDisplayId;
                if (isA11yTracingEnabledForTypes) {
                    FullScreenMagnificationController.m135$$Nest$mlogTrace(
                            fullScreenMagnificationController,
                            "setMagnificationCallbacks",
                            "displayID=" + i + ";callback=null");
                }
                fullScreenMagnificationController.mControllerCtx.mWindowManager
                        .setMagnificationCallbacks(i, null);
                this.mMagnificationRegion.setEmpty();
                this.mRegistered = false;
                if (z) {
                    fullScreenMagnificationController.mDisplays.remove(i);
                }
                boolean z2 = false;
                for (int i2 = 0;
                        i2 < fullScreenMagnificationController.mDisplays.size()
                                && !(z2 =
                                        ((DisplayMagnification)
                                                        fullScreenMagnificationController.mDisplays
                                                                .valueAt(i2))
                                                .mRegistered);
                        i2++) {}
                if (!z2) {
                    ScreenStateObserver screenStateObserver =
                            fullScreenMagnificationController.mScreenStateObserver;
                    if (screenStateObserver.mRegistered) {
                        screenStateObserver.mContext.unregisterReceiver(screenStateObserver);
                        screenStateObserver.mRegistered = false;
                    }
                }
                MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
                if (magnificationThumbnail != null) {
                    magnificationThumbnail.mHandler.post(
                            new MagnificationThumbnail$$ExternalSyntheticLambda0(
                                    magnificationThumbnail, 1));
                    this.mMagnificationThumbnail = null;
                }
            }
            this.mUnregisterPending = false;
        }

        public final boolean updateCurrentSpecWithOffsetsLocked(float f, float f2) {
            boolean z;
            if (FullScreenMagnificationController.SEC_DEBUG) {
                Slog.i(
                        "FullScreenMagnificationController",
                        "updateCurrentSpecWithOffsetsLocked(nonNormOffsetX = "
                                + f
                                + ", nonNormOffsetY = "
                                + f2
                                + ")");
            }
            float constrain = MathUtils.constrain(f, getMinOffsetXLocked(), getMaxOffsetXLocked());
            if (Float.compare(this.mCurrentMagnificationSpec.offsetX, constrain) != 0) {
                this.mCurrentMagnificationSpec.offsetX = constrain;
                z = true;
            } else {
                z = false;
            }
            float constrain2 =
                    MathUtils.constrain(f2, getMinOffsetYLocked(), getMaxOffsetYLocked());
            if (Float.compare(this.mCurrentMagnificationSpec.offsetY, constrain2) == 0) {
                return z;
            }
            this.mCurrentMagnificationSpec.offsetY = constrain2;
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface MagnificationInfoChangedCallback {
        void onFullScreenMagnificationActivationState(int i, boolean z);

        void onFullScreenMagnificationChanged(
                int i, Region region, MagnificationConfig magnificationConfig);

        void onImeWindowVisibilityChanged(int i, boolean z);

        void onRequestMagnificationSpec(int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenStateObserver extends BroadcastReceiver {
        public final Context mContext;
        public final FullScreenMagnificationController mController;
        public boolean mRegistered = false;

        public ScreenStateObserver(
                Context context,
                FullScreenMagnificationController fullScreenMagnificationController) {
            this.mContext = context;
            this.mController = fullScreenMagnificationController;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            FullScreenMagnificationController fullScreenMagnificationController = this.mController;
            fullScreenMagnificationController.getClass();
            fullScreenMagnificationController.mControllerCtx.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new FullScreenMagnificationController$$ExternalSyntheticLambda4(0),
                            fullScreenMagnificationController,
                            Boolean.FALSE));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SpecAnimationBridge
            implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public MagnificationAnimationCallback mAnimationCallback;
        public final ControllerContext mControllerCtx;
        public final int mDisplayId;
        public final Object mLock;
        public final ValueAnimator mValueAnimator;
        public final MagnificationSpec mSentMagnificationSpec = new MagnificationSpec();
        public final MagnificationSpec mStartMagnificationSpec = new MagnificationSpec();
        public final MagnificationSpec mEndMagnificationSpec = new MagnificationSpec();
        public boolean mEnabled = false;

        public SpecAnimationBridge(ControllerContext controllerContext, Object obj, int i) {
            this.mControllerCtx = controllerContext;
            this.mLock = obj;
            this.mDisplayId = i;
            long longValue = controllerContext.mAnimationDuration.longValue();
            ValueAnimator valueAnimator = new ValueAnimator();
            this.mValueAnimator = valueAnimator;
            valueAnimator.setDuration(longValue);
            valueAnimator.setInterpolator(new DecelerateInterpolator(2.5f));
            valueAnimator.setFloatValues(FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
            valueAnimator.addUpdateListener(this);
            valueAnimator.addListener(this);
            com.android.server.accessibility.Flags.fullscreenFlingGesture();
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            sendEndCallbackMainThread(false);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            sendEndCallbackMainThread(true);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {}

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {}

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            synchronized (this.mLock) {
                try {
                    if (this.mEnabled) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        MagnificationSpec magnificationSpec = new MagnificationSpec();
                        MagnificationSpec magnificationSpec2 = this.mStartMagnificationSpec;
                        float f = magnificationSpec2.scale;
                        MagnificationSpec magnificationSpec3 = this.mEndMagnificationSpec;
                        magnificationSpec.scale =
                                ((magnificationSpec3.scale - f) * animatedFraction) + f;
                        float f2 = magnificationSpec2.offsetX;
                        magnificationSpec.offsetX =
                                ((magnificationSpec3.offsetX - f2) * animatedFraction) + f2;
                        float f3 = magnificationSpec2.offsetY;
                        magnificationSpec.offsetY =
                                ((magnificationSpec3.offsetY - f3) * animatedFraction) + f3;
                        setMagnificationSpecLocked(magnificationSpec);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void sendEndCallbackMainThread(boolean z) {
            if (this.mAnimationCallback != null) {
                if (FullScreenMagnificationController.SEC_DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "sendEndCallbackMainThread: ", "FullScreenMagnificationController", z);
                }
                this.mAnimationCallback.onResult(z, this.mSentMagnificationSpec);
                this.mAnimationCallback = null;
            }
        }

        public final void setEnabled(boolean z) {
            synchronized (this.mLock) {
                try {
                    if (z != this.mEnabled) {
                        this.mEnabled = z;
                        if (!z) {
                            this.mSentMagnificationSpec.clear();
                            if (this.mControllerCtx.mTrace.isA11yTracingEnabledForTypes(512L)) {
                                this.mControllerCtx.mTrace.logTrace(
                                        "WindowManagerInternal.setMagnificationSpec",
                                        512L,
                                        "displayID="
                                                + this.mDisplayId
                                                + ";spec="
                                                + this.mSentMagnificationSpec);
                            }
                            this.mControllerCtx.mWindowManager.setMagnificationSpec(
                                    this.mDisplayId, this.mSentMagnificationSpec);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setMagnificationSpecLocked(MagnificationSpec magnificationSpec) {
            if (this.mEnabled) {
                if (FullScreenMagnificationController.SEC_DEBUG) {
                    Slog.i("FullScreenMagnificationController", "Sending: " + magnificationSpec);
                }
                this.mSentMagnificationSpec.setTo(magnificationSpec);
                if (this.mControllerCtx.mTrace.isA11yTracingEnabledForTypes(512L)) {
                    this.mControllerCtx.mTrace.logTrace(
                            "WindowManagerInternal.setMagnificationSpec",
                            512L,
                            "displayID="
                                    + this.mDisplayId
                                    + ";spec="
                                    + this.mSentMagnificationSpec);
                }
                this.mControllerCtx.mWindowManager.setMagnificationSpec(
                        this.mDisplayId, this.mSentMagnificationSpec);
            }
        }

        public final void updateSentSpecMainThread(
                MagnificationSpec magnificationSpec,
                MagnificationAnimationCallback magnificationAnimationCallback) {
            if (this.mValueAnimator.isRunning()) {
                this.mValueAnimator.cancel();
            }
            com.android.server.accessibility.Flags.fullscreenFlingGesture();
            this.mAnimationCallback = magnificationAnimationCallback;
            synchronized (this.mLock) {
                try {
                    if (!(!this.mSentMagnificationSpec.equals(magnificationSpec))) {
                        sendEndCallbackMainThread(true);
                    } else if (this.mAnimationCallback != null) {
                        this.mEndMagnificationSpec.setTo(magnificationSpec);
                        this.mStartMagnificationSpec.setTo(this.mSentMagnificationSpec);
                        this.mValueAnimator.start();
                    } else {
                        setMagnificationSpecLocked(magnificationSpec);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mlogTrace, reason: not valid java name */
    public static void m135$$Nest$mlogTrace(
            FullScreenMagnificationController fullScreenMagnificationController,
            String str,
            String str2) {
        fullScreenMagnificationController.mControllerCtx.mTrace.logTrace(
                "WindowManagerInternal.".concat(str), 512L, str2);
    }

    public FullScreenMagnificationController(
            final Context context,
            AccessibilityTraceManager accessibilityTraceManager,
            Object obj,
            MagnificationInfoChangedCallback magnificationInfoChangedCallback,
            MagnificationScaleProvider magnificationScaleProvider,
            Executor executor,
            MagnificationController$$ExternalSyntheticLambda0
                    magnificationController$$ExternalSyntheticLambda0) {
        this(
                new ControllerContext(
                        context,
                        accessibilityTraceManager,
                        (WindowManagerInternal)
                                LocalServices.getService(WindowManagerInternal.class),
                        new Handler(context.getMainLooper()),
                        context.getResources().getInteger(R.integer.config_longAnimTime)),
                obj,
                magnificationInfoChangedCallback,
                magnificationScaleProvider,
                null,
                executor,
                new Supplier() { // from class:
                                 // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new Scroller(context);
                    }
                },
                new FullScreenMagnificationController$$ExternalSyntheticLambda3(),
                magnificationController$$ExternalSyntheticLambda0);
    }

    public FullScreenMagnificationController(
            final ControllerContext controllerContext,
            Object obj,
            MagnificationInfoChangedCallback magnificationInfoChangedCallback,
            MagnificationScaleProvider magnificationScaleProvider,
            Supplier supplier,
            Executor executor,
            Supplier supplier2,
            Supplier supplier3,
            Supplier supplier4) {
        ArrayList arrayList = new ArrayList();
        this.mMagnificationInfoChangedCallbacks = arrayList;
        this.mDisplays = new SparseArray(0);
        this.mTempRect = new Rect();
        this.mMagnificationFollowTypingEnabled = true;
        this.mAlwaysOnMagnificationEnabled = false;
        this.mControllerCtx = controllerContext;
        this.mLock = obj;
        this.mScrollerSupplier = supplier2;
        this.mTimeAnimatorSupplier = supplier3;
        this.mMagnificationConnectionStateSupplier = supplier4;
        this.mMainThreadId = controllerContext.mContext.getMainLooper().getThread().getId();
        this.mScreenStateObserver = new ScreenStateObserver(controllerContext.mContext, this);
        synchronized (obj) {
            arrayList.add(magnificationInfoChangedCallback);
        }
        this.mScaleProvider = magnificationScaleProvider;
        this.mDisplayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        MagnificationThumbnailFeatureFlag magnificationThumbnailFeatureFlag =
                new MagnificationThumbnailFeatureFlag();
        this.mMagnificationThumbnailFeatureFlag = magnificationThumbnailFeatureFlag;
        try {
            Binder.withCleanCallingIdentity(
                    new MagnificationFeatureFlagBase$$ExternalSyntheticLambda3(
                            new MagnificationFeatureFlagBase$$ExternalSyntheticLambda1(
                                    magnificationThumbnailFeatureFlag,
                                    executor,
                                    new MagnificationFeatureFlagBase$$ExternalSyntheticLambda0(
                                            magnificationThumbnailFeatureFlag,
                                            new Runnable() { // from class:
                                                             // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FullScreenMagnificationController
                                                            fullScreenMagnificationController =
                                                                    FullScreenMagnificationController
                                                                            .this;
                                                    synchronized (
                                                            fullScreenMagnificationController
                                                                    .mLock) {
                                                        for (int i = 0;
                                                                i
                                                                        < fullScreenMagnificationController
                                                                                .mDisplays.size();
                                                                i++) {
                                                            try {
                                                                fullScreenMagnificationController
                                                                        .onMagnificationThumbnailFeatureFlagChanged(
                                                                                fullScreenMagnificationController
                                                                                        .mDisplays
                                                                                        .keyAt(i));
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                }
                                            }))));
        } catch (Throwable unused) {
        }
        if (supplier != null) {
            this.mThumbnailSupplier = supplier;
        } else {
            this.mThumbnailSupplier =
                    new Supplier() { // from class:
                                     // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            FullScreenMagnificationController fullScreenMagnificationController =
                                    FullScreenMagnificationController.this;
                            FullScreenMagnificationController.ControllerContext controllerContext2 =
                                    controllerContext;
                            fullScreenMagnificationController.mMagnificationThumbnailFeatureFlag
                                    .getClass();
                            Context context = controllerContext2.mContext;
                            return new MagnificationThumbnail(
                                    context,
                                    (WindowManager) context.getSystemService(WindowManager.class),
                                    new Handler(controllerContext2.mContext.getMainLooper()));
                        }
                    };
        }
    }

    public final float getCenterX(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return FullScreenMagnificationGestureHandler.MAX_SCALE;
                }
                return displayMagnification.getCenterX();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final float getCenterY(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return FullScreenMagnificationGestureHandler.MAX_SCALE;
                }
                return displayMagnification.getCenterY();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getIdOfLastServiceToMagnify(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return -1;
                }
                return displayMagnification.mIdOfLastServiceToMagnify;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void getMagnificationBounds(int i, Rect rect) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                rect.set(displayMagnification.mMagnificationBounds);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void getMagnificationRegion(int i, Region region) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                region.set(displayMagnification.mMagnificationRegion);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final float getPersistedScale(int i) {
        return MathUtils.constrain(
                this.mScaleProvider.getScale(i), 1.0f, MagnificationScaleProvider.MAX_SCALE);
    }

    public final float getScale(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return 1.0f;
                }
                return displayMagnification.mCurrentMagnificationSpec.scale;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isActivated(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.mMagnificationActivated;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0058, code lost:

       if (android.util.MathUtils.abs(r4.mCurrentMagnificationSpec.offsetY - r4.getMinOffsetYLocked()) <= com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAtEdge(int r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.util.SparseArray r4 = r4.mDisplays     // Catch: java.lang.Throwable -> L10
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L10
            com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification r4 = (com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) r4     // Catch: java.lang.Throwable -> L10
            r5 = 0
            if (r4 != 0) goto L12
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            return r5
        L10:
            r4 = move-exception
            goto L5d
        L12:
            android.view.MagnificationSpec r1 = r4.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L10
            float r1 = r1.offsetX     // Catch: java.lang.Throwable -> L10
            float r2 = r4.getMaxOffsetXLocked()     // Catch: java.lang.Throwable -> L10
            float r1 = r1 - r2
            float r1 = android.util.MathUtils.abs(r1)     // Catch: java.lang.Throwable -> L10
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L25
            goto L5a
        L25:
            android.view.MagnificationSpec r1 = r4.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L10
            float r1 = r1.offsetX     // Catch: java.lang.Throwable -> L10
            float r3 = r4.getMinOffsetXLocked()     // Catch: java.lang.Throwable -> L10
            float r1 = r1 - r3
            float r1 = android.util.MathUtils.abs(r1)     // Catch: java.lang.Throwable -> L10
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L37
            goto L5a
        L37:
            android.view.MagnificationSpec r1 = r4.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L10
            float r1 = r1.offsetY     // Catch: java.lang.Throwable -> L10
            float r3 = r4.getMaxOffsetYLocked()     // Catch: java.lang.Throwable -> L10
            float r1 = r1 - r3
            float r1 = android.util.MathUtils.abs(r1)     // Catch: java.lang.Throwable -> L10
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L49
            goto L5a
        L49:
            android.view.MagnificationSpec r1 = r4.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L10
            float r1 = r1.offsetY     // Catch: java.lang.Throwable -> L10
            float r4 = r4.getMinOffsetYLocked()     // Catch: java.lang.Throwable -> L10
            float r1 = r1 - r4
            float r4 = android.util.MathUtils.abs(r1)     // Catch: java.lang.Throwable -> L10
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 > 0) goto L5b
        L5a:
            r5 = 1
        L5b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            return r5
        L5d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.magnification.FullScreenMagnificationController.isAtEdge(int):boolean");
    }

    public final boolean isRegistered(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.mRegistered;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean magnificationRegionContains(int i, float f, float f2) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.mMagnificationRegion.contains((int) f, (int) f2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void offsetMagnifiedRegion(int i, float f, float f2) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.offsetMagnifiedRegion(0, f, f2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onMagnificationThumbnailFeatureFlagChanged(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                synchronized (FullScreenMagnificationController.this.mLock) {
                    MagnificationThumbnail magnificationThumbnail =
                            displayMagnification.mMagnificationThumbnail;
                    if (magnificationThumbnail != null) {
                        magnificationThumbnail.mHandler.post(
                                new MagnificationThumbnail$$ExternalSyntheticLambda0(
                                        magnificationThumbnail, 1));
                        displayMagnification.mMagnificationThumbnail = null;
                    }
                    if (displayMagnification.mMagnificationThumbnail == null) {
                        displayMagnification.mMagnificationThumbnail =
                                (MagnificationThumbnail)
                                        FullScreenMagnificationController.this.mThumbnailSupplier
                                                .get();
                        displayMagnification.refreshThumbnail();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public final void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        synchronized (this.mLock) {
            try {
                if (this.mMagnificationFollowTypingEnabled) {
                    DisplayMagnification displayMagnification =
                            (DisplayMagnification) this.mDisplays.get(i);
                    if (displayMagnification == null) {
                        return;
                    }
                    if (displayMagnification.mMagnificationActivated) {
                        Rect rect = this.mTempRect;
                        MagnificationSpec magnificationSpec =
                                displayMagnification.mSpecAnimationBridge.mSentMagnificationSpec;
                        float f = magnificationSpec.scale;
                        float f2 = magnificationSpec.offsetX;
                        float f3 = magnificationSpec.offsetY;
                        rect.set(displayMagnification.mMagnificationBounds);
                        rect.offset((int) (-f2), (int) (-f3));
                        rect.scale(1.0f / f);
                        if (rect.contains(i2, i3, i4, i5)) {
                            return;
                        }
                        displayMagnification.onRectangleOnScreenRequested(i2, i3, i4, i5);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void register(int i) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    displayMagnification = new DisplayMagnification(i);
                }
                if (displayMagnification.mRegistered) {
                    return;
                }
                if (displayMagnification.register()) {
                    this.mDisplays.put(i, displayMagnification);
                    ScreenStateObserver screenStateObserver = this.mScreenStateObserver;
                    if (!screenStateObserver.mRegistered) {
                        screenStateObserver.mContext.registerReceiver(
                                screenStateObserver,
                                new IntentFilter("android.intent.action.SCREEN_OFF"));
                        screenStateObserver.mRegistered = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean reset(
            int i, MagnificationAnimationCallback magnificationAnimationCallback) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.reset(magnificationAnimationCallback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean reset(int i, boolean z) {
        return reset(i, z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
    }

    public final void resetAllIfNeeded(boolean z) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mDisplays.size(); i++) {
                try {
                    resetIfNeeded(this.mDisplays.keyAt(i), z);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void resetIfNeeded(int i, int i2) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification != null
                        && displayMagnification.mMagnificationActivated
                        && i2 == displayMagnification.mIdOfLastServiceToMagnify) {
                    displayMagnification.reset(
                            MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
                }
            } finally {
            }
        }
    }

    public final boolean resetIfNeeded(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification != null && displayMagnification.mMagnificationActivated) {
                    displayMagnification.reset(
                            z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean setScaleAndCenter(
            float f,
            float f2,
            float f3,
            int i,
            int i2,
            MagnificationAnimationCallback magnificationAnimationCallback) {
        synchronized (this.mLock) {
            try {
                DisplayMagnification displayMagnification =
                        (DisplayMagnification) this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.setScaleAndCenter(
                        f, f2, f3, magnificationAnimationCallback, i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setScaleAndCenter(int i, float f, float f2, float f3, boolean z, int i2) {
        return setScaleAndCenter(
                f,
                f2,
                f3,
                i,
                i2,
                z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
    }

    public final String toString() {
        return "MagnificationController[, mDisplays="
                + this.mDisplays
                + ", mScaleProvider="
                + this.mScaleProvider
                + "]";
    }

    public final void unregisterLocked(int i, boolean z) {
        DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
        if (displayMagnification == null) {
            return;
        }
        if (!displayMagnification.mRegistered) {
            if (z) {
                this.mDisplays.remove(i);
            }
        } else {
            if (!displayMagnification.mMagnificationActivated) {
                displayMagnification.unregister(z);
                return;
            }
            displayMagnification.mDeleteAfterUnregister = z;
            displayMagnification.mUnregisterPending = true;
            displayMagnification.reset(MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
        }
    }
}
