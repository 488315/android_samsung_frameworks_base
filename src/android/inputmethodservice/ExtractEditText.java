package android.inputmethodservice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* loaded from: classes2.dex */
public class ExtractEditText extends EditText {
    private InputMethodService mIME;
    private int mSettingExtractedText;

    @Override // android.widget.TextView
    public boolean isInExtractedMode() {
        return true;
    }

    @Override // android.widget.TextView
    public boolean isInputMethodTarget() {
        return true;
    }

    public ExtractEditText(Context context) {
        super(context, null);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842862);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    void setIME(InputMethodService inputMethodService) {
        this.mIME = inputMethodService;
    }

    public void startInternalChanges() {
        this.mSettingExtractedText++;
    }

    public void finishInternalChanges() {
        this.mSettingExtractedText--;
    }

    @Override // android.widget.TextView
    public void setExtractedText(ExtractedText extractedText) {
        try {
            this.mSettingExtractedText++;
            super.setExtractedText(extractedText);
        } finally {
            this.mSettingExtractedText--;
        }
    }

    @Override // android.widget.TextView
    protected void onSelectionChanged(int i, int i2) {
        InputMethodService inputMethodService;
        if (this.mSettingExtractedText != 0 || (inputMethodService = this.mIME) == null || i < 0 || i2 < 0) {
            return;
        }
        inputMethodService.onExtractedSelectionChanged(i, i2);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performClick() {
        InputMethodService inputMethodService;
        if (super.performClick() || (inputMethodService = this.mIME) == null) {
            return false;
        }
        inputMethodService.onExtractedTextClicked();
        return true;
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i == 16908319 || i == 16908340) {
            return super.onTextContextMenuItem(i);
        }
        InputMethodService inputMethodService = this.mIME;
        if (inputMethodService == null || !inputMethodService.onExtractTextContextMenuItem(i)) {
            return super.onTextContextMenuItem(i);
        }
        if (i != 16908321 && i != 16908322) {
            return true;
        }
        stopTextActionMode();
        return true;
    }

    public boolean hasVerticalScrollBar() {
        return computeVerticalScrollRange() > computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return isEnabled();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return isEnabled();
    }

    @Override // android.view.View
    public boolean hasFocus() {
        return isEnabled();
    }

    @Override // android.widget.TextView
    protected void viewClicked(InputMethodManager inputMethodManager) {
        InputMethodService inputMethodService = this.mIME;
        if (inputMethodService != null) {
            inputMethodService.onViewClicked(false);
        }
    }

    @Override // android.widget.TextView
    protected void deleteText_internal(int i, int i2) {
        this.mIME.onExtractedDeleteText(i, i2);
    }

    @Override // android.widget.TextView
    protected void replaceText_internal(int i, int i2, CharSequence charSequence) {
        this.mIME.onExtractedReplaceText(i, i2, charSequence);
    }

    @Override // android.widget.TextView
    protected void setSpan_internal(Object obj, int i, int i2, int i3) {
        this.mIME.onExtractedSetSpan(obj, i, i2, i3);
    }

    @Override // android.widget.TextView
    protected void setCursorPosition_internal(int i, int i2) {
        this.mIME.onExtractedSelectionChanged(i, i2);
    }
}
