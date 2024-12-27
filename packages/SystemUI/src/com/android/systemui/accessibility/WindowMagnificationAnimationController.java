package com.android.systemui.accessibility;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WindowMagnificationAnimationController implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    public static final boolean DEBUG = Log.isLoggable("WindowMagnificationAnimationController", 3);
    static final int STATE_DISABLED = 0;
    static final int STATE_ENABLED = 1;
    public IRemoteMagnificationAnimationCallback mAnimationCallback;
    public final Context mContext;
    public WindowMagnificationController mController;
    public boolean mEndAnimationCanceled;
    public final AnimationSpec mEndSpec;
    public float mMagnificationFrameOffsetRatioX;
    public float mMagnificationFrameOffsetRatioY;
    public Runnable mOnAnimationEndRunnable;
    public final AnimationSpec mStartSpec;
    public int mState;
    public final ValueAnimator mValueAnimator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimationSpec {
        public float mCenterX;
        public float mCenterY;
        public float mScale;

        public /* synthetic */ AnimationSpec(int i) {
            this();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || AnimationSpec.class != obj.getClass()) {
                return false;
            }
            AnimationSpec animationSpec = (AnimationSpec) obj;
            return this.mScale == animationSpec.mScale && this.mCenterX == animationSpec.mCenterX && this.mCenterY == animationSpec.mCenterY;
        }

        public final int hashCode() {
            float f = this.mScale;
            int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
            float f2 = this.mCenterX;
            int floatToIntBits2 = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.mCenterY;
            return floatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0);
        }

        public final void set(float f, float f2, float f3) {
            this.mScale = f;
            this.mCenterX = f2;
            this.mCenterY = f3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("AnimationSpec{mScale=");
            sb.append(this.mScale);
            sb.append(", mCenterX=");
            sb.append(this.mCenterX);
            sb.append(", mCenterY=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.mCenterY, '}');
        }

        private AnimationSpec() {
            this.mScale = Float.NaN;
            this.mCenterX = Float.NaN;
            this.mCenterY = Float.NaN;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WindowMagnificationAnimationController(android.content.Context r5) {
        /*
            r4 = this;
            android.content.res.Resources r0 = r5.getResources()
            android.animation.ValueAnimator r1 = new android.animation.ValueAnimator
            r1.<init>()
            r2 = 17694722(0x10e0002, float:2.6081287E-38)
            int r0 = r0.getInteger(r2)
            long r2 = (long) r0
            r1.setDuration(r2)
            android.view.animation.AccelerateInterpolator r0 = new android.view.animation.AccelerateInterpolator
            r2 = 1075838976(0x40200000, float:2.5)
            r0.<init>(r2)
            r1.setInterpolator(r0)
            r0 = 2
            float[] r0 = new float[r0]
            r0 = {x002c: FILL_ARRAY_DATA , data: [0, 1065353216} // fill-array
            r1.setFloatValues(r0)
            r4.<init>(r5, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.WindowMagnificationAnimationController.<init>(android.content.Context):void");
    }

    public final void enableWindowMagnification(float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        if (this.mController == null) {
            return;
        }
        sendAnimationCallback(false);
        this.mMagnificationFrameOffsetRatioX = f4;
        this.mMagnificationFrameOffsetRatioY = f5;
        if (iRemoteMagnificationAnimationCallback == null) {
            int i = this.mState;
            if (i == 3 || i == 2) {
                this.mValueAnimator.cancel();
            }
            this.mController.updateWindowMagnificationInternal(f, f2, f3, this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
            updateState();
            return;
        }
        this.mAnimationCallback = iRemoteMagnificationAnimationCallback;
        setupEnableAnimationSpecs(f, f2, f3);
        if (!this.mEndSpec.equals(this.mStartSpec)) {
            int i2 = this.mState;
            if (i2 == 2) {
                this.mValueAnimator.reverse();
            } else {
                if (i2 == 3) {
                    this.mValueAnimator.cancel();
                }
                this.mValueAnimator.start();
            }
            setState(3);
            return;
        }
        int i3 = this.mState;
        if (i3 == 0) {
            this.mController.updateWindowMagnificationInternal(f, f2, f3, this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
        } else if (i3 == 3 || i3 == 2) {
            this.mValueAnimator.cancel();
        }
        sendAnimationCallback(true);
        updateState();
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.mEndAnimationCanceled = true;
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        this.mEndAnimationCanceled = false;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.mController == null) {
            return;
        }
        float animatedFraction = valueAnimator.getAnimatedFraction();
        AnimationSpec animationSpec = this.mStartSpec;
        float f = animationSpec.mScale;
        AnimationSpec animationSpec2 = this.mEndSpec;
        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(animationSpec2.mScale, f, animatedFraction, f);
        float f2 = animationSpec.mCenterX;
        float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(animationSpec2.mCenterX, f2, animatedFraction, f2);
        float f3 = animationSpec.mCenterY;
        this.mController.updateWindowMagnificationInternal(m$1, m$12, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(animationSpec2.mCenterY, f3, animatedFraction, f3), this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
    }

    public final void sendAnimationCallback(boolean z) {
        IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback = this.mAnimationCallback;
        if (iRemoteMagnificationAnimationCallback != null) {
            try {
                iRemoteMagnificationAnimationCallback.onResult(z);
                if (DEBUG) {
                    Log.d("WindowMagnificationAnimationController", "sendAnimationCallback success = " + z);
                }
            } catch (RemoteException e) {
                Log.w("WindowMagnificationAnimationController", "sendAnimationCallback failed : " + e);
            }
            this.mAnimationCallback = null;
        }
    }

    public final void setState(int i) {
        if (DEBUG) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("setState from "), this.mState, " to ", i, "WindowMagnificationAnimationController");
        }
        this.mState = i;
    }

    public final void setupEnableAnimationSpecs(float f, float f2, float f3) {
        WindowMagnificationController windowMagnificationController = this.mController;
        if (windowMagnificationController == null) {
            return;
        }
        float f4 = windowMagnificationController.isActivated() ? windowMagnificationController.mScale : Float.NaN;
        WindowMagnificationController windowMagnificationController2 = this.mController;
        float exactCenterX = windowMagnificationController2.isActivated() ? windowMagnificationController2.mMagnificationFrame.exactCenterX() : Float.NaN;
        WindowMagnificationController windowMagnificationController3 = this.mController;
        float exactCenterY = windowMagnificationController3.isActivated() ? windowMagnificationController3.mMagnificationFrame.exactCenterY() : Float.NaN;
        if (this.mState == 0) {
            this.mStartSpec.set(1.0f, f2, f3);
            AnimationSpec animationSpec = this.mEndSpec;
            if (Float.isNaN(f)) {
                f = this.mContext.getResources().getInteger(R.integer.magnification_default_scale);
            }
            animationSpec.set(f, f2, f3);
        } else {
            this.mStartSpec.set(f4, exactCenterX, exactCenterY);
            int i = this.mState;
            if (i == 3) {
                f4 = this.mEndSpec.mScale;
            }
            if (i == 3) {
                exactCenterX = this.mEndSpec.mCenterX;
            }
            if (i == 3) {
                exactCenterY = this.mEndSpec.mCenterY;
            }
            AnimationSpec animationSpec2 = this.mEndSpec;
            if (Float.isNaN(f)) {
                f = f4;
            }
            if (Float.isNaN(f2)) {
                f2 = exactCenterX;
            }
            if (Float.isNaN(f3)) {
                f3 = exactCenterY;
            }
            animationSpec2.set(f, f2, f3);
        }
        if (DEBUG) {
            Log.d("WindowMagnificationAnimationController", "SetupEnableAnimationSpecs : mStartSpec = " + this.mStartSpec + ", endSpec = " + this.mEndSpec);
        }
    }

    public final void updateState() {
        WindowMagnificationController windowMagnificationController = this.mController;
        if (Float.isNaN(windowMagnificationController.isActivated() ? windowMagnificationController.mScale : Float.NaN)) {
            setState(0);
        } else {
            setState(1);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator, boolean z) {
        if (this.mEndAnimationCanceled || this.mController == null) {
            return;
        }
        this.mOnAnimationEndRunnable.run();
        if (this.mState == 2) {
            this.mController.deleteWindowMagnification$1();
        }
        updateState();
        sendAnimationCallback(true);
        this.mValueAnimator.setDuration(this.mContext.getResources().getInteger(android.R.integer.config_longAnimTime));
    }

    public WindowMagnificationAnimationController(Context context, ValueAnimator valueAnimator) {
        int i = 0;
        this.mStartSpec = new AnimationSpec(i);
        this.mEndSpec = new AnimationSpec(i);
        this.mMagnificationFrameOffsetRatioX = 0.0f;
        this.mMagnificationFrameOffsetRatioY = 0.0f;
        this.mEndAnimationCanceled = false;
        this.mState = 0;
        this.mContext = context;
        this.mValueAnimator = valueAnimator;
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }
}
