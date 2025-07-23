package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemTasPolicyListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.SemTasPolicyListener";

    public static class Default implements SemTasPolicyListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.SemTasPolicyListener
        public void onTasPolicyChanged(int i, int i2) throws RemoteException {
        }
    }

    void onTasPolicyChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements SemTasPolicyListener {
        static final int TRANSACTION_onTasPolicyChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, SemTasPolicyListener.DESCRIPTOR);
        }

        public static SemTasPolicyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemTasPolicyListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemTasPolicyListener)) {
                return (SemTasPolicyListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTasPolicyChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemTasPolicyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemTasPolicyListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTasPolicyChanged(readInt, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements SemTasPolicyListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemTasPolicyListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.SemTasPolicyListener
            public void onTasPolicyChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(SemTasPolicyListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
