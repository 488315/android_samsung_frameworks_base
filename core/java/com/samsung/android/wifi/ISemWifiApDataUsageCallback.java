package com.samsung.android.wifi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiApDataUsageCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApDataUsageCallback";

    void onDataUsageChanged(String str) throws RemoteException;

    public static class Default implements ISemWifiApDataUsageCallback {
        @Override // com.samsung.android.wifi.ISemWifiApDataUsageCallback
        public void onDataUsageChanged(String value) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemWifiApDataUsageCallback {
        static final int TRANSACTION_onDataUsageChanged = 1;

        public Stub() {
            attachInterface(this, ISemWifiApDataUsageCallback.DESCRIPTOR);
        }

        public static ISemWifiApDataUsageCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiApDataUsageCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiApDataUsageCallback)) {
                return (ISemWifiApDataUsageCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onDataUsageChanged";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemWifiApDataUsageCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemWifiApDataUsageCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onDataUsageChanged(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemWifiApDataUsageCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApDataUsageCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApDataUsageCallback
            public void onDataUsageChanged(String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiApDataUsageCallback.DESCRIPTOR);
                    _data.writeString(value);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
