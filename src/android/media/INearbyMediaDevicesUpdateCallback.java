package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface INearbyMediaDevicesUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.INearbyMediaDevicesUpdateCallback";

    public static class Default implements INearbyMediaDevicesUpdateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.INearbyMediaDevicesUpdateCallback
        public void onDevicesUpdated(List<NearbyDevice> list) throws RemoteException {
        }
    }

    void onDevicesUpdated(List<NearbyDevice> list) throws RemoteException;

    public static abstract class Stub extends Binder implements INearbyMediaDevicesUpdateCallback {
        static final int TRANSACTION_onDevicesUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
        }

        public static INearbyMediaDevicesUpdateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INearbyMediaDevicesUpdateCallback)) {
                return (INearbyMediaDevicesUpdateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDevicesUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(NearbyDevice.CREATOR);
                parcel.enforceNoDataAvail();
                onDevicesUpdated(createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements INearbyMediaDevicesUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INearbyMediaDevicesUpdateCallback.DESCRIPTOR;
            }

            @Override // android.media.INearbyMediaDevicesUpdateCallback
            public void onDevicesUpdated(List<NearbyDevice> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
