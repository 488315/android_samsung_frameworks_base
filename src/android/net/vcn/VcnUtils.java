package android.net.vcn;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkSpecifier;
import android.net.TelephonyNetworkSpecifier;
import android.net.TransportInfo;
import android.net.wifi.WifiInfo;
import java.util.List;

/* loaded from: classes3.dex */
public class VcnUtils {
    public static WifiInfo getWifiInfoFromVcnCaps(ConnectivityManager connectivityManager, NetworkCapabilities networkCapabilities) {
        NetworkCapabilities vcnUnderlyingCaps = getVcnUnderlyingCaps(connectivityManager, networkCapabilities);
        if (vcnUnderlyingCaps == null) {
            return null;
        }
        TransportInfo transportInfo = vcnUnderlyingCaps.getTransportInfo();
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        return null;
    }

    public static int getSubIdFromVcnCaps(ConnectivityManager connectivityManager, NetworkCapabilities networkCapabilities) {
        NetworkCapabilities vcnUnderlyingCaps = getVcnUnderlyingCaps(connectivityManager, networkCapabilities);
        if (vcnUnderlyingCaps == null) {
            return -1;
        }
        NetworkSpecifier networkSpecifier = vcnUnderlyingCaps.getNetworkSpecifier();
        if (networkSpecifier instanceof TelephonyNetworkSpecifier) {
            return ((TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId();
        }
        return -1;
    }

    private static NetworkCapabilities getVcnUnderlyingCaps(ConnectivityManager connectivityManager, NetworkCapabilities networkCapabilities) {
        List underlyingNetworks;
        if (networkCapabilities.getTransportInfo() == null || !(networkCapabilities.getTransportInfo() instanceof VcnTransportInfo) || (underlyingNetworks = networkCapabilities.getUnderlyingNetworks()) == null) {
            return null;
        }
        return connectivityManager.getNetworkCapabilities((Network) underlyingNetworks.get(0));
    }
}
