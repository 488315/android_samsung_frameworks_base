package com.android.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
