package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;

public final class GestureCalibrationWindow extends FocusableWindow implements View.OnKeyListener {
    public final int mBackgroundAlpha;
    public final FpGestureCalibrationClient mCallback;
    public TextView mDescriptionTxtView;
    public boolean mIsCalibrationSuccess;
    public final int mSwipeDirection;
    public final Handler mUIHandler;

    public GestureCalibrationWindow(
            Context context, FpGestureCalibrationClient fpGestureCalibrationClient, int i) {
        super(context);
        this.mCallback = fpGestureCalibrationClient;
        this.mUIHandler = new Handler(context.getMainLooper());
        this.mSwipeDirection = i;
        this.mBackgroundAlpha =
                context.getResources().getInteger(R.integer.fingerprint_verification_bg_alpha);
        try {
            View inflate =
                    LayoutInflater.from(context)
                            .inflate(
                                    R.layout.fingerprint_gesture_calibration_view,
                                    (ViewGroup) null);
            this.mBaseView = inflate;
            inflate.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
            this.mBaseView.setOnKeyListener(this);
            setHelpGuideView();
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.GCW", "GestureCalibrationWindow: ", e);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(
                        -1, -1, 2008, android.R.drawable.ab_solid_shadow_mtrl_alpha, -3);
        layoutParams.screenOrientation = 1;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.dimAmount = this.mBackgroundAlpha / 100.0f;
        layoutParams.privateFlags |= 16;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.GCW";
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onAttachedToWindow(View view) {
        super.onAttachedToWindow(view);
        this.mCallback.sendEvent(1, 0);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onFocusLost() {
        FpGestureCalibrationClient fpGestureCalibrationClient = this.mCallback;
        fpGestureCalibrationClient.sendDismissedEvent(2, null);
        fpGestureCalibrationClient.stop();
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 || this.mIsCalibrationSuccess) {
            return true;
        }
        FpGestureCalibrationClient fpGestureCalibrationClient = this.mCallback;
        fpGestureCalibrationClient.sendDismissedEvent(2, null);
        fpGestureCalibrationClient.stop();
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onSystemDialogClosed() {
        FpGestureCalibrationClient fpGestureCalibrationClient = this.mCallback;
        fpGestureCalibrationClient.sendDismissedEvent(2, null);
        fpGestureCalibrationClient.stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        if (!this.mIsCalibrationSuccess) {
            Toast.makeText(this.mContext, R.string.fingerprint_gesture_calibration_not_turned_on, 0)
                    .show();
        }
        this.mUIHandler.removeCallbacksAndMessages(null);
        super.removeView();
    }

    public final void setHelpGuideView() {
        int i;
        int i2;
        final ImageView imageView = (ImageView) this.mBaseView.findViewById(R.id.help_animation);
        if (this.mSwipeDirection == 20001) {
            i = R.drawable.finger_sensor_gesture_up_anim;
            i2 = R.string.fingerprint_gesture_calibration_swipe_up;
        } else {
            i = R.drawable.finger_sensor_gesture_anim;
            i2 = R.string.fingerprint_gesture_calibration_swipe_down;
        }
        if (imageView != null) {
            imageView.setImageDrawable(this.mContext.getDrawable(i));
            imageView.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.biometrics.app.setting.fingerprint.GestureCalibrationWindow$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((AnimationDrawable) imageView.getDrawable()).start();
                        }
                    });
        }
        TextView textView = (TextView) this.mBaseView.findViewById(R.id.guide_text);
        this.mDescriptionTxtView = textView;
        if (textView != null) {
            textView.setText(i2);
        }
    }
}
