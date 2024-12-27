package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

public interface IRemoteTransition extends IInterface {
    public static final String DESCRIPTOR = "android.window.IRemoteTransition";

    void mergeAnimation(
            IBinder iBinder,
            TransitionInfo transitionInfo,
            SurfaceControl.Transaction transaction,
            IBinder iBinder2,
            IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback)
            throws RemoteException;

    void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException;

    void startAnimation(
            IBinder iBinder,
            TransitionInfo transitionInfo,
            SurfaceControl.Transaction transaction,
            IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback)
            throws RemoteException;

    void takeOverAnimation(
            IBinder iBinder,
            TransitionInfo transitionInfo,
            SurfaceControl.Transaction transaction,
            IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback,
            WindowAnimationState[] windowAnimationStateArr)
            throws RemoteException;

    public static class Default implements IRemoteTransition {
        @Override // android.window.IRemoteTransition
        public void startAnimation(
                IBinder token,
                TransitionInfo info,
                SurfaceControl.Transaction t,
                IRemoteTransitionFinishedCallback finishCallback)
                throws RemoteException {}

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
                SurfaceControl.Transaction t,
                IRemoteTransitionFinishedCallback finishCallback,
                WindowAnimationState[] states)
                throws RemoteException {}

        @Override // android.window.IRemoteTransition
        public void onTransitionConsumed(IBinder transition, boolean aborted)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteTransition {
        static final int TRANSACTION_mergeAnimation = 2;
        static final int TRANSACTION_onTransitionConsumed = 4;
        static final int TRANSACTION_startAnimation = 1;
        static final int TRANSACTION_takeOverAnimation = 3;

        public Stub() {
            attachInterface(this, IRemoteTransition.DESCRIPTOR);
        }

        public static IRemoteTransition asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteTransition.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteTransition)) {
                return (IRemoteTransition) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startAnimation";
                case 2:
                    return "mergeAnimation";
                case 3:
                    return "takeOverAnimation";
                case 4:
                    return "onTransitionConsumed";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRemoteTransition.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteTransition.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    TransitionInfo _arg1 =
                            (TransitionInfo) data.readTypedObject(TransitionInfo.CREATOR);
                    SurfaceControl.Transaction _arg2 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    IRemoteTransitionFinishedCallback _arg3 =
                            IRemoteTransitionFinishedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startAnimation(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    TransitionInfo _arg12 =
                            (TransitionInfo) data.readTypedObject(TransitionInfo.CREATOR);
                    SurfaceControl.Transaction _arg22 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    IBinder _arg32 = data.readStrongBinder();
                    IRemoteTransitionFinishedCallback _arg4 =
                            IRemoteTransitionFinishedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    mergeAnimation(_arg02, _arg12, _arg22, _arg32, _arg4);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    TransitionInfo _arg13 =
                            (TransitionInfo) data.readTypedObject(TransitionInfo.CREATOR);
                    SurfaceControl.Transaction _arg23 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    IRemoteTransitionFinishedCallback _arg33 =
                            IRemoteTransitionFinishedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    WindowAnimationState[] _arg42 =
                            (WindowAnimationState[])
                                    data.createTypedArray(WindowAnimationState.CREATOR);
                    data.enforceNoDataAvail();
                    takeOverAnimation(_arg03, _arg13, _arg23, _arg33, _arg42);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    boolean _arg14 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTransitionConsumed(_arg04, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteTransition {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTransition.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransition
            public void startAnimation(
                    IBinder token,
                    TransitionInfo info,
                    SurfaceControl.Transaction t,
                    IRemoteTransitionFinishedCallback finishCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(t, 0);
                    _data.writeStrongInterface(finishCallback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void mergeAnimation(
                    IBinder transition,
                    TransitionInfo info,
                    SurfaceControl.Transaction t,
                    IBinder mergeTarget,
                    IRemoteTransitionFinishedCallback finishCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    _data.writeStrongBinder(transition);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(t, 0);
                    _data.writeStrongBinder(mergeTarget);
                    _data.writeStrongInterface(finishCallback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void takeOverAnimation(
                    IBinder transition,
                    TransitionInfo info,
                    SurfaceControl.Transaction t,
                    IRemoteTransitionFinishedCallback finishCallback,
                    WindowAnimationState[] states)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    _data.writeStrongBinder(transition);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(t, 0);
                    _data.writeStrongInterface(finishCallback);
                    _data.writeTypedArray(states, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void onTransitionConsumed(IBinder transition, boolean aborted)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    _data.writeStrongBinder(transition);
                    _data.writeBoolean(aborted);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
