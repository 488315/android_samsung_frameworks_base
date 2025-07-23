package android.widget;

import android.graphics.Rect;
import android.text.Layout;
import android.text.Spannable;
import android.view.AccessibilityIterators;

/* loaded from: classes5.dex */
final class AccessibilityIterators {
    AccessibilityIterators() {
    }

    static class LineTextSegmentIterator extends AccessibilityIterators.AbstractTextSegmentIterator {
        protected static final int DIRECTION_END = 1;
        protected static final int DIRECTION_START = -1;
        private static LineTextSegmentIterator sLineInstance;
        protected Layout mLayout;

        LineTextSegmentIterator() {
        }

        public static LineTextSegmentIterator getInstance() {
            if (sLineInstance == null) {
                sLineInstance = new LineTextSegmentIterator();
            }
            return sLineInstance;
        }

        public void initialize(Spannable spannable, Layout layout) {
            this.mText = spannable.toString();
            this.mLayout = layout;
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            int i2;
            if (this.mText.length() <= 0 || i >= this.mText.length()) {
                return null;
            }
            if (i < 0) {
                i2 = this.mLayout.getLineForOffset(0);
            } else {
                int lineForOffset = this.mLayout.getLineForOffset(i);
                i2 = getLineEdgeIndex(lineForOffset, -1) == i ? lineForOffset : lineForOffset + 1;
            }
            if (i2 >= this.mLayout.getLineCount()) {
                return null;
            }
            return getRange(getLineEdgeIndex(i2, -1), getLineEdgeIndex(i2, 1) + 1);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int i2;
            if (this.mText.length() <= 0 || i <= 0) {
                return null;
            }
            if (i > this.mText.length()) {
                i2 = this.mLayout.getLineForOffset(this.mText.length());
            } else {
                int lineForOffset = this.mLayout.getLineForOffset(i);
                i2 = getLineEdgeIndex(lineForOffset, 1) + 1 == i ? lineForOffset : lineForOffset - 1;
            }
            if (i2 < 0) {
                return null;
            }
            return getRange(getLineEdgeIndex(i2, -1), getLineEdgeIndex(i2, 1) + 1);
        }

        protected int getLineEdgeIndex(int i, int i2) {
            if (i2 * this.mLayout.getParagraphDirection(i) < 0) {
                return this.mLayout.getLineStart(i);
            }
            return this.mLayout.getLineEnd(i) - 1;
        }
    }

    static class PageTextSegmentIterator extends LineTextSegmentIterator {
        private static PageTextSegmentIterator sPageInstance;
        private final Rect mTempRect = new Rect();
        private TextView mView;

        PageTextSegmentIterator() {
        }

        public static PageTextSegmentIterator getInstance() {
            if (sPageInstance == null) {
                sPageInstance = new PageTextSegmentIterator();
            }
            return sPageInstance;
        }

        public void initialize(TextView textView) {
            super.initialize((Spannable) textView.getIterableTextForAccessibility(), textView.getLayout());
            this.mView = textView;
        }

        @Override // android.widget.AccessibilityIterators.LineTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            if (this.mText.length() <= 0 || i >= this.mText.length() || !this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int max = Math.max(0, i);
            int lineTop = this.mLayout.getLineTop(this.mLayout.getLineForOffset(max)) + ((this.mTempRect.height() - this.mView.getTotalPaddingTop()) - this.mView.getTotalPaddingBottom());
            return getRange(max, getLineEdgeIndex((lineTop < this.mLayout.getLineTop(this.mLayout.getLineCount() - 1) ? this.mLayout.getLineForVertical(lineTop) : this.mLayout.getLineCount()) - 1, 1) + 1);
        }

        @Override // android.widget.AccessibilityIterators.LineTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            if (this.mText.length() <= 0 || i <= 0 || !this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int min = Math.min(this.mText.length(), i);
            int lineForOffset = this.mLayout.getLineForOffset(min);
            int lineTop = this.mLayout.getLineTop(lineForOffset) - ((this.mTempRect.height() - this.mView.getTotalPaddingTop()) - this.mView.getTotalPaddingBottom());
            int lineForVertical = lineTop > 0 ? this.mLayout.getLineForVertical(lineTop) : 0;
            if (min == this.mText.length() && lineForVertical < lineForOffset) {
                lineForVertical++;
            }
            return getRange(getLineEdgeIndex(lineForVertical, -1), min);
        }
    }
}
