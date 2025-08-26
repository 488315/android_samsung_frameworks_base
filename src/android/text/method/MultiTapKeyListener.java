package android.text.method;

import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.method.TextKeyListener;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;

/* loaded from: classes4.dex */
public class MultiTapKeyListener extends BaseKeyListener implements SpanWatcher {
    private static MultiTapKeyListener[] sInstance = new MultiTapKeyListener[TextKeyListener.Capitalize.values().length * 2];
    private static final SparseArray<String> sRecs;
    private boolean mAutoText;
    private TextKeyListener.Capitalize mCapitalize;

    @Override // android.text.SpanWatcher
    public void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        sRecs = sparseArray;
        sparseArray.put(8, ".,1!@#$%^&*:/?'=()");
        sparseArray.put(9, "abc2ABC");
        sparseArray.put(10, "def3DEF");
        sparseArray.put(11, "ghi4GHI");
        sparseArray.put(12, "jkl5JKL");
        sparseArray.put(13, "mno6MNO");
        sparseArray.put(14, "pqrs7PQRS");
        sparseArray.put(15, "tuv8TUV");
        sparseArray.put(16, "wxyz9WXYZ");
        sparseArray.put(7, "0+");
        sparseArray.put(18, " ");
    }

    public MultiTapKeyListener(TextKeyListener.Capitalize capitalize, boolean z) {
        this.mCapitalize = capitalize;
        this.mAutoText = z;
    }

    public static MultiTapKeyListener getInstance(boolean z, TextKeyListener.Capitalize capitalize) {
        int iOrdinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        MultiTapKeyListener[] multiTapKeyListenerArr = sInstance;
        if (multiTapKeyListenerArr[iOrdinal] == null) {
            multiTapKeyListenerArr[iOrdinal] = new MultiTapKeyListener(capitalize, z);
        }
        return sInstance[iOrdinal];
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c6  */
    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        int i2;
        int i3;
        int iIndexOfKey;
        int i4;
        String strValueAt;
        int iIndexOf;
        int prefs = view != null ? TextKeyListener.getInstance().getPrefs(view.getContext()) : 0;
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        int iMin = Math.min(selectionStart, selectionEnd);
        int iMax = Math.max(selectionStart, selectionEnd);
        int spanStart = editable.getSpanStart(TextKeyListener.ACTIVE);
        int spanEnd = editable.getSpanEnd(TextKeyListener.ACTIVE);
        int spanFlags = (editable.getSpanFlags(TextKeyListener.ACTIVE) & (-16777216)) >>> 24;
        if (spanStart == iMin && spanEnd == iMax && iMax - iMin == 1 && spanFlags >= 0) {
            SparseArray<String> sparseArray = sRecs;
            if (spanFlags < sparseArray.size()) {
                if (i == 17) {
                    char cCharAt = editable.charAt(iMin);
                    if (Character.isLowerCase(cCharAt)) {
                        editable.replace(iMin, iMax, String.valueOf(cCharAt).toUpperCase());
                        removeTimeouts(editable);
                        new Timeout(this, editable);
                        return true;
                    }
                    if (Character.isUpperCase(cCharAt)) {
                        editable.replace(iMin, iMax, String.valueOf(cCharAt).toLowerCase());
                        removeTimeouts(editable);
                        new Timeout(this, editable);
                        return true;
                    }
                }
                if (sparseArray.indexOfKey(i) == spanFlags && (iIndexOf = (strValueAt = sparseArray.valueAt(spanFlags)).indexOf(editable.charAt(iMin))) >= 0) {
                    int length = (iIndexOf + 1) % strValueAt.length();
                    editable.replace(iMin, iMax, strValueAt, length, length + 1);
                    removeTimeouts(editable);
                    new Timeout(this, editable);
                    return true;
                }
                i2 = prefs;
                i3 = iMax;
                iIndexOfKey = sparseArray.indexOfKey(i);
                if (iIndexOfKey >= 0) {
                    Selection.setSelection(editable, i3, i3);
                    iMin = i3;
                }
            }
        } else {
            i2 = prefs;
            i3 = iMax;
            iIndexOfKey = sRecs.indexOfKey(i);
        }
        int i5 = iIndexOfKey;
        if (i5 >= 0) {
            String strValueAt2 = sRecs.valueAt(i5);
            if ((i2 & 1) == 0 || !TextKeyListener.shouldCap(this.mCapitalize, editable, iMin)) {
                i4 = 0;
            } else {
                for (int i6 = 0; i6 < strValueAt2.length(); i6++) {
                    if (Character.isUpperCase(strValueAt2.charAt(i6))) {
                        i4 = i6;
                        break;
                    }
                }
                i4 = 0;
            }
            if (iMin != i3) {
                Selection.setSelection(editable, i3);
            }
            editable.setSpan(OLD_SEL_START, iMin, iMin, 17);
            editable.replace(iMin, i3, strValueAt2, i4, i4 + 1);
            int spanStart2 = editable.getSpanStart(OLD_SEL_START);
            int selectionEnd2 = Selection.getSelectionEnd(editable);
            if (selectionEnd2 != spanStart2) {
                Selection.setSelection(editable, spanStart2, selectionEnd2);
                editable.setSpan(TextKeyListener.LAST_TYPED, spanStart2, selectionEnd2, 33);
                editable.setSpan(TextKeyListener.ACTIVE, spanStart2, selectionEnd2, 33 | (i5 << 24));
            }
            removeTimeouts(editable);
            new Timeout(this, editable);
            if (editable.getSpanStart(this) < 0) {
                for (Object obj : (KeyListener[]) editable.getSpans(0, editable.length(), KeyListener.class)) {
                    editable.removeSpan(obj);
                }
                editable.setSpan(this, 0, editable.length(), 18);
            }
            return true;
        }
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
        if (obj == Selection.SELECTION_END) {
            spannable.removeSpan(TextKeyListener.ACTIVE);
            removeTimeouts(spannable);
        }
    }

    private static void removeTimeouts(Spannable spannable) {
        for (Timeout timeout : (Timeout[]) spannable.getSpans(0, spannable.length(), Timeout.class)) {
            timeout.removeCallbacks(timeout);
            timeout.mBuffer = null;
            spannable.removeSpan(timeout);
        }
    }

    private class Timeout extends Handler implements Runnable {
        private Editable mBuffer;

        public Timeout(MultiTapKeyListener multiTapKeyListener, Editable editable) {
            this.mBuffer = editable;
            editable.setSpan(this, 0, editable.length(), 18);
            postAtTime(this, SystemClock.uptimeMillis() + 2000);
        }

        @Override // java.lang.Runnable
        public void run() {
            Editable editable = this.mBuffer;
            if (editable != null) {
                int selectionStart = Selection.getSelectionStart(editable);
                int selectionEnd = Selection.getSelectionEnd(editable);
                int spanStart = editable.getSpanStart(TextKeyListener.ACTIVE);
                int spanEnd = editable.getSpanEnd(TextKeyListener.ACTIVE);
                if (selectionStart == spanStart && selectionEnd == spanEnd) {
                    Selection.setSelection(editable, Selection.getSelectionEnd(editable));
                }
                editable.removeSpan(this);
            }
        }
    }
}
