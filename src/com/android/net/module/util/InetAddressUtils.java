package com.android.net.module.util;

import android.os.Parcel;
import android.util.Log;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes6.dex */
public class InetAddressUtils {
    private static final int INET4_ADDR_LENGTH = 4;
    private static final int INET6_ADDR_LENGTH = 16;
    private static final String TAG = "InetAddressUtils";

    public static void parcelInetAddress(Parcel parcel, InetAddress inetAddress, int i) {
        parcel.writeByteArray(inetAddress != null ? inetAddress.getAddress() : null);
        if (inetAddress instanceof Inet6Address) {
            Inet6Address inet6Address = (Inet6Address) inetAddress;
            boolean z = inet6Address.getScopeId() != 0;
            parcel.writeBoolean(z);
            if (z) {
                parcel.writeInt(inet6Address.getScopeId());
            }
        }
    }

    public static InetAddress unparcelInetAddress(Parcel parcel) {
        byte[] createByteArray = parcel.createByteArray();
        if (createByteArray == null) {
            return null;
        }
        try {
            if (createByteArray.length == 16) {
                return Inet6Address.getByAddress((String) null, createByteArray, parcel.readBoolean() ? parcel.readInt() : 0);
            }
            return InetAddress.getByAddress(createByteArray);
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    public static Inet6Address withScopeId(Inet6Address inet6Address, int i) {
        if (!inet6Address.isLinkLocalAddress()) {
            return inet6Address;
        }
        try {
            return Inet6Address.getByAddress((String) null, inet6Address.getAddress(), i);
        } catch (UnknownHostException e) {
            Log.wtf(TAG, "Cannot construct scoped Inet6Address with Inet6Address.getAddress(" + inet6Address.getHostAddress() + "): ", e);
            return null;
        }
    }

    public static Inet6Address v4MappedV6Address(Inet4Address inet4Address) {
        byte[] bArr = new byte[16];
        bArr[10] = -1;
        bArr[11] = -1;
        System.arraycopy(inet4Address.getAddress(), 0, bArr, 12, 4);
        try {
            return Inet6Address.getByAddress((String) null, bArr, -1);
        } catch (UnknownHostException e) {
            Log.wtf(TAG, "Failed to generate v4-mapped v6 address from " + inet4Address, e);
            return null;
        }
    }

    private InetAddressUtils() {
    }
}
