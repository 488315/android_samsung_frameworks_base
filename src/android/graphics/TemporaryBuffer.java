package android.graphics;

import com.android.internal.util.ArrayUtils;

/* loaded from: classes.dex */
public class TemporaryBuffer {
    private static char[] sTemp;

    public static char[] obtain(int i) {
        char[] cArr;
        synchronized (TemporaryBuffer.class) {
            cArr = sTemp;
            sTemp = null;
        }
        return (cArr == null || cArr.length < i) ? ArrayUtils.newUnpaddedCharArray(i) : cArr;
    }

    public static void recycle(char[] cArr) {
        if (cArr.length > 1000) {
            return;
        }
        synchronized (TemporaryBuffer.class) {
            sTemp = cArr;
        }
    }
}
