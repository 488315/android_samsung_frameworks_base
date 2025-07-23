package androidx.compose.ui.platform;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AccessibilityIterators$AbstractTextSegmentIterator implements AccessibilityIterators$TextSegmentIterator {
    public final int[] segment = new int[2];
    public String text;

    public final int[] getRange(int i, int i2) {
        if (i < 0 || i2 < 0 || i == i2) {
            return null;
        }
        int[] iArr = this.segment;
        iArr[0] = i;
        iArr[1] = i2;
        return iArr;
    }
}
