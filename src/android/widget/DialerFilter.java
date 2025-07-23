package android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;

@Deprecated
/* loaded from: classes5.dex */
public class DialerFilter extends RelativeLayout {
    public static final int DIGITS_AND_LETTERS = 1;
    public static final int DIGITS_AND_LETTERS_NO_DIGITS = 2;
    public static final int DIGITS_AND_LETTERS_NO_LETTERS = 3;
    public static final int DIGITS_ONLY = 4;
    public static final int LETTERS_ONLY = 5;
    EditText mDigits;
    EditText mHint;
    ImageView mIcon;
    InputFilter[] mInputFilters;
    private boolean mIsQwerty;
    EditText mLetters;
    int mMode;
    EditText mPrimary;

    protected void onModeChange(int i, int i2) {
    }

    public DialerFilter(Context context) {
        super(context);
    }

    public DialerFilter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputFilters = new InputFilter[]{new InputFilter.AllCaps()};
        EditText editText = (EditText) findViewById(16908293);
        this.mHint = editText;
        if (editText == null) {
            throw new IllegalStateException("DialerFilter must have a child EditText named hint");
        }
        editText.setFilters(this.mInputFilters);
        EditText editText2 = this.mHint;
        this.mLetters = editText2;
        editText2.setKeyListener(TextKeyListener.getInstance());
        this.mLetters.setMovementMethod(null);
        this.mLetters.setFocusable(false);
        EditText editText3 = (EditText) findViewById(16908300);
        this.mPrimary = editText3;
        if (editText3 == null) {
            throw new IllegalStateException("DialerFilter must have a child EditText named primary");
        }
        editText3.setFilters(this.mInputFilters);
        EditText editText4 = this.mPrimary;
        this.mDigits = editText4;
        editText4.setKeyListener(DialerKeyListener.getInstance());
        this.mDigits.setMovementMethod(null);
        this.mDigits.setFocusable(false);
        this.mIcon = (ImageView) findViewById(16908294);
        setFocusable(true);
        this.mIsQwerty = true;
        setMode(1);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    public boolean isQwertyKeyboard() {
        return this.mIsQwerty;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
    
        if (r0 != 5) goto L46;
     */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r8, android.view.KeyEvent r9) {
        /*
            r7 = this;
            r0 = 66
            r1 = 1
            r2 = 0
            if (r8 == r0) goto Ld5
            r0 = 67
            r3 = 5
            r4 = 4
            r5 = 3
            r6 = 2
            if (r8 == r0) goto L68
            switch(r8) {
                case 19: goto Ld5;
                case 20: goto Ld5;
                case 21: goto Ld5;
                case 22: goto Ld5;
                case 23: goto Ld5;
                default: goto L11;
            }
        L11:
            int r0 = r7.mMode
            if (r0 == r1) goto L2f
            if (r0 == r6) goto L27
            if (r0 == r5) goto L1f
            if (r0 == r4) goto L1f
            if (r0 == r3) goto L27
            goto Ld5
        L1f:
            android.widget.EditText r0 = r7.mDigits
            boolean r2 = r0.onKeyDown(r8, r9)
            goto Ld5
        L27:
            android.widget.EditText r0 = r7.mLetters
            boolean r2 = r0.onKeyDown(r8, r9)
            goto Ld5
        L2f:
            android.widget.EditText r0 = r7.mLetters
            boolean r2 = r0.onKeyDown(r8, r9)
            boolean r0 = android.view.KeyEvent.isModifierKey(r8)
            if (r0 == 0) goto L43
            android.widget.EditText r0 = r7.mDigits
            r0.onKeyDown(r8, r9)
            r2 = r1
            goto Ld5
        L43:
            boolean r0 = r9.isPrintingKey()
            if (r0 != 0) goto L51
            r0 = 62
            if (r8 == r0) goto L51
            r0 = 61
            if (r8 != r0) goto Ld5
        L51:
            char[] r0 = android.text.method.DialerKeyListener.CHARACTERS
            char r0 = r9.getMatch(r0)
            if (r0 == 0) goto L63
            android.widget.EditText r0 = r7.mDigits
            boolean r0 = r0.onKeyDown(r8, r9)
            r0 = r0 & r2
            r2 = r0
            goto Ld5
        L63:
            r7.setMode(r6)
            goto Ld5
        L68:
            int r0 = r7.mMode
            if (r0 == r1) goto Lc8
            if (r0 == r6) goto La8
            if (r0 == r5) goto L83
            if (r0 == r4) goto L7c
            if (r0 == r3) goto L75
            goto Ld5
        L75:
            android.widget.EditText r0 = r7.mLetters
            boolean r2 = r0.onKeyDown(r8, r9)
            goto Ld5
        L7c:
            android.widget.EditText r0 = r7.mDigits
            boolean r2 = r0.onKeyDown(r8, r9)
            goto Ld5
        L83:
            android.widget.EditText r0 = r7.mDigits
            android.text.Editable r0 = r0.getText()
            int r0 = r0.length()
            android.widget.EditText r2 = r7.mLetters
            android.text.Editable r2 = r2.getText()
            int r2 = r2.length()
            if (r0 != r2) goto La1
            android.widget.EditText r0 = r7.mLetters
            r0.onKeyDown(r8, r9)
            r7.setMode(r1)
        La1:
            android.widget.EditText r0 = r7.mDigits
            boolean r2 = r0.onKeyDown(r8, r9)
            goto Ld5
        La8:
            android.widget.EditText r0 = r7.mLetters
            boolean r2 = r0.onKeyDown(r8, r9)
            android.widget.EditText r0 = r7.mLetters
            android.text.Editable r0 = r0.getText()
            int r0 = r0.length()
            android.widget.EditText r3 = r7.mDigits
            android.text.Editable r3 = r3.getText()
            int r3 = r3.length()
            if (r0 != r3) goto Ld5
            r7.setMode(r1)
            goto Ld5
        Lc8:
            android.widget.EditText r0 = r7.mDigits
            boolean r0 = r0.onKeyDown(r8, r9)
            android.widget.EditText r2 = r7.mLetters
            boolean r2 = r2.onKeyDown(r8, r9)
            r2 = r2 & r0
        Ld5:
            if (r2 != 0) goto Ldc
            boolean r7 = super.onKeyDown(r8, r9)
            return r7
        Ldc:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.DialerFilter.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mLetters.onKeyUp(i, keyEvent) || this.mDigits.onKeyUp(i, keyEvent);
    }

    public int getMode() {
        return this.mMode;
    }

    public void setMode(int i) {
        if (i == 1) {
            makeDigitsPrimary();
            this.mLetters.setVisibility(0);
            this.mDigits.setVisibility(0);
        } else if (i == 2) {
            makeLettersPrimary();
            this.mLetters.setVisibility(0);
            this.mDigits.setVisibility(4);
        } else if (i == 3) {
            makeDigitsPrimary();
            this.mLetters.setVisibility(4);
            this.mDigits.setVisibility(0);
        } else if (i == 4) {
            makeDigitsPrimary();
            this.mLetters.setVisibility(8);
            this.mDigits.setVisibility(0);
        } else if (i == 5) {
            makeLettersPrimary();
            this.mLetters.setVisibility(0);
            this.mDigits.setVisibility(8);
        }
        int i2 = this.mMode;
        this.mMode = i;
        onModeChange(i2, i);
    }

    private void makeLettersPrimary() {
        if (this.mPrimary == this.mDigits) {
            swapPrimaryAndHint(true);
        }
    }

    private void makeDigitsPrimary() {
        if (this.mPrimary == this.mLetters) {
            swapPrimaryAndHint(false);
        }
    }

    private void swapPrimaryAndHint(boolean z) {
        Editable text = this.mLetters.getText();
        Editable text2 = this.mDigits.getText();
        KeyListener keyListener = this.mLetters.getKeyListener();
        KeyListener keyListener2 = this.mDigits.getKeyListener();
        if (z) {
            this.mLetters = this.mPrimary;
            this.mDigits = this.mHint;
        } else {
            this.mLetters = this.mHint;
            this.mDigits = this.mPrimary;
        }
        this.mLetters.setKeyListener(keyListener);
        this.mLetters.lambda$setTextAsync$0(text);
        Editable text3 = this.mLetters.getText();
        Selection.setSelection(text3, text3.length());
        this.mDigits.setKeyListener(keyListener2);
        this.mDigits.lambda$setTextAsync$0(text2);
        Editable text4 = this.mDigits.getText();
        Selection.setSelection(text4, text4.length());
        this.mPrimary.setFilters(this.mInputFilters);
        this.mHint.setFilters(this.mInputFilters);
    }

    public CharSequence getLetters() {
        if (this.mLetters.getVisibility() == 0) {
            return this.mLetters.getText();
        }
        return "";
    }

    public CharSequence getDigits() {
        if (this.mDigits.getVisibility() == 0) {
            return this.mDigits.getText();
        }
        return "";
    }

    public CharSequence getFilterText() {
        if (this.mMode != 4) {
            return getLetters();
        }
        return getDigits();
    }

    public void append(String str) {
        int i = this.mMode;
        if (i == 1) {
            this.mDigits.getText().append((CharSequence) str);
            this.mLetters.getText().append((CharSequence) str);
            return;
        }
        if (i != 2) {
            if (i == 3 || i == 4) {
                this.mDigits.getText().append((CharSequence) str);
                return;
            } else if (i != 5) {
                return;
            }
        }
        this.mLetters.getText().append((CharSequence) str);
    }

    public void clearText() {
        this.mLetters.getText().clear();
        this.mDigits.getText().clear();
        if (this.mIsQwerty) {
            setMode(1);
        } else {
            setMode(4);
        }
    }

    public void setLettersWatcher(TextWatcher textWatcher) {
        Editable text = this.mLetters.getText();
        text.setSpan(textWatcher, 0, text.length(), 18);
    }

    public void setDigitsWatcher(TextWatcher textWatcher) {
        Editable text = this.mDigits.getText();
        text.setSpan(textWatcher, 0, text.length(), 18);
    }

    public void setFilterWatcher(TextWatcher textWatcher) {
        if (this.mMode != 4) {
            setLettersWatcher(textWatcher);
        } else {
            setDigitsWatcher(textWatcher);
        }
    }

    public void removeFilterWatcher(TextWatcher textWatcher) {
        Editable text;
        if (this.mMode != 4) {
            text = this.mLetters.getText();
        } else {
            text = this.mDigits.getText();
        }
        text.removeSpan(textWatcher);
    }
}
