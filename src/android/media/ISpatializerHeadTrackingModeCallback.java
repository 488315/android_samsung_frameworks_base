package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingModeCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerHeadTrackingModeCallback";

    public static class Default implements ISpatializerHeadTrackingModeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws RemoteException {
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws RemoteException {
        }
    }

    void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws RemoteException;

    void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpatializerHeadTrackingModeCallback {
        static final int TRANSACTION_dispatchSpatializerActualHeadTrackingModeChanged = 1;
        static final int TRANSACTION_dispatchSpatializerDesiredHeadTrackingModeChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadTrackingModeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpatializerHeadTrackingModeCallback)) {
                return (ISpatializerHeadTrackingModeCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "dispatchSpatializerActualHeadTrackingModeChanged";
            }
            if (i != 2) {
                return null;
            }
            return "dispatchSpatializerDesiredHeadTrackingModeChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispatchSpatializerActualHeadTrackingModeChanged(i3);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispatchSpatializerDesiredHeadTrackingModeChanged(i4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpatializerHeadTrackingModeCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadTrackingModeCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
