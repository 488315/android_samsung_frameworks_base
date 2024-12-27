package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.EmergencyRegistrationResult;

public interface IWwanSelectorResultCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.IWwanSelectorResultCallback";

    void onComplete(EmergencyRegistrationResult emergencyRegistrationResult) throws RemoteException;

    public static class Default implements IWwanSelectorResultCallback {
        @Override // com.android.internal.telephony.IWwanSelectorResultCallback
        public void onComplete(EmergencyRegistrationResult result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWwanSelectorResultCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IWwanSelectorResultCallback.DESCRIPTOR);
        }

        public static IWwanSelectorResultCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWwanSelectorResultCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWwanSelectorResultCallback)) {
                return (IWwanSelectorResultCallback) iin;
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
                data.enforceInterface(IWwanSelectorResultCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWwanSelectorResultCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    EmergencyRegistrationResult _arg0 =
                            (EmergencyRegistrationResult)
                                    data.readTypedObject(EmergencyRegistrationResult.CREATOR);
                    data.enforceNoDataAvail();
                    onComplete(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWwanSelectorResultCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWwanSelectorResultCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWwanSelectorResultCallback
            public void onComplete(EmergencyRegistrationResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWwanSelectorResultCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
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
