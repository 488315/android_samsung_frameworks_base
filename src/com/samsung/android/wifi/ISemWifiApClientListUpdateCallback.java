package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemWifiApClientListUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApClientListUpdateCallback";

    public static class Default implements ISemWifiApClientListUpdateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
        public void onClientListUpdated(List<SemWifiApClientDetails> list, long j) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
        public void onOverallDataLimitChanged(long j) throws RemoteException {
        }
    }

    void onClientListUpdated(List<SemWifiApClientDetails> list, long j) throws RemoteException;

    void onOverallDataLimitChanged(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiApClientListUpdateCallback {
        static final int TRANSACTION_onClientListUpdated = 1;
        static final int TRANSACTION_onOverallDataLimitChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemWifiApClientListUpdateCallback.DESCRIPTOR);
        }

        public static ISemWifiApClientListUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemWifiApClientListUpdateCallback)) {
                return (ISemWifiApClientListUpdateCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onClientListUpdated";
            }
            if (i != 2) {
                return null;
            }
            return "onOverallDataLimitChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemWifiApClientDetails.CREATOR);
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                onClientListUpdated(arrayListCreateTypedArrayList, j);
            } else if (i == 2) {
                long j2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                onOverallDataLimitChanged(j2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemWifiApClientListUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApClientListUpdateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onClientListUpdated(List<SemWifiApClientDetails> list, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onOverallDataLimitChanged(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
