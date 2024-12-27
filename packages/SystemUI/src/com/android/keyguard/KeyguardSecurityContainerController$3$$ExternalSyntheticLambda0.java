package com.android.keyguard;

public final /* synthetic */ class KeyguardSecurityContainerController$3$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException unused) {
        }
        System.gc();
        System.runFinalization();
        System.gc();
    }
}
