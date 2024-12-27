package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

public interface ISwitchToProfileCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.euicc.ISwitchToProfileCallback";

    void onComplete(int i, EuiccProfileInfo euiccProfileInfo) throws RemoteException;

    public static class Default implements ISwitchToProfileCallback {
        @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
        public void onComplete(int resultCode, EuiccProfileInfo profile) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISwitchToProfileCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, ISwitchToProfileCallback.DESCRIPTOR);
        }

        public static ISwitchToProfileCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISwitchToProfileCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISwitchToProfileCallback)) {
                return (ISwitchToProfileCallback) iin;
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
                data.enforceInterface(ISwitchToProfileCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISwitchToProfileCallback.DESCRIPTOR);
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

        private static class Proxy implements ISwitchToProfileCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISwitchToProfileCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
            public void onComplete(int resultCode, EuiccProfileInfo profile)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISwitchToProfileCallback.DESCRIPTOR);
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
