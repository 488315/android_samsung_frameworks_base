package android.os.incremental;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIncrementalServiceConnector extends IInterface {
    public static final String DESCRIPTOR = "android.os.incremental.IIncrementalServiceConnector";

    public static class Default implements IIncrementalServiceConnector {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.incremental.IIncrementalServiceConnector
        public int setStorageParams(boolean z) throws RemoteException {
            return 0;
        }
    }

    int setStorageParams(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IIncrementalServiceConnector {
        static final int TRANSACTION_setStorageParams = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIncrementalServiceConnector.DESCRIPTOR);
        }

        public static IIncrementalServiceConnector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIncrementalServiceConnector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncrementalServiceConnector)) {
                return (IIncrementalServiceConnector) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setStorageParams";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIncrementalServiceConnector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIncrementalServiceConnector.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int storageParams = setStorageParams(readBoolean);
                parcel2.writeNoException();
                parcel2.writeInt(storageParams);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIncrementalServiceConnector {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncrementalServiceConnector.DESCRIPTOR;
            }

            @Override // android.os.incremental.IIncrementalServiceConnector
            public int setStorageParams(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalServiceConnector.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
