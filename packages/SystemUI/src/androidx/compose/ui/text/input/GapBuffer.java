package androidx.compose.ui.text.input;

/* loaded from: classes.dex */
final class GapBuffer {
    public char[] buffer;
    public int capacity;
    public int gapEnd;
    public int gapStart;

    public GapBuffer(char[] cArr, int i, int i2) {
        this.capacity = cArr.length;
        this.buffer = cArr;
        this.gapStart = i;
        this.gapEnd = i2;
    }

    public final int gapLength() {
        return this.gapEnd - this.gapStart;
    }

    public final String toString() {
        return "";
    }
}
