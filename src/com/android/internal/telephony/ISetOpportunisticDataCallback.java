package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISetOpportunisticDataCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISetOpportunisticDataCallback";

    public static class Default implements ISetOpportunisticDataCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(int i) throws RemoteException {
        }
    }

    void onComplete(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISetOpportunisticDataCallback {
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
            attachInterface(this, ISetOpportunisticDataCallback.DESCRIPTOR);
        }

        public static ISetOpportunisticDataCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISetOpportunisticDataCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISetOpportunisticDataCallback)) {
                return (ISetOpportunisticDataCallback) queryLocalInterface;
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
                parcel.enforceInterface(ISetOpportunisticDataCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISetOpportunisticDataCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onComplete(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISetOpportunisticDataCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISetOpportunisticDataCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
            public void onComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISetOpportunisticDataCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
