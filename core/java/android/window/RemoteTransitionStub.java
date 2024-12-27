package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public abstract class RemoteTransitionStub extends IRemoteTransition.Stub {
    @Override // android.window.IRemoteTransition
    public void mergeAnimation(
            IBinder transition,
            TransitionInfo info,
            SurfaceControl.Transaction t,
            IBinder mergeTarget,
            IRemoteTransitionFinishedCallback finishCallback)
            throws RemoteException {}

    @Override // android.window.IRemoteTransition
    public void takeOverAnimation(
            IBinder transition,
            TransitionInfo info,
            SurfaceControl.Transaction startTransaction,
            IRemoteTransitionFinishedCallback finishCallback,
            WindowAnimationState[] states)
            throws RemoteException {
        throw new RemoteException("Takeovers are not supported by this IRemoteTransition");
    }

    @Override // android.window.IRemoteTransition
    public void onTransitionConsumed(IBinder transition, boolean aborted) throws RemoteException {}
}
