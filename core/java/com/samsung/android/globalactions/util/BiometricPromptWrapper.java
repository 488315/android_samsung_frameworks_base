package com.samsung.android.globalactions.util;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

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
        AnonymousClass1() {}

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback,
                  // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            BiometricPromptWrapper.this.mLogWrapper.i(
                    BiometricPromptWrapper.TAG,
                    "onAuthenticationError() code : "
                            + errorCode
                            + ", errString : "
                            + ((Object) errString));
            super.onAuthenticationError(errorCode, errString);
            if (BiometricPromptWrapper.this.mFailRunnable != null) {
                BiometricPromptWrapper.this.mHandler.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BiometricPromptWrapper.AnonymousClass1.this
                                        .lambda$onAuthenticationError$0();
                            }
                        },
                        100L);
            }
        }

        public /* synthetic */ void lambda$onAuthenticationError$0() {
            BiometricPromptWrapper.this.mFailRunnable.run();
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            if (BiometricPromptWrapper.this.mSuccessRunnable != null) {
                BiometricPromptWrapper.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BiometricPromptWrapper.AnonymousClass1.this
                                        .lambda$onAuthenticationSucceeded$1();
                            }
                        });
            }
            super.onAuthenticationSucceeded(result);
        }

        public /* synthetic */ void lambda$onAuthenticationSucceeded$1() {
            BiometricPromptWrapper.this.mSuccessRunnable.run();
        }
    }

    public BiometricPromptWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mBiometricManager =
                (BiometricManager) context.getSystemService(BiometricManager.class);
        this.mBuilder = new BiometricPrompt.Builder(this.mContext);
    }

    public void initPrompt(String title, int authenticators) {
        this.mBuilder.setTitle(title).setAllowedAuthenticators(authenticators);
    }

    public void setRunnable(Runnable successRunnable, Runnable failRunnable) {
        this.mSuccessRunnable = successRunnable;
        this.mFailRunnable = failRunnable;
    }

    public void buildAndRun(CancellationSignal signal) {
        BiometricPrompt bp = this.mBuilder.build();
        bp.authenticate(
                signal,
                new Executor() { // from class:
                                 // com.samsung.android.globalactions.util.BiometricPromptWrapper$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        runnable.run();
                    }
                },
                this.mCallback);
    }

    public boolean canAuthenticate(int authenticators) {
        int auth = this.mBiometricManager.canAuthenticate(authenticators);
        this.mLogWrapper.i(TAG, "canAuthenticate() : " + auth);
        return auth == 0;
    }
}
