package com.android.systemui.multifold.guide;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.rune.CoreRune;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MultiFoldGuideController implements CoreStartable, ConfigurationController.ConfigurationListener {
    public static final VibrationAttributes VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(66);
    public static final VibrationEffect VIBRATION_EFFECT_LV1;
    public final Display mDefaultDisplay;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final MultiFoldGuideController$$ExternalSyntheticLambda0 mResumeReverseFoldingVibrationRunnable;
    public final VibrationEffect mVibrationEffect = VIBRATION_EFFECT_LV1;
    public final VibratorHelper mVibratorHelper;

    static {
        int semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(10);
        VibrationEffect.SemMagnitudeType semMagnitudeType = VibrationEffect.SemMagnitudeType.TYPE_MAX;
        VIBRATION_EFFECT_LV1 = VibrationEffect.semCreateHaptic(semGetVibrationIndex, 0, semMagnitudeType);
        VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(11), 0, semMagnitudeType);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.multifold.guide.MultiFoldGuideController$$ExternalSyntheticLambda0] */
    public MultiFoldGuideController(Context context, Resources resources, Executor executor, Handler handler, LayoutInflater layoutInflater, WindowManager windowManager, DisplayManager displayManager, VibratorHelper vibratorHelper) {
        new DisplayManager.DisplayListener() { // from class: com.android.systemui.multifold.guide.MultiFoldGuideController.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                if (i != 0) {
                    return;
                }
                Log.d("MultiFoldGuideController", "onDisplayChanged state=" + MultiFoldGuideController.this.mDefaultDisplay.getState());
                if (MultiFoldGuideController.this.mDefaultDisplay.getState() != 1) {
                    MultiFoldGuideController.this.mDefaultDisplay.getState();
                } else if (CoreRune.FW_MULTI_FOLD_HALF_CLOSE_CONTINUITY) {
                    MultiFoldGuideController.this.getClass();
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mResumeReverseFoldingVibrationRunnable = new Runnable() { // from class: com.android.systemui.multifold.guide.MultiFoldGuideController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MultiFoldGuideController multiFoldGuideController = MultiFoldGuideController.this;
                multiFoldGuideController.mVibratorHelper.vibrate(multiFoldGuideController.mVibrationEffect, MultiFoldGuideController.VIBRATION_ATTRIBUTES);
                Handler handler2 = multiFoldGuideController.mHandler;
                MultiFoldGuideController$$ExternalSyntheticLambda0 multiFoldGuideController$$ExternalSyntheticLambda0 = multiFoldGuideController.mResumeReverseFoldingVibrationRunnable;
                handler2.removeCallbacks(multiFoldGuideController$$ExternalSyntheticLambda0);
                handler2.postDelayed(multiFoldGuideController$$ExternalSyntheticLambda0, 1000L);
            }
        };
        SensorManager sensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mVibratorHelper = vibratorHelper;
        this.mDefaultDisplay = displayManager.getDisplay(0);
        sensorManager.getDefaultSensor(25, true);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
