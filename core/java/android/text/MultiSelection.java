package android.text;

public class MultiSelection {
    public static final Object CURRENT_SELECTION_START = new START();
    public static final Object CURRENT_SELECTION_END = new END();
    private static boolean mIsSelecting = false;
    private static boolean mIsTextViewHovered = false;
    private static boolean mNeedToScroll = false;
    private static int mHoveredIcon = -1;

    private MultiSelection() {}

    public static final int getSelectionStart(CharSequence text) {
        if (text instanceof Spanned) {
            return ((Spanned) text).getSpanStart(CURRENT_SELECTION_START);
        }
        return -1;
    }

    public static final int getSelectionEnd(CharSequence text) {
        if (text instanceof Spanned) {
            return ((Spanned) text).getSpanStart(CURRENT_SELECTION_END);
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d1, code lost:

       if (r4 == r2) goto L88;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:?, code lost:

       return;
    */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setSelection(android.text.Spannable r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.text.MultiSelection.setSelection(android.text.Spannable, int,"
                    + " int):void");
    }

    public static final void removeCurSelection(Spannable text) {
        text.removeSpan(CURRENT_SELECTION_START);
        text.removeSpan(CURRENT_SELECTION_END);
    }

    public static final void selectAll(Spannable text) {
        setSelection(text, 0, text.length());
    }

    public static final void addMultiSelection(Spannable spannable, int i, int i2) {
        if (i < 0 || i2 < 0) {
            return;
        }
        START start = new START();
        END end = new END();
        spannable.setSpan(start, i, i, 546);
        spannable.setSpan(end, i2, i2, 34);
    }

    public static final boolean removeMultiSelection(Spannable text, int start, int stop) {
        START[] spansStarts = (START[]) text.getSpans(start, start, START.class);
        END[] spansEnds = (END[]) text.getSpans(stop, stop, END.class);
        boolean ret = true;
        if (spansStarts.length == 1) {
            text.removeSpan(spansStarts[0]);
        } else {
            ret = false;
        }
        if (spansEnds.length == 1) {
            text.removeSpan(spansEnds[0]);
            return ret;
        }
        return false;
    }

    public static final void clearMultiSelection(Spannable text) {
        START[] spansStarts = (START[]) text.getSpans(0, text.length(), START.class);
        END[] spansEnds = (END[]) text.getSpans(0, text.length(), END.class);
        for (int i = 0; i < spansStarts.length; i++) {
            text.removeSpan(spansStarts[i]);
            text.removeSpan(spansEnds[i]);
        }
    }

    public static final int[] getMultiSelectionStart(Spannable text) {
        START[] spans = (START[]) text.getSpans(0, text.length(), START.class);
        int[] starts = new int[spans.length];
        for (int i = 0; i < spans.length; i++) {
            starts[i] = text.getSpanStart(spans[i]);
        }
        return starts;
    }

    public static final int[] getMultiSelectionEnd(Spannable text) {
        END[] spans = (END[]) text.getSpans(0, text.length(), END.class);
        int[] ends = new int[spans.length];
        for (int i = 0; i < spans.length; i++) {
            ends[i] = text.getSpanStart(spans[i]);
        }
        return ends;
    }

    public static final int getMultiSelectionCount(Spannable text) {
        START[] spans = (START[]) text.getSpans(0, text.length(), START.class);
        return spans.length;
    }

    public static final void setIsMultiSelectingText(boolean bSelecting) {
        mIsSelecting = bSelecting;
    }

    public static final boolean getIsMultiSelectingText() {
        return mIsSelecting;
    }

    public static final void setTextViewHovered(boolean bSelecting) {
        setTextViewHovered(bSelecting, -1);
    }

    public static final void setTextViewHovered(boolean bSelecting, int type) {
        mIsTextViewHovered = bSelecting;
        mHoveredIcon = type;
    }

    public static final boolean isTextViewHovered() {
        return mIsTextViewHovered;
    }

    public static final int getHoveredIcon() {
        return mHoveredIcon;
    }

    public static final void setNeedToScroll(boolean bflag) {
        mNeedToScroll = bflag;
    }

    public static final boolean isNeedToScroll() {
        return mNeedToScroll;
    }

    private static final class START implements NoCopySpan {
        private START() {}
    }

    private static final class END implements NoCopySpan {
        private END() {}
    }
}
