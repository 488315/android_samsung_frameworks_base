package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import kotlin.ranges.IntRange;

/* loaded from: classes4.dex */
public class CharsKt__CharJVMKt {
    public static void checkRadix(int i) {
        if (2 > i || i >= 37) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "radix ", " was not in valid range ");
            sbM.append(new IntRange(2, 36));
            throw new IllegalArgumentException(sbM.toString());
        }
    }

    public static boolean isWhitespace(char c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }
}
