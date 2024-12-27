package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackerAvailableCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.media.ISpatializerHeadTrackerAvailableCallback";

    void dispatchSpatializerHeadTrackerAvailable(boolean z) throws RemoteException;

    public static class Default implements ISpatializerHeadTrackerAvailableCallback {
        @Override // android.media.ISpatializerHeadTrackerAvailableCallback
        public void dispatchSpatializerHeadTrackerAvailable(boolean available)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements ISpatializerHeadTrackerAvailableCallback {
        static final int TRANSACTION_dispatchSpatializerHeadTrackerAvailable = 1;

        public Stub() {
            attachInterface(this, ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadTrackerAvailableCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializerHeadTrackerAvailableCallback)) {
                return (ISpatializerHeadTrackerAvailableCallback) iin;
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
                    return "dispatchSpatializerHeadTrackerAvailable";
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
                data.enforceInterface(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchSpatializerHeadTrackerAvailable(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpatializerHeadTrackerAvailableCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackerAvailableCallback
            public void dispatchSpatializerHeadTrackerAvailable(boolean available)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                    _data.writeBoolean(available);
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
