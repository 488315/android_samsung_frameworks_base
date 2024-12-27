package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.EuiccNotification;

/* loaded from: classes5.dex */
public interface IListNotificationsCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IListNotificationsCallback";

    void onComplete(int i, EuiccNotification[] euiccNotificationArr) throws RemoteException;

    public static class Default implements IListNotificationsCallback {
        @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
        public void onComplete(int resultCode, EuiccNotification[] notifications) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IListNotificationsCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IListNotificationsCallback.DESCRIPTOR);
        }

        public static IListNotificationsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IListNotificationsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IListNotificationsCallback)) {
                return (IListNotificationsCallback) iin;
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IListNotificationsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IListNotificationsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    EuiccNotification[] _arg1 = (EuiccNotification[]) data.createTypedArray(EuiccNotification.CREATOR);
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IListNotificationsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IListNotificationsCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
            public void onComplete(int resultCode, EuiccNotification[] notifications) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IListNotificationsCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeTypedArray(notifications, 0);
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
