package com.samsung.android.wifi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiApSmartCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApSmartCallback";

    void onStateChanged(int i, String str) throws RemoteException;

    public static class Default implements ISemWifiApSmartCallback {
        @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
        public void onStateChanged(int state, String mhsMac) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemWifiApSmartCallback {
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, ISemWifiApSmartCallback.DESCRIPTOR);
        }

        public static ISemWifiApSmartCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiApSmartCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiApSmartCallback)) {
                return (ISemWifiApSmartCallback) iin;
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
                    return "onStateChanged";
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
                data.enforceInterface(ISemWifiApSmartCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemWifiApSmartCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            onStateChanged(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemWifiApSmartCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApSmartCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
            public void onStateChanged(int state, String mhsMac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiApSmartCallback.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeString(mhsMac);
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
