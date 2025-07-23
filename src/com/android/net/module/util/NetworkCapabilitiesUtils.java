package com.android.net.module.util;

import android.net.NetworkCapabilities;

/* loaded from: classes6.dex */
public final class NetworkCapabilitiesUtils {
    private static final int[] DISPLAY_TRANSPORT_PRIORITIES = {4, 0, 5, 2, 1, 3, 8, 10};
    private static final long FORCE_RESTRICTED_CAPABILITIES = 608174080;
    public static final long RESTRICTED_CAPABILITIES = 12490639292L;
    public static final long UNRESTRICTED_CAPABILITIES = 4163;

    public static boolean inferRestrictedCapability(long j) {
        if ((FORCE_RESTRICTED_CAPABILITIES & j) != 0) {
            return true;
        }
        return (UNRESTRICTED_CAPABILITIES & j) == 0 && (j & RESTRICTED_CAPABILITIES) != 0;
    }

    public static int getDisplayTransport(int[] iArr) {
        for (int i : DISPLAY_TRANSPORT_PRIORITIES) {
            if (CollectionUtils.contains(iArr, i)) {
                return i;
            }
        }
        if (iArr.length < 1) {
            throw new IllegalArgumentException("No transport in the provided array");
        }
        return iArr[0];
    }

    public static boolean inferRestrictedCapability(NetworkCapabilities networkCapabilities) {
        return inferRestrictedCapability(BitUtils.packBits(networkCapabilities.getCapabilities()));
    }
}
