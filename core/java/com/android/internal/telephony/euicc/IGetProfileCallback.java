package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

public interface IGetProfileCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.euicc.IGetProfileCallback";

    void onComplete(int i, EuiccProfileInfo euiccProfileInfo) throws RemoteException;

    public static class Default implements IGetProfileCallback {
        @Override // com.android.internal.telephony.euicc.IGetProfileCallback
        public void onComplete(int resultCode, EuiccProfileInfo profile) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGetProfileCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IGetProfileCallback.DESCRIPTOR);
        }

        public static IGetProfileCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetProfileCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetProfileCallback)) {
                return (IGetProfileCallback) iin;
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
                data.enforceInterface(IGetProfileCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetProfileCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    EuiccProfileInfo _arg1 =
                            (EuiccProfileInfo) data.readTypedObject(EuiccProfileInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetProfileCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetProfileCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetProfileCallback
            public void onComplete(int resultCode, EuiccProfileInfo profile)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetProfileCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeTypedObject(profile, 0);
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
