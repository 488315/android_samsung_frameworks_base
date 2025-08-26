package com.samsung.android.globalactions.util;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* loaded from: classes6.dex */
public class BiometricPromptWrapper {
    private static final String TAG = "BiometricPromptWrapper";
    private final BiometricManager mBiometricManager;
    private final BiometricPrompt.Builder mBuilder;
    private final Context mContext;
    private Runnable mFailRunnable;
    private final LogWrapper mLogWrapper;
    private Runnable mSuccessRunnable;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BiometricPrompt.AuthenticationCallback mCallback = new AnonymousClass1();

    /* renamed from: com.samsung.android.globalactions.util.BiometricPromptWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends BiometricPrompt.AuthenticationCallback {
        AnonymousClass1() {
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
            BiometricPromptWrapper.this.mLogWrapper.i(BiometricPromptWrapper.TAG, "onAuthenticationError() code : " + i + ", errString : " + ((Object) charSequence));
            super.onAuthenticationError(i, charSequence);
            if (BiometricPromptWrapper.this.mFailRunnable != null) {
                BiometricPromptWrapper.this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAuthenticationError$0();
                    }
                }, 100L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationError$0() {
            BiometricPromptWrapper.this.mFailRunnable.run();
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
            if (BiometricPromptWrapper.this.mSuccessRunnable != null) {
                BiometricPromptWrapper.this.mHandler.post(new Runnable() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAuthenticationSucceeded$1();
                    }
                });
            }
            super.onAuthenticationSucceeded(authenticationResult);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$1() {
            BiometricPromptWrapper.this.mSuccessRunnable.run();
        }
    }

    public BiometricPromptWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mBiometricManager = (BiometricManager) context.getSystemService(BiometricManager.class);
        this.mBuilder = new BiometricPrompt.Builder(context);
    }

    public void initPrompt(String str, int i) {
        this.mBuilder.setTitle(str).setAllowedAuthenticators(i).setAllowBackgroundAuthentication(true);
    }

    public void setRunnable(Runnable runnable, Runnable runnable2) {
        this.mSuccessRunnable = runnable;
        this.mFailRunnable = runnable2;
    }

    public void buildAndRun(CancellationSignal cancellationSignal) {
        this.mBuilder.build().authenticate(cancellationSignal, new Executor() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                runnable.run();
            }
        }, this.mCallback);
    }

    public boolean canAuthenticate(int i) {
        int iCanAuthenticate = this.mBiometricManager.canAuthenticate(i);
        this.mLogWrapper.i(TAG, "canAuthenticate() : " + iCanAuthenticate);
        return iCanAuthenticate == 0;
    }
}
