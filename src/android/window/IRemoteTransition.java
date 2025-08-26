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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTransition.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTransition)) {
                return (IRemoteTransition) iInterfaceQueryLocalInterface;
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
                IBinder strongBinder = parcel.readStrongBinder();
                TransitionInfo transitionInfo = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallbackAsInterface = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                startAnimation(strongBinder, transitionInfo, transaction, iRemoteTransitionFinishedCallbackAsInterface);
            } else if (i == 2) {
                IBinder strongBinder2 = parcel.readStrongBinder();
                TransitionInfo transitionInfo2 = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IBinder strongBinder3 = parcel.readStrongBinder();
                IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallbackAsInterface2 = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                mergeAnimation(strongBinder2, transitionInfo2, transaction2, strongBinder3, iRemoteTransitionFinishedCallbackAsInterface2);
            } else if (i == 3) {
                IBinder strongBinder4 = parcel.readStrongBinder();
                TransitionInfo transitionInfo3 = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallbackAsInterface3 = IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                WindowAnimationState[] windowAnimationStateArr = (WindowAnimationState[]) parcel.createTypedArray(WindowAnimationState.CREATOR);
                parcel.enforceNoDataAvail();
                takeOverAnimation(strongBinder4, transitionInfo3, transaction3, iRemoteTransitionFinishedCallbackAsInterface3, windowAnimationStateArr);
            } else if (i == 4) {
                IBinder strongBinder5 = parcel.readStrongBinder();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTransitionConsumed(strongBinder5, z);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(transitionInfo, 0);
                    parcelObtain.writeTypedObject(transaction, 0);
                    parcelObtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(transitionInfo, 0);
                    parcelObtain.writeTypedObject(transaction, 0);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(transitionInfo, 0);
                    parcelObtain.writeTypedObject(transaction, 0);
                    parcelObtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    parcelObtain.writeTypedArray(windowAnimationStateArr, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void onTransitionConsumed(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTransition.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
