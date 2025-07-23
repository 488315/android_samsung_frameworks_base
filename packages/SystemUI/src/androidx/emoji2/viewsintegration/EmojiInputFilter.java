package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EmojiInputFilter implements InputFilter {
    public InitCallbackImpl mInitCallback;
    public final TextView mTextView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InitCallbackImpl extends EmojiCompat.InitCallback {
        public final Reference mEmojiInputFilterReference;
        public final Reference mViewRef;

        public InitCallbackImpl(TextView textView, EmojiInputFilter emojiInputFilter) {
            this.mViewRef = new WeakReference(textView);
            this.mEmojiInputFilterReference = new WeakReference(emojiInputFilter);
        }

        @Override // androidx.emoji2.text.EmojiCompat.InitCallback
        public final void onInitialized() {
            InputFilter[] filters;
            int length;
            TextView textView = (TextView) this.mViewRef.get();
            InputFilter inputFilter = (InputFilter) this.mEmojiInputFilterReference.get();
            if (inputFilter == null || textView == null || (filters = textView.getFilters()) == null) {
                return;
            }
            for (InputFilter inputFilter2 : filters) {
                if (inputFilter2 == inputFilter) {
                    if (textView.isAttachedToWindow()) {
                        CharSequence text = textView.getText();
                        EmojiCompat emojiCompat = EmojiCompat.get();
                        if (text == null) {
                            length = 0;
                        } else {
                            emojiCompat.getClass();
                            length = text.length();
                        }
                        CharSequence process = emojiCompat.process(0, length, 0, text);
                        if (text == process) {
                            return;
                        }
                        int selectionStart = Selection.getSelectionStart(process);
                        int selectionEnd = Selection.getSelectionEnd(process);
                        textView.setText(process);
                        if (process instanceof Spannable) {
                            Spannable spannable = (Spannable) process;
                            if (selectionStart >= 0 && selectionEnd >= 0) {
                                Selection.setSelection(spannable, selectionStart, selectionEnd);
                                return;
                            } else if (selectionStart >= 0) {
                                Selection.setSelection(spannable, selectionStart);
                                return;
                            } else {
                                if (selectionEnd >= 0) {
                                    Selection.setSelection(spannable, selectionEnd);
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
            }
        }
    }

    public EmojiInputFilter(TextView textView) {
        this.mTextView = textView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0017, code lost:
    
        if (r0 != 3) goto L27;
     */
    @Override // android.text.InputFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.CharSequence filter(java.lang.CharSequence r3, int r4, int r5, android.text.Spanned r6, int r7, int r8) {
        /*
            r2 = this;
            android.widget.TextView r0 = r2.mTextView
            boolean r0 = r0.isInEditMode()
            if (r0 == 0) goto L9
            goto L4a
        L9:
            androidx.emoji2.text.EmojiCompat r0 = androidx.emoji2.text.EmojiCompat.get()
            int r0 = r0.getLoadState()
            if (r0 == 0) goto L4b
            r1 = 1
            if (r0 == r1) goto L1a
            r4 = 3
            if (r0 == r4) goto L4b
            goto L4a
        L1a:
            if (r8 != 0) goto L2d
            if (r7 != 0) goto L2d
            int r6 = r6.length()
            if (r6 != 0) goto L2d
            android.widget.TextView r2 = r2.mTextView
            java.lang.CharSequence r2 = r2.getText()
            if (r3 != r2) goto L2d
            goto L4a
        L2d:
            if (r3 == 0) goto L4a
            if (r4 != 0) goto L38
            int r2 = r3.length()
            if (r5 != r2) goto L38
            goto L3c
        L38:
            java.lang.CharSequence r3 = r3.subSequence(r4, r5)
        L3c:
            androidx.emoji2.text.EmojiCompat r2 = androidx.emoji2.text.EmojiCompat.get()
            int r4 = r3.length()
            r5 = 0
            java.lang.CharSequence r2 = r2.process(r5, r4, r5, r3)
            return r2
        L4a:
            return r3
        L4b:
            androidx.emoji2.text.EmojiCompat r4 = androidx.emoji2.text.EmojiCompat.get()
            androidx.emoji2.viewsintegration.EmojiInputFilter$InitCallbackImpl r5 = r2.mInitCallback
            if (r5 != 0) goto L5c
            androidx.emoji2.viewsintegration.EmojiInputFilter$InitCallbackImpl r5 = new androidx.emoji2.viewsintegration.EmojiInputFilter$InitCallbackImpl
            android.widget.TextView r6 = r2.mTextView
            r5.<init>(r6, r2)
            r2.mInitCallback = r5
        L5c:
            androidx.emoji2.viewsintegration.EmojiInputFilter$InitCallbackImpl r2 = r2.mInitCallback
            r4.registerInitCallback(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.viewsintegration.EmojiInputFilter.filter(java.lang.CharSequence, int, int, android.text.Spanned, int, int):java.lang.CharSequence");
    }
}
