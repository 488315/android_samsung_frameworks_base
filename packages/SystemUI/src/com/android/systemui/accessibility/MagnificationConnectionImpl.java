package com.android.systemui.accessibility;

import android.R;
import android.os.Handler;
import android.util.SparseArray;
import android.view.accessibility.IMagnificationConnection;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import com.android.systemui.Flags;
import com.android.systemui.accessibility.Magnification;
import com.android.systemui.accessibility.WindowMagnificationAnimationController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MagnificationConnectionImpl extends IMagnificationConnection.Stub {
    public IMagnificationConnectionCallback mConnectionCallback;
    public final Handler mHandler;
    public final Magnification mMagnification;

    public MagnificationConnectionImpl(Magnification magnification, Handler handler) {
        this.mMagnification = magnification;
        this.mHandler = handler;
    }

    public final void disableWindowMagnification(final int i, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i2 = i;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null) {
                    WindowMagnificationAnimationController windowMagnificationAnimationController = windowMagnificationController.mAnimationController;
                    if (windowMagnificationAnimationController.mController == null) {
                        return;
                    }
                    windowMagnificationAnimationController.sendAnimationCallback(false);
                    if (iRemoteMagnificationAnimationCallback2 == null) {
                        int i3 = windowMagnificationAnimationController.mState;
                        if (i3 == 3 || i3 == 2) {
                            windowMagnificationAnimationController.mValueAnimator.cancel();
                        }
                        windowMagnificationAnimationController.mController.deleteWindowMagnification$1();
                        windowMagnificationAnimationController.updateState();
                        return;
                    }
                    windowMagnificationAnimationController.mAnimationCallback = iRemoteMagnificationAnimationCallback2;
                    int i4 = windowMagnificationAnimationController.mState;
                    if (i4 == 0 || i4 == 2) {
                        if (i4 == 0) {
                            windowMagnificationAnimationController.sendAnimationCallback(true);
                        }
                    } else {
                        windowMagnificationAnimationController.mStartSpec.set(1.0f, Float.NaN, Float.NaN);
                        WindowMagnificationAnimationController.AnimationSpec animationSpec = windowMagnificationAnimationController.mEndSpec;
                        WindowMagnificationController windowMagnificationController2 = windowMagnificationAnimationController.mController;
                        animationSpec.set(windowMagnificationController2.isActivated() ? windowMagnificationController2.mScale : Float.NaN, Float.NaN, Float.NaN);
                        windowMagnificationAnimationController.mValueAnimator.reverse();
                        windowMagnificationAnimationController.setState(2);
                    }
                }
            }
        });
    }

    public final void enableWindowMagnification(final int i, final float f, final float f2, final float f3, final float f4, final float f5, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i2 = i;
                float f6 = f;
                float f7 = f2;
                float f8 = f3;
                float f9 = f4;
                float f10 = f5;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null) {
                    windowMagnificationController.mAnimationController.enableWindowMagnification(f6, f7, f8, f9, f10, iRemoteMagnificationAnimationCallback2);
                }
            }
        });
    }

    public final void moveWindowMagnifier(final int i, final float f, final float f2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i2 = i;
                float f3 = f;
                float f4 = f2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null) {
                    windowMagnificationController.moveWindowMagnifier(f3, f4);
                }
            }
        });
    }

    public final void moveWindowMagnifierToPosition(final int i, final float f, final float f2, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i2 = i;
                float f3 = f;
                float f4 = f2;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController == null || windowMagnificationController.mMirrorSurfaceView == null) {
                    return;
                }
                WindowMagnificationAnimationController windowMagnificationAnimationController = windowMagnificationController.mAnimationController;
                int i3 = windowMagnificationAnimationController.mState;
                if (i3 == 1) {
                    windowMagnificationAnimationController.mValueAnimator.setDuration(windowMagnificationAnimationController.mContext.getResources().getInteger(R.integer.config_shortAnimTime));
                    windowMagnificationAnimationController.enableWindowMagnification(Float.NaN, f3, f4, Float.NaN, Float.NaN, iRemoteMagnificationAnimationCallback2);
                } else if (i3 == 3) {
                    windowMagnificationAnimationController.sendAnimationCallback(false);
                    windowMagnificationAnimationController.mAnimationCallback = iRemoteMagnificationAnimationCallback2;
                    windowMagnificationAnimationController.mValueAnimator.setDuration(windowMagnificationAnimationController.mContext.getResources().getInteger(R.integer.config_shortAnimTime));
                    windowMagnificationAnimationController.setupEnableAnimationSpecs(Float.NaN, f3, f4);
                }
            }
        });
    }

    public final void onFullscreenMagnificationActivationChanged(int i, boolean z) {
        this.mHandler.post(new MagnificationConnectionImpl$$ExternalSyntheticLambda3(this, i, z, 0));
    }

    public final void onUserMagnificationScaleChanged(final int i, final int i2, final float f) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i3 = i;
                int i4 = i2;
                float f2 = f;
                Magnification magnification = magnificationConnectionImpl.mMagnification;
                SparseArray<Float> sparseArray = magnification.mUsersScales.get(i3);
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                    magnification.mUsersScales.put(i3, sparseArray);
                }
                if (sparseArray.contains(i4) && sparseArray.get(i4).floatValue() == f2) {
                    return;
                }
                sparseArray.put(i4, Float.valueOf(f2));
                WindowMagnificationSettings windowMagnificationSettings = ((MagnificationSettingsController) magnification.mMagnificationSettingsSupplier.get(i4)).mWindowMagnificationSettings;
                if (windowMagnificationSettings == null) {
                    return;
                }
                windowMagnificationSettings.mScale = f2;
                if (windowMagnificationSettings.mIsVisible) {
                    windowMagnificationSettings.setScaleSeekbar(f2);
                }
            }
        });
    }

    public final void removeMagnificationButton(int i) {
        this.mHandler.post(new MagnificationConnectionImpl$$ExternalSyntheticLambda1(this, i, 1));
    }

    public final void removeMagnificationSettingsPanel(int i) {
        this.mHandler.post(new MagnificationConnectionImpl$$ExternalSyntheticLambda1(this, i, 0));
    }

    public final void secSetCursorVisible(int i, boolean z) {
        this.mHandler.post(new MagnificationConnectionImpl$$ExternalSyntheticLambda3(this, i, z, 1));
    }

    public final void setConnectionCallback(IMagnificationConnectionCallback iMagnificationConnectionCallback) {
        this.mConnectionCallback = iMagnificationConnectionCallback;
    }

    public final void setScaleForWindowMagnification(final int i, final float f) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i2 = i;
                float f2 = f;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController == null || windowMagnificationController.mAnimationController.mValueAnimator.isRunning() || !windowMagnificationController.isActivated() || windowMagnificationController.mScale == f2) {
                    return;
                }
                windowMagnificationController.updateWindowMagnificationInternal(f2, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
                windowMagnificationController.mHandler.removeCallbacks(windowMagnificationController.mUpdateStateDescriptionRunnable);
                windowMagnificationController.mHandler.postDelayed(windowMagnificationController.mUpdateStateDescriptionRunnable, 100L);
            }
        });
    }

    public final void showMagnificationButton(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationConnectionImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationConnectionImpl.this;
                int i3 = i;
                int i4 = i2;
                Magnification magnification = magnificationConnectionImpl.mMagnification;
                magnification.getClass();
                Flags.FEATURE_FLAGS.getClass();
                Magnification.AnonymousClass1 anonymousClass1 = magnification.mHandler;
                if (anonymousClass1.hasMessages(1)) {
                    return;
                }
                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(1, i3, i4), 300L);
            }
        });
    }
}
