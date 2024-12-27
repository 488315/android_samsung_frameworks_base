package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.vibrator.PrimitiveSegment;
import android.util.SparseBooleanArray;
import com.android.server.vibrator.Vibration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationStats {
    public float mAdaptiveScale;
    public long mCreateTimeDebug;
    public long mCreateUptimeMillis;
    public long mEndTimeDebug;
    public long mEndUptimeMillis;
    public int mEndedByUid;
    public int mEndedByUsage;
    public int mInterruptedUsage;
    public int mRepeatCount;
    public long mStartTimeDebug;
    public long mStartUptimeMillis;
    public int mVibrationCompositionTotalSize;
    public int mVibrationPwleTotalSize;
    public int mVibratorComposeCount;
    public int mVibratorComposePwleCount;
    public SparseBooleanArray mVibratorEffectsUsed;
    public int mVibratorOffCount;
    public int mVibratorOnCount;
    public int mVibratorOnTotalDurationMillis;
    public int mVibratorPerformCount;
    public SparseBooleanArray mVibratorPrimitivesUsed;
    public int mVibratorSetAmplitudeCount;
    public int mVibratorSetExternalControlCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsInfo {
        public final float adaptiveScale;
        public final int endLatencyMillis;
        public final boolean endedBySameUid;
        public final int endedByUsage;
        public final int halComposeCount;
        public final int halComposePwleCount;
        public final int halCompositionSize;
        public final int halOffCount;
        public final int halOnCount;
        public final int halPerformCount;
        public final int halPwleSize;
        public final int halSetAmplitudeCount;
        public final int halSetExternalControlCount;
        public final int[] halSupportedCompositionPrimitivesUsed;
        public final int[] halSupportedEffectsUsed;
        public final int[] halUnsupportedCompositionPrimitivesUsed;
        public final int[] halUnsupportedEffectsUsed;
        public final int interruptedUsage;
        public boolean mIsWritten;
        public final int repeatCount;
        public final int startLatencyMillis;
        public final int status;
        public final int totalDurationMillis;
        public final int uid;
        public final int usage;
        public final int vibrationType;
        public final int vibratorOnMillis;

        public StatsInfo(int i, int i2, int i3, Vibration.Status status, VibrationStats vibrationStats, long j) {
            this.uid = i;
            this.vibrationType = i2;
            this.usage = i3;
            this.status = status.getProtoEnumValue();
            this.adaptiveScale = vibrationStats.mAdaptiveScale;
            this.endedBySameUid = i == vibrationStats.mEndedByUid;
            this.endedByUsage = vibrationStats.mEndedByUsage;
            this.interruptedUsage = vibrationStats.mInterruptedUsage;
            this.repeatCount = vibrationStats.mRepeatCount;
            long j2 = vibrationStats.mCreateUptimeMillis;
            this.totalDurationMillis = (int) Math.max(0L, j - j2);
            this.vibratorOnMillis = vibrationStats.mVibratorOnTotalDurationMillis;
            long j3 = vibrationStats.mStartUptimeMillis;
            if (j3 > 0) {
                this.startLatencyMillis = (int) Math.max(0L, j3 - j2);
                this.endLatencyMillis = (int) Math.max(0L, j - vibrationStats.mEndUptimeMillis);
            } else {
                this.endLatencyMillis = 0;
                this.startLatencyMillis = 0;
            }
            this.halComposeCount = vibrationStats.mVibratorComposeCount;
            this.halComposePwleCount = vibrationStats.mVibratorComposePwleCount;
            this.halOnCount = vibrationStats.mVibratorOnCount;
            this.halOffCount = vibrationStats.mVibratorOffCount;
            this.halPerformCount = vibrationStats.mVibratorPerformCount;
            this.halSetAmplitudeCount = vibrationStats.mVibratorSetAmplitudeCount;
            this.halSetExternalControlCount = vibrationStats.mVibratorSetExternalControlCount;
            this.halCompositionSize = vibrationStats.mVibrationCompositionTotalSize;
            this.halPwleSize = vibrationStats.mVibrationPwleTotalSize;
            this.halSupportedCompositionPrimitivesUsed = filteredKeys(vibrationStats.mVibratorPrimitivesUsed, true);
            this.halSupportedEffectsUsed = filteredKeys(vibrationStats.mVibratorEffectsUsed, true);
            this.halUnsupportedCompositionPrimitivesUsed = filteredKeys(vibrationStats.mVibratorPrimitivesUsed, false);
            this.halUnsupportedEffectsUsed = filteredKeys(vibrationStats.mVibratorEffectsUsed, false);
        }

        public static int[] filteredKeys(SparseBooleanArray sparseBooleanArray, boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                if (sparseBooleanArray.valueAt(i2) == z) {
                    i++;
                }
            }
            if (i == 0) {
                return null;
            }
            int[] iArr = new int[i];
            int i3 = 0;
            for (int i4 = 0; i4 < sparseBooleanArray.size(); i4++) {
                if (sparseBooleanArray.valueAt(i4) == z) {
                    iArr[i3] = sparseBooleanArray.keyAt(i4);
                    i3++;
                }
            }
            return iArr;
        }

        public boolean isWritten() {
            return this.mIsWritten;
        }
    }

    public final void reportComposePrimitives(PrimitiveSegment[] primitiveSegmentArr, long j) {
        this.mVibratorComposeCount++;
        this.mVibrationCompositionTotalSize += primitiveSegmentArr.length;
        if (j <= 0) {
            for (PrimitiveSegment primitiveSegment : primitiveSegmentArr) {
                this.mVibratorPrimitivesUsed.put(primitiveSegment.getPrimitiveId(), false);
            }
            return;
        }
        for (PrimitiveSegment primitiveSegment2 : primitiveSegmentArr) {
            j -= r5.getDelay();
            this.mVibratorPrimitivesUsed.put(primitiveSegment2.getPrimitiveId(), true);
        }
        if (j > 0) {
            this.mVibratorOnTotalDurationMillis += (int) j;
        }
    }

    public final void reportEnded(Vibration.CallerInfo callerInfo) {
        if (this.mEndUptimeMillis > 0) {
            return;
        }
        if (callerInfo != null) {
            this.mEndedByUid = callerInfo.uid;
            this.mEndedByUsage = callerInfo.attrs.getUsage();
        }
        this.mEndUptimeMillis = SystemClock.uptimeMillis();
        this.mEndTimeDebug = System.currentTimeMillis();
    }

    public final void reportInterruptedAnotherVibration(Vibration.CallerInfo callerInfo) {
        if (this.mInterruptedUsage < 0) {
            this.mInterruptedUsage = callerInfo.attrs.getUsage();
        }
    }
}
