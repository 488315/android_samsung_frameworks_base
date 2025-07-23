package com.android.internal.net;

import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;

/* loaded from: classes5.dex */
public class NetworkUtilsInternal {
    private static final int[] ADDRESS_FAMILIES = {OsConstants.AF_INET, OsConstants.AF_INET6};

    public static native boolean protectFromVpn(int i);

    public static native boolean protectFromVpn(FileDescriptor fileDescriptor);

    public static native void setAllowNetworkingForProcess(boolean z);

    public static boolean isWeaklyValidatedHostname(String str) {
        if (!str.matches("^[a-zA-Z0-9_.-]+$")) {
            return false;
        }
        for (int i : ADDRESS_FAMILIES) {
            if (Os.inet_pton(i, str) != null) {
                return false;
            }
        }
        return true;
    }
}
