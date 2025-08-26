package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.StringHelpersKt;
import androidx.compose.foundation.text.selection.BaseTextPreparedSelection;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class BaseTextPreparedSelection<T extends BaseTextPreparedSelection<T>> {
    public final AnnotatedString annotatedString;
    public final TextLayoutResult layoutResult;
    public final OffsetMapping offsetMapping;
    public final long originalSelection;
    public final AnnotatedString originalText;
    public long selection;
    public final TextPreparedSelectionState state;

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

    public /* synthetic */ BaseTextPreparedSelection(AnnotatedString annotatedString, long j, TextLayoutResult textLayoutResult, OffsetMapping offsetMapping, TextPreparedSelectionState textPreparedSelectionState, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, j, textLayoutResult, offsetMapping, textPreparedSelectionState);
    }

    public final Integer getLineEndByOffset() {
        TextLayoutResult textLayoutResult = this.layoutResult;
        if (textLayoutResult == null) {
            return null;
        }
        int iM751getMaximpl = TextRange.m751getMaximpl(this.selection);
        OffsetMapping offsetMapping = this.offsetMapping;
        int iOriginalToTransformed = offsetMapping.originalToTransformed(iM751getMaximpl);
        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
        return Integer.valueOf(offsetMapping.transformedToOriginal(multiParagraph.getLineEnd(multiParagraph.getLineForOffset(iOriginalToTransformed), true)));
    }

    public final Integer getLineStartByOffset() {
        TextLayoutResult textLayoutResult = this.layoutResult;
        if (textLayoutResult == null) {
            return null;
        }
        int iM752getMinimpl = TextRange.m752getMinimpl(this.selection);
        OffsetMapping offsetMapping = this.offsetMapping;
        return Integer.valueOf(offsetMapping.transformedToOriginal(textLayoutResult.getLineStart(textLayoutResult.multiParagraph.getLineForOffset(offsetMapping.originalToTransformed(iM752getMinimpl)))));
    }

    public final Integer getNextWordOffset() {
        int length;
        TextLayoutResult textLayoutResult = this.layoutResult;
        if (textLayoutResult == null) {
            return null;
        }
        int iTransformedEndOffset = transformedEndOffset();
        while (true) {
            AnnotatedString annotatedString = this.originalText;
            if (iTransformedEndOffset < annotatedString.text.length()) {
                int length2 = this.annotatedString.text.length() - 1;
                if (iTransformedEndOffset <= length2) {
                    length2 = iTransformedEndOffset;
                }
                long jM746getWordBoundaryjx7JFs = textLayoutResult.m746getWordBoundaryjx7JFs(length2);
                TextRange.Companion companion = TextRange.Companion;
                int i = (int) (jM746getWordBoundaryjx7JFs & 4294967295L);
                if (i > iTransformedEndOffset) {
                    length = this.offsetMapping.transformedToOriginal(i);
                    break;
                }
                iTransformedEndOffset++;
            } else {
                length = annotatedString.text.length();
                break;
            }
        }
        return Integer.valueOf(length);
    }

    public final Integer getPreviousWordOffset() {
        int iTransformedToOriginal;
        TextLayoutResult textLayoutResult = this.layoutResult;
        if (textLayoutResult == null) {
            return null;
        }
        int iTransformedEndOffset = transformedEndOffset();
        while (true) {
            if (iTransformedEndOffset <= 0) {
                iTransformedToOriginal = 0;
                break;
            }
            int length = this.annotatedString.text.length() - 1;
            if (iTransformedEndOffset <= length) {
                length = iTransformedEndOffset;
            }
            long jM746getWordBoundaryjx7JFs = textLayoutResult.m746getWordBoundaryjx7JFs(length);
            TextRange.Companion companion = TextRange.Companion;
            int i = (int) (jM746getWordBoundaryjx7JFs >> 32);
            if (i < iTransformedEndOffset) {
                iTransformedToOriginal = this.offsetMapping.transformedToOriginal(i);
                break;
            }
            iTransformedEndOffset--;
        }
        return Integer.valueOf(iTransformedToOriginal);
    }

    public final boolean isLtr() {
        TextLayoutResult textLayoutResult = this.layoutResult;
        return (textLayoutResult != null ? textLayoutResult.getParagraphDirection(transformedEndOffset()) : null) != ResolvedTextDirection.Rtl;
    }

    public final int jumpByLinesOffset(TextLayoutResult textLayoutResult, int i) {
        int iTransformedEndOffset = transformedEndOffset();
        TextPreparedSelectionState textPreparedSelectionState = this.state;
        if (textPreparedSelectionState.cachedX == null) {
            textPreparedSelectionState.cachedX = Float.valueOf(textLayoutResult.getCursorRect(iTransformedEndOffset).left);
        }
        int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(iTransformedEndOffset) + i;
        if (lineForOffset < 0) {
            return 0;
        }
        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
        if (lineForOffset >= multiParagraph.lineCount) {
            return this.annotatedString.text.length();
        }
        float lineBottom = multiParagraph.getLineBottom(lineForOffset) - 1;
        Float f = textPreparedSelectionState.cachedX;
        f.getClass();
        float fFloatValue = f.floatValue();
        if ((isLtr() && fFloatValue >= textLayoutResult.getLineRight(lineForOffset)) || (!isLtr() && fFloatValue <= textLayoutResult.getLineLeft(lineForOffset))) {
            return multiParagraph.getLineEnd(lineForOffset, true);
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f.floatValue()) << 32) | (Float.floatToRawIntBits(lineBottom) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return this.offsetMapping.transformedToOriginal(multiParagraph.m736getOffsetForPositionk4lQ0M(jFloatToRawIntBits));
    }

    public final void moveCursorNextByParagraph() {
        this.state.cachedX = null;
        AnnotatedString annotatedString = this.annotatedString;
        if (annotatedString.text.length() > 0) {
            int iFindParagraphEnd = StringHelpersKt.findParagraphEnd(TextRange.m751getMaximpl(this.selection), annotatedString.text);
            if (iFindParagraphEnd == TextRange.m751getMaximpl(this.selection) && iFindParagraphEnd != annotatedString.text.length()) {
                iFindParagraphEnd = StringHelpersKt.findParagraphEnd(iFindParagraphEnd + 1, annotatedString.text);
            }
            setSelection(iFindParagraphEnd, iFindParagraphEnd);
        }
    }

    public final void moveCursorPrevByParagraph() {
        this.state.cachedX = null;
        AnnotatedString annotatedString = this.annotatedString;
        if (annotatedString.text.length() > 0) {
            int iFindParagraphStart = StringHelpersKt.findParagraphStart(TextRange.m752getMinimpl(this.selection), annotatedString.text);
            if (iFindParagraphStart == TextRange.m752getMinimpl(this.selection) && iFindParagraphStart != 0) {
                iFindParagraphStart = StringHelpersKt.findParagraphStart(iFindParagraphStart - 1, annotatedString.text);
            }
            setSelection(iFindParagraphStart, iFindParagraphStart);
        }
    }

    public final void moveCursorToLineEnd() {
        Integer lineEndByOffset;
        this.state.cachedX = null;
        if (this.annotatedString.text.length() <= 0 || (lineEndByOffset = getLineEndByOffset()) == null) {
            return;
        }
        int iIntValue = lineEndByOffset.intValue();
        setSelection(iIntValue, iIntValue);
    }

    public final void moveCursorToLineStart() {
        Integer lineStartByOffset;
        this.state.cachedX = null;
        if (this.annotatedString.text.length() <= 0 || (lineStartByOffset = getLineStartByOffset()) == null) {
            return;
        }
        int iIntValue = lineStartByOffset.intValue();
        setSelection(iIntValue, iIntValue);
    }

    public final void selectMovement() {
        if (this.annotatedString.text.length() > 0) {
            TextRange.Companion companion = TextRange.Companion;
            this.selection = TextRangeKt.TextRange((int) (this.originalSelection >> 32), (int) (this.selection & 4294967295L));
        }
    }

    public final void setSelection(int i, int i2) {
        this.selection = TextRangeKt.TextRange(i, i2);
    }

    public final int transformedEndOffset() {
        long j = this.selection;
        TextRange.Companion companion = TextRange.Companion;
        return this.offsetMapping.originalToTransformed((int) (j & 4294967295L));
    }

    private BaseTextPreparedSelection(AnnotatedString annotatedString, long j, TextLayoutResult textLayoutResult, OffsetMapping offsetMapping, TextPreparedSelectionState textPreparedSelectionState) {
        this.originalText = annotatedString;
        this.originalSelection = j;
        this.layoutResult = textLayoutResult;
        this.offsetMapping = offsetMapping;
        this.state = textPreparedSelectionState;
        this.selection = j;
        this.annotatedString = annotatedString;
    }
}
