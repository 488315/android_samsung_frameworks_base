package com.android.server.display;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManagerInternal;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.Display;

import com.android.server.display.state.DisplayStateController;
import com.android.server.display.utils.DebugUtils;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayOffloadSessionImpl
        implements DisplayManagerInternal.DisplayOffloadSession {
    public static final boolean DEBUG = DebugUtils.isDebuggable("DisplayOffloadSessionImpl");
    public final DisplayManagerInternal.DisplayOffloader mDisplayOffloader;
    public final DisplayPowerControllerInterface mDisplayPowerController;
    public boolean mIsActive;

    public DisplayOffloadSessionImpl(
            DisplayManagerInternal.DisplayOffloader displayOffloader,
            DisplayPowerControllerInterface displayPowerControllerInterface) {
        this.mDisplayOffloader = displayOffloader;
        this.mDisplayPowerController = displayPowerControllerInterface;
    }

    public final boolean allowAutoBrightnessInDoze() {
        DisplayManagerInternal.DisplayOffloader displayOffloader = this.mDisplayOffloader;
        if (displayOffloader == null) {
            return false;
        }
        return displayOffloader.allowAutoBrightnessInDoze();
    }

    public final boolean blockScreenOn(Runnable runnable) {
        DisplayManagerInternal.DisplayOffloader displayOffloader = this.mDisplayOffloader;
        if (displayOffloader == null) {
            return false;
        }
        displayOffloader.onBlockingScreenOn(runnable);
        return true;
    }

    public final float[] getAutoBrightnessLevels(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Unknown auto-brightness mode: "));
        }
        DisplayPowerController displayPowerController =
                (DisplayPowerController) this.mDisplayPowerController;
        return displayPowerController.mDisplayDeviceConfig.getAutoBrightnessBrighteningLevels(
                i,
                Settings.System.getIntForUser(
                        displayPowerController.mContext.getContentResolver(),
                        "screen_brightness_for_als",
                        2,
                        -2));
    }

    public final float[] getAutoBrightnessLuxLevels(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Unknown auto-brightness mode: "));
        }
        DisplayPowerController displayPowerController =
                (DisplayPowerController) this.mDisplayPowerController;
        return displayPowerController.mDisplayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(
                i,
                Settings.System.getIntForUser(
                        displayPowerController.mContext.getContentResolver(),
                        "screen_brightness_for_als",
                        2,
                        -2));
    }

    public final float getBrightness() {
        return ((DisplayPowerController) this.mDisplayPowerController)
                .mDisplayBrightnessController.getScreenBrightnessSetting();
    }

    public final float getDozeBrightness() {
        DisplayPowerController displayPowerController =
                (DisplayPowerController) this.mDisplayPowerController;
        return displayPowerController.mDisplayBrightnessController.getCurrentBrightness()
                * displayPowerController.mDozeScaleFactor;
    }

    public final boolean isActive() {
        return this.mIsActive;
    }

    public final void setDozeStateOverride(final int i) {
        final DisplayPowerController displayPowerController =
                (DisplayPowerController) this.mDisplayPowerController;
        displayPowerController.getClass();
        Slog.i("DisplayPowerController2", "New offload doze override: " + Display.stateToString(i));
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda9
                    public final /* synthetic */ int f$2 = 3;

                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayPowerController displayPowerController2 =
                                DisplayPowerController.this;
                        int i2 = i;
                        int i3 = this.f$2;
                        if (displayPowerController2.mDisplayOffloadSession != null) {
                            if (DisplayManagerInternal.DisplayOffloadSession
                                            .isSupportedOffloadState(i2)
                                    || i2 == 0) {
                                DisplayStateController displayStateController =
                                        displayPowerController2.mDisplayStateController;
                                displayStateController.mDozeStateOverride = i2;
                                displayStateController.mDozeStateOverrideReason = i3;
                                displayPowerController2.updatePowerState();
                            }
                        }
                    }
                };
        displayPowerController.mClock.getClass();
        displayPowerController.mHandler.postAtTime(runnable, SystemClock.uptimeMillis());
    }

    public final void updateBrightness(float f) {
        if (this.mIsActive) {
            DisplayPowerController displayPowerController =
                    (DisplayPowerController) this.mDisplayPowerController;
            displayPowerController.getClass();
            int floatToIntBits = Float.floatToIntBits(f);
            DisplayPowerController.DisplayControllerHandler displayControllerHandler =
                    displayPowerController.mHandler;
            Message obtainMessage = displayControllerHandler.obtainMessage(17, floatToIntBits, 0);
            displayPowerController.mClock.getClass();
            displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
        }
    }
}
