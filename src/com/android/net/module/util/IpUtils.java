package com.android.net.module.util;

import android.hardware.gnss.V1_0.IGnss;
import android.system.OsConstants;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/* loaded from: classes6.dex */
public class IpUtils {
    private static int intAbs(short s) {
        return s & 65535;
    }

    public static boolean isValidUdpOrTcpPort(int i) {
        return i > 0 && i < 65536;
    }

    public static int checksum(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int iIntAbs = i + 65535;
        int iPosition = byteBuffer.position();
        byteBuffer.position(i2);
        ShortBuffer shortBufferAsShortBuffer = byteBuffer.asShortBuffer();
        byteBuffer.position(iPosition);
        int i4 = (i3 - i2) / 2;
        for (int i5 = 0; i5 < i4; i5++) {
            iIntAbs += intAbs(shortBufferAsShortBuffer.get(i5));
        }
        int i6 = i2 + (i4 * 2);
        if (i3 != i6) {
            short s = byteBuffer.get(i6);
            if (s < 0) {
                s = (short) (s + IGnss.GnssAidingData.DELETE_SVSTEER);
            }
            iIntAbs += s * IGnss.GnssAidingData.DELETE_SVSTEER;
        }
        int i7 = ((iIntAbs >> 16) & 65535) + (iIntAbs & 65535);
        return (((i7 >> 16) & 65535) + (i7 & 65535)) ^ 65535;
    }

    private static int pseudoChecksumIPv4(ByteBuffer byteBuffer, int i, int i2, int i3) {
        return i2 + i3 + intAbs(byteBuffer.getShort(i + 12)) + intAbs(byteBuffer.getShort(i + 14)) + intAbs(byteBuffer.getShort(i + 16)) + intAbs(byteBuffer.getShort(i + 18));
    }

    private static int pseudoChecksumIPv6(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int iIntAbs = i2 + i3;
        for (int i4 = 8; i4 < 40; i4 += 2) {
            iIntAbs += intAbs(byteBuffer.getShort(i + i4));
        }
        return iIntAbs;
    }

    private static byte ipversion(ByteBuffer byteBuffer, int i) {
        return (byte) ((byteBuffer.get(i) & (-16)) >> 4);
    }

    public static short ipChecksum(ByteBuffer byteBuffer, int i) {
        return (short) checksum(byteBuffer, 0, i, (((byte) (byteBuffer.get(i) & 15)) * 4) + i);
    }

    private static short transportChecksum(ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int iPseudoChecksumIPv6;
        if (i4 < 0) {
            throw new IllegalArgumentException("Transport length < 0: " + i4);
        }
        byte bIpversion = ipversion(byteBuffer, i2);
        if (bIpversion == 4) {
            iPseudoChecksumIPv6 = pseudoChecksumIPv4(byteBuffer, i2, i, i4);
        } else if (bIpversion == 6) {
            iPseudoChecksumIPv6 = pseudoChecksumIPv6(byteBuffer, i2, i, i4);
        } else {
            throw new UnsupportedOperationException("Checksum must be IPv4 or IPv6");
        }
        int iChecksum = checksum(byteBuffer, iPseudoChecksumIPv6, i3, i4 + i3);
        if (i == OsConstants.IPPROTO_UDP && iChecksum == 0) {
            iChecksum = -1;
        }
        return (short) iChecksum;
    }

    public static short udpChecksum(ByteBuffer byteBuffer, int i, int i2) {
        return transportChecksum(byteBuffer, OsConstants.IPPROTO_UDP, i, i2, intAbs(byteBuffer.getShort(i2 + 4)));
    }

    public static short tcpChecksum(ByteBuffer byteBuffer, int i, int i2, int i3) {
        return transportChecksum(byteBuffer, OsConstants.IPPROTO_TCP, i, i2, i3);
    }

    public static short icmpChecksum(ByteBuffer byteBuffer, int i, int i2) {
        return (short) checksum(byteBuffer, 0, i, i2 + i);
    }

    public static short icmpv6Checksum(ByteBuffer byteBuffer, int i, int i2, int i3) {
        return transportChecksum(byteBuffer, OsConstants.IPPROTO_ICMPV6, i, i2, i3);
    }

    public static String addressAndPortToString(InetAddress inetAddress, int i) {
        return String.format(inetAddress instanceof Inet6Address ? "[%s]:%d" : "%s:%d", inetAddress.getHostAddress(), Integer.valueOf(i));
    }
}
