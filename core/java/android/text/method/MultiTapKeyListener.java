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
    private static final SparseArray<String> sRecs = new SparseArray<>();
    private boolean mAutoText;
    private TextKeyListener.Capitalize mCapitalize;

    static {
        sRecs.put(8, ".,1!@#$%^&*:/?'=()");
        sRecs.put(9, "abc2ABC");
        sRecs.put(10, "def3DEF");
        sRecs.put(11, "ghi4GHI");
        sRecs.put(12, "jkl5JKL");
        sRecs.put(13, "mno6MNO");
        sRecs.put(14, "pqrs7PQRS");
        sRecs.put(15, "tuv8TUV");
        sRecs.put(16, "wxyz9WXYZ");
        sRecs.put(7, "0+");
        sRecs.put(18, " ");
    }

    public MultiTapKeyListener(TextKeyListener.Capitalize cap, boolean autotext) {
        this.mCapitalize = cap;
        this.mAutoText = autotext;
    }

    public static MultiTapKeyListener getInstance(boolean z, TextKeyListener.Capitalize capitalize) {
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        if (sInstance[ordinal] == null) {
            sInstance[ordinal] = new MultiTapKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(View view, Editable content, int keyCode, KeyEvent event) {
        int pref;
        int rec;
        int selStart;
        int off;
        if (view == null) {
            pref = 0;
        } else {
            int pref2 = TextKeyListener.getInstance().getPrefs(view.getContext());
            pref = pref2;
        }
        int a = Selection.getSelectionStart(content);
        int b = Selection.getSelectionEnd(content);
        int selStart2 = Math.min(a, b);
        int selEnd = Math.max(a, b);
        int activeStart = content.getSpanStart(TextKeyListener.ACTIVE);
        int activeEnd = content.getSpanEnd(TextKeyListener.ACTIVE);
        int rec2 = (content.getSpanFlags(TextKeyListener.ACTIVE) & (-16777216)) >>> 24;
        if (activeStart == selStart2 && activeEnd == selEnd && selEnd - selStart2 == 1 && rec2 >= 0 && rec2 < sRecs.size()) {
            if (keyCode == 17) {
                char current = content.charAt(selStart2);
                if (Character.isLowerCase(current)) {
                    content.replace(selStart2, selEnd, String.valueOf(current).toUpperCase());
                    removeTimeouts(content);
                    new Timeout(content);
                    return true;
                }
                if (Character.isUpperCase(current)) {
                    content.replace(selStart2, selEnd, String.valueOf(current).toLowerCase());
                    removeTimeouts(content);
                    new Timeout(content);
                    return true;
                }
            }
            if (sRecs.indexOfKey(keyCode) == rec2) {
                String val = sRecs.valueAt(rec2);
                char ch = content.charAt(selStart2);
                int ix = val.indexOf(ch);
                if (ix >= 0) {
                    int ix2 = (ix + 1) % val.length();
                    content.replace(selStart2, selEnd, val, ix2, ix2 + 1);
                    removeTimeouts(content);
                    new Timeout(content);
                    return true;
                }
            }
            int rec3 = sRecs.indexOfKey(keyCode);
            if (rec3 < 0) {
                rec = selStart2;
                selStart = rec3;
            } else {
                Selection.setSelection(content, selEnd, selEnd);
                rec = selEnd;
                selStart = rec3;
            }
        } else {
            rec = selStart2;
            selStart = sRecs.indexOfKey(keyCode);
        }
        if (selStart >= 0) {
            String val2 = sRecs.valueAt(selStart);
            if ((pref & 1) != 0 && TextKeyListener.shouldCap(this.mCapitalize, content, rec)) {
                for (int i = 0; i < val2.length(); i++) {
                    if (Character.isUpperCase(val2.charAt(i))) {
                        int off2 = i;
                        off = off2;
                        break;
                    }
                }
            }
            off = 0;
            if (rec != selEnd) {
                Selection.setSelection(content, selEnd);
            }
            content.setSpan(OLD_SEL_START, rec, rec, 17);
            content.replace(rec, selEnd, val2, off, off + 1);
            int oldStart = content.getSpanStart(OLD_SEL_START);
            int selEnd2 = Selection.getSelectionEnd(content);
            if (selEnd2 != oldStart) {
                Selection.setSelection(content, oldStart, selEnd2);
                content.setSpan(TextKeyListener.LAST_TYPED, oldStart, selEnd2, 33);
                content.setSpan(TextKeyListener.ACTIVE, oldStart, selEnd2, 33 | (selStart << 24));
            }
            removeTimeouts(content);
            new Timeout(content);
            if (content.getSpanStart(this) < 0) {
                Object[] methods = (KeyListener[]) content.getSpans(0, content.length(), KeyListener.class);
                for (Object method : methods) {
                    content.removeSpan(method);
                }
                content.setSpan(this, 0, content.length(), 18);
            }
            return true;
        }
        return super.onKeyDown(view, content, keyCode, event);
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(Spannable buf, Object what, int s, int e, int start, int stop) {
        if (what == Selection.SELECTION_END) {
            buf.removeSpan(TextKeyListener.ACTIVE);
            removeTimeouts(buf);
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

        public Timeout(Editable buffer) {
            this.mBuffer = buffer;
            this.mBuffer.setSpan(this, 0, this.mBuffer.length(), 18);
            postAtTime(this, SystemClock.uptimeMillis() + 2000);
        }

        @Override // java.lang.Runnable
        public void run() {
            Spannable buf = this.mBuffer;
            if (buf != null) {
                int st = Selection.getSelectionStart(buf);
                int en = Selection.getSelectionEnd(buf);
                int start = buf.getSpanStart(TextKeyListener.ACTIVE);
                int end = buf.getSpanEnd(TextKeyListener.ACTIVE);
                if (st == start && en == end) {
                    Selection.setSelection(buf, Selection.getSelectionEnd(buf));
                }
                buf.removeSpan(this);
            }
        }
    }

    @Override // android.text.SpanWatcher
    public void onSpanAdded(Spannable s, Object what, int start, int end) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(Spannable s, Object what, int start, int end) {
    }
}
