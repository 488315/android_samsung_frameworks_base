package com.android.net.module.util;

import android.net.NetworkCapabilities;

public final class NetworkCapabilitiesUtils {
    private static final int[] DISPLAY_TRANSPORT_PRIORITIES = {4, 0, 5, 2, 1, 3, 8, 10};
    private static final long FORCE_RESTRICTED_CAPABILITIES = 608174080;
    public static final long RESTRICTED_CAPABILITIES = 12490639292L;
    public static final long UNRESTRICTED_CAPABILITIES = 4163;

    public static int getDisplayTransport(int[] transports) {
        for (int transport : DISPLAY_TRANSPORT_PRIORITIES) {
            if (CollectionUtils.contains(transports, transport)) {
                return transport;
            }
        }
        if (transports.length < 1) {
            throw new IllegalArgumentException("No transport in the provided array");
        }
        return transports[0];
    }

    public static boolean inferRestrictedCapability(NetworkCapabilities nc) {
        return inferRestrictedCapability(BitUtils.packBits(nc.getCapabilities()));
    }

    public static boolean inferRestrictedCapability(long capabilities) {
        if ((FORCE_RESTRICTED_CAPABILITIES & capabilities) != 0) {
            return true;
        }
        return (UNRESTRICTED_CAPABILITIES & capabilities) == 0
                && (RESTRICTED_CAPABILITIES & capabilities) != 0;
    }
}
