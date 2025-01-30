package com.android.systemui.accessibility;

import android.R;
import android.os.Handler;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import android.view.accessibility.IWindowMagnificationConnection;
import android.view.accessibility.IWindowMagnificationConnectionCallback;
import com.android.systemui.accessibility.WindowMagnificationAnimationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowMagnificationConnectionImpl extends IWindowMagnificationConnection.Stub {
    public IWindowMagnificationConnectionCallback mConnectionCallback;
    public final Handler mHandler;
    public final WindowMagnification mWindowMagnification;

    public WindowMagnificationConnectionImpl(WindowMagnification windowMagnification, Handler handler) {
        this.mWindowMagnification = windowMagnification;
        this.mHandler = handler;
    }

    public final void disableWindowMagnification(final int i, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i2 = i;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationControllerSupplier.get(i2);
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
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i2 = i;
                float f6 = f;
                float f7 = f2;
                float f8 = f3;
                float f9 = f4;
                float f10 = f5;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null) {
                    windowMagnificationController.mAnimationController.enableWindowMagnification(f6, f7, f8, f9, f10, iRemoteMagnificationAnimationCallback2);
                }
            }
        });
    }

    public final void moveWindowMagnifier(final int i, final float f, final float f2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i2 = i;
                float f3 = f;
                float f4 = f2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null) {
                    windowMagnificationController.moveWindowMagnifier(f3, f4);
                }
            }
        });
    }

    public final void moveWindowMagnifierToPosition(final int i, final float f, final float f2, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i2 = i;
                float f3 = f;
                float f4 = f2;
                IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback2 = iRemoteMagnificationAnimationCallback;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationControllerSupplier.get(i2);
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

    public final void removeMagnificationButton(int i) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5(this, i, 1));
    }

    public final void removeMagnificationSettingsPanel(int i) {
        this.mHandler.post(new WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5(this, i, 0));
    }

    public final void setConnectionCallback(IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback) {
        this.mConnectionCallback = iWindowMagnificationConnectionCallback;
    }

    public final void setScale(final int i, final float f) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i2 = i;
                float f2 = f;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController == null || windowMagnificationController.mAnimationController.mValueAnimator.isRunning() || !windowMagnificationController.isActivated() || windowMagnificationController.mScale == f2) {
                    return;
                }
                windowMagnificationController.enableWindowMagnificationInternal(f2, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
                windowMagnificationController.mHandler.removeCallbacks(windowMagnificationController.mUpdateStateDescriptionRunnable);
                windowMagnificationController.mHandler.postDelayed(windowMagnificationController.mUpdateStateDescriptionRunnable, 100L);
            }
        });
    }

    public final void showMagnificationButton(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch magnificationModeSwitch;
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnificationConnectionImpl.this;
                int i3 = i;
                int i4 = i2;
                WindowMagnification windowMagnification = windowMagnificationConnectionImpl.mWindowMagnification;
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) windowMagnification.mMagnificationSettingsSupplier.get(i3);
                if ((magnificationSettingsController != null ? magnificationSettingsController.mWindowMagnificationSettings.mIsVisible : false) || (magnificationModeSwitch = (MagnificationModeSwitch) windowMagnification.mModeSwitchesController.mSwitchSupplier.get(i3)) == null) {
                    return;
                }
                magnificationModeSwitch.showButton(i4, true);
            }
        });
    }
}
