package com.samsung.android.ssdid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemSsdidManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ssdid.ISemSsdidManagerService";

    String getSsdid() throws RemoteException;

    public static class Default implements ISemSsdidManagerService {
        @Override // com.samsung.android.ssdid.ISemSsdidManagerService
        public String getSsdid() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemSsdidManagerService {
        static final int TRANSACTION_getSsdid = 1;

        public Stub() {
            attachInterface(this, ISemSsdidManagerService.DESCRIPTOR);
        }

        public static ISemSsdidManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemSsdidManagerService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemSsdidManagerService)) {
                return (ISemSsdidManagerService) iin;
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
                    return "getSsdid";
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
                data.enforceInterface(ISemSsdidManagerService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemSsdidManagerService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _result = getSsdid();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemSsdidManagerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemSsdidManagerService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ssdid.ISemSsdidManagerService
            public String getSsdid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemSsdidManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
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
