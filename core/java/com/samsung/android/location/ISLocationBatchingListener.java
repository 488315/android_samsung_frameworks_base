package com.samsung.android.location;

import android.location.Location;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISLocationBatchingListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationBatchingListener";

    void onLocationAvailable(Location[] locationArr, boolean z) throws RemoteException;

    public static class Default implements ISLocationBatchingListener {
        @Override // com.samsung.android.location.ISLocationBatchingListener
        public void onLocationAvailable(Location[] locations, boolean flushCompleted) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISLocationBatchingListener {
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

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISLocationBatchingListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISLocationBatchingListener.DESCRIPTOR);
                    return true;
                default:
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
        }

        private static class Proxy implements ISLocationBatchingListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationBatchingListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationBatchingListener
            public void onLocationAvailable(Location[] locations, boolean flushCompleted) throws RemoteException {
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
