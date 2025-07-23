package android.service.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGetAvailableMemoryInBytesCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.euicc.IGetAvailableMemoryInBytesCallback";

    public static class Default implements IGetAvailableMemoryInBytesCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
        public void onSuccess(long j) throws RemoteException {
        }

        @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
        public void onUnsupportedOperationException(String str) throws RemoteException {
        }
    }

    void onSuccess(long j) throws RemoteException;

    void onUnsupportedOperationException(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetAvailableMemoryInBytesCallback {
        static final int TRANSACTION_onSuccess = 1;
        static final int TRANSACTION_onUnsupportedOperationException = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
        }

        public static IGetAvailableMemoryInBytesCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetAvailableMemoryInBytesCallback)) {
                return (IGetAvailableMemoryInBytesCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onUnsupportedOperationException";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onSuccess(readLong);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onUnsupportedOperationException(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGetAvailableMemoryInBytesCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetAvailableMemoryInBytesCallback.DESCRIPTOR;
            }

            @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
            public void onSuccess(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
            public void onUnsupportedOperationException(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
