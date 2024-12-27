package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.widget.LockPatternView;

public interface KnoxSysUiClientHelper {
    KnoxAuthCredentialView changeCredentialViewIfNeeded(View view);

    TextView getDetailsTextView(KnoxAuthCredentialView knoxAuthCredentialView);

    String getErrorMessage();

    boolean isForgotbtnDialogShowing();

    void modifyLayoutParamsIfNeeded(WindowManager.LayoutParams layoutParams);

    void onAttachedPatternViewToWindow(LockPatternView lockPatternView);

    void onAttachedToWindow(
            KnoxAuthCredentialView knoxAuthCredentialView,
            TextView textView,
            TextView textView2,
            TextView textView3,
            ImageView imageView);

    void onConfigurationChanged();

    void onCredentialVerified(int i, boolean z, View view, int i2, TextView textView);

    void onDetachedFromWindow();

    void onErrorTimeoutFinish(KnoxAuthCredentialView knoxAuthCredentialView, int i, View view);

    void setBiometricAttemptDeadline(int i);

    void setDetailText(TextView textView);

    void setErrorTimerText(TextView textView, long j);
}
