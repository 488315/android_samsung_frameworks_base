package com.android.server.knox.dar;

import android.os.Binder;
import com.android.server.knox.dar.sdp.SDPLog;
import java.security.SecureRandom;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class SecureUtil {
    public static void clear(String str) {
    }

    public static boolean isFailed(Object obj) {
        return ((obj instanceof Boolean) && !((Boolean) obj).booleanValue()) || ((obj instanceof Integer) && ((Integer) obj).intValue() != 0);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || ((obj instanceof byte[]) && ((byte[]) obj).length == 0) || ((obj instanceof String) && ((String) obj).isEmpty());
    }

    public static boolean isAnyoneEmptyHere(Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return true;
        }
        for (Object obj : objArr) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    public static void clearAll(Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return;
        }
        for (Object obj : objArr) {
            if (obj instanceof byte[]) {
                clear((byte[]) obj);
            } else if (obj instanceof String) {
                clear((String) obj);
            }
        }
    }

    public static void clear(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        Arrays.fill(bArr, 0, bArr.length, (byte) 0);
    }

    public static byte[] generateRandomBytes(int i) {
        return new SecureRandom().generateSeed(i);
    }

    public static boolean record(boolean z) {
        if (!z) {
            SDPLog.m40e(new Exception(String.format("Unexpected failure with a process [ UID : %d, PID : %d ]", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()))));
        }
        return z;
    }
}
