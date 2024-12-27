package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBleAdvertiserService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IBleAdvertiserService";

    boolean needToKeepBinding() throws RemoteException;

    public static class Default implements IBleAdvertiserService {
        @Override // com.samsung.android.desktopmode.IBleAdvertiserService
        public boolean needToKeepBinding() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBleAdvertiserService {
        static final int TRANSACTION_needToKeepBinding = 1;

        public Stub() {
            attachInterface(this, IBleAdvertiserService.DESCRIPTOR);
        }

        public static IBleAdvertiserService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBleAdvertiserService.DESCRIPTOR);
            if (iin != null && (iin instanceof IBleAdvertiserService)) {
                return (IBleAdvertiserService) iin;
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
                    return "needToKeepBinding";
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
                data.enforceInterface(IBleAdvertiserService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBleAdvertiserService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = needToKeepBinding();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBleAdvertiserService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBleAdvertiserService.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IBleAdvertiserService
            public boolean needToKeepBinding() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBleAdvertiserService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
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
