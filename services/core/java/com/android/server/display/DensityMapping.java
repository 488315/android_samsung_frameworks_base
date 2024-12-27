package com.android.server.display;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

import java.util.Arrays;
import java.util.Comparator;

public final class DensityMapping {
    public final Entry[] mSortedDensityMappingEntries;

    public final class Entry {
        public static final Entry ZEROES = new Entry(0, 0, 0);
        public final int density;
        public final int squaredDiagonal;

        public Entry(int i, int i2, int i3) {
            this.squaredDiagonal = (i2 * i2) + (i * i);
            this.density = i3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DensityMappingEntry{squaredDiagonal=");
            sb.append(this.squaredDiagonal);
            sb.append(", density=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.density, '}');
        }
    }

    public DensityMapping(Entry[] entryArr) {
        Arrays.sort(
                entryArr, Comparator.comparingInt(new DensityMapping$$ExternalSyntheticLambda0()));
        this.mSortedDensityMappingEntries = entryArr;
        for (int i = 1; i < entryArr.length; i++) {
            Entry entry = entryArr[i - 1];
            Entry entry2 = entryArr[i];
            if (entry.squaredDiagonal == entry2.squaredDiagonal) {
                throw new IllegalStateException(
                        "Found two entries in the density mapping with the same diagonal: "
                                + entry
                                + ", "
                                + entry2);
            }
            if (entry.density > entry2.density) {
                throw new IllegalStateException(
                        "Found two entries in the density mapping with increasing diagonal but"
                            + " decreasing density: "
                                + entry
                                + ", "
                                + entry2);
            }
        }
    }

    public final String toString() {
        return "DensityMapping{mDensityMappingEntries="
                + Arrays.toString(this.mSortedDensityMappingEntries)
                + '}';
    }
}
