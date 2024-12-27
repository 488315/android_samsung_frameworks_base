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

    void onLocationChanged(Location location) throws RemoteException;

    public static class Default implements ISLocationListener {
        @Override // com.samsung.android.location.ISLocationListener
        public void onLocationChanged(Location location) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISLocationListener {
        static final int TRANSACTION_onLocationChanged = 1;

        public Stub() {
            attachInterface(this, ISLocationListener.DESCRIPTOR);
        }

        public static ISLocationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISLocationListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISLocationListener)) {
                return (ISLocationListener) iin;
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
                    return "onLocationChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISLocationListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISLocationListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Location _arg0 = (Location) data.readTypedObject(Location.CREATOR);
                    data.enforceNoDataAvail();
                    onLocationChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISLocationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISLocationListener.DESCRIPTOR);
                    _data.writeTypedObject(location, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
