package com.samsung.android.desktopsystemui.sharedlib.system;

import android.window.IRemoteTransitionFinishedCallback;
import com.samsung.android.desktopsystemui.sharedlib.system.RemoteTransitionCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteTransitionCompat$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IRemoteTransitionFinishedCallback f$0;

    public /* synthetic */ RemoteTransitionCompat$1$$ExternalSyntheticLambda0(IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = iRemoteTransitionFinishedCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RemoteTransitionCompat.C45761.lambda$startAnimation$0(this.f$0);
                break;
            default:
                RemoteTransitionCompat.C45761.lambda$mergeAnimation$2(this.f$0);
                break;
        }
    }
}
