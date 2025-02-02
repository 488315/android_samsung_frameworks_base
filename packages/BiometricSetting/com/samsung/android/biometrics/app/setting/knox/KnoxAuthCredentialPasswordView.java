package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public class KnoxAuthCredentialPasswordView extends KnoxAuthCredentialView implements TextView.OnEditorActionListener, TextWatcher {
    private final InputMethodManager mImm;
    private int mNdigitsPinEnabled;
    private EditText mPasswordField;

    public static /* synthetic */ void $r8$lambda$5G8XFY8VkX7pnLson2S1KvQI6bo(KnoxAuthCredentialPasswordView knoxAuthCredentialPasswordView) {
        knoxAuthCredentialPasswordView.mPasswordField.requestFocus();
        knoxAuthCredentialPasswordView.mImm.showSoftInput(knoxAuthCredentialPasswordView.mPasswordField, 1);
    }

    public KnoxAuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNdigitsPinEnabled = -1;
        this.mImm = (InputMethodManager) ((LinearLayout) this).mContext.getSystemService(InputMethodManager.class);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        if (this.mNdigitsPinEnabled <= 0 || editable.length() != this.mNdigitsPinEnabled) {
            return;
        }
        this.mPasswordField.onEditorAction(6);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView, android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (Utils.isScreenLandscape(((LinearLayout) this).mContext)) {
            imageView.setVisibility(8);
            setOrientation(0);
        } else {
            imageView.setVisibility(0);
            setOrientation(1);
        }
        if (this.mPromptConfig.getCredentialType() == 1) {
            this.mPasswordField.setInputType(18);
            this.mPasswordField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(256)});
        }
        postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialPasswordView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KnoxAuthCredentialPasswordView.$r8$lambda$5G8XFY8VkX7pnLson2S1KvQI6bo(KnoxAuthCredentialPasswordView.this);
            }
        }, 100L);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mPromptConfig.isKnoxProfile()) {
            return;
        }
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (configuration.orientation == 2) {
            imageView.setVisibility(8);
            setOrientation(0);
        } else {
            imageView.setVisibility(0);
            setOrientation(1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView
    protected final void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        super.onCredentialVerified(verifyCredentialResponse, i);
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onCredentialVerified(this.mPromptConfig.getCredentialType(), verifyCredentialResponse.isMatched(), this.mPasswordField, i, this.mErrorView);
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (!z && !z2) {
            return false;
        }
        LockscreenCredential createPinOrNone = this.mPromptConfig.getCredentialType() == 1 ? LockscreenCredential.createPinOrNone(this.mPasswordField.getText()) : LockscreenCredential.createPasswordOrNone(this.mPasswordField.getText());
        try {
            if (!createPinOrNone.isNone()) {
                this.mPendingLockCheck = LockPatternChecker.verifyCredential(this.mLockPatternUtils, createPinOrNone, this.mPromptConfig.getUserId(), 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialPasswordView$$ExternalSyntheticLambda0
                    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i2) {
                        KnoxAuthCredentialPasswordView.this.onCredentialVerified(verifyCredentialResponse, i2);
                    }
                });
            }
            createPinOrNone.close();
            return true;
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

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView
    protected final void onErrorTimeoutFinish() {
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onErrorTimeoutFinish(this, this.mPromptConfig.getCredentialType(), this.mPasswordField);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView, android.view.View
    protected final void onFinishInflate() {
        super.onFinishInflate();
        EditText editText = (EditText) findViewById(R.id.lockPassword);
        this.mPasswordField = editText;
        editText.setOnEditorActionListener(this);
        this.mPasswordField.setOnKeyListener(this.mOnKeyListener);
        this.mPasswordField.addTextChangedListener(this);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView
    void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        if (promptConfig.getCredentialType() == 1) {
            int userId = promptConfig.getUserId();
            this.mNdigitsPinEnabled = this.mLockPatternUtils.isAutoPinConfirmEnabled(userId) ? this.mLockPatternUtils.getPinLength(userId) : Settings.Secure.getIntForUser(((LinearLayout) this).mContext.getContentResolver(), "n_digits_pin_enabled", -1, this.mPromptConfig.getUserId());
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
