package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class UdfpsMaskWindow extends SysUiWindow
        implements DisplayBrightnessMonitor.OnBrightnessListener, HbmProvider {
    static final float MAX_ALPHA = 0.93f;
    public int mCurrentAlpha;
    public TextView mDebugView;
    public final DisplayBrightnessMonitor mDisplayBrightnessMonitor;
    public final DisplayStateManager mDisplayStateManager;
    public LightSourceView mLightSourceSurfaceView;
    public final View mMaskView;
    final FrameLayout mMaskWindowLayout;
    public final FingerprintSensorInfo mSensorInfo;

    public UdfpsMaskWindow(
            Context context,
            FingerprintSensorInfo fingerprintSensorInfo,
            DisplayStateManager displayStateManager,
            DisplayBrightnessMonitor displayBrightnessMonitor) {
        super(context);
        this.mSensorInfo = fingerprintSensorInfo;
        this.mDisplayStateManager = displayStateManager;
        this.mDisplayBrightnessMonitor = displayBrightnessMonitor;
        FrameLayout frameLayout =
                (FrameLayout)
                        LayoutInflater.from(context)
                                .inflate(R.layout.sem_fingerprint_maskview, (ViewGroup) null);
        this.mMaskWindowLayout = frameLayout;
        this.mMaskView = frameLayout.findViewById(R.id.sem_fingerprint_maskview);
        frameLayout.setLayoutDirection(0);
        if (!Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
            frameLayout.setForceDarkAllowed(false);
        }
        this.mBaseView = frameLayout;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        updateBackgroundColor(-1);
        super.addView();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final int getCurrentAlpha() {
        return this.mCurrentAlpha;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(-1, -1, 2619, 16777240, -3);
        layoutParams.flags &= -65537;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 16;
        if (!Utils.isTpaMode(this.mContext)) {
            layoutParams.privateFlags |= 1048576;
        }
        layoutParams.setFitInsetsTypes(0);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.semAddPrivateFlags(536870912);
        layoutParams.semAddExtensionFlags(131072);
        layoutParams.gravity = 51;
        layoutParams.setTitle("FP Maskview");
        layoutParams.surfaceType = 4;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_UdfpsMaskWindow";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void initHbmProvider() {
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        fingerprintSensorInfo.getClass();
        if (!Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
            LightSourceView lightSourceView =
                    (LightSourceView)
                            this.mMaskWindowLayout.findViewById(
                                    R.id.sem_fingerprint_lightsource_surfaceview);
            this.mLightSourceSurfaceView = lightSourceView;
            lightSourceView.mSensorInfo = fingerprintSensorInfo;
            lightSourceView.setLightSourceInfo();
            lightSourceView.setVisibility(0);
        }
        if (isDebugMode()) {
            TextView textView =
                    (TextView)
                            this.mMaskWindowLayout.findViewById(
                                    R.id.sem_fingerprint_maskview_debug_textview);
            this.mDebugView = textView;
            textView.setVisibility(0);
        }
        setVisibility(4);
        this.mDisplayBrightnessMonitor.registerListener(this);
        addView();
    }

    public boolean isDebugMode() {
        return (Build.IS_ENG || Build.IS_USERDEBUG)
                && Utils.getIntDb(this.mContext, "fingerprint_maskview_debug_mode", true, 0) != 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor.OnBrightnessListener
    public final void onBrightnessChanged(int i) {
        updateBackgroundColor(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void onConfigurationInfoChanged() {
        LightSourceView lightSourceView;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager.mCurrentRotation != displayStateManager.mPrevRotation
                && (lightSourceView = this.mLightSourceSurfaceView) != null) {
            lightSourceView.setVisibility(4);
        }
        LightSourceView lightSourceView2 = this.mLightSourceSurfaceView;
        if (lightSourceView2 != null) {
            lightSourceView2.setLightSourceInfo();
        }
        updateViewLayout();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void onRotationChanged() {
        LightSourceView lightSourceView;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager.mCurrentRotation != displayStateManager.mPrevRotation
                && (lightSourceView = this.mLightSourceSurfaceView) != null) {
            lightSourceView.setVisibility(4);
        }
        LightSourceView lightSourceView2 = this.mLightSourceSurfaceView;
        if (lightSourceView2 != null) {
            lightSourceView2.setLightSourceInfo();
        }
        updateViewLayout();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        this.mDisplayBrightnessMonitor.unregisterListener(this);
        LightSourceView lightSourceView = this.mLightSourceSurfaceView;
        if (lightSourceView != null) {
            lightSourceView.setVisibility(8);
        }
        super.removeView();
    }

    public final void setVisibility(int i) {
        Log.i("BSS_UdfpsMaskWindow", "setVisibility: [" + i + "]");
        View view = this.mBaseView;
        if (view != null) {
            view.setVisibility(i);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOffCalibrationLightSource() {
        LightSourceView lightSourceView = this.mLightSourceSurfaceView;
        if (lightSourceView != null) {
            lightSourceView.mPaint.setColor(
                    Color.parseColor("#ff" + this.mSensorInfo.mCalibrationLightColor));
            lightSourceView.handleLightSource(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOffHBM() {
        setVisibility(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOffLightSource() {
        LightSourceView lightSourceView = this.mLightSourceSurfaceView;
        if (lightSourceView != null) {
            lightSourceView.handleLightSource(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOnCalibrationLightSource() {
        LightSourceView lightSourceView = this.mLightSourceSurfaceView;
        if (lightSourceView != null) {
            lightSourceView.mPaint.setColor(
                    Color.parseColor("#ff" + this.mSensorInfo.mCalibrationLightColor));
            lightSourceView.handleLightSource(true);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOnHBM() {
        if (isVisible()) {
            return;
        }
        updateBackgroundColor(-1);
        setVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOnLightSource() {
        LightSourceView lightSourceView;
        this.mSensorInfo.getClass();
        if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE
                || (lightSourceView = this.mLightSourceSurfaceView) == null) {
            return;
        }
        lightSourceView.handleLightSource(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x007d, code lost:

       if (r6 != 2) goto L26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0068, code lost:

       r10 = 30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0066, code lost:

       r10 = 60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0064, code lost:

       if (r6 != 2) goto L26;
    */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateBackgroundColor(int r14) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.fingerprint.UdfpsMaskWindow.updateBackgroundColor(int):void");
    }
}
