package android.net.vcn.util;

import android.os.ParcelUuid;
import com.android.net.module.util.HexDump;

/* loaded from: classes3.dex */
public class LogUtils {
    public static String getHashedSubscriptionGroup(ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return null;
        }
        return HexDump.toHexString(parcelUuid.hashCode());
    }
}
