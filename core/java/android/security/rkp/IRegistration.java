package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRegistration extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IRegistration";

    void cancelGetKey(IGetKeyCallback iGetKeyCallback) throws RemoteException;

    void getKey(int i, IGetKeyCallback iGetKeyCallback) throws RemoteException;

    void storeUpgradedKeyAsync(
            byte[] bArr, byte[] bArr2, IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback)
            throws RemoteException;

    public static class Default implements IRegistration {
        @Override // android.security.rkp.IRegistration
        public void getKey(int keyId, IGetKeyCallback callback) throws RemoteException {}

        @Override // android.security.rkp.IRegistration
        public void cancelGetKey(IGetKeyCallback callback) throws RemoteException {}

        @Override // android.security.rkp.IRegistration
        public void storeUpgradedKeyAsync(
                byte[] oldKeyBlob, byte[] newKeyBlob, IStoreUpgradedKeyCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRegistration {
        static final int TRANSACTION_cancelGetKey = 2;
        static final int TRANSACTION_getKey = 1;
        static final int TRANSACTION_storeUpgradedKeyAsync = 3;

        public Stub() {
            attachInterface(this, IRegistration.DESCRIPTOR);
        }

        public static IRegistration asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRegistration.DESCRIPTOR);
            if (iin != null && (iin instanceof IRegistration)) {
                return (IRegistration) iin;
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
                    return "getKey";
                case 2:
                    return "cancelGetKey";
                case 3:
                    return "storeUpgradedKeyAsync";
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
                data.enforceInterface(IRegistration.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRegistration.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    IGetKeyCallback _arg1 =
                            IGetKeyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getKey(_arg0, _arg1);
                    return true;
                case 2:
                    IGetKeyCallback _arg02 =
                            IGetKeyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    cancelGetKey(_arg02);
                    return true;
                case 3:
                    byte[] _arg03 = data.createByteArray();
                    byte[] _arg12 = data.createByteArray();
                    IStoreUpgradedKeyCallback _arg2 =
                            IStoreUpgradedKeyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    storeUpgradedKeyAsync(_arg03, _arg12, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRegistration {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRegistration.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRegistration
            public void getKey(int keyId, IGetKeyCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    _data.writeInt(keyId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void cancelGetKey(IGetKeyCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void storeUpgradedKeyAsync(
                    byte[] oldKeyBlob, byte[] newKeyBlob, IStoreUpgradedKeyCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    _data.writeByteArray(oldKeyBlob);
                    _data.writeByteArray(newKeyBlob);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
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
