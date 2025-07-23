package android.graphics.text;

import android.graphics.Paint;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class TextRunShaper {
    private static native long nativeShapeTextRun(String str, int i, int i2, int i3, int i4, boolean z, long j);

    private static native long nativeShapeTextRun(char[] cArr, int i, int i2, int i3, int i4, boolean z, long j);

    private TextRunShaper() {
    }

    public static PositionedGlyphs shapeTextRun(char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        Preconditions.checkNotNull(cArr);
        Preconditions.checkNotNull(paint);
        return new PositionedGlyphs(nativeShapeTextRun(cArr, i, i2, i3, i4, z, paint.getNativeInstance()), f, f2);
    }

    public static PositionedGlyphs shapeTextRun(CharSequence charSequence, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(paint);
        if (charSequence instanceof String) {
            return new PositionedGlyphs(nativeShapeTextRun((String) charSequence, i, i2, i3, i4, z, paint.getNativeInstance()), f, f2);
        }
        char[] cArr = new char[i4];
        TextUtils.getChars(charSequence, i3, i3 + i4, cArr, 0);
        return new PositionedGlyphs(nativeShapeTextRun(cArr, i - i3, i2, 0, i4, z, paint.getNativeInstance()), f, f2);
    }
}
