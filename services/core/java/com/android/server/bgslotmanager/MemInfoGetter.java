package com.android.server.bgslotmanager;

import android.os.Process;

import com.android.internal.util.MemInfoReader;
import com.android.server.knox.dar.ddar.ta.TACommandRequest;

public final class MemInfoGetter {
    public final int[][] memoryMBToGBPool = {
        new int[] {12288, 16},
        new int[] {8192, 12},
        new int[] {6144, 8},
        new int[] {4096, 6},
        new int[] {TACommandRequest.MAX_DATA_TRANSACTION_SIZE, 4},
        new int[] {2048, 3},
        new int[] {1024, 2},
        new int[] {0, 1}
    };
    public final MemInfoReader mInfoInner = new MemInfoReader();
    public final long mTotalMemMb = Process.getTotalMemory() / 1048576;
}
