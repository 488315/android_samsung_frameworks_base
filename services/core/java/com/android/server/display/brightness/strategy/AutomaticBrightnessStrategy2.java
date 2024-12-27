package com.android.server.display.brightness.strategy;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.display.BrightnessConfiguration;
import android.provider.Settings;
import android.util.MathUtils;
import android.view.Display;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessUtils;

import java.io.PrintWriter;

public class AutomaticBrightnessStrategy2 {
    public boolean mAppliedTemporaryAutoBrightnessAdjustment;
    public float mAutoBrightnessAdjustment;
    public boolean mAutoBrightnessAdjustmentChanged;
    public boolean mAutoBrightnessDisabledDueToDisplayOff;
    public AutomaticBrightnessController mAutomaticBrightnessController;
    public BrightnessConfiguration mBrightnessConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public boolean mGameAutoBrightnessLocked;
    public float mPendingAutoBrightnessAdjustment;
    public float mTemporaryAutoBrightnessAdjustment;
    public int mAutoBrightnessAdjustmentReasonsFlags = 0;
    public boolean mShouldResetShortTermModel = false;
    public boolean mAppliedAutoBrightness = false;
    public boolean mUseAutoBrightness = false;
    public boolean mIsAutoBrightnessEnabled = false;
    public boolean mIsShortTermModelActive = false;

    public AutomaticBrightnessStrategy2(Context context, int i) {
        this.mContext = context;
        this.mDisplayId = i;
        ContentResolver contentResolver = context.getContentResolver();
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float floatForUser =
                Settings.System.getFloatForUser(
                        contentResolver,
                        "screen_auto_brightness_adj",
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        -2);
        if (!Float.isNaN(floatForUser)) {
            float f2 = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
            f = MathUtils.constrain(floatForUser, -1.0f, 1.0f);
        }
        this.mAutoBrightnessAdjustment = f;
        this.mPendingAutoBrightnessAdjustment = Float.NaN;
        this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
    }

    public void accommodateUserBrightnessChanges(
            boolean z,
            float f,
            int i,
            int i2,
            BrightnessConfiguration brightnessConfiguration,
            int i3,
            boolean z2,
            int i4) {
        processPendingAutoBrightnessAdjustments();
        boolean z3 = !Float.isNaN(this.mTemporaryAutoBrightnessAdjustment);
        this.mAppliedTemporaryAutoBrightnessAdjustment = z3;
        float f2 = z3 ? this.mTemporaryAutoBrightnessAdjustment : this.mAutoBrightnessAdjustment;
        this.mIsShortTermModelActive = false;
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        if (automaticBrightnessController != null) {
            automaticBrightnessController.configure(
                    i3,
                    brightnessConfiguration,
                    f,
                    z,
                    f2,
                    this.mAutoBrightnessAdjustmentChanged,
                    i,
                    i2,
                    this.mShouldResetShortTermModel,
                    z2,
                    i4);
            this.mShouldResetShortTermModel = false;
            this.mIsShortTermModelActive =
                    this.mAutomaticBrightnessController.mCurrentBrightnessMapper
                            .hasUserDataPoints();
        }
    }

    public void adjustAutomaticBrightnessStateIfValid(float f) {
        this.mAutoBrightnessAdjustmentReasonsFlags =
                isTemporaryAutoBrightnessAdjustmentApplied() ? 1 : 2;
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        float autoBrightnessAdjustment =
                automaticBrightnessController != null
                        ? automaticBrightnessController.mCurrentBrightnessMapper
                                .getAutoBrightnessAdjustment()
                        : FullScreenMagnificationGestureHandler.MAX_SCALE;
        if (Float.isNaN(autoBrightnessAdjustment)
                || this.mAutoBrightnessAdjustment == autoBrightnessAdjustment) {
            this.mAutoBrightnessAdjustmentReasonsFlags = 0;
        } else {
            putAutoBrightnessAdjustmentSetting(autoBrightnessAdjustment);
        }
    }

    public void dump(PrintWriter printWriter) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                KillPolicyManager$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                KillPolicyManager$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                KillPolicyManager$$ExternalSyntheticOutline0
                                                                                                                        .m(
                                                                                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                                        .m(
                                                                                                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                                                        .m$1(
                                                                                                                                                                printWriter,
                                                                                                                                                                "AutomaticBrightnessStrategy:",
                                                                                                                                                                "  mDisplayId="),
                                                                                                                                                this
                                                                                                                                                        .mDisplayId,
                                                                                                                                                printWriter,
                                                                                                                                                "  mAutoBrightnessAdjustment="),
                                                                                                                                this
                                                                                                                                        .mAutoBrightnessAdjustment,
                                                                                                                                printWriter,
                                                                                                                                "  mPendingAutoBrightnessAdjustment="),
                                                                                                                this
                                                                                                                        .mPendingAutoBrightnessAdjustment,
                                                                                                                printWriter,
                                                                                                                "  mTemporaryAutoBrightnessAdjustment="),
                                                                                                this
                                                                                                        .mTemporaryAutoBrightnessAdjustment,
                                                                                                printWriter,
                                                                                                "  mShouldResetShortTermModel="),
                                                                                this
                                                                                        .mShouldResetShortTermModel,
                                                                                printWriter,
                                                                                "  mAppliedAutoBrightness="),
                                                                this.mAppliedAutoBrightness,
                                                                printWriter,
                                                                "  mAutoBrightnessAdjustmentChanged="),
                                                this.mAutoBrightnessAdjustmentChanged,
                                                printWriter,
                                                "  mAppliedTemporaryAutoBrightnessAdjustment="),
                                        this.mAppliedTemporaryAutoBrightnessAdjustment,
                                        printWriter,
                                        "  mUseAutoBrightness="),
                                this.mUseAutoBrightness,
                                printWriter,
                                "  mWasShortTermModelActive="),
                        this.mIsShortTermModelActive,
                        printWriter,
                        "  mAutoBrightnessAdjustmentReasonsFlags="),
                this.mAutoBrightnessAdjustmentReasonsFlags,
                printWriter);
    }

    public float getAutoBrightnessAdjustment() {
        return this.mAutoBrightnessAdjustment;
    }

    public boolean getAutoBrightnessAdjustmentChanged() {
        return this.mAutoBrightnessAdjustmentChanged;
    }

    public final int getAutoBrightnessAdjustmentReasonsFlags() {
        return this.mAutoBrightnessAdjustmentReasonsFlags;
    }

    public final float getAutomaticScreenBrightness(BrightnessEvent brightnessEvent) {
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        float automaticScreenBrightness =
                automaticBrightnessController != null
                        ? automaticBrightnessController.getAutomaticScreenBrightness(
                                brightnessEvent)
                        : Float.NaN;
        adjustAutomaticBrightnessStateIfValid(automaticScreenBrightness);
        return automaticScreenBrightness;
    }

    public float getPendingAutoBrightnessAdjustment() {
        return this.mPendingAutoBrightnessAdjustment;
    }

    public float getTemporaryAutoBrightnessAdjustment() {
        return this.mTemporaryAutoBrightnessAdjustment;
    }

    public boolean hasAppliedAutoBrightness() {
        return this.mAppliedAutoBrightness;
    }

    public boolean isAutoBrightnessDisabledDueToDisplayOff() {
        return this.mAutoBrightnessDisabledDueToDisplayOff;
    }

    public boolean isAutoBrightnessEnabled() {
        return this.mIsAutoBrightnessEnabled;
    }

    public boolean isGameAutoBrightnessLocked() {
        return this.mGameAutoBrightnessLocked;
    }

    public boolean isShortTermModelActive() {
        return this.mIsShortTermModelActive;
    }

    public boolean isTemporaryAutoBrightnessAdjustmentApplied() {
        return this.mAppliedTemporaryAutoBrightnessAdjustment;
    }

    public void processPendingAutoBrightnessAdjustments() {
        this.mAutoBrightnessAdjustmentChanged = false;
        if (Float.isNaN(this.mPendingAutoBrightnessAdjustment)) {
            return;
        }
        float f = this.mAutoBrightnessAdjustment;
        float f2 = this.mPendingAutoBrightnessAdjustment;
        if (f == f2) {
            this.mPendingAutoBrightnessAdjustment = Float.NaN;
            return;
        }
        this.mAutoBrightnessAdjustment = f2;
        this.mPendingAutoBrightnessAdjustment = Float.NaN;
        this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
        this.mAutoBrightnessAdjustmentChanged = true;
    }

    public void putAutoBrightnessAdjustmentSetting(float f) {
        if (this.mDisplayId == 0) {
            this.mAutoBrightnessAdjustment = f;
            Settings.System.putFloatForUser(
                    this.mContext.getContentResolver(), "screen_auto_brightness_adj", f, -2);
        }
    }

    public void setAutoBrightnessApplied(boolean z) {
        this.mAppliedAutoBrightness = z;
    }

    public void setAutoBrightnessState(
            int i, boolean z, int i2, int i3, float f, boolean z2, boolean z3, boolean z4, int i4) {
        boolean z5 = false;
        boolean z6 = z && Display.isDozeState(i);
        this.mIsAutoBrightnessEnabled =
                shouldUseAutoBrightness()
                        && !((i != 2 && !z6)
                                || i2 == 6
                                || this.mAutomaticBrightnessController == null
                                || this.mGameAutoBrightnessLocked
                                || z3
                                || i2 == 10);
        if (shouldUseAutoBrightness() && i != 2 && !z6) {
            z5 = true;
        }
        this.mAutoBrightnessDisabledDueToDisplayOff = z5;
        accommodateUserBrightnessChanges(
                z2,
                f,
                i3,
                i,
                this.mBrightnessConfiguration,
                this.mIsAutoBrightnessEnabled ? 1 : z5 ? 3 : 2,
                z4,
                i4);
    }

    public void setAutomaticBrightnessController(
            AutomaticBrightnessController automaticBrightnessController) {
        AutomaticBrightnessController automaticBrightnessController2 =
                this.mAutomaticBrightnessController;
        if (automaticBrightnessController == automaticBrightnessController2) {
            return;
        }
        if (automaticBrightnessController2 != null) {
            automaticBrightnessController2.setLightSensorEnabled(false);
        }
        this.mAutomaticBrightnessController = automaticBrightnessController;
    }

    public void setBrightnessConfiguration(
            BrightnessConfiguration brightnessConfiguration, boolean z) {
        this.mBrightnessConfiguration = brightnessConfiguration;
        setShouldResetShortTermModel(z);
    }

    public void setShouldResetShortTermModel(boolean z) {
        this.mShouldResetShortTermModel = z;
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mTemporaryAutoBrightnessAdjustment = f;
    }

    public void setUseAutoBrightness(boolean z) {
        this.mUseAutoBrightness = z;
    }

    public boolean shouldResetShortTermModel() {
        return this.mShouldResetShortTermModel;
    }

    public boolean shouldUseAutoBrightness() {
        return this.mUseAutoBrightness;
    }

    public void updateGameAutoBrightnessLock() {
        this.mGameAutoBrightnessLocked =
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "game_autobrightness_lock",
                                0,
                                -2)
                        != 0;
    }

    public void updatePendingAutoBrightnessAdjustments() {
        float constrain;
        float floatForUser =
                Settings.System.getFloatForUser(
                        this.mContext.getContentResolver(),
                        "screen_auto_brightness_adj",
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        -2);
        if (Float.isNaN(floatForUser)) {
            constrain = Float.NaN;
        } else {
            float f = BrightnessUtils.sScreenExtendedBrightnessRangeMaximum;
            constrain = MathUtils.constrain(floatForUser, -1.0f, 1.0f);
        }
        this.mPendingAutoBrightnessAdjustment = constrain;
    }
}
