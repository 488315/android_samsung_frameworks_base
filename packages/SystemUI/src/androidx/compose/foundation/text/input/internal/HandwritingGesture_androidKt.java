package androidx.compose.foundation.text.input.internal;

import android.graphics.PointF;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextInclusionStrategy$Companion$$ExternalSyntheticLambda0;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class HandwritingGesture_androidKt {
    /* renamed from: access$getOffsetForHandwritingGesture-d-4ec7I, reason: not valid java name */
    public static final int m215access$getOffsetForHandwritingGestured4ec7I(LegacyTextFieldState legacyTextFieldState, long j, ViewConfiguration viewConfiguration) {
        TextLayoutResult textLayoutResult;
        MultiParagraph multiParagraph;
        LayoutCoordinates layoutCoordinates;
        long mo616screenToLocalMKHz9U;
        int m217getLineForHandwritingGestured4ec7I;
        TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
        if (layoutResult == null || (textLayoutResult = layoutResult.value) == null || (multiParagraph = textLayoutResult.multiParagraph) == null || (layoutCoordinates = legacyTextFieldState.getLayoutCoordinates()) == null || (m217getLineForHandwritingGestured4ec7I = m217getLineForHandwritingGestured4ec7I(multiParagraph, (mo616screenToLocalMKHz9U = layoutCoordinates.mo616screenToLocalMKHz9U(j)), viewConfiguration)) == -1) {
            return -1;
        }
        return multiParagraph.m734getOffsetForPositionk4lQ0M(Offset.m394copydBAh8RU$default(mo616screenToLocalMKHz9U, (multiParagraph.getLineBottom(m217getLineForHandwritingGestured4ec7I) + multiParagraph.getLineTop(m217getLineForHandwritingGestured4ec7I)) / 2.0f, 1));
    }

    /* renamed from: access$getRangeForScreenRects-O048IG0, reason: not valid java name */
    public static final long m216access$getRangeForScreenRectsO048IG0(LegacyTextFieldState legacyTextFieldState, Rect rect, Rect rect2, int i, TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 textInclusionStrategy$Companion$$ExternalSyntheticLambda0) {
        long m218getRangeForScreenRectOH9lIzo = m218getRangeForScreenRectOH9lIzo(legacyTextFieldState, rect, i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
        if (TextRange.m747getCollapsedimpl(m218getRangeForScreenRectOH9lIzo)) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        long m218getRangeForScreenRectOH9lIzo2 = m218getRangeForScreenRectOH9lIzo(legacyTextFieldState, rect2, i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
        if (TextRange.m747getCollapsedimpl(m218getRangeForScreenRectOH9lIzo2)) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        int i2 = (int) (m218getRangeForScreenRectOH9lIzo >> 32);
        int i3 = (int) (m218getRangeForScreenRectOH9lIzo2 & 4294967295L);
        return TextRangeKt.TextRange(Math.min(i2, i2), Math.max(i3, i3));
    }

    public static final boolean access$isBiDiBoundary(TextLayoutResult textLayoutResult, int i) {
        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
        int lineForOffset = multiParagraph.getLineForOffset(i);
        return i == textLayoutResult.getLineStart(lineForOffset) || i == multiParagraph.getLineEnd(lineForOffset, false) ? textLayoutResult.getParagraphDirection(i) != textLayoutResult.getBidiRunDirection(i) : textLayoutResult.getBidiRunDirection(i) != textLayoutResult.getBidiRunDirection(i - 1);
    }

    public static final long access$toOffset(PointF pointF) {
        float f = pointF.x;
        float f2 = pointF.y;
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: getLineForHandwritingGesture-d-4ec7I, reason: not valid java name */
    public static final int m217getLineForHandwritingGestured4ec7I(MultiParagraph multiParagraph, long j, ViewConfiguration viewConfiguration) {
        float handwritingGestureLineMargin = viewConfiguration != null ? viewConfiguration.getHandwritingGestureLineMargin() : 0.0f;
        int i = (int) (4294967295L & j);
        int lineForVerticalPosition = multiParagraph.getLineForVerticalPosition(Float.intBitsToFloat(i));
        if (Float.intBitsToFloat(i) < multiParagraph.getLineTop(lineForVerticalPosition) - handwritingGestureLineMargin || Float.intBitsToFloat(i) > multiParagraph.getLineBottom(lineForVerticalPosition) + handwritingGestureLineMargin) {
            return -1;
        }
        int i2 = (int) (j >> 32);
        if (Float.intBitsToFloat(i2) < (-handwritingGestureLineMargin) || Float.intBitsToFloat(i2) > multiParagraph.width + handwritingGestureLineMargin) {
            return -1;
        }
        return lineForVerticalPosition;
    }

    /* renamed from: getRangeForScreenRect-OH9lIzo, reason: not valid java name */
    public static final long m218getRangeForScreenRectOH9lIzo(LegacyTextFieldState legacyTextFieldState, Rect rect, int i, TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 textInclusionStrategy$Companion$$ExternalSyntheticLambda0) {
        TextLayoutResult textLayoutResult;
        TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
        MultiParagraph multiParagraph = (layoutResult == null || (textLayoutResult = layoutResult.value) == null) ? null : textLayoutResult.multiParagraph;
        LayoutCoordinates layoutCoordinates = legacyTextFieldState.getLayoutCoordinates();
        if (multiParagraph == null || layoutCoordinates == null) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        Offset.Companion.getClass();
        return multiParagraph.m735getRangeForRect86BmAI(rect.m410translatek4lQ0M(layoutCoordinates.mo616screenToLocalMKHz9U(0L)), i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
    }

    public static final boolean isPunctuation(int i) {
        int type = Character.getType(i);
        return type == 23 || type == 20 || type == 22 || type == 30 || type == 29 || type == 24 || type == 21;
    }

    public static final boolean isWhitespace(int i) {
        return Character.isWhitespace(i) || i == 160;
    }

    public static final boolean isWhitespaceExceptNewline(int i) {
        int type;
        return (!isWhitespace(i) || (type = Character.getType(i)) == 14 || type == 13 || i == 10) ? false : true;
    }
}
