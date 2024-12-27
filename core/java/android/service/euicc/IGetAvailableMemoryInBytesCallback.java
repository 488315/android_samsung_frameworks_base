package android.service.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGetAvailableMemoryInBytesCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.euicc.IGetAvailableMemoryInBytesCallback";

    void onSuccess(long j) throws RemoteException;

    void onUnsupportedOperationException(String str) throws RemoteException;

    public static class Default implements IGetAvailableMemoryInBytesCallback {
        @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
        public void onSuccess(long availableMemoryInBytes) throws RemoteException {
        }

        @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
        public void onUnsupportedOperationException(String message) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGetAvailableMemoryInBytesCallback {
        static final int TRANSACTION_onSuccess = 1;
        static final int TRANSACTION_onUnsupportedOperationException = 2;

        public Stub() {
            attachInterface(this, IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
        }

        public static IGetAvailableMemoryInBytesCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetAvailableMemoryInBytesCallback)) {
                return (IGetAvailableMemoryInBytesCallback) iin;
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
                    return "onSuccess";
                case 2:
                    return "onUnsupportedOperationException";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onUnsupportedOperationException(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetAvailableMemoryInBytesCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetAvailableMemoryInBytesCallback.DESCRIPTOR;
            }

            @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
            public void onSuccess(long availableMemoryInBytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                    _data.writeLong(availableMemoryInBytes);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.euicc.IGetAvailableMemoryInBytesCallback
            public void onUnsupportedOperationException(String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetAvailableMemoryInBytesCallback.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
