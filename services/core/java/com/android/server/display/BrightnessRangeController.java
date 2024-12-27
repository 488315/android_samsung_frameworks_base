package com.android.server.display;

import android.os.IBinder;
import android.util.Slog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.brightness.clamper.HdrClamper;
import com.android.server.display.config.HdrBrightnessData;
import com.android.server.display.feature.DisplayManagerFlags;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrightnessRangeController {
    public final HighBrightnessModeController mHbmController;
    public final HdrClamper mHdrClamper;
    public final Runnable mModeChangeCallback;
    public final NormalBrightnessModeController mNormalBrightnessModeController;
    public final boolean mUseHdrClamper;
    public final boolean mUseNbmController;

    public BrightnessRangeController(HighBrightnessModeController highBrightnessModeController, Runnable runnable, DisplayDeviceConfig displayDeviceConfig, NormalBrightnessModeController normalBrightnessModeController, HdrClamper hdrClamper, DisplayManagerFlags displayManagerFlags, IBinder iBinder, DisplayDeviceInfo displayDeviceInfo) {
        this.mHbmController = highBrightnessModeController;
        this.mModeChangeCallback = runnable;
        this.mHdrClamper = hdrClamper;
        this.mNormalBrightnessModeController = normalBrightnessModeController;
        this.mUseHdrClamper = displayManagerFlags.mHdrClamperFlagState.isEnabled();
        boolean isEnabled = displayManagerFlags.mNbmControllerFlagState.isEnabled();
        this.mUseNbmController = isEnabled;
        if (isEnabled) {
            normalBrightnessModeController.mMaxBrightnessLimits = displayDeviceConfig.mLuxThrottlingData;
            normalBrightnessModeController.recalculateMaxBrightness();
        }
        updateHdrClamper(displayDeviceInfo, iBinder, displayDeviceConfig);
    }

    public final void applyChanges(BooleanSupplier booleanSupplier, Runnable runnable) {
        if (!this.mUseNbmController) {
            runnable.run();
            return;
        }
        boolean asBoolean = booleanSupplier.getAsBoolean();
        runnable.run();
        if (asBoolean) {
            this.mModeChangeCallback.run();
        }
    }

    public final float getCurrentBrightnessMax() {
        boolean z = this.mUseNbmController;
        HighBrightnessModeController highBrightnessModeController = this.mHbmController;
        return (!z || (highBrightnessModeController.deviceSupportsHbm() && highBrightnessModeController.isHbmCurrentlyAllowed())) ? highBrightnessModeController.getCurrentBrightnessMax() : Math.min(highBrightnessModeController.getCurrentBrightnessMax(), this.mNormalBrightnessModeController.mMaxBrightness);
    }

    public final float getHdrBrightnessValue() {
        return this.mHbmController.getHdrBrightnessValue();
    }

    public final float getHdrTransitionRate() {
        if (this.mUseHdrClamper) {
            HdrClamper hdrClamper = this.mHdrClamper;
            r1 = hdrClamper.mUseSlowTransition ? hdrClamper.mTransitionRate : -1.0f;
            hdrClamper.mUseSlowTransition = false;
        }
        return r1;
    }

    public final void onAmbientLuxChange(final float f) {
        applyChanges(new BooleanSupplier() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                BrightnessRangeController brightnessRangeController = BrightnessRangeController.this;
                float f2 = f;
                NormalBrightnessModeController normalBrightnessModeController = brightnessRangeController.mNormalBrightnessModeController;
                normalBrightnessModeController.mAmbientLux = f2;
                return normalBrightnessModeController.recalculateMaxBrightness();
            }
        }, new Runnable() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessRangeController brightnessRangeController = BrightnessRangeController.this;
                float f2 = f;
                HighBrightnessModeController highBrightnessModeController = brightnessRangeController.mHbmController;
                highBrightnessModeController.mAmbientLux = f2;
                if (highBrightnessModeController.deviceSupportsHbm() && highBrightnessModeController.mIsAutoBrightnessEnabled) {
                    boolean z = f2 >= highBrightnessModeController.mHbmData.minimumLux;
                    if (z != highBrightnessModeController.mIsInAllowedAmbientRange) {
                        highBrightnessModeController.mIsInAllowedAmbientRange = z;
                        highBrightnessModeController.recalculateTimeAllowance();
                    }
                }
            }
        });
        if (this.mUseHdrClamper) {
            HdrClamper hdrClamper = this.mHdrClamper;
            hdrClamper.mAmbientLux = f;
            hdrClamper.recalculateBrightnessCap(hdrClamper.mHdrBrightnessData, f, hdrClamper.mHdrVisible);
        }
    }

    public final void onBrightnessChanged(int i, float f, float f2) {
        this.mHbmController.onBrightnessChanged(i, f, f2);
    }

    public final void setAutoBrightnessEnabled(final int i) {
        applyChanges(new BooleanSupplier() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                BrightnessRangeController brightnessRangeController = BrightnessRangeController.this;
                int i2 = i;
                NormalBrightnessModeController normalBrightnessModeController = brightnessRangeController.mNormalBrightnessModeController;
                normalBrightnessModeController.getClass();
                boolean z = i2 == 1;
                if (z == normalBrightnessModeController.mAutoBrightnessEnabled) {
                    return false;
                }
                normalBrightnessModeController.mAutoBrightnessEnabled = z;
                return normalBrightnessModeController.recalculateMaxBrightness();
            }
        }, new Runnable() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessRangeController brightnessRangeController = BrightnessRangeController.this;
                int i2 = i;
                HighBrightnessModeController highBrightnessModeController = brightnessRangeController.mHbmController;
                highBrightnessModeController.getClass();
                boolean z = i2 == 1;
                highBrightnessModeController.mIsAutoBrightnessOffByState = i2 == 3;
                if (!highBrightnessModeController.deviceSupportsHbm() || z == highBrightnessModeController.mIsAutoBrightnessEnabled) {
                    return;
                }
                if (HighBrightnessModeController.DEBUG) {
                    Slog.d("HighBrightnessModeController", "setAutoBrightnessEnabled( " + z + " )");
                }
                highBrightnessModeController.mIsAutoBrightnessEnabled = z;
                highBrightnessModeController.mIsInAllowedAmbientRange = false;
                highBrightnessModeController.recalculateTimeAllowance();
            }
        });
        HdrClamper hdrClamper = this.mHdrClamper;
        hdrClamper.getClass();
        boolean z = i == 1;
        if (z != hdrClamper.mAutoBrightnessEnabled) {
            hdrClamper.mAutoBrightnessEnabled = z;
            hdrClamper.recalculateBrightnessCap(hdrClamper.mHdrBrightnessData, hdrClamper.mAmbientLux, hdrClamper.mHdrVisible);
        }
    }

    public final void updateHdrClamper(DisplayDeviceInfo displayDeviceInfo, IBinder iBinder, DisplayDeviceConfig displayDeviceConfig) {
        if (this.mUseHdrClamper) {
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig.getHighBrightnessModeData();
            float f = highBrightnessModeData == null ? -1.0f : highBrightnessModeData.minimumHdrPercentOfScreen;
            HdrBrightnessData hdrBrightnessData = displayDeviceConfig.mHdrBrightnessData;
            int i = displayDeviceInfo.width;
            int i2 = displayDeviceInfo.height;
            HdrClamper hdrClamper = this.mHdrClamper;
            hdrClamper.mHdrBrightnessData = hdrBrightnessData;
            float f2 = i * i2 * f;
            HdrClamper.HdrLayerInfoListener hdrLayerInfoListener = hdrClamper.mHdrListener;
            hdrLayerInfoListener.mHdrMinPixels = f2;
            IBinder iBinder2 = hdrClamper.mRegisteredDisplayToken;
            if (iBinder != iBinder2) {
                if (iBinder2 != null) {
                    hdrLayerInfoListener.unregister(iBinder2);
                    hdrClamper.mHdrVisible = false;
                    hdrClamper.mRegisteredDisplayToken = null;
                }
                if (iBinder != null && hdrLayerInfoListener.mHdrMinPixels >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    hdrLayerInfoListener.register(iBinder);
                    hdrClamper.mRegisteredDisplayToken = iBinder;
                }
            }
            hdrClamper.recalculateBrightnessCap(hdrBrightnessData, hdrClamper.mAmbientLux, hdrClamper.mHdrVisible);
        }
    }
}
