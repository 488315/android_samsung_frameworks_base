package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.server.vibrator.CommonPatternInfo;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import com.samsung.android.vibrator.VibRune;

import java.util.ArrayList;

public final class SemIndexVibration extends SemVibration {
    @Override // com.android.server.vibrator.SemVibration
    public final HalVibration getVibration() {
        CommonPatternInfo[] commonPatternInfoArr;
        CommonPatternInfo[] commonPatternInfoArr2 = null;
        if (!commonValidation()) {
            return null;
        }
        int i = this.mIndex;
        if (i == 50084) {
            Slog.d("VibratorManagerService", "Silent vibration is ignored.");
            return null;
        }
        IBinder iBinder = this.mToken;
        CombinedVibration combinedVibration = this.mEffect;
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        long indexDuration = vibratorHelper.getIndexDuration(i);
        if (VibRune.SUPPORT_HYBRID_HAPTIC() && !SemHapticFeedbackConstants.isRamIndexValid(i)) {
            if (SemHapticFeedbackConstants.isResourceIndexValid(i)) {
                commonPatternInfoArr2 = vibratorHelper.getResourceIndexData(i);
            } else if (SemHapticFeedbackConstants.isHybridIndexValid(i)) {
                Slog.d("VibratorManagerService", "get hybrid index data");
                int sepIndex = VibratorHelper.getSepIndex(i);
                ArrayList arrayList = new ArrayList();
                int[] iArr = vibratorHelper.getPatternInfo(sepIndex).hybrid;
                if (iArr.length == 0) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            sepIndex, "index : ", " has not hybrid data", "VibratorManagerService");
                }
                int length = iArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i3 < length) {
                    int i4 = iArr[i3];
                    int i5 = 50024 + i4;
                    if (i4 < 0) {
                        arrayList.add(new CommonPatternInfo(0, 0, 0, i4 * (-1), 0));
                    } else if (SemHapticFeedbackConstants.isRamIndexValid(i5)) {
                        Slog.d("VibratorManagerService", "get ram index data");
                        int sepIndex2 = VibratorHelper.getSepIndex(i5);
                        int sepIndex3 = VibratorHelper.getSepIndex(i5);
                        arrayList.add(
                                new CommonPatternInfo[] {
                                            new CommonPatternInfo(
                                                    81,
                                                    sepIndex2,
                                                    vibratorHelper.getPatternInfo(sepIndex3) == null
                                                            ? 100
                                                            : vibratorHelper.getPatternInfo(
                                                                            sepIndex3)
                                                                    .scale,
                                                    vibratorHelper.getIndexDuration(i5),
                                                    vibratorHelper.getPatternFrequencyByIndex(i5))
                                        }
                                        [i2]);
                    } else if (SemHapticFeedbackConstants.isResourceIndexValid(i5)) {
                        CommonPatternInfo[] resourceIndexData =
                                vibratorHelper.getResourceIndexData(i5);
                        int length2 = resourceIndexData.length;
                        for (int i6 = i2; i6 < length2; i6++) {
                            arrayList.add(resourceIndexData[i6]);
                        }
                    }
                    i3++;
                    i2 = 0;
                }
                CommonPatternInfo[] commonPatternInfoArr3 = new CommonPatternInfo[arrayList.size()];
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    commonPatternInfoArr3[i7] = (CommonPatternInfo) arrayList.get(i7);
                }
                commonPatternInfoArr = commonPatternInfoArr3;
                return new HalVibration(
                        iBinder,
                        combinedVibration,
                        indexDuration,
                        this.mMagnitude,
                        null,
                        commonPatternInfoArr,
                        getCallerInfo());
            }
        }
        commonPatternInfoArr = commonPatternInfoArr2;
        return new HalVibration(
                iBinder,
                combinedVibration,
                indexDuration,
                this.mMagnitude,
                null,
                commonPatternInfoArr,
                getCallerInfo());
    }

    public final String toString() {
        return "semIndexVibrate : " + getCommonLog();
    }
}
