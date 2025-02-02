package com.samsung.android.biometrics.app.setting.knox;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public abstract class KnoxAuthCredentialView extends LinearLayout {
    private final AccessibilityManager mAccessibilityManager;
    protected final Runnable mClearErrorRunnable;
    private TextView mDescriptionView;
    private final DevicePolicyManager mDevicePolicyManager;
    protected ErrorTimer mErrorTimer;
    protected TextView mErrorView;
    protected final Handler mHandler;
    private ImageView mIconView;
    protected KnoxSysUiClientHelper mKnoxClientHelper;
    protected final LockPatternUtils mLockPatternUtils;
    protected View.OnKeyListener mOnKeyListener;
    protected AsyncTask<?, ?, ?> mPendingLockCheck;
    protected PromptConfig mPromptConfig;
    private TextView mSubtitleView;
    private TextView mTitleView;
    private final UserManager mUserManager;

    /* renamed from: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView$2 */
    final class RunnableC02742 implements Runnable {
        RunnableC02742() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (!KnoxAuthCredentialView.this.mPromptConfig.isKnoxProfile()) {
                KnoxAuthCredentialView.this.mErrorView.setText("");
            } else {
                KnoxAuthCredentialView knoxAuthCredentialView = KnoxAuthCredentialView.this;
                knoxAuthCredentialView.mKnoxClientHelper.setDetailText(knoxAuthCredentialView.mErrorView);
            }
        }
    }

    protected static class ErrorTimer extends CountDownTimer {
        private final Context mContext;
        private final TextView mErrorView;

        public ErrorTimer(Context context, long j, TextView textView) {
            super(j, 1000L);
            this.mErrorView = textView;
            this.mContext = context;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            int i = (int) (j / 1000);
            int i2 = i / 60;
            if (i > 60) {
                int i3 = i2 + 1;
                this.mErrorView.setText(this.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts_min, i3, Integer.valueOf(i3)));
            } else {
                if (i > 60 || i <= 0) {
                    return;
                }
                this.mErrorView.setText(this.mContext.getResources().getQuantityString(R.plurals.biometric_dialog_credential_too_many_attempts, i, Integer.valueOf(i)));
            }
        }
    }

    public KnoxAuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView.1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                if (keyEvent.getAction() == 1 && KnoxAuthCredentialView.this.mPromptConfig.getCallback() != null) {
                    KnoxAuthCredentialView.this.mPromptConfig.getCallback().onSystemEvent();
                    KnoxAuthCredentialView.this.mPromptConfig.getCallback().onUserCancel(2);
                }
                return true;
            }
        };
        this.mClearErrorRunnable = new RunnableC02742();
        this.mLockPatternUtils = new LockPatternUtils(((LinearLayout) this).mContext);
        this.mHandler = new Handler(context.getMainLooper());
        this.mAccessibilityManager = (AccessibilityManager) ((LinearLayout) this).mContext.getSystemService(AccessibilityManager.class);
        this.mUserManager = (UserManager) ((LinearLayout) this).mContext.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) ((LinearLayout) this).mContext.getSystemService(DevicePolicyManager.class);
    }

    private int getUserTypeForWipe() {
        UserInfo userInfo = this.mUserManager.getUserInfo(this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(this.mPromptConfig.getUserId()));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        return userInfo.isManagedProfile() ? 2 : 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        Drawable drawable;
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
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
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
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        if (this.mPromptConfig.isManagedProfile()) {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_enterprise, ((LinearLayout) this).mContext.getTheme());
            int organizationColor = this.mPromptConfig.getOrganizationColor();
            if (organizationColor != 0) {
                drawable.setTint(organizationColor);
            }
        } else {
            drawable = getResources().getDrawable(R.drawable.auth_dialog_lock, ((LinearLayout) this).mContext.getTheme());
        }
        this.mIconView.setImageDrawable(drawable);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onAttachedToWindow(this, this.mTitleView, this.mSubtitleView, this.mDescriptionView, this.mIconView);
            this.mErrorView = this.mKnoxClientHelper.getDetailsTextView(this);
        }
    }

    @Override // android.view.View
    public abstract void onConfigurationChanged(Configuration configuration);

    protected void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        String errorMessage;
        Log.d("BSS_KnoxAuthCredentialView", "onCredentialVerified: " + i);
        if (verifyCredentialResponse.isMatched()) {
            this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mPromptConfig.getUserId());
            this.mLockPatternUtils.userPresent(this.mPromptConfig.getUserId());
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            this.mPromptConfig.getCallback().onDismissed(7, this.mLockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.mPromptConfig.getOperationId(), this.mPromptConfig.getUserId()).getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
            return;
        }
        if (i <= 0) {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.getUserId());
            if (updateErrorMessage(this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mPromptConfig.getUserId()))) {
                return;
            }
            if (!this.mPromptConfig.isKnoxProfile() || (errorMessage = this.mKnoxClientHelper.getErrorMessage()) == null) {
                showError(getResources().getString(R.string.sec_lockpassword_need_to_unlock_wrong));
                return;
            } else {
                showError(errorMessage);
                return;
            }
        }
        this.mHandler.removeCallbacks(this.mClearErrorRunnable);
        long lockoutAttemptDeadline = this.mLockPatternUtils.setLockoutAttemptDeadline(this.mPromptConfig.getUserId(), i);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mErrorTimer = new ErrorTimer(((LinearLayout) this).mContext, lockoutAttemptDeadline - SystemClock.elapsedRealtime(), this.mErrorView) { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView.3
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    KnoxAuthCredentialView.this.onErrorTimeoutFinish();
                    ((RunnableC02742) KnoxAuthCredentialView.this.mClearErrorRunnable).run();
                }

                @Override // com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView.ErrorTimer, android.os.CountDownTimer
                public final void onTick(long j) {
                    KnoxAuthCredentialView knoxAuthCredentialView = KnoxAuthCredentialView.this;
                    knoxAuthCredentialView.mKnoxClientHelper.setErrorTimerText(knoxAuthCredentialView.mErrorView, j);
                }
            };
        } else {
            this.mErrorTimer = new ErrorTimer(((LinearLayout) this).mContext, lockoutAttemptDeadline - SystemClock.elapsedRealtime(), this.mErrorView) { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialView.4
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    KnoxAuthCredentialView.this.onErrorTimeoutFinish();
                    ((RunnableC02742) KnoxAuthCredentialView.this.mClearErrorRunnable).run();
                }
            };
        }
        this.mErrorTimer.start();
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.getUserId());
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.setBiometricAttemptDeadline(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ErrorTimer errorTimer = this.mErrorTimer;
        if (errorTimer != null) {
            errorTimer.cancel();
        }
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onDetachedFromWindow();
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mSubtitleView = (TextView) findViewById(R.id.subtitle);
        this.mDescriptionView = (TextView) findViewById(R.id.description);
        this.mIconView = (ImageView) findViewById(R.id.icon);
        this.mErrorView = (TextView) findViewById(R.id.error);
    }

    void setKnoxClientHelper(KnoxSysUiClientHelper knoxSysUiClientHelper) {
        this.mKnoxClientHelper = knoxSysUiClientHelper;
    }

    void setPromptConfig(PromptConfig promptConfig) {
        this.mPromptConfig = promptConfig;
    }

    protected final void showError(String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mClearErrorRunnable);
            this.mHandler.postDelayed(this.mClearErrorRunnable, 3000L);
        }
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    protected final boolean updateErrorMessage(int i) {
        int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mPromptConfig.getUserId());
        if (maximumFailedPasswordsForWipe <= 0 || i <= 0) {
            return false;
        }
        if (this.mErrorView == null) {
            return true;
        }
        showError(getResources().getString(R.string.biometric_dialog_credential_attempts_before_wipe, Integer.valueOf(i), Integer.valueOf(maximumFailedPasswordsForWipe)));
        return true;
    }

    protected void onErrorTimeoutFinish() {
    }
}
