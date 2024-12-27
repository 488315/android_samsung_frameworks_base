package com.android.server.knox.dar;

import android.os.Binder;

import com.android.server.knox.dar.sdp.SDPLog;

import java.security.SecureRandom;
import java.util.Arrays;

public abstract class SecureUtil {
    public static final SecureRandom sSecureRandom = new SecureRandom();

    public static void clear(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        Arrays.fill(bArr, 0, bArr.length, (byte) 0);
    }

    public static boolean isAnyoneEmptyHere(Object... objArr) {
        if (objArr.length == 0) {
            return true;
        }
        for (Object obj : objArr) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null
                || ((obj instanceof byte[]) && ((byte[]) obj).length == 0)
                || ((obj instanceof String) && ((String) obj).isEmpty());
    }

    public static boolean isFailed(Object obj) {
        return ((obj instanceof Boolean) && !((Boolean) obj).booleanValue())
                || ((obj instanceof Integer) && ((Integer) obj).intValue() != 0);
    }

    public static void record(boolean z) {
        if (z) {
            return;
        }
        SDPLog.e(
                new Exception(
                        String.format(
                                "Unexpected failure with a process [ UID : %d, PID : %d ]",
                                Integer.valueOf(Binder.getCallingUid()),
                                Integer.valueOf(Binder.getCallingPid()))),
                null,
                null);
    }
}
