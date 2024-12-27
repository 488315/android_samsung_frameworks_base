package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

import java.util.List;

public class KnoxAuthCredentialPatternView extends KnoxAuthCredentialView {
    public LockPatternView mLockPatternView;

    public KnoxAuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView,
              // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        LockPatternView findViewById = findViewById(R.id.lockPattern);
        this.mLockPatternView = findViewById;
        findViewById.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setInStealthMode(
                !this.mLockPatternUtils.isVisiblePatternEnabled(this.mPromptConfig.mUserId));
        if (Utils.isScreenLandscape(((LinearLayout) this).mContext)) {
            setOrientation(0);
        } else {
            setOrientation(1);
        }
        this.mLockPatternView.setColors(
                ((LinearLayout) this)
                        .mContext.getColor(R.color.biometric_prompt_credential_pattern_color),
                -12303292,
                -65536);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onAttachedPatternViewToWindow(this.mLockPatternView);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView,
              // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            setOrientation(0);
        } else {
            setOrientation(1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView
    public final void onErrorTimeoutFinish() {
        this.mLockPatternView.setEnabled(true);
        if (this.mPromptConfig.isKnoxProfile()) {
            this.mKnoxClientHelper.onErrorTimeoutFinish(
                    this, this.mPromptConfig.mCredentialType, this.mLockPatternView);
        }
    }

    public final class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public UnlockPatternListener() {}

        public final void onPatternDetected(List list) {
            String errorMessage;
            AsyncTask asyncTask = KnoxAuthCredentialPatternView.this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            if (list.size() < 4) {
                KnoxAuthCredentialPatternView knoxAuthCredentialPatternView =
                        KnoxAuthCredentialPatternView.this;
                if (knoxAuthCredentialPatternView.updateErrorMessage(
                        knoxAuthCredentialPatternView.mLockPatternUtils
                                .getCurrentFailedPasswordAttempts(
                                        knoxAuthCredentialPatternView.mPromptConfig.mUserId))) {
                    return;
                }
                if (KnoxAuthCredentialPatternView.this.mPromptConfig.isKnoxProfile()
                        && (errorMessage =
                                        KnoxAuthCredentialPatternView.this.mKnoxClientHelper
                                                .getErrorMessage())
                                != null) {
                    KnoxAuthCredentialPatternView.this.showError(errorMessage);
                    return;
                } else {
                    KnoxAuthCredentialPatternView knoxAuthCredentialPatternView2 =
                            KnoxAuthCredentialPatternView.this;
                    knoxAuthCredentialPatternView2.showError(
                            knoxAuthCredentialPatternView2
                                    .getResources()
                                    .getString(R.string.sec_lockpassword_need_to_unlock_wrong));
                    return;
                }
            }
            KnoxAuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            try {
                KnoxAuthCredentialPatternView knoxAuthCredentialPatternView3 =
                        KnoxAuthCredentialPatternView.this;
                knoxAuthCredentialPatternView3.mPendingLockCheck =
                        LockPatternChecker.verifyCredential(
                                knoxAuthCredentialPatternView3.mLockPatternUtils,
                                createPattern,
                                knoxAuthCredentialPatternView3.mPromptConfig.mUserId,
                                1,
                                new LockPatternChecker
                                        .OnVerifyCallback() { // from class:
                                                              // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialPatternView$UnlockPatternListener$$ExternalSyntheticLambda0
                                    public final void onVerified(
                                            VerifyCredentialResponse verifyCredentialResponse,
                                            int i) {
                                        KnoxAuthCredentialPatternView.UnlockPatternListener
                                                unlockPatternListener =
                                                        KnoxAuthCredentialPatternView
                                                                .UnlockPatternListener.this;
                                        KnoxAuthCredentialPatternView.this.onCredentialVerified(
                                                verifyCredentialResponse, i);
                                        if (i > 0) {
                                            KnoxAuthCredentialPatternView.this.mLockPatternView
                                                    .setEnabled(false);
                                        } else {
                                            KnoxAuthCredentialPatternView.this.mLockPatternView
                                                    .setEnabled(true);
                                        }
                                        if (KnoxAuthCredentialPatternView.this.mPromptConfig
                                                .isKnoxProfile()) {
                                            KnoxAuthCredentialPatternView
                                                    knoxAuthCredentialPatternView4 =
                                                            KnoxAuthCredentialPatternView.this;
                                            KnoxSysUiClientHelper knoxSysUiClientHelper =
                                                    knoxAuthCredentialPatternView4
                                                            .mKnoxClientHelper;
                                            int i2 =
                                                    knoxAuthCredentialPatternView4
                                                            .mPromptConfig
                                                            .mCredentialType;
                                            boolean isMatched =
                                                    verifyCredentialResponse.isMatched();
                                            KnoxAuthCredentialPatternView
                                                    knoxAuthCredentialPatternView5 =
                                                            KnoxAuthCredentialPatternView.this;
                                            knoxSysUiClientHelper.onCredentialVerified(
                                                    i2,
                                                    isMatched,
                                                    knoxAuthCredentialPatternView5.mLockPatternView,
                                                    i,
                                                    knoxAuthCredentialPatternView5.mErrorView);
                                        }
                                    }
                                });
                if (createPattern != null) {
                    createPattern.close();
                }
            } catch (Throwable th) {
                if (createPattern != null) {
                    try {
                        createPattern.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void onPatternCleared() {}

        public final void onPatternStart() {}

        public final void onPatternCellAdded(List list) {}
    }
}
