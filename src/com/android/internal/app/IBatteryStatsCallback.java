package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SemSimpleNetworkStats;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IBatteryStatsCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IBatteryStatsCallback";

    public static class Default implements IBatteryStatsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStatsCallback
        public void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> list) throws RemoteException {
        }
    }

    void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IBatteryStatsCallback {
        static final int TRANSACTION_notifyNetworkStatsUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBatteryStatsCallback.DESCRIPTOR);
        }

        public static IBatteryStatsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBatteryStatsCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBatteryStatsCallback)) {
                return (IBatteryStatsCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "notifyNetworkStatsUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBatteryStatsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBatteryStatsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemSimpleNetworkStats.CREATOR);
                parcel.enforceNoDataAvail();
                notifyNetworkStatsUpdated(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBatteryStatsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBatteryStatsCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IBatteryStatsCallback
            public void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBatteryStatsCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
