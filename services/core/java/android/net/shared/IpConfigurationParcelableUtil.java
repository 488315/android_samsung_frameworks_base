package android.net.shared;

import android.net.InetAddresses;

import java.net.InetAddress;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IpConfigurationParcelableUtil {
    public static String parcelAddress(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        return inetAddress.getHostAddress();
    }

    public static InetAddress unparcelAddress(String str) {
        if (str == null) {
            return null;
        }
        return InetAddresses.parseNumericAddress(str);
    }
}
