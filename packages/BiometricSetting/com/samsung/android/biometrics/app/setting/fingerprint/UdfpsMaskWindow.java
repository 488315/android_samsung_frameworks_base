package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;

/* loaded from: classes.dex */
public final class UdfpsMaskWindow extends SysUiWindow implements DisplayBrightnessMonitor.OnBrightnessListener, HbmProvider {

    @VisibleForTesting
    static final float MAX_ALPHA = 0.93f;
    private int mCurrentAlpha;
    private TextView mDebugView;
    private final DisplayBrightnessMonitor mDisplayBrightnessMonitor;
    private final DisplayStateManager mDisplayStateManager;
    private LightSourceView mLightSourceSurfaceView;
    final View mMaskView;

    @VisibleForTesting
    final FrameLayout mMaskWindowLayout;
    private final UdfpsInfo mSensorInfo;

    public UdfpsMaskWindow(Context context, UdfpsInfo udfpsInfo, DisplayStateManager displayStateManager, DisplayBrightnessMonitor displayBrightnessMonitor) {
        super(context);
        this.mSensorInfo = udfpsInfo;
        this.mDisplayStateManager = displayStateManager;
        this.mDisplayBrightnessMonitor = displayBrightnessMonitor;
        FrameLayout frameLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.sem_fingerprint_maskview, (ViewGroup) null);
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
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2619, 16777240, -3);
        layoutParams.flags &= -65537;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 16;
        if (!Utils.isTpaMode(getContext())) {
            layoutParams.privateFlags |= 1048576;
        }
        layoutParams.setFitInsetsTypes(0);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.semAddPrivateFlags(536870912);
        layoutParams.semAddExtensionFlags(131072);
        layoutParams.gravity = 51;
        layoutParams.setTitle("FP Maskview");
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_UdfpsMaskWindow";
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void initHbmProvider() {
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        udfpsInfo.getClass();
        if (!Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
            LightSourceView lightSourceView = (LightSourceView) this.mMaskWindowLayout.findViewById(R.id.sem_fingerprint_lightsource_surfaceview);
            this.mLightSourceSurfaceView = lightSourceView;
            lightSourceView.init(udfpsInfo);
        }
        if (isDebugMode()) {
            TextView textView = (TextView) this.mMaskWindowLayout.findViewById(R.id.sem_fingerprint_maskview_debug_textview);
            this.mDebugView = textView;
            textView.setVisibility(0);
        }
        setVisibility(4);
        this.mDisplayBrightnessMonitor.registerListener(this);
        addView();
    }

    @VisibleForTesting
    protected boolean isDebugMode() {
        return (Build.IS_ENG || Build.IS_USERDEBUG) && Utils.getIntDb(getContext(), "fingerprint_maskview_debug_mode", true, 0) != 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor.OnBrightnessListener
    public final void onBrightnessChanged(int i) {
        updateBackgroundColor(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void onConfigurationInfoChanged() {
        LightSourceView lightSourceView;
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        if (displayStateManager.getCurrentRotation() != displayStateManager.getPrevRotation() && (lightSourceView = this.mLightSourceSurfaceView) != null) {
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
        if (displayStateManager.getCurrentRotation() != displayStateManager.getPrevRotation() && (lightSourceView = this.mLightSourceSurfaceView) != null) {
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

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void setVisibility(int i) {
        Log.i("BSS_UdfpsMaskWindow", "setVisibility: [" + i + "]");
        super.setVisibility(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
    public final void turnOffCalibrationLightSource() {
        LightSourceView lightSourceView = this.mLightSourceSurfaceView;
        if (lightSourceView != null) {
            lightSourceView.handleCalibrationLightSource(this.mSensorInfo, false);
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
            lightSourceView.handleCalibrationLightSource(this.mSensorInfo, true);
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
        if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE || (lightSourceView = this.mLightSourceSurfaceView) == null) {
            return;
        }
        lightSourceView.handleLightSource(true);
    }

    @VisibleForTesting
    void updateBackgroundColor(int i) {
        Log.d("BSS_UdfpsMaskWindow", "updateBackgroundColor: " + i);
        if (i == -1) {
            i = this.mDisplayBrightnessMonitor.getCurrentBrightness();
        }
        if (i < 0) {
            Log.i("BSS_UdfpsMaskWindow", "updateBackgroundColor: invalid brightness value, set to default");
            i = 127;
        }
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        float fingerPrintBacklightValue = displayStateManager.getFingerPrintBacklightValue(i);
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        float alphaMaskLevel = displayStateManager.getAlphaMaskLevel(fingerPrintBacklightValue, udfpsInfo.getBrightnessNitForOptical());
        if (alphaMaskLevel <= 0.0f) {
            float nitsForOptical = udfpsInfo.getNitsForOptical();
            alphaMaskLevel = (1.644f * fingerPrintBacklightValue) - (fingerPrintBacklightValue <= 128.0f ? (0.23f * fingerPrintBacklightValue) - 2.0f : fingerPrintBacklightValue <= 255.0f ? (255.0f - fingerPrintBacklightValue) * 0.215f : 0.0f) > nitsForOptical ? 0.0f : (float) (1.0d - Math.pow(r3 / nitsForOptical, 0.5405405405405405d));
        }
        float min = Math.min(MAX_ALPHA, alphaMaskLevel);
        int i2 = (int) (255.0f * min);
        this.mCurrentAlpha = i2;
        int i3 = i2 << 24;
        this.mMaskView.setBackgroundColor(i3);
        TextView textView = this.mDebugView;
        if (textView != null) {
            textView.setText("Alpha : " + min);
        }
        Log.i("BSS_UdfpsMaskWindow", "updateBackgroundColor: " + Integer.toHexString(i3) + ", " + fingerPrintBacklightValue + ", " + min);
    }
}
