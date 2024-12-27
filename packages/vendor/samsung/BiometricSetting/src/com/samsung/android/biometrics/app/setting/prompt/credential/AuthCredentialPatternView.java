package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

import java.util.List;

public class AuthCredentialPatternView extends AuthCredentialView
        implements LockPatternView.OnPatternListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mClearPatternRunnable;
    public FrameLayout mLockPatternLayout;
    public LockPatternView mLockPatternView;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView$1] */
    public AuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClearPatternRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LockPatternView lockPatternView =
                                AuthCredentialPatternView.this.mLockPatternView;
                        if (lockPatternView != null) {
                            lockPatternView.clearPattern();
                        }
                    }
                };
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void enterAlertMode(int i) {
        Log.d("BSS_AuthCredentialPatternView", "enterAlertMode: " + i);
        super.enterAlertMode(i);
        this.mLockPatternView.setEnabled(false);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void exitAlertMode() {
        Log.d("BSS_AuthCredentialPatternView", "exitAlertMode");
        super.exitAlertMode();
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
        disableButton(this.mBtnCancel);
        disableButton(this.mBtnContinue);
    }

    public void hidePattern() {
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setVisibility(4);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView,
              // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLockPatternView = findViewById(R.id.lockPattern);
        this.mLockPatternLayout = (FrameLayout) findViewById(R.id.layout_lockPattern);
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setOnPatternListener(this);
            this.mLockPatternView.setFadePattern(true);
            this.mLockPatternView.setInStealthMode(
                    true ^ this.mLockPatternUtils.isVisiblePatternEnabled(getEffectiveUserId()));
            this.mLockPatternView.setColors(
                    ((LinearLayout) this)
                            .mContext.getColor(R.color.biometric_prompt_credential_pattern_color),
                    ((LinearLayout) this)
                            .mContext.getColor(R.color.auth_credential_pattern_draw_regular_color),
                    ((LinearLayout) this)
                            .mContext.getColor(R.color.auth_credential_pattern_draw_error_color));
        }
        updateScrollView();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView,
              // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("BSS_AuthCredentialPatternView", "onConfigurationChanged");
        resizePattern();
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.clearPattern();
        }
        updateScrollView();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        this.mLockPatternView.setVisibility(0);
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void onLockoutTimeoutStart() {
        hidePattern();
    }

    public final void onPatternCleared() {
        this.mLockPatternView.removeCallbacks(this.mClearPatternRunnable);
    }

    public final void onPatternDetected(List list) {
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        if (list != null) {
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            try {
                this.mPendingLockCheck =
                        LockPatternChecker.verifyCredential(
                                this.mLockPatternUtils,
                                createPattern,
                                getEffectiveUserId(),
                                1,
                                new LockPatternChecker
                                        .OnVerifyCallback() { // from class:
                                                              // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView$$ExternalSyntheticLambda0
                                    public final void onVerified(
                                            VerifyCredentialResponse verifyCredentialResponse,
                                            int i) {
                                        AuthCredentialPatternView authCredentialPatternView =
                                                AuthCredentialPatternView.this;
                                        int i2 = AuthCredentialPatternView.$r8$clinit;
                                        authCredentialPatternView.onCredentialVerified(
                                                verifyCredentialResponse, i);
                                        Log.d("BSS_AuthCredentialPatternView", "onPatternVerified");
                                        if (!verifyCredentialResponse.isMatched()) {
                                            authCredentialPatternView.mLockPatternView
                                                    .setDisplayMode(
                                                            LockPatternView.DisplayMode.Wrong);
                                            authCredentialPatternView.mLockPatternView
                                                    .removeCallbacks(
                                                            authCredentialPatternView
                                                                    .mClearPatternRunnable);
                                            authCredentialPatternView.mLockPatternView.postDelayed(
                                                    authCredentialPatternView.mClearPatternRunnable,
                                                    2000L);
                                        }
                                        if (i <= 0) {
                                            authCredentialPatternView.mLockPatternView.setEnabled(
                                                    true);
                                        } else {
                                            authCredentialPatternView.mLockPatternView.setEnabled(
                                                    false);
                                            authCredentialPatternView.hidePattern();
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
    }

    public final void onPatternStart() {
        this.mLockPatternView.removeCallbacks(this.mClearPatternRunnable);
    }

    public void resizePattern() {
        FrameLayout frameLayout = this.mLockPatternLayout;
        if (frameLayout != null) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
            if (isScreenLandscape()) {
                layoutParams.height = 0;
                layoutParams.weight = 1.0f;
            } else {
                layoutParams.height = -2;
            }
            this.mLockPatternLayout.setLayoutParams(layoutParams);
        }
    }

    public void setPatternViewVisibility(int i) {
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView != null) {
            lockPatternView.setVisibility(i);
        }
    }

    public final void updateScrollView() {
        Context context;
        double d;
        ScrollView scrollView = getScrollView();
        if (scrollView == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
        if (isScreenLandscape()) {
            context = ((LinearLayout) this).mContext;
            d = 500.0d;
        } else {
            context = ((LinearLayout) this).mContext;
            d = 250.0d;
        }
        layoutParams.matchConstraintMaxHeight = Utils.dipToPixel(context, d);
        scrollView.setLayoutParams(layoutParams);
        scrollView.requestLayout();
        updateScrollViewDivider();
    }

    public final void onPatternCellAdded(List list) {}
}
