package com.samsung.android.wifi.ai.iwh;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIwhTrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.iwh.IIwhTrService";

    void initialize(String str, int i, int i2, int i3, boolean z) throws RemoteException;

    public static class Default implements IIwhTrService {
        @Override // com.samsung.android.wifi.ai.iwh.IIwhTrService
        public void initialize(
                String key, int desiredScore, int scoreMarginPercent, int mode, boolean testmode)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIwhTrService {
        static final int TRANSACTION_initialize = 1;

        public Stub() {
            attachInterface(this, IIwhTrService.DESCRIPTOR);
        }

        public static IIwhTrService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIwhTrService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIwhTrService)) {
                return (IIwhTrService) iin;
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
                    return "initialize";
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
                data.enforceInterface(IIwhTrService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIwhTrService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    boolean _arg4 = data.readBoolean();
                    data.enforceNoDataAvail();
                    initialize(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIwhTrService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIwhTrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ai.iwh.IIwhTrService
            public void initialize(
                    String key,
                    int desiredScore,
                    int scoreMarginPercent,
                    int mode,
                    boolean testmode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIwhTrService.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(desiredScore);
                    _data.writeInt(scoreMarginPercent);
                    _data.writeInt(mode);
                    _data.writeBoolean(testmode);
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
