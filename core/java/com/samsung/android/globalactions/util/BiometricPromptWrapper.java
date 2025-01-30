package com.samsung.android.globalactions.util;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.p009os.CancellationSignal;
import android.p009os.Handler;
import android.p009os.Looper;
import com.samsung.android.globalactions.util.BiometricPromptWrapper;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class BiometricPromptWrapper {
    private static final String TAG = "BiometricPromptWrapper";
    private final BiometricManager mBiometricManager;
    private final BiometricPrompt.Builder mBuilder;
    private final Context mContext;
    private Runnable mFailRunnable;
    private final LogWrapper mLogWrapper;
    private Runnable mSuccessRunnable;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BiometricPrompt.AuthenticationCallback mCallback = new C50281();

    /* renamed from: com.samsung.android.globalactions.util.BiometricPromptWrapper$1 */
    class C50281 extends BiometricPrompt.AuthenticationCallback {
        C50281() {
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            BiometricPromptWrapper.this.mLogWrapper.m265i(BiometricPromptWrapper.TAG, "onAuthenticationError() code : " + errorCode + ", errString : " + ((Object) errString));
            super.onAuthenticationError(errorCode, errString);
            if (BiometricPromptWrapper.this.mFailRunnable != null) {
                BiometricPromptWrapper.this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricPromptWrapper.C50281.this.lambda$onAuthenticationError$0();
                    }
                }, 100L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationError$0() {
            BiometricPromptWrapper.this.mFailRunnable.run();
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            if (BiometricPromptWrapper.this.mSuccessRunnable != null) {
                BiometricPromptWrapper.this.mHandler.post(new Runnable() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricPromptWrapper.C50281.this.lambda$onAuthenticationSucceeded$1();
                    }
                });
            }
            super.onAuthenticationSucceeded(result);
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

    public void initPrompt(String title, int authenticators) {
        this.mBuilder.setTitle(title).setAllowedAuthenticators(authenticators);
    }

    public void setRunnable(Runnable successRunnable, Runnable failRunnable) {
        this.mSuccessRunnable = successRunnable;
        this.mFailRunnable = failRunnable;
    }

    public void buildAndRun(CancellationSignal signal) {
        BiometricPrompt bp = this.mBuilder.build();
        bp.authenticate(signal, new Executor() { // from class: com.samsung.android.globalactions.util.BiometricPromptWrapper$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                runnable.run();
            }
        }, this.mCallback);
    }

    public boolean canAuthenticate(int authenticators) {
        int auth = this.mBiometricManager.canAuthenticate(authenticators);
        this.mLogWrapper.m265i(TAG, "canAuthenticate() : " + auth);
        return auth == 0;
    }
}
