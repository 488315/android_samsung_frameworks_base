package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDataLoaderManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IDataLoaderManager";

    boolean bindToDataLoader(
            int i,
            DataLoaderParamsParcel dataLoaderParamsParcel,
            long j,
            IDataLoaderStatusListener iDataLoaderStatusListener)
            throws RemoteException;

    IDataLoader getDataLoader(int i) throws RemoteException;

    void unbindFromDataLoader(int i) throws RemoteException;

    public static class Default implements IDataLoaderManager {
        @Override // android.content.pm.IDataLoaderManager
        public boolean bindToDataLoader(
                int id,
                DataLoaderParamsParcel params,
                long bindDelayMs,
                IDataLoaderStatusListener listener)
                throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IDataLoaderManager
        public IDataLoader getDataLoader(int dataLoaderId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IDataLoaderManager
        public void unbindFromDataLoader(int dataLoaderId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDataLoaderManager {
        static final int TRANSACTION_bindToDataLoader = 1;
        static final int TRANSACTION_getDataLoader = 2;
        static final int TRANSACTION_unbindFromDataLoader = 3;

        public Stub() {
            attachInterface(this, IDataLoaderManager.DESCRIPTOR);
        }

        public static IDataLoaderManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDataLoaderManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IDataLoaderManager)) {
                return (IDataLoaderManager) iin;
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
                    return "bindToDataLoader";
                case 2:
                    return "getDataLoader";
                case 3:
                    return "unbindFromDataLoader";
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
                data.enforceInterface(IDataLoaderManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDataLoaderManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    DataLoaderParamsParcel _arg1 =
                            (DataLoaderParamsParcel)
                                    data.readTypedObject(DataLoaderParamsParcel.CREATOR);
                    long _arg2 = data.readLong();
                    IDataLoaderStatusListener _arg3 =
                            IDataLoaderStatusListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result = bindToDataLoader(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    IDataLoader _result2 = getDataLoader(_arg02);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    unbindFromDataLoader(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDataLoaderManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataLoaderManager.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoaderManager
            public boolean bindToDataLoader(
                    int id,
                    DataLoaderParamsParcel params,
                    long bindDelayMs,
                    IDataLoaderStatusListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeTypedObject(params, 0);
                    _data.writeLong(bindDelayMs);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public IDataLoader getDataLoader(int dataLoaderId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    _data.writeInt(dataLoaderId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IDataLoader _result = IDataLoader.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public void unbindFromDataLoader(int dataLoaderId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    _data.writeInt(dataLoaderId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
