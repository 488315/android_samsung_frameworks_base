package com.samsung.android.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISLocationBatchingListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.location.ISLocationBatchingListener";

    void onLocationAvailable(Location[] locationArr, boolean z) throws RemoteException;

    public static class Default implements ISLocationBatchingListener {
        @Override // com.samsung.android.location.ISLocationBatchingListener
        public void onLocationAvailable(Location[] locations, boolean flushCompleted)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISLocationBatchingListener {
        static final int TRANSACTION_onLocationAvailable = 1;

        public Stub() {
            attachInterface(this, ISLocationBatchingListener.DESCRIPTOR);
        }

        public static ISLocationBatchingListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISLocationBatchingListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISLocationBatchingListener)) {
                return (ISLocationBatchingListener) iin;
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
                    return "onLocationAvailable";
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
                data.enforceInterface(ISLocationBatchingListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISLocationBatchingListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Location[] _arg0 = (Location[]) data.createTypedArray(Location.CREATOR);
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onLocationAvailable(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISLocationBatchingListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationBatchingListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationBatchingListener
            public void onLocationAvailable(Location[] locations, boolean flushCompleted)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISLocationBatchingListener.DESCRIPTOR);
                    _data.writeTypedArray(locations, 0);
                    _data.writeBoolean(flushCompleted);
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
