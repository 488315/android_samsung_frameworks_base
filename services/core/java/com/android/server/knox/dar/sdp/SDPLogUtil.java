package com.android.server.knox.dar.sdp;

import android.os.Binder;
import android.os.Process;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class SDPLogUtil {
    public static String safe(String str) {
        return str != null ? str : "null";
    }

    public static String makeDebugMessage(String str) {
        return getCurrentTime() + ",D," + safe(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] makeInfoMessages(String str, Throwable th) {
        String str2;
        String str3;
        String str4 = getCurrentTime() + ",I,";
        if (th != null) {
            try {
                StackTraceElement[] stackTrace = th.getStackTrace();
                String str5 = "()";
                if (stackTrace.length > 1) {
                    str2 = stackTrace[1].getClassName() + "." + stackTrace[1].getMethodName() + "()";
                } else {
                    str2 = "()";
                }
                if (stackTrace.length > 2) {
                    str5 = stackTrace[2].getClassName() + "." + stackTrace[2].getMethodName() + "()";
                }
                String valueOf = String.valueOf(Binder.getCallingUid());
                String valueOf2 = String.valueOf(Binder.getCallingPid());
                String valueOf3 = String.valueOf(Process.myTid());
                str3 = str4 + makePairs("UserId", String.valueOf(Binder.getCallingUserHandle().getIdentifier()), "UID", valueOf, "PID", valueOf2, "TID", valueOf3, "Curr", str2, "Prev", str5);
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
            if (str != null) {
                return new String[]{str3};
            }
            return new String[]{str4 + str, str3};
        }
        str3 = str4;
        if (str != null) {
        }
    }

    public static String[] makeErrorMessages(String str, Exception exc) {
        String str2 = getCurrentTime() + ",E,";
        int i = 0;
        String[] strArr = new String[0];
        if (exc == null) {
            return strArr;
        }
        try {
            StackTraceElement[] stackTrace = exc.getStackTrace();
            int i2 = 1;
            strArr = new String[(str != null ? 1 : 0) + 1 + stackTrace.length];
            if (str != null) {
                strArr[0] = str2 + str;
            } else {
                i2 = 0;
            }
            int i3 = i2 + 1;
            strArr[i2] = str2 + exc.toString();
            int length = stackTrace.length;
            while (i < length) {
                int i4 = i3 + 1;
                strArr[i3] = str2 + stackTrace[i].toString();
                i++;
                i3 = i4;
            }
        } catch (Exception unused) {
        }
        return strArr;
    }

    public static String makeParamMessage(String str, String str2) {
        return str + ",P," + str2;
    }

    public static String makePairs(Object... objArr) {
        String obj;
        if (objArr == null) {
            return "null";
        }
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
                obj = bytesToHex((byte[]) obj2);
            } else {
                obj = obj2.toString();
            }
            if (z) {
                str = str + obj + " ]";
                sb.append(str);
            } else {
                str = "[ " + obj + " : ";
            }
            z = !z;
        }
        if (z) {
            sb.append(str + "]");
        }
        return sb.toString();
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format((Object) new Date(System.currentTimeMillis()));
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
