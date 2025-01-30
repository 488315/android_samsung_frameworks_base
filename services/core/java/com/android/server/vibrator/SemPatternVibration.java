package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.server.vibrator.VibratorHelper;

/* loaded from: classes3.dex */
public class SemPatternVibration extends SemVibration {
    public boolean mHasEngineData;
    public String mIsExecutablePkg;

    @Override // com.android.server.vibrator.SemVibration
    public HalVibration getVibration() {
        if (VibratorHelper.getIsHapticEngineSupported()) {
            int[] engineData = this.mVibratorHelper.getEngineData(this.mIndex);
            if (this.mVibratorHelper.hasEngineData(this.mIndex, engineData)) {
                this.mHasEngineData = true;
                return getHapticEngineVibration(engineData);
            }
        }
        return getPatternVibration();
    }

    public SemPatternVibration(SemVibrationBundle semVibrationBundle) {
        super(semVibrationBundle);
        this.mIsExecutablePkg = "";
        this.mHasEngineData = false;
    }

    public final HalVibration getHapticEngineVibration(int[] iArr) {
        long[] patternByIndex;
        if (!hapticEngineValidation(iArr)) {
            return null;
        }
        int i = this.mIndex;
        if (i == -1) {
            patternByIndex = this.mVibratorHelper.getCustomPattern(iArr);
        } else {
            patternByIndex = this.mVibratorHelper.getPatternByIndex(i);
        }
        long[] colorfulPattern = getColorfulPattern(patternByIndex);
        if (colorfulPattern.length == 2 && colorfulPattern[0] < 0) {
            Slog.d("VibratorManagerService", "This " + this.mIndex + " is not supported.(colorful)");
            return null;
        }
        if (this.mRepeat <= -1 || isExecutablePattern(colorfulPattern)) {
            return new HalVibration(this.mToken, this.mEffect, this.mVibratorHelper.getIndexDuration(this.mIndex) + 100, colorfulPattern, this.mMagnitude, -1, iArr, null, getCallerInfo());
        }
        return null;
    }

    public final HalVibration getPatternVibration() {
        long[] patternByIndex = this.mVibratorHelper.getPatternByIndex(this.mIndex);
        if (!patternValidation(patternByIndex)) {
            return null;
        }
        VibrationEffect createWaveform = VibrationEffect.createWaveform(patternByIndex, this.mRepeat);
        createWaveform.semSetMagnitude(this.mMagnitude);
        return new HalVibration(this.mToken, CombinedVibration.createParallel(createWaveform), this.mVibratorHelper.getIndexDuration(this.mIndex), patternByIndex, this.mMagnitude, this.mVibratorHelper.getPatternFrequencyByIndex(this.mIndex), null, null, getCallerInfo());
    }

    public final boolean hapticEngineValidation(int[] iArr) {
        if (iArr == null) {
            Slog.d("VibratorManagerService", "engine data is null");
            return false;
        }
        if (iArr.length == 5 && iArr[0] == 4 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0) {
            Slog.d("VibratorManagerService", "Empty pattern for haptic engine.");
            return false;
        }
        if (VibratorHelper.isColorfulDataFormat(iArr)) {
            return true;
        }
        Slog.d("VibratorManagerService", "data is not haptic engine data");
        return false;
    }

    public final boolean patternValidation(long[] jArr) {
        int i;
        if (jArr == null) {
            return false;
        }
        if (jArr.length == 2) {
            long j = jArr[0];
            if (j == 0 && jArr[1] == 0) {
                Slog.d("VibratorManagerService", "This " + this.mIndex + " is blank pattern.(common)");
                return false;
            }
            if (j < 0) {
                Slog.d("VibratorManagerService", "This " + this.mIndex + " is not supported.(common)");
                return false;
            }
        }
        if (jArr.length != 0 && !isAll0(jArr) && (i = this.mRepeat) < jArr.length && this.mToken != null) {
            return i <= -1 || this.mIndex != -1 || isExecutablePattern(jArr);
        }
        Slog.d("VibratorManagerService", "semPatternVibrate() is failed by illegal argument.");
        Slog.d("VibratorManagerService", "semPatternVibrate() - pattern.length = " + jArr.length + ", repeat = " + this.mRepeat);
        StringBuilder sb = new StringBuilder();
        sb.append("semPatternVibrate() - token = ");
        sb.append(this.mToken);
        Slog.d("VibratorManagerService", sb.toString());
        return false;
    }

    public final boolean isAll0(long[] jArr) {
        for (long j : jArr) {
            if (j != 0) {
                return false;
            }
        }
        Slog.e("VibratorManagerService", "isAll0() is true");
        return true;
    }

    public final long[] getColorfulPattern(long[] jArr) {
        if (jArr.length == 2 && jArr[0] < 0) {
            return jArr;
        }
        long[] jArr2 = new long[2];
        int i = 0;
        for (long j : jArr) {
            i = (int) (i + j);
        }
        jArr2[0] = 0;
        jArr2[1] = i;
        return jArr2;
    }

    public final boolean isExecutablePattern(long[] jArr) {
        if (jArr == null) {
            Slog.w("VibratorManagerService", "isExecutablePattern() - pattern is null");
            return false;
        }
        int length = jArr.length;
        int i = this.mRepeat;
        if (i >= length) {
            Slog.w("VibratorManagerService", "isExecutablePattern() - length = " + length + ", repeat = " + this.mRepeat);
            return false;
        }
        if (jArr[i] > 0) {
            return true;
        }
        if ((((i / 2) * 2 == i && (length / 2) * 2 == length) || ((i / 2) * 2 != i && (length / 2) * 2 != length)) && jArr[length - 1] > 0) {
            return true;
        }
        for (int i2 = 0; i2 < length; i2 += 2) {
            int i3 = this.mRepeat + i2 + 1;
            int i4 = i3 + 1;
            if (i4 < length && jArr[i3] + jArr[i4] > 0) {
                return true;
            }
        }
        Slog.w("VibratorManagerService", "This pattern is not executable. repeat = " + this.mRepeat);
        for (int i5 = 0; i5 < length; i5++) {
            Slog.w("VibratorManagerService", "pattern[" + i5 + "] = " + jArr[i5]);
        }
        if (this.mIsExecutablePkg.length() < 1000) {
            this.mIsExecutablePkg += " " + this.mOpPkg + XmlUtils.STRING_ARRAY_SEPARATOR;
            for (long j : jArr) {
                this.mIsExecutablePkg += j + " ";
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mHasEngineData ? "semColorfulVibrate : " : "semPatternVibrate : ");
        sb.append(getCommonLog());
        return sb.toString();
    }
}
