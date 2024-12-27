package com.samsung.android.wifi.ai.iwh;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIwhInfService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.iwh.IIwhInfService";

    void iwhInfResult(String str, int i, String str2, int i2) throws RemoteException;

    public static class Default implements IIwhInfService {
        @Override // com.samsung.android.wifi.ai.iwh.IIwhInfService
        public void iwhInfResult(String key, int mode, String bssid, int score)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIwhInfService {
        static final int TRANSACTION_iwhInfResult = 1;

        public Stub() {
            attachInterface(this, IIwhInfService.DESCRIPTOR);
        }

        public static IIwhInfService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIwhInfService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIwhInfService)) {
                return (IIwhInfService) iin;
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
                    return "iwhInfResult";
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
                data.enforceInterface(IIwhInfService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIwhInfService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    iwhInfResult(_arg0, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIwhInfService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIwhInfService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ai.iwh.IIwhInfService
            public void iwhInfResult(String key, int mode, String bssid, int score)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIwhInfService.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(mode);
                    _data.writeString(bssid);
                    _data.writeInt(score);
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
