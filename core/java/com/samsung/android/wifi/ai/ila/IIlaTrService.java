package com.samsung.android.wifi.ai.ila;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIlaTrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.ila.IIlaTrService";

    void train(String str) throws RemoteException;

    public static class Default implements IIlaTrService {
        @Override // com.samsung.android.wifi.ai.ila.IIlaTrService
        public void train(String bssid) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIlaTrService {
        static final int TRANSACTION_train = 1;

        public Stub() {
            attachInterface(this, IIlaTrService.DESCRIPTOR);
        }

        public static IIlaTrService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIlaTrService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIlaTrService)) {
                return (IIlaTrService) iin;
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
                    return "train";
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
                data.enforceInterface(IIlaTrService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIlaTrService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    train(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIlaTrService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIlaTrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ai.ila.IIlaTrService
            public void train(String bssid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIlaTrService.DESCRIPTOR);
                    _data.writeString(bssid);
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
