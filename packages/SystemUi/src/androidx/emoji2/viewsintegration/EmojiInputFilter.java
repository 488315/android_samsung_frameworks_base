package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiInputFilter implements InputFilter {
    public InitCallbackImpl mInitCallback;
    public final TextView mTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InitCallbackImpl extends EmojiCompat.InitCallback {
        public final Reference mEmojiInputFilterReference;
        public final Reference mViewRef;

        public InitCallbackImpl(TextView textView, EmojiInputFilter emojiInputFilter) {
            this.mViewRef = new WeakReference(textView);
            this.mEmojiInputFilterReference = new WeakReference(emojiInputFilter);
        }

        @Override // androidx.emoji2.text.EmojiCompat.InitCallback
        public final void onInitialized() {
            boolean z;
            int length;
            InputFilter[] filters;
            TextView textView = (TextView) this.mViewRef.get();
            InputFilter inputFilter = (InputFilter) this.mEmojiInputFilterReference.get();
            if (inputFilter != null && textView != null && (filters = textView.getFilters()) != null) {
                for (InputFilter inputFilter2 : filters) {
                    if (inputFilter2 == inputFilter) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (z && textView.isAttachedToWindow()) {
                CharSequence text = textView.getText();
                EmojiCompat emojiCompat = EmojiCompat.get();
                if (text == null) {
                    length = 0;
                } else {
                    emojiCompat.getClass();
                    length = text.length();
                }
                CharSequence process = emojiCompat.process(0, length, text, Integer.MAX_VALUE);
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
                    } else if (selectionStart >= 0) {
                        Selection.setSelection(spannable, selectionStart);
                    } else if (selectionEnd >= 0) {
                        Selection.setSelection(spannable, selectionEnd);
                    }
                }
            }
        }
    }

    public EmojiInputFilter(TextView textView) {
        this.mTextView = textView;
    }

    @Override // android.text.InputFilter
    public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        if (this.mTextView.isInEditMode()) {
            return charSequence;
        }
        int loadState = EmojiCompat.get().getLoadState();
        if (loadState != 0) {
            boolean z = true;
            if (loadState == 1) {
                if (i4 == 0 && i3 == 0 && spanned.length() == 0 && charSequence == this.mTextView.getText()) {
                    z = false;
                }
                if (!z || charSequence == null) {
                    return charSequence;
                }
                if (i != 0 || i2 != charSequence.length()) {
                    charSequence = charSequence.subSequence(i, i2);
                }
                return EmojiCompat.get().process(0, charSequence.length(), charSequence, Integer.MAX_VALUE);
            }
            if (loadState != 3) {
                return charSequence;
            }
        }
        EmojiCompat emojiCompat = EmojiCompat.get();
        if (this.mInitCallback == null) {
            this.mInitCallback = new InitCallbackImpl(this.mTextView, this);
        }
        emojiCompat.registerInitCallback(this.mInitCallback);
        return charSequence;
    }
}
