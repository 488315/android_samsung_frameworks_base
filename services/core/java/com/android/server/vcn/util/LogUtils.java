package com.android.server.vcn.util;

import android.os.ParcelUuid;
import com.android.internal.util.HexDump;

/* loaded from: classes3.dex */
public abstract class LogUtils {
    public static String getHashedSubscriptionGroup(ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return null;
        }
        return HexDump.toHexString(parcelUuid.hashCode());
    }
}
