package com.android.keyguard;

import android.hardware.fingerprint.FingerprintManager;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;

public final class SecFpAuthCallback extends FingerprintManager.AuthenticationCallback {
    public final int mCallbackSeq;
    public final WeakReference mDispatcher;
    public final Runnable mRunnable;

    public SecFpAuthCallback(int i, Consumer<SecFpMsg> consumer, Runnable runnable) {
        this.mCallbackSeq = i;
        this.mDispatcher = new WeakReference(consumer);
        this.mRunnable = runnable;
    }

    public final void onAuthenticationAcquired(int i) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFpAuthCallback$$ExternalSyntheticLambda0(this, i, 2));
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public final void onAuthenticationError(int i, CharSequence charSequence) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFpAuthCallback$$ExternalSyntheticLambda1(this, i, charSequence, 0));
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public final void onAuthenticationFailed() {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFpAuthCallback$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Consumer) obj).accept(SecFpMsg.obtain(3, SecFpAuthCallback.this.mCallbackSeq, -1, null, null));
            }
        });
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public final void onAuthenticationHelp(int i, CharSequence charSequence) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFpAuthCallback$$ExternalSyntheticLambda1(this, i, charSequence, 1));
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public final void onAuthenticationSucceeded(final FingerprintManager.AuthenticationResult authenticationResult) {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFpAuthCallback$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecFpAuthCallback secFpAuthCallback = SecFpAuthCallback.this;
                ((Consumer) obj).accept(SecFpMsg.obtain(2, secFpAuthCallback.mCallbackSeq, -1, null, authenticationResult));
            }
        });
    }

    public final void onUdfpsPointerDown(int i) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFpAuthCallback$$ExternalSyntheticLambda0(this, i, 1));
    }

    public final void onUdfpsPointerUp(int i) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFpAuthCallback$$ExternalSyntheticLambda0(this, i, 0));
    }
}
