package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.EuiccNotification;

/* loaded from: classes4.dex */
public interface IListNotificationsCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IListNotificationsCallback";

    public static class Default implements IListNotificationsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
        public void onComplete(int i, EuiccNotification[] euiccNotificationArr) throws RemoteException {
        }
    }

    void onComplete(int i, EuiccNotification[] euiccNotificationArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IListNotificationsCallback {
        static final int TRANSACTION_onComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IListNotificationsCallback.DESCRIPTOR);
        }

        public static IListNotificationsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IListNotificationsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IListNotificationsCallback)) {
                return (IListNotificationsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IListNotificationsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IListNotificationsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                EuiccNotification[] euiccNotificationArr = (EuiccNotification[]) parcel.createTypedArray(EuiccNotification.CREATOR);
                parcel.enforceNoDataAvail();
                onComplete(readInt, euiccNotificationArr);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IListNotificationsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IListNotificationsCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
            public void onComplete(int i, EuiccNotification[] euiccNotificationArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IListNotificationsCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(euiccNotificationArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
