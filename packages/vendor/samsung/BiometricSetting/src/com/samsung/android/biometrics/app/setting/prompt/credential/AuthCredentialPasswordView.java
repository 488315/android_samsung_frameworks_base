package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.content.res.ColorStateList;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class AuthCredentialPasswordView extends AuthCredentialView
        implements TextView.OnEditorActionListener, TextWatcher {
    public ImageButton mBtnPasswordShow;
    public final InputMethodManager mImm;
    public boolean mIsKeyboardVisible;
    public boolean mIsPinType;
    public KeyboardVisibilityObserver mKeyboardVisibilityObserver;
    public int mNdigitsPinEnabled;
    public AuthCredentialEditText mPasswordField;

    public AuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNdigitsPinEnabled = -1;
        this.mImm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
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

    public final void checkPasswordAndUnlock() {
        LockscreenCredential createPinOrNone =
                this.mPromptConfig.mCredentialType == 1
                        ? LockscreenCredential.createPinOrNone(this.mPasswordField.getText())
                        : LockscreenCredential.createPasswordOrNone(this.mPasswordField.getText());
        try {
            if (createPinOrNone.isNone()) {
                createPinOrNone.close();
            } else {
                this.mPendingLockCheck =
                        LockPatternChecker.verifyCredential(
                                this.mLockPatternUtils,
                                createPinOrNone,
                                getEffectiveUserId(),
                                1,
                                new LockPatternChecker
                                        .OnVerifyCallback() { // from class:
                                                              // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda2
                                    public final void onVerified(
                                            VerifyCredentialResponse verifyCredentialResponse,
                                            int i) {
                                        AuthCredentialPasswordView.this.onCredentialVerified(
                                                verifyCredentialResponse, i);
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

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void enterAlertMode(int i) {
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

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void exitAlertMode() {
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

    public int getDisplayHeight() {
        return Utils.getDisplayHeight(((LinearLayout) this).mContext);
    }

    public int getMaxScrollViewHeight() {
        Context context;
        float f;
        if (!isScreenLandscape()) {
            return this.mIsKeyboardVisible
                    ? this.mPromptConfig.mPromptInfo.isShowEmergencyCallButton()
                            ? Utils.getRatioHeight(((LinearLayout) this).mContext, 0.15f)
                            : Utils.getRatioHeight(((LinearLayout) this).mContext, 0.2f)
                    : Utils.getRatioHeight(((LinearLayout) this).mContext, 0.5f);
        }
        if (this.mIsKeyboardVisible) {
            context = ((LinearLayout) this).mContext;
            f = 0.4f;
        } else {
            context = ((LinearLayout) this).mContext;
            f = 0.7f;
        }
        return Utils.getRatioHeight(context, f);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda0] */
    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView,
              // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("BSS_AuthCredentialPasswordView", "onAttachedToWindow");
        this.mPasswordField = (AuthCredentialEditText) findViewById(R.id.lockPassword);
        this.mBtnPasswordShow = (ImageButton) findViewById(R.id.btn_password_show);
        this.mPasswordField.setFilters(new InputFilter[] {new InputFilter.LengthFilter(256)});
        if (this.mIsPinType) {
            this.mPasswordField.setInputType(18);
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
            this.mBtnPasswordShow.setContentDescription(
                    ((LinearLayout) this)
                            .mContext.getString(R.string.sec_lockpassword_show_button));
            this.mBtnPasswordShow.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AuthCredentialPasswordView authCredentialPasswordView =
                                    AuthCredentialPasswordView.this;
                            int selectionEnd =
                                    authCredentialPasswordView.mPasswordField.getSelectionEnd();
                            authCredentialPasswordView.setPasswordShown(
                                    !authCredentialPasswordView.mIsPasswordShown);
                            try {
                                authCredentialPasswordView.mPasswordField.setSelection(
                                        selectionEnd);
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        KeyboardVisibilityObserver keyboardVisibilityObserver =
                new KeyboardVisibilityObserver(
                        this,
                        new Supplier() { // from class:
                                         // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return Integer.valueOf(
                                        AuthCredentialPasswordView.this.getDisplayHeight());
                            }
                        });
        this.mKeyboardVisibilityObserver = keyboardVisibilityObserver;
        keyboardVisibilityObserver.mListener =
                new AuthCredentialPasswordView$$ExternalSyntheticLambda1(this);
        if (keyboardVisibilityObserver.mIsStarted) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(keyboardVisibilityObserver);
        keyboardVisibilityObserver.mIsStarted = true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onCancelButtonClicked() {
        this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onContinueButtonClicked() {
        checkPasswordAndUnlock();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onCredentialVerified(
            VerifyCredentialResponse verifyCredentialResponse, int i) {
        super.onCredentialVerified(verifyCredentialResponse, i);
        Log.d("BSS_AuthCredentialPasswordView", "onCredentialVerified");
        boolean z = false;
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
        this.mPasswordField.setEnabled(i <= 0);
        Button button = this.mBtnContinue;
        if (i <= 0 && this.mNdigitsPinEnabled <= 0) {
            z = true;
        }
        button.setEnabled(z);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView,
              // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KeyboardVisibilityObserver keyboardVisibilityObserver = this.mKeyboardVisibilityObserver;
        if (keyboardVisibilityObserver != null) {
            keyboardVisibilityObserver.mListener = null;
            if (keyboardVisibilityObserver.mIsStarted) {
                keyboardVisibilityObserver.mIsStarted = false;
                keyboardVisibilityObserver
                        .mView
                        .getViewTreeObserver()
                        .removeOnGlobalLayoutListener(keyboardVisibilityObserver);
            }
            this.mKeyboardVisibilityObserver = null;
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 =
                keyEvent != null
                        && KeyEvent.isConfirmKey(keyEvent.getKeyCode())
                        && keyEvent.getAction() == 0;
        if (!z && !z2) {
            return false;
        }
        checkPasswordAndUnlock();
        return true;
    }

    public void onKeyboardVisibilityChanged(boolean z) {
        Log.d("BSS_AuthCredentialPasswordView", "onKeyboardVisibilityChanged: isVisible = " + z);
        updateScrollView();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        reactivatePasswordInputFieldAndShowKeyboard();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onLockoutTimeoutStart() {
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

    public void reactivatePasswordInputFieldAndShowKeyboard() {
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setEnabled(true);
            this.mPasswordField.requestFocus();
            this.mPasswordField.scheduleShowSoftInput();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void setPasswordShown(boolean z) {
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
            this.mBtnPasswordShow.setContentDescription(
                    ((LinearLayout) this)
                            .mContext.getString(R.string.sec_lockpassword_hide_button));
        } else {
            this.mBtnPasswordShow.setImageResource(i2);
            this.mPasswordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.mBtnPasswordShow.setContentDescription(
                    ((LinearLayout) this)
                            .mContext.getString(R.string.sec_lockpassword_show_button));
        }
        this.mBtnPasswordShow.setImageTintList(
                ColorStateList.valueOf(
                        ((LinearLayout) this)
                                .mContext
                                .getResources()
                                .getColor(R.color.auth_credential_subtitle_text_color, null)));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        if (promptConfig.mCredentialType == 1) {
            this.mIsPinType = true;
            int effectiveUserId = getEffectiveUserId();
            this.mNdigitsPinEnabled =
                    this.mLockPatternUtils.isAutoPinConfirmEnabled(effectiveUserId)
                            ? this.mLockPatternUtils.getPinLength(effectiveUserId)
                            : Settings.Secure.getIntForUser(
                                    ((LinearLayout) this).mContext.getContentResolver(),
                                    "n_digits_pin_enabled",
                                    -1,
                                    effectiveUserId);
        }
    }

    public void updateScrollView() {
        ScrollView scrollView = getScrollView();
        if (scrollView == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
        layoutParams.matchConstraintMaxHeight = getMaxScrollViewHeight();
        layoutParams.matchConstraintMinHeight =
                Utils.getRatioHeight(((LinearLayout) this).mContext, 0.2f);
        scrollView.setLayoutParams(layoutParams);
        scrollView.requestLayout();
        updateScrollViewDivider();
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}
