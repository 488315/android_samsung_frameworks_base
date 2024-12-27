package android.os.incremental;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIncrementalServiceConnector extends IInterface {
    public static final String DESCRIPTOR = "android.os.incremental.IIncrementalServiceConnector";

    int setStorageParams(boolean z) throws RemoteException;

    public static class Default implements IIncrementalServiceConnector {
        @Override // android.os.incremental.IIncrementalServiceConnector
        public int setStorageParams(boolean enableReadLogs) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIncrementalServiceConnector {
        static final int TRANSACTION_setStorageParams = 1;

        public Stub() {
            attachInterface(this, IIncrementalServiceConnector.DESCRIPTOR);
        }

        public static IIncrementalServiceConnector asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIncrementalServiceConnector.DESCRIPTOR);
            if (iin != null && (iin instanceof IIncrementalServiceConnector)) {
                return (IIncrementalServiceConnector) iin;
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
                    return "setStorageParams";
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
                data.enforceInterface(IIncrementalServiceConnector.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIncrementalServiceConnector.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result = setStorageParams(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIncrementalServiceConnector {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncrementalServiceConnector.DESCRIPTOR;
            }

            @Override // android.os.incremental.IIncrementalServiceConnector
            public int setStorageParams(boolean enableReadLogs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIncrementalServiceConnector.DESCRIPTOR);
                    _data.writeBoolean(enableReadLogs);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
