package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteLockMonitorCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.widget.IRemoteLockMonitorCallback";

    void changeRemoteLockState(RemoteLockInfo remoteLockInfo) throws RemoteException;

    int checkRemoteLockPassword(byte[] bArr) throws RemoteException;

    public static class Default implements IRemoteLockMonitorCallback {
        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public void changeRemoteLockState(RemoteLockInfo data) throws RemoteException {}

        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public int checkRemoteLockPassword(byte[] password) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteLockMonitorCallback {
        static final int TRANSACTION_changeRemoteLockState = 1;
        static final int TRANSACTION_checkRemoteLockPassword = 2;

        public Stub() {
            attachInterface(this, IRemoteLockMonitorCallback.DESCRIPTOR);
        }

        public static IRemoteLockMonitorCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteLockMonitorCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteLockMonitorCallback)) {
                return (IRemoteLockMonitorCallback) iin;
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
                    return "changeRemoteLockState";
                case 2:
                    return "checkRemoteLockPassword";
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
                data.enforceInterface(IRemoteLockMonitorCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteLockMonitorCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    RemoteLockInfo _arg0 =
                            (RemoteLockInfo) data.readTypedObject(RemoteLockInfo.CREATOR);
                    data.enforceNoDataAvail();
                    changeRemoteLockState(_arg0);
                    return true;
                case 2:
                    byte[] _arg02 = data.createByteArray();
                    data.enforceNoDataAvail();
                    int _result = checkRemoteLockPassword(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteLockMonitorCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLockMonitorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IRemoteLockMonitorCallback
            public void changeRemoteLockState(RemoteLockInfo data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteLockMonitorCallback
            public int checkRemoteLockPassword(byte[] password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    _data.writeByteArray(password);
                    this.mRemote.transact(2, _data, _reply, 0);
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
            return 1;
        }
    }
}
