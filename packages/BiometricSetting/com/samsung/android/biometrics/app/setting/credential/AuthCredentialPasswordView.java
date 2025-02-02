package com.samsung.android.biometrics.app.setting.credential;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public class AuthCredentialPasswordView extends AuthCredentialView implements TextView.OnEditorActionListener, TextWatcher {
    private ImageButton mBtnPasswordShow;
    private int mHeight;
    private final InputMethodManager mImm;
    protected boolean mIsPinType;
    private int mNdigitsPinEnabled;
    private AuthCredentialPasswordView$$ExternalSyntheticLambda1 mOnGlobalLayoutListener;
    protected AuthCredentialEditText mPasswordField;

    /* renamed from: $r8$lambda$b-eTuhJ0h1i1MlsCeIBmDH4A90U, reason: not valid java name */
    public static /* synthetic */ void m40$r8$lambda$beTuhJ0h1i1MlsCeIBmDH4A90U(AuthCredentialPasswordView authCredentialPasswordView) {
        authCredentialPasswordView.getClass();
        authCredentialPasswordView.getWindowVisibleDisplayFrame(new Rect());
        int height = authCredentialPasswordView.getRootView().getHeight();
        if (authCredentialPasswordView.mHeight == 0) {
            authCredentialPasswordView.mHeight = height;
        }
        int i = authCredentialPasswordView.mHeight;
        authCredentialPasswordView.onKeyboardVisibilityChanged(((double) (i - height)) > ((double) i) * 0.15d);
    }

    public AuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNdigitsPinEnabled = -1;
        this.mImm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
    }

    private void checkPasswordAndUnlock() {
        LockscreenCredential createPinOrNone = this.mPromptConfig.getCredentialType() == 1 ? LockscreenCredential.createPinOrNone(this.mPasswordField.getText()) : LockscreenCredential.createPasswordOrNone(this.mPasswordField.getText());
        try {
            if (createPinOrNone.isNone()) {
                createPinOrNone.close();
            } else {
                this.mPendingLockCheck = LockPatternChecker.verifyCredential(this.mLockPatternUtils, createPinOrNone, getEffectiveUserId(), 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda0
                    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
                        AuthCredentialPasswordView.this.onCredentialVerified(verifyCredentialResponse, i);
                    }
                });
                createPinOrNone.close();
            }
        } catch (Throwable th) {
            if (createPinOrNone != null) {
                try {
                    createPinOrNone.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        if (this.mNdigitsPinEnabled > 0) {
            if (editable.length() == this.mNdigitsPinEnabled) {
                this.mPasswordField.onEditorAction(6);
            }
        } else {
            Button button = this.mBtnContinue;
            if (button == null || this.mAlertMode != 0) {
                return;
            }
            button.setEnabled(this.mPasswordField.getText().length() > 0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void enterAlertMode(int i) {
        Log.d("BSS_AuthCredentialPasswordView", "enterAlertMode: " + i);
        super.enterAlertMode(i);
        this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setText("");
            this.mPasswordField.setEnabled(false);
        }
        ImageButton imageButton = this.mBtnPasswordShow;
        if (imageButton != null) {
            imageButton.setEnabled(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void exitAlertMode() {
        Log.d("BSS_AuthCredentialPasswordView", "exitAlertMode");
        super.exitAlertMode();
        if (this.mPasswordField != null) {
            reactivatePasswordInputFieldAndShowKeyboard();
        }
        ImageButton imageButton = this.mBtnPasswordShow;
        if (imageButton != null) {
            imageButton.setEnabled(true);
        }
        Button button = this.mBtnContinue;
        if (button != null) {
            button.setEnabled(false);
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda1] */
    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("BSS_AuthCredentialPasswordView", "onAttachedToWindow");
        this.mPasswordField = (AuthCredentialEditText) findViewById(R.id.lockPassword);
        this.mBtnPasswordShow = (ImageButton) findViewById(R.id.btn_password_show);
        if (this.mPromptConfig.getCredentialType() == 1) {
            this.mPasswordField.setInputType(18);
            this.mPasswordField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            this.mBtnPasswordShow.setVisibility(8);
        }
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setUserId(getEffectiveUserId());
            this.mPasswordField.setOnEditorActionListener(this);
            this.mPasswordField.setOnKeyListener(this);
            this.mPasswordField.addTextChangedListener(this);
        }
        if (this.mPasswordField != null && this.mBtnPasswordShow != null) {
            setPasswordShown(this.mIsPasswordShown);
            this.mBtnPasswordShow.setContentDescription(((LinearLayout) this).mContext.getString(R.string.sec_lockpassword_show_button));
            this.mBtnPasswordShow.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AuthCredentialPasswordView authCredentialPasswordView = AuthCredentialPasswordView.this;
                    int selectionEnd = authCredentialPasswordView.mPasswordField.getSelectionEnd();
                    authCredentialPasswordView.setPasswordShown(!authCredentialPasswordView.mIsPasswordShown);
                    try {
                        authCredentialPasswordView.mPasswordField.setSelection(selectionEnd);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (this.mOnGlobalLayoutListener == null) {
            this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    AuthCredentialPasswordView.m40$r8$lambda$beTuhJ0h1i1MlsCeIBmDH4A90U(AuthCredentialPasswordView.this);
                }
            };
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onCancelButtonClicked() {
        this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onContinueButtonClicked() {
        checkPasswordAndUnlock();
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        super.onCredentialVerified(verifyCredentialResponse, i);
        Log.d("BSS_AuthCredentialPasswordView", "onCredentialVerified");
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
        this.mPasswordField.setEnabled(i <= 0);
        this.mBtnContinue.setEnabled(false);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mOnGlobalLayoutListener != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            this.mOnGlobalLayoutListener = null;
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (!z && !z2) {
            return false;
        }
        checkPasswordAndUnlock();
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        reactivatePasswordInputFieldAndShowKeyboard();
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onLockoutTimeoutStart() {
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setEnabled(false);
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z || this.mIsBiometricInfoModeOnCover) {
            return;
        }
        this.mPasswordField.requestFocus();
        this.mImm.showSoftInput(this.mPasswordField, 1);
    }

    protected void reactivatePasswordInputFieldAndShowKeyboard() {
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setEnabled(true);
            this.mPasswordField.requestFocus();
            this.mPasswordField.scheduleShowSoftInput();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void setPasswordShown(boolean z) {
        int i;
        int i2;
        if (this.mBtnPasswordShow == null || this.mPasswordField == null) {
            return;
        }
        this.mIsPasswordShown = z;
        if (Utils.isNightThemeOn(((LinearLayout) this).mContext)) {
            i = R.drawable.lock_password_btn_password_show_mtrl;
            i2 = R.drawable.lock_password_btn_password_hide_mtrl;
        } else {
            i = R.drawable.lock_password_btn_password_show_mtrl_light;
            i2 = R.drawable.lock_password_btn_password_hide_mtrl_light;
        }
        if (z) {
            this.mBtnPasswordShow.setImageResource(i);
            this.mPasswordField.setTransformationMethod((TransformationMethod) null);
            this.mBtnPasswordShow.setContentDescription(((LinearLayout) this).mContext.getString(R.string.sec_lockpassword_hide_button));
        } else {
            this.mBtnPasswordShow.setImageResource(i2);
            this.mPasswordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.mBtnPasswordShow.setContentDescription(((LinearLayout) this).mContext.getString(R.string.sec_lockpassword_show_button));
        }
        this.mBtnPasswordShow.setImageTintList(ColorStateList.valueOf(((LinearLayout) this).mContext.getResources().getColor(R.color.auth_credential_subtitle_text_color, null)));
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        if (promptConfig.getCredentialType() == 1) {
            this.mIsPinType = true;
            int effectiveUserId = getEffectiveUserId();
            this.mNdigitsPinEnabled = this.mLockPatternUtils.isAutoPinConfirmEnabled(effectiveUserId) ? this.mLockPatternUtils.getPinLength(effectiveUserId) : Settings.Secure.getIntForUser(((LinearLayout) this).mContext.getContentResolver(), "n_digits_pin_enabled", -1, effectiveUserId);
        }
    }

    protected void onKeyboardVisibilityChanged(boolean z) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
