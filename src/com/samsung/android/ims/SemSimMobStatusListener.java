package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemSimMobStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemSimMobStatusListener";

    public static class Default implements SemSimMobStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.SemSimMobStatusListener
        public void onSimMobilityStateChanged(boolean z) throws RemoteException {
        }
    }

    void onSimMobilityStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements SemSimMobStatusListener {
        static final int TRANSACTION_onSimMobilityStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, SemSimMobStatusListener.DESCRIPTOR);
        }

        public static SemSimMobStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemSimMobStatusListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemSimMobStatusListener)) {
                return (SemSimMobStatusListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSimMobilityStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemSimMobStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemSimMobStatusListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSimMobilityStateChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements SemSimMobStatusListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemSimMobStatusListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemSimMobStatusListener
            public void onSimMobilityStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(SemSimMobStatusListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
