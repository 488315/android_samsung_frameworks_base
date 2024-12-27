package com.android.server.power.stats;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import com.android.internal.os.LongArrayMultiStateCounter;
import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiStateStats {
    public int mCompositeState;
    public final LongArrayMultiStateCounter mCounter;
    public final Factory mFactory;
    public boolean mTracking;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Factory {
        public final int[] mCompositeToSerialState;
        public final int mDimensionCount;
        public final int mSerialStateCount;
        public final int[] mStateBitFieldMasks;
        public final short[] mStateBitFieldShifts;
        public final States[] mStates;

        public Factory(int i, States... statesArr) {
            this.mDimensionCount = i;
            this.mStates = statesArr;
            int i2 = 1;
            for (States states : statesArr) {
                if (states.mTracked) {
                    i2 *= states.mLabels.length;
                }
            }
            this.mSerialStateCount = i2;
            States[] statesArr2 = this.mStates;
            this.mStateBitFieldMasks = new int[statesArr2.length];
            this.mStateBitFieldShifts = new short[statesArr2.length];
            int i3 = 0;
            int i4 = 0;
            while (true) {
                States[] statesArr3 = this.mStates;
                if (i3 >= statesArr3.length) {
                    if (i4 >= 31) {
                        throw new IllegalArgumentException(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        i4,
                                        "Too many states: ",
                                        " bits are required to represent the composite state, but"
                                            + " only 31 are available"));
                    }
                    int i5 = -1;
                    int i6 = 0;
                    while (true) {
                        States[] statesArr4 = this.mStates;
                        if (i6 >= statesArr4.length) {
                            break;
                        }
                        if (!statesArr4[i6].mTracked) {
                            i5 &= ~this.mStateBitFieldMasks[i6];
                        }
                        i6++;
                    }
                    int[] iArr = new int[1 << i4];
                    this.mCompositeToSerialState = iArr;
                    Arrays.fill(iArr, -1);
                    int i7 = 0;
                    for (int i8 = 0; i8 < this.mCompositeToSerialState.length; i8++) {
                        int i9 = 0;
                        while (true) {
                            States[] statesArr5 = this.mStates;
                            if (i9 >= statesArr5.length) {
                                int[] iArr2 = this.mCompositeToSerialState;
                                int i10 = iArr2[i8 & i5];
                                if (i10 != -1) {
                                    iArr2[i8] = i10;
                                } else {
                                    iArr2[i8] = i7;
                                    i7++;
                                }
                            } else if (((this.mStateBitFieldMasks[i9] & i8)
                                            >>> this.mStateBitFieldShifts[i9])
                                    >= statesArr5[i9].mLabels.length) {
                                break;
                            } else {
                                i9++;
                            }
                        }
                    }
                    return;
                }
                this.mStateBitFieldShifts[i3] = (short) i4;
                String[] strArr = statesArr3[i3].mLabels;
                if (strArr.length < 2) {
                    throw new IllegalArgumentException(
                            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Invalid state: "),
                                    Arrays.toString(this.mStates[i3].mLabels),
                                    ". Should have at least two values."));
                }
                int numberOfLeadingZeros = 32 - Integer.numberOfLeadingZeros(strArr.length - 1);
                this.mStateBitFieldMasks[i3] = ((1 << numberOfLeadingZeros) - 1) << i4;
                i4 += numberOfLeadingZeros;
                i3++;
            }
        }

        public int getSerialState(int[] iArr) {
            Preconditions.checkArgument(iArr.length == this.mStates.length);
            int i = 0;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                i =
                        (i & (~this.mStateBitFieldMasks[i2]))
                                | (iArr[i2] << this.mStateBitFieldShifts[i2]);
            }
            int i3 = this.mCompositeToSerialState[i];
            if (i3 != -1) {
                return i3;
            }
            throw new IllegalArgumentException(
                    "State values out of bounds: " + Arrays.toString(iArr));
        }

        public int getSerialStateCount() {
            return this.mSerialStateCount;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class States {
        public final String[] mLabels;
        public final String mName;
        public final boolean mTracked;

        public States(String str, boolean z, String... strArr) {
            this.mName = str;
            this.mTracked = z;
            this.mLabels = strArr;
        }

        public static int findTrackedStateByName(States[] statesArr, String str) {
            for (int i = 0; i < statesArr.length; i++) {
                if (statesArr[i].mName.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public static void forEachTrackedStateCombination(
                Consumer consumer, States[] statesArr, int[] iArr, int i) {
            if (i >= iArr.length) {
                consumer.accept(iArr);
                return;
            }
            if (!statesArr[i].mTracked) {
                forEachTrackedStateCombination(consumer, statesArr, iArr, i + 1);
                return;
            }
            for (int i2 = 0; i2 < statesArr[i].mLabels.length; i2++) {
                iArr[i] = i2;
                forEachTrackedStateCombination(consumer, statesArr, iArr, i + 1);
            }
        }
    }

    public MultiStateStats(Factory factory, int i) {
        this.mFactory = factory;
        this.mCounter = new LongArrayMultiStateCounter(factory.mSerialStateCount, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0115, code lost:

       return true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x006c, code lost:

       android.util.Slog.e("MultiStateStats", "State index out of bounds: " + r10 + " length: " + r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0085, code lost:

       return r7;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean readFromXml(com.android.modules.utils.TypedXmlPullParser r17) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.MultiStateStats.readFromXml(com.android.modules.utils.TypedXmlPullParser):boolean");
    }

    public final void setState(int i, int i2, long j) {
        if (!this.mTracking) {
            LongArrayMultiStateCounter longArrayMultiStateCounter = this.mCounter;
            longArrayMultiStateCounter.updateValues(
                    new long[longArrayMultiStateCounter.getArrayLength()], j);
            this.mTracking = true;
        }
        int i3 = this.mCompositeState;
        Factory factory = this.mFactory;
        int i4 = (i2 << factory.mStateBitFieldShifts[i]) | (i3 & (~factory.mStateBitFieldMasks[i]));
        this.mCompositeState = i4;
        this.mCounter.setState(factory.mCompositeToSerialState[i4], j);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        long[] jArr = new long[this.mCounter.getArrayLength()];
        States[] statesArr = this.mFactory.mStates;
        States.forEachTrackedStateCombination(
                new MultiStateStats$$ExternalSyntheticLambda0(this, jArr, sb),
                statesArr,
                new int[statesArr.length],
                0);
        return sb.toString();
    }

    public final void writeXml(TypedXmlSerializer typedXmlSerializer) {
        long[] jArr = new long[this.mCounter.getArrayLength()];
        try {
            States[] statesArr = this.mFactory.mStates;
            States.forEachTrackedStateCombination(
                    new MultiStateStats$$ExternalSyntheticLambda0(this, typedXmlSerializer, jArr),
                    statesArr,
                    new int[statesArr.length],
                    0);
        } catch (RuntimeException e) {
            if (!(e.getCause() instanceof IOException)) {
                throw e;
            }
            throw ((IOException) e.getCause());
        }
    }

    public final void writeXmlForStates(
            TypedXmlSerializer typedXmlSerializer, int[] iArr, long[] jArr) {
        int i;
        LongArrayMultiStateCounter longArrayMultiStateCounter = this.mCounter;
        Factory factory = this.mFactory;
        longArrayMultiStateCounter.getCounts(jArr, factory.getSerialState(iArr));
        for (long j : jArr) {
            if (j != 0) {
                typedXmlSerializer.startTag((String) null, "stats");
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    States states = factory.mStates[i2];
                    if (states.mTracked && (i = iArr[i2]) != 0) {
                        typedXmlSerializer.attribute(
                                (String) null, states.mName, states.mLabels[i]);
                    }
                }
                for (int i3 = 0; i3 < jArr.length; i3++) {
                    if (jArr[i3] != 0) {
                        typedXmlSerializer.attributeLong(
                                (String) null,
                                VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "_"),
                                jArr[i3]);
                    }
                }
                typedXmlSerializer.endTag((String) null, "stats");
                return;
            }
        }
    }
}
