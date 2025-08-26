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

/* loaded from: classes.dex */
public abstract class HandwritingGesture_androidKt {
    /* renamed from: access$getOffsetForHandwritingGesture-d-4ec7I, reason: not valid java name */
    public static final int m216access$getOffsetForHandwritingGestured4ec7I(LegacyTextFieldState legacyTextFieldState, long j, ViewConfiguration viewConfiguration) {
        TextLayoutResult textLayoutResult;
        MultiParagraph multiParagraph;
        LayoutCoordinates layoutCoordinates;
        long jMo618screenToLocalMKHz9U;
        int iM218getLineForHandwritingGestured4ec7I;
        TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
        if (layoutResult == null || (textLayoutResult = layoutResult.value) == null || (multiParagraph = textLayoutResult.multiParagraph) == null || (layoutCoordinates = legacyTextFieldState.getLayoutCoordinates()) == null || (iM218getLineForHandwritingGestured4ec7I = m218getLineForHandwritingGestured4ec7I(multiParagraph, (jMo618screenToLocalMKHz9U = layoutCoordinates.mo618screenToLocalMKHz9U(j)), viewConfiguration)) == -1) {
            return -1;
        }
        return multiParagraph.m736getOffsetForPositionk4lQ0M(Offset.m396copydBAh8RU$default(jMo618screenToLocalMKHz9U, (multiParagraph.getLineBottom(iM218getLineForHandwritingGestured4ec7I) + multiParagraph.getLineTop(iM218getLineForHandwritingGestured4ec7I)) / 2.0f, 1));
    }

    /* renamed from: access$getRangeForScreenRects-O048IG0, reason: not valid java name */
    public static final long m217access$getRangeForScreenRectsO048IG0(LegacyTextFieldState legacyTextFieldState, Rect rect, Rect rect2, int i, TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 textInclusionStrategy$Companion$$ExternalSyntheticLambda0) {
        long jM219getRangeForScreenRectOH9lIzo = m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, rect, i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
        if (TextRange.m749getCollapsedimpl(jM219getRangeForScreenRectOH9lIzo)) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        long jM219getRangeForScreenRectOH9lIzo2 = m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, rect2, i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
        if (TextRange.m749getCollapsedimpl(jM219getRangeForScreenRectOH9lIzo2)) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        int i2 = (int) (jM219getRangeForScreenRectOH9lIzo >> 32);
        int i3 = (int) (jM219getRangeForScreenRectOH9lIzo2 & 4294967295L);
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
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: getLineForHandwritingGesture-d-4ec7I, reason: not valid java name */
    public static final int m218getLineForHandwritingGestured4ec7I(MultiParagraph multiParagraph, long j, ViewConfiguration viewConfiguration) {
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
    public static final long m219getRangeForScreenRectOH9lIzo(LegacyTextFieldState legacyTextFieldState, Rect rect, int i, TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 textInclusionStrategy$Companion$$ExternalSyntheticLambda0) {
        TextLayoutResult textLayoutResult;
        TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
        MultiParagraph multiParagraph = (layoutResult == null || (textLayoutResult = layoutResult.value) == null) ? null : textLayoutResult.multiParagraph;
        LayoutCoordinates layoutCoordinates = legacyTextFieldState.getLayoutCoordinates();
        if (multiParagraph == null || layoutCoordinates == null) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        Offset.Companion.getClass();
        return multiParagraph.m737getRangeForRect86BmAI(rect.m412translatek4lQ0M(layoutCoordinates.mo618screenToLocalMKHz9U(0L)), i, textInclusionStrategy$Companion$$ExternalSyntheticLambda0);
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
