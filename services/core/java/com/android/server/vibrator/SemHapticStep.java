package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.server.vibrator.CommonPatternInfo;
import com.samsung.android.vibrator.VibRune;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemHapticStep extends AbstractVibratorStep {
    public long mNextOffTime;

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final boolean acceptVibratorCompleteCallback(int i) {
        if (this.controller.mVibratorInfo.getId() == i) {
            this.mVibratorCompleteCallbackReceived = true;
            this.mNextOffTime = SystemClock.uptimeMillis();
        }
        return this.mNextOffTime < this.startTime
                && this.controller.mCurrentAmplitude
                        > FullScreenMagnificationGestureHandler.MAX_SCALE;
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "SemHapticStep");
        try {
            SemHapticSegment semHapticSegment =
                    (VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
            int i = 1;
            if (!(semHapticSegment instanceof SemHapticSegment)) {
                Slog.w(
                        "SemHapticStep",
                        "Ignoring wrong segment for a SemHapticSegment: " + semHapticSegment);
                return nextSteps(1);
            }
            HalVibration halVibration = this.conductor.mVibration;
            int i2 = halVibration.mMagnitude;
            int sepIndex = semHapticSegment.getSepIndex();
            long j = halVibration.mTimeout;
            boolean z = this.effect.getRepeatIndex() > -1;
            Slog.d(
                    "SemHapticStep",
                    "sepIndex="
                            + sepIndex
                            + ", intensity="
                            + i2
                            + ", repeat="
                            + z
                            + ", duration="
                            + j);
            startVibrating(sepIndex, i2, z, j);
            if (z && halVibration.mEngineData == null) {
                this.mNextOffTime = 9223372036854770807L;
                i = -1;
            } else {
                this.mNextOffTime = this.startTime + j;
            }
            return nextSteps(i, this.mNextOffTime);
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final void startVibrating(int i, int i2, boolean z, long j) {
        CommonPatternInfo[] commonPatternInfoArr;
        StringBuilder sb = new StringBuilder("Turning on vibrator ");
        sb.append(this.controller.mVibratorInfo.getId());
        sb.append(" for ");
        sb.append(j);
        BootReceiver$$ExternalSyntheticOutline0.m(sb, "ms", "SemHapticStep");
        HalVibration halVibration = this.conductor.mVibration;
        if (!VibRune.SUPPORT_HYBRID_HAPTIC()
                || (commonPatternInfoArr = halVibration.mCommonData) == null) {
            int[] iArr = halVibration.mEngineData;
            if (iArr == null) {
                VibratorController vibratorController = this.controller;
                vibratorController.mNativeWrapper.performPrebakedHapticPattern(i, i2, z);
                vibratorController.notifyListenerOnVibrating(true);
                return;
            } else {
                VibratorController vibratorController2 = this.controller;
                vibratorController2.mNativeWrapper.performHapticEngine((int[]) iArr.clone(), i2);
                vibratorController2.notifyListenerOnVibrating(true);
                return;
            }
        }
        int[] iArr2 = new int[(commonPatternInfoArr.length * 5) + 1];
        int i3 = 0;
        iArr2[0] = commonPatternInfoArr.length;
        int i4 = 1;
        while (true) {
            CommonPatternInfo[] commonPatternInfoArr2 = halVibration.mCommonData;
            if (i3 >= commonPatternInfoArr2.length) {
                VibratorController vibratorController3 = this.controller;
                vibratorController3.mNativeWrapper.performCommonInputff(iArr2, z, i2);
                vibratorController3.notifyListenerOnVibrating(true);
                return;
            }
            CommonPatternInfo commonPatternInfo = commonPatternInfoArr2[i3];
            iArr2[i4] = commonPatternInfo.type;
            iArr2[i4 + 1] = commonPatternInfo.index;
            iArr2[i4 + 2] = commonPatternInfo.scale;
            int i5 = i4 + 4;
            iArr2[i4 + 3] = commonPatternInfo.duration;
            i4 += 5;
            iArr2[i5] = commonPatternInfo.frequency;
            i3++;
        }
    }
}
