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
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessReason;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.power.PowerManagerUtil;

import java.io.PrintWriter;

public final class AutomaticBrightnessStrategy extends AutomaticBrightnessStrategy2
        implements DisplayBrightnessStrategy {
    public boolean mAppliedAutoBrightness;
    public boolean mAppliedTemporaryAutoBrightnessAdjustment;
    public float mAutoBrightnessAdjustment;
    public boolean mAutoBrightnessAdjustmentChanged;
    public int mAutoBrightnessAdjustmentReasonsFlags;
    public boolean mAutoBrightnessDisabledDueToDisplayOff;
    public AutomaticBrightnessController mAutomaticBrightnessController;
    public BrightnessConfiguration mBrightnessConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public final DisplayManagerFlags mDisplayManagerFlags;
    public boolean mGameAutoBrightnessLocked;
    public final Injector mInjector;
    public boolean mIsAutoBrightnessEnabled;
    public boolean mIsConfigured;
    public boolean mIsShortTermModelActive;
    public boolean mIsSlowChange;
    public float mPendingAutoBrightnessAdjustment;
    public boolean mShouldResetShortTermModel;
    public float mTemporaryAutoBrightnessAdjustment;
    public boolean mUseAutoBrightness;

    /* JADX INFO: Access modifiers changed from: package-private */
    public interface Injector {
        BrightnessEvent getBrightnessEvent(int i);
    }

    public final class RealInjector implements Injector {
        @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy.Injector
        public final BrightnessEvent getBrightnessEvent(int i) {
            return new BrightnessEvent(i);
        }
    }

    public AutomaticBrightnessStrategy(
            Context context, int i, Injector injector, DisplayManagerFlags displayManagerFlags) {
        super(context, i);
        this.mAutoBrightnessAdjustmentReasonsFlags = 0;
        this.mShouldResetShortTermModel = false;
        this.mAppliedAutoBrightness = false;
        this.mUseAutoBrightness = false;
        this.mIsAutoBrightnessEnabled = false;
        this.mIsShortTermModelActive = false;
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
        this.mDisplayManagerFlags = displayManagerFlags;
        this.mInjector = injector == null ? new RealInjector() : injector;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
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

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public void adjustAutomaticBrightnessStateIfValid(float f) {
        this.mAutoBrightnessAdjustmentReasonsFlags =
                this.mAppliedTemporaryAutoBrightnessAdjustment ? 1 : 2;
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

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2,
              // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void dump(PrintWriter printWriter) {
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

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public float getAutoBrightnessAdjustment() {
        return this.mAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean getAutoBrightnessAdjustmentChanged() {
        return this.mAutoBrightnessAdjustmentChanged;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final String getName() {
        return "AutomaticBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public float getPendingAutoBrightnessAdjustment() {
        return this.mPendingAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final int getReason() {
        return 4;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public float getTemporaryAutoBrightnessAdjustment() {
        return this.mTemporaryAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean hasAppliedAutoBrightness() {
        return this.mAppliedAutoBrightness;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean isAutoBrightnessDisabledDueToDisplayOff() {
        return this.mAutoBrightnessDisabledDueToDisplayOff;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean isAutoBrightnessEnabled() {
        return this.mIsAutoBrightnessEnabled;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean isGameAutoBrightnessLocked() {
        return this.mGameAutoBrightnessLocked;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean isShortTermModelActive() {
        return this.mIsShortTermModelActive;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean isTemporaryAutoBrightnessAdjustmentApplied() {
        return this.mAppliedTemporaryAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void processPendingAutoBrightnessAdjustments() {
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

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public void putAutoBrightnessAdjustmentSetting(float f) {
        if (this.mDisplayId == 0) {
            this.mAutoBrightnessAdjustment = f;
            Settings.System.putFloatForUser(
                    this.mContext.getContentResolver(), "screen_auto_brightness_adj", f, -2);
        }
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setAutoBrightnessApplied(boolean z) {
        this.mAppliedAutoBrightness = z;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setAutoBrightnessState(
            int i, boolean z, int i2, int i3, float f, boolean z2, boolean z3, boolean z4, int i4) {
        AutomaticBrightnessController automaticBrightnessController;
        boolean z5 = false;
        if (this.mDisplayManagerFlags.mAutoBrightnessModesFlagState.isEnabled()
                && (automaticBrightnessController = this.mAutomaticBrightnessController) != null
                && !automaticBrightnessController.isInIdleMode()) {
            this.mAutomaticBrightnessController.switchMode(Display.isDozeState(i) ? 2 : 0, false);
        }
        boolean z6 = (z || PowerManagerUtil.isFakeAodAvailable(i4)) && Display.isDozeState(i);
        boolean z7 = this.mUseAutoBrightness;
        boolean z8 =
                z7
                        && !((i != 2 && !z6)
                                || i2 == 6
                                || this.mAutomaticBrightnessController == null
                                || this.mGameAutoBrightnessLocked
                                || z3
                                || i2 == 10);
        this.mIsAutoBrightnessEnabled = z8;
        if (z7 && i != 2 && !z6) {
            z5 = true;
        }
        this.mAutoBrightnessDisabledDueToDisplayOff = z5;
        accommodateUserBrightnessChanges(
                z2, f, i3, i, this.mBrightnessConfiguration, z8 ? 1 : z5 ? 3 : 2, z4, i4);
        this.mIsConfigured = true;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setAutomaticBrightnessController(
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

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setBrightnessConfiguration(
            BrightnessConfiguration brightnessConfiguration, boolean z) {
        this.mBrightnessConfiguration = brightnessConfiguration;
        setShouldResetShortTermModel(z);
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public void setShouldResetShortTermModel(boolean z) {
        this.mShouldResetShortTermModel = z;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mTemporaryAutoBrightnessAdjustment = f;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void setUseAutoBrightness(boolean z) {
        this.mUseAutoBrightness = z;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public boolean shouldResetShortTermModel() {
        return this.mShouldResetShortTermModel;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final boolean shouldUseAutoBrightness() {
        return this.mUseAutoBrightness;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void strategySelectionPostProcessor(
            StrategySelectionNotifyRequest strategySelectionNotifyRequest) {
        if (!this.mIsConfigured) {
            setAutoBrightnessState(
                    strategySelectionNotifyRequest.mTargetDisplayState,
                    strategySelectionNotifyRequest.mAllowAutoBrightnessWhileDozingConfig,
                    strategySelectionNotifyRequest.mSelectedDisplayBrightnessStrategy.getReason(),
                    strategySelectionNotifyRequest.mDisplayPowerRequest.policy,
                    strategySelectionNotifyRequest.mLastUserSetScreenBrightness,
                    strategySelectionNotifyRequest.mUserSetBrightnessChanged,
                    false,
                    false,
                    -1);
        }
        this.mIsConfigured = false;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final DisplayBrightnessState updateBrightness(
            StrategyExecutionRequest strategyExecutionRequest) {
        BrightnessReason brightnessReason = new BrightnessReason();
        BrightnessEvent brightnessEvent = this.mInjector.getBrightnessEvent(this.mDisplayId);
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        float automaticScreenBrightness =
                automaticBrightnessController != null
                        ? automaticBrightnessController.getAutomaticScreenBrightness(
                                brightnessEvent)
                        : Float.NaN;
        brightnessReason.setReason(automaticScreenBrightness, 4);
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mBrightness = automaticScreenBrightness;
        builder.mSdrBrightness = automaticScreenBrightness;
        builder.mBrightnessReason = brightnessReason;
        builder.mDisplayBrightnessStrategyName = "AutomaticBrightnessStrategy";
        builder.mIsSlowChange = this.mIsSlowChange;
        builder.mBrightnessEvent = brightnessEvent;
        builder.mBrightnessAdjustmentFlag = this.mAutoBrightnessAdjustmentReasonsFlags;
        boolean z = true;
        builder.mShouldUpdateScreenBrightnessSetting =
                automaticScreenBrightness != strategyExecutionRequest.mCurrentScreenBrightness;
        if (!this.mAutoBrightnessAdjustmentChanged
                && !strategyExecutionRequest.mUserSetBrightnessChanged) {
            z = false;
        }
        builder.mIsUserInitiatedChange = z;
        return new DisplayBrightnessState(builder);
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void updateGameAutoBrightnessLock() {
        this.mGameAutoBrightnessLocked =
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "game_autobrightness_lock",
                                0,
                                -2)
                        != 0;
    }

    @Override // com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2
    public final void updatePendingAutoBrightnessAdjustments() {
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
