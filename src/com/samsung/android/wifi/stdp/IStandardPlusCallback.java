package com.samsung.android.wifi.stdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IStandardPlusCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.stdp.IStandardPlusCallback";

    public static class Default implements IStandardPlusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
        public void onEvent(int i) throws RemoteException {
        }
    }

    void onEvent(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IStandardPlusCallback {
        static final int TRANSACTION_onEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStandardPlusCallback.DESCRIPTOR);
        }

        public static IStandardPlusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStandardPlusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStandardPlusCallback)) {
                return (IStandardPlusCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStandardPlusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStandardPlusCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEvent(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStandardPlusCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStandardPlusCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
            public void onEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStandardPlusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
