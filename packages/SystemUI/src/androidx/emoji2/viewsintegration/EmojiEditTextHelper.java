package androidx.emoji2.viewsintegration;

import android.widget.EditText;
import androidx.core.util.Preconditions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EmojiEditTextHelper {
    public final HelperInternal19 mHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HelperInternal {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HelperInternal19 extends HelperInternal {
        public final EditText mEditText;
        public final EmojiTextWatcher mTextWatcher;

        public HelperInternal19(EditText editText, boolean z) {
            this.mEditText = editText;
            EmojiTextWatcher emojiTextWatcher = new EmojiTextWatcher(editText, z);
            this.mTextWatcher = emojiTextWatcher;
            editText.addTextChangedListener(emojiTextWatcher);
            editText.setEditableFactory(EmojiEditableFactory.getInstance());
        }
    }

    public EmojiEditTextHelper(EditText editText) {
        this(editText, true);
    }

    public EmojiEditTextHelper(EditText editText, boolean z) {
        Preconditions.checkNotNull(editText, "editText cannot be null");
        this.mHelper = new HelperInternal19(editText, z);
    }
}
