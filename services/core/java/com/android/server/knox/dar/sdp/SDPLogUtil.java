package com.android.server.knox.dar.sdp;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class SDPLogUtil {
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                .format((Object) new Date(System.currentTimeMillis()));
    }

    public static String makeDebugMessage(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentTime());
        sb.append(",D,");
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        return sb.toString();
    }

    public static String makePairs(Object... objArr) {
        String obj;
        if (objArr.length == 0) {
            return "[:]";
        }
        StringBuilder sb = new StringBuilder((objArr.length + 1) >> 1);
        String str = "";
        boolean z = false;
        for (Object obj2 : objArr) {
            if (obj2 == null) {
                obj = "null";
            } else if (obj2 instanceof byte[]) {
                byte[] bArr = (byte[]) obj2;
                if (bArr.length == 0) {
                    obj = "";
                } else {
                    StringBuilder sb2 = new StringBuilder(bArr.length * 2);
                    int length = bArr.length;
                    for (int i = 0;
                            i < length;
                            i =
                                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                            "%02X",
                                            new Object[] {Byte.valueOf(bArr[i])}, sb2, i, 1)) {}
                    obj = sb2.toString();
                }
            } else {
                obj = obj2.toString();
            }
            if (z) {
                str = str + obj + " ]";
                sb.append(str);
            } else {
                str = XmlUtils$$ExternalSyntheticOutline0.m("[ ", obj, " : ");
            }
            z = !z;
        }
        if (z) {
            sb.append(str + "]");
        }
        return sb.toString();
    }
}
