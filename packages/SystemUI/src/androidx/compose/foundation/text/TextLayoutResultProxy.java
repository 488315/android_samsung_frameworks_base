package androidx.compose.foundation.text;

import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.text.TextLayoutResult;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextLayoutResultProxy {
    public LayoutCoordinates decorationBoxCoordinates;
    public LayoutCoordinates innerTextFieldCoordinates;
    public final TextLayoutResult value;

    public TextLayoutResultProxy(TextLayoutResult textLayoutResult, LayoutCoordinates layoutCoordinates, LayoutCoordinates layoutCoordinates2) {
        this.value = textLayoutResult;
        this.innerTextFieldCoordinates = layoutCoordinates;
        this.decorationBoxCoordinates = layoutCoordinates2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001d, code lost:
    
        if (r5 == null) goto L12;
     */
    /* renamed from: coercedInVisibleBoundsOfInputText-MK-Hz9U, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long m207coercedInVisibleBoundsOfInputTextMKHz9U(long r6) {
        /*
            r5 = this;
            androidx.compose.ui.layout.LayoutCoordinates r0 = r5.innerTextFieldCoordinates
            if (r0 == 0) goto L1f
            boolean r1 = r0.isAttached()
            if (r1 == 0) goto L16
            androidx.compose.ui.layout.LayoutCoordinates r5 = r5.decorationBoxCoordinates
            if (r5 == 0) goto L14
            r1 = 1
            androidx.compose.ui.geometry.Rect r5 = r5.localBoundingBoxOf(r0, r1)
            goto L1d
        L14:
            r5 = 0
            goto L1d
        L16:
            androidx.compose.ui.geometry.Rect$Companion r5 = androidx.compose.ui.geometry.Rect.Companion
            r5.getClass()
            androidx.compose.ui.geometry.Rect r5 = androidx.compose.ui.geometry.Rect.Zero
        L1d:
            if (r5 != 0) goto L26
        L1f:
            androidx.compose.ui.geometry.Rect$Companion r5 = androidx.compose.ui.geometry.Rect.Companion
            r5.getClass()
            androidx.compose.ui.geometry.Rect r5 = androidx.compose.ui.geometry.Rect.Zero
        L26:
            r0 = 32
            long r1 = r6 >> r0
            int r1 = (int) r1
            float r2 = java.lang.Float.intBitsToFloat(r1)
            float r3 = r5.left
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L36
            goto L45
        L36:
            float r2 = java.lang.Float.intBitsToFloat(r1)
            float r3 = r5.right
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L41
            goto L45
        L41:
            float r3 = java.lang.Float.intBitsToFloat(r1)
        L45:
            r1 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r6 = r6 & r1
            int r6 = (int) r6
            float r7 = java.lang.Float.intBitsToFloat(r6)
            float r4 = r5.top
            int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r7 >= 0) goto L57
            goto L66
        L57:
            float r7 = java.lang.Float.intBitsToFloat(r6)
            float r4 = r5.bottom
            int r5 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r5 <= 0) goto L62
            goto L66
        L62:
            float r4 = java.lang.Float.intBitsToFloat(r6)
        L66:
            int r5 = java.lang.Float.floatToRawIntBits(r3)
            long r5 = (long) r5
            int r7 = java.lang.Float.floatToRawIntBits(r4)
            long r3 = (long) r7
            long r5 = r5 << r0
            long r0 = r3 & r1
            long r5 = r5 | r0
            androidx.compose.ui.geometry.Offset$Companion r7 = androidx.compose.ui.geometry.Offset.Companion
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextLayoutResultProxy.m207coercedInVisibleBoundsOfInputTextMKHz9U(long):long");
    }

    /* renamed from: getOffsetForPosition-3MmeM6k, reason: not valid java name */
    public final int m208getOffsetForPosition3MmeM6k(long j, boolean z) {
        if (z) {
            j = m207coercedInVisibleBoundsOfInputTextMKHz9U(j);
        }
        return this.value.multiParagraph.m734getOffsetForPositionk4lQ0M(m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release(j));
    }

    /* renamed from: isPositionOnText-k-4lQ0M, reason: not valid java name */
    public final boolean m209isPositionOnTextk4lQ0M(long j) {
        long m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release = m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release(m207coercedInVisibleBoundsOfInputTextMKHz9U(j));
        float intBitsToFloat = Float.intBitsToFloat((int) (4294967295L & m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release));
        TextLayoutResult textLayoutResult = this.value;
        int lineForVerticalPosition = textLayoutResult.multiParagraph.getLineForVerticalPosition(intBitsToFloat);
        int i = (int) (m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release >> 32);
        return Float.intBitsToFloat(i) >= textLayoutResult.getLineLeft(lineForVerticalPosition) && Float.intBitsToFloat(i) <= textLayoutResult.getLineRight(lineForVerticalPosition);
    }

    /* renamed from: translateDecorationToInnerCoordinates-MK-Hz9U$foundation_release, reason: not valid java name */
    public final long m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release(long j) {
        LayoutCoordinates layoutCoordinates;
        LayoutCoordinates layoutCoordinates2 = this.innerTextFieldCoordinates;
        if (layoutCoordinates2 != null) {
            if (!layoutCoordinates2.isAttached()) {
                layoutCoordinates2 = null;
            }
            if (layoutCoordinates2 != null && (layoutCoordinates = this.decorationBoxCoordinates) != null) {
                LayoutCoordinates layoutCoordinates3 = layoutCoordinates.isAttached() ? layoutCoordinates : null;
                if (layoutCoordinates3 != null) {
                    return layoutCoordinates2.mo611localPositionOfR5De75A(layoutCoordinates3, j);
                }
            }
        }
        return j;
    }

    /* renamed from: translateInnerToDecorationCoordinates-MK-Hz9U$foundation_release, reason: not valid java name */
    public final long m211translateInnerToDecorationCoordinatesMKHz9U$foundation_release(long j) {
        LayoutCoordinates layoutCoordinates;
        LayoutCoordinates layoutCoordinates2 = this.innerTextFieldCoordinates;
        if (layoutCoordinates2 != null) {
            if (!layoutCoordinates2.isAttached()) {
                layoutCoordinates2 = null;
            }
            if (layoutCoordinates2 != null && (layoutCoordinates = this.decorationBoxCoordinates) != null) {
                LayoutCoordinates layoutCoordinates3 = layoutCoordinates.isAttached() ? layoutCoordinates : null;
                if (layoutCoordinates3 != null) {
                    return layoutCoordinates3.mo611localPositionOfR5De75A(layoutCoordinates2, j);
                }
            }
        }
        return j;
    }

    public /* synthetic */ TextLayoutResultProxy(TextLayoutResult textLayoutResult, LayoutCoordinates layoutCoordinates, LayoutCoordinates layoutCoordinates2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(textLayoutResult, (i & 2) != 0 ? null : layoutCoordinates, (i & 4) != 0 ? null : layoutCoordinates2);
    }
}
