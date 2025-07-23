package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface ITransitionPlayer extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITransitionPlayer";

    public static class Default implements ITransitionPlayer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITransitionPlayer
        public void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws RemoteException {
        }

        @Override // android.window.ITransitionPlayer
        public void requestStartTransition(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) throws RemoteException {
        }

        @Override // android.window.ITransitionPlayer
        public void transitionAborted(IBinder iBinder) throws RemoteException {
        }
    }

    void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws RemoteException;

    void requestStartTransition(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) throws RemoteException;

    void transitionAborted(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements ITransitionPlayer {
        static final int TRANSACTION_onTransitionReady = 1;
        static final int TRANSACTION_requestStartTransition = 2;
        static final int TRANSACTION_transitionAborted = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ITransitionPlayer.DESCRIPTOR);
        }

        public static ITransitionPlayer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITransitionPlayer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITransitionPlayer)) {
                return (ITransitionPlayer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTransitionReady";
            }
            if (i == 2) {
                return "requestStartTransition";
            }
            if (i != 3) {
                return null;
            }
            return "transitionAborted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransitionPlayer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransitionPlayer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                TransitionInfo transitionInfo = (TransitionInfo) parcel.readTypedObject(TransitionInfo.CREATOR);
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                parcel.enforceNoDataAvail();
                onTransitionReady(readStrongBinder, transitionInfo, transaction, transaction2);
            } else if (i == 2) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                TransitionRequestInfo transitionRequestInfo = (TransitionRequestInfo) parcel.readTypedObject(TransitionRequestInfo.CREATOR);
                parcel.enforceNoDataAvail();
                requestStartTransition(readStrongBinder2, transitionRequestInfo);
            } else if (i == 3) {
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                transitionAborted(readStrongBinder3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITransitionPlayer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransitionPlayer.DESCRIPTOR;
            }

            @Override // android.window.ITransitionPlayer
            public void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeTypedObject(transaction2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITransitionPlayer
            public void requestStartTransition(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionRequestInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITransitionPlayer
            public void transitionAborted(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITransitionPlayer.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
