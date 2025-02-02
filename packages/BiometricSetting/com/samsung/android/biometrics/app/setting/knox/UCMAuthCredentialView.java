package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public abstract class UCMAuthCredentialView extends LinearLayout {
    public TextView mDescriptionView;
    protected TextView mErrorView;
    private ImageView mIconView;
    protected final LockPatternUtils mLockPatternUtils;
    protected View.OnKeyListener mOnKeyListener;
    protected AsyncTask<?, ?, ?> mPendingLockCheck;
    protected PromptConfig mPromptConfig;
    public TextView mSubtitleView;
    private TextView mTitleView;
    private TextView mUCMtitleView;

    public UCMAuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialView.1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                if (keyEvent.getAction() == 1 && UCMAuthCredentialView.this.mPromptConfig.getCallback() != null) {
                    UCMAuthCredentialView.this.mPromptConfig.getCallback().onSystemEvent();
                    UCMAuthCredentialView.this.mPromptConfig.getCallback().onUserCancel(2);
                }
                return true;
            }
        };
        this.mLockPatternUtils = new LockPatternUtils(((LinearLayout) this).mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextView textView = this.mTitleView;
        PromptInfo promptInfo = this.mPromptConfig.getPromptInfo();
        CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
        if (deviceCredentialTitle == null) {
            deviceCredentialTitle = promptInfo.getTitle();
        }
        textView.setText(deviceCredentialTitle);
        TextView textView2 = this.mSubtitleView;
        PromptInfo promptInfo2 = this.mPromptConfig.getPromptInfo();
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
        PromptInfo promptInfo3 = this.mPromptConfig.getPromptInfo();
        CharSequence deviceCredentialDescription = promptInfo3.getDeviceCredentialDescription();
        if (deviceCredentialDescription == null) {
            deviceCredentialDescription = promptInfo3.getDescription();
        }
        if (TextUtils.isEmpty(deviceCredentialDescription)) {
            textView3.setVisibility(8);
        } else {
            textView3.setText(deviceCredentialDescription);
        }
        this.mUCMtitleView.setText(UCMUtils.getUCMKeyguardVendorName());
        this.mIconView.setImageDrawable(getResources().getDrawable(R.drawable.auth_dialog_lock, ((LinearLayout) this).mContext.getTheme()));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mSubtitleView = (TextView) findViewById(R.id.subtitle);
        this.mUCMtitleView = (TextView) findViewById(R.id.ucmTitleText);
        this.mDescriptionView = (TextView) findViewById(R.id.description);
        this.mIconView = (ImageView) findViewById(R.id.icon);
        this.mErrorView = (TextView) findViewById(R.id.error);
    }

    void setPromptConfig(PromptConfig promptConfig) {
        this.mPromptConfig = promptConfig;
    }

    void setKnoxClientHelper(KnoxSysUiClientHelper knoxSysUiClientHelper) {
    }
}
