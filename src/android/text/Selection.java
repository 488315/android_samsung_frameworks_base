package android.text;

/* loaded from: classes4.dex */
public class Selection {
    private static final char PARAGRAPH_SEPARATOR = '\n';
    public static final Object SELECTION_END;
    private static final Object SELECTION_MEMORY;
    public static final Object SELECTION_START;

    public interface PositionIterator {
        public static final int DONE = -1;

        int following(int i);

        int preceding(int i);
    }

    private Selection() {
    }

    public static final int getSelectionStart(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned) charSequence).getSpanStart(SELECTION_START);
        }
        return -1;
    }

    public static final int getSelectionEnd(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned) charSequence).getSpanStart(SELECTION_END);
        }
        return -1;
    }

    private static int getSelectionMemory(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned) charSequence).getSpanStart(SELECTION_MEMORY);
        }
        return -1;
    }

    public static void setSelection(Spannable spannable, int i, int i2) {
        setSelection(spannable, i, i2, -1);
    }

    private static void setSelection(Spannable spannable, int i, int i2, int i3) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart == i && selectionEnd == i2) {
            return;
        }
        spannable.setSpan(SELECTION_START, i, i, 546);
        spannable.setSpan(SELECTION_END, i2, i2, 34);
        updateMemory(spannable, i3);
    }

    private static void updateMemory(Spannable spannable, int i) {
        if (i > -1) {
            int selectionMemory = getSelectionMemory(spannable);
            if (i != selectionMemory) {
                spannable.setSpan(SELECTION_MEMORY, i, i, 34);
                if (selectionMemory == -1) {
                    spannable.setSpan(new MemoryTextWatcher(), 0, spannable.length(), 18);
                    return;
                }
                return;
            }
            return;
        }
        removeMemory(spannable);
    }

    private static void removeMemory(Spannable spannable) {
        spannable.removeSpan(SELECTION_MEMORY);
        for (MemoryTextWatcher memoryTextWatcher : (MemoryTextWatcher[]) spannable.getSpans(0, spannable.length(), MemoryTextWatcher.class)) {
            spannable.removeSpan(memoryTextWatcher);
        }
    }

    public static final class MemoryTextWatcher implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            editable.removeSpan(Selection.SELECTION_MEMORY);
            editable.removeSpan(this);
        }
    }

    public static final void setSelection(Spannable spannable, int i) {
        setSelection(spannable, i, i);
    }

    public static final void selectAll(Spannable spannable) {
        setSelection(spannable, 0, spannable.length());
    }

    public static final void extendSelection(Spannable spannable, int i) {
        extendSelection(spannable, i, -1);
    }

    private static void extendSelection(Spannable spannable, int i, int i2) {
        Object obj = SELECTION_END;
        if (spannable.getSpanStart(obj) != i) {
            spannable.setSpan(obj, i, i, 34);
        }
        updateMemory(spannable, i2);
    }

    public static final void removeSelection(Spannable spannable) {
        spannable.removeSpan(SELECTION_START, 512);
        spannable.removeSpan(SELECTION_END);
        removeMemory(spannable);
    }

    public static boolean moveUp(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            int iMin = Math.min(selectionStart, selectionEnd);
            int iMax = Math.max(selectionStart, selectionEnd);
            setSelection(spannable, iMin);
            return (iMin == 0 && iMax == spannable.length()) ? false : true;
        }
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset > 0) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, -1, false);
            return true;
        }
        if (selectionEnd == 0) {
            return false;
        }
        setSelection(spannable, 0);
        return true;
    }

    private static void setSelectionAndMemory(Spannable spannable, Layout layout, int i, int i2, int i3, boolean z) {
        int lineStart;
        int paragraphDirection = layout.getParagraphDirection(i);
        int i4 = i + i3;
        int i5 = -1;
        if (paragraphDirection == layout.getParagraphDirection(i4)) {
            int selectionMemory = getSelectionMemory(spannable);
            if (selectionMemory > -1) {
                lineStart = layout.getOffsetForHorizontal(i4, layout.getPrimaryHorizontal(selectionMemory));
                i2 = selectionMemory;
            } else {
                lineStart = layout.getOffsetForHorizontal(i4, layout.getPrimaryHorizontal(i2));
            }
            i5 = i2;
        } else {
            lineStart = layout.getLineStart(i4);
        }
        if (z) {
            extendSelection(spannable, lineStart, i5);
        } else {
            setSelection(spannable, lineStart, lineStart, i5);
        }
    }

    public static boolean moveDown(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            int iMin = Math.min(selectionStart, selectionEnd);
            int iMax = Math.max(selectionStart, selectionEnd);
            setSelection(spannable, iMax);
            return (iMin == 0 && iMax == spannable.length()) ? false : true;
        }
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset < layout.getLineCount() - 1) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, 1, false);
            return true;
        }
        if (selectionEnd == spannable.length()) {
            return false;
        }
        setSelection(spannable, spannable.length());
        return true;
    }

    public static boolean moveLeft(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, -1, selectionStart, selectionEnd));
            return true;
        }
        int offsetToLeftOf = layout.getOffsetToLeftOf(selectionEnd);
        if (offsetToLeftOf == selectionEnd) {
            return false;
        }
        setSelection(spannable, offsetToLeftOf);
        return true;
    }

    public static boolean moveRight(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, 1, selectionStart, selectionEnd));
            return true;
        }
        int offsetToRightOf = layout.getOffsetToRightOf(selectionEnd);
        if (offsetToRightOf == selectionEnd) {
            return false;
        }
        setSelection(spannable, offsetToRightOf);
        return true;
    }

    public static boolean moveToParagraphStart(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, -1, selectionStart, selectionEnd));
            return true;
        }
        int iLastIndexOf = TextUtils.lastIndexOf(spannable, PARAGRAPH_SEPARATOR, selectionStart - 1);
        if (iLastIndexOf == -1) {
            iLastIndexOf = 0;
        }
        if (iLastIndexOf == selectionEnd) {
            return false;
        }
        setSelection(spannable, iLastIndexOf);
        return true;
    }

    public static boolean moveToParagraphEnd(Spannable spannable, Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, 1, selectionStart, selectionEnd));
            return true;
        }
        int iIndexOf = TextUtils.indexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd + 1);
        if (iIndexOf == -1) {
            iIndexOf = spannable.length();
        }
        if (iIndexOf == selectionEnd) {
            return false;
        }
        setSelection(spannable, iIndexOf);
        return true;
    }

    public static boolean extendToParagraphStart(Spannable spannable) {
        int selectionEnd = getSelectionEnd(spannable);
        int iLastIndexOf = TextUtils.lastIndexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd - 1);
        if (iLastIndexOf == -1) {
            iLastIndexOf = 0;
        }
        if (iLastIndexOf == selectionEnd) {
            return false;
        }
        extendSelection(spannable, iLastIndexOf);
        return true;
    }

    public static boolean extendToParagraphEnd(Spannable spannable) {
        int selectionEnd = getSelectionEnd(spannable);
        int iIndexOf = TextUtils.indexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd + 1);
        if (iIndexOf == -1) {
            iIndexOf = spannable.length();
        }
        if (iIndexOf == selectionEnd) {
            return false;
        }
        extendSelection(spannable, iIndexOf);
        return true;
    }

    public static boolean extendUp(Spannable spannable, Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset > 0) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, -1, true);
            return true;
        }
        if (selectionEnd != 0) {
            extendSelection(spannable, 0);
        }
        return true;
    }

    public static boolean extendDown(Spannable spannable, Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset < layout.getLineCount() - 1) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, 1, true);
            return true;
        }
        if (selectionEnd != spannable.length()) {
            extendSelection(spannable, spannable.length(), -1);
        }
        return true;
    }

    public static boolean extendLeft(Spannable spannable, Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int offsetToLeftOf = layout.getOffsetToLeftOf(selectionEnd);
        if (offsetToLeftOf != selectionEnd) {
            extendSelection(spannable, offsetToLeftOf);
        }
        return true;
    }

    public static boolean extendRight(Spannable spannable, Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int offsetToRightOf = layout.getOffsetToRightOf(selectionEnd);
        if (offsetToRightOf != selectionEnd) {
            extendSelection(spannable, offsetToRightOf);
        }
        return true;
    }

    public static boolean extendToLeftEdge(Spannable spannable, Layout layout) {
        extendSelection(spannable, findEdge(spannable, layout, -1));
        return true;
    }

    public static boolean extendToRightEdge(Spannable spannable, Layout layout) {
        extendSelection(spannable, findEdge(spannable, layout, 1));
        return true;
    }

    public static boolean moveToLeftEdge(Spannable spannable, Layout layout) {
        setSelection(spannable, findEdge(spannable, layout, -1));
        return true;
    }

    public static boolean moveToRightEdge(Spannable spannable, Layout layout) {
        setSelection(spannable, findEdge(spannable, layout, 1));
        return true;
    }

    public static boolean moveToPreceding(Spannable spannable, PositionIterator positionIterator, boolean z) {
        int iPreceding = positionIterator.preceding(getSelectionEnd(spannable));
        if (iPreceding == -1) {
            return true;
        }
        if (z) {
            extendSelection(spannable, iPreceding);
            return true;
        }
        setSelection(spannable, iPreceding);
        return true;
    }

    public static boolean moveToFollowing(Spannable spannable, PositionIterator positionIterator, boolean z) {
        int iFollowing = positionIterator.following(getSelectionEnd(spannable));
        if (iFollowing == -1) {
            return true;
        }
        if (z) {
            extendSelection(spannable, iFollowing);
            return true;
        }
        setSelection(spannable, iFollowing);
        return true;
    }

    private static int findEdge(Spannable spannable, Layout layout, int i) {
        int lineForOffset = layout.getLineForOffset(getSelectionEnd(spannable));
        if (i * layout.getParagraphDirection(lineForOffset) < 0) {
            return layout.getLineStart(lineForOffset);
        }
        int lineEnd = layout.getLineEnd(lineForOffset);
        return lineForOffset == layout.getLineCount() + (-1) ? lineEnd : lineEnd - 1;
    }

    private static int chooseHorizontal(Layout layout, int i, int i2, int i3) {
        if (layout.getLineForOffset(i2) == layout.getLineForOffset(i3)) {
            float primaryHorizontal = layout.getPrimaryHorizontal(i2);
            float primaryHorizontal2 = layout.getPrimaryHorizontal(i3);
            return (i >= 0 ? primaryHorizontal <= primaryHorizontal2 : primaryHorizontal >= primaryHorizontal2) ? i3 : i2;
        }
        if (layout.getParagraphDirection(layout.getLineForOffset(i2)) == i) {
            return Math.max(i2, i3);
        }
        return Math.min(i2, i3);
    }

    private static final class START implements NoCopySpan {
        private START() {
        }
    }

    private static final class END implements NoCopySpan {
        private END() {
        }
    }

    private static final class MEMORY implements NoCopySpan {
        private MEMORY() {
        }
    }

    static {
        SELECTION_MEMORY = new MEMORY();
        SELECTION_START = new START();
        SELECTION_END = new END();
    }
}
