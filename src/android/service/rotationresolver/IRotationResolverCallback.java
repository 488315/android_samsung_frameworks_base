package android.service.rotationresolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRotationResolverCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.rotationresolver.IRotationResolverCallback";

    public static class Default implements IRotationResolverCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onFailure(int i) throws RemoteException {
        }

        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onSuccess(int i) throws RemoteException {
        }
    }

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(int i) throws RemoteException;

    void onSuccess(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRotationResolverCallback {
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IRotationResolverCallback.DESCRIPTOR);
        }

        public static IRotationResolverCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRotationResolverCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRotationResolverCallback)) {
                return (IRotationResolverCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCancellable";
            }
            if (i == 2) {
                return "onSuccess";
            }
            if (i != 3) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRotationResolverCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRotationResolverCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ICancellationSignal asInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCancellable(asInterface);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSuccess(readInt);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFailure(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRotationResolverCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRotationResolverCallback.DESCRIPTOR;
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onSuccess(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
