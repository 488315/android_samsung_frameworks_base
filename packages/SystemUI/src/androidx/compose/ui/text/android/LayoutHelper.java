package androidx.compose.ui.text.android;

import android.text.Layout;
import android.text.TextUtils;
import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class LayoutHelper {
    public final boolean[] bidiProcessedParagraphs;
    public final Layout layout;
    public final List paragraphBidi;
    public final List paragraphEnds;
    public char[] tmpBuffer;

    public final class BidiRun {
        public final int end;
        public final boolean isRtl;
        public final int start;

        public BidiRun(int i, int i2, boolean z) {
            this.start = i;
            this.end = i2;
            this.isRtl = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BidiRun)) {
                return false;
            }
            BidiRun bidiRun = (BidiRun) obj;
            return this.start == bidiRun.start && this.end == bidiRun.end && this.isRtl == bidiRun.isRtl;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isRtl) + ReorderTile$$ExternalSyntheticOutline0.m(this.end, Integer.hashCode(this.start) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("BidiRun(start=");
            sb.append(this.start);
            sb.append(", end=");
            sb.append(this.end);
            sb.append(", isRtl=");
            return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.isRtl, ')');
        }
    }

    public LayoutHelper(Layout layout) {
        this.layout = layout;
        ArrayList arrayList = new ArrayList();
        int length = 0;
        do {
            int iIndexOf$default = StringsKt__StringsKt.indexOf$default(this.layout.getText(), '\n', length, 4);
            length = iIndexOf$default < 0 ? this.layout.getText().length() : iIndexOf$default + 1;
            arrayList.add(Integer.valueOf(length));
        } while (length < this.layout.getText().length());
        this.paragraphEnds = arrayList;
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(null);
        }
        this.paragraphBidi = arrayList2;
        this.bidiProcessedParagraphs = new boolean[((ArrayList) this.paragraphEnds).size()];
        ((ArrayList) this.paragraphEnds).size();
    }

    public final float getDownstreamHorizontal(int i, boolean z) {
        int lineEnd = this.layout.getLineEnd(this.layout.getLineForOffset(i));
        if (i > lineEnd) {
            i = lineEnd;
        }
        return z ? this.layout.getPrimaryHorizontal(i) : this.layout.getSecondaryHorizontal(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float getHorizontalPosition(int i, boolean z, boolean z2) {
        boolean z3;
        Bidi bidi;
        Bidi bidi2;
        boolean z4;
        int i2;
        int i3;
        int iLineEndToVisibleEnd = i;
        if (!z2) {
            return getDownstreamHorizontal(i, z);
        }
        int lineForOffset = LayoutCompat_androidKt.getLineForOffset(this.layout, iLineEndToVisibleEnd, z2);
        int lineStart = this.layout.getLineStart(lineForOffset);
        int lineEnd = this.layout.getLineEnd(lineForOffset);
        if (iLineEndToVisibleEnd != lineStart && iLineEndToVisibleEnd != lineEnd) {
            return getDownstreamHorizontal(i, z);
        }
        if (iLineEndToVisibleEnd == 0 || iLineEndToVisibleEnd == this.layout.getText().length()) {
            return getDownstreamHorizontal(i, z);
        }
        int iBinarySearch$default = CollectionsKt__CollectionsKt.binarySearch$default(this.paragraphEnds, Integer.valueOf(iLineEndToVisibleEnd));
        int i4 = iBinarySearch$default < 0 ? -(iBinarySearch$default + 1) : iBinarySearch$default + 1;
        if (z2 && i4 > 0) {
            int i5 = i4 - 1;
            if (iLineEndToVisibleEnd == ((Number) ((ArrayList) this.paragraphEnds).get(i5)).intValue()) {
                i4 = i5;
            }
        }
        boolean z5 = false;
        boolean z6 = this.layout.getParagraphDirection(this.layout.getLineForOffset(getParagraphStart(i4))) == -1;
        int iLineEndToVisibleEnd2 = lineEndToVisibleEnd(lineEnd, lineStart);
        int paragraphStart = getParagraphStart(i4);
        int i6 = lineStart - paragraphStart;
        int i7 = iLineEndToVisibleEnd2 - paragraphStart;
        boolean[] zArr = this.bidiProcessedParagraphs;
        if (zArr[i4]) {
            bidi2 = (Bidi) ((ArrayList) this.paragraphBidi).get(i4);
        } else {
            int iIntValue = i4 == 0 ? 0 : ((Number) ((ArrayList) this.paragraphEnds).get(i4 - 1)).intValue();
            int iIntValue2 = ((Number) ((ArrayList) this.paragraphEnds).get(i4)).intValue();
            int i8 = iIntValue2 - iIntValue;
            char[] cArr = this.tmpBuffer;
            if (cArr == null || cArr.length < i8) {
                cArr = new char[i8];
            }
            TextUtils.getChars(this.layout.getText(), iIntValue, iIntValue2, cArr, 0);
            if (Bidi.requiresBidi(cArr, 0, i8)) {
                Bidi bidi3 = new Bidi(cArr, 0, null, 0, i8, this.layout.getParagraphDirection(this.layout.getLineForOffset(getParagraphStart(i4))) == -1 ? 1 : 0);
                z3 = true;
                if (bidi3.getRunCount() != 1) {
                    bidi = bidi3;
                }
                ((ArrayList) this.paragraphBidi).set(i4, bidi);
                zArr[i4] = z3;
                if (bidi != null) {
                    char[] cArr2 = this.tmpBuffer;
                    cArr = cArr == cArr2 ? null : cArr2;
                }
                this.tmpBuffer = cArr;
                bidi2 = bidi;
            } else {
                z3 = true;
            }
            bidi = null;
            ((ArrayList) this.paragraphBidi).set(i4, bidi);
            zArr[i4] = z3;
            if (bidi != null) {
            }
            this.tmpBuffer = cArr;
            bidi2 = bidi;
        }
        Bidi bidiCreateLineBidi = bidi2 != null ? bidi2.createLineBidi(i6, i7) : null;
        if (bidiCreateLineBidi == null) {
            z4 = true;
        } else {
            if (bidiCreateLineBidi.getRunCount() != 1) {
                int runCount = bidiCreateLineBidi.getRunCount();
                BidiRun[] bidiRunArr = new BidiRun[runCount];
                for (int i9 = 0; i9 < runCount; i9++) {
                    bidiRunArr[i9] = new BidiRun(bidiCreateLineBidi.getRunStart(i9) + lineStart, bidiCreateLineBidi.getRunLimit(i9) + lineStart, bidiCreateLineBidi.getRunLevel(i9) % 2 == 1);
                }
                int runCount2 = bidiCreateLineBidi.getRunCount();
                byte[] bArr = new byte[runCount2];
                for (int i10 = 0; i10 < runCount2; i10++) {
                    bArr[i10] = (byte) bidiCreateLineBidi.getRunLevel(i10);
                }
                Bidi.reorderVisually(bArr, 0, bidiRunArr, 0, runCount);
                if (iLineEndToVisibleEnd == lineStart) {
                    int i11 = 0;
                    while (true) {
                        if (i11 >= runCount) {
                            i3 = -1;
                            break;
                        }
                        if (bidiRunArr[i11].start == iLineEndToVisibleEnd) {
                            i3 = i11;
                            break;
                        }
                        i11++;
                    }
                    BidiRun bidiRun = bidiRunArr[i3];
                    if (!z && z6 != bidiRun.isRtl) {
                        z5 = z6;
                    } else if (!z6) {
                        z5 = true;
                    }
                    return (i3 == 0 && z5) ? this.layout.getLineLeft(lineForOffset) : (i3 != runCount + (-1) || z5) ? z5 ? this.layout.getPrimaryHorizontal(bidiRunArr[i3 - 1].start) : this.layout.getPrimaryHorizontal(bidiRunArr[i3 + 1].start) : this.layout.getLineRight(lineForOffset);
                }
                if (iLineEndToVisibleEnd > iLineEndToVisibleEnd2) {
                    iLineEndToVisibleEnd = lineEndToVisibleEnd(iLineEndToVisibleEnd, lineStart);
                }
                int i12 = 0;
                while (true) {
                    if (i12 >= runCount) {
                        i2 = -1;
                        break;
                    }
                    if (bidiRunArr[i12].end == iLineEndToVisibleEnd) {
                        i2 = i12;
                        break;
                    }
                    i12++;
                }
                BidiRun bidiRun2 = bidiRunArr[i2];
                if (z || z6 == bidiRun2.isRtl) {
                    z5 = z6;
                } else if (!z6) {
                    z5 = true;
                }
                return (i2 == 0 && z5) ? this.layout.getLineLeft(lineForOffset) : (i2 != runCount + (-1) || z5) ? z5 ? this.layout.getPrimaryHorizontal(bidiRunArr[i2 - 1].end) : this.layout.getPrimaryHorizontal(bidiRunArr[i2 + 1].end) : this.layout.getLineRight(lineForOffset);
            }
            z4 = true;
        }
        boolean zIsRtlCharAt = this.layout.isRtlCharAt(lineStart);
        if (z || z6 == zIsRtlCharAt) {
            z6 = !z6 ? z4 : false;
        }
        boolean z7 = iLineEndToVisibleEnd == lineStart ? z6 : !z6 ? z4 : false;
        Layout layout = this.layout;
        return z7 ? layout.getLineLeft(lineForOffset) : layout.getLineRight(lineForOffset);
    }

    public final int getParagraphStart(int i) {
        if (i == 0) {
            return 0;
        }
        return ((Number) ((ArrayList) this.paragraphEnds).get(i - 1)).intValue();
    }

    public final int lineEndToVisibleEnd(int i, int i2) {
        while (i > i2) {
            char cCharAt = this.layout.getText().charAt(i - 1);
            if (cCharAt != ' ' && cCharAt != '\n' && cCharAt != 5760 && ((Intrinsics.compare(cCharAt, 8192) < 0 || Intrinsics.compare(cCharAt, 8202) > 0 || cCharAt == 8199) && cCharAt != 8287 && cCharAt != 12288)) {
                return i;
            }
            i--;
        }
        return i;
    }
}
