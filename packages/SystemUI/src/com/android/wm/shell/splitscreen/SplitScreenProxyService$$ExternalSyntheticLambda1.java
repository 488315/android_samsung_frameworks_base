package com.android.wm.shell.splitscreen;

import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.splitscreen.SplitScreenProxyService;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenProxyService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplitScreenProxyService$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SplitScreenProxyService) obj).getClass();
                return;
            default:
                IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback = (IRemoteTransitionFinishedCallback) obj;
                int i2 = SplitScreenProxyService.AnonymousClass1.$r8$clinit;
                try {
                    boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                    Slog.d("SplitScreenProxyService", "Call onTransitionFinished: " + iRemoteTransitionFinishedCallback);
                    iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
                    return;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
