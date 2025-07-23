package android.text.method;

import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.method.TextKeyListener;
import android.util.SparseArray;

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
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        MultiTapKeyListener[] multiTapKeyListenerArr = sInstance;
        if (multiTapKeyListenerArr[ordinal] == null) {
            multiTapKeyListenerArr[ordinal] = new MultiTapKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015d  */
    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(android.view.View r14, android.text.Editable r15, int r16, android.view.KeyEvent r17) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.MultiTapKeyListener.onKeyDown(android.view.View, android.text.Editable, int, android.view.KeyEvent):boolean");
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
