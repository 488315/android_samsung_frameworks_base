package androidx.slice.widget;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.slice.SliceItem;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RemoteInputView extends LinearLayout implements View.OnClickListener, TextWatcher {
    public static final Object VIEW_TAG = null;
    public final SliceItem mAction;
    public RemoteEditText mEditText;
    public ProgressBar mProgressBar;
    public final RemoteInput mRemoteInput;
    public final RemoteInput[] mRemoteInputs;
    public boolean mResetting;
    public ImageButton mSendButton;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RemoteEditText extends EditText {
        public final Drawable mBackground;
        public RemoteInputView mRemoteInputView;
        public boolean mShowImeOnInputConnection;

        public RemoteEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mBackground = getBackground();
        }

        public final void defocusIfNeeded() {
            if (this.mRemoteInputView != null || isTemporarilyDetached()) {
                isTemporarilyDetached();
                return;
            }
            if (isFocusable() && isEnabled()) {
                setInnerFocusable(false);
                RemoteInputView remoteInputView = this.mRemoteInputView;
                if (remoteInputView != null) {
                    remoteInputView.setVisibility(4);
                }
                this.mShowImeOnInputConnection = false;
            }
        }

        @Override // android.widget.TextView, android.view.View
        public final void getFocusedRect(Rect rect) {
            super.getFocusedRect(rect);
            rect.top = getScrollY();
            rect.bottom = (getBottom() - getTop()) + getScrollY();
        }

        @Override // android.widget.TextView
        public final void onCommitCompletion(CompletionInfo completionInfo) {
            clearComposingText();
            setText(completionInfo.getText());
            setSelection(getText().length());
        }

        @Override // android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            final InputMethodManager inputMethodManager;
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.mShowImeOnInputConnection && onCreateInputConnection != null && (inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class)) != null) {
                post(new Runnable() { // from class: androidx.slice.widget.RemoteInputView.RemoteEditText.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        inputMethodManager.viewClicked(RemoteEditText.this);
                        inputMethodManager.showSoftInput(RemoteEditText.this, 0);
                    }
                });
            }
            return onCreateInputConnection;
        }

        @Override // android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            if (z) {
                return;
            }
            defocusIfNeeded();
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyDown(int i, KeyEvent keyEvent) {
            if (i == 4) {
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        }

        @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyUp(int i, KeyEvent keyEvent) {
            if (i != 4) {
                return super.onKeyUp(i, keyEvent);
            }
            defocusIfNeeded();
            return true;
        }

        @Override // android.widget.TextView, android.view.View
        public final void onVisibilityChanged(View view, int i) {
            super.onVisibilityChanged(view, i);
            if (isShown()) {
                return;
            }
            defocusIfNeeded();
        }

        public final void setInnerFocusable(boolean z) {
            setFocusableInTouchMode(z);
            setFocusable(z);
            setCursorVisible(z);
            if (!z) {
                setBackground(null);
            } else {
                requestFocus();
                setBackground(this.mBackground);
            }
        }
    }

    public RemoteInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        updateSendButton();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchFinishTemporaryDetach() {
        if (isAttachedToWindow()) {
            RemoteEditText remoteEditText = this.mEditText;
            attachViewToParent(remoteEditText, 0, remoteEditText.getLayoutParams());
        } else {
            removeDetachedView(this.mEditText, false);
        }
        super.dispatchFinishTemporaryDetach();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        detachViewFromParent(this.mEditText);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mSendButton) {
            sendRemoteInput();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mProgressBar = (ProgressBar) findViewById(R.id.remote_input_progress);
        ImageButton imageButton = (ImageButton) findViewById(R.id.remote_input_send);
        this.mSendButton = imageButton;
        imageButton.setOnClickListener(this);
        RemoteEditText remoteEditText = (RemoteEditText) getChildAt(0);
        this.mEditText = remoteEditText;
        remoteEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.slice.widget.RemoteInputView.1
            /* JADX WARN: Removed duplicated region for block: B:28:0x0042  */
            @Override // android.widget.TextView.OnEditorActionListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onEditorAction(android.widget.TextView r4, int r5, android.view.KeyEvent r6) {
                /*
                    r3 = this;
                    r4 = 0
                    r0 = 1
                    if (r6 != 0) goto Lf
                    r1 = 6
                    if (r5 == r1) goto Ld
                    r1 = 5
                    if (r5 == r1) goto Ld
                    r1 = 4
                    if (r5 != r1) goto Lf
                Ld:
                    r5 = r0
                    goto L10
                Lf:
                    r5 = r4
                L10:
                    if (r6 == 0) goto L31
                    int r1 = r6.getKeyCode()
                    java.lang.Object r2 = androidx.slice.widget.RemoteInputView.VIEW_TAG
                    r2 = 23
                    if (r1 == r2) goto L29
                    r2 = 62
                    if (r1 == r2) goto L29
                    r2 = 66
                    if (r1 == r2) goto L29
                    r2 = 160(0xa0, float:2.24E-43)
                    if (r1 == r2) goto L29
                    goto L31
                L29:
                    int r6 = r6.getAction()
                    if (r6 != 0) goto L31
                    r6 = r0
                    goto L32
                L31:
                    r6 = r4
                L32:
                    if (r5 != 0) goto L38
                    if (r6 == 0) goto L37
                    goto L38
                L37:
                    return r4
                L38:
                    androidx.slice.widget.RemoteInputView r4 = androidx.slice.widget.RemoteInputView.this
                    androidx.slice.widget.RemoteInputView$RemoteEditText r4 = r4.mEditText
                    int r4 = r4.length()
                    if (r4 <= 0) goto L47
                    androidx.slice.widget.RemoteInputView r3 = androidx.slice.widget.RemoteInputView.this
                    r3.sendRemoteInput()
                L47:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RemoteInputView.AnonymousClass1.onEditorAction(android.widget.TextView, int, android.view.KeyEvent):boolean");
            }
        });
        this.mEditText.addTextChangedListener(this);
        this.mEditText.setInnerFocusable(false);
        this.mEditText.mRemoteInputView = this;
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mResetting && view == this.mEditText) {
            return false;
        }
        return super.onRequestSendAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final void reset() {
        this.mResetting = true;
        this.mEditText.getText().clear();
        this.mEditText.setEnabled(true);
        this.mSendButton.setVisibility(0);
        this.mProgressBar.setVisibility(4);
        updateSendButton();
        setVisibility(4);
        this.mResetting = false;
    }

    public final void sendRemoteInput() {
        Bundle bundle = new Bundle();
        bundle.putString(this.mRemoteInput.getResultKey(), this.mEditText.getText().toString());
        Intent addFlags = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(this.mRemoteInputs, addFlags, bundle);
        this.mEditText.setEnabled(false);
        this.mSendButton.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mEditText.mShowImeOnInputConnection = false;
        try {
            this.mAction.fireActionInternal(getContext(), addFlags);
            reset();
        } catch (PendingIntent.CanceledException e) {
            Log.i("RemoteInput", "Unable to send remote input result", e);
            Toast.makeText(getContext(), "Failure sending pending intent for inline reply :(", 0).show();
            reset();
        }
    }

    public final void updateSendButton() {
        this.mSendButton.setEnabled(this.mEditText.getText().length() != 0);
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
