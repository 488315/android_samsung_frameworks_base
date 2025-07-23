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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiApClientListUpdateCallback)) {
                return (ISemWifiApClientListUpdateCallback) queryLocalInterface;
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
                ArrayList createTypedArrayList = parcel.createTypedArrayList(SemWifiApClientDetails.CREATOR);
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onClientListUpdated(createTypedArrayList, readLong);
            } else if (i == 2) {
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                onOverallDataLimitChanged(readLong2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onOverallDataLimitChanged(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
