package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteAppModeListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.remoteappmode.IRemoteAppModeListener";

    void onRemoteAppModeStateChanged(boolean z) throws RemoteException;

    public static class Default implements IRemoteAppModeListener {
        @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
        public void onRemoteAppModeStateChanged(boolean isEnabled) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteAppModeListener {
        static final int TRANSACTION_onRemoteAppModeStateChanged = 1;

        public Stub() {
            attachInterface(this, IRemoteAppModeListener.DESCRIPTOR);
        }

        public static IRemoteAppModeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteAppModeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteAppModeListener)) {
                return (IRemoteAppModeListener) iin;
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
                    return "onRemoteAppModeStateChanged";
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
                data.enforceInterface(IRemoteAppModeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteAppModeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onRemoteAppModeStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteAppModeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppModeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
            public void onRemoteAppModeStateChanged(boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteAppModeListener.DESCRIPTOR);
                    _data.writeBoolean(isEnabled);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
