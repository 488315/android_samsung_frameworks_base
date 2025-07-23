package android.security.keystore;

import java.util.Date;

/* loaded from: classes3.dex */
public final class Utils {
    private Utils() {
    }

    static Date cloneIfNotNull(Date date) {
        if (date != null) {
            return (Date) date.clone();
        }
        return null;
    }

    static byte[] cloneIfNotNull(byte[] bArr) {
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    static int[] cloneIfNotNull(int[] iArr) {
        if (iArr != null) {
            return (int[]) iArr.clone();
        }
        return null;
    }
}
