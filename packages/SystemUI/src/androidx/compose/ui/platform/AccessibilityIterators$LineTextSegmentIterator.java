package androidx.compose.ui.platform;

import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AccessibilityIterators$LineTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    public static AccessibilityIterators$LineTextSegmentIterator lineInstance;
    public TextLayoutResult layoutResult;
    public static final Companion Companion = new Companion(null);
    public static final ResolvedTextDirection DirectionStart = ResolvedTextDirection.Rtl;
    public static final ResolvedTextDirection DirectionEnd = ResolvedTextDirection.Ltr;

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
        int lineForOffset;
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
            lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(0);
        } else {
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            int lineForOffset2 = textLayoutResult2.multiParagraph.getLineForOffset(i);
            lineForOffset = getLineEdgeIndex(lineForOffset2, resolvedTextDirection) == i ? lineForOffset2 : lineForOffset2 + 1;
        }
        TextLayoutResult textLayoutResult3 = this.layoutResult;
        if (textLayoutResult3 == null) {
            textLayoutResult3 = null;
        }
        if (lineForOffset >= textLayoutResult3.multiParagraph.lineCount) {
            return null;
        }
        return getRange(getLineEdgeIndex(lineForOffset, resolvedTextDirection), getLineEdgeIndex(lineForOffset, DirectionEnd) + 1);
    }

    public final int getLineEdgeIndex(int i, ResolvedTextDirection resolvedTextDirection) {
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
        return (this.layoutResult != null ? r3 : null).multiParagraph.getLineEnd(i, false) - 1;
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] preceding(int i) {
        int lineForOffset;
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
            lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(str3.length());
        } else {
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            int lineForOffset2 = textLayoutResult2.multiParagraph.getLineForOffset(i);
            lineForOffset = getLineEdgeIndex(lineForOffset2, resolvedTextDirection) + 1 == i ? lineForOffset2 : lineForOffset2 - 1;
        }
        if (lineForOffset < 0) {
            return null;
        }
        return getRange(getLineEdgeIndex(lineForOffset, DirectionStart), getLineEdgeIndex(lineForOffset, resolvedTextDirection) + 1);
    }

    private AccessibilityIterators$LineTextSegmentIterator() {
    }
}
