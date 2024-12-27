package com.samsung.android.biometrics.app.setting.prompt.knox;

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
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class KnoxAuthCredentialView extends LinearLayout {
    public final AccessibilityManager mAccessibilityManager;
    public final AnonymousClass2 mClearErrorRunnable;
    public TextView mDescriptionView;
    public final DevicePolicyManager mDevicePolicyManager;
    public AnonymousClass3 mErrorTimer;
    public TextView mErrorView;
    public final Handler mHandler;
    public ImageView mIconView;
    public KnoxSysUiClientHelper mKnoxClientHelper;
    public final LockPatternUtils mLockPatternUtils;
    public final AnonymousClass1 mOnKeyListener;
    public AsyncTask mPendingLockCheck;
    public PromptConfig mPromptConfig;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public AnonymousClass2() {}

        @Override // java.lang.Runnable
        public final void run() {
            if (!KnoxAuthCredentialView.this.mPromptConfig.isKnoxProfile()) {
                KnoxAuthCredentialView.this.mErrorView.setText("");
            } else {
                KnoxAuthCredentialView knoxAuthCredentialView = KnoxAuthCredentialView.this;
                knoxAuthCredentialView.mKnoxClientHelper.setDetailText(
                        knoxAuthCredentialView.mErrorView);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView$1] */
    public KnoxAuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnKeyListener =
                new View
                        .OnKeyListener() { // from class:
                                           // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView.1
                    @Override // android.view.View.OnKeyListener
                    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                        BiometricPromptCallback biometricPromptCallback;
                        if (i != 4) {
                            return false;
                        }
                        if (keyEvent.getAction() == 1
                                && (biometricPromptCallback =
                                                KnoxAuthCredentialView.this.mPromptConfig.mCallback)
                                        != null) {
                            ((BiometricPromptClient) biometricPromptCallback).sendEvent(1003, 1);
                            ((BiometricPromptClient)
                                            KnoxAuthCredentialView.this.mPromptConfig.mCallback)
                                    .onUserCancel(2);
                        }
                        return true;
                    }
                };
        this.mClearErrorRunnable = new AnonymousClass2();
        this.mLockPatternUtils = new LockPatternUtils(((LinearLayout) this).mContext);
        this.mHandler = new Handler(context.getMainLooper());
        this.mAccessibilityManager =
                (AccessibilityManager)
                        ((LinearLayout) this).mContext.getSystemService(AccessibilityManager.class);
        this.mUserManager =
                (UserManager) ((LinearLayout) this).mContext.getSystemService(UserManager.class);
        this.mDevicePolicyManager =
                (DevicePolicyManager)
                        ((LinearLayout) this).mContext.getSystemService(DevicePolicyManager.class);
    }

    private int getUserTypeForWipe() {
        UserInfo userInfo =
                this.mUserManager.getUserInfo(
                        this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(
                                this.mPromptConfig.mUserId));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        return userInfo.isManagedProfile() ? 2 : 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        Drawable drawable;
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
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
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
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        if (this.mPromptConfig.mIsManagedProfile) {
            drawable =
                    getResources()
                            .getDrawable(
                                    R.drawable.auth_dialog_enterprise,
                                    ((LinearLayout) this).mContext.getTheme());
            int i = this.mPromptConfig.mOrganizationColor;
            if (i != 0) {
                drawable.setTint(i);
            }
        } else {
            drawable =
                    getResources()
                            .getDrawable(
                                    R.drawable.auth_dialog_lock,
                                    ((LinearLayout) this).mContext.getTheme());
        }
        this.mIconView.setImageDrawable(drawable);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onAttachedToWindow(
                    this,
                    this.mTitleView,
                    this.mSubtitleView,
                    this.mDescriptionView,
                    this.mIconView);
            this.mErrorView = this.mKnoxClientHelper.getDetailsTextView(this);
        }
    }

    @Override // android.view.View
    public abstract void onConfigurationChanged(Configuration configuration);

    /* JADX WARN: Type inference failed for: r9v0, types: [com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView$3] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView$3] */
    public void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        String errorMessage;
        Log.d("BSS_KnoxAuthCredentialView", "onCredentialVerified: " + i);
        if (verifyCredentialResponse.isMatched()) {
            this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mPromptConfig.mUserId);
            this.mLockPatternUtils.userPresent(this.mPromptConfig.mUserId);
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            PromptConfig promptConfig = this.mPromptConfig;
            ((BiometricPromptClient) this.mPromptConfig.mCallback)
                    .onDismissed(
                            7,
                            lockPatternUtils
                                    .verifyGatekeeperPasswordHandle(
                                            gatekeeperPasswordHandle,
                                            promptConfig.mOperationId,
                                            promptConfig.mUserId)
                                    .getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
            return;
        }
        if (i <= 0) {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.mUserId);
            if (updateErrorMessage(
                    this.mLockPatternUtils.getCurrentFailedPasswordAttempts(
                            this.mPromptConfig.mUserId))) {
                return;
            }
            if (!this.mPromptConfig.isKnoxProfile()
                    || (errorMessage = this.mKnoxClientHelper.getErrorMessage()) == null) {
                showError(getResources().getString(R.string.sec_lockpassword_need_to_unlock_wrong));
                return;
            } else {
                showError(errorMessage);
                return;
            }
        }
        this.mHandler.removeCallbacks(this.mClearErrorRunnable);
        long lockoutAttemptDeadline =
                this.mLockPatternUtils.setLockoutAttemptDeadline(this.mPromptConfig.mUserId, i);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mErrorTimer =
                    new CountDownTimer(
                            this,
                            ((LinearLayout) this).mContext,
                            lockoutAttemptDeadline - SystemClock.elapsedRealtime(),
                            this.mErrorView,
                            0) { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView.3
                        public final /* synthetic */ int $r8$classId;
                        public final Context mContext;
                        public final TextView mErrorView;
                        public final /* synthetic */ KnoxAuthCredentialView this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r5, 1000L);
                            this.$r8$classId = r8;
                            this.this$0 = this;
                            this.mErrorView = r7;
                            this.mContext = r4;
                        }

                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.onErrorTimeoutFinish();
                                    this.this$0.mClearErrorRunnable.run();
                                    break;
                                default:
                                    this.this$0.onErrorTimeoutFinish();
                                    this.this$0.mClearErrorRunnable.run();
                                    break;
                            }
                        }

                        @Override // android.os.CountDownTimer
                        public void onTick(long j) {
                            switch (this.$r8$classId) {
                                case 0:
                                    KnoxAuthCredentialView knoxAuthCredentialView = this.this$0;
                                    knoxAuthCredentialView.mKnoxClientHelper.setErrorTimerText(
                                            knoxAuthCredentialView.mErrorView, j);
                                    break;
                                default:
                                    onTick$com$samsung$android$biometrics$app$setting$prompt$knox$KnoxAuthCredentialView$ErrorTimer(
                                            j);
                                    break;
                            }
                        }

                        public final void
                                onTick$com$samsung$android$biometrics$app$setting$prompt$knox$KnoxAuthCredentialView$ErrorTimer(
                                        long j) {
                            int i2 = (int) (j / 1000);
                            int i3 = i2 / 60;
                            if (i2 > 60) {
                                int i4 = i3 + 1;
                                this.mErrorView.setText(
                                        this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals
                                                                .biometric_dialog_credential_too_many_attempts_min,
                                                        i4,
                                                        Integer.valueOf(i4)));
                            } else {
                                if (i2 > 60 || i2 <= 0) {
                                    return;
                                }
                                this.mErrorView.setText(
                                        this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals
                                                                .biometric_dialog_credential_too_many_attempts,
                                                        i2,
                                                        Integer.valueOf(i2)));
                            }
                        }
                    };
        } else {
            this.mErrorTimer =
                    new CountDownTimer(
                            this,
                            ((LinearLayout) this).mContext,
                            lockoutAttemptDeadline - SystemClock.elapsedRealtime(),
                            this.mErrorView,
                            1) { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView.3
                        public final /* synthetic */ int $r8$classId;
                        public final Context mContext;
                        public final TextView mErrorView;
                        public final /* synthetic */ KnoxAuthCredentialView this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(r5, 1000L);
                            this.$r8$classId = r8;
                            this.this$0 = this;
                            this.mErrorView = r7;
                            this.mContext = r4;
                        }

                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.onErrorTimeoutFinish();
                                    this.this$0.mClearErrorRunnable.run();
                                    break;
                                default:
                                    this.this$0.onErrorTimeoutFinish();
                                    this.this$0.mClearErrorRunnable.run();
                                    break;
                            }
                        }

                        @Override // android.os.CountDownTimer
                        public void onTick(long j) {
                            switch (this.$r8$classId) {
                                case 0:
                                    KnoxAuthCredentialView knoxAuthCredentialView = this.this$0;
                                    knoxAuthCredentialView.mKnoxClientHelper.setErrorTimerText(
                                            knoxAuthCredentialView.mErrorView, j);
                                    break;
                                default:
                                    onTick$com$samsung$android$biometrics$app$setting$prompt$knox$KnoxAuthCredentialView$ErrorTimer(
                                            j);
                                    break;
                            }
                        }

                        public final void
                                onTick$com$samsung$android$biometrics$app$setting$prompt$knox$KnoxAuthCredentialView$ErrorTimer(
                                        long j) {
                            int i2 = (int) (j / 1000);
                            int i3 = i2 / 60;
                            if (i2 > 60) {
                                int i4 = i3 + 1;
                                this.mErrorView.setText(
                                        this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals
                                                                .biometric_dialog_credential_too_many_attempts_min,
                                                        i4,
                                                        Integer.valueOf(i4)));
                            } else {
                                if (i2 > 60 || i2 <= 0) {
                                    return;
                                }
                                this.mErrorView.setText(
                                        this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals
                                                                .biometric_dialog_credential_too_many_attempts,
                                                        i2,
                                                        Integer.valueOf(i2)));
                            }
                        }
                    };
        }
        start();
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.mUserId);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.setBiometricAttemptDeadline(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnonymousClass3 anonymousClass3 = this.mErrorTimer;
        if (anonymousClass3 != null) {
            anonymousClass3.cancel();
        }
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onDetachedFromWindow();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mSubtitleView = (TextView) findViewById(R.id.subtitle);
        this.mDescriptionView = (TextView) findViewById(R.id.description);
        this.mIconView = (ImageView) findViewById(R.id.icon);
        this.mErrorView = (TextView) findViewById(R.id.error);
    }

    public void setKnoxClientHelper(KnoxSysUiClientHelper knoxSysUiClientHelper) {
        this.mKnoxClientHelper = knoxSysUiClientHelper;
    }

    public void setPromptConfig(PromptConfig promptConfig) {
        this.mPromptConfig = promptConfig;
    }

    public final void showError(String str) {
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

    public final boolean updateErrorMessage(int i) {
        int maximumFailedPasswordsForWipe =
                this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mPromptConfig.mUserId);
        if (maximumFailedPasswordsForWipe <= 0 || i <= 0) {
            return false;
        }
        if (this.mErrorView == null) {
            return true;
        }
        showError(
                getResources()
                        .getString(
                                R.string.biometric_dialog_credential_attempts_before_wipe,
                                Integer.valueOf(i),
                                Integer.valueOf(maximumFailedPasswordsForWipe)));
        return true;
    }

    public void onErrorTimeoutFinish() {}
}
