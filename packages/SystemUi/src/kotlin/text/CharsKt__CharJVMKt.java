package kotlin.text;

import android.support.v4.media.AbstractC0000x2c234b15;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CharsKt__CharJVMKt {
    public static final void checkRadix(int i) {
        IntRange intRange = new IntRange(2, 36);
        if (intRange.first <= i && i <= intRange.last) {
            return;
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("radix ", i, " was not in valid range ");
        m1m.append(new IntRange(2, 36));
        throw new IllegalArgumentException(m1m.toString());
    }

    public static final boolean isWhitespace(char c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }
}
