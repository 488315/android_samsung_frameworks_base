package androidx.compose.ui.platform;

import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AccessibilityIterators$LineTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    public static AccessibilityIterators$LineTextSegmentIterator lineInstance;
    public TextLayoutResult layoutResult;
    public static final Companion Companion = new Companion(null);
    public static final ResolvedTextDirection DirectionStart = ResolvedTextDirection.Rtl;
    public static final ResolvedTextDirection DirectionEnd = ResolvedTextDirection.Ltr;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ AccessibilityIterators$LineTextSegmentIterator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] following(int i) {
        int i2;
        String str = this.text;
        if (str == null) {
            str = null;
        }
        if (str.length() <= 0) {
            return null;
        }
        String str2 = this.text;
        if (str2 == null) {
            str2 = null;
        }
        if (i >= str2.length()) {
            return null;
        }
        ResolvedTextDirection resolvedTextDirection = DirectionStart;
        if (i < 0) {
            TextLayoutResult textLayoutResult = this.layoutResult;
            if (textLayoutResult == null) {
                textLayoutResult = null;
            }
            i2 = textLayoutResult.multiParagraph.getLineForOffset(0);
        } else {
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            int lineForOffset = textLayoutResult2.multiParagraph.getLineForOffset(i);
            i2 = getLineEdgeIndex(lineForOffset, resolvedTextDirection) == i ? lineForOffset : lineForOffset + 1;
        }
        TextLayoutResult textLayoutResult3 = this.layoutResult;
        if (textLayoutResult3 == null) {
            textLayoutResult3 = null;
        }
        if (i2 >= textLayoutResult3.multiParagraph.lineCount) {
            return null;
        }
        return getRange(getLineEdgeIndex(i2, resolvedTextDirection), getLineEdgeIndex(i2, DirectionEnd) + 1);
    }

    public final int getLineEdgeIndex(int i, ResolvedTextDirection resolvedTextDirection) {
        int lineEnd;
        TextLayoutResult textLayoutResult = this.layoutResult;
        if (textLayoutResult == null) {
            textLayoutResult = null;
        }
        int lineStart = textLayoutResult.getLineStart(i);
        TextLayoutResult textLayoutResult2 = this.layoutResult;
        if (textLayoutResult2 == null) {
            textLayoutResult2 = null;
        }
        if (resolvedTextDirection != textLayoutResult2.getParagraphDirection(lineStart)) {
            TextLayoutResult textLayoutResult3 = this.layoutResult;
            return (textLayoutResult3 != null ? textLayoutResult3 : null).getLineStart(i);
        }
        TextLayoutResult textLayoutResult4 = this.layoutResult;
        lineEnd = (textLayoutResult4 != null ? textLayoutResult4 : null).multiParagraph.getLineEnd(i, false);
        return lineEnd - 1;
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] preceding(int i) {
        int i2;
        String str = this.text;
        if (str == null) {
            str = null;
        }
        if (str.length() <= 0 || i <= 0) {
            return null;
        }
        String str2 = this.text;
        if (str2 == null) {
            str2 = null;
        }
        int length = str2.length();
        ResolvedTextDirection resolvedTextDirection = DirectionEnd;
        if (i > length) {
            TextLayoutResult textLayoutResult = this.layoutResult;
            if (textLayoutResult == null) {
                textLayoutResult = null;
            }
            String str3 = this.text;
            if (str3 == null) {
                str3 = null;
            }
            i2 = textLayoutResult.multiParagraph.getLineForOffset(str3.length());
        } else {
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            int lineForOffset = textLayoutResult2.multiParagraph.getLineForOffset(i);
            i2 = getLineEdgeIndex(lineForOffset, resolvedTextDirection) + 1 == i ? lineForOffset : lineForOffset - 1;
        }
        if (i2 < 0) {
            return null;
        }
        return getRange(getLineEdgeIndex(i2, DirectionStart), getLineEdgeIndex(i2, resolvedTextDirection) + 1);
    }

    private AccessibilityIterators$LineTextSegmentIterator() {
    }
}
