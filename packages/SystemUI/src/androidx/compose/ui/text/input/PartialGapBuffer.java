package androidx.compose.ui.text.input;

import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PartialGapBuffer {
    public GapBuffer buffer;
    public String text;
    public int bufStart = -1;
    public int bufEnd = -1;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public PartialGapBuffer(String str) {
        this.text = str;
    }

    public final int getLength() {
        GapBuffer gapBuffer = this.buffer;
        if (gapBuffer == null) {
            return this.text.length();
        }
        return (gapBuffer.capacity - gapBuffer.gapLength()) + (this.text.length() - (this.bufEnd - this.bufStart));
    }

    public final void replace(int i, int i2, String str) {
        if (i > i2) {
            InlineClassHelperKt.throwIllegalArgumentException("start index must be less than or equal to end index: " + i + " > " + i2);
        }
        if (i < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("start must be non-negative, but was " + i);
        }
        GapBuffer gapBuffer = this.buffer;
        if (gapBuffer == null) {
            int iMax = Math.max(255, str.length() + 128);
            char[] cArr = new char[iMax];
            int iMin = Math.min(i, 64);
            int iMin2 = Math.min(this.text.length() - i2, 64);
            int i3 = i - iMin;
            this.text.getChars(i3, i, cArr, 0);
            int i4 = iMax - iMin2;
            int i5 = iMin2 + i2;
            this.text.getChars(i2, i5, cArr, i4);
            str.getChars(0, str.length(), cArr, iMin);
            this.buffer = new GapBuffer(cArr, str.length() + iMin, i4);
            this.bufStart = i3;
            this.bufEnd = i5;
            return;
        }
        int i6 = this.bufStart;
        int i7 = i - i6;
        int i8 = i2 - i6;
        if (i7 < 0 || i8 > gapBuffer.capacity - gapBuffer.gapLength()) {
            this.text = toString();
            this.buffer = null;
            this.bufStart = -1;
            this.bufEnd = -1;
            replace(i, i2, str);
            return;
        }
        int length = str.length() - (i8 - i7);
        if (length > gapBuffer.gapLength()) {
            int iGapLength = length - gapBuffer.gapLength();
            int i9 = gapBuffer.capacity;
            do {
                i9 *= 2;
            } while (i9 - gapBuffer.capacity < iGapLength);
            char[] cArr2 = new char[i9];
            System.arraycopy(gapBuffer.buffer, 0, cArr2, 0, gapBuffer.gapStart);
            int i10 = gapBuffer.capacity;
            int i11 = gapBuffer.gapEnd;
            int i12 = i10 - i11;
            int i13 = i9 - i12;
            System.arraycopy(gapBuffer.buffer, i11, cArr2, i13, (i12 + i11) - i11);
            gapBuffer.buffer = cArr2;
            gapBuffer.capacity = i9;
            gapBuffer.gapEnd = i13;
        }
        int i14 = gapBuffer.gapStart;
        if (i7 < i14 && i8 <= i14) {
            int i15 = i14 - i8;
            char[] cArr3 = gapBuffer.buffer;
            System.arraycopy(cArr3, i8, cArr3, gapBuffer.gapEnd - i15, i15);
            gapBuffer.gapStart = i7;
            gapBuffer.gapEnd -= i15;
        } else if (i7 >= i14 || i8 < i14) {
            int iGapLength2 = i7 + gapBuffer.gapLength();
            int iGapLength3 = i8 + gapBuffer.gapLength();
            int i16 = gapBuffer.gapEnd;
            int i17 = iGapLength2 - i16;
            char[] cArr4 = gapBuffer.buffer;
            System.arraycopy(cArr4, i16, cArr4, gapBuffer.gapStart, i17);
            gapBuffer.gapStart += i17;
            gapBuffer.gapEnd = iGapLength3;
        } else {
            gapBuffer.gapEnd = i8 + gapBuffer.gapLength();
            gapBuffer.gapStart = i7;
        }
        str.getChars(0, str.length(), gapBuffer.buffer, gapBuffer.gapStart);
        gapBuffer.gapStart = str.length() + gapBuffer.gapStart;
    }

    public final String toString() {
        GapBuffer gapBuffer = this.buffer;
        if (gapBuffer == null) {
            return this.text;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) this.text, 0, this.bufStart);
        sb.append(gapBuffer.buffer, 0, gapBuffer.gapStart);
        char[] cArr = gapBuffer.buffer;
        int i = gapBuffer.gapEnd;
        sb.append(cArr, i, gapBuffer.capacity - i);
        String str = this.text;
        sb.append((CharSequence) str, this.bufEnd, str.length());
        return sb.toString();
    }
}
