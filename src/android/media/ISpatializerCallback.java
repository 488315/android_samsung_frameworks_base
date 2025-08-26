package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerCallback";

    public static class Default implements ISpatializerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerAvailableChanged(boolean z) throws RemoteException {
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerEnabledChanged(boolean z) throws RemoteException {
        }
    }

    void dispatchSpatializerAvailableChanged(boolean z) throws RemoteException;

    void dispatchSpatializerEnabledChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpatializerCallback {
        static final int TRANSACTION_dispatchSpatializerAvailableChanged = 2;
        static final int TRANSACTION_dispatchSpatializerEnabledChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISpatializerCallback.DESCRIPTOR);
        }

        public static ISpatializerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpatializerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpatializerCallback)) {
                return (ISpatializerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "dispatchSpatializerEnabledChanged";
            }
            if (i != 2) {
                return null;
            }
            return "dispatchSpatializerAvailableChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                dispatchSpatializerEnabledChanged(z);
            } else if (i == 2) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                dispatchSpatializerAvailableChanged(z2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpatializerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerCallback
            public void dispatchSpatializerEnabledChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializerCallback
            public void dispatchSpatializerAvailableChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
