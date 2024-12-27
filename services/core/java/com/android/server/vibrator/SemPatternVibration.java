package com.android.server.vibrator;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;

import com.samsung.android.server.vibrator.VibratorHelper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemPatternVibration extends SemVibration {
    public boolean mHasEngineData;
    public String mIsExecutablePkg;

    @Override // com.android.server.vibrator.SemVibration
    public final HalVibration getVibration() {
        int[] iArr;
        long[] patternByIndex;
        int i;
        long j;
        int i2;
        int i3;
        int[] iArr2;
        int i4 = 0;
        int i5 = 1;
        boolean z = VibratorHelper.sIsHapticEngineSupported;
        int i6 = this.mRepeat;
        int i7 = this.mIndex;
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        if (z) {
            int[] engineData = vibratorHelper.getEngineData(i7);
            if (engineData.length != 2 || engineData[0] >= 0) {
                this.mHasEngineData = true;
                if (engineData.length == 5
                        && engineData[0] == 4
                        && engineData[1] == 0
                        && engineData[2] == 0
                        && engineData[3] == 0
                        && engineData[4] == 0) {
                    Slog.d("VibratorManagerService", "Empty pattern for haptic engine.");
                } else {
                    int length = engineData.length;
                    int i8 = length - 1;
                    if (length <= 4 || (i8 / 4) * 4 != i8) {
                        while (i4 < length) {
                            StringBuilder m =
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            i4,
                                            "isColorfulDataFormat() - wrong format(1) : data[",
                                            "] = ");
                            m.append(engineData[i4]);
                            Slog.e("VibratorManagerService", m.toString());
                            i4++;
                        }
                    } else if (i8 == engineData[0]) {
                        if (i7 == -1) {
                            int length2 = engineData.length;
                            if (engineData.length <= 4) {
                                patternByIndex = new long[] {-1, -1};
                                iArr = engineData;
                            } else {
                                long[] jArr = new long[((length2 - 1) / 4) + 1];
                                jArr[0] = 0;
                                if (engineData[2] != 0) {
                                    i3 = 0;
                                    i2 = 1;
                                } else {
                                    i2 = 0;
                                    i3 = 1;
                                }
                                int i9 = 1;
                                while (i9 < length2) {
                                    int i10 = i9 + 1;
                                    if (i10 < length2) {
                                        if (i3 != 0 && engineData[i10] != 0) {
                                            i2 += i5;
                                            i3 = 0;
                                        } else if (i3 == 0 && engineData[i10] == 0) {
                                            i2 += i5;
                                            i3 = i5;
                                        }
                                        iArr2 = engineData;
                                        jArr[i2] = jArr[i2] + engineData[i9];
                                    } else {
                                        iArr2 = engineData;
                                    }
                                    i9 += 4;
                                    engineData = iArr2;
                                    i5 = 1;
                                }
                                iArr = engineData;
                                int i11 = i2 + i5;
                                patternByIndex = new long[i11];
                                System.arraycopy(jArr, 0, patternByIndex, 0, i11);
                            }
                        } else {
                            iArr = engineData;
                            patternByIndex = vibratorHelper.getPatternByIndex(i7);
                        }
                        if (patternByIndex.length != 2 || patternByIndex[0] >= 0) {
                            int i12 = 0;
                            for (long j2 : patternByIndex) {
                                i12 = (int) (i12 + j2);
                            }
                            i = 2;
                            j = 0;
                            patternByIndex = new long[] {0, i12};
                        } else {
                            i = 2;
                            j = 0;
                        }
                        if (patternByIndex.length == i && patternByIndex[0] < j) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    i7,
                                    "This ",
                                    " is not supported.(colorful)",
                                    "VibratorManagerService");
                        } else if (i6 <= -1 || isExecutablePattern(patternByIndex)) {
                            return new HalVibration(
                                    this.mToken,
                                    this.mEffect,
                                    vibratorHelper.getIndexDuration(i7) + 100,
                                    this.mMagnitude,
                                    iArr,
                                    null,
                                    getCallerInfo());
                        }
                    } else {
                        while (i4 < length) {
                            StringBuilder m2 =
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            i4,
                                            "isColorfulDataFormat() - wrong format(2) : data[",
                                            "] = ");
                            m2.append(engineData[i4]);
                            Slog.e("VibratorManagerService", m2.toString());
                            i4++;
                        }
                    }
                    Slog.d("VibratorManagerService", "data is not haptic engine data");
                }
                return null;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i7, "This ", " is not supported for engine.", "VibratorManagerService");
        }
        long[] patternByIndex2 = vibratorHelper.getPatternByIndex(i7);
        if (patternByIndex2.length == 2) {
            long j3 = patternByIndex2[0];
            if (j3 == 0 && patternByIndex2[1] == 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i7, "This ", " is blank pattern.(common)", "VibratorManagerService");
            } else if (j3 < 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i7, "This ", " is not supported.(common)", "VibratorManagerService");
            }
            return null;
        }
        if (patternByIndex2.length != 0) {
            int length3 = patternByIndex2.length;
            while (true) {
                if (i4 >= length3) {
                    Slog.e("VibratorManagerService", "isAll0() is true");
                    break;
                }
                if (patternByIndex2[i4] == 0) {
                    i4++;
                } else if (i6 < patternByIndex2.length && this.mToken != null) {
                    if (i6 <= -1 || i7 != -1 || isExecutablePattern(patternByIndex2)) {
                        VibrationEffect createWaveform =
                                VibrationEffect.createWaveform(patternByIndex2, i6);
                        createWaveform.semSetMagnitude(this.mMagnitude);
                        CombinedVibration createParallel =
                                CombinedVibration.createParallel(createWaveform);
                        IBinder iBinder = this.mToken;
                        long indexDuration = vibratorHelper.getIndexDuration(i7);
                        vibratorHelper.getPatternFrequencyByIndex(i7);
                        return new HalVibration(
                                iBinder,
                                createParallel,
                                indexDuration,
                                this.mMagnitude,
                                null,
                                null,
                                getCallerInfo());
                    }
                }
            }
        }
        Slog.d("VibratorManagerService", "semPatternVibrate() is failed by illegal argument.");
        Slog.d(
                "VibratorManagerService",
                "semPatternVibrate() - pattern.length = "
                        + patternByIndex2.length
                        + ", repeat = "
                        + i6);
        StringBuilder sb = new StringBuilder("semPatternVibrate() - token = ");
        sb.append(this.mToken);
        Slog.d("VibratorManagerService", sb.toString());
        return null;
    }

    public final boolean isExecutablePattern(long[] jArr) {
        int length = jArr.length;
        int i = this.mRepeat;
        if (i >= length) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    length,
                    i,
                    "isExecutablePattern() - length = ",
                    ", repeat = ",
                    "VibratorManagerService");
            return false;
        }
        if (jArr[i] > 0) {
            return true;
        }
        int i2 = (i / 2) * 2;
        if (((i2 == i && (length / 2) * 2 == length) || (i2 != i && (length / 2) * 2 != length))
                && jArr[length - 1] > 0) {
            return true;
        }
        for (int i3 = 0; i3 < length; i3 += 2) {
            int i4 = i3 + i;
            int i5 = i4 + 1;
            int i6 = i4 + 2;
            if (i6 < length && jArr[i5] + jArr[i6] > 0) {
                return true;
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "This pattern is not executable. repeat = ", "VibratorManagerService");
        for (int i7 = 0; i7 < length; i7++) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i7, "pattern[", "] = ");
            m.append(jArr[i7]);
            Slog.w("VibratorManagerService", m.toString());
        }
        if (this.mIsExecutablePkg.length() < 1000) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIsExecutablePkg);
            sb.append(" ");
            this.mIsExecutablePkg =
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mOpPkg, ":");
            for (long j : jArr) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mIsExecutablePkg);
                this.mIsExecutablePkg = AudioConfig$$ExternalSyntheticOutline0.m(sb2, j, " ");
            }
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mHasEngineData ? "semColorfulVibrate : " : "semPatternVibrate : ");
        sb.append(getCommonLog());
        return sb.toString();
    }
}
