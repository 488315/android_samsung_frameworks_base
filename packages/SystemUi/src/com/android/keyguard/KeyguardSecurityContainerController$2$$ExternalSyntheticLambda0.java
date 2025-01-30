package com.android.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0 implements Runnable {
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
