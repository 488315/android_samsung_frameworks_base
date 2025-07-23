package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CharsKt__CharJVMKt {
    public static void checkRadix(int i) {
        if (2 > i || i >= 37) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "radix ", " was not in valid range ");
            m.append(new IntRange(2, 36));
            throw new IllegalArgumentException(m.toString());
        }
    }

    public static boolean isWhitespace(char c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }
}
