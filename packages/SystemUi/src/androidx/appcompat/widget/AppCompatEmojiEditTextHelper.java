package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.appcompat.R$styleable;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.viewsintegration.EmojiEditTextHelper;
import androidx.emoji2.viewsintegration.EmojiInputConnection;
import androidx.emoji2.viewsintegration.EmojiKeyListener;
import androidx.emoji2.viewsintegration.EmojiTextWatcher;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatEmojiEditTextHelper {
    public final EmojiEditTextHelper mEmojiEditTextHelper;
    public final EditText mView;

    public AppCompatEmojiEditTextHelper(EditText editText) {
        this.mView = editText;
        this.mEmojiEditTextHelper = new EmojiEditTextHelper(editText, false);
    }

    public final KeyListener getKeyListener(KeyListener keyListener) {
        if (!(!(keyListener instanceof NumberKeyListener))) {
            return keyListener;
        }
        this.mEmojiEditTextHelper.mHelper.getClass();
        if (keyListener instanceof EmojiKeyListener) {
            return keyListener;
        }
        if (keyListener == null) {
            return null;
        }
        return keyListener instanceof NumberKeyListener ? keyListener : new EmojiKeyListener(keyListener);
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView, i, 0);
        try {
            boolean z = obtainStyledAttributes.hasValue(14) ? obtainStyledAttributes.getBoolean(14, true) : true;
            obtainStyledAttributes.recycle();
            EmojiTextWatcher emojiTextWatcher = this.mEmojiEditTextHelper.mHelper.mTextWatcher;
            if (emojiTextWatcher.mEnabled != z) {
                if (emojiTextWatcher.mInitCallback != null) {
                    EmojiCompat emojiCompat = EmojiCompat.get();
                    EmojiTextWatcher.InitCallbackImpl initCallbackImpl = emojiTextWatcher.mInitCallback;
                    emojiCompat.getClass();
                    Preconditions.checkNotNull(initCallbackImpl, "initCallback cannot be null");
                    ReentrantReadWriteLock reentrantReadWriteLock = (ReentrantReadWriteLock) emojiCompat.mInitLock;
                    reentrantReadWriteLock.writeLock().lock();
                    try {
                        emojiCompat.mInitCallbacks.remove(initCallbackImpl);
                    } finally {
                        reentrantReadWriteLock.writeLock().unlock();
                    }
                }
                emojiTextWatcher.mEnabled = z;
                if (z) {
                    EmojiTextWatcher.processTextOnEnablingEvent(emojiTextWatcher.mEditText, EmojiCompat.get().getLoadState());
                }
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo) {
        EmojiEditTextHelper emojiEditTextHelper = this.mEmojiEditTextHelper;
        if (inputConnection == null) {
            emojiEditTextHelper.getClass();
            return null;
        }
        EmojiEditTextHelper.HelperInternal19 helperInternal19 = emojiEditTextHelper.mHelper;
        helperInternal19.getClass();
        if (!(inputConnection instanceof EmojiInputConnection)) {
            inputConnection = new EmojiInputConnection(helperInternal19.mEditText, inputConnection, editorInfo);
        }
        return inputConnection;
    }
}
