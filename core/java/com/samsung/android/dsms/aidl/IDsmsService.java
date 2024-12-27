package com.samsung.android.dsms.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDsmsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.dsms.aidl.IDsmsService";

    void sendMessage(String str, String str2, long j) throws RemoteException;

    public static class Default implements IDsmsService {
        @Override // com.samsung.android.dsms.aidl.IDsmsService
        public void sendMessage(String featureCode, String detail, long value)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDsmsService {
        static final int TRANSACTION_sendMessage = 1;

        public Stub() {
            attachInterface(this, IDsmsService.DESCRIPTOR);
        }

        public static IDsmsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDsmsService.DESCRIPTOR);
            if (iin != null && (iin instanceof IDsmsService)) {
                return (IDsmsService) iin;
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
                    return "sendMessage";
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
                data.enforceInterface(IDsmsService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDsmsService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    sendMessage(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDsmsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDsmsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.dsms.aidl.IDsmsService
            public void sendMessage(String featureCode, String detail, long value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDsmsService.DESCRIPTOR);
                    _data.writeString(featureCode);
                    _data.writeString(detail);
                    _data.writeLong(value);
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
