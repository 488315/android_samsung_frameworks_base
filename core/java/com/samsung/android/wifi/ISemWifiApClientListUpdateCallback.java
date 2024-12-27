package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemWifiApClientListUpdateCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.wifi.ISemWifiApClientListUpdateCallback";

    void onClientListUpdated(List<SemWifiApClientDetails> list, long j) throws RemoteException;

    void onOverallDataLimitChanged(long j) throws RemoteException;

    public static class Default implements ISemWifiApClientListUpdateCallback {
        @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
        public void onClientListUpdated(
                List<SemWifiApClientDetails> clientsList, long totalDataUsageInBytes)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
        public void onOverallDataLimitChanged(long dataLimitInBytes) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemWifiApClientListUpdateCallback {
        static final int TRANSACTION_onClientListUpdated = 1;
        static final int TRANSACTION_onOverallDataLimitChanged = 2;

        public Stub() {
            attachInterface(this, ISemWifiApClientListUpdateCallback.DESCRIPTOR);
        }

        public static ISemWifiApClientListUpdateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiApClientListUpdateCallback)) {
                return (ISemWifiApClientListUpdateCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onClientListUpdated";
                case 2:
                    return "onOverallDataLimitChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SemWifiApClientDetails> _arg0 =
                            data.createTypedArrayList(SemWifiApClientDetails.CREATOR);
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    onClientListUpdated(_arg0, _arg1);
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    data.enforceNoDataAvail();
                    onOverallDataLimitChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemWifiApClientListUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApClientListUpdateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onClientListUpdated(
                    List<SemWifiApClientDetails> clientsList, long totalDataUsageInBytes)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    _data.writeTypedList(clientsList, 0);
                    _data.writeLong(totalDataUsageInBytes);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onOverallDataLimitChanged(long dataLimitInBytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiApClientListUpdateCallback.DESCRIPTOR);
                    _data.writeLong(dataLimitInBytes);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
