package com.samsung.android.biometrics.app.setting.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public final class GestureCalibrationWindow extends FocusableWindow implements View.OnKeyListener {
    private final int mBackgroundAlpha;
    private final GestureCalibrationCallback mCallback;
    private TextView mDescriptionTxtView;
    private boolean mIsCalibrationSuccess;
    private final int mSwipeDirection;
    private final Handler mUIHandler;

    public GestureCalibrationWindow(Context context, GestureCalibrationCallback gestureCalibrationCallback, int i) {
        super(context);
        this.mCallback = gestureCalibrationCallback;
        this.mUIHandler = new Handler(context.getMainLooper());
        this.mSwipeDirection = i;
        this.mBackgroundAlpha = context.getResources().getInteger(R.integer.fingerprint_verification_bg_alpha);
        try {
            View inflate = getLayoutInflater().inflate(R.layout.fingerprint_gesture_calibration_view, (ViewGroup) null);
            this.mBaseView = inflate;
            inflate.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
            this.mBaseView.setOnKeyListener(this);
            setHelpGuideView();
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.GCW", "GestureCalibrationWindow: ", e);
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void setHelpGuideView() {
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
            imageView.setImageDrawable(getContext().getDrawable(i));
            imageView.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.GestureCalibrationWindow$$ExternalSyntheticLambda0
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

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2008, android.R.drawable.ab_solid_shadow_mtrl_alpha, -3);
        layoutParams.screenOrientation = 1;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.dimAmount = this.mBackgroundAlpha / 100.0f;
        layoutParams.privateFlags |= 16;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.GCW";
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    protected final void onAttachedToWindow(View view) {
        super.onAttachedToWindow(view);
        ((FpGestureCalibrationClient) this.mCallback).onUiReady();
    }

    final void onCalibrationSucceeded() {
        TextView textView = this.mDescriptionTxtView;
        if (textView != null) {
            textView.setText(R.string.fingerprint_gesture_calibration_good_job);
        }
        this.mIsCalibrationSuccess = true;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onFocusLost() {
        ((FpGestureCalibrationClient) this.mCallback).onUserCancel(1);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 || this.mIsCalibrationSuccess) {
            return true;
        }
        ((FpGestureCalibrationClient) this.mCallback).onUserCancel(3);
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onSystemDialogClosed() {
        ((FpGestureCalibrationClient) this.mCallback).onUserCancel(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        if (!this.mIsCalibrationSuccess) {
            Toast.makeText(getContext(), R.string.fingerprint_gesture_calibration_not_turned_on, 0).show();
        }
        this.mUIHandler.removeCallbacksAndMessages(null);
        super.removeView();
    }
}
