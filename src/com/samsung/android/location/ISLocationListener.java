package com.samsung.android.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISLocationListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationListener";

    public static class Default implements ISLocationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.location.ISLocationListener
        public void onLocationChanged(Location location) throws RemoteException {
        }
    }

    void onLocationChanged(Location location) throws RemoteException;

    public static abstract class Stub extends Binder implements ISLocationListener {
        static final int TRANSACTION_onLocationChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISLocationListener.DESCRIPTOR);
        }

        public static ISLocationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISLocationListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISLocationListener)) {
                return (ISLocationListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onLocationChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISLocationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISLocationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Location location = (Location) parcel.readTypedObject(Location.CREATOR);
                parcel.enforceNoDataAvail();
                onLocationChanged(location);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISLocationListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationListener
            public void onLocationChanged(Location location) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISLocationListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(location, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
