package com.android.server.display;

import android.hardware.display.DisplayManagerInternal;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import android.view.Display;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.DisplayPowerController;
import com.android.server.display.DisplayPowerState;
import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.DisplayBrightnessController;
import com.android.server.display.brightness.clamper.BrightnessClamperController;
import com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda0;
import com.android.server.display.brightness.clamper.HdrClamper;
import com.android.server.display.config.DisplayBrightnessMappingConfig;
import com.android.server.display.config.HdrBrightnessData;
import com.android.server.display.state.DisplayStateController;
import com.android.server.power.PowerManagerUtil;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayPowerController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DisplayPowerController$$ExternalSyntheticLambda7(DisplayPowerController displayPowerController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = displayPowerController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayPowerController displayPowerController = this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                displayPowerController.getClass();
                printWriter.println();
                printWriter.println("Display Power Controller Thread State:");
                printWriter.println("  mPowerRequest=" + displayPowerController.mPowerRequest);
                printWriter.println("  mBrightnessReason=" + displayPowerController.mBrightnessReason);
                printWriter.println("  mAppliedDimming=false");
                printWriter.println("  mAppliedThrottling=false");
                printWriter.println("  mDozing=" + displayPowerController.mDozing);
                printWriter.println("  mSkipRampState=RAMP_STATE_SKIP_NONE");
                StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mScreenOnBlockStartRealTime="), displayPowerController.mScreenOnBlockStartRealTime, printWriter, "  mScreenOffBlockStartRealTime="), displayPowerController.mScreenOffBlockStartRealTime, printWriter, "  mPendingScreenOnUnblocker=");
                m.append(displayPowerController.mPendingScreenOnUnblocker);
                printWriter.println(m.toString());
                printWriter.println("  mPendingScreenOffUnblocker=" + displayPowerController.mPendingScreenOffUnblocker);
                StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mPendingScreenOff="), displayPowerController.mPendingScreenOff, printWriter, "  mReportedToPolicy=");
                int i = displayPowerController.mReportedScreenStateToPolicy;
                m2.append(i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "REPORTED_TO_POLICY_SCREEN_ON" : "REPORTED_TO_POLICY_SCREEN_TURNING_ON" : "REPORTED_TO_POLICY_SCREEN_OFF");
                printWriter.println(m2.toString());
                printWriter.println("  mIsRbcActive=false");
                PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
                displayPowerController.mAutomaticBrightnessStrategy.dump(indentingPrintWriter);
                if (displayPowerController.mScreenBrightnessRampAnimator != null) {
                    printWriter.println("  mScreenBrightnessRampAnimator.isAnimating()=" + displayPowerController.mScreenBrightnessRampAnimator.isAnimating());
                }
                if (displayPowerController.mColorFadeOnAnimator != null) {
                    printWriter.println("  mColorFadeOnAnimator.isStarted()=" + displayPowerController.mColorFadeOnAnimator.isStarted());
                }
                if (displayPowerController.mColorFadeOffAnimator != null) {
                    printWriter.println("  mColorFadeOffAnimator.isStarted()=" + displayPowerController.mColorFadeOffAnimator.isStarted());
                }
                DisplayPowerState displayPowerState = displayPowerController.mPowerState;
                if (displayPowerState != null) {
                    printWriter.println();
                    printWriter.println("Display Power State:");
                    StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mStopped="), displayPowerState.mStopped, printWriter, "  mScreenState=");
                    m3.append(Display.stateToString(displayPowerState.mScreenState));
                    printWriter.println(m3.toString());
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mScreenBrightness="), displayPowerState.mScreenBrightness, printWriter, "  mSdrScreenBrightness="), displayPowerState.mSdrScreenBrightness, printWriter, "  mScreenReady="), displayPowerState.mScreenReady, printWriter, "  mScreenUpdatePending="), displayPowerState.mScreenUpdatePending, printWriter, "  mColorFadePrepared="), displayPowerState.mColorFadePrepared, printWriter, "  mColorFadeLevel="), displayPowerState.mColorFadeLevel, printWriter, "  mColorFadeReady="), displayPowerState.mColorFadeReady, printWriter, "  mColorFadeDrawPending="), displayPowerState.mColorFadeDrawPending, printWriter);
                    DisplayPowerState.PhotonicModulator photonicModulator = displayPowerState.mPhotonicModulator;
                    synchronized (photonicModulator.mLock) {
                        printWriter.println();
                        printWriter.println("Photonic Modulator State:");
                        printWriter.println("  mPendingState=" + Display.stateToString(photonicModulator.mPendingState));
                        printWriter.println("  mPendingBacklight=" + photonicModulator.mPendingBacklight);
                        printWriter.println("  mPendingSdrBacklight=" + photonicModulator.mPendingSdrBacklight);
                        printWriter.println("  mActualState=" + Display.stateToString(photonicModulator.mActualState));
                        printWriter.println("  mActualBacklight=" + photonicModulator.mActualBacklight);
                        printWriter.println("  mActualSdrBacklight=" + photonicModulator.mActualSdrBacklight);
                        printWriter.println("  mStateChangeInProgress=" + photonicModulator.mStateChangeInProgress);
                        printWriter.println("  mBacklightChangeInProgress=" + photonicModulator.mBacklightChangeInProgress);
                    }
                    ColorFade colorFade = displayPowerState.mColorFade;
                    if (colorFade != null) {
                        printWriter.println();
                        printWriter.println("Color Fade State:");
                        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mPrepared="), colorFade.mPrepared, printWriter, "  mMode="), colorFade.mMode, printWriter, "  mDisplayLayerStack="), colorFade.mDisplayLayerStack, printWriter, "  mDisplayWidth="), colorFade.mDisplayWidth, printWriter, "  mDisplayHeight="), colorFade.mDisplayHeight, printWriter, "  mSurfaceVisible="), colorFade.mSurfaceVisible, printWriter, "  mSurfaceAlpha="), colorFade.mSurfaceAlpha, printWriter);
                    }
                }
                AutomaticBrightnessController automaticBrightnessController = displayPowerController.mAutomaticBrightnessController;
                if (automaticBrightnessController != null) {
                    printWriter.println();
                    printWriter.println("Automatic Brightness Controller Configuration:");
                    float f = automaticBrightnessController.mScreenBrightnessRangeMinimum;
                    printWriter.println("  mScreenBrightnessRangeMinimum=".concat(PowerManagerUtil.brightnessToString(f)));
                    float f2 = automaticBrightnessController.mScreenBrightnessRangeMaximum;
                    printWriter.println("  mScreenBrightnessRangeMaximum=".concat(PowerManagerUtil.brightnessToString(f2)));
                    StringBuilder sb = new StringBuilder("  mState=");
                    int i2 = automaticBrightnessController.mState;
                    sb.append(i2 != 1 ? i2 != 2 ? i2 != 3 ? String.valueOf(i2) : "AUTO_BRIGHTNESS_OFF_DUE_TO_DISPLAY_STATE" : "AUTO_BRIGHTNESS_DISABLED" : "AUTO_BRIGHTNESS_ENABLED");
                    printWriter.println(sb.toString());
                    printWriter.println("  mScreenBrightnessRangeMinimum=" + f);
                    printWriter.println("  mScreenBrightnessRangeMaximum=" + f2);
                    StringBuilder m4 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mDozeScaleFactor="), automaticBrightnessController.mDozeScaleFactor, printWriter, "  mInitialLightSensorRate="), automaticBrightnessController.mInitialLightSensorRate, printWriter, "  mNormalLightSensorRate="), automaticBrightnessController.mNormalLightSensorRate, printWriter, "  mLightSensorWarmUpTimeConfig="), automaticBrightnessController.mLightSensorWarmUpTimeConfig, printWriter, "  mBrighteningLightDebounceConfig="), automaticBrightnessController.mBrighteningLightDebounceConfig, printWriter, "  mDarkeningLightDebounceConfig="), automaticBrightnessController.mDarkeningLightDebounceConfig, printWriter, "  mBrighteningLightDebounceConfigIdle="), automaticBrightnessController.mBrighteningLightDebounceConfigIdle, printWriter, "  mDarkeningLightDebounceConfigIdle="), automaticBrightnessController.mDarkeningLightDebounceConfigIdle, printWriter, "  mResetAmbientLuxAfterWarmUpConfig="), automaticBrightnessController.mResetAmbientLuxAfterWarmUpConfig, printWriter, "  mAmbientLightHorizonLong="), automaticBrightnessController.mAmbientLightHorizonLong, printWriter, "  mAmbientLightHorizonShort="), automaticBrightnessController.mAmbientLightHorizonShort, printWriter, "  mWeightingIntercept="), automaticBrightnessController.mWeightingIntercept, printWriter, "  SEC_FEATURE_SUPPORT_HBM="), PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM, printWriter, "  HBM_LUX=");
                    m4.append(PowerManagerUtil.HBM_LUX);
                    printWriter.println(m4.toString());
                    printWriter.println();
                    printWriter.println("Automatic Brightness Controller State:");
                    printWriter.println("  mLightSensor=" + automaticBrightnessController.mLightSensor);
                    printWriter.println("  mLightSensorCct=" + automaticBrightnessController.mLightSensorCct);
                    StringBuilder m5 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mLightSensorEnabled="), automaticBrightnessController.mLightSensorEnabled, printWriter, "  mLightSensorEnableTime=");
                    m5.append(TimeUtils.formatUptime(automaticBrightnessController.mLightSensorEnableTime));
                    printWriter.println(m5.toString());
                    StringBuilder m6 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mCurrentLightSensorRate="), automaticBrightnessController.mCurrentLightSensorRate, printWriter, "  mAmbientLux="), automaticBrightnessController.mAmbientLux, printWriter, "  mAmbientLuxValid="), automaticBrightnessController.mAmbientLuxValid, printWriter, "  mInjectedLuxEvent=");
                    m6.append(automaticBrightnessController.mInjectedLuxEvent);
                    printWriter.println(m6.toString());
                    StringBuilder m7 = KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mPreThesholdLux="), automaticBrightnessController.mPreThresholdLux, printWriter, "  mPreThesholdBrightness="), automaticBrightnessController.mPreThresholdBrightness, printWriter, "  mAmbientBrighteningThreshold="), automaticBrightnessController.mAmbientBrighteningThreshold, printWriter, "  mAmbientDarkeningThreshold=");
                    m7.append(automaticBrightnessController.mAmbientDarkeningThreshold);
                    printWriter.println(m7.toString());
                    printWriter.println("  mScreenBrighteningThreshold=0.0");
                    printWriter.println("  mScreenDarkeningThreshold=0.0");
                    StringBuilder m8 = KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mLastObservedLux="), automaticBrightnessController.mLastObservedLux, printWriter, "  mLastObservedLuxTime=");
                    m8.append(TimeUtils.formatUptime(automaticBrightnessController.mLastObservedLuxTime));
                    printWriter.println(m8.toString());
                    StringBuilder m9 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mRecentLightSamples="), automaticBrightnessController.mRecentLightSamples, printWriter, "  mAmbientLightRingBuffer=");
                    m9.append(automaticBrightnessController.mAmbientLightRingBuffer);
                    printWriter.println(m9.toString());
                    StringBuilder m10 = KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mScreenAutoBrightness="), automaticBrightnessController.mScreenAutoBrightness, printWriter, "  mDisplayPolicy=");
                    m10.append(DisplayManagerInternal.DisplayPowerRequest.policyToString(automaticBrightnessController.mDisplayPolicy));
                    printWriter.println(m10.toString());
                    printWriter.println("  mShortTermModel=");
                    AutomaticBrightnessController.ShortTermModel shortTermModel = automaticBrightnessController.mShortTermModel;
                    shortTermModel.getClass();
                    printWriter.println(shortTermModel);
                    printWriter.println("  mPausedShortTermModel=");
                    AutomaticBrightnessController.ShortTermModel shortTermModel2 = automaticBrightnessController.mPausedShortTermModel;
                    shortTermModel2.getClass();
                    printWriter.println(shortTermModel2);
                    printWriter.println();
                    StringBuilder m11 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, automaticBrightnessController.mPendingForegroundAppPackageName, "  mForegroundAppCategory=", BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, automaticBrightnessController.mForegroundAppPackageName, "  mPendingForegroundAppPackageName=", KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mBrightnessAdjustmentSamplePending="), automaticBrightnessController.mBrightnessAdjustmentSamplePending, printWriter, "  mBrightnessAdjustmentSampleOldLux="), automaticBrightnessController.mBrightnessAdjustmentSampleOldLux, printWriter, "  mBrightnessAdjustmentSampleOldBrightness="), automaticBrightnessController.mBrightnessAdjustmentSampleOldBrightness, printWriter, "  mForegroundAppPackageName="))), automaticBrightnessController.mForegroundAppCategory, printWriter, "  mPendingForegroundAppCategory="), automaticBrightnessController.mPendingForegroundAppCategory, printWriter, "  Current mode=");
                    m11.append(DisplayBrightnessMappingConfig.autoBrightnessModeToString(automaticBrightnessController.mCurrentBrightnessMapper.getMode()));
                    printWriter.println(m11.toString());
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mUseLightSensorBlockingPrevention="), automaticBrightnessController.mUseLightSensorBlockingPrevention, printWriter, "  mProximity="), automaticBrightnessController.mProximity, printWriter);
                    if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
                        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsRearLightSensor="), automaticBrightnessController.mIsRearLightSensor, printWriter, "  mLastAmbientLuxOfFrontLightSensor="), automaticBrightnessController.mLastAmbientLuxOfFrontLightSensor, printWriter);
                    }
                    for (int i3 = 0; i3 < automaticBrightnessController.mBrightnessMappingStrategyMap.size(); i3++) {
                        printWriter.println();
                        printWriter.println("  Mapper for mode " + DisplayBrightnessMappingConfig.autoBrightnessModeToString(automaticBrightnessController.mBrightnessMappingStrategyMap.keyAt(i3)) + ":");
                        BrightnessMappingStrategy brightnessMappingStrategy = (BrightnessMappingStrategy) automaticBrightnessController.mBrightnessMappingStrategyMap.valueAt(i3);
                        HighBrightnessModeController highBrightnessModeController = automaticBrightnessController.mBrightnessRangeController.mHbmController;
                        if (highBrightnessModeController.deviceSupportsHbm()) {
                            float f3 = highBrightnessModeController.mHbmData.transitionPoint;
                        }
                        brightnessMappingStrategy.dump(printWriter);
                    }
                    printWriter.println();
                    printWriter.println("  mAmbientBrightnessThresholds=" + automaticBrightnessController.mAmbientBrightnessThresholds);
                    printWriter.println("  mAmbientBrightnessThresholdsIdle=" + automaticBrightnessController.mAmbientBrightnessThresholdsIdle);
                    if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
                        printWriter.println("mAmbientBrightnessThresholdsTouchHigh:" + automaticBrightnessController.mAmbientBrightnessThresholdsTouchHigh);
                        printWriter.println("mAmbientBrightnessThresholdsTouchLow:" + automaticBrightnessController.mAmbientBrightnessThresholdsTouchLow);
                    }
                    int size = displayPowerController.mBrightnessEventRingBuffer.size();
                    if (size < 1) {
                        printWriter.println("No Automatic Brightness Adjustments");
                    } else {
                        ActiveServices$$ExternalSyntheticOutline0.m(size, printWriter, "Automatic Brightness Adjustments Last ", " Events: ");
                        BrightnessEvent[] brightnessEventArr = (BrightnessEvent[]) displayPowerController.mBrightnessEventRingBuffer.toArray();
                        for (int i4 = 0; i4 < displayPowerController.mBrightnessEventRingBuffer.size(); i4++) {
                            printWriter.println("  " + brightnessEventArr[i4].toString(true));
                        }
                    }
                }
                int size2 = displayPowerController.mRbcEventRingBuffer.size();
                if (size2 < 1) {
                    printWriter.println("No Reduce Bright Colors Adjustments");
                } else {
                    ActiveServices$$ExternalSyntheticOutline0.m(size2, printWriter, "Reduce Bright Colors Adjustments Last ", " Events: ");
                    BrightnessEvent[] brightnessEventArr2 = (BrightnessEvent[]) displayPowerController.mRbcEventRingBuffer.toArray();
                    for (int i5 = 0; i5 < displayPowerController.mRbcEventRingBuffer.size(); i5++) {
                        printWriter.println("  " + brightnessEventArr2[i5]);
                    }
                }
                ScreenOffBrightnessSensorController screenOffBrightnessSensorController = displayPowerController.mScreenOffBrightnessSensorController;
                if (screenOffBrightnessSensorController != null) {
                    screenOffBrightnessSensorController.dump(printWriter);
                }
                BrightnessRangeController brightnessRangeController = displayPowerController.mBrightnessRangeController;
                if (brightnessRangeController != null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "BrightnessRangeController:", "  mUseNormalBrightnessController="), brightnessRangeController.mUseNbmController, printWriter, "  mUseHdrClamper="), brightnessRangeController.mUseHdrClamper, printWriter);
                    HighBrightnessModeController highBrightnessModeController2 = brightnessRangeController.mHbmController;
                    highBrightnessModeController2.getClass();
                    highBrightnessModeController2.mHandler.runWithScissors(new HighBrightnessModeController$$ExternalSyntheticLambda1(highBrightnessModeController2, printWriter, 1), 1000L);
                    NormalBrightnessModeController normalBrightnessModeController = brightnessRangeController.mNormalBrightnessModeController;
                    normalBrightnessModeController.getClass();
                    printWriter.println("NormalBrightnessModeController:");
                    StringBuilder m12 = KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mAutoBrightnessEnabled="), normalBrightnessModeController.mAutoBrightnessEnabled, printWriter, "  mAmbientLux="), normalBrightnessModeController.mAmbientLux, printWriter, "  mMaxBrightness="), normalBrightnessModeController.mMaxBrightness, printWriter, "  mMaxBrightnessLimits=");
                    m12.append(normalBrightnessModeController.mMaxBrightnessLimits);
                    printWriter.println(m12.toString());
                    HdrClamper hdrClamper = brightnessRangeController.mHdrClamper;
                    hdrClamper.getClass();
                    printWriter.println("HdrClamper:");
                    StringBuilder m13 = KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(new StringBuilder("  mMaxBrightness="), hdrClamper.mMaxBrightness, printWriter, "  mDesiredMaxBrightness="), hdrClamper.mDesiredMaxBrightness, printWriter, "  mTransitionRate="), hdrClamper.mTransitionRate, printWriter, "  mDesiredTransitionRate="), hdrClamper.mDesiredTransitionRate, printWriter, "  mHdrVisible="), hdrClamper.mHdrVisible, printWriter, "  mHdrListener.mHdrMinPixels="), hdrClamper.mHdrListener.mHdrMinPixels, printWriter, "  mHdrBrightnessData=");
                    HdrBrightnessData hdrBrightnessData = hdrClamper.mHdrBrightnessData;
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, hdrBrightnessData == null ? "null" : hdrBrightnessData.toString(), "  mHdrListener registered=", m13), hdrClamper.mRegisteredDisplayToken != null, printWriter, "  mAmbientLux="), hdrClamper.mAmbientLux, printWriter, "  mAutoBrightnessEnabled="), hdrClamper.mAutoBrightnessEnabled, printWriter);
                }
                BrightnessThrottler brightnessThrottler = displayPowerController.mBrightnessThrottler;
                if (brightnessThrottler != null) {
                    brightnessThrottler.mHandler.runWithScissors(new BrightnessThrottler$$ExternalSyntheticLambda2(0, brightnessThrottler, printWriter), 1000L);
                }
                printWriter.println();
                printWriter.println();
                WakelockController wakelockController = displayPowerController.mWakelockController;
                if (wakelockController != null) {
                    StringBuilder m14 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "WakelockController State:", "  mDisplayId="), wakelockController.mDisplayId, printWriter, "  mUnfinishedBusiness=");
                    m14.append(wakelockController.hasUnfinishedBusiness());
                    printWriter.println(m14.toString());
                    printWriter.println("  mOnStateChangePending=" + wakelockController.isOnStateChangedPending());
                    printWriter.println("  mOnProximityPositiveMessages=" + wakelockController.isProximityPositiveAcquired());
                    printWriter.println("  mOnProximityNegativeMessages=" + wakelockController.isProximityNegativeAcquired());
                    printWriter.println("  mIsRefreshRateRequested=" + wakelockController.isRefreshRateRequested());
                    printWriter.println("  mIsEarlyWakeUpRequested=" + wakelockController.isEarlyWakeupRequested());
                }
                printWriter.println();
                DisplayBrightnessController displayBrightnessController = displayPowerController.mDisplayBrightnessController;
                if (displayBrightnessController != null) {
                    printWriter.println();
                    printWriter.println("DisplayBrightnessController:");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mDisplayId=: "), displayBrightnessController.mDisplayId, printWriter, "  mScreenBrightnessDefault="), displayBrightnessController.mScreenBrightnessDefault, printWriter, "  mPersistBrightnessNitsForDefaultDisplay="), displayBrightnessController.mPersistBrightnessNitsForDefaultDisplay, printWriter);
                    synchronized (displayBrightnessController.mLock) {
                        try {
                            printWriter.println("  mPendingScreenBrightness=" + displayBrightnessController.mPendingScreenBrightness);
                            printWriter.println("  mCurrentScreenBrightness=" + displayBrightnessController.mCurrentScreenBrightness);
                            printWriter.println("  mLastUserSetScreenBrightness=" + displayBrightnessController.mLastUserSetScreenBrightness);
                            if (displayBrightnessController.mDisplayBrightnessStrategy != null) {
                                printWriter.println("  Last selected DisplayBrightnessStrategy= " + displayBrightnessController.mDisplayBrightnessStrategy.getName());
                            }
                            displayBrightnessController.mDisplayBrightnessStrategySelector.dump(new IndentingPrintWriter(printWriter, " "));
                        } finally {
                        }
                    }
                }
                printWriter.println();
                DisplayStateController displayStateController = displayPowerController.mDisplayStateController;
                if (displayStateController != null) {
                    displayStateController.getClass();
                    printWriter.println();
                    printWriter.println("DisplayStateController:");
                    StringBuilder m15 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mPerformScreenOffTransition:"), displayStateController.mPerformScreenOffTransition, printWriter, "  mDozeStateOverride=");
                    m15.append(displayStateController.mDozeStateOverride);
                    printWriter.println(m15.toString());
                    IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(printWriter, " ");
                    DisplayPowerProximityStateController displayPowerProximityStateController = displayStateController.mDisplayPowerProximityStateController;
                    if (displayPowerProximityStateController != null) {
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("DisplayPowerProximityStateController:");
                        synchronized (displayPowerProximityStateController.mLock) {
                            indentingPrintWriter2.println("  mPendingWaitForNegativeProximityLocked=" + displayPowerProximityStateController.mPendingWaitForNegativeProximityLocked);
                        }
                        indentingPrintWriter2.println("  mDisplayId=" + displayPowerProximityStateController.mDisplayId);
                        indentingPrintWriter2.println("  mWaitingForNegativeProximity=" + displayPowerProximityStateController.mWaitingForNegativeProximity);
                        indentingPrintWriter2.println("  mIgnoreProximityUntilChanged=" + displayPowerProximityStateController.mIgnoreProximityUntilChanged);
                        indentingPrintWriter2.println("  mProximitySensor=" + displayPowerProximityStateController.mProximitySensor);
                        indentingPrintWriter2.println("  mProximitySensorEnabled=" + displayPowerProximityStateController.mProximitySensorEnabled);
                        indentingPrintWriter2.println("  mProximityThreshold=" + displayPowerProximityStateController.mProximityThreshold);
                        StringBuilder sb2 = new StringBuilder("  mProximity=");
                        int i6 = displayPowerProximityStateController.mProximity;
                        sb2.append(i6 != -1 ? i6 != 0 ? i6 != 1 ? Integer.toString(i6) : "Positive" : "Negative" : "Unknown");
                        indentingPrintWriter2.println(sb2.toString());
                        StringBuilder sb3 = new StringBuilder("  mPendingProximity=");
                        int i7 = displayPowerProximityStateController.mPendingProximity;
                        sb3.append(i7 != -1 ? i7 != 0 ? i7 != 1 ? Integer.toString(i7) : "Positive" : "Negative" : "Unknown");
                        indentingPrintWriter2.println(sb3.toString());
                        indentingPrintWriter2.println("  mPendingProximityDebounceTime=" + TimeUtils.formatUptime(displayPowerProximityStateController.mPendingProximityDebounceTime));
                        indentingPrintWriter2.println("  mScreenOffBecauseOfProximity=" + displayPowerProximityStateController.mScreenOffBecauseOfProximity);
                        indentingPrintWriter2.println("  mSkipRampBecauseOfProximityChangeToNegative=" + displayPowerProximityStateController.mSkipRampBecauseOfProximityChangeToNegative);
                    }
                }
                if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL && displayPowerController.mAdaptiveBrightnessLongtermModelBuilder != null) {
                    printWriter.println();
                    displayPowerController.mAdaptiveBrightnessLongtermModelBuilder.dump(printWriter);
                }
                if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("  mDualScreenPolicy="), displayPowerController.mDualScreenPolicy, printWriter);
                }
                printWriter.println("  SEC_FEATURE_EARLY_WAKEUP=true");
                EarlyWakeUpManager earlyWakeUpManager = displayPowerController.mEarlyWakeUpManager;
                if (earlyWakeUpManager != null) {
                    synchronized (earlyWakeUpManager.mEarlyWakeUpLock) {
                        printWriter.println();
                        printWriter.println("[ew] EarlyWakeUpManager:");
                        printWriter.println("  mAppliedLocked=" + earlyWakeUpManager.mAppliedLocked);
                        printWriter.println("  mEarlyLightSensorEnabled=" + earlyWakeUpManager.mEarlyLightSensorEnabled);
                        printWriter.println("  mEarlyDisplayEnabled=" + earlyWakeUpManager.mEarlyDisplayEnabled);
                        printWriter.println("  mLastEnableRequestedTime=" + earlyWakeUpManager.mLastEnableRequestedTime);
                        printWriter.println("  now=" + SystemClock.uptimeMillis());
                        printWriter.println("  mHoldingSuspendBlocker=" + earlyWakeUpManager.mHoldingSuspendBlocker);
                        printWriter.println("    mEarlyLightSensorReadyLocked=" + earlyWakeUpManager.mEarlyLightSensorReadyLocked);
                        printWriter.println("    mEarlyDisplayReadyLocked=" + earlyWakeUpManager.mEarlyDisplayReadyLocked);
                    }
                }
                printWriter.println();
                printWriter.println("  USE_LONG_RAMP_RATE_FOR_NON_HBM=true");
                printWriter.println();
                BrightnessClamperController brightnessClamperController = displayPowerController.mBrightnessClamperController;
                if (brightnessClamperController != null) {
                    indentingPrintWriter.println("BrightnessClamperController:");
                    indentingPrintWriter.println("  mBrightnessCap: " + brightnessClamperController.mBrightnessCap);
                    indentingPrintWriter.println("  mClamperType: " + brightnessClamperController.mClamperType);
                    indentingPrintWriter.println("  mClamperApplied: " + brightnessClamperController.mClamperApplied);
                    indentingPrintWriter.println("  mLightSensor=" + brightnessClamperController.mLightSensor);
                    indentingPrintWriter.println("  mRegisteredLightSensor=" + brightnessClamperController.mRegisteredLightSensor);
                    IndentingPrintWriter indentingPrintWriter3 = new IndentingPrintWriter(indentingPrintWriter, "    ");
                    ((ArrayList) brightnessClamperController.mClampers).forEach(new BrightnessClamperController$$ExternalSyntheticLambda0(0, indentingPrintWriter3));
                    ((ArrayList) brightnessClamperController.mModifiers).forEach(new BrightnessClamperController$$ExternalSyntheticLambda0(1, indentingPrintWriter3));
                }
                printWriter.println("  SEC_FEATURE_BRIGHTNESS_CONTROL_BY_EXTRA_DIM=false");
                return;
            default:
                DisplayPowerController displayPowerController2 = this.f$0;
                DisplayManagerInternal.DisplayOffloadSession displayOffloadSession = (DisplayManagerInternal.DisplayOffloadSession) this.f$1;
                DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController2.mHandler;
                displayControllerHandler.sendMessage(displayControllerHandler.obtainMessage(18, displayOffloadSession));
                return;
        }
    }
}
