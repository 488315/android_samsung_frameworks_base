package com.samsung.android.server.audio;

import android.media.AudioSystem;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceAliasManager {
    public final SparseArray mDevices;
    public final SparseArray mLeOnlyDevices;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceAlias {
        public final int[] mAliases;
        public final int mExcludeStreams;

        public DeviceAlias(int[] iArr, int[] iArr2) {
            this.mAliases = iArr;
            int i = 0;
            for (int i2 : iArr2) {
                i |= 1 << i2;
            }
            this.mExcludeStreams = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DeviceAliasRunner {
        void run(int i);
    }

    public DeviceAliasManager() {
        SparseArray sparseArray = new SparseArray();
        this.mDevices = sparseArray;
        this.mLeOnlyDevices = new SparseArray();
        sparseArray.put(2, new DeviceAlias(new int[] {1}, new int[] {0, 8}));
        sparseArray.put(1, new DeviceAlias(new int[] {2}, new int[] {0, 8}));
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        int[] iArr = new int[numStreamTypes];
        for (int i = 0; i < numStreamTypes; i++) {
            if (i != 3) {
                iArr[i] = i;
            }
        }
        this.mDevices.put(128, new DeviceAlias(new int[] {536870912, 536870914}, iArr));
        this.mDevices.put(536870912, new DeviceAlias(new int[] {128, 536870914}, iArr));
        this.mDevices.put(536870914, new DeviceAlias(new int[] {536870912, 128}, iArr));
        this.mLeOnlyDevices.put(536870912, new DeviceAlias(new int[] {536870914}, iArr));
        this.mLeOnlyDevices.put(536870914, new DeviceAlias(new int[] {536870912}, iArr));
    }
}
