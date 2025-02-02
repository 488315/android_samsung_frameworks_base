package com.samsung.android.biometrics.app.setting.credential;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import java.util.List;

/* loaded from: classes.dex */
public class AuthCredentialPatternView extends AuthCredentialView implements LockPatternView.OnPatternListener {
    private final Runnable mClearPatternRunnable;
    private FrameLayout mLockPatternLayout;
    private LockPatternView mLockPatternView;

    public static void $r8$lambda$EseEYwFd0Hhgn6RMuZUgot5xJDI(AuthCredentialPatternView authCredentialPatternView, VerifyCredentialResponse verifyCredentialResponse, int i) {
        authCredentialPatternView.onCredentialVerified(verifyCredentialResponse, i);
        Log.d("BSS_AuthCredentialPatternView", "onPatternVerified");
        if (!verifyCredentialResponse.isMatched()) {
            authCredentialPatternView.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            authCredentialPatternView.mLockPatternView.removeCallbacks(authCredentialPatternView.mClearPatternRunnable);
            authCredentialPatternView.mLockPatternView.postDelayed(authCredentialPatternView.mClearPatternRunnable, 2000L);
        }
        if (i <= 0) {
            authCredentialPatternView.mLockPatternView.setEnabled(true);
        } else {
            authCredentialPatternView.mLockPatternView.setEnabled(false);
            authCredentialPatternView.hidePattern();
        }
    }

    public AuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClearPatternRunnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView.1
            @Override // java.lang.Runnable
            public final void run() {
                if (AuthCredentialPatternView.this.mLockPatternView != null) {
                    AuthCredentialPatternView.this.mLockPatternView.clearPattern();
                }
            }
        };
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void enterAlertMode(int i) {
        Log.d("BSS_AuthCredentialPatternView", "enterAlertMode: " + i);
        super.enterAlertMode(i);
        this.mLockPatternView.setEnabled(false);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void exitAlertMode() {
        Log.d("BSS_AuthCredentialPatternView", "exitAlertMode");
        super.exitAlertMode();
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
        disableButton(this.mBtnCancel);
        disableButton(this.mBtnContinue);
    }

    protected void hidePattern() {
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setVisibility(4);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLockPatternView = findViewById(R.id.lockPattern);
        this.mLockPatternLayout = (FrameLayout) findViewById(R.id.layout_lockPattern);
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setOnPatternListener(this);
            this.mLockPatternView.setFadePattern(true);
            this.mLockPatternView.setInStealthMode(true ^ this.mLockPatternUtils.isVisiblePatternEnabled(getEffectiveUserId()));
            this.mLockPatternView.setColors(((LinearLayout) this).mContext.getColor(R.color.biometric_prompt_credential_pattern_color), ((LinearLayout) this).mContext.getColor(R.color.auth_credential_pattern_draw_regular_color), ((LinearLayout) this).mContext.getColor(R.color.auth_credential_pattern_draw_error_color));
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("BSS_AuthCredentialPatternView", "onConfigurationChanged");
        if (this.mLockPatternLayout != null && !Utils.isApplyingTabletGUI(((LinearLayout) this).mContext)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLockPatternLayout.getLayoutParams();
            if (isScreenLandscape()) {
                layoutParams.height = 0;
                layoutParams.weight = 1.0f;
            } else {
                layoutParams.height = -2;
            }
            this.mLockPatternLayout.setLayoutParams(layoutParams);
        }
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.clearPattern();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        this.mLockPatternView.setVisibility(0);
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected void onLockoutTimeoutStart() {
        hidePattern();
    }

    public final void onPatternCleared() {
        this.mLockPatternView.removeCallbacks(this.mClearPatternRunnable);
    }

    public final void onPatternDetected(List<LockPatternView.Cell> list) {
        AsyncTask<?, ?, ?> asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        if (list != null) {
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            try {
                this.mPendingLockCheck = LockPatternChecker.verifyCredential(this.mLockPatternUtils, createPattern, getEffectiveUserId(), 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView$$ExternalSyntheticLambda0
                    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
                        AuthCredentialPatternView.$r8$lambda$EseEYwFd0Hhgn6RMuZUgot5xJDI(AuthCredentialPatternView.this, verifyCredentialResponse, i);
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
    }

    public final void onPatternStart() {
        this.mLockPatternView.removeCallbacks(this.mClearPatternRunnable);
    }

    protected void setPatternViewVisibility(int i) {
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setVisibility(i);
        }
    }

    public final void onPatternCellAdded(List<LockPatternView.Cell> list) {
    }
}
