package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IGetEuiccInfo1Callback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.euicc.IGetEuiccInfo1Callback";

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static class Default implements IGetEuiccInfo1Callback {
        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo1Callback
        public void onComplete(int resultCode, byte[] info) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGetEuiccInfo1Callback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IGetEuiccInfo1Callback.DESCRIPTOR);
        }

        public static IGetEuiccInfo1Callback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetEuiccInfo1Callback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetEuiccInfo1Callback)) {
                return (IGetEuiccInfo1Callback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IGetEuiccInfo1Callback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetEuiccInfo1Callback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetEuiccInfo1Callback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetEuiccInfo1Callback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetEuiccInfo1Callback
            public void onComplete(int resultCode, byte[] info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetEuiccInfo1Callback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeByteArray(info);
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
