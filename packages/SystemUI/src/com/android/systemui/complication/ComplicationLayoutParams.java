package com.android.systemui.complication;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ComplicationLayoutParams extends ViewGroup.LayoutParams {
    public static final Map INVALID_DIRECTIONS;
    public static final int[] INVALID_POSITIONS = {3, 12};
    public final int mConstraint;
    public final int mDirection;
    public final int mDirectionalSpacing;
    public final int mPosition;
    public final boolean mSnapToGuide;
    public final int mWeight;

    static {
        HashMap hashMap = new HashMap();
        INVALID_DIRECTIONS = hashMap;
        hashMap.put(2, 2);
        hashMap.put(1, 1);
        hashMap.put(4, 4);
        hashMap.put(8, 8);
    }

    public ComplicationLayoutParams(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, i5, -1, -1, false);
    }

    public static void iteratePositions(int i, Consumer consumer) {
        for (int i2 = 1; i2 <= 8; i2 <<= 1) {
            if ((i & i2) == i2) {
                consumer.accept(Integer.valueOf(i2));
            }
        }
    }

    public ComplicationLayoutParams(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, i4, i5, i6, -1, false);
    }

    public ComplicationLayoutParams(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(i, i2, i3, i4, i5, i6, i7, false);
    }

    public ComplicationLayoutParams(int i, int i2, int i3, int i4, int i5, boolean z) {
        this(i, i2, i3, i4, i5, -1, -1, z);
    }

    public ComplicationLayoutParams(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        super(i, i2);
        if (i3 != 0) {
            int[] iArr = INVALID_POSITIONS;
            for (int i8 = 0; i8 < 2; i8++) {
                int i9 = iArr[i8];
                if ((i3 & i9) != i9) {
                }
            }
            this.mPosition = i3;
            for (int i10 = 1; i10 <= 8; i10 <<= 1) {
                if ((i3 & i10) == i10) {
                    HashMap hashMap = (HashMap) INVALID_DIRECTIONS;
                    if (hashMap.containsKey(Integer.valueOf(i10)) && (((Integer) hashMap.get(Integer.valueOf(i10))).intValue() & i4) != 0) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "invalid direction:"));
                    }
                }
            }
            this.mDirection = i4;
            this.mWeight = i5;
            this.mDirectionalSpacing = i6;
            this.mConstraint = i7;
            this.mSnapToGuide = z;
            return;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "invalid position:"));
    }

    public ComplicationLayoutParams(ComplicationLayoutParams complicationLayoutParams) {
        super(complicationLayoutParams);
        this.mPosition = complicationLayoutParams.mPosition;
        this.mDirection = complicationLayoutParams.mDirection;
        this.mWeight = complicationLayoutParams.mWeight;
        this.mDirectionalSpacing = complicationLayoutParams.mDirectionalSpacing;
        this.mConstraint = complicationLayoutParams.mConstraint;
        this.mSnapToGuide = complicationLayoutParams.mSnapToGuide;
    }
}
