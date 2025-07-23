package android.text;

/* loaded from: classes4.dex */
public class MultiSelection {
    public static final Object CURRENT_SELECTION_END;
    public static final Object CURRENT_SELECTION_START;
    private static boolean mIsSelecting = false;
    private static boolean mIsTextViewHovered = false;
    private static boolean mNeedToScroll = false;
    private static int mHoveredIcon = -1;

    private MultiSelection() {
    }

    public static final int getSelectionStart(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned) charSequence).getSpanStart(CURRENT_SELECTION_START);
        }
        return -1;
    }

    public static final int getSelectionEnd(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned) charSequence).getSpanStart(CURRENT_SELECTION_END);
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x00bc, code lost:
    
        if (r1 == r14) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:?, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setSelection(android.text.Spannable r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.MultiSelection.setSelection(android.text.Spannable, int, int):void");
    }

    public static final void removeCurSelection(Spannable spannable) {
        spannable.removeSpan(CURRENT_SELECTION_START);
        spannable.removeSpan(CURRENT_SELECTION_END);
    }

    public static final void selectAll(Spannable spannable) {
        setSelection(spannable, 0, spannable.length());
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

    public static final boolean removeMultiSelection(Spannable spannable, int i, int i2) {
        boolean z;
        START[] startArr = (START[]) spannable.getSpans(i, i, START.class);
        END[] endArr = (END[]) spannable.getSpans(i2, i2, END.class);
        if (startArr.length == 1) {
            spannable.removeSpan(startArr[0]);
            z = true;
        } else {
            z = false;
        }
        if (endArr.length != 1) {
            return false;
        }
        spannable.removeSpan(endArr[0]);
        return z;
    }

    public static final void clearMultiSelection(Spannable spannable) {
        START[] startArr = (START[]) spannable.getSpans(0, spannable.length(), START.class);
        END[] endArr = (END[]) spannable.getSpans(0, spannable.length(), END.class);
        for (int i = 0; i < startArr.length; i++) {
            spannable.removeSpan(startArr[i]);
            spannable.removeSpan(endArr[i]);
        }
    }

    public static final int[] getMultiSelectionStart(Spannable spannable) {
        START[] startArr = (START[]) spannable.getSpans(0, spannable.length(), START.class);
        int[] iArr = new int[startArr.length];
        for (int i = 0; i < startArr.length; i++) {
            iArr[i] = spannable.getSpanStart(startArr[i]);
        }
        return iArr;
    }

    public static final int[] getMultiSelectionEnd(Spannable spannable) {
        END[] endArr = (END[]) spannable.getSpans(0, spannable.length(), END.class);
        int[] iArr = new int[endArr.length];
        for (int i = 0; i < endArr.length; i++) {
            iArr[i] = spannable.getSpanStart(endArr[i]);
        }
        return iArr;
    }

    public static final int getMultiSelectionCount(Spannable spannable) {
        return ((START[]) spannable.getSpans(0, spannable.length(), START.class)).length;
    }

    public static final void setIsMultiSelectingText(boolean z) {
        mIsSelecting = z;
    }

    public static final boolean getIsMultiSelectingText() {
        return mIsSelecting;
    }

    public static final void setTextViewHovered(boolean z) {
        setTextViewHovered(z, -1);
    }

    public static final void setTextViewHovered(boolean z, int i) {
        mIsTextViewHovered = z;
        mHoveredIcon = i;
    }

    public static final boolean isTextViewHovered() {
        return mIsTextViewHovered;
    }

    public static final int getHoveredIcon() {
        return mHoveredIcon;
    }

    public static final void setNeedToScroll(boolean z) {
        mNeedToScroll = z;
    }

    public static final boolean isNeedToScroll() {
        return mNeedToScroll;
    }

    private static final class START implements NoCopySpan {
        private START() {
        }
    }

    private static final class END implements NoCopySpan {
        private END() {
        }
    }

    static {
        CURRENT_SELECTION_START = new START();
        CURRENT_SELECTION_END = new END();
    }
}
