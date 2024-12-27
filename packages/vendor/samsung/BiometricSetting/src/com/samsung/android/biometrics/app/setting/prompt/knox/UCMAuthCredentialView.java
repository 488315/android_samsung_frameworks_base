package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public abstract class UCMAuthCredentialView extends LinearLayout {
    public TextView mDescriptionView;
    public TextView mErrorView;
    public ImageView mIconView;
    public final LockPatternUtils mLockPatternUtils;
    public final AnonymousClass1 mOnKeyListener;
    public AsyncTask mPendingLockCheck;
    public PromptConfig mPromptConfig;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public TextView mUCMtitleView;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialView$1] */
    public UCMAuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnKeyListener =
                new View
                        .OnKeyListener() { // from class:
                                           // com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialView.1
                    @Override // android.view.View.OnKeyListener
                    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                        BiometricPromptCallback biometricPromptCallback;
                        if (i != 4) {
                            return false;
                        }
                        if (keyEvent.getAction() == 1
                                && (biometricPromptCallback =
                                                UCMAuthCredentialView.this.mPromptConfig.mCallback)
                                        != null) {
                            ((BiometricPromptClient) biometricPromptCallback).sendEvent(1003, 1);
                            ((BiometricPromptClient)
                                            UCMAuthCredentialView.this.mPromptConfig.mCallback)
                                    .onUserCancel(2);
                        }
                        return true;
                    }
                };
        this.mLockPatternUtils = new LockPatternUtils(((LinearLayout) this).mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextView textView = this.mTitleView;
        PromptInfo promptInfo = this.mPromptConfig.mPromptInfo;
        CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
        if (deviceCredentialTitle == null) {
            deviceCredentialTitle = promptInfo.getTitle();
        }
        textView.setText(deviceCredentialTitle);
        TextView textView2 = this.mSubtitleView;
        PromptInfo promptInfo2 = this.mPromptConfig.mPromptInfo;
        CharSequence deviceCredentialSubtitle = promptInfo2.getDeviceCredentialSubtitle();
        if (deviceCredentialSubtitle == null) {
            deviceCredentialSubtitle = promptInfo2.getSubtitle();
        }
        if (TextUtils.isEmpty(deviceCredentialSubtitle)) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(deviceCredentialSubtitle);
        }
        TextView textView3 = this.mDescriptionView;
        PromptInfo promptInfo3 = this.mPromptConfig.mPromptInfo;
        CharSequence deviceCredentialDescription = promptInfo3.getDeviceCredentialDescription();
        if (deviceCredentialDescription == null) {
            deviceCredentialDescription = promptInfo3.getDescription();
        }
        if (TextUtils.isEmpty(deviceCredentialDescription)) {
            textView3.setVisibility(8);
        } else {
            textView3.setText(deviceCredentialDescription);
        }
        TextView textView4 = this.mUCMtitleView;
        boolean z = UCMUtils.DBG;
        textView4.setText(UCMUtils.getUCMKeyguardVendorName(UserHandle.myUserId()));
        this.mIconView.setImageDrawable(
                getResources()
                        .getDrawable(
                                R.drawable.auth_dialog_lock,
                                ((LinearLayout) this).mContext.getTheme()));
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mSubtitleView = (TextView) findViewById(R.id.subtitle);
        this.mUCMtitleView = (TextView) findViewById(R.id.ucmTitleText);
        this.mDescriptionView = (TextView) findViewById(R.id.description);
        this.mIconView = (ImageView) findViewById(R.id.icon);
        this.mErrorView = (TextView) findViewById(R.id.error);
    }

    public void setPromptConfig(PromptConfig promptConfig) {
        this.mPromptConfig = promptConfig;
    }

    public void setKnoxClientHelper(KnoxSysUiClientHelper knoxSysUiClientHelper) {}
}
