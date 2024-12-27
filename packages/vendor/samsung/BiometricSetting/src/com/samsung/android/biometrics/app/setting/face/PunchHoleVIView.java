package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.Utils;

public final class PunchHoleVIView {
    public Context mContext;
    public View mPunchCutView;
    public LottieAnimationView mPunchHoleIcon;
    public WindowManager mWindowManager;

    public final void pause() {
        View view = this.mPunchCutView;
        if (view == null || this.mPunchHoleIcon == null) {
            return;
        }
        view.setVisibility(8);
        this.mPunchHoleIcon.pauseAnimation();
    }

    public final void play() {
        Context context = this.mContext;
        boolean z = Utils.DEBUG;
        if (context == null) {
            Log.w("BSS_Utils", "isOneHandedModeActive : context = null");
        } else if (Settings.System.getInt(context.getContentResolver(), "any_screen_running", 0)
                == 1) {
            pause();
            return;
        }
        View view = this.mPunchCutView;
        if (view == null || this.mPunchHoleIcon == null) {
            return;
        }
        view.setVisibility(0);
        this.mPunchHoleIcon.playAnimation();
    }
}
