package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackerAvailableCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerHeadTrackerAvailableCallback";

    public static class Default implements ISpatializerHeadTrackerAvailableCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializerHeadTrackerAvailableCallback
        public void dispatchSpatializerHeadTrackerAvailable(boolean z) throws RemoteException {
        }
    }

    void dispatchSpatializerHeadTrackerAvailable(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpatializerHeadTrackerAvailableCallback {
        static final int TRANSACTION_dispatchSpatializerHeadTrackerAvailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadTrackerAvailableCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpatializerHeadTrackerAvailableCallback)) {
                return (ISpatializerHeadTrackerAvailableCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchSpatializerHeadTrackerAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                dispatchSpatializerHeadTrackerAvailable(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpatializerHeadTrackerAvailableCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackerAvailableCallback
            public void dispatchSpatializerHeadTrackerAvailable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
