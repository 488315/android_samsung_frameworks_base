package com.android.systemui.keyguard;

import android.os.Handler;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;

/* loaded from: classes2.dex */
public final class KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 implements Runnable {
    public final /* synthetic */ IRemoteAnimationFinishedCallback $finishedCallback;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl this$0;

    public KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl) {
        this.$finishedCallback = iRemoteAnimationFinishedCallback;
        this.this$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.$finishedCallback.onAnimationFinished();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Handler handler$1 = this.this$0.getHandler$1();
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
        handler$1.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1.1
            @Override // java.lang.Runnable
            public final void run() {
                keyguardViewMediatorHelperImpl.fixedRotationMonitor.cancel();
            }
        });
    }
}
