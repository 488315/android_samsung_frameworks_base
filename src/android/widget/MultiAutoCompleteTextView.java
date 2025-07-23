package android.widget;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.QwertyKeyListener;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/* loaded from: classes5.dex */
public class MultiAutoCompleteTextView extends AutoCompleteTextView {
    private Tokenizer mTokenizer;

    public interface Tokenizer {
        int findTokenEnd(CharSequence charSequence, int i);

        int findTokenStart(CharSequence charSequence, int i);

        CharSequence terminateToken(CharSequence charSequence);
    }

    void finishInit() {
    }

    public MultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.mTokenizer = tokenizer;
    }

    @Override // android.widget.AutoCompleteTextView
    protected void performFiltering(CharSequence charSequence, int i) {
        if (enoughToFilter()) {
            int selectionEnd = getSelectionEnd();
            performFiltering(charSequence, this.mTokenizer.findTokenStart(charSequence, selectionEnd), selectionEnd, i);
            return;
        }
        dismissDropDown();
        Filter filter = getFilter();
        if (filter != null) {
            filter.filter(null);
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public boolean enoughToFilter() {
        Tokenizer tokenizer;
        Editable text = getText();
        int selectionEnd = getSelectionEnd();
        return selectionEnd >= 0 && (tokenizer = this.mTokenizer) != null && selectionEnd - tokenizer.findTokenStart(text, selectionEnd) >= getThreshold();
    }

    @Override // android.widget.AutoCompleteTextView
    public void performValidation() {
        AutoCompleteTextView.Validator validator = getValidator();
        if (validator == null || this.mTokenizer == null) {
            return;
        }
        Editable text = getText();
        int length = getText().length();
        while (length > 0) {
            int findTokenStart = this.mTokenizer.findTokenStart(text, length);
            CharSequence subSequence = text.subSequence(findTokenStart, this.mTokenizer.findTokenEnd(text, findTokenStart));
            if (TextUtils.isEmpty(subSequence)) {
                text.replace(findTokenStart, length, "");
            } else if (!validator.isValid(subSequence)) {
                text.replace(findTokenStart, length, this.mTokenizer.terminateToken(validator.fixText(subSequence)));
            }
            length = findTokenStart;
        }
    }

    protected void performFiltering(CharSequence charSequence, int i, int i2, int i3) {
        getFilter().filter(charSequence.subSequence(i, i2), this);
    }

    @Override // android.widget.AutoCompleteTextView
    protected void replaceText(CharSequence charSequence) {
        clearComposingText();
        int selectionEnd = getSelectionEnd();
        int findTokenStart = this.mTokenizer.findTokenStart(getText(), selectionEnd);
        Editable text = getText();
        QwertyKeyListener.markAsReplaced(text, findTokenStart, selectionEnd, TextUtils.substring(text, findTokenStart, selectionEnd));
        text.replace(findTokenStart, selectionEnd, this.mTokenizer.terminateToken(charSequence));
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.EditText, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return MultiAutoCompleteTextView.class.getName();
    }

    public static class CommaTokenizer implements Tokenizer {
        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public int findTokenStart(CharSequence charSequence, int i) {
            int i2 = i;
            while (i2 > 0 && charSequence.charAt(i2 - 1) != ',') {
                i2--;
            }
            while (i2 < i && charSequence.charAt(i2) == ' ') {
                i2++;
            }
            return i2;
        }

        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public int findTokenEnd(CharSequence charSequence, int i) {
            int length = charSequence.length();
            while (i < length) {
                if (charSequence.charAt(i) == ',') {
                    return i;
                }
                i++;
            }
            return length;
        }

        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public CharSequence terminateToken(CharSequence charSequence) {
            int length = charSequence.length();
            while (length > 0 && charSequence.charAt(length - 1) == ' ') {
                length--;
            }
            if (length > 0 && charSequence.charAt(length - 1) == ',') {
                return charSequence;
            }
            if (charSequence instanceof Spanned) {
                SpannableString spannableString = new SpannableString(((Object) charSequence) + ", ");
                TextUtils.copySpansFrom((Spanned) charSequence, 0, charSequence.length(), Object.class, spannableString, 0);
                return spannableString;
            }
            return ((Object) charSequence) + ", ";
        }
    }
}
