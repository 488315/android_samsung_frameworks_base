package com.android.server.hdmi;

import android.util.FastImmutableArraySet;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;

public final class HdmiCecMessageCache {
    public static final FastImmutableArraySet CACHEABLE_OPCODES =
            new FastImmutableArraySet(
                    new Integer[] {
                        71,
                        132,
                        135,
                        Integer.valueOf(
                                FrameworkStatsLog
                                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL)
                    });
    public final SparseArray mCache = new SparseArray();
}
