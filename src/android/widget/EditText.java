package android.widget;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.SpanUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class EditText extends TextView {
    private static final int ID_BOLD = 16908379;
    private static final int ID_ITALIC = 16908380;
    private static final int ID_UNDERLINE = 16908381;
    public static final long LINE_HEIGHT_FOR_LOCALE = 303326708;
    private boolean mStyleShortcutsEnabled;

    @Override // android.widget.TextView
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override // android.widget.TextView
    public boolean getFreezesText() {
        return true;
    }

    @Override // android.widget.TextView
    protected boolean supportsAutoSizeText() {
        return false;
    }

    public EditText(Context context) {
        this(context, null);
    }

    public EditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public EditText(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public EditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mStyleShortcutsEnabled = false;
        Resources.Theme theme = context.getTheme();
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.EditText, i, i2);
        try {
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == 0) {
                    this.mStyleShortcutsEnabled = obtainStyledAttributes.getBoolean(index, false);
                }
            }
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.TextView, i, i2);
            try {
                boolean hasValue = obtainStyledAttributes.hasValue(102);
                setLocalePreferredLineHeightForMinimumUsed(hasValue ? hasValue ? obtainStyledAttributes.getBoolean(102, false) : false : CompatChanges.isChangeEnabled(LINE_HEIGHT_FOR_LOCALE));
            } finally {
            }
        } finally {
        }
    }

    @Override // android.widget.TextView
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override // android.widget.TextView
    public Editable getText() {
        CharSequence text = super.getText();
        if (text == null) {
            return null;
        }
        if (text instanceof Editable) {
            return (Editable) text;
        }
        super.setText(text, TextView.BufferType.EDITABLE);
        return (Editable) super.getText();
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, TextView.BufferType.EDITABLE);
    }

    public void setSelection(int i, int i2) {
        Selection.setSelection(getText(), i, i2);
    }

    public void setSelection(int i) {
        Selection.setSelection(getText(), i);
    }

    public void selectAll() {
        Selection.selectAll(getText());
    }

    public void extendSelection(int i) {
        Selection.extendSelection(getText(), i);
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(truncateAt);
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return EditText.class.getName();
    }

    public void semHideCursorControllers() {
        super.hideCursorControllers();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        if (keyEvent.hasModifiers(4096)) {
            if (i != 30) {
                if (i == 37) {
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908380);
                    }
                } else if (i == 49 && this.mStyleShortcutsEnabled && hasSelection()) {
                    return onTextContextMenuItem(16908381);
                }
            } else if (this.mStyleShortcutsEnabled && hasSelection()) {
                return onTextContextMenuItem(16908379);
            }
        }
        return super.onKeyShortcut(i, keyEvent);
    }

    @Override // android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i == 16908379 || i == 16908380 || i == 16908381) {
            return performStylingAction(i);
        }
        return super.onTextContextMenuItem(i);
    }

    private boolean performStylingAction(int i) {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart >= 0 && selectionEnd >= 0) {
            int min = Math.min(selectionStart, selectionEnd);
            int max = Math.max(selectionStart, selectionEnd);
            Editable text = getText();
            if (i == 16908379) {
                return SpanUtils.toggleBold(text, min, max);
            }
            if (i == 16908380) {
                return SpanUtils.toggleItalic(text, min, max);
            }
            if (i == 16908381) {
                return SpanUtils.toggleUnderline(text, min, max);
            }
        }
        return false;
    }

    public void setStyleShortcutsEnabled(boolean z) {
        this.mStyleShortcutsEnabled = z;
    }

    public boolean isStyleShortcutEnabled() {
        return this.mStyleShortcutsEnabled;
    }
}
