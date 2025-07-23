package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;

/* loaded from: classes5.dex */
public abstract class RemoteTransitionStub extends IRemoteTransition.Stub {
    @Override // android.window.IRemoteTransition
    public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
    }

    @Override // android.window.IRemoteTransition
    public void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException {
    }

    @Override // android.window.IRemoteTransition
    public void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
        throw new RemoteException("Takeovers are not supported by this IRemoteTransition");
    }
}
