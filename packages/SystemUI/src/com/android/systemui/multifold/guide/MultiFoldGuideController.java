package com.android.systemui.multifold.guide;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.WindowManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class MultiFoldGuideController implements CoreStartable, ConfigurationController.ConfigurationListener {
    public static final VibrationAttributes VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(66);
    public static final VibrationEffect VIBRATION_EFFECT_LV1;
    public final Context mContext;
    public HalfCloseContinuityGuideView mContinuityGuideView;
    public final Display mDefaultDisplay;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final LayoutInflater mLayoutInflater;
    public final Resources mResources;
    public final MultiFoldGuideController$$ExternalSyntheticLambda0 mResumeReverseFoldingVibrationRunnable;
    public final IStatusBarService mStatusBarService;
    public final VibratorHelper mVibratorHelper;
    public final WindowManager mWindowManager;
    public final IBinder mToken = new Binder();
    public final int mDeviceState = -1;
    public final VibrationEffect mVibrationEffect = VIBRATION_EFFECT_LV1;

    /* renamed from: -$$Nest$mhandleContinuityGuideView, reason: not valid java name */
    public static void m2639$$Nest$mhandleContinuityGuideView(MultiFoldGuideController multiFoldGuideController) {
        if (multiFoldGuideController.mContinuityGuideView != null) {
            Log.d("MultiFoldGuideController", "mContinuityGuideView is already showing!");
            try {
                multiFoldGuideController.mWindowManager.removeView(multiFoldGuideController.mContinuityGuideView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            multiFoldGuideController.mContinuityGuideView = null;
        }
        multiFoldGuideController.getClass();
        try {
            IStatusBarService iStatusBarService = multiFoldGuideController.mStatusBarService;
            if (iStatusBarService != null) {
                iStatusBarService.disable(23068672, multiFoldGuideController.mToken, multiFoldGuideController.mContext.getPackageName());
            }
        } catch (RemoteException e2) {
            Log.w("MultiFoldGuideController", "disableStatusBar: disable failed, flag=23068672", e2);
        }
        HalfCloseContinuityGuideView halfCloseContinuityGuideView = new HalfCloseContinuityGuideView(multiFoldGuideController.mContext, multiFoldGuideController.mLayoutInflater, multiFoldGuideController.mResources.getConfiguration().isNightModeActive(), true);
        multiFoldGuideController.mContinuityGuideView = halfCloseContinuityGuideView;
        WindowManager windowManager = multiFoldGuideController.mWindowManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2633, NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME, -3);
        if (halfCloseContinuityGuideView.mGuideVisible) {
            layoutParams.screenOrientation = 0;
        }
        layoutParams.windowAnimations = R.style.HalfCloseFadeAnimation;
        layoutParams.setTitle("HalfCloseContinuityGuideView");
        windowManager.addView(halfCloseContinuityGuideView, layoutParams);
    }

    static {
        int iSemGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(10);
        VibrationEffect.SemMagnitudeType semMagnitudeType = VibrationEffect.SemMagnitudeType.TYPE_MAX;
        VIBRATION_EFFECT_LV1 = VibrationEffect.semCreateHaptic(iSemGetVibrationIndex, 0, semMagnitudeType);
        VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(11), 0, semMagnitudeType);
    }

    /* JADX WARN: Type inference failed for: r9v5, types: [com.android.systemui.multifold.guide.MultiFoldGuideController$$ExternalSyntheticLambda0] */
    public MultiFoldGuideController(Context context, Resources resources, Executor executor, Handler handler, LayoutInflater layoutInflater, WindowManager windowManager, DisplayManager displayManager, IStatusBarService iStatusBarService, ConfigurationController configurationController, VibratorHelper vibratorHelper) {
        new DisplayManager.DisplayListener() { // from class: com.android.systemui.multifold.guide.MultiFoldGuideController.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                HalfCloseContinuityGuideView halfCloseContinuityGuideView;
                HalfCloseContinuityGuideView halfCloseContinuityGuideView2;
                if (i != 0) {
                    return;
                }
                Log.d("MultiFoldGuideController", "onDisplayChanged state=" + MultiFoldGuideController.this.mDefaultDisplay.getState());
                if (MultiFoldGuideController.this.mDefaultDisplay.getState() == 1) {
                    if (CoreRune.FW_MULTI_FOLD_HALF_CLOSE_CONTINUITY) {
                        MultiFoldGuideController multiFoldGuideController = MultiFoldGuideController.this;
                        if (multiFoldGuideController.mDeviceState != 6 || (halfCloseContinuityGuideView2 = multiFoldGuideController.mContinuityGuideView) == null || halfCloseContinuityGuideView2.mGuideVisible) {
                            return;
                        }
                        MultiFoldGuideController.m2639$$Nest$mhandleContinuityGuideView(multiFoldGuideController);
                        return;
                    }
                    return;
                }
                if (MultiFoldGuideController.this.mDefaultDisplay.getState() != 2 && Display.isDozeState(MultiFoldGuideController.this.mDefaultDisplay.getState()) && CoreRune.FW_MULTI_FOLD_HALF_CLOSE_CONTINUITY) {
                    MultiFoldGuideController multiFoldGuideController2 = MultiFoldGuideController.this;
                    if (multiFoldGuideController2.mDeviceState != 6 || (halfCloseContinuityGuideView = multiFoldGuideController2.mContinuityGuideView) == null || halfCloseContinuityGuideView.mGuideVisible) {
                        return;
                    }
                    MultiFoldGuideController.m2639$$Nest$mhandleContinuityGuideView(multiFoldGuideController2);
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
                MultiFoldGuideController multiFoldGuideController = this.f$0;
                multiFoldGuideController.mVibratorHelper.vibrate(multiFoldGuideController.mVibrationEffect, MultiFoldGuideController.VIBRATION_ATTRIBUTES);
                Handler handler2 = multiFoldGuideController.mHandler;
                MultiFoldGuideController$$ExternalSyntheticLambda0 multiFoldGuideController$$ExternalSyntheticLambda0 = multiFoldGuideController.mResumeReverseFoldingVibrationRunnable;
                handler2.removeCallbacks(multiFoldGuideController$$ExternalSyntheticLambda0);
                handler2.postDelayed(multiFoldGuideController$$ExternalSyntheticLambda0, 1000L);
            }
        };
        this.mContext = context;
        this.mResources = resources;
        this.mLayoutInflater = layoutInflater;
        SensorManager sensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mWindowManager = windowManager;
        this.mStatusBarService = iStatusBarService;
        this.mVibratorHelper = vibratorHelper;
        this.mDefaultDisplay = displayManager.getDisplay(0);
        sensorManager.getDefaultSensor(25, true);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
