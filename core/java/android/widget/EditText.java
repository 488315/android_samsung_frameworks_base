package android.widget;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.SpanUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class EditText extends TextView {
    private static final int ID_BOLD = 16908379;
    private static final int ID_ITALIC = 16908380;
    private static final int ID_UNDERLINE = 16908381;
    public static final long LINE_HEIGHT_FOR_LOCALE = 303326708;
    private boolean mStyleShortcutsEnabled;

    public EditText(Context context) {
        this(context, null);
    }

    public EditText(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int n;
        int i;
        this.mStyleShortcutsEnabled = false;
        Resources.Theme theme = context.getTheme();
        TypedArray tvArray = theme.obtainStyledAttributes(attrs, R.styleable.EditText, defStyleAttr, defStyleRes);
        try {
            n = tvArray.getIndexCount();
        } finally {
        }
        for (i = 0; i < n; i++) {
            int attr = tvArray.getIndex(i);
            switch (attr) {
                case 0:
                    this.mStyleShortcutsEnabled = tvArray.getBoolean(attr, false);
                    continue;
                default:
                    continue;
            }
        }
        tvArray.recycle();
        tvArray = theme.obtainStyledAttributes(attrs, R.styleable.TextView, defStyleAttr, defStyleRes);
        try {
            boolean hasUseLocalePreferredLineHeightForMinimumInt = tvArray.hasValue(102);
            boolean useLocalePreferredLineHeightForMinimumInt = hasUseLocalePreferredLineHeightForMinimumInt ? tvArray.getBoolean(102, false) : false;
            setLocalePreferredLineHeightForMinimumUsed(hasUseLocalePreferredLineHeightForMinimumInt ? useLocalePreferredLineHeightForMinimumInt : CompatChanges.isChangeEnabled(LINE_HEIGHT_FOR_LOCALE));
        } finally {
        }
    }

    @Override // android.widget.TextView
    public boolean getFreezesText() {
        return true;
    }

    @Override // android.widget.TextView
    protected boolean getDefaultEditable() {
        return true;
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
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, TextView.BufferType.EDITABLE);
    }

    public void setSelection(int start, int stop) {
        Selection.setSelection(getText(), start, stop);
    }

    public void setSelection(int index) {
        Selection.setSelection(getText(), index);
    }

    public void selectAll() {
        Selection.selectAll(getText());
    }

    public void extendSelection(int index) {
        Selection.extendSelection(getText(), index);
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        if (ellipsis == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(ellipsis);
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return EditText.class.getName();
    }

    @Override // android.widget.TextView
    protected boolean supportsAutoSizeText() {
        return false;
    }

    public void semHideCursorControllers() {
        super.hideCursorControllers();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if (event.hasModifiers(4096)) {
            switch (keyCode) {
                case 30:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908379);
                    }
                    break;
                case 37:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908380);
                    }
                    break;
                case 49:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908381);
                    }
                    break;
            }
        }
        return super.onKeyShortcut(keyCode, event);
    }

    @Override // android.widget.TextView
    public boolean onTextContextMenuItem(int id) {
        if (id == 16908379 || id == 16908380 || id == 16908381) {
            return performStylingAction(id);
        }
        return super.onTextContextMenuItem(id);
    }

    private boolean performStylingAction(int actionId) {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart < 0 || selectionEnd < 0) {
            return false;
        }
        int min = Math.min(selectionStart, selectionEnd);
        int max = Math.max(selectionStart, selectionEnd);
        Spannable spannable = getText();
        if (actionId == 16908379) {
            return SpanUtils.toggleBold(spannable, min, max);
        }
        if (actionId == 16908380) {
            return SpanUtils.toggleItalic(spannable, min, max);
        }
        if (actionId != 16908381) {
            return false;
        }
        return SpanUtils.toggleUnderline(spannable, min, max);
    }

    public void setStyleShortcutsEnabled(boolean enabled) {
        this.mStyleShortcutsEnabled = enabled;
    }

    public boolean isStyleShortcutEnabled() {
        return this.mStyleShortcutsEnabled;
    }
}
