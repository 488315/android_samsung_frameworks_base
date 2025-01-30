package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.emoji2.viewsintegration.EmojiTextViewHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatEmojiTextHelper {
    public final EmojiTextViewHelper mEmojiTextViewHelper;
    public final TextView mView;

    public AppCompatEmojiTextHelper(TextView textView) {
        this.mView = textView;
        this.mEmojiTextViewHelper = new EmojiTextViewHelper(textView, false);
    }

    public final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
        return this.mEmojiTextViewHelper.mHelper.getFilters(inputFilterArr);
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView, i, 0);
        try {
            boolean z = obtainStyledAttributes.hasValue(14) ? obtainStyledAttributes.getBoolean(14, true) : true;
            obtainStyledAttributes.recycle();
            this.mEmojiTextViewHelper.mHelper.setEnabled(z);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void setAllCaps(boolean z) {
        this.mEmojiTextViewHelper.mHelper.setAllCaps(z);
    }
}
