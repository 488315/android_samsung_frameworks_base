package com.android.internal.org.bouncycastle.util;

/* loaded from: classes5.dex */
public class Objects {
    public static boolean areEqual(Object obj, Object obj2) {
        if (obj != obj2) {
            return (obj == null || obj2 == null || !obj.equals(obj2)) ? false : true;
        }
        return true;
    }

    public static int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
