package com.android.server.hdmi;

import android.util.FastImmutableArraySet;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
