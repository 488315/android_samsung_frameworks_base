package com.samsung.android.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISLocationBatchingListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationBatchingListener";

    public static class Default implements ISLocationBatchingListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.location.ISLocationBatchingListener
        public void onLocationAvailable(Location[] locationArr, boolean z) throws RemoteException {
        }
    }

    void onLocationAvailable(Location[] locationArr, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISLocationBatchingListener {
        static final int TRANSACTION_onLocationAvailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISLocationBatchingListener.DESCRIPTOR);
        }

        public static ISLocationBatchingListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISLocationBatchingListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISLocationBatchingListener)) {
                return (ISLocationBatchingListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onLocationAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISLocationBatchingListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISLocationBatchingListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Location[] locationArr = (Location[]) parcel.createTypedArray(Location.CREATOR);
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onLocationAvailable(locationArr, z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISLocationBatchingListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationBatchingListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationBatchingListener
            public void onLocationAvailable(Location[] locationArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISLocationBatchingListener.DESCRIPTOR);
                    parcelObtain.writeTypedArray(locationArr, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
