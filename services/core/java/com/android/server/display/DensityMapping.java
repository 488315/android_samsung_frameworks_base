package com.android.server.display;

import com.android.server.display.DensityMapping;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* loaded from: classes2.dex */
public class DensityMapping {
    public final Entry[] mSortedDensityMappingEntries;

    public static DensityMapping createByOwning(Entry[] entryArr) {
        return new DensityMapping(entryArr);
    }

    public DensityMapping(Entry[] entryArr) {
        Arrays.sort(entryArr, Comparator.comparingInt(new ToIntFunction() { // from class: com.android.server.display.DensityMapping$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int i;
                i = ((DensityMapping.Entry) obj).squaredDiagonal;
                return i;
            }
        }));
        this.mSortedDensityMappingEntries = entryArr;
        verifyDensityMapping(entryArr);
    }

    public int getDensityForResolution(int i, int i2) {
        Entry entry;
        int i3 = (i * i) + (i2 * i2);
        Entry entry2 = Entry.ZEROES;
        Entry[] entryArr = this.mSortedDensityMappingEntries;
        int length = entryArr.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                entry = null;
                break;
            }
            entry = entryArr[i4];
            if (entry.squaredDiagonal > i3) {
                break;
            }
            i4++;
            entry2 = entry;
        }
        if (entry2.squaredDiagonal == i3) {
            return entry2.density;
        }
        if (entry == null) {
            entry = entry2;
            entry2 = Entry.ZEROES;
        }
        double sqrt = Math.sqrt(entry2.squaredDiagonal);
        double sqrt2 = Math.sqrt(entry.squaredDiagonal);
        double sqrt3 = Math.sqrt(i3) - sqrt;
        int i5 = entry.density;
        return (int) Math.round(((sqrt3 * (i5 - r9)) / (sqrt2 - sqrt)) + entry2.density);
    }

    public static void verifyDensityMapping(Entry[] entryArr) {
        for (int i = 1; i < entryArr.length; i++) {
            Entry entry = entryArr[i - 1];
            Entry entry2 = entryArr[i];
            if (entry.squaredDiagonal == entry2.squaredDiagonal) {
                throw new IllegalStateException("Found two entries in the density mapping with the same diagonal: " + entry + ", " + entry2);
            }
            if (entry.density > entry2.density) {
                throw new IllegalStateException("Found two entries in the density mapping with increasing diagonal but decreasing density: " + entry + ", " + entry2);
            }
        }
    }

    public String toString() {
        return "DensityMapping{mDensityMappingEntries=" + Arrays.toString(this.mSortedDensityMappingEntries) + '}';
    }

    public class Entry {
        public static final Entry ZEROES = new Entry(0, 0, 0);
        public final int density;
        public final int squaredDiagonal;

        public Entry(int i, int i2, int i3) {
            this.squaredDiagonal = (i * i) + (i2 * i2);
            this.density = i3;
        }

        public String toString() {
            return "DensityMappingEntry{squaredDiagonal=" + this.squaredDiagonal + ", density=" + this.density + '}';
        }
    }
}
