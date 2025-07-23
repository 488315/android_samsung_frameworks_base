package android.graphics.text;

/* loaded from: classes.dex */
public class GraphemeBreak {
    private static native void nIsGraphemeBreak(float[] fArr, char[] cArr, int i, int i2, boolean[] zArr);

    private GraphemeBreak() {
    }

    public static void isGraphemeBreak(float[] fArr, char[] cArr, int i, int i2, boolean[] zArr) {
        if (i > i2) {
            throw new IllegalArgumentException("start is greater than end: start = " + i + " end = " + i2);
        }
        if (fArr.length < i2) {
            throw new IllegalArgumentException("the length of advances is less than endadvances.length = " + fArr.length + " end = " + i2);
        }
        int i3 = i2 - i;
        if (zArr.length < i3) {
            throw new IndexOutOfBoundsException("isGraphemeBreak doesn't have enough space to receive the result, isGraphemeBreak.length: " + zArr.length + " needed space: " + i3);
        }
        nIsGraphemeBreak(fArr, cArr, i, i2, zArr);
    }
}
