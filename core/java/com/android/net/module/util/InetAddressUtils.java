package com.android.net.module.util;

import android.os.Parcel;
import android.util.Log;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes5.dex */
public class InetAddressUtils {
    private static final int INET4_ADDR_LENGTH = 4;
    private static final int INET6_ADDR_LENGTH = 16;
    private static final String TAG = InetAddressUtils.class.getSimpleName();

    public static void parcelInetAddress(Parcel parcel, InetAddress address, int flags) {
        byte[] addressArray = address != null ? address.getAddress() : null;
        parcel.writeByteArray(addressArray);
        if (address instanceof Inet6Address) {
            Inet6Address v6Address = (Inet6Address) address;
            boolean hasScopeId = v6Address.getScopeId() != 0;
            parcel.writeBoolean(hasScopeId);
            if (hasScopeId) {
                parcel.writeInt(v6Address.getScopeId());
            }
        }
    }

    public static InetAddress unparcelInetAddress(Parcel in) {
        byte[] addressArray = in.createByteArray();
        if (addressArray == null) {
            return null;
        }
        try {
            if (addressArray.length == 16) {
                boolean hasScopeId = in.readBoolean();
                int scopeId = hasScopeId ? in.readInt() : 0;
                return Inet6Address.getByAddress((String) null, addressArray, scopeId);
            }
            return InetAddress.getByAddress(addressArray);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static Inet6Address withScopeId(Inet6Address addr, int scopeid) {
        if (!addr.isLinkLocalAddress()) {
            return addr;
        }
        try {
            return Inet6Address.getByAddress((String) null, addr.getAddress(), scopeid);
        } catch (UnknownHostException impossible) {
            Log.wtf(
                    TAG,
                    "Cannot construct scoped Inet6Address with Inet6Address.getAddress("
                            + addr.getHostAddress()
                            + "): ",
                    impossible);
            return null;
        }
    }

    public static Inet6Address v4MappedV6Address(Inet4Address v4Addr) {
        byte[] v6AddrBytes = new byte[16];
        v6AddrBytes[10] = -1;
        v6AddrBytes[11] = -1;
        System.arraycopy(v4Addr.getAddress(), 0, v6AddrBytes, 12, 4);
        try {
            return Inet6Address.getByAddress((String) null, v6AddrBytes, -1);
        } catch (UnknownHostException impossible) {
            Log.wtf(TAG, "Failed to generate v4-mapped v6 address from " + v4Addr, impossible);
            return null;
        }
    }

    private InetAddressUtils() {}
}
