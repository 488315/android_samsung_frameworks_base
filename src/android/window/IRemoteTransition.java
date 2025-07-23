package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;

/* loaded from: classes5.dex */
public interface IRemoteTransition extends IInterface {
    public static final String DESCRIPTOR = "android.window.IRemoteTransition";

    public static class Default implements IRemoteTransition {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IRemoteTransition
        public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
        }

        @Override // android.window.IRemoteTransition
        public void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.window.IRemoteTransition
        public void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
        }

        @Override // android.window.IRemoteTransition
        public void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
        }
    }

    void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException;

    void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException;

    void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException;

    void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTransition {
        static final int TRANSACTION_mergeAnimation = 2;
        static final int TRANSACTION_onTransitionConsumed = 4;
        static final int TRANSACTION_startAnimation = 1;
        static final int TRANSACTION_takeOverAnimation = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IRemoteTransition.DESCRIPTOR);
        }

        public static IRemoteTransition asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteTransition.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteTransition)) {
                return (IRemoteTransition) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startAnimation";
            }
            if (i == 2) {
                return "mergeAnimation";
            }
            if (i == 3) {
                return "takeOverAnimation";
            }
            if (i != 4) {
                return null;
            }
            return "onTransitionConsumed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteTransition.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTransition.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                TransitionInfo transitionInfo = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IRemoteTransitionFinishedCallback asInterface = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                startAnimation(readStrongBinder, transitionInfo, transaction, asInterface);
            } else if (i == 2) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                TransitionInfo transitionInfo2 = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                IRemoteTransitionFinishedCallback asInterface2 = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                mergeAnimation(readStrongBinder2, transitionInfo2, transaction2, readStrongBinder3, asInterface2);
            } else if (i == 3) {
                IBinder readStrongBinder4 = parcel.readStrongBinder();
                TransitionInfo transitionInfo3 = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IRemoteTransitionFinishedCallback asInterface3 = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                WindowAnimationState[] windowAnimationStateArr = (WindowAnimationState[]) parcel.createTypedArray(WindowAnimationState.CREATOR);
                parcel.enforceNoDataAvail();
                takeOverAnimation(readStrongBinder4, transitionInfo3, transaction3, asInterface3, windowAnimationStateArr);
            } else if (i == 4) {
                IBinder readStrongBinder5 = parcel.readStrongBinder();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTransitionConsumed(readStrongBinder5, readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteTransition {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTransition.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransition
            public void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    obtain.writeTypedArray(windowAnimationStateArr, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
