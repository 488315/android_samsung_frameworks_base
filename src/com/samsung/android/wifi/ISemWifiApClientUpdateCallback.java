package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiApClientUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApClientUpdateCallback";

    public static class Default implements ISemWifiApClientUpdateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
        public void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails) throws RemoteException {
        }
    }

    void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiApClientUpdateCallback {
        static final int TRANSACTION_onClientUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemWifiApClientUpdateCallback.DESCRIPTOR);
        }

        public static ISemWifiApClientUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemWifiApClientUpdateCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemWifiApClientUpdateCallback)) {
                return (ISemWifiApClientUpdateCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onClientUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiApClientUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiApClientUpdateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemWifiApClientDetails semWifiApClientDetails = (SemWifiApClientDetails) parcel.readTypedObject(SemWifiApClientDetails.CREATOR);
                parcel.enforceNoDataAvail();
                onClientUpdated(semWifiApClientDetails);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemWifiApClientUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApClientUpdateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
            public void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiApClientUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semWifiApClientDetails, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
