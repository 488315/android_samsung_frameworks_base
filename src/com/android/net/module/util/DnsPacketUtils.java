package com.android.net.module.util;

import android.media.MediaMetrics;
import android.net.InetAddresses;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Patterns;
import com.android.net.module.util.DnsPacket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.FieldPosition;

/* loaded from: classes6.dex */
public final class DnsPacketUtils {

    public static class DnsRecordParser {
        private static final int MAXLABELCOUNT = 128;
        private static final int MAXLABELSIZE = 63;
        private static final int MAXNAMESIZE = 255;
        private static final DecimalFormat sByteFormat = new DecimalFormat();
        private static final FieldPosition sPos = new FieldPosition(0);

        static String labelToString(byte[] bArr) {
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArr) {
                int unsignedInt = Byte.toUnsignedInt(b);
                if (unsignedInt <= 32 || unsignedInt >= 127) {
                    stringBuffer.append('\\');
                    sByteFormat.format(unsignedInt, stringBuffer, sPos);
                } else if (unsignedInt == 34 || unsignedInt == 46 || unsignedInt == 59 || unsignedInt == 92 || unsignedInt == 40 || unsignedInt == 41 || unsignedInt == 64 || unsignedInt == 36) {
                    stringBuffer.append('\\');
                    stringBuffer.append((char) unsignedInt);
                } else {
                    stringBuffer.append((char) unsignedInt);
                }
            }
            return stringBuffer.toString();
        }

        public static byte[] domainNameToLabels(String str) throws ParseException, IOException {
            if (str.length() > 255) {
                throw new ParseException("Domain name exceeds max length: " + str.length());
            }
            if (!isHostName(str)) {
                throw new ParseException("Failed to parse domain name: " + str);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (String str2 : str.split("\\.")) {
                if (str2.length() > 63) {
                    throw new ParseException("label is too long: " + str2);
                }
                byteArrayOutputStream.write(str2.length());
                byteArrayOutputStream.write(str2.getBytes(StandardCharsets.UTF_8));
            }
            byteArrayOutputStream.write(0);
            return byteArrayOutputStream.toByteArray();
        }

        public static boolean isHostName(String str) {
            return (str == null || !Patterns.DOMAIN_NAME.matcher(str).matches() || InetAddresses.isNumericAddress(str)) ? false : true;
        }

        public static String parseName(ByteBuffer byteBuffer, int i, boolean z) throws BufferUnderflowException, DnsPacket.ParseException {
            return parseName(byteBuffer, i, 128, z);
        }

        public static String parseName(ByteBuffer byteBuffer, int i, int i2, boolean z) throws BufferUnderflowException, DnsPacket.ParseException {
            if (i > i2) {
                throw new DnsPacket.ParseException("Failed to parse name, too many labels");
            }
            int unsignedInt = Byte.toUnsignedInt(byteBuffer.get());
            int i3 = unsignedInt & 192;
            if (unsignedInt == 0) {
                return "";
            }
            if ((i3 != 0 && i3 != 192) || (!z && i3 == 192)) {
                throw new DnsPacket.ParseException("Parse name fail, bad label type: " + i3);
            }
            if (i3 == 192) {
                int unsignedInt2 = ((unsignedInt & (-193)) << 8) + Byte.toUnsignedInt(byteBuffer.get());
                int iPosition = byteBuffer.position();
                if (unsignedInt2 >= iPosition - 2) {
                    throw new DnsPacket.ParseException("Parse compression name fail, invalid compression");
                }
                byteBuffer.position(unsignedInt2);
                String name = parseName(byteBuffer, i + 1, i2, z);
                byteBuffer.position(iPosition);
                return name;
            }
            byte[] bArr = new byte[unsignedInt];
            byteBuffer.get(bArr);
            String strLabelToString = labelToString(bArr);
            if (strLabelToString.length() > 63) {
                throw new DnsPacket.ParseException("Parse name fail, invalid label length");
            }
            String name2 = parseName(byteBuffer, i + 1, i2, z);
            if (TextUtils.isEmpty(name2)) {
                return strLabelToString;
            }
            return strLabelToString + MediaMetrics.SEPARATOR + name2;
        }

        private DnsRecordParser() {
        }
    }

    private DnsPacketUtils() {
    }
}
