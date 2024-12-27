package com.samsung.android.server.vibrator;

import android.content.res.Resources;
import android.util.Log;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.vibrator.VibRune;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibratorHelper {
    public static final String PKG_ACT_ALWAYS = VibRune.DND_EXCEPTION_PACKAGES;
    public static VibratorHelper sInstance;
    public static boolean sIsFrequencySupported;
    public static boolean sIsHapticEngineSupported;
    public static int sMotorType;
    public final HashMap mChangedPatternHash;
    public int mSepIndex;
    public final HashMap mVibePatternHash;

    public VibratorHelper() {
        HashSet hashSet = new HashSet();
        this.mVibePatternHash = new HashMap();
        this.mSepIndex = -1;
        this.mChangedPatternHash = new HashMap();
        if (VibRune.SUPPORT_ALWAYS_VIBRATE) {
            hashSet.addAll(Arrays.asList(PKG_ACT_ALWAYS.split(",")));
        }
    }

    public static String getMotorTypeToString(int i) {
        String str;
        switch (i) {
            case 0:
                str = "MOTOR_NONE";
                break;
            case 1:
                str = "MOTOR_COIN_DC";
                break;
            case 2:
                str = "MOTOR_LINEAR_0832";
                break;
            case 3:
                str = "MOTOR_COIN_DC_PMIC";
                break;
            case 4:
                str = "MOTOR_COIN_DC_INDEX";
                break;
            case 5:
                str = "MOTOR_LINEAR_INDEX";
                break;
            case 6:
                str = "MOTOR_LINEAR_0832RC";
                break;
            case 7:
                str = "MOTOR_LINEAR_1040";
                break;
            case 8:
                str = "MOTOR_LINEAR_RCVMOT";
                break;
            case 9:
                str = "MOTOR_LINEAR_1030";
                break;
            default:
                str = "";
                break;
        }
        return str + " (" + i + ")";
    }

    public static int getSepIndex(int i) {
        return i - HapticFeedbackConstants.semGetVibrationIndex(0);
    }

    public final PatternInfo copyPatternInfo(int i) {
        PatternInfo patternInfo = new PatternInfo();
        PatternInfo patternInfo2 = (PatternInfo) this.mVibePatternHash.get(Integer.valueOf(i));
        if (patternInfo2 == null) {
            return null;
        }
        patternInfo.pattern = patternInfo2.pattern;
        patternInfo.frequency = patternInfo2.frequency;
        patternInfo.engine = patternInfo2.engine;
        patternInfo.duration = patternInfo2.duration;
        patternInfo.scale = patternInfo2.scale;
        patternInfo.hybrid = patternInfo2.hybrid;
        return patternInfo;
    }

    public final void createIndexDurationInfo(int i, int i2) {
        PatternInfo patternInfo = new PatternInfo();
        patternInfo.frequency = -1;
        patternInfo.duration = i2 + 100;
        this.mVibePatternHash.put(Integer.valueOf(i), patternInfo);
    }

    public final void createPatternInfo(int i, Resources resources, int i2, int i3, int i4) {
        int i5;
        int[] iArr;
        PatternInfo patternInfo = new PatternInfo();
        int[] intArray = resources.getIntArray(i2);
        int length = intArray.length;
        int i6 = 0;
        for (int i7 = 0; i7 < length && intArray[i7] != -1; i7++) {
            i6++;
        }
        long[] jArr = new long[i6];
        for (int i8 = 0; i8 < i6; i8++) {
            jArr[i8] = intArray[i8];
        }
        patternInfo.pattern = jArr;
        patternInfo.frequency = i3;
        int[] intArray2 = resources.getIntArray(i2);
        int i9 = 0;
        while (true) {
            if (i9 >= intArray2.length) {
                i5 = -1;
                break;
            } else {
                if (intArray2[i9] == -1) {
                    i5 = i9 + 1;
                    break;
                }
                i9++;
            }
        }
        if (i5 < 0) {
            iArr = null;
        } else {
            int length2 = intArray2.length - i5;
            iArr = new int[length2];
            System.arraycopy(intArray2, i5, iArr, 0, length2);
        }
        if (iArr == null) {
            iArr = new int[]{-1, -1};
        }
        patternInfo.engine = iArr;
        patternInfo.duration = i4 + 100;
        patternInfo.scale = 100;
        patternInfo.hybrid = i >= 10000 ? resources.getIntArray(i2) : null;
        this.mVibePatternHash.put(Integer.valueOf(i), patternInfo);
        Log.v("VibratorManagerService", "mVibePatternHash.put(" + i + ", pattern), mVibePatternHash.size() : " + this.mVibePatternHash.size());
    }

    public final int[] getEngineData(int i) {
        int[] iArr;
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo != null && (iArr = patternInfo.engine) != null) {
            return iArr;
        }
        ASKSManagerService$$ExternalSyntheticOutline0.m(i, sepIndex, "getEngineData(), IndexOutOfBoundsException occurred, type:", ", index:", "VibratorManagerService");
        return new int[]{-1, -1};
    }

    public final int getIndexDuration(int i) {
        int sepIndex = getSepIndex(i);
        if (this.mVibePatternHash.containsKey(Integer.valueOf(sepIndex))) {
            return ((PatternInfo) this.mVibePatternHash.get(Integer.valueOf(sepIndex))).duration;
        }
        return 5000;
    }

    public final long[] getPatternByIndex(int i) {
        long[] jArr;
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo != null && (jArr = patternInfo.pattern) != null) {
            return jArr;
        }
        StringBuilder sb = new StringBuilder("getPatternByIndex(), index out of bound, mVibePatternHash:");
        sb.append(this.mVibePatternHash.size());
        sb.append(", type:");
        sb.append(i);
        sb.append(", index:");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, sepIndex, "VibratorManagerService");
        return new long[]{-1, -1};
    }

    public final int getPatternFrequencyByIndex(int i) {
        Integer num;
        int i2;
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo == null || (i2 = patternInfo.frequency) == -1) {
            StringBuilder sb = new StringBuilder("getPatternFrequencyByIndex(), index out of bound, mVibePatternHash:");
            sb.append(this.mVibePatternHash.size());
            sb.append(", type:");
            sb.append(i);
            sb.append(", index:");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, sepIndex, "VibratorManagerService");
            num = null;
        } else {
            num = Integer.valueOf(i2);
        }
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public final PatternInfo getPatternInfo(int i) {
        return (PatternInfo) this.mVibePatternHash.get(Integer.valueOf(i));
    }

    public final CommonPatternInfo[] getResourceIndexData(int i) {
        Slog.d("VibratorManagerService", "get resource index data");
        int[] engineData = getEngineData(i);
        int length = (engineData.length - 1) / 4;
        CommonPatternInfo[] commonPatternInfoArr = new CommonPatternInfo[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 4;
            commonPatternInfoArr[i2] = new CommonPatternInfo(82, 0, engineData[i3 + 2], engineData[i3 + 1], engineData[i3 + 3]);
        }
        return commonPatternInfoArr;
    }
}
