package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.emoji2.text.EmojiCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiTextWatcher implements TextWatcher {
    public final EditText mEditText;
    public final boolean mExpectInitializedEmojiCompat;
    public InitCallbackImpl mInitCallback;
    public final int mMaxEmojiCount = Integer.MAX_VALUE;
    public boolean mEnabled = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InitCallbackImpl extends EmojiCompat.InitCallback {
        public final Reference mViewRef;

        public InitCallbackImpl(EditText editText) {
            this.mViewRef = new WeakReference(editText);
        }

        @Override // androidx.emoji2.text.EmojiCompat.InitCallback
        public final void onInitialized() {
            EmojiTextWatcher.processTextOnEnablingEvent((EditText) this.mViewRef.get(), 1);
        }
    }

    public EmojiTextWatcher(EditText editText, boolean z) {
        this.mEditText = editText;
        this.mExpectInitializedEmojiCompat = z;
    }

    public static void processTextOnEnablingEvent(EditText editText, int i) {
        int length;
        if (i == 1 && editText != null && editText.isAttachedToWindow()) {
            Editable editableText = editText.getEditableText();
            int selectionStart = Selection.getSelectionStart(editableText);
            int selectionEnd = Selection.getSelectionEnd(editableText);
            EmojiCompat emojiCompat = EmojiCompat.get();
            if (editableText == null) {
                length = 0;
            } else {
                emojiCompat.getClass();
                length = editableText.length();
            }
            emojiCompat.process(0, length, editableText, Integer.MAX_VALUE);
            if (selectionStart >= 0 && selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionStart, selectionEnd);
            } else if (selectionStart >= 0) {
                Selection.setSelection(editableText, selectionStart);
            } else if (selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionEnd);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:
    
        if ((androidx.emoji2.text.EmojiCompat.sInstance != null) == false) goto L13;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004a  */
    @Override // android.text.TextWatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        boolean z;
        int loadState;
        if (this.mEditText.isInEditMode()) {
            return;
        }
        if (this.mEnabled) {
            z = false;
            if (!this.mExpectInitializedEmojiCompat) {
            }
            if (z && i2 <= i3 && (charSequence instanceof Spannable)) {
                loadState = EmojiCompat.get().getLoadState();
                if (loadState != 0) {
                    if (loadState == 1) {
                        EmojiCompat.get().process(i, i3 + i, (Spannable) charSequence, this.mMaxEmojiCount);
                        return;
                    } else if (loadState != 3) {
                        return;
                    }
                }
                EmojiCompat emojiCompat = EmojiCompat.get();
                if (this.mInitCallback == null) {
                    this.mInitCallback = new InitCallbackImpl(this.mEditText);
                }
                emojiCompat.registerInitCallback(this.mInitCallback);
            }
            return;
        }
        z = true;
        if (z) {
            return;
        }
        loadState = EmojiCompat.get().getLoadState();
        if (loadState != 0) {
        }
        EmojiCompat emojiCompat2 = EmojiCompat.get();
        if (this.mInitCallback == null) {
        }
        emojiCompat2.registerInitCallback(this.mInitCallback);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
