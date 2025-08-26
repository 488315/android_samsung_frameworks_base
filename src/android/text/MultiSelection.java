package android.text;

import android.graphics.Paint;

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

    /* JADX WARN: Removed duplicated region for block: B:30:0x0052 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setSelection(Spannable spannable, int i, int i2) {
        boolean z;
        char cCharAt;
        if (i == i2 || i < 0 || i2 < 0) {
            return;
        }
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        int length = spannable.length();
        if ((i > 0 && i < length) || (i2 > 0 && i2 < length)) {
            if (i <= 0 || i >= length) {
                if (i2 > 0 && i2 < length) {
                    cCharAt = spannable.charAt(i2);
                    if (!Character.isLowSurrogate(cCharAt)) {
                        i2++;
                    } else if (!z && (TextUtils.isIndianChar(cCharAt) || TextUtils.isThaiChar(cCharAt) || TextUtils.isKhmerChar(cCharAt) || TextUtils.isMyanmarChar(cCharAt) || TextUtils.isLaoChar(cCharAt))) {
                        z = true;
                    }
                }
                if (z) {
                    float[] fArr = new float[length];
                    char[] cArr = new char[length];
                    Paint paint = new Paint(1);
                    TextUtils.getChars(spannable, 0, length, cArr, 0);
                    paint.getTextRunAdvances(cArr, 0, length, 0, length, false, fArr, 0);
                    while (i < length && fArr[i] == 0.0f && cArr[i] != '\n') {
                        i++;
                    }
                    while (i2 < length && fArr[i2] == 0.0f && cArr[i2] != '\n') {
                        i2++;
                    }
                }
            } else {
                char cCharAt2 = spannable.charAt(i);
                if (Character.isLowSurrogate(cCharAt2)) {
                    i++;
                } else {
                    z = TextUtils.isIndianChar(cCharAt2) || TextUtils.isThaiChar(cCharAt2) || TextUtils.isKhmerChar(cCharAt2) || TextUtils.isMyanmarChar(cCharAt2) || TextUtils.isLaoChar(cCharAt2);
                    if (i2 > 0) {
                        cCharAt = spannable.charAt(i2);
                        if (!Character.isLowSurrogate(cCharAt)) {
                        }
                    }
                    if (z) {
                    }
                }
                if (i2 > 0) {
                }
                if (z) {
                }
            }
        }
        if (selectionStart == i && selectionEnd == i2) {
            return;
        }
        START[] startArr = (START[]) spannable.getSpans(0, spannable.length(), START.class);
        END[] endArr = (END[]) spannable.getSpans(0, spannable.length(), END.class);
        for (int i3 = 0; i3 < startArr.length; i3++) {
            int spanStart = spannable.getSpanStart(startArr[i3]);
            int spanStart2 = spannable.getSpanStart(endArr[i3]);
            if ((spanStart <= i && i < spanStart2) || (spanStart < i2 && i2 <= spanStart2)) {
                spannable.removeSpan(startArr[i3]);
                spannable.removeSpan(endArr[i3]);
            }
        }
        spannable.setSpan(CURRENT_SELECTION_START, i, i, 546);
        spannable.setSpan(CURRENT_SELECTION_END, i2, i2, 34);
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
