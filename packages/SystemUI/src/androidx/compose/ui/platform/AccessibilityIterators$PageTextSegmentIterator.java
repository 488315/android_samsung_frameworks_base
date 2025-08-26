package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AccessibilityIterators$PageTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    public static AccessibilityIterators$PageTextSegmentIterator pageInstance;
    public TextLayoutResult layoutResult;
    public SemanticsNode node;
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

    public /* synthetic */ AccessibilityIterators$PageTextSegmentIterator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] following(int i) {
        int lineForVerticalPosition;
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
        try {
            SemanticsNode semanticsNode = this.node;
            if (semanticsNode == null) {
                semanticsNode = null;
            }
            Rect boundsInRoot = semanticsNode.getBoundsInRoot();
            int iRound = Math.round(boundsInRoot.bottom - boundsInRoot.top);
            if (i <= 0) {
                i = 0;
            }
            TextLayoutResult textLayoutResult = this.layoutResult;
            if (textLayoutResult == null) {
                textLayoutResult = null;
            }
            int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(i);
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            float lineTop = textLayoutResult2.multiParagraph.getLineTop(lineForOffset) + iRound;
            TextLayoutResult textLayoutResult3 = this.layoutResult;
            TextLayoutResult textLayoutResult4 = textLayoutResult3 == null ? null : textLayoutResult3;
            if (textLayoutResult3 == null) {
                textLayoutResult3 = null;
            }
            if (lineTop < textLayoutResult4.multiParagraph.getLineTop(textLayoutResult3.multiParagraph.lineCount - 1)) {
                TextLayoutResult textLayoutResult5 = this.layoutResult;
                lineForVerticalPosition = (textLayoutResult5 != null ? textLayoutResult5 : null).multiParagraph.getLineForVerticalPosition(lineTop);
            } else {
                TextLayoutResult textLayoutResult6 = this.layoutResult;
                lineForVerticalPosition = (textLayoutResult6 != null ? textLayoutResult6 : null).multiParagraph.lineCount;
            }
            return getRange(i, getLineEdgeIndex$1(lineForVerticalPosition - 1, DirectionEnd) + 1);
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    public final int getLineEdgeIndex$1(int i, ResolvedTextDirection resolvedTextDirection) {
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
        int lineForVerticalPosition;
        String str = this.text;
        if (str == null) {
            str = null;
        }
        if (str.length() <= 0 || i <= 0) {
            return null;
        }
        try {
            SemanticsNode semanticsNode = this.node;
            if (semanticsNode == null) {
                semanticsNode = null;
            }
            Rect boundsInRoot = semanticsNode.getBoundsInRoot();
            int iRound = Math.round(boundsInRoot.bottom - boundsInRoot.top);
            String str2 = this.text;
            if (str2 == null) {
                str2 = null;
            }
            int length = str2.length();
            if (length <= i) {
                i = length;
            }
            TextLayoutResult textLayoutResult = this.layoutResult;
            if (textLayoutResult == null) {
                textLayoutResult = null;
            }
            int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(i);
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                textLayoutResult2 = null;
            }
            float lineTop = textLayoutResult2.multiParagraph.getLineTop(lineForOffset) - iRound;
            if (lineTop > 0.0f) {
                TextLayoutResult textLayoutResult3 = this.layoutResult;
                if (textLayoutResult3 == null) {
                    textLayoutResult3 = null;
                }
                lineForVerticalPosition = textLayoutResult3.multiParagraph.getLineForVerticalPosition(lineTop);
            } else {
                lineForVerticalPosition = 0;
            }
            String str3 = this.text;
            if (i == (str3 != null ? str3 : null).length() && lineForVerticalPosition < lineForOffset) {
                lineForVerticalPosition++;
            }
            return getRange(getLineEdgeIndex$1(lineForVerticalPosition, DirectionStart), i);
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    private AccessibilityIterators$PageTextSegmentIterator() {
        new android.graphics.Rect();
    }
}
