package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IImsStateCallback";

    public static class Default implements IImsStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IImsStateCallback
        public void onAvailable() throws RemoteException {
        }

        @Override // com.android.internal.telephony.IImsStateCallback
        public void onUnavailable(int i) throws RemoteException {
        }
    }

    void onAvailable() throws RemoteException;

    void onUnavailable(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsStateCallback {
        static final int TRANSACTION_onAvailable = 2;
        static final int TRANSACTION_onUnavailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IImsStateCallback.DESCRIPTOR);
        }

        public static IImsStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsStateCallback)) {
                return (IImsStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onUnavailable";
            }
            if (i != 2) {
                return null;
            }
            return "onAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUnavailable(readInt);
            } else if (i == 2) {
                onAvailable();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IImsStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsStateCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IImsStateCallback
            public void onUnavailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IImsStateCallback
            public void onAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsStateCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
