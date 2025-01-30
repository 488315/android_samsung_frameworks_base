package com.android.server.bgslotmanager;

import android.os.Process;
import android.p005os.IInstalld;
import com.android.internal.util.MemInfoReader;

/* loaded from: classes.dex */
public class MemInfoGetter {
    public final int[][] memoryMBToGBPool = {new int[]{12288, 16}, new int[]{IInstalld.FLAG_FORCE, 12}, new int[]{6144, 8}, new int[]{IInstalld.FLAG_USE_QUOTA, 6}, new int[]{3072, 4}, new int[]{IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, 3}, new int[]{1024, 2}, new int[]{0, 1}};
    public MemInfoReader mInfoInner = new MemInfoReader();
    public final long mTotalMemMb = getTotalMemoryMB();

    public static long getTotalMemoryMB() {
        return Process.getTotalMemory() / 1048576;
    }

    public final void readLightMemInfo() {
        this.mInfoInner.readLightMemInfo();
    }

    public final long getSwapTotalSizeKb() {
        readLightMemInfo();
        return this.mInfoInner.getSwapTotalSizeKb();
    }

    public long getAvailableMemLegacy() {
        readLightMemInfo();
        return (this.mInfoInner.getFreeSize() + this.mInfoInner.getCachedSizeLegacy()) - (this.mInfoInner.getRbinTotalSize() - this.mInfoInner.getRbinAllocedSize());
    }

    public int getSwapsizeGB() {
        int swapTotalSizeKb = (int) (getSwapTotalSizeKb() / 1024);
        for (int[] iArr : this.memoryMBToGBPool) {
            if (swapTotalSizeKb > iArr[0]) {
                return iArr[1];
            }
        }
        return 0;
    }

    public int getPhysicalMemory() {
        for (int[] iArr : this.memoryMBToGBPool) {
            if (this.mTotalMemMb > iArr[0]) {
                return iArr[1];
            }
        }
        return 0;
    }
}
